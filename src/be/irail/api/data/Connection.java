package be.irail.api.data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author pieterc
 */
public class Connection  implements Serializable
{
    private TripNode    arrival;
    private TripNode    departure;
    private int         duration;
    private List<Via>   vias;

    public Connection(TripNode arrival, TripNode departure, int duration, List<Via> vias)
    {
        this.arrival    = arrival;
        this.departure  = departure;
        this.duration   = duration;
        this.vias       = vias;
    }

    public TripNode getArrival()
    {
        return arrival;
    }

    public TripNode getDeparture()
    {
        return departure;
    }

    public int getDuration()
    {
        return duration;
    }

    public List<Via> getVias()
    {
        return vias;
    }

    public boolean hasVias()
    {
        return vias!=null;
    }
}
