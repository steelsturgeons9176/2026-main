package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Amps;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;

import static frc.robot.Constants.ClimbConstants.*;
 
public class ClimbSubsystem extends SubsystemBase{
  // Create the motors and their config
  private final TalonFX m_climbFront;
  private final TalonFX m_climbBack;
  private TalonFXConfiguration climbConfig;

  private Timer timer;

  // Does something???
  private final PositionVoltage positionVoltageRequest = 
    new PositionVoltage(0)
      .withUpdateFreqHz(0);

  // Position and Velocity
  private final StatusSignal<Angle> position;
  private final StatusSignal<AngularVelocity> velocity;

  // Creating the profile variables
  private TrapezoidProfile profile;
  private TrapezoidProfile.State previousProfiledReference;

  // Creating the PID variables
  private double kP;
  private double kI;
  private double kD;

  private ElevatorFeedforward ff =
    new ElevatorFeedforward(CLIMB_S, CLIMB_G, CLIMB_V);


  public ClimbSubsystem() {
    timer = new Timer();
    timer.start();
    timer.reset();

    // Set Motor IDs & Make Objects
    m_climbFront = new TalonFX(CLIMB_FRONT_ID);
    m_climbBack = new TalonFX(CLIMB_BACK_ID);

    // Create Motor Configuration
    climbConfig = new TalonFXConfiguration()
      .withCurrentLimits(
        new CurrentLimitsConfigs()
          .withStatorCurrentLimit(Amps.of(CLIMB_CURRENT_LIMIT))
          .withStatorCurrentLimitEnable(true)
      );
      climbConfig.Slot0.kP = CLIMB_P;
      climbConfig.Slot0.kI = CLIMB_I;
      climbConfig.Slot0.kD = CLIMB_D;

    // Apply Configuration
    m_climbBack.getConfigurator().apply(climbConfig);
    m_climbFront.getConfigurator().apply(climbConfig);

    position = m_climbBack.getPosition();
    velocity = m_climbBack.getVelocity();

    // Define the trapezoid profile
    previousProfiledReference =
      new TrapezoidProfile.State();
    profile =
      new TrapezoidProfile(
        new TrapezoidProfile.Constraints(CLIMB_TRAP_MAXV, CLIMB_TRAP_MAXA)
        );

    kP = CLIMB_P;
    kI = CLIMB_I;
    kD = CLIMB_D;
  }
  
  @Override
  public void periodic() {
    // This is where I'd put my periodic code... IF I HAD ANY
  }

  public void updateMotionProfile() {
    timer.reset();
  }

  public double getP() {
    return kP;
  }

  public double getI() {
    return kI;
  }

  public double getD() {
    return kD;
  }

  public void updatePID() {
   climbConfig.Slot0.kP = kP;
   climbConfig.Slot0.kI = kI;
   climbConfig.Slot0.kD = kD;
  }

  public void stop() {
    m_climbBack.set(0);
    m_climbFront.set(0);
  }
}
