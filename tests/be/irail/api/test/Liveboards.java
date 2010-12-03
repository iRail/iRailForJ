package be.irail.api.test;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import be.irail.api.data.Station;
import be.irail.api.data.Liveboard;
import be.irail.api.IRail;
import be.irail.api.data.ArrivalDeparture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pieterc
 */
public class Liveboards
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
    public void setUp()
    {
        iRail = new IRail(ApiTestConstants.TEST_URL);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testLiveboard()
    {
        try
        {
            Liveboard liveBoard = iRail.getLiveboard(new Station("LIEDEKERKE"));
            assertTrue(liveBoard.getArrivalsAndDepartures().size() > 0);
            List <ArrivalDeparture> arrivalsAndDepartures=liveBoard.getArrivalsAndDepartures();
            for(ArrivalDeparture arrivalDeparture : arrivalsAndDepartures)
            {
                logger.log(Level.SEVERE, "Track {0} Type {1} Destination {2} Departs {3}", new Object[]{arrivalDeparture.getPlatform(),arrivalDeparture.getVehicle().getType(),arrivalDeparture.getStation().getName(), arrivalDeparture.getDate().toString()});
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
