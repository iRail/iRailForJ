package iRailAPITests;

import api.DataBuilder;
import api.datastructs.Station;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pieterc
 */
public class Stations {

     private DataBuilder db = DataBuilder.getInstance("Unittest in Java");
    private ArrayList<Station> stations ;
    public Stations() {
        try {
            db.setDev(true);
            db.setCountry("BE");
            db.setLang("EN");
            stations = db.getStations();
        } catch (Exception ex) {
            Logger.getLogger(Stations.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test_fetchStations(){
        //test if AALST is first station
        assertEquals(stations.get(0).getName(),"AALST");
        //TODO: test general system
    }

    /**        /*ArrayList <Connection> cons = db.getConnections("Harelbeke", "Gent sint pieters");
        System.out.println("Platform: " + cons.get(0).getDeparture().getPlatform());
        System.out.println("Arrivaltime: "+cons.get(0).getDeparture().getTime());
        System.out.println("Arrivaltime: "+cons.get(0).getArrival().getTime());
        System.out.println("Delay: " + cons.get(0).getDeparture().getDelay());*/
        /*Liveboard l = db.getLiveboard("Gent-sint-pieters");
        System.out.println(l.getS().getName());
        System.out.println(l.getNodes().size());*//*
        VehicleInformation vi = db.getVehicleInformation("BE.NMBS.P2143");
        System.out.println(vi.getVehicle().getId());
        System.out.println(vi.getStops().size());
        for (Stop p : vi.getStops()) {
            System.out.println(p.getStation().getName());
        }*/

}