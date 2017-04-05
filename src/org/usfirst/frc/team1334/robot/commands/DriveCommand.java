package org.usfirst.frc.team1334.robot.commands;


import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {
	boolean GyroInit;
	boolean EncoderInit;
	boolean DriveInit;
	boolean PID = false;
	double Start,End;
	public double USDistance = 20;
	double RequiredDistance = 3000;
    public DriveCommand(){
    	super("DriveCommand");
        requires(Robot.driveSubsystem);
       
    }

    @Override
    protected void initialize() {
		//DriveSubsystem.left1.setControlMode(0);
        //DriveSubsystem.left2.setControlMode(0);
        //DriveSubsystem.right1.setControlMode(0);
        //DriveSubsystem.right2.setControlMode(0);
        Robot.driveSubsystem.ResetGyroAngle();
        Start = System.currentTimeMillis();
        
    }
    
    
    @Override
    protected void execute() {
    	Robot.driveSubsystem.cameraControl();
    	End = System.currentTimeMillis();
    	if(End-Start >100){
    	Robot.driveSubsystem.US.ping();
    	Start = End;
    	}
    	if(Robot.driveSubsystem.US.getRangeMM() >=20 && Robot.driveSubsystem.US.getRangeMM() <=4000){
    	USDistance = Robot.driveSubsystem.US.getRangeMM();
    	}
    	System.out.println(USDistance);
    	double error = USDistance - RequiredDistance;
    	double robotUSOut = error * 0.001;
    	//Gear Toggle
    	OI.Operator.toggleBank.put("RBC", OI.Operator.boolfalsetotruelistener(OI.OGearPistonToggle(),OI.Operator.toggleBank.get("RBP")));
        OI.Operator.toggleBank.put("RBP", OI.OGearPistonToggle());
        if(OI.Operator.toggleBank.get("RBC")){
        	Robot.driveSubsystem.gearIn = !Robot.driveSubsystem.gearIn;
        }
    	Robot.driveSubsystem.gearPiston(Robot.driveSubsystem.gearIn);
    	//Gyro PID Toggle
    	OI.Driver.toggleBank.put("XC", OI.Driver.boolfalsetotruelistener(OI.DEnableGyro(),OI.Driver.toggleBank.get("XP")));
        OI.Driver.toggleBank.put("XP", OI.DEnableGyro());
        if(OI.Driver.toggleBank.get("XC")){
        	PID = !PID;
        }
    	if(PID ){
    		if(GyroInit){//
    			Robot.driveSubsystem.GyroPIDinit();
    			GyroInit = false;
    		}
    		DriveInit = true;
    		EncoderInit = true;
    		//Vision Tracking Toggle
    		if(OI.DToggleVision()){
    		Robot.driveSubsystem.VisionDrive(Robot.x, Robot.x2);	
    		}
    		/*if(Math.abs(error)>20 && OI.DRangeFinderRecord()){
	    		if(robotUSOut < Robot.driveSubsystem.minimalvoltage && robotUSOut > 0){
	    		Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage;
	    		}else if (robotUSOut > -Robot.driveSubsystem.minimalvoltage && robotUSOut < 0){
	    		Robot.driveSubsystem.speed = -Robot.driveSubsystem.minimalvoltage;
	    		}else{
	    		Robot.driveSubsystem.speed = robotUSOut;
	    		}
	    	}else{
	    		Robot.driveSubsystem.speed = OI.DgetSpeed();
	    	}*/
    		Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(OI.DgetTurn()));
    		if(USDistance < 1000 && OI.DgetSpeed() > 0){
    			Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage;
    		}else{
    		Robot.driveSubsystem.speed = OI.DgetSpeed();
    		}
    		Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);
    		
    		DriveSubsystem.setGearShift();
    	}else{//Drive Settings Init
	    	if(DriveInit){
	    	Robot.driveSubsystem.driveControlMode();
	    	}
	    	GyroInit = true;
	    	EncoderInit = true;
	    	// TESTING
	    	
	    	System.out.println("USout" + robotUSOut);
	    	
	    	// TESTING
	    	Robot.driveSubsystem.arcadeDrive(OI.DgetTurn(), OI.DgetSpeed());
	    	
	    	Robot.driveSubsystem.speed = OI.DgetSpeed();
	    	DriveSubsystem.shiftGear(OI.DHighGear(),OI.DLowGear());
	    	DriveSubsystem.setGearShift();
	    }
    	
    }

    @Override
    protected boolean isFinished() {
        
    	return false;
    }

    @Override
	public void end() {
    	
    }

    @Override
    protected void interrupted() {

    }
}
