package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public Sheep (int id, String name, int farmId, int dateOfBirth, boolean alive, int weight) {
		this.id = id;
		this.name = name;
		this.farmId = farmId;
		this.dateOfBirth = dateOfBirth;
		this.alive = alive;
		this.weight = weight;
		recentStatuses = new ArrayList<SheepStatus>();
	}
	
	public Sheep (String name, int farmId, int dateOfBirth, boolean alive, int weight) {
		this.name = name;
		this.farmId = farmId;
		this.dateOfBirth = dateOfBirth;
		this.alive = alive;
		this.weight = weight;
		recentStatuses = new ArrayList<SheepStatus>();
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

	public int getFarmId() {
		return farmId;
	}

	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}

	public int getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public String toString() {
		return name;
	}

	public ArrayList<SheepStatus> getRecentStatuses() {
		return recentStatuses;
	}

	public void setRecentStatuses(ArrayList<SheepStatus> recentStatuses) {
		for (int i = 0; i < recentStatuses.size(); i++) {
			this.recentStatuses.add(recentStatuses.get(i));
		}
	}
	
	public void addSheepStatus(SheepStatus status) {
		recentStatuses.add(status);
	}
	
	
}
