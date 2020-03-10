package ca.mcgill.ecse211.project;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * This class is used to define static resources in one place for easy access and to avoid
 * cluttering the rest of the codebase. All resources can be imported at once like this:
 * 
 * <p>{@code import static ca.mcgill.ecse211.lab3.Resources.*;}
 */
public class Resources {

  /** Wheel rotation speed in degrees/sec when the robot is rotating. */
  public static final int ROTATE_SPEED = 70;

  /** Wheel rotation speed in degrees/sec when the robot is going straight. */
  public static final int STRAIGHT_SPEED = 100;

  /**
   * Wheel radius in cm.
   */
  public static final double WHEEL_RAD = 2.130;

  /**
   * Distance between the two wheels of the robot in cm.
   */
  public static final double BASE_WIDTH = 14.8;

  /**
   * Code base sleep time in milliseconds.
   */
  public static final long POLL_SLEEP_TIME = 25;
  
  /**
   * Color detection sleep time in milliseconds.
   */
  public static final long COLOR_SLEEP_TIME = 150;

  /**
   * Tile length and width in cm.
   */
  public static final double TILE_SIZE = 30.48;

  /**
   * Distance below which the robot considers it is facing a wall in cm.
   */
  public static final int THRESHOLD_DISTANCE = 40;

  /**
   * The constant to convert from degrees to radians.
   */
  public static final double DEG_TO_RAD = 0.0175;

  /** Constant below which the sensor detects a line. */
  public static final int LIGHT_SENSOR_THRESHOLD = 10;

  /** Angle to turn by if the left sensor detects a line first. */
  public static final int LEFT_SENSOR_FIRST_LOCALIZATION_ANGLE = -90;

  /** Angle to turn by if the right sensor detects a line first. */
  public static final int RIGHT_SENSOR_FIRST_LOCALIZATION_ANGLE = -85;
  
  /** Path 1 of lab 5. */
  public static final double[][] PATH1 = {{1,7},{3,4},{7,7},{7,4},{4,1}};
  
  /** Path 2 of lab 5. */
  public static final double[][] PATH2 = {{2,2},{1,3},{3,3},{3,2},{2,1}};
  
  /** Path 3 of lab 5. */
  public static final double[][] PATH3 = {{2,1},{3,2},{3,3},{1,3},{2,2}};
  
  /** Path 4 of lab 5. */
  public static final double[][] PATH4 = {{1,2},{2,3},{2,1},{3,2},{3,3}};
  
  /** Yellow ring for lab 5. */
  public static final double[] YELLOW = {0.2098,0.0351,0.1288,0.0192,0.0263,0.0032};
  
  /** Green ring for lab 5. */
  public static final double[] GREEN = {0.0724,0.0181,0.1351,0.0247,0.0202,0.0046};
  
  /** Blue ring for lab 5. */
  public static final double[] BLUE = {0.0259,0.0035,0.1084,0.0072,0.0937,0.0036};
  
  /** Orange ring for lab 5. */
  public static final double[] ORANGE = {0.1594,0.0515,0.0390,0.0113,0.0127,0.0054};

  /**
   * Odometer.
   */
  public static Odometer odometer = Odometer.getOdometer();

  /**
   * LCD.
   */
  public static final TextLCD TEXT_LCD = LocalEV3.get().getTextLCD();

  /**
   * Ultrasonic sensor.
   */
  public static final EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);

  /**
   * Left light sensor.
   */
  public static final EV3ColorSensor leftLightSensor = new EV3ColorSensor(SensorPort.S4);

  /**
   * Right light sensor.
   */
  public static final EV3ColorSensor rightLightSensor = new EV3ColorSensor(SensorPort.S1);

  /**
   * Middle light sensor.
   */
  public static final EV3ColorSensor middleLightSensor = new EV3ColorSensor(SensorPort.S3);

  /**
   * Left motor.
   */
  public static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.D);

  /**
   * Right motor.
   */
  public static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.A);

}
