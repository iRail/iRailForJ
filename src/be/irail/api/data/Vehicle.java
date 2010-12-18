package be.irail.api.data;

import java.io.Serializable;

/**
 *
 * @author pieterc
 */
public class Vehicle  implements Serializable
{
    private String  id;
    private int     number;
    private String  company;
    private String  country;
    private String  type;

    public Vehicle(String id, String country, String company, String type, int number)
    {
        this.id         = id;
        this.number     = number;
        this.company    = company;
        this.country    = country;
        this.type       = type;
    }

    public Vehicle(String id)
    {
        this.id         = id;
    }

    public String getCompany()
    {
        return company;
    }

    public String getCountry()
    {
        return country;
    }

    public String getId()
    {
        return id;
    }

    public int getNumber()
    {
        return number;
    }

    public String getType()
    {
        return type;
    }
}
