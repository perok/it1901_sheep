
package wmsClient.mapCanvas;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.io.*;
import java.lang.*;

import wmsClient.gui.*;

import wmsClient.layerTree.*;
 import wmsClient.layerList.*;

public class LayerLoader extends Thread
{
	/**
	 * Dieser layerPanel muss beim ersten mal initialisiert werden.
	 */
	static protected LayerPanel layerPanel = null;
	
	private Image _img;
	
	private int _index;

	private LayerInformation _info;
	private WmsImageCanvas   _canvas;
	private RequestParameter _reqParam;
	private StatusBar _statusBar;
	
    public LayerLoader( LayerInformation info, WmsImageCanvas canvas, RequestParameter reqParam, int index, StatusBar statusBar )
    {
        _statusBar = statusBar;
    	_canvas   = canvas;
    	_info     = info;
    	_reqParam = reqParam;
    	_index    = index;
    }

    public void run()
    {
	try {
	    float f[] = _info.getLatLonBoundingBox();
	    String s = new String(_info.getServerInformation().getGetMapURL() );
	    StringBuffer url = new StringBuffer( s );
	    url.append( s.indexOf("?") == -1 ? "?" : "&");
	    url.append("request=GetMap" );
	    url.append( "&styles=" ); 	// 7.2.3.4; MUST be present and
					// may be a null value for default
					// or must one from the list.
	    url.append( "&layers=" + _info.getField( "name" ) );            
	    url.append( "&bbox=" + new Float(_reqParam.getBoundingX1()).toString() );
	    url.append( "," + new Float(_reqParam.getBoundingY1()).toString() );
	    url.append( "," + new Float(_reqParam.getBoundingX2()).toString() );
	    url.append( "," + new Float(_reqParam.getBoundingY2()).toString() );
	    url.append( "&SRS=" + _reqParam.getSRS() );
	    url.append( "&TRANSPARENT=TRUE" );
	    url.append( "&width=" + _reqParam.getPixelWidth() + "&height=" + _reqParam.getPixelHeight() );
	    url.append( "&format=" + _reqParam.getImageFormat() );
	    url.append( "&version=1.1" );
	    
	    System.out.println( url.toString() );  
	    
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    _img = toolkit.getImage( new URL(url.toString()) );
	    
	    MediaTracker mt = new MediaTracker( _canvas );
	    mt.addImage( _img,  5 );
	    mt.waitForAll();

	    if ( _img == null || mt.isErrorAny() ) {
		System.out.println("Error on loading the layer: "+ _info.getField( "name" ) );
		_statusBar.setMessage("Error loading layer '"+ _info.getField( "name" ) +"'");
		_statusBar.incrementErrors();
	    } else {
		_statusBar.increment();

		_canvas.updateLayers( _img, _index, _reqParam.getPixelWidth(), _reqParam.getPixelHeight() );
		_canvas.repaint();		
		_statusBar.setMessage("Layer '"+ _info.getField( "name" ) +"' loaded.");
	    }
	}
        catch( InterruptedException ie ) {
	    _statusBar.setMessage("Loading layerinterrupted: "+ ie);
	    ie.printStackTrace();
	    
	} catch( MalformedURLException mue ) {
	    _statusBar.setMessage("Error loading layer :"+ mue);
	    mue.printStackTrace();
	    
	} catch (  Exception e ) {
	    System.out.println("Error on loading the layer: "+ _info.getField( "name" ) + ":\n"+ e);
	    _statusBar.setMessage("Error loading layer '"+ _info.getField( "name" ) + "': "+ e);
	}
//	_statusBar.setMessage("All Layers loaded.");
	_reqParam.submitDone();
    }
}
