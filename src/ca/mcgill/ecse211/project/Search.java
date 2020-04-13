package ca.mcgill.ecse211.project;

/**
 * The search class enables the robot to search for the cart in the search zone.
 * 
 * TODO: Determine whether the s-pattern or the center scan approach should be used.
 * 
 * In addition to sweeping the search area, this class should enable the robot to return to its search path after an 
 * obstacle avoidance routine has been completed.
 * 
 * It also contains the method that allows the robot to capture the cart after it has been detected by the 
 * ObstacleAvoidance class.
 * 
 * @author Kaan Gure
 */
public class Search {
  
  /**
   * This method has not yet been implemented
   * 
   * The sNavigation() method moves the robot on an S shaped path along the grid lines in the search zone
   * in order to perform a grid-search for the car.
   * 
   * If the center-scan approach were to be used, this method should be renamed accordingly. 
   * 
   */
  public void sNavigation() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    
  }
  
  /**
   * This method has not yet been implemented
   * 
   * Returns the robot back to the search path after an obstacle has been circumnavigated
   * and the robot has deviated from the search path. The odometer should be used to determine the required movement to 
   * rejoin the searching trajectory.
   * 
   * @param xyt: The current x,y coordinates and t angle of the robot from the odometer.
   */
  public void returnToPath(double[] xyt) {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");

  }
  
  /**
   * This method has not yet been implemented
   * 
   * Attaches the robot to the car once it has been detected by getting close to it and engaging the grabber arms 
   * to capture the car inside the robot.
   * 
   * With the current hardware design, pushing the cart a little bit will actually help place it in a position where the
   * robot can effectively grab it.
   * 
   */
  public void attach() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");

  }
  
  /**
   * This method has not yet been implemented
   * 
   * Main logic for the search operation takes place here by utilizing the methods in this class. This method should also
   * manage the obstacle avoidance thread.
   * 
   */
  public static void search() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");

  }

}
