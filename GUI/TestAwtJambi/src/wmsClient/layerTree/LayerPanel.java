package wmsClient.layerTree;

import wmsClient.layerList.*;


import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


/**
 * Diese Klasse Stellt einen Frame mit einer Repräsentation der Layer bereit.
 * 
 * Der Constructor muss einen InputStream mit einem XML-Dokument übergeben bekommen. 
 * Dises wird dann geparset und aus den Layern ein TreeObjekt erstellt.
 * 
 * Das mit setLayerSelectionListener() gesetzte Objekt wir jedesmal benachrichtigt, 
 * wenn der Benutzer auf einen Layer doppelt Clickt.
 * Bei der Benachrichtigung wird ein LayerInformation-Objekt übergeben, das den Namen,
 * sowie weitere Eigenschaften des Layers enthält.
 */
 
public class LayerPanel extends JPanel
    implements  MouseListener{

    protected JTree layerTree;
    protected Vector treeData;

    protected LayerSelectionListener selectionListener;
    protected JPanel infoPanel;
    protected JLabel infoLabel;
    protected JTextArea infoArea;
    

    /**
     * @param capabilities XML-Dokument einer GetCapabilitiesanfrage an einen WMS
     */
    public LayerPanel( ) {
        super( new BorderLayout() );
        treeData = new Vector();
        layerTree =  new JTree( treeData );
        
        makeLayout();
    }


    public void displayCaps( java.io.InputStream capabilities ) {
	
    	//HER BLIR LAYERENE HENTA UT
        CapabilitiesParser parser = new CapabilitiesParser( capabilities );
	    
        Vector constructedDataTree =  parser.constructDataTree();
        if ( constructedDataTree == null ) {
            JOptionPane.showMessageDialog( this, "Error on parsing the server description." );        
            return;
        }
        
        
        treeData.add( constructedDataTree.elementAt(0) );
        
        
        // Sicher nicht so schön, immern einen neuen Tree zu bauen,
        // der dann gar nicht benutzt wird, funzt aber!
        layerTree.setModel(  (new JTree( treeData )).getModel() );
    }

    /**
     * Setzt ein Objekt, das jedesmal benachrichtigt, 
     * wenn der Benutzer auf einen Layer doppelt Clickt.
     * Bei der Benachrichtigung wird ein LayerInformation-Objekt übergeben, das den Namen,
     * sowie weitere Eigenschaften des Layers enthält.     
     */    
    public void setLayerSelectionListener(LayerSelectionListener listener) {
        selectionListener = listener;
    }
    
    protected void makeLayout() {
        
        layerTree.addMouseListener( this );
        layerTree.expandRow( 0 );
        
        infoPanel = new JPanel(new BorderLayout());
        infoLabel = new JLabel( "Layerinfos:" );
        infoArea = new JTextArea();
        infoArea.setRows(4);
        infoArea.setEditable( false );
        infoArea.setBackground( new Color( 150, 150, 150 ) );
        infoPanel.add( infoLabel, BorderLayout.NORTH);
        infoPanel.add( new JScrollPane( infoArea ) , BorderLayout.CENTER);
        
        JSplitPane mainPanel = new JSplitPane( JSplitPane.VERTICAL_SPLIT, new JScrollPane( layerTree ), infoPanel);
        mainPanel.setDividerSize( 4 );
        mainPanel.setDividerLocation( 250 );
        
        add( mainPanel );
    }


    
    
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mousePressed(MouseEvent e) {
        TreePath selPath = layerTree.getPathForLocation(e.getX(), e.getY());
        if( selPath != null  ) {	        
            TreeNodeInterface node = ((TreeNodeInterface)((DefaultMutableTreeNode)selPath.getPathComponent(selPath.getPathCount()-1)).getUserObject());
            LayerInformation info = node.getLayerInformation();
            
            infoLabel.setText("Layer: "+ info);
            if ( e.getClickCount() == 2 && (selectionListener != null)) {
                if ( info.getLatLonBoundingBox() == null  || info.getField("name") == null ) 
                    infoArea.setText("Can´t add Layer.\nThe Layer is only for formatimg.\n\nOr there ist an \nserver side error.");
                else {
                	System.out.println("LayerPanel Selected Layer::"+info.getField("name"));
                    selectionListener.layerSelected( info );
                    infoArea.setText("Layer added!");
                }
            } else {
                String abstr;
                if ( info.getField("abstract") != null )
                    abstr = info.getField("abstract");
                else 
                    abstr = "";
                
                Vector bb = new Vector();
                float[] bbF = info.getLatLonBoundingBox();
                if ( bbF != null ) 
                    for ( int i=0; i< bbF.length; i++ )
                        bb.add( new Float( bbF[i] ) );
                infoArea.setText( "LatLonBoundingBox: "+ bb
                                  +"\nFormats: "+ info.getServerInformation().getGetMapFormats()
                                  +"\n\nAbstract:\n"+ abstr);
            }
        }
    }
    
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseReleased(MouseEvent e) {
    }
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseEntered(MouseEvent e) {
    }
    /**
     * Hier geschieht die Interne Event-Verarbeitung
     */
    public void mouseExited(MouseEvent e) {
    }

	
}
