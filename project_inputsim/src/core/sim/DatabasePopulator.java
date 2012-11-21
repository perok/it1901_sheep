package core.sim;
import java.util.ArrayList;
import java.util.Random; 
import com.db.*;

import core.classes.Sheep;
import core.settings.*;


/** Makes population of database easier. Provides creation and changing of users, sheep etc.
 * @version 0.2
 * @author Lars Erik
 *
 */
public class DatabasePopulator {
	private DatabaseConnector sc;
	private Random rand;

	/** Constructor.
	 * @return DatabasePopulator
	 */
	public DatabasePopulator() {
		sc = new DatabaseConnector(new Settings());
		rand = new Random();
	}

	/** Calls the database and adds access rights.
	 * 
	 * @param userId
	 * @param farmId
	 */
	public void addAccessRights(int userId, int farmId) {
		sc.addAccessRights(userId, farmId);
	}
	
	/** Calls the database and removes access rights.
	 * 
	 * @param userId
	 * @param farmId
	 */
	public void removeAccessRights(int userId, int farmId) {
		sc.removeAccessRights(userId, farmId);
	}
	
	/** Sends the parameter to database via DatabaseConnector to create a user.
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
	
	/** Deletes all users from the database via DatabaseConnector.
	 * 
	 */
	public void deleteUser() {
		sc.deleteUser();
	}

	/** Generates random farms based on given parameter.
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
	
	/** Sends a message to DatabaseConnector to wipe farm database.
	 * 
	 */
	public void deleteFarm() {
		sc.deleteFarm();
	}
	
	/** Takes in the number of sheep to be generated.
	 * Generates a String[][] with number of sheep and calls DatabaseConnector.
	 * Farms is specified in parameter.
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
	 * Farms is randomly chosen from the farms in the database.
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

	/** Adds the parameter via DatabaseConnector class.
	 * 
	 * @param sheep
	 */
	public void addSheep(String[][] sheep) {
		sc.insertSheep(sheep);
	}

	/** Sends a message to DatabaseConnector that wipes everything in sheep table.
	 * @return void
	 */
	public void deleteSheep() {
		sc.deleteSheep();
	}
	
	/** Sets the sheep id to dead.
	 * 
	 * @param id
	 */
	public void killSheep(int id) {
		sc.setSheepAlive(id, 0);
	}
	
	/** Sets the sheep id to alive.
	 * 
	 * @param id
	 */
	public void reviveSheep(int id) {
		sc.setSheepAlive(id, 1);
	}
	
	/** Returns a string of all users in database with id and name.
	 * 
	 * @return
	 */
	public String listUsers() {
		String[][] users = sc.listUsers();
		String res = new String();
		for (int i = 0; i < users.length; i++) {
			res += "Name: " + users[i][0]+", Id: " + users[i][1] + "\n";
		}
		return res;
	}
	
	/** Returns a string of all farms in database with id and name.
	 * 
	 * @return
	 */
	public String listFarms() {
		String[][] farms = sc.listFarms();
		String res = new String();
		for (int i = 0; i < farms.length; i++) {
			res += "Name: " + farms[i][0]+", Id: " + farms[i][1] + "\n";
		}
		return res;
	}
	
	/** Returns a string of all users in database with id and name.
	 * 
	 * @return
	 */
	public String listSheep() {
		ArrayList<Sheep> sheep = sc.listSheep();
		String res = new String();
		for (int i = 0; i < sheep.size(); i++) {
			res += "Name:" + sheep.get(i).getName()+", Id:" + sheep.get(i).getId() +", Farm:" + sheep.get(i).getFarmId() + "\n";
		}
		return res;
	}
	
	/** Sends a message to DatabaseConnector that wipes everything in status table.
	 * @return void
	 */
	public void deleteStatus() {
		sc.deleteSheepStatus();
	}
}
