package be.irail.api.json;

/**
 * @author frank@apsu.be
 */
public class NumericUtil
{
    public static double decimalCharsToDouble(char[] data, int start, int length)
    {
        double result=0.0;
        double divisor=1.0;
        int numpos=0;

        for(int i=0;i<length;i++)
        {
           char kar=data[start+i];
           if(kar>='0' && kar<='9')
           {
                result+=((kar-'0')/Math.pow(10.0, numpos));
                numpos++;
           }
           else if(kar=='.')
           {
                if(divisor!=1.0)
                    throw new NumberFormatException("Multiple Decimal Points");
                else if(i==(length-1))
                    throw new NumberFormatException("Decimal Point In Final Position");
                divisor=Math.pow(10.0,i-1);
           }
           else
           {
                throw new NumberFormatException("Illegal Character");
           }
        }

        return result*divisor;
    }

    public static long decimalCharsToLong(char[] data, int start, int length)
    {
        long result=0;
        long multiplier=1;
                
        for(int i=(length-1);i>=0;i--)
        {
           char kar=data[start+i];
           if(kar>='0' && kar<='9')
           {
                result+=((kar-'0')*multiplier);
                multiplier*=10;
           }
           else
           {
                throw new NumberFormatException("Illegal Character");
           }
        }

        return result;
    }

    public static void main(String argv[])
    {
        char[] source="50.948316".toCharArray();
        double yield=decimalCharsToDouble(source,0,9);
        System.out.println(yield);

        char[] source1="123456789".toCharArray();
        long yield1=decimalCharsToLong(source1,0,9);
        System.out.println(yield1);
    }
}


/// 43.45433


