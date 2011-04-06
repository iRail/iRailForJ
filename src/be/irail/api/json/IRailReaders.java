/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.irail.api.json;

import be.irail.api.data.ArrivalDeparture;
import be.irail.api.data.Liveboard;
import be.irail.api.data.Location;
import be.irail.api.data.Station;
import be.irail.api.data.Stop;
import be.irail.api.data.Vehicle;
import java.util.Date;

/**
 *
 * @author frank@apsu.be
 */
public class IRailReaders
{
    public static void readLiveboardInfo(Liveboard liveboard ,char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelStarts]=='t')                                                                              // timestamp
            liveboard.setTimeStamp(new Date(1000*NumericUtil.decimalCharsToLong(data, valueStarts, valueSize)));
    }

    public static void readStationInfo(Station station, Location location,char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelStarts]=='i')                                                                      // id
            station.setId(new String(data,valueStarts,valueSize));
        else if(data[labelStarts]=='n')                                                                 // name
            station.setName(new String(data,valueStarts,valueSize));
        else if(data[labelStarts]=='l')                                                                 // location
           readLocation(location,data,labelStarts,labelSize,valueStarts,valueSize);
    }

    public static void readLocation(Location location,char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelSize]=='X')
                location.setLongitude(NumericUtil.decimalCharsToDouble(data,valueStarts,valueSize));      // locationX
        else if(data[labelSize]=='Y')
                location.setLatitude(NumericUtil.decimalCharsToDouble(data,valueStarts,valueSize));       // locationY
    }

    public static void readPlatform(ArrivalDeparture arrivalDeparture, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelStarts]=='n')                                                                      // name or normal
        {
            if(data[labelStarts+1]=='a')                                                                // name
                arrivalDeparture.setPlatform(new String(data,valueStarts,valueSize));
            else if (data[labelStarts+1]=='o')                                                          // normal
                arrivalDeparture.setNormal(data[valueStarts]=='1');
        }
    }

    public static void readArrivalDepartureInfo(ArrivalDeparture arrivalDeparture, Vehicle vehicle, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelStarts]=='d')                                                                                  // delay
            arrivalDeparture.setDelay((int)NumericUtil.decimalCharsToLong(data, valueStarts, valueSize));
        else if(data[labelStarts]=='t')                                                                             // time
            arrivalDeparture.setDate(new Date(1000*NumericUtil.decimalCharsToLong(data, valueStarts, valueSize)));
        else if(data[labelStarts]=='v')                                                                             // vehicle
            readVehicle(vehicle, data, valueStarts, valueSize);
    }

    public static void readStop(Stop stop, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelStarts]=='d')                                                                                  // delay
            stop.setDelay((int)NumericUtil.decimalCharsToLong(data, valueStarts, valueSize));
        else if(data[labelStarts]=='t')                                                                             // time
            stop.setTime(new Date(1000*NumericUtil.decimalCharsToLong(data, valueStarts, valueSize)));
    }

    public static void readVehicleInformation(Vehicle vehicle, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize)
    {
        if(data[labelStarts]=='v')                                                                             // vehicle
            readVehicle(vehicle, data, valueStarts, valueSize);
    }

    private static void readVehicle(Vehicle vehicle, char[] data, int valueStarts, int valueSize)
    {
        vehicle.setId(new String(data, valueStarts, valueSize));
    }
}
