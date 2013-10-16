import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/* 
 * Class that creates the programs menus and handles menu events.
 */
public class mainmenuhandler implements ActionListener
{
  
    private MainGui frame;
    private JMenuBar menubar;
    private JMenu sniffermenu;

    /* class constructor */
    public mainmenuhandler(MainGui frm)
    {
        JMenuItem menuItem=null;       
        frame = frm;
        menubar = new JMenuBar();  
       sniffermenu = new JMenu("Packet Sniffing");
        menuItem = new JMenuItem("Start Logging Hex");
        menuItem.addActionListener(this);
        sniffermenu.add(menuItem);      
        menuItem = new JMenuItem("Start Logging Chars");
        menuItem.addActionListener(this);
        sniffermenu.add(menuItem);      
        menuItem = new JMenuItem("Stop Logging");
        menuItem.addActionListener(this);        
        sniffermenu.add(menuItem);            
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(this);        
        sniffermenu.add(menuItem);
        menubar.add(sniffermenu);        
        frame.setJMenuBar(menubar);
    }
    
    
    /* Function that handles menu events. */
    public void actionPerformed(ActionEvent e)
    {
        listdata lst=null;
        snifferthread tmpthrd = null;
   
        if(e.getActionCommand().equals("comboBoxChanged"))
        {
            frame.setType(frame.getComboBox().getSelectedIndex()-1);
        }
        else if(e.getActionCommand().equals("Start Logging Hex"))
        {
            if(frame.getType()==-1)
            {
                JOptionPane.showMessageDialog(frame, "Please select a network interface.", "No Network Interface Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.getModel().clear();
            frame.getTextArea2().updateUI();
            frame.getTextArea1().setText("");
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
            tmpthrd = new snifferthread(frame.getType(), frame.getModel(), true);
            frame.setsniffer(tmpthrd);
            tmpthrd.start();
        }
        else if(e.getActionCommand().equals("Start Logging Chars"))
        {
            if(frame.getType()==-1)
            {
                JOptionPane.showMessageDialog(frame, "Please select a network interface.", "No Network Interface Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.getModel().clear();
            frame.getTextArea2().updateUI();
            frame.getTextArea1().setText("");
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
            tmpthrd = new snifferthread(frame.getType(), frame.getModel(), false);
            frame.setsniffer(tmpthrd);
            tmpthrd.start();            
        }
        else if(e.getActionCommand().equals("Stop Logging"))
        {
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
        }
  
        else if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
      
    }
}
