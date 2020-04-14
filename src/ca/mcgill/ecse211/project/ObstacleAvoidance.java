package ca.mcgill.ecse211.project;


/**
 * The ObstacleAvoidance class detects and identifies obstacles near the robot.
 * It utilizes distance readings from the two ultrasonic sensors placed in front of the robot, one near the top part of 
 * the robot, one lower to the ground. When both sensors detect an obstacle, then it could be a block or a wall, 
 * as they are both tall enough to be detected by both sensors. 
 * 
 * TODO: When a block is detected, determine whether the wall follower approach or the tile avoidance approach should 
 * be used, and implement the circumnavigate method to go around the block and return to the search path.
 * 
 * When only the lower sensor detects an obstacle, then it is considered to be the cart, as the cart is not tall enough 
 * to be detected by the top sensor.
 * When the car is detected, a flag is sent to the search class.
 * This thread will run continuously over a set interval while the robot is in the search zone.
 * 
 * For more information on this class and its logic, refer to the Group08_SOFTWARE_OVERVIEW_DOC_v4.0 section 9.8
 * 
 * @author Kaan Gure
 *
 */
public class ObstacleAvoidance implements Runnable{
  
  /**
   * This method has not yet been implemented.
   * 
   * Checks if there is an obstacle in front of the robot utilizing the ultrasonic sensors. An obstacle is said to be 
   * detected if either only the bottom sensor detects a sufficiently low distance, or both sensors detect sufficiently 
   * low distances.
   * 
   * @return true if an obstacle has been detected.
   * 
   */
  public boolean checkForObstacle() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    
  }
  
  /**
   * This method has not yet been implemented.
   * 
   * Identifies if the obstacle is the cart, a block, or a wall. The obstacle is considered to be the cart if only the 
   * lower ultrasonic sensor detects it. If both sensors detect an obstacle, the odometer should be used to determine 
   * whether the obstacle is in the search zone (block) or not (wall).
   *  
   * @return 0 if car, 1 if block, 2 if wall.
   * 
   */
  public int identifyObstacle() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    
  }
  
  /**
   * This method has not yet been implemented.
   * 
   * Moves the robot around the obstacle in front of it.
   * 
   * TODO: Determine if the wall follower approach or the tile avoidance approach should be used. 
   * 
   * Note the wall follower approach would require the top ultrasonic sensor to be rotated by 45 degrees
   * 
   * In both cases, the odometer should be used to determine which side it would be best to move around the block to 
   * avoid hitting other obstacles during the circumnavigation.
   * 
   */
  public void circumnavigate() {
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
  }
  
  /**
   * This method has not yet been implemented.
   * 
   * This is where the main logic for ObstacleAvoidance will run. The thread will be started by the Search class and 
   * should only interrupt the motion of the robot if an obstacle avoidance is necessary.
   * 
   */
  public void run() {
    
  }

}
