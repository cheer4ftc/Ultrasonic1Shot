// add package here

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbLegacyModule;
import java.util.Arrays;

/**
 * Created by cheer4ftc on 8/2/2016.
 */

public class UltrasonicSensorThread extends Thread implements Runnable {

  static int BUFFER_SIZE=5;                     // use odd number
  static int DELAY_BEFORE_FIRST_READ=250;       // suggested initial delay before read
  static int DELAY_BEFORE_SUBSEQUENT_READS=60;  // suggested delay before subsequent reads > 50

  Team25UltrasonicSensor t25us;
  double ultrasonicLevel;
  double[] usLevelBuffer;

  UltrasonicSensorThread(ModernRoboticsUsbLegacyModule legacyModule, int port) {
    t25us = new Team25UltrasonicSensor(legacyModule, port);
  }

  public void run() {
    int delayBeforeReading = DELAY_BEFORE_FIRST_READ;
    boolean waitedOK;

    ultrasonicLevel = -1; // negative for invalid data

    int bufferPointer=0;
    usLevelBuffer = new double[BUFFER_SIZE];
    for (int i=0; i<BUFFER_SIZE; i++) {
      usLevelBuffer[i]=-1;
    }

    while (!Thread.currentThread().isInterrupted()) {
      // do 1 shot ping of sensor
      t25us.doPing();

      // wait for data to be valid
      waitedOK=false;
      try { Thread.sleep(delayBeforeReading); waitedOK=true;}
      catch (InterruptedException ex)
      { Thread.currentThread().interrupt(); }

      // read value
      if (waitedOK) {
        ultrasonicLevel = t25us.getUltrasonicLevel();
        usLevelBuffer[bufferPointer]=ultrasonicLevel;
        bufferPointer = (bufferPointer+1)%BUFFER_SIZE;
      }

      // wait
      try { Thread.sleep(delayBeforeReading);}
      catch (InterruptedException ex)
      { Thread.currentThread().interrupt(); }

      delayBeforeReading=DELAY_BEFORE_SUBSEQUENT_READS;
    }
  }

  public double getUltrasonicLevel() {
    return ultrasonicLevel;
  }

  public double getUltrasonicLevelMedian() {
    double[] sortedUsLevelBuffer= new double[BUFFER_SIZE];

    for (int i=0; i<BUFFER_SIZE; i++) {
      sortedUsLevelBuffer[i]=usLevelBuffer[i];
    }
    Arrays.sort(sortedUsLevelBuffer);
    return sortedUsLevelBuffer[(BUFFER_SIZE-1)/2];
  }

  public double getUltrasonicLevelMin() {
    double returnLevel=255+1;
    for (int i=0; i<BUFFER_SIZE; i++) {
      if (usLevelBuffer[i]<returnLevel) {
        returnLevel = usLevelBuffer[i];
      }
    }
    return returnLevel;
  }

  public double getUltrasonicLevelMax() {
    double returnLevel=0-1;
    for (int i=0; i<BUFFER_SIZE; i++) {
      if (usLevelBuffer[i]>returnLevel) {
        returnLevel = usLevelBuffer[i];
      }
    }
    return returnLevel;
  }
}
