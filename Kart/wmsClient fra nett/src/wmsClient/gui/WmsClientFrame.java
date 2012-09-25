package wmsClient.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.net.*;
import java.io.*;
import java.util.*;

import wmsClient.layerTree.*;
import wmsClient.layerList.*;
import wmsClient.mapCanvas.*;

public class WmsClientFrame
{	

    WmsClientPanel mainPanel;
    JFrame frame;
    
    
    /**Construct the frame*/
    public WmsClientFrame()
    {
        try {
            init();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**Component initialization*/
    private void init() throws Exception {
    

        frame = new JFrame();
        frame.setIconImage( (new ImageIcon( this.getClass().getResource("programIcon.gif") )).getImage() );

        //contentPane.setLayout(borderLayout1);
        frame.setSize(new Dimension(900, 600));

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
       
        frame.setTitle("wms client v1.2");

        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    exit();
                }
            });


        mainPanel = new WmsClientPanel();

        // Menu Bar
        frame.setJMenuBar( getMenuBar() );
        frame.getContentPane().add( mainPanel, BorderLayout.CENTER  );        
    }


    public void show() {
        frame.setVisible(true);
    }


    protected JMenuBar getMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Der gleiche Bezeichner wird für alle Benutzt.
        JMenu currentMenu;
        JMenuItem currentMenuItem;
        
        //File
        currentMenu = menuBar.add( new JMenu("File") );
        currentMenu.setMnemonic('f');
        
        currentMenuItem = currentMenu.add( new JMenuItem("Open Server", 'o') );
        currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        currentMenuItem.addActionListener( 
                                          new ActionListener() {
                                              public void actionPerformed(ActionEvent e)
                                              {  mainPanel.openServer();  }
                                          } );
        
        currentMenuItem = currentMenu.add( new JMenuItem("Load Server", 'l') );
        currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
        currentMenuItem.addActionListener( 
                                          new ActionListener() {
                                              public void actionPerformed(ActionEvent e)
                                              {  mainPanel.loadServer();  }
                                          } );
                
        currentMenuItem = currentMenu.add( new JMenuItem("Exit", 'x') );
        currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        currentMenuItem.addActionListener( 
                                          new ActionListener() {
                                              public void actionPerformed(ActionEvent e)
                                              {  exit(); }
                                          } );       

        
        //Help
        currentMenu = menuBar.add( new JMenu("Help", true) );
        currentMenu.setMnemonic('h');                   
        
        currentMenuItem = currentMenu.add( new JMenuItem("About", 'a') );
        currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK ));
        currentMenuItem.addActionListener(  
                                          new ActionListener() {
                                              public void actionPerformed(ActionEvent e)
                                              {  openAboutBox(); }
                                          } );
        return menuBar;
    }

    /**File | Exit action performed*/
    public void exit()
    {
        System.exit(0);
    }

    /**Help | About action performed*/
    public void openAboutBox()
    {
        AboutBox dlg = new AboutBox(frame);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = frame.getSize();
        Point loc = frame.getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.show();
    }    
}
