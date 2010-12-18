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
        liveBoardController=new LiveboardController(new IRail(url, language,50).setAgent("LiveboardExample_0_1"),new Station(station));

        liveBoardController.addObserver(liveBoardCanvas);
        liveBoardController.setDelay(10);
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
        for(int i=0;i<argv.length;i++)
            System.err.println("ARG " + i + " = " + argv[i]);

        if(argv.length==3)
            new LiveBoard(argv[0],argv[1],argv[2]);
        else
            new LiveBoard("http://api.irail.be/","nl","Liedekerke");         /// <<<< Change Me
    }
}
