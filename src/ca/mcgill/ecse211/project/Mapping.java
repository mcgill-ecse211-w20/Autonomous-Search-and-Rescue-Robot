package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;


/**
 * The Mapping class converts raw map data to coordinates that are used by the robot after initial localization.
 * As the robot will localize in different directions based on its starting zone, its internal x and y-axis will be different with respect to the given raw map coordinates
 * so these coordinates must be converted to a form that respects the internal localizer x and y-axis.
 *
 */
public class Mapping {
  
  /**
   * Determines the team that the robot is on, either red or green
   * @return true if team is red
   */
  private static boolean isRedTeam() {
    if (redTeam == TEAM_NUMBER) {
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Sets the odometer to the correct coordinates depending on the starting corner of the robot. 
   * Assumes an accurate initial localization.
   */
  public static void mapOdo() {
    int corner;

    if (isRedTeam()) {
      corner = redCorner;
    }
    else {
      corner = greenCorner;
    }
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
  private static char getBridgeOrient(double TN_LL_x, double TN_UR_x) {

    int dx = (int) Math.abs(TN_LL_x - TN_UR_x);

    if (dx == 2) {
      return 'X';
    } else {
      return 'Y';
    }
  }
  
  /**
   * @return the point that the robot must travel to so that it is in line with the bridge. Ensures that the robot
   * follows the grid lines
   */
  public static double[] getPointInLineWithBridge() {
    char bridgeOrientation;
    double[] pointInLineWithBridge = new double[2];
    if (isRedTeam()) {
      bridgeOrientation = getBridgeOrient(tnr.ll.x, tnr.ur.x);
      if (bridgeOrientation == 'X') {
        //move only in y-direction
        pointInLineWithBridge[0] = odometer.getXyt()[0];
        pointInLineWithBridge[1] = ((tnr.ll.y + tnr.ur.y)/2) * TILE_SIZE;
      }
      else {
        //move only in x-direction
        pointInLineWithBridge[0] = ((tnr.ll.x + tnr.ur.x)/2) * TILE_SIZE;
        pointInLineWithBridge[1] = odometer.getXyt()[1];
      }
    }
    else {
      bridgeOrientation = getBridgeOrient(tng.ll.x, tng.ur.x);
      if (bridgeOrientation == 'X') {
        //move only in y-direction
        pointInLineWithBridge[0] = odometer.getXyt()[0];
        pointInLineWithBridge[1] = ((tng.ll.y + tng.ur.y)/2) * TILE_SIZE;
      }
      else {
        //move only in x-direction
        pointInLineWithBridge[0] = ((tng.ll.x + tng.ur.x)/2) * TILE_SIZE;
        pointInLineWithBridge[1] = odometer.getXyt()[1];
      }
    }
    return pointInLineWithBridge;
  }
  
  /**
   * @return the point one tile away from the exit of the bridge. 
   */
  public static double[] getPointAfterBridge() {
    char bridgeOrientation;
    double[] pointAfterBridge = new double[2];
    if (isRedTeam()) {
      bridgeOrientation = getBridgeOrient(tnr.ll.x, tnr.ur.x);
      if (bridgeOrientation == 'X') {
        //move only in x-direction
        pointAfterBridge[0] = (tnr.ur.x - tnr.ll.x + 1) * TILE_SIZE;
        pointAfterBridge[1] = odometer.getXyt()[1];
      }
      else {
        //move only in y-direction
        pointAfterBridge[0] = odometer.getXyt()[0];
        pointAfterBridge[1] = (tnr.ur.y - tnr.ll.y + 1) * TILE_SIZE;
      }
    }
    else {
      bridgeOrientation = getBridgeOrient(tng.ll.x, tng.ur.x);
      if (bridgeOrientation == 'X') {
        //move only in x-direction
        pointAfterBridge[0] = (tng.ur.x - tng.ll.x + 1) * TILE_SIZE;
        pointAfterBridge[1] = odometer.getXyt()[1];
      }
      else {
        //move only in y-direction
        pointAfterBridge[0] = odometer.getXyt()[0];
        pointAfterBridge[1] = (tng.ur.y - tng.ll.y + 1) * TILE_SIZE;
      }
    }

    return pointAfterBridge;
  }
  
  /**
   * @return the point that the robot should travel to to begin the search procedure
   */
  public static double[] getPointForSearch() {
    double[] pointForSearch = new double[2];
    //TODO: Implement depending on search pattern decided upon
    return pointForSearch;

  }

}
