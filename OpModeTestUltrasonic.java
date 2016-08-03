// add package here

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbLegacyModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by cheer4ftc on 8/2/2016.
 */

public class OpModeTestUltrasonic extends OpMode {

  UltrasonicSensorThread ultrasonicThreadA, ultrasonicThreadB;

  @Override
  public void init() {
    ModernRoboticsUsbLegacyModule legacyModule = (ModernRoboticsUsbLegacyModule) hardwareMap.legacyModule.get("legacy");

    int ultrasonicLegacyPort = 4; // legacy module port number for the ultrasonic sensor
    ultrasonicThreadA = new UltrasonicSensorThread(legacyModule, ultrasonicLegacyPort);
    ultrasonicThreadA.start();

    ultrasonicLegacyPort = 5; // legacy module port number for the ultrasonic sensor
    ultrasonicThreadB = new UltrasonicSensorThread(legacyModule, ultrasonicLegacyPort);
    ultrasonicThreadB.start();
  }

  @Override
  public void loop() {
	// can call getUltrasonicLevel() or add Min, Median, or Max to method name (e.g., getUltrasonicLevelMedian() )
    telemetry.addData("ultrasonic reading A: ", ultrasonicThreadA.getUltrasonicLevel());
    telemetry.addData("ultrasonic reading B: ", ultrasonicThreadB.getUltrasonicLevel());
  }

  @Override
  public void stop() {
    ultrasonicThreadA.interrupt(); // make sure to interrupt them in stop() or at the end of a LinearOpMode's runOpMode method!
    ultrasonicThreadB.interrupt();
  }

}
