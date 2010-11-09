package api.datastructs;
/**
 *
 * @author pieterc
 */
public class Station {
    String name;
    String id;
    Geoloc geolocation;

    public Station(String name, String id, Geoloc geolocation) {
        this.id = id;
        this.name = name;
        this.geolocation = geolocation;
    }

    public Geoloc getGeolocation() {
        return geolocation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}