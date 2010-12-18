package be.irail.api.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author pieterc
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
}
