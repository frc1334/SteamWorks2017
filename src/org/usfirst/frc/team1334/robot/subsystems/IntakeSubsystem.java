package org.usfirst.frc.team1334.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1334.robot.RobotMap;

public class IntakeSubsystem extends Subsystem
{
    public CANTalon intakePickup = new CANTalon(RobotMap.INTAKE_PICKUP);
    public CANTalon intakeDump = new CANTalon(RobotMap.INTAKE_DUMP);

    @Override
    protected void initDefaultCommand()
    {
    }

    public void runIntakePickup(boolean isSpinning)
    {
        intakeDump.set(isSpinning ? -1 : 0);
    }

    public void runIntakeDump(boolean isSpinning)
    {
        intakeDump.set(isSpinning ? -1 : 0);
    }

}
