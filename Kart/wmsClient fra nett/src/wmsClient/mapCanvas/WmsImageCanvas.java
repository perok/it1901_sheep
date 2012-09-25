package wmsClient.mapCanvas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.io.*;
import java.util.*;

import wmsClient.gui.*;
import wmsClient.layerTree.*;

public class WmsImageCanvas extends JPanel 
{
    Hashtable htLayers  = new Hashtable();
    MoveListener moveListener;

	// Ddefault size; 400 x 300 -  which is fine on 
	// a normal screen. And Scrolling is there anyway.
	final int DEFWSIZE = 400;
	final int DEFHSIZE = 300;

	// No maps smaller than 100x100 pixels.
	final int MINWSIZE = 100;
	final int MINHSIZE = 100; 
    
    public WmsImageCanvas()
    {
        this.setLayout( new BorderLayout() );        	
    }
	
    public void initLayers()
    {
        htLayers.clear();
        this.repaint();	
    }
    
    public void updateLayers( Image img, int index, int w, int h )
    {	
        htLayers.put( new Integer(index), (Image)img );	    		
    }	
  
    public int getImageWidth(int d)
    {
    	int w = (int)getSize().getWidth();
        if (d == 0)
            d = DEFWSIZE;
        if (d < MINWSIZE)
            d = MINWSIZE;
        if (w<d)
            w = d;
        return w;
    }

    public int getImageHeight(int d)
    {
        int w = (int)getSize().getHeight();
        if (d == 0)
            d = DEFHSIZE;
        if (d < MINHSIZE)
            d = MINHSIZE;
        if (w<d)
            w = d;
	return (w);
    }
    
    public int getImageHeight()
    {
	return getImageHeight(0);
    }

    public int getImageWidth()
    {
	return getImageWidth(0);
    }

    public void update(Graphics g)
    {
	paint(g);
    }
    
    public void paint( Graphics g )
    {
        super.paint( g );
        g.clearRect( 0, 0,  (int)getSize().getWidth(), (int)getSize().getHeight() );
        
        for( int i=htLayers.size()-1; i>=0; i-- )
            {
                Image image = (Image)htLayers.get( new Integer(i) );
                if ( image != null )
                    g.drawImage( image, 0, 0, this );
                
            }
    }    	
}
