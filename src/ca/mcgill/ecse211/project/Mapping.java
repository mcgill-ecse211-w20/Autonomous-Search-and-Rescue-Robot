package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;


/**
 * The Mapping class converts raw map data to coordinates that are used by the robot after initial localization.
 * As the robot will localize in different directions based on its starting zone, its internal x and y-axis will be different with respect to the given raw map coordinates
 * so these coordinates must be converted to a form that respects the internal localizer x and y-axis.
 * 
 * For more information on this class and its logic, refer to the Group08_SOFTWARE_OVERVIEW_DOC_v4.0 section 9.4
 * 
 * @author Maxime Buteau
 * @author Kaan Gure
 *
 */
public class Mapping {
  
  /**
   * Determines the team that the robot is on, either red or green, by comparing the red team number provided by wifi
   * with the TEAM_NUMBER
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
   * Sets the odometer to the correct coordinates depending on the starting corner of the robot. The starting corner
   * value, between 0 and 3, is obtained via wifi. An accurate initial localization is assumed, so the center of the
   * wheelbase of the robot should be centered on the grid line intersection nearest to the starting corner. The robot
   * should also be pointing in the positive y-direction relative to its starting corner. The theoretical x, y, and
   * theta values are then passed to the odometer.
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
   * Finds the orientation of the bridge by taking the difference between the upper right x-coordinate and lower-left
   * x-coordinate of the bridge. If the difference is  2, the bridge is oriented in the x-direction. Otherwise, it is
   * oriented in the y-direction. 
   * 
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
   * Finds the point in line with the bridge. If the bridge is oriented in the x-direction, the robot will only move in 
   * the y-direction. Otherwise, it will only move in the x-direction. This ensures that the robot will be following a
   * gridline, allowing for light correction. Since the center of the bridge entrance is in the middle of a tile, the 
   * robot will also stop in the middle of the tile.
   * 
   * @return the point in line with the bridge
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
   * Finds the point on the first line perpendicular to the bridge's orientation after the bridge exit. Depending on the
   * bridge orientation, the robot should only move in the x or y-direction after having traveled to the point in line
   * with the bridge. Even though the center of the wheelbase will be in the middle of a tile, it will still be possible
   * to perform light corrections on the perpendicular grid lines.
   * 
   * TODO: Ensure no light corrections are performed inside the bridge, as there are no gridlines on the ground
   * 
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
   * This method has not been implemented yet, as it depends on the search pattern that is selected, either the 
   * s-pattern or the center-scan. Note that a new localization on grid line intersections might be necessary to reduce
   * inaccuracies that the bridge traversal might have caused
   *  
   * @return the point that the robot should travel to to begin the search procedure
   */
  public static double[] getPointForSearch() {
    double[] pointForSearch = new double[2];
    //TODO: Implement depending on search pattern decided upon
    return pointForSearch;

  }

}
