package be.irail.api;

import be.irail.api.dom.IRailParser;
import java.util.ArrayList;
import be.irail.api.data.Connection;
import be.irail.api.data.Liveboard;
import be.irail.api.data.Station;
import be.irail.api.data.Vehicle;
import be.irail.api.data.VehicleInformation;
import be.irail.helper.URLFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pieterc
 */
public class IRail
{
    private static final String DEFAULT_LANGUAGE    ="en";
    private static final String WRAPPER_SUFFIX      ="IRailForJ";
    private static final int    NO_MAX_RESULTS      =0;
    
    private String  language;
    private int     maxResults;
    private String  providerURL;

    public IRail(String providerURL)
    {
        this.providerURL=providerURL;
        this.language=DEFAULT_LANGUAGE;
        this.maxResults=NO_MAX_RESULTS;
    }

    public IRail(String providerURL,String language)
    {
        this.providerURL=providerURL;
        this.language=language;
        this.maxResults=NO_MAX_RESULTS;
    }

    public IRail(String providerURL,int maxResults)
    {
        this.providerURL=providerURL;
        this.language=DEFAULT_LANGUAGE;
        this.maxResults=maxResults;
    }

    public IRail(String providerURL, String language, int maxResults)
    {
        this.providerURL=providerURL;
        this.language=language;
        this.maxResults=maxResults;
    }

    public IRail(String providerURL, String language, int maxResults, String agentName)
    {
        this.providerURL=providerURL;
        this.language=language;
        this.maxResults=maxResults;
        setAgent(agentName);
    }

    /* --------------------------------------------------------------------------------------------- */
    
    public synchronized VehicleInformation getVehicleInformation(Vehicle vehicle) throws Exception
    {
        URLFactory  urlFactory=new URLFactory(providerURL,"vehicle/");
                    urlFactory.addQuery("id",vehicle.getId());
                    urlFactory.addQuery("lang",language);
        return IRailParser.parseVehicle(urlFactory.getURL());
    }

    public synchronized Liveboard getLiveboard(Station station) throws Exception
    {
        URLFactory  urlFactory=new URLFactory(providerURL,"liveboard/");
                    urlFactory.addQuery("station",station.getName());
                    urlFactory.addQuery("lang",language);
        return IRailParser.parseLiveboard(urlFactory.getURL());
    }

    public synchronized ArrayList<Connection> getConnections(String from, String to) throws Exception
    {
        return getConnections(from, to, null, false);
    }

    public synchronized ArrayList<Connection> getConnections(String from, String to, Date dateTime) throws Exception
    {
        return getConnections(from, to, dateTime, false);
    }

    public synchronized ArrayList<Connection> getConnections(String from, String to,  Date dateTime, boolean wantArrivalTime) throws Exception
    {
        URLFactory  urlFactory=new URLFactory(providerURL,"connections/");
                    urlFactory.addQuery("from",from);
                    urlFactory.addQuery("to",to);

                    if(maxResults>0)
                    {
                        urlFactory.addQuery("results", String.valueOf(maxResults));
                    }

                    if(dateTime!=null)
                    {
                        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
                        DateFormat timeFormat = new SimpleDateFormat("HHmm");
                        urlFactory.addQuery("date",dateFormat.format(dateTime));
                        urlFactory.addQuery("time",timeFormat.format(dateTime));
                    }
                    
                    if(wantArrivalTime)
                    {
                        urlFactory.addQuery("timeSel","arr");
                    }

                    urlFactory.addQuery("lang", language);

        return (ArrayList<Connection>)IRailParser.parseConnections(urlFactory.getURL());
    }

    public synchronized ArrayList<Station> getStations() throws Exception
    {
        URLFactory  urlFactory=new URLFactory(providerURL,"stations/");
                    urlFactory.addQuery("lang",language);
                    
        return (ArrayList<Station>)IRailParser.parseStations(urlFactory.getURL());
    }

    /* -------------------------------------------------------------------------------------- */

    public String getLanguage()
    {
        return language;
    }

    public IRail setLanguage(String language)
    {
        this.language = language;
        return this;
    }

    public int getMaxResults()
    {
        return maxResults;
    }

    public IRail setMaxResults(int maxResults)
    {
        this.maxResults = maxResults;
        return this;
    }

    /**
     *
     * @param agentName is the parameter that is used to identify yourself in the IRail api. Set it to null if you want to remain anonymous. Set it to your application name if you want us to see it's you
     * @return
     */
    public final IRail setAgent(String agentName)
    {
        System.setProperty("http.agent", agentName!=null?agentName+" "+WRAPPER_SUFFIX:WRAPPER_SUFFIX);
        return this;
    }
}
