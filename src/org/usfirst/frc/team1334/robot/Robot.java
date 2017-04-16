package org.usfirst.frc.team1334.robot;


import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1334.robot.commands.TestCommand;
import org.usfirst.frc.team1334.robot.commands.autonomous.groups.*;
import org.usfirst.frc.team1334.robot.commands.teleop.ClimbCommand;
import org.usfirst.frc.team1334.robot.commands.teleop.DriveCommand;
import org.usfirst.frc.team1334.robot.commands.teleop.IntakeCommand;
import org.usfirst.frc.team1334.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.IntakeSubsystem;


public class Robot extends IterativeRobot
{


    //autoFile is a global constant that keeps you from recording into a different file than the one you play from
    public static String autoFile = "/home/lvuser/Center.csv";
    public static DriveSubsystem driveSubsystem;
    public static ClimberSubsystem climberSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static IntakeCommand intakeCommand;
    public static ClimbCommand climbCommand;
    public static DriveCommand driveCommand;
    public static OI oi;
    public static double x;
    public static double x2;
    public static double Distance;
    //SendableChooser<String> AutoChooser;
    public static double FOV;
    public static double StartPing;
    public static double EndPing;
    public static NetworkTable Pitable;
    //few different auto programs
    static int autoNumber = 11;
    BTMacroPlay player = null;
    BTMacroRecord recorder = null;
    boolean isRecording = false;
    boolean togglerecord = false;
    CommandGroup autonomousCommand;
    SendableChooser<CommandGroup> AutoChooser;

    @Override
    public void robotInit()
    {
        AutoChooser = new SendableChooser<CommandGroup>();
        //AutoChooser = new SendableChooser<String>();


        driveSubsystem = new DriveSubsystem();
        oi = new OI();
        driveCommand = new DriveCommand();
        intakeSubsystem = new IntakeSubsystem();
        intakeCommand = new IntakeCommand();
        climberSubsystem = new ClimberSubsystem();
        climbCommand = new ClimbCommand();
        Pitable = NetworkTable.getTable("Pitable");
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);

        AutoChooser.addDefault("Center", new CenterPeg());
        AutoChooser.addObject("Red Left", new BlueLeftPeg());
        AutoChooser.addObject("Red Right", new BlueRightPeg());
        AutoChooser.addObject("Blue Left", new RedLeftPeg());
        AutoChooser.addObject("Blue Right", new RedRightPeg());
        AutoChooser.addObject("DONOTCLICK", new TestCommand());

        SmartDashboard.putData("Autonomous Selector", AutoChooser);
        /*
        AutoChooser.addDefault("Red Center", "/home/lvuser/RCenter.csv");
    	AutoChooser.addObject("Red Left", "/home/lvuser/RLeft.csv");
    	AutoChooser.addObject("Red Right", "/home/lvuser/RRight.csv");
    	AutoChooser.addObject("Blue Center", "/home/lvuser/BCenter.csv");
    	AutoChooser.addObject("Blue Left", "/home/lvuser/BLeft.csv");
    	AutoChooser.addObject("Blue Right", "/home/lvuser/BRight.csv");*/
    }


    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        Robot.driveSubsystem.ResetGyroAngle();
        autonomousCommand = new CenterPeg();
        if (autonomousCommand != null)
        {
            autonomousCommand.start();
        }
        //Robot.autoFile = (String) AutoChooser.getSelected();
        StartPing = System.currentTimeMillis();
        Robot.driveSubsystem.gearPiston(true);
        Robot.driveSubsystem.Flap(false);
        Robot.driveSubsystem.shiftGear(true, false);


        //try to create a new player
        //if there is a file, great - you have a new non-null object "player"

    	/*try 
        {
    		 player = new BTMacroPlay();
		} 
    	
		//if not, print out an error
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}*/

    }


    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
    	
    	
		/*if (player != null)
		{
			player.play();
		}*/
        Robot.x = Robot.Pitable.getNumber("x", Robot.FOV / 2);
        Robot.x2 = Robot.Pitable.getNumber("x2", Robot.FOV / 2);

        Robot.EndPing = System.currentTimeMillis();
        if (Robot.EndPing - Robot.StartPing > 100)
        {
            Robot.driveSubsystem.US.ping();
            Robot.StartPing = Robot.EndPing;
        }

        if (Robot.driveSubsystem.US.getRangeMM() >= 20 && Robot.driveSubsystem.US.getRangeMM() <= 4000)
        {
            Robot.Distance = Robot.driveSubsystem.US.getRangeMM();
        }
        Scheduler.getInstance().run();
    }

    public void teleopInit()
    {

        autoNumber += 1;
        isRecording = false;
    	/*try {
			recorder = new BTMacroRecord();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("oopsie!");
		}*/

        driveCommand.start();
        climbCommand.start();
        intakeCommand.start();
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void disabledInit()
    {
    }

    @Override
    public void teleopPeriodic()
    {
        SmartDashboard.putNumber("OUTPUT", driveSubsystem.getPIDController().get());
        SmartDashboard.putNumber("ERROR", driveSubsystem.getPIDController().getAvgError());
        SmartDashboard.putData("DriveSubsystem", driveSubsystem.getPIDController());
        SmartDashboard.putBoolean("Gear", driveSubsystem.gear1.get());
        SmartDashboard.putBoolean("Climber", climberSubsystem.climb1.get());
        SmartDashboard.putBoolean("Shift", driveSubsystem.shiftup.get());
        // PID GRAPH
        SmartDashboard.putNumber("SETPOINT", driveSubsystem.getPIDController().getSetpoint());
        SmartDashboard.putNumber("CURRENT YAW", driveSubsystem.ahrs.pidGet());

        FOV = 640;
        x = Pitable.getNumber("x", FOV / 2);
        x2 = Pitable.getNumber("x2", FOV / 2);
        boolean afterrecord = false;
        Scheduler.getInstance().run();
        OI.Driver.toggleBank.put("RBC", OI.Driver.boolfalsetotruelistener(OI.DToggleRecord(), OI.Driver.toggleBank.get("RBP")));
        OI.Driver.toggleBank.put("RBP", OI.DToggleRecord());
        if (OI.Driver.toggleBank.get("RBC"))
        {
            isRecording = !isRecording;
        }
        //if our record button has been pressed, lets start recording!
        if (isRecording)
        {
            afterrecord = true;
			/*try
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
			}*/
        }
        else if (afterrecord && !isRecording)
        {
			/*if(recorder!=null){
				try {
					recorder.end();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
        }

    }

    @Override
    public void testInit()
    {
        driveCommand.start();
    }

    @Override
    public void testPeriodic()
    {
        Scheduler.getInstance().run();
        LiveWindow.run();
    }


}