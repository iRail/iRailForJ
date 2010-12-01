package be.irail.api.data;

/**
 *
 * @author pieterc
 */
public class Location
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
