package org.usfirst.frc.team1334.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.math.*;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;
import org.usfirst.frc.team1334.robot.RobotMap;

public class DriveSubsystem extends PIDSubsystem{

	public double kToleranceDegrees = 2.0;
	public static CANTalon left1  = new CANTalon(RobotMap.left1);
	public static CANTalon left2  = new CANTalon(RobotMap.left2);
	public static CANTalon right1 = new CANTalon(RobotMap.right1);
	public static CANTalon right2 = new CANTalon(RobotMap.right2);
	public Servo camPan = new Servo(RobotMap.campan);
	public Servo camTilt = new Servo(RobotMap.camtilt);
	public DriveSubsystem()
	{
		super("Drive",0.02, 0.0, 0,0);
        getPIDController().setInputRange(-180.0f,  180.0f);
        getPIDController().setOutputRange(-1.0, 1.0);
        getPIDController().setAbsoluteTolerance(kToleranceDegrees);
        getPIDController().setContinuous(true);
	}
	public double speed;
    public AHRS ahrs;
    public double rotateToAngleRate;
    public static float angle = 0;
    static int b;
    public static Solenoid shiftup = new Solenoid(RobotMap.shiftup);
    public static Solenoid shiftdown = new Solenoid(RobotMap.shiftdown);
    public Solenoid gear1 = new Solenoid(RobotMap.gear1);
    public Solenoid gear2 = new Solenoid(RobotMap.gear2);
    public static boolean highGear = false;
    public Compressor C = new Compressor(0);
    public static final int cameraFOVdegrees = 63;

    @Override
    protected void initDefaultCommand() {	
        left1.setSafetyEnabled(true);
        left2.setSafetyEnabled(true);
        right1.setSafetyEnabled(true);
        right2.setSafetyEnabled(true);
        left1.setExpiration(0.1);
        left2.setExpiration(0.1);
        right2.setExpiration(0.1);
        right1.setExpiration(0.1);
        try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
        	ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

        ResetGyroAngle();
        getPIDController().enable();
        LiveWindow.addActuator("DriveSubsystem", "RotateController", getPIDController());
        
    }
    public void CompressorControl(){
    	C.setClosedLoopControl(true);    
    }
    public static void tankDrive(double left,double right)
    {
        left1.set(left);
        left2.set(left);
        right1.set(right);
        right2.set(right);
    }
    public void encoderControlMode(){
    	left1.changeControlMode(TalonControlMode.Speed);
    	left1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	left1.setInverted(false);
    	left1.setPID(0.03,0.0,0.0);
    	left1.setIZone(100);
    	left1.setVoltageRampRate(25);
    	left1.set(0);
    	left2.changeControlMode(TalonControlMode.Follower);
    	left2.set(1);
    	right1.changeControlMode(TalonControlMode.Speed);
    	right1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	right1.setInverted(false);
    	right1.setPID(0.03, 0.0, 0.0);
    	right1.setIZone(100);
    	right1.setVoltageRampRate(25);
    	right1.set(0);
    	right2.changeControlMode(TalonControlMode.Follower);
    	right2.set(3);
    }
    public void driveControlMode(){
    	left1.setControlMode(0);
    	left2.setControlMode(0);
    	right1.setControlMode(0);
    	right2.setControlMode(0);
    }
    public void arcadeDrive(double turn,double speed)
    {
    	//high gear speed
        if(highGear)tankDrive((-speed)-turn,(speed)-turn);
        //low gear speed
        else if(!highGear)tankDrive((-speed)-turn,(speed)-turn);
        /*System.out.println("angle " + angle);
        System.out.println("PID " + ahrs.pidGet());
        System.out.println("Error " + turnController.getError());
        System.out.println("AcceptableError " + kToleranceDegrees);
        System.out.println(rotateToAngleRate);
        System.out.println("Ontarget: "+ turnController.onTarget());*/
        
    }
    public void gearPiston(boolean out, boolean in){
    	if(out){
    		gear1.set(true);
    		gear2.set(false);
    	}else if(in){
    		gear1.set(false);
    		gear2.set(true);
    	}
    }
    public static void shiftGear(boolean up, boolean down)
    {
        if(up){
            highGear = true;
        }else if (down){
        	highGear = false;
        }
        
    }
    public static void setGearShift(){
    	shiftup.set(!highGear);
        shiftdown.set(highGear);
    }
    public void ResetGyroAngle(){
    	ahrs.reset();
    	angle = 0;
    }
    public double VisionDrive(double X1,double X2){
    	double centerx = (X1+X2)/2;
    	double error = centerx - (640/2);
    	double Tangle = angle + error/40;
    	b = (int)Tangle/180;
    	Tangle = (float) (Tangle * Math.pow(-1, b));
    	return Tangle;
    }
    
    public double GyroDrive(double turn){
    	angle+=(3*Math.pow(turn, 3));
    	b = (int)angle/180;
    	angle = (float) (angle * Math.pow(-1, b));
    	return angle;
    	/* code for proper setpoint and controller stopping within error range
    	if(getPIDController().getSetpoint()!=angle){
    		getPIDController().setSetpoint(angle);
    	}
    	if(Math.abs(getPIDController().getError()) <= 1){
    	DriveSubsystem.arcadeDrive(0, speed);
    	}else{
    	DriveSubsystem.arcadeDrive(rotateToAngleRate, speed);
    	}
    	*/
    }
    public void GyroPIDinit(){
    	highGear = false;
    	ResetGyroAngle();    	
    }
    public void PIDdrive(double turn,double speed){
    	System.out.println(left1.getEncVelocity());
    	System.out.println(right1.getEncVelocity());
    	left1.set(4000*(-speed-(turn)));
    	right1.set(4000*(speed-(turn)));
    }
	@Override
	protected double returnPIDInput() {
		return ahrs.pidGet();
	}
	@Override
	public void usePIDOutput(double output) {
			if(output >=0.8 ){
				rotateToAngleRate = 0.8;
			}else if(output<=-0.8){
				rotateToAngleRate = -0.8;
			}else{
				rotateToAngleRate = output;
			}	
	}
}