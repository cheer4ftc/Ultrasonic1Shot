# Ultrasonic1Shot
Easy way to use the "1 shot" mode of the LEGO Ultrasonic Sensor.

Add the name of your java package at the top of each file and incorporate these into your normal codebase. Don't forget to add "OpModeTestUltrasonic" to your FtcOpModeRegister file to try out the test OpMode.

See OpModeTestUltrasonic for a simple example of how to use the code.

1. initialize the Ultrasonic thread(s) in init() (or before waitForStart() in a LinearOpMode).
2. interrupt the Ultrasonic thread(s) in stop() (or at the end of the runOpMode() method in a LinearOpMode).
3. call getUltrasonicLevel() in loop() as necessary (or in runOpMode() in a LinearOpMode).

Other methods are getUltrasonicLevelMin(), ...Median(), and ...Max() which return the min, median, and max of the previous 5 ultrasonic level readings. 5 is a static int BUFFER_SIZE that can be changed in the UltrasonicSensorThread code.

Thanks to Team25 for the Ultrasonic 1Shot code.
