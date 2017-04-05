package org.usfirst.frc.team1334.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.math.*;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;
import org.usfirst.frc.team1334.robot.RobotMap;

public class DriveSubsystem extends PIDSubsystem{

	public double kToleranceDegrees = 2.0;
	public static Talon left1  = new Talon(RobotMap.left1);
	public static Talon left2  = new Talon(RobotMap.left2);
	public static Talon right1 = new Talon(RobotMap.right1);
	public static Talon right2 = new Talon(RobotMap.right2);
	//public Servo camPan = new Servo(RobotMap.campan);
	//public Servo camTilt = new Servo(RobotMap.camtilt);
	public AnalogInput URF1 = new AnalogInput(0);
	public float minimalvoltage = 0.30f;//0.15
	public double post = 0;
	public double negt = 0;
	public DriveSubsystem()
	{
		super("Drive",0.03, 0.0, 0,0);
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
    public boolean isstill = false;
    public Ultrasonic US =  new Ultrasonic(0,1);
    public boolean gearIn = false;
    @Override
    protected void initDefaultCommand() {	
        left1.setSafetyEnabled(true);
        //left1.setVoltageRampRate(36);
        left2.setSafetyEnabled(true);
        //left2.setVoltageRampRate(36);
        right1.setSafetyEnabled(true);
        //right1.setVoltageRampRate(36);
        right2.setSafetyEnabled(true);
        //right2.setVoltageRampRate(36);
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
    
    
    public void cameraControl(){
    	//camPan.set(Math.abs(OI.OgetPan()));
    	//camTilt.set(Math.abs(OI.OgetTilt()));
    }
    
    public static void tankDrive(double left,double right)
    {
        left1.set(left);
        left2.set(left);
        right1.set(right);
        right2.set(right);
    }
    public void driveControlMode(){
    	//left1.setControlMode(0);
    	//left2.setControlMode(0);
    	//right1.setControlMode(0);
    	//right2.setControlMode(0);
    }
    public void arcadeDrive(double turn,double speed)
    {
    	//high gear speed
        if(highGear)tankDrive((-speed)+turn,(speed)+turn);
        //low gear speed
        else if(!highGear)tankDrive((-speed)+turn,(speed)+turn);
        /*System.out.println("angle " + angle);
        System.out.println("PID " + ahrs.pidGet());
        System.out.println("Error " + turnController.getError());
        System.out.println("AcceptableError " + kToleranceDegrees);
        System.out.println(rotateToAngleRate);
        System.out.println("Ontarget: "+ turnController.onTarget());*/
        if(speed == 0){
        	isstill = true;
        }else{
        	isstill = false;
        }
    }
    public void gearPiston(boolean out){
    	if(out){
    		gear1.set(true);
    		gear2.set(false);
    	}else{
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
    public Double error;
    public void VisionDrive(double X1,double X2){
    	double centerx = (X1+X2)/2;
    	System.out.println("centerx " + centerx);
    	
    	
    	error = centerx - 20 - (640/2);
    	
    	System.out.println("error" + error);
    	if(Math.abs(error)>25){
    	angle = (float)(angle + error*0.002);
    	}
    	
    }
    
    public double GyroDrive(double turn){
    	angle+=turn;
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
			if(isstill){
			//System.out.println("error" + this.getPIDController().getError());
			
			if(this.getPIDController().getError()>=1 || this.getPIDController().getError()<=-1){
				
				//System.out.println("offtarget");
				if(rotateToAngleRate<= minimalvoltage && rotateToAngleRate > 0){
					//System.out.println("positive");
					post +=1;
					rotateToAngleRate = minimalvoltage - ((1-this.getPIDController().getError())/65)+post/100;
				}else if(rotateToAngleRate>= -minimalvoltage && rotateToAngleRate <0){
					//System.out.println("negative");
					negt+=1;
					rotateToAngleRate = -minimalvoltage + ((1- this.getPIDController().getError())/65)-negt/100;
				}
				
			}else{
				post = 0;
				negt = 0;
			}
			//System.out.println("post "+ post);
			//System.out.println("negt" + negt);
			}
			//System.out.println(rotateToAngleRate);
	}
}