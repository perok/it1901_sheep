package main;

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

public class WmsClientPanel extends JPanel
{	
       
    //SmartListBox slb = new SmartListBox();


    /**
     * List with Layers, that schould be schown
     */
    LayerList layerList  = new LayerList(); 


    /**
     * Tree with all Layers availible from Servers
     */
    LayerPanel layerTree = new LayerPanel();


    /**
     * Canvas with the Map
     */
    ZoomeableImageCanvas imgCanvas = new ZoomeableImageCanvas();


    /**
     * Status Bar on Bottom of the Frame
     */
    StatusBar statusBar = new StatusBar();
    

    /**
     * Control Panel for Zooming and Navigation
     */
    RequestControlPanel requestControl = new RequestControlPanel( layerList, imgCanvas, statusBar );


    Container contentPane;
    

    JFileChooser chooser = new JFileChooser();
    
    /**Construct the frame*/
    public WmsClientPanel()
    {
        try {
            init();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**Component initialization*/
    private void init() throws Exception {
    	System.out.println("Initializing panel");

        //Abhängigkeiten der einzelnen Komponenten
        imgCanvas.setZoomListener( requestControl );
        imgCanvas.setMoveListener( requestControl );
        layerTree.setLayerSelectionListener( layerList );
        layerList.getListModel().addListDataListener( requestControl );


        contentPane = this;
        contentPane.setLayout( new BorderLayout() );


        // Tool Bar
        JToolBar jToolBar = new JToolBar();


        //JButton jLoadButton = new JButton("RHELLA"); //new ImageIcon( this.getClass().getResource("loadFile.gif") ));
        //jLoadButton.setToolTipText("Load server");
       /* jLoadButton.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {  loadServer();}
            } );    */    
        
        
        //jToolBar.add(jLoadButton);
        //jToolBar.add( slb );
        jToolBar.add( Box.createHorizontalGlue() );

        //contentPane.add( jToolBar, BorderLayout.NORTH );        
        
        // Status Bar
        contentPane.add( statusBar, BorderLayout.SOUTH );                


        // Einbindung der Layer komponenten
        JSplitPane layerSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, layerList, layerTree );
        layerSplitPane.setDividerSize( 4 );
        layerSplitPane.setDividerLocation( 100 );
        
        
        // rechter Panel mit requestControl und imgCanvas
        JPanel rightPanel = new JPanel( new BorderLayout() );
        rightPanel.add( requestControl, BorderLayout.NORTH );
        
        JPanel mapPanel = new JPanel( new BorderLayout() );
        mapPanel.setBorder(new BevelBorder( BevelBorder.LOWERED));
        mapPanel.add(imgCanvas, BorderLayout.CENTER );
        
        JPanel borderPanel = new JPanel( new BorderLayout() );
        borderPanel.setBorder(new EtchedBorder());
        borderPanel.add( mapPanel, BorderLayout.CENTER );
        
        rightPanel.add( borderPanel, BorderLayout.CENTER );
        
     	JSplitPane mainPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, layerSplitPane, rightPanel );
        
		mainPanel.setDividerSize( 4 );
	 	mainPanel.setDividerLocation( 270 );
        
        contentPane.add( mainPanel, BorderLayout.CENTER  );

        setStatusMessage ( "Ready" );  
        
        //Starter lasting av kartet
        GetCapabilitiesWorker worker = new GetCapabilitiesWorker("http://openwms.statkart.no/skwms1/wms.kartdata2?request=GetCapabilities&service=wms&version=1.1.1", layerTree, statusBar );
        worker.start();  
    }
    /*
    public void loadServer() {
    	System.out.println("WmsClientPanel: loadServer");
        String url = (String)slb.getSelectedUrl();
        System.out.println("trololo" + url);
        GetCapabilitiesWorker worker = new GetCapabilitiesWorker( url, layerTree, statusBar );
        worker.start();        
    }   */    

    public void setStatusMessage ( String message ) {
    	System.out.println(message);
        statusBar.setMessage( "Statusbar message:" + message );
    }

}
