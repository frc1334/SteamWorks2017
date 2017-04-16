package org.usfirst.frc.team1334.robot.commands.util;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.Robot;

/**
 *
 */
public class UltraDistanceCommand extends Command
{
    private double Distance, error, robotUSOut, Start, End, settleTime, SlowDistance, MaxTime;
    private double targetDistance, Begin, Finish;
    private boolean isForward;

    public UltraDistanceCommand(double TargetDistance, double SettleTime, double slowDistance)
    {


        requires(Robot.driveSubsystem);
        targetDistance = TargetDistance;
        settleTime = SettleTime;

    }


    protected void initialize()
    {
        isForward = (targetDistance < Robot.Distance);
        Start = System.currentTimeMillis();
        End = System.currentTimeMillis();
    }


    protected void execute()
    {
        Distance = Robot.Distance;
        System.out.println(Robot.Distance);
        error = targetDistance - Distance;
        System.out.println("error" + error);
        robotUSOut = error * 0.001;
        System.out.println("output" + robotUSOut);
        if (Math.abs(error) > 20)
        {
            Start = System.currentTimeMillis();
            if (Distance < SlowDistance && robotUSOut > 0)
            {
                Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage * 0.8;
            }
            else if (robotUSOut < Robot.driveSubsystem.minimalvoltage * 0.8 && robotUSOut > 0)
            {
                Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage * 0.8;
            }
            else if (robotUSOut > -Robot.driveSubsystem.minimalvoltage * 0.8 && robotUSOut < 0)
            {
                Robot.driveSubsystem.speed = -Robot.driveSubsystem.minimalvoltage * 0.8;
            }
            else if (robotUSOut > 0.45)
            {
                Robot.driveSubsystem.speed = 0.45;
            }
            else if (robotUSOut < -0.45)
            {
                Robot.driveSubsystem.speed = -0.45;
            }
            else
            {
                Robot.driveSubsystem.speed = robotUSOut;
            }
        }
        else
        {
            End = System.currentTimeMillis();
            Robot.driveSubsystem.speed = 0;
        }


        Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());
        Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);

    }


    protected boolean isFinished()
    {
        return End - Start > 500;

    }


    protected void end()
    {
        Robot.driveSubsystem.arcadeDrive(0, 0);
    }


    protected void interrupted()
    {
    }
}
