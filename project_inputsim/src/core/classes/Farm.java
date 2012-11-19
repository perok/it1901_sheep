package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

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
	private boolean isAdmin;
	
	public Farm(int id, String name) {
		this.setId(id);
		this.setName(name);
		sheepList = new ArrayList<Sheep>();
	}
	
	public Farm(int id, String name, boolean isAdmin) {
		this.setId(id);
		this.setName(name);
		sheepList = new ArrayList<Sheep>();
		this.isAdmin = isAdmin;
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
	
	
	public void addSheep(Sheep sheep){
		sheepList.add(sheep);
	}
	
	public ArrayList<Sheep> getSheepList(){
		return sheepList;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
