package jrail;

import api.DataBuilder;
import api.datastructs.Stop;
import api.datastructs.VehicleInformation;

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
            db.setDev(true);
            /*ArrayList <Connection> cons = db.getConnections("Harelbeke", "Gent sint pieters");
            System.out.println("Platform: " + cons.get(0).getDeparture().getPlatform());
            System.out.println("Arrivaltime: "+cons.get(0).getDeparture().getTime());
            System.out.println("Arrivaltime: "+cons.get(0).getArrival().getTime());
            System.out.println("Delay: " + cons.get(0).getDeparture().getDelay());*/
            /*Liveboard l = db.getLiveboard("Gent-sint-pieters");
            System.out.println(l.getS().getName());
            System.out.println(l.getNodes().size());*/
            VehicleInformation vi = db.getVehicleInformation("BE.NMBS.P2143");
            System.out.println(vi.getVehicle().getId());
            System.out.println(vi.getStops().size());
            for(Stop p : vi.getStops()){
                System.out.println(p.getStation().getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
