package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The UltrasonicLocalizer class executes the initial localization of the robot. It's goal is to detect very
 * approximately the direction of the corner that the 2 outside walls form. This does not have to be precise, as 
 * light localization will then perform the appropriate corrections.
 * 
 * @author Maxime Buteau
 * @author Rayan Wehbe
 */
public class UltrasonicLocalizer implements Runnable{

  /** The us data. */
  private float[] usData = new float[usSensor.sampleSize()];

  /** The distance from the wall. */
  private volatile int distance;

  /** The queue used for filtering the us data. */
  private Queue<Float> queue = new LinkedList<Float>();
  
  /** The filter list used to calculate the median of the 5 last values. */
  private ArrayList<Float> filterList = new ArrayList<Float>();

  /**
   * Lists of thetas associated with distances to wall. They contain only thetas  with distances 
   * under the THRESHOLD_DISTANCE. Each one is filled during a 360 degrees turn.
   */
  private ArrayList<Double> angleList = new ArrayList<Double>();
  
  /**
   * Turns the robot to a 90 degree angle from the Y-axis. It uses the approximate position the robot has detected 
   * for the corner as a reference point.
   */
  public void turnToNinety() {
    Utility.turnBy(angleList.get(angleList.size() / 2) - 135, ROTATE_SPEED);
  }

  /**
   * Checks to make sure the robot points away from the wall before starting the localization process. This ensures 
   * that the middle value of the angles recorded when the ultrasonic sensor detects a low enough distance is approximately 
   * the corner.
   * 
   * The robot turns by 30 degrees until distance reading is high enough.
   */
  public void initialPositionning() {
    usSensor.fetchSample(usData, 0);

    // If distance is too low rotate by 30 deg.
    if(usData[0] < 100) {
      Utility.turnBy(30, ROTATE_SPEED);
      Utility.sleepFor(2*POLL_SLEEP_TIME);
      leftMotor.resetTachoCount();
      rightMotor.resetTachoCount();
      initialPositionning();
    }
  }

  

  /**
   * Where the logic of the UltrasonicLocalizer runs. The robot first performs a full 360 degree turn, then identifies
   * the corner and uses it to turn and face the positive x axis.
   */
  public void run() {

    initialPositionning();
    Utility.resetTacho();
    odometer.setTheta(0);
    Utility.turnByWithoutWaiting(360, ROTATE_SPEED);

    while (true) {

      readUSDistance();
      // During first turn add theta values associated with low distances to first list.
      if (distance <= THRESHOLD_DISTANCE) {
        angleList.add(odometer.getXyt()[2]);
      }
      
      if (Utility.motorsAreStopped()) {
        break;
      }
      Utility.sleepFor(POLL_SLEEP_TIME);
    }
    turnToNinety();
    Main.ultrasonicLocalizationComplete = true;
  }

  /**
   * Updates value of distance based on filtered readings from the ultrasonic sensor.
   */
  public void readUSDistance() {
    usSensor.fetchSample(usData, 0);
    if (queue.isEmpty() && filterList.isEmpty()) {
      for (int i = 0; i < 5; i ++) {
        queue.add(usData[0]);
        filterList.add(usData[0]);
      }
    }
    else {
      int index = filterList.indexOf(queue.remove());
      queue.add(usData[0]);
      filterList.remove(index);
      filterList.add(usData[0]);
    }
    distance = filter(filterList);
  }
  
  /**
   * Filter for the ultrasonic sensor. The median of a moving window is used.
   *
   * @param lastFiveValues the last five values
   * @return the median value
   */
  int filter(ArrayList<Float> lastFiveValues) {
    float filteredDist = 0;
    Collections.sort(lastFiveValues);
    filteredDist = lastFiveValues.get(2);
    return ((int) (filteredDist * 100.0));
  }
}


