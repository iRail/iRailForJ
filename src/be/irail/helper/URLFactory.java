package be.irail.helper;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author frank@apsu.be
 */
public class URLFactory
{
    private String          baseURL;
    private String          file;
    private StringBuffer    query;

    public URLFactory(String baseURL, String file)
    {
        this.baseURL=baseURL;
        this.file=file;
        query=new StringBuffer();
    }

    public URLFactory addQuery(String label, String value)
    {
        if(query.length()!=0)
            query.append("&");
        query.append(label);
        query.append("=");
        query.append(value);
        return this;
    }

    public URL getURL() throws MalformedURLException
    {
        StringBuilder variablePart=new StringBuilder(file);
        if(query.length()>0)
        {
            variablePart.append("?");
            variablePart.append(query);
        }

        System.err.println(variablePart.toString());
        
        return new URL(new URL(baseURL),variablePart.toString());
    }
}
