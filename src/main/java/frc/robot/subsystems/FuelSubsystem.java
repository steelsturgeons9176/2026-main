// Arden Boettcher
// 1/31/2026

package frc.robot.subsystems;

// wpilib imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
// CTR Electronics imports
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

// Internal imports
import static frc.robot.Constants.FuelConstants.*;

public class FuelSubsystem extends SubsystemBase{
    // Create the intake and feeder motors
    private final TalonFX feederRoller;
    private final TalonFX intakeLauncherRoller;
    
    // The constructor
    
    public FuelSubsystem() {
        // Define the motors in the constructor
        intakeLauncherRoller = new TalonFX(INTAKE_LAUNCHER_MOTOR_ID);
        feederRoller = new TalonFX(FEEDER_MOTOR_ID);
        
        //Configure the motors using a TalonFXConfiguration
        TalonFXConfiguration feederConfig = new TalonFXConfiguration()
            .withCurrentLimits(
                new CurrentLimitsConfigs()
                    .withStatorCurrentLimit(Amps.of(FEEDER_MOTOR_CURRENT_LIMIT))
                    .withStatorCurrentLimitEnable(true)
            );


        TalonFXConfiguration launcherConfig = new TalonFXConfiguration()
            .withCurrentLimits(
                new CurrentLimitsConfigs()
                    .withStatorCurrentLimit(Amps.of(LAUNCHER_MOTOR_CURRENT_LIMIT))
                    .withStatorCurrentLimitEnable(true)
            )
            .withMotorOutput(
                new MotorOutputConfigs()
                    .withInverted(InvertedValue.CounterClockwise_Positive)
                );
        
        
        // Configure the motors using the configs
        feederRoller.getConfigurator().apply(feederConfig);
        intakeLauncherRoller.getConfigurator().apply(launcherConfig);
    }

// A method to set the voltage of the intake roller
  public void setIntakeLauncherRoller(double voltage) {
    intakeLauncherRoller.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
  }

  // A method to stop the rollers
  public void stop() {
    feederRoller.set(0);
    intakeLauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
