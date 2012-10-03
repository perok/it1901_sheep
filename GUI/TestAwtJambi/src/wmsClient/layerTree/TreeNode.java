package wmsClient.layerTree;

/**
 * Klasse zur Datenhaltung innerhalb des LayerPanel
 */
 class TreeNode implements TreeNodeInterface {
    LayerInformation content;
    public TreeNode( LayerInformation content ) {
	this.content = content;
    }
    public String toString() {
	return content.toString();	    
    }
    public LayerInformation getLayerInformation() {
	return content;
    }
}
