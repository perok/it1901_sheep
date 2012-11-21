package core.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**Class for generating users
 * 
 * @author Svenn
 *
 */
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
	
	

	/**Constructor
	 * 
	 * @param id
	 * @param username
	 * @param name
	 * @param password
	 * @param mobileNumber
	 * @param email
	 * @param farmlist
	 */
	public User(int id, String username, String name, String password, int mobileNumber, String email,  
			ArrayList<Farm> farmlist) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.farmlist = farmlist;
	}
	/**Constructor
	 * 
	 * @param id
	 * @param username
	 * @param name
	 * @param password
	 * @param mobileNumber
	 * @param email
	 */
	public User(int id, String username, String name, String password, int mobileNumber, String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.username = username;
		this.farmlist = new ArrayList<Farm>();
	}

	/**Returns user id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**Sets user id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Returns user name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**Sets user name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**Returns user mobile number
	 * 
	 * @return
	 */
	public int getMobileNumber() {
		return mobileNumber;
	}

	/**Set user mobile number
	 * 
	 * @param mobileNumber
	 */
	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**Returns user email
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**Sets user email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**Returns user username
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**Sets user username
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**Returns list of farms
	 * 
	 * @return
	 */
	public ArrayList<Farm> getFarmlist() {
		return farmlist;
	}
	
	/**Sets list of farms
	 * 
	 * @param farmlist
	 */
	public void setFarmlist(ArrayList<Farm> farmlist) {
		this.farmlist = farmlist;
	}
	
	/**Adds a list of farms to farmlist
	 * 
	 * @param farms
	 */
	public void addFarms(ArrayList<Farm> farms) {
		for (int i = 0; i < farms.size(); i++) {
			this.farmlist.add(farms.get(i));
		}
		
	}

	/**Returns user password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**Set user password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
