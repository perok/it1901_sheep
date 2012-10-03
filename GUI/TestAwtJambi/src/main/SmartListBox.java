package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
  * Diese Klasse stelle eine URL-Leiste wie bei Internet-Browsern, dar.
  */
public class SmartListBox extends JPanel implements ActionListener {
    
    JComboBox selecter = new JComboBox();
    Vector serverUrls = new Vector();
    Vector serverNames = new Vector();
    JButton newButton = new JButton( "Add Server" );
    int maxServers = 500;

    public SmartListBox() {	
        super( new FlowLayout( FlowLayout.LEFT, 10, 0 ) );
        
        // Die box erlaubt immer die direkte Eingabe von Text.
        this.addServer( null, (String)"http://ogc.intergraph.com/ows_wms/request.asp?request=GetCapabilities&version=1.1.12&%20service=WMS" );
        //      this.addServer( null, (String)"http://www.cubewerx.com/wmt/cubeserv/cubeserv.cgi?VERSION=1.1.0&REQUEST=GetCapabilities" );
        this.addServer( null, (String)"http://gisdata.usgs.net/servlet/com.esri.wms.Esrimap?servicename=USGS_WMS_REF&request=capabilities" );
        this.addServer( null, (String)"http://wms.i3.com/wms/wms8bitdemo?VERSION=1.1.0&REQUEST=GetCapabilities" );
        this.addServer( null, (String)"http://www.asemantics.com/mapserv/mapserv.cgi?VERSION=1.1.0&REQUEST=GetCapabilities&map=/usr/local/www/data/asemantics/mapserv/itasca/demo.map" );
        this.addServer( null, (String)"http://globe.digitalearth.gov/cgi-bin/wmt.cgi?request=capabilities" );
        this.addServer( null, (String)"http://www.cubewerx.com/demo/cubeserv/cubeserv.cgi?VERSION=1.1.2&REQUEST=GetCapabilities" );
        // Versteht nur 'map' als Type anstatt getMap
        //this.addServer( (String)"http://maps.intergraph.com/wms/london/Request.asp?request=capabilities" );
        
        add( selecter );
        
        add( newButton );
        newButton.addActionListener( this );
        
    }
    
    public void addServer( String name, String mapURL ) {
        if ( mapURL == null || "".equals( mapURL ) ) 
            return;
        
        if ( name == null || "".equals( name ) ) {
            int lPos = mapURL.indexOf( "://" )+3;
            int rPos = mapURL.indexOf( "/", lPos );
            name = mapURL.substring( lPos, rPos );
            int anz = 1;
            String sueffix = "";
            while ( serverNames.contains( name+sueffix ) ) {
                anz++;
                sueffix =  " <"+ anz +">";
            }
            name = name + sueffix;
        }
        serverNames.add( 0, name );
        serverUrls.add( 0, mapURL );
        if ( serverUrls.size() > maxServers ) {
            serverNames.setSize( maxServers );
            serverUrls.setSize( maxServers );
        }
        System.out.println("SmartListbox: Added to Server List");
        addItemToServerList( name );
    }
    
    public void addItemToServerList( String name ) {
        selecter.insertItemAt( 	"  "+name, 0 );
        selecter.setSelectedIndex( 0 );
        if ( selecter.getItemCount() > maxServers ) {
            selecter.removeItemAt( maxServers-1 );
        }
    }

    public String getSelectedUrl() {
        return (String)serverUrls.elementAt( selecter.getSelectedIndex() );
    }

    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if ( s == newButton ) {
            addServerDlg();
        }
    }


    public void addServerDlg() {
    	
        String newUrl = JOptionPane.showInputDialog(this,  "Server Url:"); 
        System.out.println("SmartListBox: Added url: " + newUrl);
        addServer( null, newUrl );
    }
}
