package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**Class for creating a sheep
 * 
 * @author Svenn
 *
 */
public class Sheep implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	int farmId;
	int dateOfBirth;
	boolean alive;
	int weight;
	ArrayList<SheepStatus> recentStatuses;
	
	/**Constructor
	 * 
	 * @param id
	 * @param name
	 * @param farmId
	 * @param dateOfBirth
	 * @param alive
	 * @param weight
	 * @param stats
	 */
	public Sheep (int id, String name, int farmId, int dateOfBirth, boolean alive, int weight, ArrayList<SheepStatus> stats) {
		this.id = id;
		this.name = name;
		this.farmId = farmId;
		this.dateOfBirth = dateOfBirth;
		this.alive = alive;
		this.weight = weight;
		recentStatuses = new ArrayList<SheepStatus>();
		for (int i = 0; i < recentStatuses.size(); i++) {
			this.recentStatuses.add(stats.get(i));
		}
	}
	
	/**Constructor
	 * 
	 * @param id
	 * @param name
	 * @param farmId
	 * @param dateOfBirth
	 * @param alive
	 * @param weight
	 */
	public Sheep (int id, String name, int farmId, int dateOfBirth, boolean alive, int weight) {
		this.id = id;
		this.name = name;
		this.farmId = farmId;
		this.dateOfBirth = dateOfBirth;
		this.alive = alive;
		this.weight = weight;
		recentStatuses = new ArrayList<SheepStatus>();
	}
	
	/**Constructor
	 * 
	 * @param name
	 * @param farmId
	 * @param dateOfBirth
	 * @param alive
	 * @param weight
	 */
	public Sheep (String name, int farmId, int dateOfBirth, boolean alive, int weight) {
		this.name = name;
		this.farmId = farmId;
		this.dateOfBirth = dateOfBirth;
		this.alive = alive;
		this.weight = weight;
		recentStatuses = new ArrayList<SheepStatus>();
	}
	
	/**Returs sheep id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**Set sheep id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Returns sheep name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**Sets sheep name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**Returns farm id
	 * 
	 * @return
	 */
	public int getFarmId() {
		return farmId;
	}

	/**Sets farm id
	 * 
	 * @param farmId
	 */
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
	
	/**Returns date of birth of sheep
	 * 
	 * @return
	 */
	public int getDateOfBirth() {
		return dateOfBirth;
	}

	/**Sets date of birth of sheep
	 * 
	 * @param dateOfBirth
	 */
	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**Returns alive status
	 * 
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}

	/**Sets alive status
	 * 
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**Returns sheep weight
	 * 
	 * @return
	 */
	public int getWeight() {
		return weight;
	}

	/**Set sheep weight
	 * 
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**Returns name string
	 * 
	 */
	public String toString() {
		return name;
	}
	
	/**Returns an array list of recent sheep statuses
	 * 
	 * @return
	 */
	public ArrayList<SheepStatus> getRecentStatuses() {
		return recentStatuses;
	}

	/**Accepts an array list of statuses and adds it to this sheeps statuses
	 * 
	 * @param recentStatuses
	 */
	public void setRecentStatuses(ArrayList<SheepStatus> recentStatuses) {
		for (int i = 0; i < recentStatuses.size(); i++) {
			this.recentStatuses.add(recentStatuses.get(i));
		}
	}
	
	/**Adds a sheep status
	 * 
	 * @param status
	 */
	public void addSheepStatus(SheepStatus status) {
		recentStatuses.add(status);
	}
	
	
}
