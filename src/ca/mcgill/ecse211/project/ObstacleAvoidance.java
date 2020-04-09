package ca.mcgill.ecse211.project;


/**
 * The ObstacleAvoidance class detects and identifies obstacles near the robot.
 * It utilizes distance readings from the two ultrasonic sensors placed in front of the robot, one near the top part of the robot, one lower to the ground.
 * When both sensors detect an obstacle, then it is considered a block, as blocks are tall enough to be detected by both sensors.
 * When a block is detected, a variant of the wall follower program used in the previous labs is used to circumnavigate the block and return to the search path.
 * When only the lower sensor detects an obstacle, then it is considered to be the car, as the car is not tall enough to be detected by the top sensor.
 * When the car is detected, a flag is sent to the search class.
 * This thread will run continuously over a set interval while the robot is in the search zone.
 * 
 * @author Kaan Gure
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
