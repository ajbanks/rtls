/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package footballstats;

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
        readCSVFile(logDate, action);
        for (int i = 0; i < actionTimes.length; i++){
            System.out.println(i + ": " + actionTimes[i]);
        }
        
        //read log file
        int numActions = actionTimes.length/2;
        actions = new ArrayList<ArrayList<String>>(numActions);
        String fileName = "../../logs/" + logDate + "/pos_log.txt";
        Scanner file = new Scanner(new File(fileName));
        boolean startRecording = false;
        String actionStart = action + " start";
        String actionEnd = action + " end";
        //the action number eg pass 0, pass 1, pass 2
        int actionCount = 0;       
        //this indicates whther an action has started
        boolean hasStarted = false;
        while (file.hasNextLine())
        {
            String lineFromFile = file.nextLine();
            //beginning of action
            if(lineFromFile.contains(actionStart))
            {
                startRecording = true;
                System.out.println(lineFromFile);
            }
            if (lineFromFile.contains(actionEnd))
            {
                startRecording = false;
                System.out.println(lineFromFile);
            }
            
            //if action has begun
            if (startRecording){
                
                if (actionTimes[actionCount] == time){
                    if (hasStarted){
                        hasStarted = false;
                        actionCount++;
                    }
                    else{
                        hasStarted  = true;
                        actions.add(new ArrayList<String>());
                    }
                    
                }
                if (hasStarted){
                    actions.get(actionCount).add(lineFromFile);
                    while (file.hasNextLine()){
                        lineFromFile = file.nextLine();
                        if (lineFromFile.length() != 0){
                            actions.get(actionCount).add(lineFromFile);
                        }
                        else
                            break;
                    }
                }
                
                //100 milliseconds has passed so increase time
                if (lineFromFile.length() > 0 && lineFromFile.charAt(0) == '['){
                    time = timeCount/10.0;
                    timeCount++;
                }
            }
        }
        
        for (int i = 0; i < actions.size(); i++){
            System.out.println(i + ":");
            for(int j = 0; j < actions.get(i).size(); j++)
            System.out.println(actions.get(i).get(j));
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
