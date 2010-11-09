package jrail;

import api.DOM.Parser;
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
            ArrayList <Connection> cons = (ArrayList<Connection>) Parser.parseConnections("http://api.irail.be/connections/?from=Harelbeke&to=Gent");
            System.out.println("Arrivaltime: "+cons.get(0).getArrival().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
