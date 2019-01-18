package footballstats;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author natha
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
//        System.out.println("Working Directory = " +
//              System.getProperty("user.dir"));
        //cc03 = aj, 88b4 = nf, 41aa = ball
        String action = "actions";
        String[] tagIDs = {"CC03", "88B4", "41AA"} ;
        PosLogToArff pos = new PosLogToArff();
        pos.readFile("06012019", action, tagIDs);
//        String filePath = action + "_output.txt";
//        ARFFBuilder arff = new ARFFBuilder(filePath, true);
//        arff.createArffFile();
        //Experiments();
    }
    
}
