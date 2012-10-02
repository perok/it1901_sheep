package core;

import java.util.Random;

public class DatabasePopulator {
	private SqlConnection sc;
	private Random rand;

	public DatabasePopulator() {
		sc = new SqlConnection();
		rand = new Random();
	}
	
	public void addFarms() {
		
	}
	
	/** Takes in the number of sheep to be generated together with farmID.
	 * Generates a String[][] with number of sheep and calls SqlConnection
	 * 
	 * @return void
	 * @param numberOfSheep
	 * @param farmId
	 */
	public void addSheep(int numberOfSheep,int farmId) {
		String[][] sheep = new String[numberOfSheep][6];
		for (int i = 0; i < numberOfSheep; i++) {
			sheep[i][0] = Integer.toString(i); 
			sheep[i][1] = "Betty " + Integer.toString(i); 
			sheep[i][2] = Integer.toString(farmId);
			sheep[i][3] = Integer.toString(rand.nextInt(36000)+1389080800); 
			sheep[i][4] = Integer.toString(1); 
			sheep[i][5] = Integer.toString(rand.nextInt(20)+15); 
		}
		sc.insertSheep(sheep);
	}
	
	public void addSheep(String[][] sheep) {
		
	}
}
