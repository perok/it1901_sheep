package wmsClient.mapCanvas;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import java.net.*;
import java.io.*;
import java.util.*;

import wmsClient.layerList.*;

public class ZoomeableImageCanvas extends WmsImageCanvas
    implements MouseListener, MouseMotionListener { 

    int topLeftX;
    int topLeftY;
    int bottomRightX;
    int bottomRightY;

    int mouseFirstX, mouseFirstY;
    int mouseLastX, mouseLastY;

    boolean dragging = false;

    ZoomListener zoomListener;
    MoveListener moveListener;

    int squareBreadth = 40;
    int currentMouseSquareX = -1; // -1=outside, 0=west, 1=center, 2=east
    int currentMouseSquareY = -1; // -1=outside, 0=north, 1=center, 2=south

    public  ZoomeableImageCanvas() {
        super();
        addMouseListener( this );
        addMouseMotionListener( this );
        dragging = false;
    }


    public void setMoveListener( MoveListener listener ) {
        moveListener = listener;
    }


    public void setZoomListener(ZoomListener listener) {
        zoomListener = listener;
    }

    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mousePressed(MouseEvent e) {
        setBBX(e.getX(),e.getY());
    }

    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseReleased(MouseEvent e) {
        dragging = false;
        updateBBX(e.getX(),e.getY());

        if ( Math.abs( topLeftX-bottomRightX ) < 5
             && Math.abs( topLeftY-bottomRightY) < 5 ) {
            moveListener.moveRelative( (currentMouseSquareX-1)*0.6f,  (currentMouseSquareY-1)*0.6f );
        } else if ( topLeftX < bottomRightX && topLeftY < bottomRightY ) {
            zoomListener.setZoomFrame( topLeftX, topLeftY,bottomRightX, bottomRightY );
        }
        else
            JOptionPane.showMessageDialog( this, "The zoom square must bee from the left upper to the right under corner." );
    }
    
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseEntered(MouseEvent e) {
        if(dragging) repaint();
        dragging = false;
    }
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseExited(MouseEvent e) {
        dragging = false;
        currentMouseSquareX = currentMouseSquareY = -1;
        repaint();
    }

    private void setBBX(int x, int y)
    {
        topLeftX = bottomRightX = mouseLastX = mouseFirstX = x;
        topLeftY = bottomRightY = mouseLastY = mouseFirstY = y;
    }

    private void updateBBX(int x, int y) {
        mouseLastX = x;
        mouseLastY = y;

        topLeftX     =  (mouseLastX < mouseFirstX) ? mouseLastX : mouseFirstX;
        topLeftY     =  (mouseLastY < mouseFirstY) ? mouseLastY : mouseFirstY;
        bottomRightX =  (mouseLastX < mouseFirstX) ? mouseFirstX : mouseLastX;
        bottomRightY =  (mouseLastY < mouseFirstY) ? mouseFirstY : mouseLastY;		
    }

    public void mouseDragged(MouseEvent e) {	
        updateBBX(e.getX(),e.getY());
        
        dragging = true;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        dragging = false;
        int w = (int) getSize().getWidth();
        int h = (int)getSize().getHeight();
        
        updateBBX(e.getX(),e.getY());
        
        //int xPos = bottomRightX;
        //int yPos = bottomRightY;
        
        int xPos = e.getX();
        int yPos = e.getY();
        int newX = -1;
        int newY = -1;
        
        if ( yPos <= squareBreadth ) 
            newY = 0;
        else if ( yPos >= h-squareBreadth ) 
            newY = 2;
        else 
            newY = 1;
        
        // 	System.out.println("yPos >= h-squareBreadth"+ yPos +" >= "+ (h-squareBreadth));
        
        if ( xPos <= squareBreadth ) 
            newX = 0;
        else if ( xPos >= w-squareBreadth ) 
            newX = 2;
        else 
            newX = 1;
        
        if ( currentMouseSquareX != newX || currentMouseSquareY != newY ) {
            currentMouseSquareX = newX;
            currentMouseSquareY = newY;
            repaint();
            //  	    System.out.println("mouseMoved: "+ newY + ", "+ newX );
        }        
    }

    
    public void paint( Graphics g )
    {
        super.paint( g );
        //System.out.println( currentMouseSquareX +" != -1 && "+ currentMouseSquareY +" != -1 && ( "+ currentMouseSquareX +" != 1 || "+ currentMouseSquareY +" != 1 ) )" );
        
        
        if ( ! dragging && currentMouseSquareX != -1 && currentMouseSquareY != -1 && ( currentMouseSquareX != 1 || currentMouseSquareY != 1 ) ) {
            int w = (int) getSize().getWidth()-1;
            int h = (int)getSize().getHeight()-1;
            
            int width = squareBreadth;
            int height = squareBreadth;
            
            int xPos = 0;
            if ( currentMouseSquareX == 1 ) {
                xPos = squareBreadth;
                width = w-squareBreadth*2;
            } else if ( currentMouseSquareX == 2 ) {
                xPos = w-squareBreadth;
            }
            
            int yPos = 0;
            if ( currentMouseSquareY == 1 ) {
                yPos = squareBreadth;
                height = h-squareBreadth*2;
            } else if ( currentMouseSquareY == 2 ) {
                yPos = h-squareBreadth;
            }
            
            // 	     System.out.println("xPos="+xPos +" yPos="+yPos);
            //	     System.out.println("width="+width +" height="+height);	     
            
            // 	     g.setColor( new Color( 0.5f, 0f, 0f, 0.25f ) );
            float[] bg = new float[3];
            getBackground().getRGBColorComponents(bg);
            g.setColor( new Color( bg[0], bg[1], bg[2], 0.5f)  );
            g.fillRect( xPos, yPos,  width, height );
            g.setColor( new Color( 0f, 0f, 0f, 1f ) );	
            g.drawRect( xPos, yPos,  width, height );
            
            if ( width > squareBreadth ) {
                for ( int i=width/4; i+squareBreadth/2<width; i+=width/4 )
                    drawArrow( g, i+squareBreadth/2+1, yPos+1, currentMouseSquareX, currentMouseSquareY);
            } else if ( height > squareBreadth ) {
                for ( int i=height/4; i+squareBreadth/2<height; i+=height/4 )
                    drawArrow( g, xPos+1, i+squareBreadth/2+1, currentMouseSquareX, currentMouseSquareY);
            } else
                drawArrow( g, xPos+1, yPos+1, currentMouseSquareX, currentMouseSquareY);
        }
        
        if (dragging) {
	    	int w = (int) getSize().getWidth()-1;
	    	int h = (int)getSize().getHeight()-1;
	    	float[] bg = new float[3];
            getBackground().getRGBColorComponents(bg);
            g.setColor( new Color( bg[0], bg[1], bg[2], 0.8f)  );
            // Assume mousePress < mouseLastSeen
	    	g.fillRect( 0,0,w,topLeftY );
	    	g.fillRect( 0,topLeftY,topLeftX,bottomRightY-topLeftY);
	    	g.fillRect( bottomRightX,topLeftY,w-bottomRightX,bottomRightY-topLeftY);
	    	g.fillRect( 0,bottomRightY,w,h-bottomRightY);
	    	// g.fillRect( topLeftX, topLeftY, bottomRightX - topLeftX, bottomRightY - topLeftY);
	    	g.setColor( new Color( 0f, 0f, 0f, 1f ) );	
	    	g.drawRect( topLeftX, topLeftY, bottomRightX - topLeftX, bottomRightY - topLeftY);
            
            g.drawLine(0,topLeftY, topLeftX-8,topLeftY);
            g.drawLine(bottomRightX+8 ,topLeftY, w,topLeftY);
            g.drawLine(0,bottomRightY, topLeftX-8,bottomRightY);
            g.drawLine(bottomRightX+8 ,bottomRightY, w,bottomRightY);
            g.drawLine(topLeftX,0,topLeftX,topLeftY-8);
            g.drawLine(bottomRightX,0,bottomRightX,topLeftY-8);
            g.drawLine(topLeftX,bottomRightY+8,topLeftX,h);
            g.drawLine(bottomRightX,bottomRightY+8,bottomRightX,h);
        }
    }
    
    public void drawArrow( Graphics graphics, int xPos, int yPos, int directionX, int directionY ) {
        Graphics2D g = (Graphics2D)graphics;	
        double angle = (Math.PI/4) * (directionX+directionY);
        if ( directionX < directionY )
            angle *= -1;
        angle += 3*Math.PI/4;
        
        AffineTransform t = new AffineTransform();
       	t.translate( xPos, yPos );
        t.rotate( angle, (squareBreadth-2)/2, (squareBreadth-2)/2 );
        g.setTransform( t );
        
        int offset = (squareBreadth-2)/8;
        g.drawLine( squareBreadth/2-4, 2+offset, squareBreadth/2-4, squareBreadth/2+offset );
        g.drawLine( squareBreadth/2+4, 2+offset, squareBreadth/2+4, squareBreadth/2+offset );
        g.drawLine( squareBreadth/2-8, squareBreadth/2-4+offset, squareBreadth/2, 3*squareBreadth/4+offset );
        g.drawLine( squareBreadth/2+8, squareBreadth/2-4+offset, squareBreadth/2, 3*squareBreadth/4+offset );       
        
        g.setTransform( new AffineTransform() );
    }

}

