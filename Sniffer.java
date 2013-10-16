import jpcap.*;
import jpcap.packet.Packet;
import javax.swing.*;
import java.util.*;


public class Sniffer implements PacketReceiver
{
    private DefaultListModel ljep;
    private boolean displayhex;
    private int pknum;
    
    /* Constructor */
    public Sniffer(DefaultListModel jep, boolean hex)
    {
        ljep=jep;
        displayhex=hex;
        pknum = 0;
    }
    
    /* Returns a hex string representation of a byte value.*/
    String byteToHex(byte b) 
    {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(array);
    }

    /* Converts a byte array into a string.*/
    String bytesToString(byte[] array)
    {
        int x=0;
        StringBuffer str = new StringBuffer();
        if(displayhex == true)
        {
            for(int k = 0; k < array.length; k++) 
            {
                if(x<9)
                {
                    str.append(" 0x" + byteToHex(array[k]));
                    ++x;
                }
                else
                {
                    x=0;
                    str.append(" 0x" + byteToHex(array[k])+ "\r\n");
                }
            }
            return str.toString();
        }
        else
        {
            return new String(array);
        }
    }
    
    /* Function that is passed to packets by pcap.*/
    public void receivePacket(Packet packet) 
    {
        listdata tmp;
        if(packet.data.length>0)
        {
            tmp = new listdata();
            tmp.data = bytesToString(packet.data)+"\r\n";
            tmp.header = Integer.toString(pknum++) + " " + new Date().toString();
            ljep.add(0,tmp);
        }
    }
}