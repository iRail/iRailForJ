package be.irail.api.json;

import be.irail.api.data.Location;
import be.irail.api.data.Station;
import be.irail.api.data.Stop;
import be.irail.api.data.Vehicle;
import be.irail.api.data.VehicleInformation;
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

public class VehicleInformationParser implements JsonDataHandler
{
    private static final int VEHICLE=1;
    private static final int VEHICLEINFORMATION_OR_STOPS=2;
    private static final int STOP=4;
    private static final int STOP_STATION=5;

    private VehicleInformation  vehicleInformation;
    private Vehicle             vehicle;
    private Stop                stop;
    private Station             station;
    private Location            location;
    private JsonParser          parser;
    private boolean             inVehicleInfo;

    public VehicleInformation getVehicleInformation(InputStream inputStream)
    {
        parser=new JsonParser();
        
        try
        {
            parser.parse(inputStream, this);
        }
        catch (IOException ex)
        {
            Logger.getLogger(VehicleInformationParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vehicleInformation;
    }

    public void open(int level,char[] data, int labelStarts, int labelSize)
    {
        System.out.println("open " + level + "," + (labelSize>0?new String(data,labelStarts,labelSize):"(none)"));

        switch(level)
        {
            case VEHICLE:
                vehicleInformation=new VehicleInformation();
                vehicle=new Vehicle();
            break;

           case VEHICLEINFORMATION_OR_STOPS:
                switch(data[labelStarts])
                {
                    case 'v':                               // vehicleinfo
                    location=new Location();
                    inVehicleInfo=true;
                    break;

                    case 's':                               // stops
                    inVehicleInfo=false;
                    break;
                }
            break;

            case STOP:
            stop=new Stop();
            break;

            case STOP_STATION:
                station=new Station();
                location=new Location();
            break;
        }
    }

    public void data(int level, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        String label=new String(data,labelStarts,labelSize);
        String value=null;

        if(valueStarts>0)
            value=new String(data,valueStarts,valueSize);

        System.out.println(level + "," + label + ":" + value);

        switch(level)
        {
            case VEHICLE:
                IRailReaders.readVehicleInformation(vehicle,data,labelStarts,labelSize,valueStarts,valueSize);   
            break;

            case VEHICLEINFORMATION_OR_STOPS:
                if(inVehicleInfo)
                    IRailReaders.readLocation(location,data,labelStarts,labelSize,valueStarts,valueSize);

            break;

            case STOP:
                IRailReaders.readStop(stop, data, labelStarts, labelSize, valueStarts, valueSize);
            break;

            case STOP_STATION:
                IRailReaders.readStationInfo(station, location, data, labelStarts, labelSize, valueStarts, valueSize);
            break;
        }
    }

    public void close(int level)
    {
        System.out.println("close " + level);

        switch(level)
        {
            case STOP_STATION:
                station.setLocation(location);
                stop.setStation(station);
            break;

            case STOP:
                vehicleInformation.addStop(stop);
            break;

            case VEHICLEINFORMATION_OR_STOPS:
                if(inVehicleInfo)
                    vehicleInformation.setLocation(location);

            break;

            case VEHICLE:
                vehicleInformation.setVehicle(vehicle);
            break;
        }
    }

    private static void testVehicleInformation() throws IOException, MalformedURLException
    {
        byte[] readbuffer=new byte[131072];
        
        //URL url = new URL("http://dev.api.irail.be/liveboard/?station=liedekerke&format=json&lang=fr");
        //URL url = new URL("http://dev.api.irail.be/liveboard/?station=LIEDEKERKE&format=json&lang=fr");
        
        URL url = new URL("http://dev.api.irail.be/vehicle/?id=Be.NMBS.IR3532&format=json&lang=fr");
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

        VehicleInformationParser slParser = new VehicleInformationParser();

        for(int i=0;i<1;i++)
        {
            VehicleInformation vehicleInformation = slParser.getVehicleInformation(new ByteArrayInputStream(readbuffer,0,pos));
            for(Stop stop : vehicleInformation.getStops())
            {
                System.out.println(stop.getStation());
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException, IOException
    {
        testVehicleInformation();
    }
}
