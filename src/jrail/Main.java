package jrail;

import api.DataBuilder;
import api.datastructs.Connection;
import java.util.ArrayList;

/**
 *
 * @author pieterc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DataBuilder db = DataBuilder.getInstance("iRail for J Test program");
            db.setCountry("be");
            db.setLang("NL");
            ArrayList <Connection> cons = db.getConnections("Harelbeke", "Gent");
            System.out.println("Platform: " + cons.get(0).getDeparture().getPlatform());
            System.out.println("Arrivaltime: "+cons.get(0).getDeparture().getTime());
            System.out.println("Arrivaltime: "+cons.get(0).getArrival().getTime());
            System.out.println("Delay: " + cons.get(0).getDeparture().getDelay());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
