package org.usfirst.frc.team1334.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1334.robot.RobotMap;

import java.util.LinkedList;
import java.util.Queue;

public class ClimberSubsystem extends Subsystem
{
    public CANTalon climber = new CANTalon(RobotMap.climber);
    public Solenoid climb1 = new Solenoid(RobotMap.EJECT_1);
    private Solenoid climb2 = new Solenoid(RobotMap.EJECT_2);
    private double Start = System.currentTimeMillis();
    private double End = System.currentTimeMillis();
    private LinkedList<Double> CurrentQueue = new LinkedList<Double>();

    @Override
    protected void initDefaultCommand()
    {
        climber.setControlMode(0);

    }

    public void Eject(boolean isEject, boolean isSafe)
    {
        if (isSafe)
        {
            End = System.currentTimeMillis();
        }
        else
        {
            Start = System.currentTimeMillis();
        }

        if (isEject && End - Start > 500)
        {
            climb1.set(true);
            climb2.set(false);
        }
        else
        {
            climb1.set(false);
            climb2.set(true);
        }
    }

    private Double QueueSum(Queue<Double> Q)
    {
        Double sum = 0.0;
        for (int i = 0; i < Q.size(); i++)
        {
            double n = Q.remove();
            sum += n;
            Q.add(n);
        }
        return sum;
    }

    public void climb(boolean isClimbing, boolean climbstopbypass)
    {
        if (!climbstopbypass)
        {
            climber.set(isClimbing ? 1 : 0);
        }
    }

    public boolean reverseClimb(boolean isReversed)
    {
        if (isReversed)
        {
            climber.set(-1.0);
        }
        return isReversed;
    }

    public void climbPiston(boolean out)
    {
        climb1.set(out);
        climb2.set(!out);
    }

    public boolean isStalledCap(double cap)
    {
        double current = climber.getOutputCurrent();
        return current > cap;
    }

    public boolean isStalledCapAverage(int Queuelength, double cap)
    {
        double current = climber.getOutputCurrent();

        CurrentQueue.add(current);
        while (CurrentQueue.size() > Queuelength)
        {
            CurrentQueue.remove();
        }

        return QueueSum(CurrentQueue) / CurrentQueue.size() < cap;
    }

    public boolean isStalledRatio(double ratio)
    {
        double current = climber.getOutputCurrent();

        CurrentQueue.add(current);
        while (CurrentQueue.size() > 2)
        {
            CurrentQueue.remove();
        }
        return CurrentQueue.get(0) / CurrentQueue.get(1) > ratio;
    }

}
