/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballstats;

import java.io.FileNotFoundException;

/**
 *
 * @author natha
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("Working Directory = " +
//              System.getProperty("user.dir"));
        String[] tagIDs = {"0B3A", "921E", "59AD"} ;
        PosLogToArff pos = new PosLogToArff();
        pos.readFile("06052018", "passes", tagIDs);
    }
    
}
