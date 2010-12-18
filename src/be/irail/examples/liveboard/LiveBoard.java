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
    private LiveBoardPanel          liveBoardCanvas;
    private LiveboardController     liveBoardController;

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

    public void setCaptionLine(String captionLine)
    {
        liveBoardCanvas.setCaptionLine(captionLine);
    }

    public static void main(String[] argv) throws Exception
    {
        String  url     ="http://api.irail.be/";
        String  lang    ="en";
        String  station ="Liedekerke";
        String  caption ="                 Powered by iRail";

        for(int i=0;i<argv.length;i++)
            System.err.println("ARG " + i + " = " + argv[i]);

        switch(argv.length)
        {
            case 1:
            station=argv[0];
            break;

            case 2:
            station=argv[0];
            lang=argv[1];
            break;

            case 3:
            url=argv[0];
            lang=argv[1];
            station=argv[2];
            break;

            case 4:
            url=argv[0];
            lang=argv[1];
            station=argv[2];
            if((argv[3].equals("")))
                caption=null;
            else
                caption=argv[3];
        }

        if(caption!=null)
            new LiveBoard(url,lang,station).setCaptionLine(caption);
        else
            new LiveBoard(url,lang,station);
    }
}
