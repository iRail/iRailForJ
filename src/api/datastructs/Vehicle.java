package api.datastructs;

/**
 *
 * @author pieterc
 */
public class Vehicle {
    private String id;
    private String company;
    private String country;

    public Vehicle(String id, String company, String country) {
        this.id = id;
        this.company = company;
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }
    
}
