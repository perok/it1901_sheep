package wmsClient.layerTree;


import java.util.*;


/**
 * In dieser Klasse werden Informationen zu eiem Layer bereit gehalten 
 */
public class LayerInformation {
    
    private Hashtable data;
    private ServerInformation server;
    private LayerInformation parent;
    private float[] latLonBoundingBox;

    /**
     * Erstellt ein neues Objekt mit Refferenz auf den Server,
     * der diesen Layer anbietet.
     */
    public LayerInformation(ServerInformation server) {
	this.server = server;
	this.data = new Hashtable();
    }


    /**
     * Setzt die Refferenz auf den Übergeordneten Layer
     * <br>Diese kann benötigt werden, da die Layer sich ihre Eigenschaften teilweise vererben
     */
    public void setParentLayer( LayerInformation parent ) {
	this.parent = parent;
    }

    /**
     * Gibt die Refferenz auf den Übergeordneten Layer
     * <br>Diese kann benötigt werden, da die Layer sich ihre Eigenschaften teilweise vererben
     * 
     * @return Refferenz auf Übergeordneten Layer, oder null für den Root-Layer
     */
    public LayerInformation getParentLayer() {
	return parent;
    }

    /**
     * Setzt die den Bereich in dem dieser Layer verfügbar ist
     */     
    public void setLatLonBoundingBox( float minx, float miny, float maxx, float maxy ) {
	latLonBoundingBox =  new float[]{ minx, miny, maxx, maxy };
    }

    /**
     * Gibt die den Bereich in dem dieser Layer verfügbar ist
     * 
     * @return floatArray mit { minx, miny, maxx, maxy }
     */
    public float[] getLatLonBoundingBox( )  {
 	if ( latLonBoundingBox == null && parent != null )
 	    return parent.getLatLonBoundingBox();
	return latLonBoundingBox;
    }
    

    /**
     * Setzt eine beliebige Eigenschaft des Layers
     * <br><br>
     * <br>Von dem GetCapabilities-Parser werden im Moment folgende gesetzt:
     * <br>  queryable, srs, title, name, abstract
     * <br>
     * <br>Beachte: Die Keys sollten LowerCase sein.
     * <br>
     * <br>Die Eigenschaft srs ist einfach als Text gespeichert, wie im XML-Dokument
     * <br>Eine Funktion getSRSList gibt es noch nicht
     */
    public void setField(String key, String value) {
	data.put( key, value );
    }

    /**
     * Liefert eine beliebige Eigenschaft des Layers
     * <br><br>
     * <br>Von dem GetCapabilities-Parser werden im Moment folgende gesetzt:
     * <br>  queryable, srs, title, name, abstract
     * <br>
     * <br>Beachte: Die Keys sollten LowerCase sein.
     * <br>
     * <br>Die Eigenschaft srs ist einfach als Text gespeichert, wie im XML-Dokument
     * <br>Eine Funktion getSRSList gibt es noch nicht. Kann aber bei bedarf hinzu kommen.
     */
    public String getField(String key) {
	return (String)data.get( key );
    }
    

    /**
     * Liefert eine Refferenz auf den Server, der diesen Layer anbietet.
     */
    public ServerInformation getServerInformation() {
	return server;
    }
       
    /**
     * Liefert den Titel des Layers
     */
    public String toString() {
	if ( getField( "title" ) != null )
	    return getField( "title" );
	else 
	    return super.toString();
    }

}
