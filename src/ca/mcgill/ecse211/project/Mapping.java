package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;


/**
 * The Mapping class converts raw map data to coordinates that are used by the robot after initial localization.
 * As the robot will localize in different directions based on its starting zone, its internal x and y-axis will be different with respect to the given raw map coordinates
 * so these coordinates must be converted to a form that respects the internal localizer x and y-axis.
 *
 */
public class Mapping {
  
  public static char bridgeOrientation; //Either X or Y
  
  public static double[] bridgeExit = new double[2];
  
  public static void mapOdo(int corner) {
    if (corner == 0) {
      odometer.setXyt(TILE_SIZE, TILE_SIZE, 0);
    }
    else if (corner == 1) {
      odometer.setXyt(14*TILE_SIZE, TILE_SIZE, 270);
    }
    else if (corner == 2) {
      odometer.setXyt(14*TILE_SIZE, 8*TILE_SIZE, 180);
    }
    else {
      odometer.setXyt(TILE_SIZE, 8*TILE_SIZE, 90);
    }
  }
  
  
  
  /**
   * Finds which way the bridge is oriented with respect to the robot by taking the difference between the lower left and upper right x-coordinates of the bridge.
   * As they are only 1 square apart, if the difference is 0, then the bridge must be oriented in the Y-direction, else, the x-direction.
   * @param TN_LL_x : the lower left x-coordinate of the bridge.
   * @param TN_UR_x : the upper right x-coordinate of the bridge.
   * @return "X" if oriented in the x-direction, "Y" if oriented in the y-direction.
   */
  public char getBridgeOrient(int TN_LL_x, int TN_UR_x) {
    
    int dx = Math.abs(TN_LL_x - TN_UR_x);
    
    if (dx == 2) {
      bridgeOrientation = 'X';
      return 'X';
    } else {
      bridgeOrientation = 'Y';
      return 'Y';
    }
    
  }
  
  /**
   * calculates the bridge exit coordinate from the corner coordinates of the bridge
   * @param TN_LL_x : Lower left x-coordinate of the bridge
   * @param TN_LL_y : Lower left y-coordinate of the bridge
   * @param TN_UR_x : Upper right x-coordinate of the bridge
   * @param TN_UR_y : Upper right y-coordinate of the bridge
   * @return The (x,y) coordinate of the bridge exit
   */
  public double[] getBridgeExit(int TN_LL_x, int TN_LL_y, int TN_UR_x, int TN_UR_y) {
    //IN PROGRESS
    throw new java.lang.UnsupportedOperationException("Not implemented yet");
    /*char orientation = getBridgeOrient(TN_LL_x, TN_UR_x);
    int[] exit = new int[2];
    if(orientation == 'X') {
      exit[0] = TN_UR_x;
      if(TN_UR_y > TN_LL_y) {
        
      }
    }*/
    
  }
  
  
  

}
