package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

/*Ser at ikke alt her er likt som i diagrammet.
 * Er fult villig til � forandre p� ting s�klart, men vil gjerne snakke om det f�rst.*/

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
		sheepList = new ArrayList<Sheep>();
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
	
}
