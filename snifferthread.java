import jpcap.*;
import javax.swing.*;

/*
 * Class that implements the background thread in which the sniffing is done.
 */

public class snifferthread extends Thread
{
    private int ldevice;
    private DefaultListModel ljep;
    private boolean type;
    private NetworkInterface[] devices;
    private JpcapCaptor jpcap;
    
    /* Constructor*/
    public snifferthread(int device, DefaultListModel jep, boolean type)
    {
        ldevice = device;
        ljep = jep;
        this.type = type;
    }
    
    /*
     * Kills the infinite loop that the pcap library goes into while sniffing packets.
     * I declare this method syncronized to force java to treat it as a critical section 
     * (forces only one thread at a time to be able to access it).
     */
    public synchronized void stopthread()
    {
        if(jpcap!=null)
        {
            jpcap.breakLoop();
        }
    }
    
    /* The threads run method (where all the action happens).*/
    public void run()
    {
        try
        {
            devices = JpcapCaptor.getDeviceList();
            jpcap = JpcapCaptor.openDevice(devices[ldevice], 2000, false, 20);
            jpcap.loopPacket(-1,new Sniffer(ljep,type));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}