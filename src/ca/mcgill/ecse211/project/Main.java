package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import java.util.ArrayList;
import lejos.hardware.Button;
import lejos.hardware.Sound;

/**
 * This class controls the execution of the different steps required for a robot to autonomously go rescue a stranded 
 * vehicle.
 */
public class Main {
  
  public static boolean colorDetected = false;
  public static boolean ultrasonicLocalizationComplete;
  public static boolean lightCorrectionComplete;
  public static boolean leftSensorFirst;
  public static ArrayList<String> ColorsDetected = new ArrayList<String>();
  
  //Store end goal of each segment to resume navigation
  public static double[] endGoal = new double[2];
  
  public static double[] pointInLineWithBridge = new double[2];
  public static double[] pointAfterBridge = new double[2];
  public static double[] pointForSearch = new double[2];
  
  /**
   * Procedure for the initial localization. The robot first performs ultrasonic localization to get a general idea of 
   * the direction it has to travel in, before performing light localization twice to accurately to locate to the (1,1)
   * point on the grid facing in the direction of the positive y axis.
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
    Mapping.mapOdo(corner);
    odometer.setXyt(TILE_SIZE, TILE_SIZE, 0); //Reset the odometer after localization is done
    for (int i = 0; i < 3; i ++) {
      Sound.beep();
    }
  }
  /*
   * Performs light correction when the robot is traveling straight
   */
  public static void doLightCorrection() {
    lightCorrectionComplete = false;
    new Thread(new LightLocalizer()).start();
    while(lightCorrectionComplete == false);
  }

  /**
   * The main entry point. The different steps of the procedure are called in order.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    
    //TODO: change the starting procedure once the wifi class is provided
    TEXT_LCD.clear();
    TEXT_LCD.drawString("Press the middle", 0, 0);
    TEXT_LCD.drawString("button to begin!", 0, 1);
    Button.waitForAnyPress();

    new Thread(odometer).start();
    doInitialLocalization();
    Navigation.travelTo(pointInLineWithBridge[0], pointInLineWithBridge[1]);
    Navigation.travelTo(pointAfterBridge[0], pointAfterBridge[1]);
    Navigation.travelTo(pointForSearch[0], pointForSearch[1]);
    Search.search();
    }

}
