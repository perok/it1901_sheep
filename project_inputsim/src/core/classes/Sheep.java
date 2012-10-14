package core.classes;

import java.io.Serializable;

public class Sheep implements Serializable{
	
	int id;
	String name;
	int farmId;
	int dateOfBirth;
	boolean alive;
	int weight;
	
	public Sheep (int id, String name, int farmId, int dateOfBirth, boolean alive, int weight) {
		this.id = id;
		this.name = name;
		this.farmId = farmId;
		this.dateOfBirth = dateOfBirth;
		this.alive = alive;
		this.weight = weight;
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
}
