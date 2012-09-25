package wmsClient.layerList;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Klasse zur Datenhaltung für LayerList
 */
class LayerListModel extends Vector implements ListModel {

    private Vector listener = new Vector();

    public void addListDataListener(ListDataListener l) {
	listener.add( l );
    }
    public void removeListDataListener(ListDataListener l) {
	listener.remove( l );
    }

    public void fireListDataListenerContentsChanged() {
	for (int i=0; i<listener.size(); i++)
	    ((ListDataListener)listener.elementAt( i )).contentsChanged( new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, listener.size() ) );
    }
    
     public Object getElementAt(int index) {
	 return super.elementAt(index);
     }

    public int getSize() {
	return super.size();
    }    
}
