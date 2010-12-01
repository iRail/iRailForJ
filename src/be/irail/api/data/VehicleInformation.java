package be.irail.api.data;

import java.util.List;

/**
 *
 * @author pieterc
 */
public class VehicleInformation
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
