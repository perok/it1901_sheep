package core.sim;
import java.util.Random; 
import com.db.*;
import core.settings.*;


/**
 * @version 0.2
 * @author Lars Erik
 *
 */
public class DatabasePopulator {
	private DatabaseConnector sc;
	private Random rand;

	/**Constructor
	 * @return DatabasePopulator
	 */
	public DatabasePopulator() {
		sc = new DatabaseConnector(new Settings());
		rand = new Random();
	}

	/**
	 * @deprecated
	 * @param numberOfUsers
	 */
	public void addUsers(int numberOfUsers) {

	}

	public void addAccessRights(int userId, int farmId) {
		sc.addAccessRights(userId, farmId);
	}
	
	public void removeAccessRights(int userId, int farmId) {
		sc.removeAccessRights(userId, farmId);
	}
	
	/**Sends the parameter to database via DatabaseConnector
	 * 
	 * @param username
	 * @param password
	 * @param phone
	 * @param cellphone
	 * @param email
	 */
	public void addUser(String [][] user) {
		sc.insertUser(user);
	}
	
	/**Deletes all users from the database via DatabaseConnector
	 * 
	 */
	public void deleteUser() {
		sc.deleteUser();
	}

	/**Generates random farms based on given parameter.
	 * 
	 * @param numberOfFarms
	 */
	public void addFarms(int numberOfFarms) {
		int latestFarm = sc.getLatestFarm();
		if(latestFarm >= 0) {
		String[] farms = new String[numberOfFarms];
		for (int i = 0; i < numberOfFarms; i++) {
			farms[i] = "Bondebakken " + Integer.toString(latestFarm++);
		}
		sc.insertFarms(farms);
		}
	}
	
	/**Sends a message to DatabaseConnector to wipe farm database
	 * 
	 */
	public void deleteFarm() {
		sc.deleteFarm();
	}
	
	/** Takes in the number of sheep to be generated.
	 * Generates a String[][] with number of sheep and calls DatabaseConnector.
	 * Farms is specified in parameter
	 * 
	 * @return void
	 * @param numberOfSheep
	 * @param farmId
	 */
	public void addSheep(int numberOfSheep,int farmId) {
		String[][] sheep = new String[numberOfSheep][6];
		for (int i = 0; i < numberOfSheep; i++) {
			sheep[i][0] = "Betty " + Integer.toString(i); 
			sheep[i][1] = Integer.toString(farmId);
			sheep[i][2] = Integer.toString(rand.nextInt(36000)+1389080800); 
			sheep[i][3] = Integer.toString(1); 
			sheep[i][4] = Integer.toString(rand.nextInt(20)+15); 
		}
		sc.insertSheep(sheep);
	}

	/** Takes in the number of sheep to be generated.
	 * Generates a String[][] with number of sheep and calls DatabaseConnector.
	 * Farms is randomly chosen from the farms in the database
	 * 
	 * @return void
	 * @param numberOfSheep
	 */
	public void addSheep(int numberOfSheep) {
		int numberOfFarms = sc.getNumberOfFarms() - 1;
		if(numberOfFarms >= 0){
			String[][] sheep = new String[numberOfSheep][6];
			for (int i = 0; i < numberOfSheep; i++) {
				sheep[i][0] = Integer.toString(i+1); 
				sheep[i][1] = "Betty " + Integer.toString(i); 
				sheep[i][2] = Integer.toString(rand.nextInt(numberOfFarms) + 1);
				sheep[i][3] = Integer.toString(rand.nextInt(36000)+1389080800); 
				sheep[i][4] = Integer.toString(1); 
				sheep[i][5] = Integer.toString(rand.nextInt(20)+15); 
			}
			sc.insertSheep(sheep);
		}
	}

	/**Adds the parameter via det DatabaseConnector class
	 * 
	 * @param sheep
	 */
	public void addSheep(String[][] sheep) {
		sc.insertSheep(sheep);
	}

	/**Sends a message to DatabaseConnector that wipes everything in sheep table
	 * @return void
	 */
	public void deleteSheep() {
		sc.deleteSheep();
	}
	
	public String listUsers() {
		String[][] users = sc.listUsers();
		String res = new String();
		for (int i = 0; i < users.length; i++) {
			res += "Name: " + users[i][0]+", Id: " + users[i][1] + "\n";
		}
		return res;
	}
	
	public String listFarms() {
		String[][] farms = sc.listFarms();
		String res = new String();
		for (int i = 0; i < farms.length; i++) {
			res += "Name: " + farms[i][0]+", Id: " + farms[i][1] + "\n";
		}
		return res;
	}
}
