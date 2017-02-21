package org.usfirst.frc.team1374.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
   
	//DRIVESUBSYSTEM
    public static final int left1   = 1;
    public static final int left2   = 2;
    public static final int right1  = 3;
    public static final int right2  = 4;    
    public static final int campan = 0;
    public static final int camtilt = 1;    
    public static final int shiftup = 0;
    public static final int shiftdown = 1;
    public static final int climber1 = 2;
    public static final int climber2 = 3;
    public static final int gear1 = 5;
    public static final int gear2 = 7;
    //END DRIVESUBSYSTEM

    //INTAKESUBSYSTEM
    public static final int intakePickup = 5;
    public static final int intakeDump = 6;
    //END INTAKESUBSYSTEM
    
    //CLIMBERSUBSYSTEM
    public static final int climber = 7;
    /* COMMENTS INDICATING WHICH BUTTON ON WHICH CONTROLLER DOES WHAT
     * 					--------DRIVER CONTROLLER--------
     * A:SHIFT INTO HIGH GEAR
     * B:SHIFT INTO LOW GEAR
     * X:ENABLE/DISABLE TOGGLE FOR GYRO PID
     * TBD: ENABLE/DISABLE TOGGLE FOR GEAR VISION TRACKING(NOT IMPLEMENTED)
     * RIGHT BUMPER: ENABLE/DISABLE TOGGLE FOR AUTONOMOUS RECORDING(NOT IMPLEMENTED)
     * 					-------OPERATOR CONTROLLER--------
     * A: RUN LOW GOAL DUMP(I DID NOT COMBINE A AND B YET BECAUSE I WANTED TO CONSULT THE DRIVERS)
     * B: RUN INTAKE
     * X: CLIMBER CLIMB UP
     * Y: CLIMBER CLIMB DOWN
    */
}