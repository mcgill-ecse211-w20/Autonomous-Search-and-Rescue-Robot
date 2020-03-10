package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.BASE_WIDTH;
import static ca.mcgill.ecse211.project.Resources.WHEEL_RAD;
import static ca.mcgill.ecse211.project.Resources.leftMotor;
import static ca.mcgill.ecse211.project.Resources.rightMotor;

public final class Utility {
  
  /**
   * Stops both motors.
   */
  public static void stopMotors() {
    leftMotor.setSpeed(0);
    rightMotor.setSpeed(0);
  }
  
  /**
   * Returns whether the motors are stopped.
   * 
   * @return whether the motors are stopped as a boolean
   */
  public static boolean motorsAreStopped() {
    if (!leftMotor.isMoving() && !rightMotor.isMoving()) {
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Converts input distance traveled by each wheel to the rotation angle of the robot.
   * 
   * @param distance the distance traveled by each wheel
   * @return the angle rotated by the robot
   */
  public static int convertDistance(double distance) {
    return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
  }
  
  /**
   * Converts input angle to the total rotation of each wheel needed to rotate the robot by that
   * angle.
   * 
   * @param angle the input angle
   * @return the wheel rotations necessary to rotate the robot by the angle
   */
  public static int convertAngle(double angle) {
    return convertDistance(Math.PI * BASE_WIDTH * angle / 360.0);
  }
  
  /**
   * Turns the robot by an input angle. Positive angles move the robot in the clockwise direction
   * 
   * @param angle the angle to turn
   * @param speed of the motors
   */
  public static void turnBy(double angle, int motorSpeed) {
    leftMotor.setSpeed(motorSpeed);
    rightMotor.setSpeed(motorSpeed);
    leftMotor.rotate(convertAngle(angle), true);
    rightMotor.rotate(-convertAngle(angle), false);
  }
  
  /**
   * Turns the robot by an input angle, but does not wait for move to complete. Positive angles move the robot in the clockwise direction
   * 
   * @param angle the angle to turn
   * @param speed of the motors
   */
  public static void turnByWithoutWaiting(double angle, int motorSpeed) {
    leftMotor.setSpeed(motorSpeed);
    rightMotor.setSpeed(motorSpeed);
    leftMotor.rotate(convertAngle(angle), true);
    rightMotor.rotate(-convertAngle(angle), true);
  }
  
  /**
   * Moves the robot straight for an input distance
   * 
   * @param distance
   * @param motorSpeed
   */
  public static void moveStraightFor(double distance, int motorSpeed) {
    leftMotor.setSpeed(motorSpeed);
    rightMotor.setSpeed(motorSpeed);
    leftMotor.rotate(convertDistance(distance), true);
    rightMotor.rotate(convertDistance(distance), false);
  }
  
  /**
   * Moves the robot straight for an input distance, but does not wait for move to complete
   * 
   * @param distance
   * @param motorSpeed
   */
  public static void moveStraightForWithoutWaiting(double distance, int motorSpeed) {
    leftMotor.setSpeed(motorSpeed);
    rightMotor.setSpeed(motorSpeed);
    leftMotor.rotate(convertDistance(distance), true);
    rightMotor.rotate(convertDistance(distance), true);
  }
  
  /**
   * Moves the robot straight at a given speed.
   * 
   * @param motorSpeed
   */
  public static void moveStraight(int motorSpeed) {
    leftMotor.setSpeed(motorSpeed);
    rightMotor.setSpeed(motorSpeed);
    leftMotor.forward();
    rightMotor.forward();
  }
  
  /**
   * Resets the tachometer count of both motors.
   */
  public static void resetTacho() {
    leftMotor.resetTachoCount();
    rightMotor.resetTachoCount();
  }
  
  /**
   * Pauses a thread for an input duration.
   * 
   * @param duration the duration to wait
   */
  public static void sleepFor(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
    }
  }
}
