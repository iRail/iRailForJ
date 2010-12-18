package be.irail.api.data;

import java.io.Serializable;

/**
 *
 * @author pieterc
 */
public class Location implements Serializable
{
    double longitude, latitude;

    public Location(double longitude, double latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }
}
