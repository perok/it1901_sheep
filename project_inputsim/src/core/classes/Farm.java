package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

/*Ser at ikke alt her er likt som i diagrammet.
 * Er fullt villig til � forandre p� ting s�klart, men vil gjerne snakke om det f�rst.*/

public class Farm implements Serializable {
	
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
	
	/**Constructor
	 * 
	 * @param id
	 * @param name
	 * @param isAdmin
	 */
	public Farm(int id, String name, boolean isAdmin) {
		this.setId(id);
		this.setName(name);
		sheepList = new ArrayList<Sheep>();
		this.isAdmin = isAdmin;
	}
	
	/**Returns id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**Sets id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Returns name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**Sets name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**Adds sheep object to sheepList
	 * 
	 * @param sheep
	 */
	public void addSheep(Sheep sheep){
		sheepList.add(sheep);
	}
	
	/**Returns sheepList
	 * 
	 * @return
	 */
	public ArrayList<Sheep> getSheepList(){
		return sheepList;
	}
	
	/**Takes an ArrayList of Sheep and sets it to sheepList
	 * 
	 * @param sheeps
	 */
	public void setSheepList(ArrayList<Sheep> sheeps){
		sheepList = sheeps;
	}

	/**Returns a boolean isAdmin
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**Sets admin
	 * 
	 * @param isAdmin
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}

/* EOF */