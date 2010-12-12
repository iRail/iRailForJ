package be.irail.examples.liveboard;

import be.irail.api.IRail;
import be.irail.api.data.Liveboard;
import be.irail.api.data.Station;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frank@apsu.be
 */
public class LiveboardController extends Observable implements Runnable
{
    private int         MINIMAL_DELAY=30;
    private IRail       iRail;
    private Station     station;
    private Liveboard   liveBoard;
    private boolean     running;
    private int         delay;

    public LiveboardController(IRail iRail, Station station)
    {
        this.iRail = iRail;
        this.station = station;
        this.delay = MINIMAL_DELAY;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = (delay>MINIMAL_DELAY?delay:MINIMAL_DELAY);
    }

    public Station getStation()
    {
        return station;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    public void start()
    {
        Thread  thisThread=new Thread(this);
                thisThread.setDaemon(true);
                thisThread.start();
    }

    public void stop()
    {
        running=false;
    }

    public void run()
    {
        running=true;
        while(running)
        {
            try
            {
                liveBoard = iRail.getLiveboard(station);
                setChanged();
                notifyObservers(liveBoard);
            }
            catch (Exception ex)
            {
                Logger.getLogger(LiveboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                Thread.sleep(this.delay*1000);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(LiveboardController.class.getName()).log(Level.SEVERE, null, ex);
                running=false;
            }
        }
    }
}
