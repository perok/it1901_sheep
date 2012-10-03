package wmsClient.layerTree;


import java.util.*;


/**
 * In dieser Klasse werden Informationen �ber eiem Server bereit gehalten 
 */
public class ServerInformation extends Vector {
    private String name;
    private Vector getMapFormats = new Vector();
    private String getMapURL;
        
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Setzt die URL des WMS, an die eine GetMap-Anfrage gestellt werden kann
     */
    public void setGetMapURL(String url) {
	this.getMapURL = url;
    }

    /**
     * Liefert die URL des WMS, an die eine GetMap-Anfrage gestellt werden kann
     */
    public String getGetMapURL() {
	return this.getMapURL;
    }

    
    public void addGetMapFormat( String format ) {
	this.getMapFormats.add( format );
    }
    
    /**
     * Liefert einen Vector von String Objekten, mit Formaten, in denen der WMS eine Map liefern kann
     */
    public Vector getGetMapFormats(  ) {
	return this.getMapFormats;
    }
    
    /**
     * Gibt zur�ck, ob ein bestimmtes Format interst�tzt wird
     */
    public boolean supportsGetMapFormat( String format  ) {
	return this.getMapFormats.contains( format );
    }
    /**
     * Liefert den Namen des Servers
     */
    public String getName() {
	return name;
    }

    /**
     * Liefert den Namen des Servers
     */
    public String toString() {
	return name;
    }

}
