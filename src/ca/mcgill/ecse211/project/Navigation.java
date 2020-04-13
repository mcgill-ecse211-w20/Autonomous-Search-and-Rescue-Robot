package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;

/**
 * The navigation class allows the robot to move to a specific point on the grid once proper localization is complete.
 * Note that the methods in this class should be called repeatedly in the Main class to ensure that the robot follows
 * grid lines and that light correction can be performed
 * 
 * @author Maxime Buteau
 * @author Rayan Wehbe
 * @author Kaan Gure
 */
public class Navigation {
  /**
   * This angle helps with finding the shortest way to turn towards the next point to be traveled to
   */
  private static double angleFromYAxis = 0;

  /**
   * Turns the robot by the shortest angle to point to the next point it has to navigate to. It ensures that the robot 
   * does not turn 270 degrees in one direction when it could simply turn 90 degrees in the other direction.
   * @param angle
   */
  private static void turnTo(double angle) { 
    double t = - odometer.getXyt()[2] + angle;
    if (t > 180) t -= 360;
    if (t < -180) t += 360;
    Utility.turnBy(t, ROTATE_SPEED);

    //currentOrientation = angle;
    Utility.sleepFor(100);
  }

  /**
   * Moves the robot to an input point on the grid. This method should be called repeatedly from the Main method with
   * coordinates that allow the robot to follow grid lines as much as possible.
   * 
   * @param x the X-coordinate of the point to go to
   * @param y the Y-coordinate of the point to go to
   */
  public static void travelTo(double x, double y) {

    double xDist = (x - odometer.getXyt()[0]);
    double yDist = (y - odometer.getXyt()[1]);

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
}
