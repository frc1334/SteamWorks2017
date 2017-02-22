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
	boolean EncoderRecord = false;
    public DriveCommand(){
    	super("DriveCommand");
        requires(Robot.driveSubsystem);
       
    }

    @Override
    protected void initialize() {
		DriveSubsystem.left1.setControlMode(0);
        DriveSubsystem.left2.setControlMode(0);
        DriveSubsystem.right1.setControlMode(0);
        DriveSubsystem.right2.setControlMode(0);
        Robot.driveSubsystem.ResetGyroAngle();
    }

    @Override
    protected void execute() {
    	Robot.driveSubsystem.CompressorControl();
    	Robot.driveSubsystem.gearPiston(OI.OgetRB(), OI.OgetLB());
    	OI.Driver.toggleBank.put("XC", OI.Driver.boolfalsetotruelistener(OI.DgetX(),OI.Driver.toggleBank.get("XP")));
        OI.Driver.toggleBank.put("XP", OI.DgetX());
        if(OI.Driver.toggleBank.get("XC")){
        	PID = !PID;
        }
        OI.Driver.toggleBank.put("YC", OI.Driver.boolfalsetotruelistener(OI.DgetY(), OI.Driver.toggleBank.get("XP")));
        OI.Driver.toggleBank.put("YP", OI.DgetY());
        if(OI.Driver.toggleBank.get("YC")){
        	EncoderRecord= !EncoderRecord;
        }
    	if(PID ){
    		if(GyroInit){
    			Robot.driveSubsystem.GyroPIDinit();
    			GyroInit = false;
    		}
    		DriveInit = true;
    		EncoderInit = true;
    		if(OI.DgetLB()){
    		Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.VisionDrive(Robot.x, Robot.x2));
    		Robot.driveSubsystem.speed = OI.DgetSpeed();
    		Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);
    		}else{
    		Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(OI.DgetTurn()));
    		Robot.driveSubsystem.speed = OI.DgetSpeed();
    		Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);
    		}
    		DriveSubsystem.setGearShift();
    	}else if(EncoderRecord){
    		if(EncoderInit){
    		Robot.driveSubsystem.encoderControlMode();
    		EncoderInit = false;
    		}
    		DriveInit = true;
    		GyroInit = true;
    		Robot.driveSubsystem.PIDdrive(OI.DgetTurn(),OI.DgetSpeed());
    		DriveSubsystem.shiftGear(OI.DgetB(), OI.DgetA());
    		DriveSubsystem.setGearShift();
	    }else{
	    	if(DriveInit){
	    	Robot.driveSubsystem.driveControlMode();
	    	}
	    	GyroInit = true;
	    	EncoderInit = true;
	    	Robot.driveSubsystem.speed = OI.DgetSpeed();
	    	Robot.driveSubsystem.arcadeDrive(OI.DgetTurn(), OI.DgetSpeed());
	    	DriveSubsystem.shiftGear(OI.DgetB(),OI.DgetA());
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
