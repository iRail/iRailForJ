package be.irail.api.test;

import be.irail.api.data.VehicleInformation;
import be.irail.api.data.Station;
import be.irail.api.data.Liveboard;
import be.irail.api.data.Vehicle;
import be.irail.api.IRail;
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
public class Vehicles
{
    IRail   iRail;
    Vehicle vehicle;
    
    public Vehicles()
    {
    }

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
        Liveboard liveBoard = iRail.getLiveboard(new Station("LIEDEKERKE"));
        assertTrue(liveBoard.getArrivalsAndDepartures().size() > 0);
        vehicle=liveBoard.getArrivalsAndDepartures().get(0).getVehicle();
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testVehicle() throws Exception
    {
            VehicleInformation vehicleInformation=iRail.getVehicleInformation(vehicle);
            assertNotNull(vehicleInformation);
            assertEquals("id matches", vehicleInformation.getVehicle().getId(), vehicle.getId());
    } 
}
