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
    public static final int kDriverControllerPort = 0;
  }


  public final class Candle{
    public static final int CANdleID = 2;
  }

  public static class MOTOR{
    public static final int MOTOR_1_ID = 5;
    public static final int MOTOR_2_ID = 6;
    public static final int CANCODER_1_ID = 3;

    public static final double MOTOR_POSITION_1 = 50;//位置1
    public static final double MOTOR_POSITION_2 = 0;//位置2 

    public static final double MOTOR_VELOCITY_1 = 10;//速度1
    public static final double MOTOR_VELOCITY_2 = -10;//速度2

    public static final double expected_error =1.0;
  
    public static double wantedvalue = 50;
    public static double current_position = 0;
  
  }
}

