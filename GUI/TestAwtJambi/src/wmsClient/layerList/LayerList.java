package wmsClient.layerList;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import wmsClient.layerTree.*;


/**
 * Dies ist eine Klasse zur Repräsentation von der Layerliste
*/
public class LayerList extends JPanel 
    implements ActionListener, LayerSelectionListener {
  
    protected LayerListModel listModel;
    protected JList theList;    

    /*
    protected JButton upButton = new JButton("Up");
    protected JButton downButton = new JButton("Down");
    protected JButton removeButton = new JButton("Delete");
    protected JButton clearButton = new JButton("Clear");*/

    public LayerList( ) {
        super();
        
        listModel = new LayerListModel();
        theList = new JList( (ListModel)listModel );
        
        /*
        upButton.addActionListener( this );
        downButton.addActionListener( this );
        removeButton.addActionListener( this );
        clearButton.addActionListener( this );*/
        
        makeLayout();
    }

    public ListModel getListModel() {
        return listModel;
    }

    /**
     * Hinzufügen eines Layers in die Liste
     * Implementiert für LayerSelectionListener
     */
    public void layerSelected( LayerInformation layerInfo ) {
    	System.out.println("Layerlist --> LayerSelected");
        if ( ! listModel.contains( layerInfo ) ) 
            listModel.add( 0, layerInfo );
        
        listModel.fireListDataListenerContentsChanged();
    }


    /**
     * Gibt eine Enumeration aller in der Liste befindlicher 'LayerInformation Objekte' zurück
     */
    public Enumeration getLayers() {
        return listModel.elements();
    }

    // Minimal bounds.
    //
    public float[] getLayerBounds() {
        float b[] = { -Float.MAX_VALUE,-Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE };
        return getLayerBounds(b);
    }

    public float[] getLayerBounds(float x1, float y1, float x2, float y2) {
        float b[] = {x1,y2,x2,y2};
        return getLayerBounds(b);
    }

    // Defer to funky interal presentation.
    //
    public float[] getLayerBounds(float b[]) {
        Enumeration x = getLayers();

        if (b[0] > b[2]) { float f = b[0]; b[0]=b[2]; b[2]=f; };
        if (b[1] > b[3]) { float f = b[1]; b[1]=b[3]; b[3]=f; };
        
        while( x.hasMoreElements() ) {     
            LayerInformation current = (LayerInformation)x.nextElement();
            
            float[] latLin = current.getLatLonBoundingBox();
            
            if ( latLin[0] > b[0]) b[0] = latLin[0]; // x1
            if ( latLin[1] > b[1]) b[1] = latLin[1]; // y1
            if ( latLin[2] < b[2]) b[2] = latLin[2]; // x2 
            if ( latLin[3] < b[3]) b[3] = latLin[3]; // y2
        }
        return b;
    }

    protected void makeLayout() {
	
        setLayout( new BorderLayout() );
        
        //JPanel buttons = new JPanel( new GridLayout(1,3) ) ;
        Box buttons = Box.createHorizontalBox();
        /*
        buttons.add( upButton );
        buttons.add( downButton );
        buttons.add( removeButton );
        buttons.add( clearButton );*/
        
        //add( new Label("Layer Auswahl"), BorderLayout.NORTH );
        add( new JScrollPane( theList ), BorderLayout.CENTER );
        JPanel  buttonFrame = new JPanel( new BorderLayout() );
        buttonFrame.add( buttons, BorderLayout.NORTH );
        buttonFrame.add( Box.createVerticalGlue(), BorderLayout.CENTER );
        add( buttonFrame, BorderLayout.SOUTH );
        
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
    /*
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        
        if (obj == clearButton) {
            
            listModel.clear( );
            listModel.fireListDataListenerContentsChanged();
            return;            
	} 

        int index = theList.getSelectedIndex();
        if ( index < 0 || index > listModel.size() )
            return;
        
        if (obj == upButton) {
            if ( index < 1 )
                return;
            Object oldPrev = listModel.remove( index-1 );
            listModel.add( index, oldPrev );
            theList.setSelectedIndex( index-1 );
        } else if (obj == downButton) {
            if ( ! (index < listModel.size()-1) )
                return;
            Object curr = listModel.remove( index );
            listModel.add( index+1, curr );
            theList.setSelectedIndex( index+1 );
        } else if (obj == removeButton) {
            
            Object[] entries = theList.getSelectedValues();
            for ( int i=0; i<entries.length; i++)
                listModel.remove( entries[i] );
            
            theList.setSelectedIndex( index-1 );
            if ( theList.getSelectedIndex() < 0 )
                theList.setSelectedIndex( listModel.size()-1 );	    
            
        } 
        
        listModel.fireListDataListenerContentsChanged();
    }   */

}
