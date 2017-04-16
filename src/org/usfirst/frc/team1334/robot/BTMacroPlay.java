package org.usfirst.frc.team1334.robot;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*Code outline to implement playing back a macro recorded in BTMacroRecord
*Be sure to read out of the same file created in BTMacroRecord
*BEWARE OF: setting your motors in a different order than in BTMacroRecord and changing motor values before
*time is up. Both issues are dealt with and explained below. Also only read/write from/to the motors 
*you have fully coded for, otherwise your code will cut out for no reason. 
*In main, the try/catch structure catches any IOExceptions or FileNotFoundExceptions. Necessary to play back
*the recorded routine during autonomous
*Dennis Melamed and Melanie (sorta, she slept)
*March 22nd, 2015
*/


public class BTMacroPlay
{
    private Scanner scanner;
    private long startTime;
    private boolean firstTime = true;
    private boolean onTime = true;
    private boolean IsUS = false;
    private double nextDouble;
    private double turn, speed, Distance, error, robotUSOut;

    private boolean A, B, gear1, LB, USActive;


    public BTMacroPlay() throws FileNotFoundException
    {
        //create a scanner to read the file created during BTMacroRecord
        //scanner is able to read out the doubles recorded into recordedAuto.csv (as of 2015)
        scanner = new Scanner(new File(Robot.autoFile));

        //let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
        scanner.useDelimiter(",|\\n");

        //lets set start time to the current time you begin autonomous

    }

    public void play()
    {
        //if recordedAuto.csv has a double to read next, then read it
        if ((scanner != null) && scanner.hasNext())
        {
            double t_delta;

            //if we have waited the recorded amount of time assigned to each respective motor value,
            //then move on to the next double value
            //prevents the macro playback from getting ahead of itself and writing different
            //motor values too quickly
            if (onTime)
            {
                //take next value
                nextDouble = scanner.nextDouble();
                if (firstTime)
                {
                    startTime = System.currentTimeMillis() - (long) nextDouble;
                    firstTime = false;
                    Robot.driveSubsystem.ResetGyroAngle();
                }
            }

            //time recorded for values minus how far into replaying it we are--> if not zero, hold up
            t_delta = nextDouble - (System.currentTimeMillis() - startTime);

            //if we are on time, then set motor values
            if (t_delta <= 0)
            {

                //these are all the motors/pistons etc available to manipulate during autonomous.
                //it is extremely important to set the motors in the SAME ORDER as was recorded in BTMacroRecord
                //otherwise, motor values will be sent to the wrong motors and the robot will be unpredicatable
                //storage.robot.getFrontLeftMotor().setX(scanner.nextDouble());

                turn = scanner.nextDouble();
                speed = scanner.nextDouble();
                Distance = scanner.nextDouble();
                error = Robot.Distance - Distance;
                robotUSOut = error * 0.001;
                System.out.println("US motor output" + robotUSOut);
                System.out.println("Robot.distance" + Robot.Distance);
                IsUS = scanner.nextBoolean();
                LB = scanner.nextBoolean();

				
				
				/*
                 *playback using gyro and ultrasonic range finder
				 */


                if (LB)
                {
                    Robot.driveSubsystem.VisionDrive(Robot.x, Robot.x2);
                }
                Robot.driveSubsystem.setSetpoint(Robot.driveSubsystem.GyroDrive(turn));
                Robot.driveSubsystem.usePIDOutput(Robot.driveSubsystem.getPIDController().get());

                if (Robot.Distance < 1000 && speed > 0 || robotUSOut > 0)
                {
                    Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage;
                }
                else if (IsUS)
                {
                    if (Math.abs(error) > 20)
                    {
                        if (robotUSOut < Robot.driveSubsystem.minimalvoltage * 0.9 && robotUSOut > 0)
                        {
                            Robot.driveSubsystem.speed = Robot.driveSubsystem.minimalvoltage * 0.9;
                        }
                        else if (robotUSOut > -Robot.driveSubsystem.minimalvoltage * 0.9 && robotUSOut < 0)
                        {
                            Robot.driveSubsystem.speed = -Robot.driveSubsystem.minimalvoltage * 0.9;
                        }
                        else
                        {
                            Robot.driveSubsystem.speed = robotUSOut;
                        }
                    }
                }
                else
                {
                    Robot.driveSubsystem.speed = speed;
                }

                Robot.driveSubsystem.arcadeDrive(Robot.driveSubsystem.rotateToAngleRate, Robot.driveSubsystem.speed);


                gear1 = scanner.nextBoolean();
                OI.Operator.toggleBank.put("RBC", OI.Operator.boolfalsetotruelistener(gear1, OI.Operator.toggleBank.get("RBP")));
                OI.Operator.toggleBank.put("RBP", gear1);
                if (OI.Operator.toggleBank.get("RBC"))
                {
                    Robot.driveSubsystem.gearIn = !Robot.driveSubsystem.gearIn;
                }
                Robot.driveSubsystem.gearPiston(Robot.driveSubsystem.gearIn);
                A = scanner.nextBoolean();
                B = scanner.nextBoolean();
                Robot.driveSubsystem.shiftGear(A, B);


                //storage.robot.getToteClamp().set(storage.robot.getToteClamp().isExtended());

                //go to next double
                onTime = true;
            }
            //else don't change the values of the motors until we are "onTime"
            else
            {
                onTime = false;
            }
        }
        //end play, there are no more values to find
        else
        {
            this.end();
            if (scanner != null)
            {
                scanner.close();
                scanner = null;
            }
        }

    }

    //stop motors and end playing the recorded file
    public void end()
    {
        //storage.robot.getFrontLeftMotor().setX(0);
        //Robot.driveSubsystem.speed = 0;
        //Robot.driveSubsystem.usePIDOutput(0);


        //all this mess of a method does is keep the piston in the same state it ended in
        //if you want it to return to a specific point at the end of auto, change that here
        //storage.robot.getToteClamp().set(storage.robot.getToteClamp().isExtended());

        if (scanner != null)
        {
            scanner.close();
        }
    }

}
