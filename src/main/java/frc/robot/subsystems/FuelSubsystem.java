// Arden Boettcher
// 1/31/2026

package frc.robot.subsystems;

// wpilib imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Amps;

// CTR Electronics imports
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

// Internal imports
import static frc.robot.Constants.FuelConstants.*;

public class FuelSubsystem extends SubsystemBase{
    // Create the intake and feeder motors
    private final TalonFX m_feederRoller;
    private final TalonFX m_intakeLauncherRoller;
    
    // The constructor
    public FuelSubsystem() {
        // Define the motors in the constructor
        m_intakeLauncherRoller = new TalonFX(INTAKE_LAUNCHER_MOTOR_ID);
        m_feederRoller = new TalonFX(FEEDER_MOTOR_ID);
        
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
        m_feederRoller.getConfigurator().apply(feederConfig);
        m_intakeLauncherRoller.getConfigurator().apply(launcherConfig);
    }

// A method to set the voltage of the intake roller
  public void setIntakeLauncherRoller(double voltage) {
    m_intakeLauncherRoller.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setFeederRoller(double voltage) {
    m_feederRoller.setVoltage(voltage);
  }

  // A method to stop the rollers
  public void stop() {
    m_feederRoller.set(0);
    m_intakeLauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
