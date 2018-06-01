import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ARFFBuilder {
	public Map<String, ArrayList<String>> instanceMap; 
	public ARFFBuilder () {
	 this.instanceMap = new HashMap<String, ArrayList<String>>();
		
	}
	
	public Map<String, ArrayList<String>> getInstanceMap() {
		return instanceMap;
	}

	public void setInstanceMap(Map<String, ArrayList<String>> instanceMap) {
		this.instanceMap = instanceMap;
	}
	
	public String actionFromString(String text) {
		if(text.toLowerCase().contains("pass")) {
			return "pass";
		}
		if(text.toLowerCase().contains("tackle")) {
			return "tackle";
		}
		if(text.toLowerCase().contains("dribble")) {
			return "dribble";
		}
		if(text.toLowerCase().contains("innacurate")) {
			return "innacuratePass";
		}
		else return "";
	}
	

	public static void main(String[] args) {
		ARFFBuilder builder = new ARFFBuilder();
	try(
			FileWriter fw = new FileWriter("ss.txt", true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			)
		{
		 
	
	int highestLength = 0;
		
	
	
	    String line = "";
	    String previousLine = "";
	    BufferedReader br = new BufferedReader(new FileReader("example_output.txt"));
	    while ((line = br.readLine()) != null) {
	       // process the line.
	    if(line.length() > 0) {
	    String firstLetter = line.substring(0, 1);
	    if(firstLetter.equals("[")) {
	    	line = line.replace("]", "");
	    	line = line.replace("[", "");
	    	int length = line.split(",").length;
	    	if (length > highestLength) {highestLength = length;};
	    	previousLine = builder.actionFromString(previousLine);
	    	if(builder.getInstanceMap().get(previousLine) != null) {
	    		builder.getInstanceMap().get(previousLine).add(line);
	    	}
	    	else {
	    		ArrayList<String> aList = new ArrayList<String>();
	    		builder.getInstanceMap().put(previousLine, aList);
	    		builder.getInstanceMap().get(previousLine).add(line);
	    	}
	    }
	    previousLine = line;
	    }
	    else {previousLine = line;}
		
		
		
	}
	
	    Iterator it = builder.getInstanceMap().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        @SuppressWarnings("unchecked")
			ArrayList<String> actionList = (ArrayList<String>) pair.getValue();
	        String action = (String) pair.getKey();
	        for (String positions : actionList) {
	        		String output = "";
	        		String [] lineArray = positions.split(",");
	        		int count = 0;
	        		output = output +"\n";
	        		//output = output +"<";
	        		for (int i = 0; i <= highestLength; i++) {
	        			if(count >= lineArray.length) {
	        				output = output +"?";
	        			}
	        			else {
	        				output = output + lineArray[i];
	        			}
	        			output = output +",";	
	        			count++;
	        		}
	        		output = output + "," + action;
	        		//out.println(action + "\n");
	        		out.println(output);
	        		
	        		
	        }
	    }
	
	
	    out.close();
	   
	}
	catch (IOException e) {
	    //exception handling left as an exercise for the reader
	}
		System.out.println("dd");
	}

}
