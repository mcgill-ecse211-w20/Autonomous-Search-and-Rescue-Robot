package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

/**
 * This class checks if a color is detected by the sensor and identifies it.
 * @author Kaan Gure
 *
 */
public class ColorDetection implements Runnable{
  
  
  private static SampleProvider RGB = middleLightSensor.getRGBMode();
  private float[] middleLsData = new float[RGB.sampleSize()];
  
  /**
   * checks if a color is detected and identifies it.
   * 
   * @param RGB the input RGB values obtained from the light sensor
   */
  public void detectColor(float[] RGB) {
    
    if((RGB[0] < (YELLOW[0] + 1.2*YELLOW[1])) && (RGB[0] > (YELLOW[0] - 1.2*YELLOW[1]))
        && (RGB[1] < (YELLOW[2] + 1.2*YELLOW[3])) && (RGB[1] > (YELLOW[2] - 1.2*YELLOW[3]))
        && (RGB[2] < (YELLOW[4] + 1.2*YELLOW[5])) && (RGB[2] > (YELLOW[4] - 1.2*YELLOW[5]))) {
      TEXT_LCD.clear();
      TEXT_LCD.drawString("Object Detected", 0, 0);
      TEXT_LCD.drawString("Yellow", 0, 1);
      Main.ColorsDetected.add("Y");
      Main.colorDetected = true;
      Sound.beep();
      Sound.beep();
      
    } else if((RGB[0] < (GREEN[0] + 2*GREEN[1])) && (RGB[0] > (GREEN[0] - 2*GREEN[1]))
        && (RGB[1] < (GREEN[2] + 2*GREEN[3])) && (RGB[1] > (GREEN[2] - 2*GREEN[3]))
        && (RGB[2] < (GREEN[4] + 2*GREEN[5])) && (RGB[2] > (GREEN[4] - 2*GREEN[5]))) {
      TEXT_LCD.clear();
      TEXT_LCD.drawString("Object Detected", 0, 0);
      TEXT_LCD.drawString("Green", 0, 1);
      Main.ColorsDetected.add("G");
      Main.colorDetected = true;
      Sound.beep();
      Sound.beep();
      
    } else if((RGB[0] < (BLUE[0] + 3*BLUE[1])) && (RGB[0] > (BLUE[0] - 3*BLUE[1]))
        && (RGB[1] < (BLUE[2] + 3*BLUE[3])) && (RGB[1] > (BLUE[2] - 3*BLUE[3]))
        && (RGB[2] < (BLUE[4] + 3*BLUE[5])) && (RGB[2] > (BLUE[4] - 3*BLUE[5]))) {
      TEXT_LCD.clear();
      TEXT_LCD.drawString("Object Detected", 0, 0);
      TEXT_LCD.drawString("Blue", 0, 1);
      Main.ColorsDetected.add("B");
      Main.colorDetected = true;
      Sound.beep();
      Sound.beep();
      
    } else if((RGB[0] < (ORANGE[0] + 1.5*ORANGE[1])) && (RGB[0] > (ORANGE[0] - 1.5*ORANGE[1]))
        && (RGB[1] < (ORANGE[2] + 1.5*ORANGE[3])) && (RGB[1] > (ORANGE[2] - 1.5*ORANGE[3]))
        && (RGB[2] < (ORANGE[4] + 1.5*ORANGE[5])) && (RGB[2] > (ORANGE[4] - 1.5*ORANGE[5]))) {
      TEXT_LCD.clear();
      TEXT_LCD.drawString("Object Detected", 0, 0);
      TEXT_LCD.drawString("Orange", 0, 1);
      Main.ColorsDetected.add("O");
      Main.colorDetected = true;
      Sound.beep();
      Sound.beep();
      
    }
    
  }
  
  /**
   * Where the main logic of the thread runs.
   */
  public void run() {
    while(true) {
      RGB.fetchSample(middleLsData, 0);
      detectColor(middleLsData);
      Utility.sleepFor(COLOR_SLEEP_TIME);
      if(Main.colorDetected) {
        Button.waitForAnyPress();
      }
    }
  }
  
}
