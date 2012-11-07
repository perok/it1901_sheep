package core.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*Ser at ikke alt her er likt som i diagrammet.
 * Er fult villig til å forandre på ting såklart, men vil gjerne snakke om det først.*/

public class Farm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private ArrayList<Sheep> sheepList;
	
	public Farm(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean addSheep(Sheep sheep){
		return false;
	}
	
	public ArrayList<Sheep> getSheepList(){
		return sheepList;
	}
	
}
