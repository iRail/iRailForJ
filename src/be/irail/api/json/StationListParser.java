package be.irail.api.json;

import be.irail.api.data.Location;
import be.irail.api.data.Station;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author frank@apsu.be
 */

public class StationListParser implements JsonDataHandler
{
    Station     station;
    Location    location;
    ArrayList   stationList;
    JsonParser  parser;

    public List getStationList(InputStream inputStream)
    {
        stationList=new ArrayList();
        parser=new JsonParser();
        
        try
        {
            parser.parse(inputStream, this);
        }
        catch (IOException ex)
        {
            Logger.getLogger(StationListParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stationList;
    }

    public void data(int level, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        switch(level)
        {
           case 3:
           IRailReaders.readStationInfo(station,location,data,labelStarts,labelSize,valueStarts,valueSize);        // station
           break;
        }
    }

    public void open(int level,char[] data, int labelStarts, int labelSize)
    {
        //System.err.println("open");
        station=new Station();
        location=new Location();
    }

    public void close(int level)
    {
        //System.err.println("close");
        if(station==null)
            return;
        station.setLocation(location);
        stationList.add(station);
        station=null;
    }

    private static void testStationList() throws IOException, MalformedURLException
    {
        byte[] readbuffer=new byte[131072];
        
        //URL url = new URL("http://dev.api.irail.be/liveboard/?station=liedekerke&format=json&lang=fr");
        URL url = new URL("http://dev.api.irail.be/stations/?format=json&lang=fr");
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("Accept-Encoding", "gzip");
        urlConnection.addRequestProperty("Connection", "close");
        InputStream inputStream = urlConnection.getInputStream();

        int pos=0;
        int charsRead=inputStream.read(readbuffer);
        while(charsRead>0)
        {
            pos+=charsRead;
            charsRead=inputStream.read(readbuffer,pos,16384);
        }

        StationListParser slParser = new StationListParser();

        for(int i=0;i<1;i++)
        {
            List<Station> stations = slParser.getStationList(new ByteArrayInputStream(readbuffer,0,pos));
            for(Station station : stations)
            {
                System.out.println(station.getName() + " " + station.getId() + " " + station.getLocation().getLongitude() + " " + station.getLocation().getLatitude());
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException, IOException
    {
        testStationList();
    }
}
