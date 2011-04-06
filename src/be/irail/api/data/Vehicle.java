package be.irail.api.data;

import java.io.Serializable;

/**
 *
 * @author pieterc
 * @author frank@apsu.be
 */
public class Vehicle  implements Serializable
{
    private String  id; 
    private String  country;
    private String  company;
    private String  name;
    private int     number;
    private String  type;

    private void init()
    {
        this.number=-1;
    }

    /**
     * 
     * @param id
     * @param country
     * @param company
     * @param type
     * @param number
     * @deprecated 
     */
    public Vehicle(String id, String country, String company, String type, int number)
    {
        init();
        this.id         = id;
        this.number     = number;
        this.company    = company;
        this.country    = country;
        this.type       = type;
    }

    public Vehicle(String id)
    {
        init();
        this.id=id;
    }

    public Vehicle()
    {
        init();
    }

    public String getCountry()
    {
        if(country==null)
            parseFields();
        return country;
    }

    public String getCompany()
    {
        if(company==null)
            parseFields();
        return company;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    /**
     * @deprecated 
     * @return
     */
    public int getNumber()
    {
        if(number==-1)
            parseFields();
        return number;
    }

    /**
     * @deprecated
     * @return
     */
    public String getType()
    {
        if(type==null)
            parseFields();
        return type;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    private void parseFields()
    {
       int dot0=id.indexOf      ('.');
       int dot1=id.lastIndexOf  ('.');

       if(dot0==-1 || dot1==-1 || dot0==dot1)
           throw new IllegalArgumentException("Too Few Fields");

       country  =id.substring(0,      dot0);
       company  =id.substring(1+dot0, dot1);
       name     =id.substring(1+dot1);

       if(country.equalsIgnoreCase("be"))
       {
           int numberStart=-1;
           for(int i=0;i<name.length();i++)
           {
               if(Character.isDigit(name.charAt(i)))
               {
                   numberStart=i;
                   break;
               }
           }

           if(numberStart!=-1)
           {
               if(numberStart!=0)
               {
                   type=name.substring(0,numberStart);
                   number=Integer.parseInt(name.substring(numberStart));
               }
           }
       }
   }
}
