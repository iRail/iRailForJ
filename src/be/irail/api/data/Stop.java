package be.irail.api.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author pieterc
 * @author frank@apsu.be
 */
public class Stop  implements Serializable
{
    private Station station;
    private Date    time;
    private int     delay;

    public Stop(Station station, Date time, int delay)
    {
        this.station = station;
        this.time = time;
        this.delay = delay;
    }

    public Stop()
    {
    }

    public int getDelay()
    {
        return delay;
    }

    public Station getStation()
    {
        return station;
    }

    public Date getTime()
    {
        return time;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }
}
