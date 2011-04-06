package be.irail.api.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author frank@apsu.be
 */
public class JsonParser
{
    int     accumulatorPosition,level;
    char[]  readbuffer,accumulator;
    int     labelEnds,valueStarts,valueEnds;

    public JsonParser(int readBufferSize, int accumulatorSize)
    {
        init(readBufferSize, accumulatorSize);
    }

    public JsonParser(int readBufferSize)
    {
        init(readBufferSize,512);
    }

    public JsonParser()
    {
        init(8192,512);
    }
    
    private void init(int readBufferSize, int accumulatorSize)
    {
        readbuffer=new char[readBufferSize];
        accumulator=new char[accumulatorSize];
        accumulatorSize=0;
        level=0;
    }

    public void parse(InputStream inputStream, JsonDataHandler handler) throws IOException
    {         
         InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
         int charsRead=inputStreamReader.read(readbuffer);

         while(charsRead>0)
         {
             for(int i=0;i<charsRead;i++)
             {
                char kar=readbuffer[i];
                //System.err.print(kar);
                
                switch(kar)
                {
                    case '{':
                    case '[':
                        level++;
                        handler.open(level,accumulator,1,labelEnds-2);
                        labelEnds=0;
                        valueStarts=-1;
                        valueEnds=-1;
                        accumulatorPosition=0;
                        
                    break;

                    case '}':
                    case ']':  // VAL
                        if(valueStarts!=-1)
                        {
                            valueEnds=accumulatorPosition;
                            accumulatorPosition=0;
                            event(handler);
                        }

                        handler.close(level);
                        level--;
                        labelEnds=0;
                        valueStarts=-1;
                        valueEnds=-1;
                    break;

                    case ':':  // LAB
                        labelEnds=accumulatorPosition;
                        valueStarts=accumulatorPosition+1;
                    break;

                    case ',':   // VAL
                        if(valueStarts!=-1)
                        {
                            valueEnds=accumulatorPosition;
                            accumulatorPosition=0;
                            event(handler);
                        }
                    break;

                    default:
                    accumulator[accumulatorPosition++]=kar;
                    break;
                }
             }

             charsRead=inputStreamReader.read(readbuffer);
         }

        inputStreamReader.close();
    }

    private void event(JsonDataHandler handler)
    {
        if(valueEnds==-1)
            return;
        handler.data(level,accumulator,1,labelEnds-2,valueStarts,(valueEnds-valueStarts)-1);
    }
}
