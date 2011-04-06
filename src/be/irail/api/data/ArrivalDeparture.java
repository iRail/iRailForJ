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
    private boolean normal;

    public ArrivalDeparture(Station station, Vehicle vehicle, Date date, String platform, int delay, boolean left)
    {
        this.station    = station;
        this.vehicle    = vehicle;
        this.date       = date;
        this.platform   = platform;
        this.delay      = delay;
        this.left       = left;
    }

    public ArrivalDeparture()
    {
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

    public boolean isNormal()
    {
        return normal;
    }

    public boolean shouldHaveLeftAt(Date atTime)
    {
         return (atTime.getTime() < (getDate().getTime()+(1000*getDelay())));
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public void setLeft(boolean left)
    {
        this.left = left;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public void setNormal(boolean normal)
    {
        this.normal = normal;
    }
}
