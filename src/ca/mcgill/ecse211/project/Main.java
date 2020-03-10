package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import java.util.ArrayList;
import lejos.hardware.Button;

/**
 * The main driver class for the lab.
 */
public class Main {
  
  public static boolean colorDetected = false;
  public static boolean ultrasonicLocalizationComplete;
  public static boolean lightCorrectionComplete;
  public static boolean leftSensorFirst;
  public static ArrayList<String> ColorsDetected = new ArrayList<String>();
  
//Store end goal of each segment to resume navigation
  public static double[] endGoal = new double[2];
  //Store the current segment we are on to resume navigation
  public static int currentSegment = 0;
  
  /**
   * Procedure for the initial localization
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
    odometer.setXyt(TILE_SIZE, TILE_SIZE, 0); //Reset the odometer after localization is done
  }
  
  public static void doLightCorrection() {
    lightCorrectionComplete = false;
    new Thread(new LightLocalizer()).start();
    while(lightCorrectionComplete == false);
  }

  /**
   * The main entry point.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    
    TEXT_LCD.clear();
    //new Thread(new ColorCalibration()).start();
    TEXT_LCD.drawString("Press the middle", 0, 0);
    TEXT_LCD.drawString("button to begin!", 0, 1);
    Button.waitForAnyPress();

    new Thread(odometer).start();
    doInitialLocalization();
    new Thread(new ColorDetection()).start();
    Navigation.navigate();
    
    while(true) {
      if(colorDetected) {
        Button.waitForAnyPress();
        TEXT_LCD.clear();
        colorDetected = false;
        Navigation.navigate();
      }
      Utility.sleepFor(POLL_SLEEP_TIME);
    }
    

    }

}
