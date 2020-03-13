package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;

/**
 * The navigation class allows for the robot's movement along a given path.
 * 
 * @author Maxime Buteau
 * @author Rayan Wehbe
 * @author Kaan Gure
 */
public class Navigation {

  private static double[] currentPosition = new double[2];

  //private static double angleFromYAxis = 0;
  /**
   * Turns the robot by the shortest angle 
   * @param angle
   */
  public static void turnTo(double angle) { 
    double t = - odometer.getXyt()[2] + angle;
    if (t > 180) t -= 360;
    if (t < -180) t += 360;
    Utility.turnBy(t, ROTATE_SPEED);

    //currentOrientation = angle;
    Utility.sleepFor(100);
  }

  /**
   * Moves the robot to an input point on the grid.
   * 
   * @param x the X-coordinate of the point to go to
   * @param y the Y-coordinate of the point to go to
   */
  private static void travelTo(double x, double y) {

    double xDist = (x * TILE_SIZE - currentPosition[0]);
    double yDist = (y * TILE_SIZE -currentPosition[1]);

    // Euclidean distance to next point
    double distance = (Math.hypot(xDist,yDist));

    // Angle to next point from Y-axis
    angleFromYAxis = Math.toDegrees(Math.atan2(xDist, yDist));

    TEXT_LCD.clear();
    TEXT_LCD.drawString("" + angleFromYAxis, 0, 0);
    TEXT_LCD.drawString("" + distance, 0, 1);

    turnTo(angleFromYAxis);

    Utility.moveStraightForWithoutWaiting(distance, 2 * STRAIGHT_SPEED);
  }
  
  public static void moveStraightForNrTiles(int nr) {
    int counter = 0;
    
    while (counter < nr && !Main.obstacleIsDetected) {
      Utility.moveStraight(STRAIGHT_SPEED);
      Utility.sleepFor(BIG_SLEEP_TIME);
      Main.doLightCorrection();
      counter ++;
    }
  }
}
