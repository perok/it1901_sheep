package wmsClient.layerTree;

import java.util.*;
import uk.co.wilson.xml.*;
import org.xml.sax.*;
import uk.org.xml.sax.*;
import java.io.*;


/**
 * Klasse zu Parsen eines XML-Streams von einer GetCapabilities Anfrage.
 */
class CapabilitiesParser extends MinML {
    
    private InputStream input;
    
    private ServerInformation server;
    private Vector currPath;
    private String charBuff;
    private boolean layerIsParent;
    private boolean getMapIsParent;
    private boolean serviceIsParent;
    private boolean mapIsParent;
    
    
    public CapabilitiesParser( InputStream input ) {
        this.input = input;
    }
    
    /**
     * Gibt eine Baumstruktur zurück, die die Layer repräsentiert
     */       
    public Vector constructDataTree() {	
        try {
            parse( new InputSource( input ) );
        } catch( Exception e ) {
            System.out.println("Exeption aufgetreten: "+ e);
            e.printStackTrace();
            return null;	    
        }
        
        return (Vector)currPath.elementAt( 0 );
    }


    public void startDocument() {
        server = new ServerInformation();
        
        currPath = new Vector();
        currPath.add( server );
    }
    
    public void endDocument() {
        if ( ((Vector)currPath.elementAt( 0 )).size() > 0 ) 
            eliminateInnerNodes((Vector)currPath.elementAt( 0 ));	
    }

    protected void eliminateInnerNodes(Vector node) {
        for ( int i=0; i< node.size(); i++ ) {
            InnerTreeNode currChild = (InnerTreeNode)node.elementAt( i );
            
            //ALLE LAYERS
            System.out.println(currChild.toString());
            
            if ( currChild.size() > 0 )
                eliminateInnerNodes( currChild );
            else
                node.setElementAt( new TreeNode( currChild.getLayerInformation() ),i );
        }
        
    }
    
    public void startElement (String name, AttributeList attributes) {
        
        if ( name.toLowerCase().equals( "layer" ) ) {
            LayerInformation layerInfo = new LayerInformation( server );
            if ( attributes.getValue( "queryable" ) == null )
                layerInfo.setField( "queryable", "0" );
            else
                layerInfo.setField( "queryable", attributes.getValue( "queryable" ) );
            
            if ( currPath.size() > 1 )
                layerInfo.setParentLayer( ((InnerTreeNode)currPath.lastElement()).getLayerInformation() );
            currPath.add( new InnerTreeNode( layerInfo ) );
            layerIsParent = true;
        } else if ( name.toLowerCase().equals( "srs" ) || name.toLowerCase().equals( "title" ) || name.toLowerCase().equals( "name" ) || name.toLowerCase().equals( "abstract" )) {
            // nothing
        } else if ( layerIsParent && name.toLowerCase().equals( "latlonboundingbox" ) ) {
            
            ((InnerTreeNode)currPath.lastElement()).getLayerInformation().setLatLonBoundingBox( Float.parseFloat(attributes.getValue("minx")), 
                                                                                                Float.parseFloat(attributes.getValue("miny")), 
                                                                                                Float.parseFloat(attributes.getValue("maxx")), 
                                                                                                Float.parseFloat(attributes.getValue("maxy")) );	    
        } else {
            
            if ( name.toLowerCase().equals("style") ) 
                layerIsParent = false;
            
            if ( name.toLowerCase().equals("getmap") ) {
                getMapIsParent = true;
            } else if ( name.toLowerCase().equals("service") ) {
                serviceIsParent = true;
            } else if ( name.toLowerCase().equals("map") ) {
                mapIsParent = true;
            }
            
        }
        
        if ( getMapIsParent && name.toLowerCase().equals("onlineresource") )
            server.setGetMapURL( attributes.getValue("xlink:href") );

        if ( mapIsParent && name.toLowerCase().equals("get") )
            server.setGetMapURL( attributes.getValue("onlineResource") );
        
        if ( mapIsParent && name.toLowerCase().equals("jpeg") )
            server.addGetMapFormat( "image/jpeg" );
        else if ( mapIsParent && name.toLowerCase().equals("png") )
            server.addGetMapFormat( "image/png" );
        else if ( mapIsParent && name.toLowerCase().equals("gif") )
            server.addGetMapFormat( "image/gif" );
        
    }
    
    public void endElement (String name) {
 	    if ( name.toLowerCase().equals( "layer" ) ) {
            Object child = currPath.remove( currPath.size()-1 );
            ((Vector)currPath.lastElement()).add( child );
 	    } else if ( layerIsParent && currPath.size() > 1  && ( name.toLowerCase().equals( "srs" ) || name.toLowerCase().equals( "title" ) || name.toLowerCase().equals( "name" )|| name.toLowerCase().equals( "abstract" ) )   ) {		
            ((InnerTreeNode)currPath.lastElement()).getLayerInformation().setField( name.toLowerCase(), charBuff );
 	    } else if ( name.toLowerCase().equals("getmap") ) {
            getMapIsParent = false;
	    } else if ( name.toLowerCase().equals("service") ) {
            serviceIsParent = false;
	    } else if ( name.toLowerCase().equals("map") ) {
            mapIsParent = false;
	    }
        
        
	    if ( name.toLowerCase().equals("style") ) 
            layerIsParent = true;
        
	    if ( getMapIsParent && name.toLowerCase().equals("format") ) {
            server.addGetMapFormat( charBuff );
	    }
	    
	    if ( serviceIsParent && name.toLowerCase().equals("title") ) {
            server.setName( charBuff );
	    }
	    
	    
    }
    
    public void characters (char ch[], int start, int length) {
        charBuff = new String(ch, start, length);
    }
    
    public void fatalError (SAXParseException e) throws SAXException {
        System.out.println("Error: " + e);
        throw e;
    }
}
