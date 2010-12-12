package be.irail.examples.liveboard;

import javax.swing.JFrame;
import be.irail.api.IRail;
import be.irail.api.data.Station;

/**
 *
 * @author frank@apsu.be
 */
public class LiveBoard extends JFrame
{
    private                 LiveBoardPanel          liveBoardCanvas;
    private                 LiveboardController     liveBoardController;

    public LiveBoard(String url, String language, String station)
    {
        liveBoardCanvas=new LiveBoardPanel();
        liveBoardController=new LiveboardController(new IRail(url, language).setAgent("LiveboardExample_0_1"),new Station(station));

        liveBoardController.addObserver(liveBoardCanvas);
        liveBoardController.setDelay(60);
        liveBoardController.start();

        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640,480);
        add(liveBoardCanvas);
        pack();

        setVisible(true);
    }

    public static void main(String[] argv) throws Exception
    {
        new LiveBoard("http://api.irail.be","nl","Brussel Centraal");         /// <<<< Change Me
    }
}
