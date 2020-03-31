package ca.mcgill.ecse211.project;

/**
 * The search class enables the robot to search for the car in the search zone.
 * It utilizes an S-shaped search pattern, sweeping the search zone from one end to the other.
 * It also enables the robot to return to its search path after an obstacle avoidance routine has been completed.
 * It also contains the method that allows the robot to capture the car after it has been detected by the ObstacleAvoidance class.
 *
 */
public class Search {
  
  /**
   * The sNavigation() method moves the robot on an S shaped path along the grid lines in the search zone
   * in order to perform a grid-search for the car.
   * 
   */
  public void sNavigation() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    
  }
  
  /**
   * Returns the robot back to the search path after an obstacle has been circumnavigated
   * and the robot has deviated from the search path.
   * 
   * @param xyt: The current x,y coordinates and t angle of the robot from the odometer.
   */
  public void returnToPath(double[] xyt) {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");

  }
  
  /**
   * Attaches the robot to the car once it has been detected by getting close to it and engaging the grabber arms to capture the car inside the robot.
   * 
   */
  public void attach() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");

  }
  
  /**
   * Main logic for the search operation takes place here by utilizing the methods in this class.
   * 
   */
  public static void search() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");

  }

}
