import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import jpcap.*;

/*
 * Class that creates the main window for the program.
 */
public class MainGui extends JFrame implements MouseListener, ListSelectionListener, ActionListener
{
    /* getter and setter methods for private class variables */
    
    private snifferthread dt;
    public snifferthread getsniffer(){return dt;}
    public void setsniffer(snifferthread pdt){dt=pdt;}
    
    private DefaultListModel model;
    public DefaultListModel getModel(){ return model; }
    public void setModel(DefaultListModel dlm){ model = dlm; }
    
    private int type;
    public int getType(){ return type; }
    public void setType(int tprm){ type = tprm; }
    
    private JScrollPane jsp1;
    public JScrollPane getScrollPane1(){ return jsp1; }
    public void setScrollPane1(JScrollPane pjsp){ jsp1=pjsp; }
    
    private JScrollPane jsp2;
    public JScrollPane getScrollPane2(){ return jsp2; }
    public void setScrollPane2(JScrollPane pjsp){ jsp2=pjsp; }
    
    private JTextArea jt1;
    public JTextArea getTextArea1(){ return jt1; }
    public void setTextArea1(JTextArea pjt){ jt1=pjt; }
    
    private JList jt2;
    public JList getTextArea2(){ return jt2; }
    public void setTextArea2(JList pjt){ jt2=pjt; }
    
    private JComboBox nic;
    public JComboBox getComboBox(){ return nic; }
    public void setComboBox(JComboBox pjc){ nic=pjc; }
    
    private DefaultListModel nicmdl;
    public DefaultListModel getNicmdl(){ return nicmdl; }

    private mainmenuhandler mmh;
    public mainmenuhandler getMenuHandler(){ return mmh; }
    public void setMenuHandler(mainmenuhandler pmmh){ mmh=pmmh; }
    
    /* The class constructor */
    public MainGui()
    {
        java.net.URL imageURL=null;
        ImageIcon img=null;
        JMenuItem menuItem=null;
        dt = null;
        jt1=null;
        jt2=null;
        jsp1=null;
        jsp2=null;
        mmh=null;
        type=-1;
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        String[] interfaces = new String[devices.length+1];
        int i=0;        
        try 
        {   
            imageURL = MainGui.class.getResource("images/sourcecon.jpg");
            img = new ImageIcon(imageURL);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        interfaces[i++]=new String("Please select a network interface.");
        for(; i < devices.length+1; i++)
        {
            interfaces[i]=new String(i-1 + ": " + devices[i-1].name + "(" + devices[i-1].description + ")");
        }
        nic = new JComboBox(interfaces);
        nic.addActionListener(this);
        nic.setBorder(BorderFactory.createLoweredBevelBorder());
        nic.setMinimumSize(new Dimension(300,30));
        getContentPane().add(nic, BorderLayout.PAGE_START);
        jt1 = new JTextArea();
        jt1.addMouseListener(this);
        jt1.setLineWrap(true);
        jt1.setMinimumSize(new Dimension(150, 150));
        jsp1=new JScrollPane(jt1);
        model = new DefaultListModel();
        jt2 = new JList(model);
        jt2.addMouseListener(this);
        jt2.setMinimumSize(new Dimension(150, 50));
        jt2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jt2.addListSelectionListener(this);
        jsp2=new JScrollPane(jt2);
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp2, jsp1);
        getContentPane().add(sp, BorderLayout.CENTER);
        setIconImage(img.getImage());
        sp.setDividerLocation(100);
        mmh = new mainmenuhandler(this);
    }
    
    public void mouseClicked(MouseEvent e)
    {}
    
    public void mouseEntered(MouseEvent e)
    {}
    
    public void mouseExited(MouseEvent e)
    {}
    
    public void mousePressed(MouseEvent e)
    {}
    
    /* Method that responds to a right-click event. */
    public void mouseReleased(MouseEvent e)
    {}
    
    /* Method that responds to changes in the network interface combo box. */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("comboBoxChanged"))
        {
            type = nic.getSelectedIndex()-1;
        }
        jt2.requestFocusInWindow();    
    }
    
    /* Method that responds to changes in the packet listbox. */
    public void valueChanged(ListSelectionEvent e)
    {
        listdata tmp;
        if(e.getValueIsAdjusting()== false)
        {
            if(jt2.getSelectedIndex()!=-1)
            {
                tmp=(listdata)jt2.getSelectedValue();
                jt1.setText(tmp.data);
                jt1.setCaretPosition(0);
            }
        }
    }
	public static void main(String s[])
	{
		MainGui m=new MainGui();
		m.setVisible(true);
	}
}