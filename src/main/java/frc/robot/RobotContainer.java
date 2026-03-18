// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// Commands
import frc.robot.commands.*;

// Constants
import frc.robot.Constants.OperatorConstants;
import static frc.robot.Constants.FuelConstants.*;

// Subsystems
import frc.robot.subsystems.FuelSubsystem;
import frc.robot.subsystems.Swerve.TunerConstants;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

// CTRE
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

// Pathplanner
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.FollowPathCommand;


// First
import static edu.wpi.first.units.Units.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Setting up swerve
  private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
  private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
          .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);

  // Subsystems :P
  private final FuelSubsystem fuelSubsystem = new FuelSubsystem();
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  
  // Controllers
  private final CommandXboxController operatorController = new CommandXboxController(
    OperatorConstants.OPERATOR_CONTROLLER_PORT);

  private final CommandPS4Controller driverController = new CommandPS4Controller(
    OperatorConstants.DRIVER_CONTROLLER_PORT);

  // The autonomous chooser
  private final SendableChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    NamedCommands.registerCommand("Launch Far", new LaunchSequence(fuelSubsystem, LAUNCHING_LAUNCHER_FAR_VOLTAGE));
    NamedCommands.registerCommand("Launch Close", new Launch(fuelSubsystem, LAUNCHING_LAUNCHER_CLOSE_VOLTAGE));
    NamedCommands.registerCommand("Eject", new Eject(fuelSubsystem));
    NamedCommands.registerCommand("Intake", new Intake(fuelSubsystem));
    NamedCommands.registerCommand("Stop", new StopFuel(fuelSubsystem));
    
    autoChooser = AutoBuilder.buildAutoChooser();
    
    // Set the options to show up in the Dashboard for selecting auto modes. If you
    // add additional auto modes you can add additional lines here with
    // autoChooser.addOption
    SmartDashboard.putData("Auto Chooser", autoChooser);

    CommandScheduler.getInstance().schedule(FollowPathCommand.warmupCommand());

    // Configure the trigger bindings
    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    operatorController.leftBumper().whileTrue(new Intake(fuelSubsystem));


    operatorController.b().whileTrue(new LaunchSequence(fuelSubsystem, LAUNCHING_LAUNCHER_FAR_VOLTAGE));
    operatorController.y().whileTrue(new LaunchSequence(fuelSubsystem, LAUNCHING_LAUNCHER_MID_VOLTAGE));
    operatorController.x().whileTrue(new LaunchSequence(fuelSubsystem, LAUNCHING_LAUNCHER_CLOSE_VOLTAGE));
    

    operatorController.a().whileTrue(new StopFuel(fuelSubsystem));
    
    operatorController.rightBumper().whileTrue(new Eject(fuelSubsystem));
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.

    // Note that X is defined as forward according to WPILib convention,
    // and Y is defined as to the left according to WPILib convention.
    drivetrain.setDefaultCommand(
      // Drivetrain will execute this command periodically
      drivetrain.applyRequest(() ->
        drive.withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
          .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
          .withRotationalRate(-driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
      )
  );

    // Idle while the robot is disabled. This ensures the configured
    // neutral mode is applied to the drive motors while disabled.
    final var idle = new SwerveRequest.Idle();
    RobotModeTriggers.disabled().whileTrue(
      drivetrain.applyRequest(() -> idle).ignoringDisable(true)
    );

    driverController.cross().whileTrue(drivetrain.applyRequest(() -> brake));
    driverController.circle().whileTrue(drivetrain.applyRequest(() ->
      point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))
    ));

    // Run SysId routines when holding back/start and X/Y.
    // Note that each routine should be run exactly once in a single log.
    driverController.share().and(driverController.triangle()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
    driverController.share().and(driverController.square()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
    driverController.options().and(driverController.triangle()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
    driverController.options().and(driverController.square()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

    // Reset the field-centric heading on left bumper press.
    driverController.L1().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

    drivetrain.registerTelemetry(logger::telemeterize);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
