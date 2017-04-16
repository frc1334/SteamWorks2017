package org.usfirst.frc.team1334.robot;


import java.io.FileWriter;
import java.io.IOException;

/*
*This macro records all the movements you make in teleop and saves them to the file you specify.
*make sure you record every variable you need, if you dont record the value from a motor or a solenoid,
*you won't be able to play it back. It records it in "frames" that contain a value for each output 
* you want to use during teleop
*BE AWARE: write into the same file as you do in the Play macro
*BE AWARE: Only write/read the motors/other things that you actually have fully created in 
*your code. Otherwise you'll lose robot code randomly with no reason
*In main, the try/catch structure catches any IOExceptions or FileNotFoundExceptions. Necessary to play back
*the recorded routine during autonomous
*/


public class BTMacroRecord
{

    //this object writes values into the file we specify
    private FileWriter writer;

    private long startTime;

    public BTMacroRecord() throws IOException
    {
        //record the time we started recording
        startTime = System.currentTimeMillis();

        //put the filesystem location you are supposed to write to as a string
        //as the argument in this method, as of 2015 it is /home/lvuser/recordedAuto.csv
        writer = new FileWriter(Robot.autoFile);
    }


    public void record() throws IOException
    {
        if (writer != null)
        {
            //start each "frame" with the elapsed time since we started recording
            writer.append(String.valueOf(System.currentTimeMillis() - startTime));

            //in this chunk, use writer.append to add each type of data you want to record to the frame
            //the 2015 robot used the following motors during auto

            //drive motors
            // example append writer.append("," + storage.robot.getFrontLeftMotor().get());
            writer.append(",").append(String.valueOf(OI.DgetTurn()));
            writer.append(",").append(String.valueOf(OI.DgetSpeed()));
            writer.append(",").append(String.valueOf(Robot.driveCommand.USDistance));
            writer.append(",").append(String.valueOf(OI.DRangeFinderRecord()));
            writer.append(",").append(String.valueOf(OI.DToggleVision()));
            writer.append(",").append(String.valueOf(OI.OGearPistonToggle()));
            writer.append(",").append(String.valueOf(OI.DLowGear()));
            writer.append(",").append(String.valueOf(OI.DHighGear())).append("\n");
        /*
         * THE LAST ENTRY OF THINGS YOU RECORD NEEDS TO HAVE A DELIMITER CONCATENATED TO
		 * THE STRING AT THE END. OTHERWISE GIVES NOSUCHELEMENTEXCEPTION
		 */

            //this records a true/false value from a piston
            // example end delimiter writer.append("," + storage.robot.getToteClamp().isExtended() + "\n");
		
		/*
		 * CAREFUL. KEEP THE LAST THING YOU RECORD BETWEEN THESE TWO COMMENTS AS A
		 * REMINDER TO APPEND THE DELIMITER
		 */
            writer.flush();
        }
    }


    //this method closes the writer and makes sure that all the data you recorded makes it into the file
    public void end() throws IOException
    {
        if (writer != null)
        {
            writer.flush();
            writer.close();
        }
    }
}
