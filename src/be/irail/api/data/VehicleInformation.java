package be.irail.api.data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author pieterc
 */
public class VehicleInformation  implements Serializable
{
    private Vehicle     vehicle;
    private List<Stop>  stops;

    public VehicleInformation(Vehicle vehicle, List<Stop> stops)
    {
        this.vehicle = vehicle;
        this.stops = stops;
    }

    public List<Stop> getStops()
    {
        return stops;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }
}
