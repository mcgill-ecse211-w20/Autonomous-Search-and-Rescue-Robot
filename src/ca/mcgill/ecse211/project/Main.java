package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;

/**
 * This class controls the execution of the different steps required for a robot to autonomously go rescue a stranded 
 * vehicle. The robot receives parameters via Wifi that map out its starting corner, starting region, covered bridge,
 * the island, and search zone on the island. 
 * 
 * The robot must first localize using the walls surrounding the whole map and grid lines on the floor. It must then 
 * navigate to the covered bridge and cross it without ever stepping outside of its starting region. 
 * 
 * The next step is to navigate to its search zone and locate the stranded vehicle in it. It must do so without hitting
 * any of the obstacles that can be present in the search zone at random locations. 
 * 
 * Once the stranded vehicle is located, the robot must bring the stranded vehicle back to its starting region. Once
 * again, no obstacles can be hit and the bridge must be successfully crossed.
 * 
 * At no point in time can the robot step outside of the defined zones for its starting region, covered bridge, or the
 * island.
 * 
 * For more information on this class and its logic, refer to the Group08_SOFTWARE_OVERVIEW_DOC_v4.0 section 9.1
 * 
 * @author Maxime Buteau
 * @author Kaan Gure
 */
public class Main {
 
  public static boolean ultrasonicLocalizationComplete;
  public static boolean lightCorrectionComplete;
  public static boolean leftSensorFirst;
  
  /**
   * The end goal of each navigation segment is stored to allow for the navigation procedure to be resumed after
   * interruption from obstacle avoidance or light correction
   */
  public static double[] endGoal = new double[2];
  
  /**
   * Procedure for the initial localization. The robot first performs ultrasonic localization to get a general idea of 
   * the direction it has to travel in, before performing light localization twice (once on the first y-axis grid line
   * and once on the first x-axis grid line) to accurately locate to the (1,1)
   * point on the grid facing in the direction of the positive y axis. Note that this y axis will change depending on the
   * starting corner of the robot.
   */
  public static void doInitialLocalization() {
    ultrasonicLocalizationComplete = false;
    new Thread(new UltrasonicLocalizer()).start();
    while(ultrasonicLocalizationComplete == false);
    Utility.moveStraight(STRAIGHT_SPEED);
    doLightCorrection();
    if (leftSensorFirst) {
      Utility.turnBy(LEFT_SENSOR_FIRST_LOCALIZATION_ANGLE, ROTATE_SPEED);
    }
    else {
      Utility.turnBy(RIGHT_SENSOR_FIRST_LOCALIZATION_ANGLE, ROTATE_SPEED);
    }
    Utility.moveStraight(STRAIGHT_SPEED);
    doLightCorrection();
    Mapping.mapOdo();//Reset odometer to appropriate position after localization is done
    for (int i = 0; i < 3; i ++) {
      Sound.beep();
    }
  }
  /*
   * Performs light correction when the robot is traveling straight. The endGoal of the current navigation segment
   * should be stored, so that navigation can be resumed after this correction is completed
   */
  public static void doLightCorrection() {
    lightCorrectionComplete = false;
    new Thread(new LightLocalizer()).start();
    while(lightCorrectionComplete == false);
  }

  /**
   * The main entry point. The different steps of the procedure are called in order. The main concern is minimizing 
   * the use of threads whenever possible, to account for the limited processing power of the EV3 brick.
   * 
   * TODO: The segments should be separated to perform light corrections. The frequency at which this should be done needs to be
   * tested
   * 
   * TODO: Determine the best way to return to the starting zone once the searching process is complete. Note that 
   * an obstacle could still be encountered during that process.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    TEXT_LCD.clear();
    TEXT_LCD.drawString("Press the middle", 0, 0);
    TEXT_LCD.drawString("button to begin!", 0, 1);
    Button.waitForAnyPress();

    new Thread(odometer).start();
    doInitialLocalization();
    Navigation.travelTo(Mapping.getPointInLineWithBridge()[0], Mapping.getPointInLineWithBridge()[1]);
    Navigation.travelTo(Mapping.getPointAfterBridge()[0], Mapping.getPointAfterBridge()[1]);
    Navigation.travelTo(Mapping.getPointForSearch()[0], Mapping.getPointForSearch()[1]);
    Search.search(); //After this completes, the robot should be carrying the cart inside itself. 
    }

}
