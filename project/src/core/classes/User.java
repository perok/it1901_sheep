package core.classes;

import java.io.Serializable;
import java.util.ArrayList;


/** Class to hold a user as an object. Holds user data and farm-associations
 * 
 * @author Gruppe 10
 */
public class User  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int mobileNumber;
	private String email;
	private String username;
	private ArrayList<Farm> farmlist;
	private String password;

	/** Constructor. Initialize..
	 * 
	 * @param id user-id
	 * @param username the username of this user, as used for login
	 * @param name the IRL name of this user.
	 * @param password the password for this user
	 * @param mobileNumber the mobile number for this user
	 * @param email the email for this user
	 * @param farmlist list of farms this user has admin-rights on
	 */
	public User(int id, String username, String name, String password, int mobileNumber, String email,  
			ArrayList<Farm> farmlist) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.farmlist = new ArrayList<Farm>();
		
		for (int i = 0; i < farmlist.size(); i++) {
			this.farmlist.add(farmlist.get(i));
		}
	}
	
	/** Constructor. Initialize..
	 * 
	 * @param id user-id
	 * @param username the username of this user, as used for login
	 * @param name the IRL name of this user.
	 * @param password the password for this user
	 * @param mobileNumber the mobile number for this user
	 * @param email the email for this user
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
	
	/** Make a shallow and non-static copy of this user.
	 * By shallow it is meant that only some data is copied.
	 * (id, name, mobile number, email)
	 * 
	 * @return a new user-object (not a reference)
	 */
	public User copyShallowUser()
	{
		return new User(this.id, "", this.name, "", this.mobileNumber, this.email);
	}
	
	/** Does this user have the same
	 * name, mobile number and mail
	 * as another user?
	 * 
	 * @param other user-object to compare with
	 * @return true if the users are equal, false if not
	 */
	public boolean shallowEquals(User other)
	{
		return
			(this.name.equals(other.getName()) )
			&&  (this.mobileNumber == other.getMobileNumber())
			&&  (this.email.equals(other.getEmail()));
	}

	/** Get the ID of THIS
	 * 
	 * @return an integer for id of this user.
	 */
	public int getId() {
		return id;
	}

	/** Set(modify) the ID of this user
	 * 
	 * @param id ID-number for this user. 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Return the name of THIS
	 * 
	 * @return the name of THIS.
	 */
	public String getName() {
		return name;
	}

	/** Set (modify) the name of this user.
	 * 
	 * @param name the new name of THIS
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Get the mobile number of this user.
	 * 
	 * @return the mobile number of this user.
	 */
	public int getMobileNumber() {
		return mobileNumber;
	}

	/** Set the mobile number for this user
	 * 
	 * @param mobileNumber the number as an integer for this user.
	 */
	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/** Get the email of this user
	 * 
	 * @return the email of THIS.
	 */
	public String getEmail() {
		return email;
	}

	/** Set the email of THIS
	 * 
	 * @param email the string to set as a new email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Get the username of THIS.
	 * 
	 * @return the username used for login, etc
	 */
	public String getUsername() {
		return username;
	}

	/** Set (modify) the username of THIS
	 * 
	 * @param username the username to set for THIS
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/** Get a list of farms associated with this user.
	 * 
	 * @return a list of farms associated with this user.
	 */
	public ArrayList<Farm> getFarmlist() {
		return farmlist;
	}

	/** Set a new list of associated farms. 
	 * The old list is truncated.
	 * 
	 * @param farmlist
	 */
	public void setFarmlist(ArrayList<Farm> farmlist) {
		this.farmlist = farmlist;
	}
	
	/** Append a list of farms associated with this user.
	 * 
	 * @param farms a list of farms that should be associated to THIS.
	 */
	public void addFarms(ArrayList<Farm> farms) {
		/* For each farm in farms.. */
		for (int i = 0; i < farms.size(); i++) {
			/* ... append to the list of farms */
			this.farmlist.add(farms.get(i));
		}
	}

	/** Get the password for THIS
	 * 
	 * @return the unscrambled, straight-up password for THIS
	 */
	public String getPassword() {
		return password;
	}

	/** Set the password for THIS.
	 * 
	 * @param password should be the literal password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}

/* EOF */