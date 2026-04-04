// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FuelSubsystem;
import frc.robot.Constants.FuelConstants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LaunchSequence extends Command {
  /** Creates a new LaunchSequence. */
  FuelSubsystem fuelSubsystem;
  double launchSpeed;

  public LaunchSequence(FuelSubsystem fuelSubsystem, double launchSpeed) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    this.fuelSubsystem = fuelSubsystem;
    this.launchSpeed = launchSpeed;
  }

  @Override
  public void initialize() {
    CommandScheduler.getInstance().schedule(
      new SpinUp(this.fuelSubsystem, this.launchSpeed)
        .withTimeout(FuelConstants.SPIN_UP_SECONDS)
      );
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
