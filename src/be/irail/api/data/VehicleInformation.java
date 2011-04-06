package be.irail.api.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pieterc
 */
public class VehicleInformation  implements Serializable
{
    private Vehicle     vehicle;
    private List<Stop>  stops;
    private Location    location;

    public VehicleInformation(Vehicle vehicle, List<Stop> stops)
    {
        this.vehicle = vehicle;
        this.stops = stops;
    }

    public VehicleInformation()
    {
        stops=new ArrayList<Stop>();
    }

    public List<Stop> getStops()
    {
        return stops;
    }

    public Location getLocation()
    {
        return location;
    }
    
    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setStops(List<Stop> stops)
    {
        this.stops = stops;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public VehicleInformation addStop(Stop stop)
    {
        stops.add(stop);
        return this;
    }
}
