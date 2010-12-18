package be.irail.api.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author pieterc
 */
public class TripNode  implements Serializable
{
    private Station station;
    private String  platform;
    private Vehicle vehicle;
    private Date    time;
    private int     delay;

    public TripNode(Station station, String platform, Vehicle vehicle, Date time, int delay)
    {
        this.station = station;
        this.platform = platform;
        this.vehicle = vehicle;
        this.time = time;
        this.delay = delay;
    }

    public int getDelay()
    {
        return delay;
    }

    public String getPlatform()
    {
        return platform;
    }

    public Station getStation()
    {
        return station;
    }

    public Date getTime()
    {
        return time;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }
}
