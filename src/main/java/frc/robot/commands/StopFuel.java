package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FuelSubsystem;

/**
 * Stops the FuelSubsystem, meant to be used after finishing LaunchSequence.
 */
public class StopFuel extends Command{
    
  FuelSubsystem fuelSubsystem;

  public StopFuel(FuelSubsystem fuelSystem) {
    addRequirements(fuelSystem);
    this.fuelSubsystem = fuelSystem;
  }

  @Override
  public void initialize() {
    fuelSubsystem
      .stop();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
