package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class User  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int mobileNumber;
	private String email;
	private String username;
	private ArrayList<Farm> farmlist;
	private String password;
	
	

	public User(int id, String username, String name, String password, int mobileNumber, String email,  
			ArrayList<Farm> farmlist) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.username = username;
		this.farmlist = farmlist;
	}
	
	public User(int id, String username, String name, String password, int mobileNumber, String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.username = username;
		this.farmlist = new ArrayList<Farm>();
	}
	
	public User copyShallowUser()
	{
		return new User(this.id, "", this.name, "", this.mobileNumber, this.email);
	}
	public boolean shallowEquals(User other)
	{
		return 
			 (this.name.equals(other.getName()) )
	     &&  (this.mobileNumber == other.getMobileNumber())
	     &&  (this.email.equals(other.getEmail()));
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
	
	public void addFarms(ArrayList<Farm> farms) {
		for (int i = 0; i < farms.size(); i++) {
			this.farmlist.add(farms.get(i));
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
