package org.usfirst.frc.team1334.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

    //DRIVESUBSYSTEM
    public static final int LEFT_1 = 1;
    public static final int LEFT_2 = 2;
    public static final int RIGHT_1 = 3;
    public static final int RIGHT_2 = 4;
    public static final int CAMPAN = 0;
    public static final int CAMTILT = 1;
    public static final int SHIFTUP = 0;
    public static final int SHIFTDOWN = 1;
    public static final int CLIMBER_1 = 2;
    public static final int CLIMBER_2 = 3;
    public static final int GEAR_1 = 5;
    public static final int GEAR_2 = 7;
    public static final int EJECT_1 = 4;
    public static final int EJECT_2 = 6;
    //END DRIVESUBSYSTEM

    //INTAKESUBSYSTEM
    public static final int INTAKE_PICKUP = 5;
    public static final int INTAKE_DUMP = 6;
    //END INTAKESUBSYSTEM

    //CLIMBERSUBSYSTEM
    public static final int climber = 7;
    /* COMMENTS INDICATING WHICH BUTTON ON WHICH CONTROLLER DOES WHAT
     * 					--------DRIVER CONTROLLER--------
     * A:SHIFT INTO HIGH GEAR
     * B:SHIFT INTO LOW GEAR
     * X:ENABLE/DISABLE TOGGLE FOR GYRO PID
     * LEFT BUMPER: ENABLE/DISABLE TOGGLE FOR GEAR VISION TRACKING
     * RIGHT BUMPER: ENABLE/DISABLE TOGGLE FOR AUTONOMOUS RECORDING
     * RIGHT STICK CLICK: ULTRASONIC RANGE FINDER REQUIRED DISTANCE RECORDING
     * 					-------OPERATOR CONTROLLER--------
     * A: RUN LOW GOAL DUMP(I DID NOT COMBINE A AND B YET BECAUSE I WANTED TO CONSULT THE DRIVERS)
     * B: RUN INTAKE
     * X: CLIMBER CLIMB UP
     * Y: CLIMBER CLIMB DOWN
     * RIGHT BUMPER: TOGGLE GEAR PISTONS
     * LEFT BUMPER: TOGGLE CLIMBER PISTON
     * LEFT JOYSTICK X AXIS: DRIVER CAMERA PAN
     * LEFT JOYSTICK Y AXIS: DRIVER CAMERA TILT
    */
}
