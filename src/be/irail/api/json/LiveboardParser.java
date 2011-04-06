package be.irail.api.json;

import be.irail.api.data.ArrivalDeparture;
import be.irail.api.data.Liveboard;
import be.irail.api.data.Location;
import be.irail.api.data.Station;
import be.irail.api.data.Vehicle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author frank@apsu.be
 */

public class LiveboardParser implements JsonDataHandler
{
    private static final int LIVEBOARD=1;
    private static final int LIVEBOARD_STATION=2;
    private static final int ARRIVALDEPARTURE=4;
    private static final int ARRIVALDEPARTURE_STATION_OR_PLATFORM=5;

    private Liveboard           liveBoard;
    private Station             station;
    private Location            location;
    private ArrivalDeparture    arrivalDeparture;
    private Vehicle             vehicle;
    private JsonParser          parser;
    private boolean             inArrivalDeparturePlatform;

    public Liveboard getLiveboard(InputStream inputStream)
    {
        parser=new JsonParser();
        
        try
        {
            parser.parse(inputStream, this);
        }
        catch (IOException ex)
        {
            Logger.getLogger(LiveboardParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return liveBoard;
    }

    public void open(int level,char[] data, int labelStarts, int labelSize)
    {
        //System.out.println("open " + level + "," + (labelSize>0?new String(data,labelStarts,labelSize):"(none)"));

        switch(level)
        {
            case LIVEBOARD:
                liveBoard=new Liveboard();
            break;

            case LIVEBOARD_STATION:
                station=new Station();
                location=new Location();
            break;

            case ARRIVALDEPARTURE:
                arrivalDeparture=new ArrivalDeparture();
                vehicle=new Vehicle();
            break;

            case ARRIVALDEPARTURE_STATION_OR_PLATFORM:
                switch(data[labelStarts])
                {
                    case 's':                               // stationinfo
                    station=new Station();
                    location=new Location();
                    inArrivalDeparturePlatform=false;
                    break;

                    case 'p':                               // platforminfo
                    inArrivalDeparturePlatform=true;
                    break;
                }
            break;
        }
    }

    public void data(int level, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
//        String label=new String(data,labelStarts,labelSize);
//        String value=null;
//
//        if(valueStarts>0)
//            value=new String(data,valueStarts,valueSize);
//
//        System.out.println(level + "," + label + ":" + value);

        switch(level)
        {
            case LIVEBOARD:              
                IRailReaders.readLiveboardInfo(liveBoard,data,labelStarts,labelSize,valueStarts,valueSize);             // liveboard's timestamp
            break;

            case LIVEBOARD_STATION:                                                                                         
                IRailReaders.readStationInfo(station,location,data,labelStarts,labelSize,valueStarts,valueSize);        // liveboard's station
            break;

            case ARRIVALDEPARTURE:
                IRailReaders.readArrivalDepartureInfo(arrivalDeparture,vehicle,data,labelStarts,labelSize,valueStarts,valueSize);        // delay, time, vehicle
            break;

            case ARRIVALDEPARTURE_STATION_OR_PLATFORM:
                if(inArrivalDeparturePlatform)                                                                      
                    IRailReaders.readPlatform(arrivalDeparture,data,labelStarts,labelSize,valueStarts,valueSize);   // arrivalDeparture's platform
                else                                                                                                
                    IRailReaders.readStationInfo(station,location,data,labelStarts,labelSize,valueStarts,valueSize);    // arrivalDeparture's  station
            break;
        }
    }

    public void close(int level)
    {
        //System.out.println("close " + level);

        switch(level)
        {
            case ARRIVALDEPARTURE_STATION_OR_PLATFORM:
                if(!inArrivalDeparturePlatform)
                    arrivalDeparture.setStation(station);
            break;
            
            case ARRIVALDEPARTURE:
                arrivalDeparture.setVehicle(vehicle);
                liveBoard.addArrivalDeparture(arrivalDeparture);
            break;

            case LIVEBOARD_STATION: 
                station.setLocation(location);
                liveBoard.setStation(station);
            break;

            case LIVEBOARD:                    
            break;
        }
    }

    private static void testLiveboard() throws IOException, MalformedURLException
    {
        byte[] readbuffer=new byte[131072];
        
        //URL url = new URL("http://dev.api.irail.be/liveboard/?station=liedekerke&format=json&lang=fr");
        URL url = new URL("http://dev.api.irail.be/liveboard/?station=LIEDEKERKE&format=json&lang=fr");
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

        LiveboardParser slParser = new LiveboardParser();

        for(int i=0;i<1;i++)
        {
            Liveboard liveboard = slParser.getLiveboard(new ByteArrayInputStream(readbuffer,0,pos));
            for(ArrivalDeparture ad : liveboard.getArrivalsAndDepartures())
            {
                System.out.println(ad.getDate().toString() + " " + ad.getDelay() + " " + ad.getPlatform() + " " + ad.getStation().getName() + " " + ad.getVehicle().getCountry() + " " + ad.getVehicle().getCompany() + " " + ad.getVehicle().getType() + " " + ad.getVehicle().getNumber());
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException, IOException
    {
        testLiveboard();
    }
}
