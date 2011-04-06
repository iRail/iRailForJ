package be.irail.api.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pieterc
 * @author frank@apsu.be
 */
public class Liveboard  implements Serializable
{
    private Date                        timeStamp;
    private Station                     station;
    private ArrayList<ArrivalDeparture> arrivalsAndDepartures;

    public Liveboard(Date timeStamp, Station station, ArrayList<ArrivalDeparture> arrivalsAndDepartures)
    {
        this.timeStamp=timeStamp;
        this.station = station;
        this.arrivalsAndDepartures=arrivalsAndDepartures;
    }
    
    public Liveboard()
    {
        this.arrivalsAndDepartures=new ArrayList<ArrivalDeparture>();
    }

    public List<ArrivalDeparture> getArrivalsAndDepartures()
    {
        return arrivalsAndDepartures;
    }

    public Station getStation()
    {
        return station;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setArrivalsAndDepartures(ArrayList<ArrivalDeparture> arrivalsAndDepartures)
    {
        this.arrivalsAndDepartures = arrivalsAndDepartures;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    public void setTimeStamp(Date timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public boolean addArrivalDeparture(ArrivalDeparture arrivalDeparture)
    {
        return arrivalsAndDepartures.add(arrivalDeparture);
    }
}
