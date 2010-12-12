package be.irail.api.test;

import be.irail.api.data.TripNode;
import be.irail.api.data.Connection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import be.irail.api.IRail;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pieterc
 */
public class Connections
{
    static final Logger logger=Logger.getLogger(Connections.class.getName());
    IRail iRail;

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        iRail = new IRail(ApiTestConstants.TEST_URL);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testConnections()
    {
        
        try
        {
            List<Connection> connections = iRail.getConnections("LIEDEKERKE","BRUSSEL CENTRAAL");
            assertFalse(connections.isEmpty());
            for(Connection connection : connections)
            {
                TripNode arrival=connection.getArrival();
                TripNode departure=connection.getDeparture();
                logger.log(Level.SEVERE, "Connection ARRIVAL [{0} {1} ON PLATFORM {2} AT {3}", new Object[]{arrival.getVehicle().getType(), arrival.getVehicle().getNumber(), arrival.getPlatform(), arrival.getTime().toString()});
                //logger.log(Level.SEVERE, "Connection DEPARTS [{0} {1} ON PLATFORM {2} AT {3}", new Object[]{departure.getVehicle().getType(), departure.getVehicle().getNumber(), departure.getPlatform(), departure.getTime().toString()});
            }
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
