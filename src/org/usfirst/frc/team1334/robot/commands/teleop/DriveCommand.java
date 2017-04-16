package org.usfirst.frc.team1334.robot.commands.teleop;


import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;

public class DriveCommand extends Command
{
    public double USDistance = 20;
    private boolean GyroInit;
    private boolean DriveInit;
    private boolean PID = false;
    private double Start;

    /**
     * DriveCommand controls the robot drivetrain during teleop.
     */
    public DriveCommand()
    {
        super("DriveCommand");
        requires(Robot.driveSubsystem);

    }

    @Override
    protected void initialize()
    {
        // Set CANTalon Control modes.
        Robot.driveSubsystem.left1.setControlMode(0);
        Robot.driveSubsystem.left2.setControlMode(0);
        Robot.driveSubsystem.right1.setControlMode(0);
        Robot.driveSubsystem.right2.setControlMode(0);
        // Reset gyro angle
        Robot.driveSubsystem.ResetGyroAngle();
        Start = System.currentTimeMillis();
    }


    @Override
    protected void execute()
    {
        Robot.driveSubsystem.cameraControl();
        double end = System.currentTimeMillis();
        // Check if the time elapsed is greater than 100 milliseconds.
        if (end - Start > 100)
        {
            Robot.driveSubsystem.US.ping();
            Start = end;
        }

        // Check if the robot is within 20mm to 4000mm (2cm to 400 cm)
        if (Robot.driveSubsystem.US.getRangeMM() >= 20 && Robot.driveSubsystem.US.getRangeMM() <= 4000)
        {
            USDistance = Robot.driveSubsystem.US.getRangeMM();
        }

        System.out.println(USDistance);
        double requiredDistance = 3000;
        double error = USDistance - requiredDistance;
        double robotUSOut = error * 0.001;
        Robot.driveSubsystem.Flap(OI.OClimbGuidePistonToggle());
        Robot.driveSubsystem.gearPiston(!OI.OGearPistonToggle());

        OI.Driver.toggleBank.put("XC", OI.Driver.boolfalsetotruelistener(OI.DEnableGyro(), OI.Driver.toggleBank.get("XP")));
        OI.Driver.toggleBank.put("XP", OI.DEnableGyro());
        if (OI.Driver.toggleBank.get("XC"))
        {
            PID = !PID;
        }
        if (PID)
        {
            if (GyroInit)
            {
                Robot.driveSubsystem.GyroPIDinit();
                GyroInit = false;
            }
            DriveInit = true;

            if (OI.DToggleVision())
            {
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

            Robot.driveSubsystem.speed = OI.DgetSpeed();

            Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);

            Robot.driveSubsystem.setGearShift();
        }
        else
        {
            if (DriveInit)
            {
                Robot.driveSubsystem.driveControlMode();
            }
            GyroInit = true;


            System.out.println("USout" + robotUSOut);


            Robot.driveSubsystem.arcadeDrive(OI.DgetTurn(), OI.DgetSpeed());

            Robot.driveSubsystem.speed = OI.DgetSpeed();
            Robot.driveSubsystem.shiftGear(OI.DHighGear(), OI.DLowGear());
            Robot.driveSubsystem.setGearShift();
        }

    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
    }

    @Override
    protected void interrupted()
    {
    }
}
