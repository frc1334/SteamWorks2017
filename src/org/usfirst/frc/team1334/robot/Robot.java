package org.usfirst.frc.team1334.robot;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team1334.robot.commands.ClimbCommand;
import org.usfirst.frc.team1334.robot.commands.DriveCommand;
import org.usfirst.frc.team1334.robot.commands.IntakeCommand;
import org.usfirst.frc.team1334.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.IntakeSubsystem;


public class Robot extends IterativeRobot
{
    

	//few different auto programs
	static int autoNumber = 11;
	//autoFile is a global constant that keeps you from recording into a different file than the one you play from
	static final String autoFile = new String("/home/lvuser/recordedAuto" + autoNumber + ".csv");
	public static DriveSubsystem driveSubsystem;
	public static ClimberSubsystem climberSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static IntakeCommand intakeCommand;
	public static ClimbCommand climbCommand;
    public static DriveCommand driveCommand;
    public static OI oi;
    public static double x;
	public static double x2;
    BTMacroPlay player = null;
    BTMacroRecord recorder = null;
    boolean isRecording = false;
    boolean togglerecord = false;
    Command autonomousCommand;
    double FOV;
    NetworkTable Pitable;
    
    @Override
    public void robotInit()
    {
        driveSubsystem  = new DriveSubsystem();
        oi = new OI();
        driveCommand = new DriveCommand();
        intakeSubsystem = new IntakeSubsystem();
        intakeCommand = new IntakeCommand();
        climberSubsystem = new ClimberSubsystem();
        climbCommand = new ClimbCommand();
        Pitable = NetworkTable.getTable("Pitable");
    }


    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {

    	
    	
    	//try to create a new player
    	//if there is a file, great - you have a new non-null object "player"
    	driveSubsystem.ahrs.reset();
    	try 
    	{
    		 player = new BTMacroPlay();
		} 
    	
		//if not, print out an error
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    	if (autonomousCommand != null) autonomousCommand.start();
    }


    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
		if (player != null)
		{
			player.play();
		}
        Scheduler.getInstance().run();
    }

    public void teleopInit()
    {
    	autoNumber +=1;
    	isRecording = false;
    	try {
			recorder = new BTMacroRecord();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("oopsie!");
		}
        if (autonomousCommand != null) autonomousCommand.cancel();
        driveCommand.start();
        climbCommand.start();
        intakeCommand.start();
    }

    @Override
    public void disabledInit()
    {

    }

    @Override
    public void teleopPeriodic()
    {
    	SmartDashboard.putNumber("PID", driveSubsystem.getPIDController().get());
    	SmartDashboard.putNumber("ERROR", driveSubsystem.getPIDController().getAvgError());
    	SmartDashboard.putData("DriveSubsystem", driveSubsystem.getPIDController());
    	FOV = 640;
    	x = Pitable.getNumber("X", FOV/2);
    	x2 =Pitable.getNumber("X2", FOV/2);
    	boolean afterrecord = false;
        Scheduler.getInstance().run();
        OI.Driver.toggleBank.put("RBC", OI.Driver.boolfalsetotruelistener(OI.DgetRB(),OI.Driver.toggleBank.get("RBP")));
        OI.Driver.toggleBank.put("RBP", OI.DgetRB());
        if (OI.Driver.toggleBank.get("RBC"))
		{
			isRecording = !isRecording;
		}  
		//if our record button has been pressed, lets start recording!
		if (isRecording)
		{
			afterrecord = true;
			try
			{
				//if we succesfully have made the recorder object, lets start recording stuff
				if(recorder != null)
				{
					recorder.record();
				}
			
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}else if(afterrecord && isRecording == false){
			if(recorder!=null){
				try {
					recorder.end();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

    }

    @Override
    public void testPeriodic()
    {
        LiveWindow.run();
    }
}