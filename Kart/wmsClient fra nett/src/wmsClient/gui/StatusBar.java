package wmsClient.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;


public class StatusBar extends JPanel {
    
    int min = 0;
    int max = 10;

    int pos;
    int errors;
    String message;
    boolean isLoading = false;
    
    JTextField messageField;
    JTextField counterField;
    JProgressBar progressBar;

    public StatusBar() {
	setLayout( new BorderLayout() );

	messageField = new JTextField( 40 );
	progressBar = new JProgressBar( min, max );
	counterField = new JTextField( 12 ); 	

	messageField.setEditable( false );
	counterField.setEditable( false );

	JPanel panel = new JPanel( new GridLayout( 1,2 ) );
	panel.add( progressBar );
	panel.add( counterField );
	add( messageField, BorderLayout.CENTER );
	add( panel, BorderLayout.EAST );
    }
    
    public void clear(  ) {
        message = "";
        pos = min;
        errors = 0;
        update();
    }

    public void setMessage( String newMessage )   {
        message = newMessage;
        update();	
    }

    public void setMax( int newMax ) {
        max = newMax;
        progressBar.setMaximum( max );
        update();	
    }

    public void setPos( int newPos ) {
        pos = newPos;
        update();	
    }

    public void increment( ) {
        pos++;
        update();	
    }

    public void incrementErrors( ) {
        errors++;
        update();	
    }
    

    public void setIsLoading( boolean loading ) {
        isLoading = loading;
        update();
    }

    
    public void update() {
        
        messageField.setText( " "+ message );
        
        if ( isLoading ) {
            progressBar.setValue( pos );	
            if ( pos+errors < max ) {
                if ( errors > 0 )
                    counterField.setText( " "+ pos +"/"+ max +" loaded. "+ errors + " errors." );
                else
                    counterField.setText( " "+ pos +"/"+ max +" loaded." );
            }
            else {
                if ( errors > 0 )
                    counterField.setText( " All loaded. "+ errors + " errors." );
                else
                    counterField.setText( " All loaded." );
            }
        } else {
            progressBar.setValue( max );
            counterField.setText( "" );
        }
        
    }    
}
