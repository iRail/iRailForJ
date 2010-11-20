package api;

import api.DOM.Parser;
import java.util.ArrayList;
import api.datastructs.Connection;
import api.datastructs.Liveboard;
import api.datastructs.Station;
import api.datastructs.VehicleInformation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Singleton pattern
 * @author pieterc
 */
public class DataBuilder {
    
    private static DataBuilder me = new DataBuilder();

    private String country = "be";
    private String lang = "EN";
    private String results = "6";
    private boolean dev = false;


    private DataBuilder(){
    }

    /**
     *
     * @param appname is the parameter that is used to identify yourself in the iRail api. Set it to "-" if you want to remain anonymous. Set it to your application name if you want us to see it's you
     * @return
     */
    public static DataBuilder getInstance(String appname){
        System.setProperty("http.agent", appname);
        return me;
    }

    public VehicleInformation getVehicleInformation(String vehicleid) throws Exception{
        return Parser.parseVehicle(getBaseUrl() + "vehicle/?id=" + vehicleid);
    }

    public Liveboard getLiveboard(String station) throws Exception{
        return Parser.parseLiveboard(getBaseUrl() + "liveboard/?station=" + station);
    }

    public ArrayList<Connection> getConnections(String from, String to) throws Exception{
        return getConnections(from, to, new Date(), true);
    }

    public ArrayList<Connection> getConnections(String from, String to, Date d, boolean departureTime) throws Exception{
        DateFormat df1 = new SimpleDateFormat("ddMMyy");
        DateFormat df2 = new SimpleDateFormat("HHmm");
        String date = df1.format(d);
        String time = df2.format(d);
        String timeSel;
        if(departureTime){
            timeSel = "dep";
        }else {
            timeSel = "arr";
        }
        String url = getBaseUrl() + "connections/?from=" + from + "&to=" + to + "&results=" + results + "&date=" + date + "&time=" + time + "&lang=" + lang + "&timeSel=" + timeSel;
        return  (ArrayList<Connection>) Parser.parseConnections(url);
    }

    public ArrayList<Station> getStations() throws Exception{
        String url = getBaseUrl() + "stations/?lang=" + lang;
        return (ArrayList<Station>) Parser.parseStations(url);
    }

    private String getBaseUrl(){

        String url = null;
        if(dev){
            url = "http://dev.api.irail."+ country + "/";
        }else{
            url = "http://api.irail."+ country + "/";
        }
        return url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setDev(boolean b) {
        this.dev = b;
    }



}
