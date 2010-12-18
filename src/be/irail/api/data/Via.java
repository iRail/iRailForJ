package be.irail.api.data;

import java.io.Serializable;

/**
 *
 * @author pieterc
 */
public class Via  implements Serializable
{
    private ViaTripNode arrival;
    private ViaTripNode depart;
    private int         timeBetween;
    private Vehicle     vehicle;
    private Station     station;

    public Via(ViaTripNode arrival, ViaTripNode depart, int timeBetween, Vehicle vehicle, Station station)
    {
        this.arrival = arrival;
        this.depart = depart;
        this.timeBetween = timeBetween;
        this.vehicle = vehicle;
        this.station = station;
    }

    public ViaTripNode getArrival()
    {
        return arrival;
    }

    public ViaTripNode getDepart()
    {
        return depart;
    }

    public Station getStation()
    {
        return station;
    }

    public int getTimeBetween()
    {
        return timeBetween;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }
}
