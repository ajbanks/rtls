/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballstats;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author natha
 */
public class PosLogToArff {
    
    //time of pass 1 starts at index 0 and ends at index 1, 
    //time of pass 2 starts at index 2 and ends at index 3 etc
    double actionTimes[];
    
    ArrayList<ArrayList<String>> actions;
    
    
    double time = 0.0;
    int timeCount = 0;
    
    public void readFile(String logDate, String action, int numberOfPlayers, String playerIds[]) throws FileNotFoundException{
        //get start and end time of each instance of an action from csv file
        //and put it in actionTimes array
        readCSVFile(logDate, action);
//        for (int i = 0; i < actionTimes.length; i++){
//            System.out.println(i + ": " + actionTimes[i]);
//        }
        
        //read log file
        int numActions = actionTimes.length/2;
        actions = new ArrayList<ArrayList<String>>(numActions);
        String fileName = "../../logs/" + logDate + "/pos_log.txt";
        Scanner file = new Scanner(new File(fileName));
        int startRecording = 0;
        String actionStart = action + " start";
        String actionEnd = action + " end";
        //the index currently at from the actionTime array eg pass 0 starts, pass 0 end, pass 1 start
        int actionTimeCount = 0;
        //the action instance number eg pass 0, pass 1, pass 2
        int actionCount = 0;
        //this indicates whether an instance of an action has started
        boolean hasStarted = false;
        //current line number
        int lineNumber = -1;
        //while the file has a next line and the end of the action hasnt been
        //reached yet
        while (file.hasNextLine() && startRecording != 2)
        {
            //get the next line and increment current line number
            String lineFromFile = file.nextLine();
            lineNumber++;
            
            //if beginning of action set startRecording to 1
            if(lineFromFile.contains(actionStart))
            {
                startRecording = 1;
                System.out.println(lineFromFile);
            }
            //if end of action set startRecording to 2
            if (lineFromFile.contains(actionEnd))
            {
                System.out.println("HANKII");
                startRecording = 2;
                System.out.println(lineFromFile);
                break;
            }
            
            //if start of action has begun then get positions of each instance 
            //of an action and put it in actions ArrayList
            if (startRecording == 1){
                
                //100 milliseconds has passed so increase time
                if (lineFromFile.length() > 0 && lineFromFile.charAt(0) == '['){
                    //System.out.println(lineFromFile);
                    time = timeCount/10.0;
                    timeCount++;
                    lineFromFile = file.nextLine();
                }
                System.out.println(time);
                
                //if the time is equal to the time in actionTimes then 
                //it is either the beginnging of an instance of an action
                if (actionTimes[actionTimeCount] == time){
                    //if an instance has already started then it is now
                    //the end of the instance and increase actionTimeCount
                    //to go to the next element in actionTimes (which will be
                    //the start of the next instance)
                    if (hasStarted){
                        hasStarted = false;
                        actionTimeCount++;
                        System.out.println("END OF INSTANCE");
//                        for (int i = 0; i < actions.size(); i++){
//                            System.out.println(i + ":");
//                            for(int j = 0; j < actions.get(i).size(); j++)
//                                System.out.println(actions.get(i).get(j));
//                        }
                        System.out.println("output done from array");
                    }
                    //else if an instance hasnt already started then it is now
                    //the beginning of an instance and increase actionTimeCount
                    //to go to the next element in actionTimes (which will be the
                    //end of this instance). Add new ArrayList to actions
                    //to hold positios for this instance
                    else{
                        hasStarted  = true;
                        actions.add(new ArrayList<String>());
                        actionTimeCount++;
                        actionCount++;
                        System.out.println("START OF INSTANCE");
                    }
                    
                }
                //if an instance has started then add the lines for this 
                //0.1 second to the ArrayList
                if (hasStarted){
                    System.out.println(lineFromFile);
                    String line = lineFromFile;
                    //get next lines that are part of this 0.1 second
                    while (file.hasNextLine()){
                        lineFromFile = file.nextLine();
                        System.out.println(lineFromFile);
                        if (lineFromFile.length() != 0){
                            line += " + " + lineFromFile;
                        }
                        else{
                            //go back to previous line
                            //goToPreviousLine(file, lineNumber);
                            //exit loop
                            break;
                        }
                        lineNumber++;
                    }
                    System.out.println("done");
                    // add line to the current instance
                    actions.get(actionCount-1).add(line);
                    System.out.println("actioncount: " + (actionCount-1));
                }
                
                
                
            }
        }
        
        for (int i = 0; i < actions.size(); i++){
            System.out.println(i + ":");
            for(int j = 0; j < actions.get(i).size(); j++)
            System.out.println(actions.get(i).get(j));
        }
        System.out.println("number of passes: " + (actionTimes.length/2));
        System.out.println("time count: " + actionCount);
    }
    
    private void goToPreviousLine(Scanner file, int lineNumber){
        for(int i = 0; i <= lineNumber;i++){
            if(file.hasNextLine())
                file.nextLine();
        }
    }
    
    public void readCSVFile(String logDate, String action) throws FileNotFoundException{
        String fileName = "../../logs/" + logDate + "/" + action + ".csv";
        Scanner file = new Scanner(new File(fileName));
        
        int lines = 0;
        System.out.println("start");
        while (file.hasNextLine()) {
            lines++;
            file.nextLine();
        }
            
        System.out.println("done");
        actionTimes = new double[(lines-1)*2];
        int count = 0;
        
        file = new Scanner(new File(fileName));
        String lineFromFile = file.nextLine();
        while (file.hasNextLine())
        {
            lineFromFile = file.nextLine();    
            String parts[] = lineFromFile.split(",");
            double startTime = Double.parseDouble(parts[1]);
            startTime = Math.floor(startTime * 10)/10;
            double endTime = Double.parseDouble(parts[2]);
            endTime = Math.floor(endTime * 10)/10;
            actionTimes[count] = startTime;
            count++;
            actionTimes[count] = endTime;
            //actionTimes[count-1] = lineFromFile;
            count++;
        }
        
        
        
    }
    
    
    public void getAction(){
        
    }
}
