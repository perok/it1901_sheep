package wmsClient.layerTree;

import java.util.*;

/**
 * Klasse zur Datenhaltung innerhalb des LayerPanel
 */
class InnerTreeNode extends Vector 
    implements TreeNodeInterface {

    LayerInformation content;
    public InnerTreeNode( LayerInformation content ) {
	this.content = content;
    }
    public String toString() {
	return content.toString();	    
    }
    public LayerInformation getLayerInformation() {
	return content;
    }
}
