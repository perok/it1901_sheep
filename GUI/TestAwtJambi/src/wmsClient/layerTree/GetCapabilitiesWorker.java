
package wmsClient.layerTree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.io.*;
import java.lang.*;

//import wmsClient.gui.*;
import main.StatusBar;

import wmsClient.layerTree.*;
import wmsClient.layerList.*;


public class GetCapabilitiesWorker extends Thread
{
    protected String _url;
    protected LayerPanel _list;
    protected StatusBar _statusBar;

	
    static LayerPanel _layerPanel = null;
	
    public GetCapabilitiesWorker( String url, LayerPanel list, StatusBar statusBar )
    {
        _statusBar = statusBar;
    	_url  = url;
    	_list = list;
    }

    public void run()
    {
    	try
            {
                _statusBar.clear();
                _statusBar.setMessage( "Loading Capabilities: "+ _url );
                
                // URLConnection aufmachen und 
                URL getcap = new URL((String)_url);
                URLConnection getcapConn = getcap.openConnection();
                
                //LayerPanel panel = new LayerPanel( getcapConn.getInputStream() );
                _list.displayCaps( getcapConn.getInputStream() );
                _list.setVisible( true );
                
                _statusBar.setMessage( "Capabilities loadet." );
            }
        catch( java.net.MalformedURLException mue )
            {
                _statusBar.setMessage( "Loading error: "+ mue );
                mue.printStackTrace();
            }
        catch( IOException ioe )
            {
                _statusBar.setMessage( "Loading error: "+ ioe );
                ioe.printStackTrace();
            }
        catch( Exception e )
            {
                _statusBar.setMessage( "Loading error: "+ e );
                System.err.println("Error on loading the Server Capabilities: ");	    
                e.printStackTrace();
            }
    }
}
