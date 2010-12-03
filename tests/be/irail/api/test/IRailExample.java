package be.irail.api.test;

import be.irail.api.IRail;
import be.irail.api.data.Connection;
import be.irail.api.data.TripNode;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author frank
 */
public class IRailExample
{
    public static final String TEST_URL="http://dev.api.irail.be";

    public static void main(String[] argv) throws Exception
    {
        IRail       iRail       =new IRail(TEST_URL,"nl");
        Calendar    cal         =Calendar.getInstance();
        
        List<Connection> connections = iRail.getConnections("LIEDEKERKE","BRUSSEL CENTRAAL");
        for(Connection connection : connections)
        {
            // skip connections that have transfers, to keep this example simple
            if(connection.hasVias())        
                continue;

           TripNode departure=connection.getDeparture();
           TripNode arrival  =connection.getArrival();

           cal.setTime(departure.getTime());
           System.out.printf("DEPARTURE [%2d:%02d %-22s %-3s %2s +%02dH%02d] -> ",
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    departure.getStation().getName(),
                    departure.getVehicle().getType(),
                    departure.getPlatform(),
                    (departure.getDelay()/60)/60,
                    (departure.getDelay()/60)%60);

            cal.setTime(arrival.getTime());
            System.out.printf("ARRIVAL [%2d:%02d %-22s %-3s %2s]\n",
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    arrival.getStation().getName(),
                    arrival.getVehicle().getType(),
                    arrival.getPlatform());

        }
    }
}
