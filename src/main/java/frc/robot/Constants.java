// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }

  public static final class FuelConstants {
    // The intake_launcher is the outer motor
    // The feeder is the inner motor
    public static final int FEEDER_MOTOR_ID = 6;
    public static final int INTAKE_LAUNCHER_MOTOR_ID = 5;

    public static final int FEEDER_MOTOR_CURRENT_LIMIT = 60;
    public static final int LAUNCHER_MOTOR_CURRENT_LIMIT = 60;
  
    public static final double INTAKING_FEEDER_VOLTAGE = -12;
    public static final double INTAKING_INTAKE_VOLTAGE = 10;
    public static final double LAUNCHING_FEEDER_VOLTAGE = 9;
    public static final double LAUNCHING_LAUNCHER_VOLTAGE = 10.6;
    public static final double SPIN_UP_FEEDER_VOLTAGE = -6;
    public static final double SPIN_UP_SECONDS = 1;
  }

  public static final class ClimbConstants {
    // The intake is in the front
    // The climb is on the left side
    public static final int CLIMB_FRONT_ID = 3;
    public static final int CLIMB_BACK_ID = 4;

    public static final int CLIMB_CURRENT_LIMIT = 60;

    // public static final int CLIMBING_UP_VOLTAGE;
    // public static final int CLIMBING_DOWN_VOLTAGE;

    public static final double CLIMB_P = 0;
    public static final double CLIMB_I = 0;
    public static final double CLIMB_D = 0;
  }

  public static final class SwerveConstants {
    // The intake is in the front
    // Left/right is from the robot's perspective
    public static final int FRONT_RIGHT_ANGLE_ID = 21;
    public static final int BACK_RIGHT_ANGLE_ID = 22;
    public static final int BACK_LEFT_ANGLE_ID = 23;
    public static final int FRONT_LEFT_ANGLE_ID = 24;
    
    public static final int FRONT_RIGHT_DRIVE_ID = 31;
    public static final int BACK_RIGHT_DRIVE_ID = 32;
    public static final int BACK_LEFT_DRIVE_ID = 33;
    public static final int FRONT_LEFT_DRIVE_ID = 34;

    public static final int FRONT_RIGHT_CAN_ID = 11;
    public static final int BACK_RIGHT_CAN_ID = 12;
    public static final int BACK_LEFT_CAN_ID = 13;
    public static final int FRONT_LEFT_CAN_ID = 14;
  }
}
