package be.irail.examples.liveboard;

import be.irail.api.data.ArrivalDeparture;
import be.irail.api.data.Liveboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author frank@apsu.be
 */
public class LiveBoardPanel extends JPanel implements Observer
{

    private static final Color  BACKGROUND_COLOR    = new Color(48, 45, 59);
    private static final Color  HEADER_COLOR        = new Color(27, 118, 248);
    private static final Color  LINE_COLOR          = new Color(82, 102, 155);
    private static final Color  TEXT_COLOR          = new Color(255, 255, 0);
    private static final int    NUM_LINES           = 15;
    private static final int    CHARS_IN_TITLE      = 36;
    private static final int    CHARS_IN_LINE       = 50;
    private static final int    MAX_CHARS_IN_STATION= 28;
    private static final String FONT                = "Courier";
    private static final int    MIN_FONT_SIZE       = 2;
    private static final int    MAX_FONT_SIZE       = 72;
    private static final int    TIME_COLUMN         = 0;
    private static final int    STATION_COLUMN      = 1;
    private static final int    TYPE_COLUMN         = 2;
    private static final int    PLATFORM_COLUMN     = 3;
    private static final int    DELAY_COLUMN        = 4;
    
    private Liveboard       liveBoard;
    private int             width, height, borderThickness, imageableXPos, imageableYPos, imageableWidth, imageableHeight, lineHeight, dataYPos;
    private int             titleBaselineOffset, lineBaselineOffset,titleImageableHeight,lineImageableHeight;
    private int[]           lineColumns;
    private Font            titleFont, lineFont;
    private Calendar        calendar;
    private Dimension       oldSize;

