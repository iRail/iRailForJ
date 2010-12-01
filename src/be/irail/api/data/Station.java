package be.irail.api.data;

/**
 *
 * @author pieterc
 */
public class Station
{
    String      name;
    String      id;
    Location    location;

    public Station(String name, String id, Location location)
    {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Station(String name)
    {
        this.name = name;
    }

    public Location getLocation()
    {
        return location;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
