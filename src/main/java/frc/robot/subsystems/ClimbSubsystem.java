package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import static frc.robot.Constants.ClimbConstants.*;

public class ClimbSubsystem extends SubsystemBase{
  private final TalonFX m_climbFront;
  private final TalonFX m_climbBack;

  public ClimbSubsystem() {
    m_climbFront = new TalonFX(CLIMB_FRONT_ID);
    m_climbBack = new TalonFX(CLIMB_BACK_ID);

    TalonFXConfiguration climbConfig = new TalonFXConfiguration()
      .withCurrentLimits(
        new CurrentLimitsConfigs()
          .withStatorCurrentLimit(Amps.of(CLIMB_CURRENT_LIMIT))
          .withStatorCurrentLimitEnable(true)
      );
      climbConfig.Slot0.kP = CLIMB_P;
      climbConfig.Slot0.kI = CLIMB_I;
      climbConfig.Slot0.kD = CLIMB_D;


    m_climbBack.getConfigurator().apply(climbConfig);
    m_climbFront.getConfigurator().apply(climbConfig);
  }

}
