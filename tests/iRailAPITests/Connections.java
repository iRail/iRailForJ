package iRailAPITests;

import api.datastructs.Liveboard;
import api.DataBuilder;
import api.datastructs.Stop;
import api.datastructs.VehicleInformation;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pieterc
 */
public class Connections {

    DataBuilder db = DataBuilder.getInstance("iRail for J Test program");

    public Connections() {
        db.setCountry("be");
        db.setLang("NL");
        db.setDev(true);

    }

    @Test
    public void retrieved() {
        try {
            Liveboard l = db.getLiveboard("Gent-sint-pieters");
            assertTrue(l.getNodes().size() > 0);
        } catch (Exception ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
