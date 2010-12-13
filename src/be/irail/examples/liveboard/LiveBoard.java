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

    public LiveBoard(String url, String language, String station, boolean displayiRail)
    {
        liveBoardCanvas=new LiveBoardPanel(displayiRail);
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
        boolean displayiRail = true;
        if(argv[3].equals("")){
            displayiRail = false;
        }
        //Changed this to accept arguments from command line or jnlp
        new LiveBoard(argv[0],argv[1],argv[2], displayiRail);         /// <<<< Change Me
    }
}
