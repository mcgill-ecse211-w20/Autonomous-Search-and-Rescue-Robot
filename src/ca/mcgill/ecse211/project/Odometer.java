package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The odometer class keeps track of the robot's (x, y, theta) position. It should be noted that the x and y values
 * are recorded in centimeters, while the parameters received via wifi are given in grid coordinates. A
 * conversion using the TILE_SIZE constant is thus required.
 * 
 * @author Rodrigo Silva
 * @author Dirk Dubois
 * @author Derek Yu
 * @author Karim El-Baba
 * @author Michael Smith
 * @author Younes Boubekeur
 * @author Maxime Buteau
 * @author Rayan Wehbe
 */

public class Odometer implements Runnable {
  
  /**
   * The x-axis position in cm.
   */
  private volatile double x;
  
  /**
   * The y-axis position in cm.
   */
  private volatile double y; // y-axis position
  
  /**
   * The orientation in degrees.
   */
  private volatile double theta; // Head angle
  
  // Thread control tools
  /**
   * Fair lock for concurrent writing.
   */
  private static Lock lock = new ReentrantLock(true);
  
  /**
   * Indicates if a thread is trying to reset any position parameters.
   */
  private volatile boolean isResetting = false;

  /**
   * Lets other threads know that a reset operation is over.
   */
  private Condition doneResetting = lock.newCondition();

  private static Odometer odo; // Returned as singleton

  // Motor-related variables
  private static int leftMotorTachoCount = 0;
  private static int rightMotorTachoCount = 0;
  private static int prevLeftMotorTachoCount = 0; // Previous left tachometer value.
  private static int prevRightMotorTachoCount = 0; // Previous right tachometer value.

  /**
   * The odometer update period in ms.
   */
  private static final long ODOMETER_PERIOD = 25;

  
  /**
   * This is the default constructor of this class. It initiates all motors and variables once. It
   * cannot be accessed externally.
   */
  private Odometer() {
    setXyt(TILE_SIZE, TILE_SIZE, 0);
  }

  /**
   * Returns the Odometer Object. Use this method to obtain an instance of Odometer.
   * 
   * @return the Odometer Object
   */
  public static synchronized Odometer getOdometer() {
    if (odo == null) {
      odo = new Odometer();
    }
    
    return odo;
  }

  /**
   * This method is where the logic for the odometer runs. Every time the wheels turn, by a given increment, the
   * position is updated.
   */
  public void run() {
    long updateStart;
    long updateDuration;

    while (true) {
      updateStart = System.currentTimeMillis();
      
      double leftMotorForwardMvmt;
      double rightMotorForwardMvmt;
      double deltaDistance;
      double dTheta;
      double dX;
      double dY;

      leftMotorTachoCount = leftMotor.getTachoCount();
      rightMotorTachoCount = rightMotor.getTachoCount();

      
      // Motor displacement = Angular position (deg) * Wheel radius (cm) * Degrees to rad conversion;
      leftMotorForwardMvmt = (leftMotorTachoCount - prevLeftMotorTachoCount) * WHEEL_RAD * DEG_TO_RAD;
      rightMotorForwardMvmt = (rightMotorTachoCount - prevRightMotorTachoCount) * WHEEL_RAD * DEG_TO_RAD;
      
      prevLeftMotorTachoCount = leftMotorTachoCount;
      prevRightMotorTachoCount = rightMotorTachoCount;
      
      deltaDistance = 0.5 * (leftMotorForwardMvmt + rightMotorForwardMvmt);
      
      // theta = 0.5 * ( left motor displacement - right motor displacement ) (cm) / 
      // ( 0.5 * base width (cm) * Degrees to rad conversion )
      dTheta = (leftMotorForwardMvmt - rightMotorForwardMvmt) / BASE_WIDTH / DEG_TO_RAD;
      
      dX = deltaDistance * Math.sin(Math.toRadians(theta));
      dY = deltaDistance * Math.cos(Math.toRadians(theta));
      
      // Update odometer values with new calculated values using update()
      update(dX, dY, dTheta);
      
      // this ensures that the odometer only runs once every period.
      updateDuration = System.currentTimeMillis() - updateStart;
      if (updateDuration < ODOMETER_PERIOD) {
        Utility.sleepFor(ODOMETER_PERIOD - updateDuration);
      }
    }
  }
  
  /**
   * Returns the Odometer data.
   * 
   * <p>Writes the current position and orientation of the robot onto the odoData array.
   * {@code odoData[0] = x, odoData[1] = y; odoData[2] = theta;}
   * 
   * @return the odometer data.
   */
  public double[] getXyt() {
    double[] position = new double[3];
    lock.lock();
    try {
      while (isResetting) { // If a reset operation is being executed, wait until it is over.
        doneResetting.await(); // Using await() is lighter on the CPU than simple busy wait.
      }

      position[0] = x;
      position[1] = y;
      position[2] = theta;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return position;
  }

  /**
   * Adds dx, dy and dtheta to the current values of x, y and theta, respectively. Useful for
   * odometry.
   * 
   * @param dx the change in x
   * @param dy the change in y
   * @param dtheta the change in theta
   */
  public void update(double dx, double dy, double dtheta) {
    lock.lock();
    isResetting = true;
    try {
      x += dx;
      y += dy;
      theta = (theta + (360 + dtheta) % 360) % 360; // keeps the updates within 360 degrees
      isResetting = false;
      doneResetting.signalAll(); // Let the other threads know we are done resetting
    } finally {
      lock.unlock();
    }

  }

  /**
   * Overrides the values of x, y and theta. Use for odometry correction.
   * 
   * @param x the value of x
   * @param y the value of y
   * @param theta the value of theta in degrees
   */
  public void setXyt(double x, double y, double theta) {
    lock.lock();
    isResetting = true;
    try {
      this.x = x;
      this.y = y;
      this.theta = theta;
      isResetting = false;
      doneResetting.signalAll();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Overwrites x. Use for odometry correction.
   * 
   * @param x the value of x
   */
  public void setX(double x) {
    lock.lock();
    isResetting = true;
    try {
      this.x = x;
      isResetting = false;
      doneResetting.signalAll();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Overwrites y. Use for odometry correction.
   * 
   * @param y the value of y
   */
  public void setY(double y) {
    lock.lock();
    isResetting = true;
    try {
      this.y = y;
      isResetting = false;
      doneResetting.signalAll();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Overwrites theta. Use for odometry correction.
   * 
   * @param theta the value of theta
   */
  public void setTheta(double theta) {
    lock.lock();
    isResetting = true;
    try {
      this.theta = theta;
      isResetting = false;
      doneResetting.signalAll();
    } finally {
      lock.unlock();
    }
  }

}