    /*
     * A LiveBoardPanel is an Observer that will show Liveboard instances in an NMBS-like "small station" display
     * Since it's a JPanel, add it to any Container. The Liveboard prefers to be an arbitrary 640x480
     * but will resize to any reasonable size and remain readable as long as possible. Beware that the
     * tricks for determining Font size can be computationally expensive.
     */
    public LiveBoardPanel()
    {
        super();
        calendar = Calendar.getInstance();
        setDoubleBuffered(false);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(640,480));
    }

    @Override
    public boolean isOpaque()
    {
        return true;
    }

    /*
     * paints the liveboard into ourselves.
     */
    @Override
    protected void paintComponent(Graphics graphics)
    {
        // recalculate metrics first time or when resized
        if(oldSize==null || oldSize.getHeight()!=getHeight() || oldSize.getWidth()!=getWidth())
        {
            calculateMetrics(graphics);
            oldSize=new Dimension(getWidth(),getHeight());
        }
        
        drawBorders(graphics);

        if (liveBoard == null)
            return;

        drawTitle(graphics);    
        
        int line=0;
        for(ArrivalDeparture event : liveBoard.getArrivalsAndDepartures())
        {
            calendar.setTime(event.getDate());
            drawLine(graphics,event,line++);
            if(line>12)
                break;
        }

        for(;line<=12;line++)
            drawLine(graphics,null,line);   
    }

   
    /*
     * calculates various places to draw, and Fonts to use to fit all the info
     * on the current size
     */
    private void calculateMetrics(Graphics g2d)
    {
        FontMetrics metrics=null;
                
        width                   = getWidth();
        height                  = getHeight();
        borderThickness         = (height / NUM_LINES) / 4;
        imageableXPos           = borderThickness;
        imageableYPos           = borderThickness;
        imageableWidth          = width - (borderThickness * 2);
        imageableHeight         = height - (borderThickness * 3);
        lineHeight              = imageableHeight / NUM_LINES;
        dataYPos                = imageableYPos+(lineHeight * 2) + borderThickness;
        
        titleImageableHeight    = (lineHeight*2)-(lineHeight/16);
        lineImageableHeight     = lineHeight-(lineHeight/32);
        
        titleFont=fontThatFits(g2d, imageableWidth, titleImageableHeight, CHARS_IN_TITLE);
        if(titleFont!=null)
        {
            metrics=g2d.getFontMetrics(titleFont);
            titleBaselineOffset=metrics.getAscent()+(titleImageableHeight-metrics.getHeight())/2;
        }

        lineFont=fontThatFits(g2d, imageableWidth, lineImageableHeight, CHARS_IN_LINE);
        if(lineFont!=null)
        {
            metrics=g2d.getFontMetrics(lineFont);
            lineBaselineOffset=metrics.getAscent()+(lineImageableHeight-metrics.getHeight())/2;
        }

        if(metrics!=null)
        {
            lineColumns=new int[5];
            lineColumns[TIME_COLUMN]=imageableXPos+metrics.getMaxAdvance();
            lineColumns[STATION_COLUMN]=lineColumns[TIME_COLUMN]+(6*metrics.getMaxAdvance());
            lineColumns[TYPE_COLUMN]=lineColumns[STATION_COLUMN]+((MAX_CHARS_IN_STATION+1)*metrics.getMaxAdvance());
            lineColumns[PLATFORM_COLUMN]=lineColumns[TYPE_COLUMN]+(4*metrics.getMaxAdvance());
            lineColumns[DELAY_COLUMN]=lineColumns[PLATFORM_COLUMN]+(4*metrics.getMaxAdvance());
        }
    }

    // draw border and separator between title and lines
    private void drawBorders(Graphics g2d)
    {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, width, borderThickness);
        g2d.fillRect(0, height-(borderThickness*2), width, (borderThickness*2));

        g2d.fillRect(0, 0, borderThickness, height);
        g2d.fillRect(width-borderThickness, 0, borderThickness, height);

        g2d.fillRect(0,imageableYPos+(2*lineHeight),width,borderThickness);

    }

    // draw title at the top, displays timestamp and station name
    private void drawTitle(Graphics graphics)
    {
        graphics.setColor(HEADER_COLOR);
        graphics.fillRect(imageableXPos, imageableYPos, imageableWidth, lineHeight * 2);
        if(titleFont!=null)
        {
            graphics.setFont(titleFont);
            graphics.setColor(Color.white);
            calendar.setTime(liveBoard.getTimeStamp());
            graphics.drawString(String.format("%1$2d:%2$02d (%3$s)",calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),liveBoard.getStation().getName()),
                    lineColumns[TIME_COLUMN],imageableYPos+titleBaselineOffset);
        }
    }

    // draw one departure line: time, station name, vehicle type, platform, and optionally delay
    // will vehicles that are supposed to to departed are displayed in light gray
    private void drawLine(Graphics graphics, ArrivalDeparture departure, int line)
    {
        graphics.setColor(line%2==0?LINE_COLOR:BACKGROUND_COLOR);
        graphics.fillRect(imageableXPos, dataYPos + (line * lineHeight), imageableWidth, lineHeight);
        
        if(departure!=null && lineFont!=null)
        {
            calendar.setTime(departure.getDate());

            graphics.setFont(lineFont);

            // if departure time + delay is past, display in grey to show probably left station
            if((liveBoard.getTimeStamp().getTime()/1000) > ((departure.getDate().getTime()/1000)+departure.getDelay()))
                graphics.setColor(Color.LIGHT_GRAY);
            else
                graphics.setColor(TEXT_COLOR);


            graphics.drawString(String.format("%1$2d:%2$02d",calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)),
                    lineColumns[TIME_COLUMN],dataYPos + lineBaselineOffset + (line * lineHeight));

            graphics.drawString((departure.getStation().getName().length()>MAX_CHARS_IN_STATION?departure.getStation().getName().substring(0, (MAX_CHARS_IN_STATION-2)) + "..":departure.getStation().getName()),
                    lineColumns[STATION_COLUMN],dataYPos + lineBaselineOffset + (line * lineHeight));

            graphics.drawString(String.format("%1$-3s",departure.getVehicle().getType()),
                    lineColumns[TYPE_COLUMN],dataYPos + lineBaselineOffset + (line * lineHeight));

            graphics.drawString(String.format("%1$2s",departure.getPlatform()),
                    lineColumns[PLATFORM_COLUMN],dataYPos + lineBaselineOffset + (line * lineHeight));

            if(departure.getDelay()>0)
            {
                graphics.setColor(Color.red);
                graphics.drawString(String.format("+%1$1dH%2$02d",(departure.getDelay()/60)/60,(departure.getDelay()/60)%60),
                    lineColumns[DELAY_COLUMN],dataYPos + lineBaselineOffset + (line * lineHeight));
            }
        }
    }

    // hack to find a Font instance that will fit the number of characters in our current size
    private Font fontThatFits(Graphics g2d, int width, int height,  int terminalWidth)
    {
        Font testFont, font = null;

        for (int size = MAX_FONT_SIZE; size > MIN_FONT_SIZE; size--)
        {
            testFont = new Font(FONT, Font.PLAIN, size);
            FontMetrics metrics = g2d.getFontMetrics(testFont);
            if ((metrics.getMaxAdvance() * terminalWidth) < width && metrics.getHeight() < height)
            {
                font = testFont;
                break;
            }
        }

        return font;
    }

    // called by a LiveboardController when a new Liveboard is available
    public void update(Observable observable, Object observed)
    {
        liveBoard = (Liveboard) observed;
        repaint();
    }
}
