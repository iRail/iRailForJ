package be.irail.api.data;

import java.util.Date;

public class ArrivalDeparture
{
    private Station station;
    private Vehicle vehicle;
    private Date    date;
    private String  platform;
    private int     delay;

    public ArrivalDeparture(Station station, Vehicle vehicle, Date date, String platform, int delay)
    {
        this.station    = station;
        this.vehicle    = vehicle;
        this.date       = date;
        this.platform   = platform;
        this.delay      = delay;
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
}
