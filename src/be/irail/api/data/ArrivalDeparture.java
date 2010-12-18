package be.irail.api.data;

import java.io.Serializable;
import java.util.Date;

public class ArrivalDeparture  implements Serializable
{
    private Station station;
    private Vehicle vehicle;
    private Date    date;
    private String  platform;
    private int     delay;
    private boolean left;

    public ArrivalDeparture(Station station, Vehicle vehicle, Date date, String platform, int delay, boolean left)
    {
        this.station    = station;
        this.vehicle    = vehicle;
        this.date       = date;
        this.platform   = platform;
        this.delay      = delay;
        this.left       = left;
    }

    public Date getDate()
    {
        return date;
    }

    public String getPlatform()
    {
        return platform;
    }

    public Station getStation()
    {
        return station;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public int getDelay()
    {
        return delay;
    }

    public boolean hasLeft()
    {
        return left;
    }

    public boolean shouldHaveLeftAt(Date atTime)
    {
         return (atTime.getTime() < (getDate().getTime()+(1000*getDelay())));
    }
}
