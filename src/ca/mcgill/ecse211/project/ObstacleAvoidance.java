package ca.mcgill.ecse211.project;


/**
 * The ObstacleAvoidance class detects and identifies obstacles near the robot.
 * This thread will run continuously over a set interval while the robot is in the search zone.
 *
 */
public class ObstacleAvoidance implements Runnable{
  
  /**
   * Checks if there is an obstacle in front of the robot utilizing the ultrasonic and color sensors.
   * @return true if an obstacle has been detected.
   * 
   */
  public boolean checkForObstacle() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    
  }
  
  /**
   * Identifies if the obstacle is the car, a block, or a wall.
   * @return 0 if car, 1 if block, 2 if wall.
   * 
   */
  public int identifyObstacle() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    
  }
  
  /**
   * Moves the robot around the obstacle in front of it using the shortest distance around it.
   * The shortest distance is obtained by sweeping with the ultrasonic sensor.
   * 
   */
  public void circumnavigate() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
  }
  
  /**
   * This is where the main logic for ObstacleAvoidance runs.
   * 
   */
  public void run() {
    
  }

}
