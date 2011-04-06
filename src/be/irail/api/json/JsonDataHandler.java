package be.irail.api.json;

/**
 *
 * @author frank@apsu.be
 */
public interface JsonDataHandler
{
    public void data    (int level, char[] data, int labelStarts, int labelSize, int valueStarts, int valueSize);
    public void open    (int level, char[] data, int labelStarts, int labelSize);
    public void close   (int level);
}
