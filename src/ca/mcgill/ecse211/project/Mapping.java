package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;



public class Mapping {
  
  public static char bridgeOrientation; //Either X or Y
  
  public static double[] bridgeExit = new double[2];
  
  public void mapOdo(int corner) {
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
