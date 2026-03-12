package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;

import static frc.robot.Constants.ClimbConstants.*;
 
public class ClimbSubsystem extends SubsystemBase{
  // Create the motors and their config
  private final TalonFX m_climbFront;
  private final TalonFX m_climbBack;
  
  
  
  public ClimbSubsystem() {
    m_climbFront = new TalonFX(CLIMB_FRONT_ID);
    m_climbBack = new TalonFX(CLIMB_BACK_ID);
    m_climbBack.setControl(new Follower(CLIMB_FRONT_ID, null));
    
    TalonFXConfiguration climbConfig = new TalonFXConfiguration()
      .withCurrentLimits(
        new CurrentLimitsConfigs()
          .withStatorCurrentLimit(Amps.of(CLIMB_CURRENT_LIMIT))
          .withStatorCurrentLimitEnable(true))
      .withMotorOutput(
        new MotorOutputConfigs()
          .withInverted(InvertedValue.CounterClockwise_Positive)
      );
    
      m_climbBack.getConfigurator().apply(climbConfig);
      m_climbFront.getConfigurator().apply(climbConfig);
  }
  
  @Override
  public void periodic() {
    // This is where I'd put my periodic code... IF I HAD ANY
  }

  public void up() {
    m_climbFront.setVoltage(CLIMBING_UP_VOLTAGE);
  }

  public void down() {

  }

  public void hold() {
    
  }


  public void stop() {
    m_climbFront.setVoltage(0);;
  }
}
