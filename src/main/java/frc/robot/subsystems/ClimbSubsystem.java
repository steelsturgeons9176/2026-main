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


  public ClimbSubsystem() {
    m_climbFront = new TalonFX(CLIMB_FRONT_ID);
    m_climbBack = new TalonFX(CLIMB_BACK_ID);

    climbConfig
  }
  
  @Override
  public void periodic() {
    // This is where I'd put my periodic code... IF I HAD ANY
  }

  public void stop() {
    m_climbBack.set(0);
    m_climbFront.set(0);
  }
}
