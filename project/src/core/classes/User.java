package core.classes;

import java.util.ArrayList;

public class User {
	private int id;
	private String name;
	private int phoneNumber;
	private int mobileNumber;
	private String email;
	private String username;
	private ArrayList<Farm> farmlist;
	
	

	public User(int id, String name, int phoneNumber, int mobileNumber, String email, String username, 
			ArrayList<Farm> farmlist) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.username = username;
		this.farmlist = farmlist;
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

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Farm> getFarmlist() {
		return farmlist;
	}

	public void setFarmlist(ArrayList<Farm> farmlist) {
		this.farmlist = farmlist;
	}

}
