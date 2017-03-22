package org.usfirst.frc.team1334.robot.commands;


import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {
	boolean GyroInit;
	boolean EncoderInit;
	boolean DriveInit;
	boolean PID = false;
	double Start,End;
	double USDistance = 20;
	double RequiredDistance = 2000;
    public DriveCommand(){
    	super("DriveCommand");
        requires(Robot.driveSubsystem);
       
    }

    @Override
    protected void initialize() {
		/*DriveSubsystem.left1.setControlMode(0);
        DriveSubsystem.left2.setControlMode(0);
        DriveSubsystem.right1.setControlMode(0);
        DriveSubsystem.right2.setControlMode(0);*/
        Robot.driveSubsystem.ResetGyroAngle();
        Start = System.currentTimeMillis();
        Robot.driveSubsystem.CompressorControl();
    }
    
    
    @Override
    protected void execute() {
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
    		Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(OI.DgetTurn()));
    		Robot.driveSubsystem.speed = OI.DgetSpeed();
    		Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);
    		
    		DriveSubsystem.setGearShift();
    	}else{//Drive Settings Init
	    	if(DriveInit){
	    	Robot.driveSubsystem.driveControlMode();
	    	}
	    	GyroInit = true;
	    	EncoderInit = true;
	    	// TESTING
	    	if(Math.abs(error)>10 && OI.DRangeFinderRecord()){
	    		Robot.driveSubsystem.arcadeDrive(OI.DgetTurn(), error * 0.001);
	    	}else if(USDistance < 100 && OI.DgetSpeed() > Robot.driveSubsystem.minimalvoltage){
	    		Robot.driveSubsystem.arcadeDrive(OI.DgetTurn(), Robot.driveSubsystem.minimalvoltage);
	    	}else{
	    	// TESTING
	    	Robot.driveSubsystem.arcadeDrive(OI.DgetTurn(), OI.DgetSpeed());
	    	}
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
