package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

/**
 * The Class LightLocalizer corrects the angle of the robot to be perpendicular to grid lines and localizes
 * the robot to the intersection of grid lines.
 * 
 */
public class LightLocalizer implements Runnable {
  
  /** The left red sensor value. */
  private static SampleProvider leftRedSensorValue = leftLightSensor.getRedMode();
  
  /** The right red sensor value. */
  private static SampleProvider rightRedSensorValue = rightLightSensor.getRedMode();

  /** The left ls data. */
  private float[] leftLsData = new float[leftLightSensor.sampleSize()];
  
  /** The right ls data. */
  private float[] rightLsData = new float[rightLightSensor.sampleSize()];
  
  /** The left below threshold counter. */
  private int leftBelowThresholdCounter = 0;
  
  /** The right below threshold counter. */
  private int rightBelowThresholdCounter = 0;

  /**
   * Filter method that checks if the left light sensor detects a line.
   *
   * @param value the value
   * @return true if a line is detected
   */
  private boolean leftLightSensorDetectsLine(float value) {
    int convertedValue = (int) (value * 100.0);
    if (convertedValue > LIGHT_SENSOR_THRESHOLD && leftBelowThresholdCounter == 1) {
      leftBelowThresholdCounter = 0;
    }
    if (convertedValue < LIGHT_SENSOR_THRESHOLD) {
      leftBelowThresholdCounter++;
    }
    if (leftBelowThresholdCounter == 2) {
      leftBelowThresholdCounter = 0;
      return true;
    }
    return false;
  }
  
  /**
   * Filter method that checks if the right light sensor detects a line.
   *
   * @param value the value
   * @return true if a line is detected
   */
  private boolean rightLightSensorDetectsLine(float value) {
    int convertedValue = (int) (value * 100.0);
    if (convertedValue > LIGHT_SENSOR_THRESHOLD && rightBelowThresholdCounter == 1) {
      rightBelowThresholdCounter = 0;
    }
    if (convertedValue < LIGHT_SENSOR_THRESHOLD) {
      rightBelowThresholdCounter++;
    }
    if (rightBelowThresholdCounter == 2) {
      rightBelowThresholdCounter = 0;
      return true;
    }
    return false;
  }
  
  /**
   * Where the main logic runs.
   */
  public void run() {

    boolean leftMotorStopped = false;
    boolean rightMotorStopped = false;

    while (true) {
      leftRedSensorValue.fetchSample(leftLsData, 0);
      rightRedSensorValue.fetchSample(rightLsData, 0);
      if (leftLightSensorDetectsLine(leftLsData[0])) {
        leftMotor.setSpeed(0);
        leftMotorStopped = true;
      }
      if (rightLightSensorDetectsLine(rightLsData[0])) {
        rightMotor.setSpeed(0);
        rightMotorStopped = true;
      }
      if (leftMotorStopped && !rightMotorStopped) {
        Main.leftSensorFirst = true;
      }
      if (!leftMotorStopped && rightMotorStopped) {
        Main.leftSensorFirst = false;
      }
      if (leftMotorStopped && rightMotorStopped) {
        break;
      }
      Utility.sleepFor(POLL_SLEEP_TIME);
    }
    Main.lightCorrectionComplete = true;
  }
}
