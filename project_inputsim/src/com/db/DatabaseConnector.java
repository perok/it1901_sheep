package com.db;

import core.settings.*;
import core.classes.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnector {
	private String sqlUrl;
	private String username;
	private String database;
	private String password;
	private Connection conn;

	public DatabaseConnector(Settings settings) {
		sqlUrl = settings.getDbUrl();
		database = settings.getDbDatabase();
		username = settings.getDbUser();
		password = settings.getDbPassword();
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			String url = "jdbc:mysql://" + sqlUrl + "/" + database;
			conn = DriverManager.getConnection (url, username, password);
			System.out.println ("Database connection established");
		}
		catch (Exception e)
		{
			System.err.println ("Cannot connect to database server"+e.toString());
		}
	}
	
	/**Helper method to get boolean value of a db-entry
	 * 
	 * @param string
	 * @return
	 */
	private boolean getBoolean(String input) {
		try {
			if(Integer.parseInt(input) == 1)
				return true;
			else
				return false;
		} catch (Exception e) {
			if(input.equals("true"))
				return true;
			else return false;
		}
	}
	
	/*
	 * CLIENT SECTION
	 */
	
	public User loginQuery(String username, String password) {
		String[][] r = processQuery("SELECT id,username,password,name,mobile_number,email FROM user WHERE username = '" + username + "'" + " AND password = '" + password + "';");
		User user = new User(Integer.parseInt(r[0][0]), r[0][1], r[0][2], r[0][3], Integer.parseInt(r[0][4]), r[0][5], null);
		String[][] r2 = processQuery("SELECT farm_id FROM access_rights WHERE user_id = " + user.getId() + ";");
		ArrayList<Farm> farms = new ArrayList<Farm>();
		for (int i = 0; i < r2.length; i++) {
			String[][] r3 = processQuery("SELECT name FROM farm WHERE farm_id = " + r2[i][0] + ";");
			Farm farm = new Farm(Integer.parseInt(r[i][0]),r3[0][0]);
			String [][] r4 = processQuery("SELECT * from sheep WHERE farm_id = " + farm.getId() + ";");
			for (int j = 0; j < r4.length; j++) {
				farm.addSheep(new Sheep(Integer.parseInt(r4[j][0]), r4[j][1], Integer.parseInt(r4[j][2]), Integer.parseInt(r4[j][3]), getBoolean(r[j][4]), Integer.parseInt(r4[i][5])));
			}
			farms.add(farm);
		}
//		user.addFarms(farms);
		return user;
	}
	
	

	public ArrayList<Sheep> getSheep(int farmId) {
		ArrayList<Sheep> list = new ArrayList<Sheep>();
		String[][] r = processQuery("SELECT * FROM sheep WHERE farm_id = " + farmId + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new Sheep(Integer.parseInt(r[i][0]),r[i][1],Integer.parseInt(r[i][2]),Integer.parseInt(r[i][3]),
					Boolean.parseBoolean(r[i][4]),Integer.parseInt(r[i][5])));
		}
		return list;
	}
	
	public boolean removeSheep(Sheep sheep) {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM sheep WHERE id = " + sheep.getId() + "");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeSheep(int sheepId) {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM sheep WHERE id = " + sheepId + "");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Farm> getFarms(User user) {
		ArrayList<Farm> list = new ArrayList<Farm>();
		String[][] r = processQuery("SELECT * FROM access_rights WHERE user_id = " + user.getId() + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new Farm(Integer.parseInt(r[i][0]),r[i][1]));
		}
		return list;
	}
	
	public boolean addAccessRights(int userId, int farmId) {
		try{
			Statement s = conn.createStatement();
			s.executeUpdate("INSERT INTO access_rights (user_id,farm_id) VALUES(" + userId + "," + farmId + ");");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean removeAccessRights(int userId, int farmId) {
		
		try{
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM access_rights WHERE " + userId + " = user_id AND " + farmId + " = farm_id;");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean editUser(int userId, User user) {
		
		try{
			Statement s = conn.createStatement();
			s.executeUpdate("UPDATE user SET name = '" + user.getName() + "', password = '" + user.getPassword() + "', phone_number = " + user.getPhoneNumber() + ", "+
					"mobile_number = " + user.getMobileNumber() + ", email = '" + user.getEmail() + "' WHERE id = " + userId + ";");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public ArrayList<SheepStatus> getSheepStatus(int farmId) {
		ArrayList<SheepStatus> list = new ArrayList<SheepStatus>();
		String[][] r = processQuery("SELECT * FROM sheep_status WHERE farm_id = " + farmId + ";");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepStatus(Integer.parseInt(r[i][0]),Integer.parseInt(r[i][1]),Integer.parseInt(r[i][2])
					,Float.parseFloat(r[i][3]), new GpsPosition(Double.parseDouble(r[i][4]), Double.parseDouble(r[i][5])),
					Integer.parseInt(r[i][6])));
		}
		return list;
	}
	
	public ArrayList<SheepAlert> getSheepAlert(int farmId) {
		ArrayList<SheepAlert> list = new ArrayList<SheepAlert>();
		String[][] r = processQuery("SELECT * FROM sheep_alert WHERE farm_id = " + farmId + ";");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepAlert(Integer.parseInt(r[i][0]),Integer.parseInt(r[i][1]),Integer.parseInt(r[i][2])
					,Float.parseFloat(r[i][3]), new GpsPosition(Double.parseDouble(r[i][4]), Double.parseDouble(r[i][5])),
					Integer.parseInt(r[i][6])));
		}
		return list;
	}
	
	
	/*
	 * SERVER SECTION
	 */
	
	public String getPhoneNumber(String username) {
		String[][] results = processQuery("SELECT phone_number FROM user WHERE username = '" + username + "';");
		return results[0][0];
	}
	
public String getEmailAddress(String username) {
	String[][] results = processQuery("SELECT e-mail FROM user WHERE username = '" + username + "';");
	return results[0][0];
	}

	public void insertSheepStatus(String[][] sheepstats) {
		try {
			Statement s = conn.createStatement();
			for (int i = 0; i < sheepstats.length; i++) {
				s.executeUpdate("INSERT INTO sheep_status (sheep_id,timestamp,temperature,heart_rate,latitude,longditude,farm_id" +
						") VALUES (" + ""+sheepstats[i][0]+"," + ""+sheepstats[i][1]+"," + ""+sheepstats[i][2]+"," +
						""+sheepstats[i][3]+"," + ""+sheepstats[i][4]+"," + ""+sheepstats[i][5]+ "," + sheepstats[i][6] + ");");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void deleteSheepStatus() {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM sheep_status WHERE 1=1;");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void insertSheepAlert(String[][] sheepalerts) {
		try {
			Statement s = conn.createStatement();
			for (int i = 0; i < sheepalerts.length; i++) {
				s.executeUpdate("INSERT INTO sheep_alert (id,sheep_id,timestamp,temperature,heart_rate,latitude,longditude,farm_id" +
						") VALUES (" + ""+sheepalerts[i][0]+"," + ""+sheepalerts[i][1]+"," + ""+sheepalerts[i][2]+"," +
						""+sheepalerts[i][3]+"," + ""+sheepalerts[i][4]+"," + ""+sheepalerts[i][5]+ "," +sheepalerts[i][6]+ "," +sheepalerts[i][7]+");");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void deleteSheepAlert() {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM sheep_alert WHERE 1=1;");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public ArrayList<SheepAlert> getNewSheepAlert() {
		ArrayList<SheepAlert> list = new ArrayList<SheepAlert>();
		String[][] r = processQuery("SELECT * FROM sheep_alert WHERE notified = " + 0 + ";");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepAlert(Integer.parseInt(r[i][0]),Integer.parseInt(r[i][1]),Integer.parseInt(r[i][2])
					,Float.parseFloat(r[i][3]), new GpsPosition(Double.parseDouble(r[i][4]), Double.parseDouble(r[i][5])),
					Integer.parseInt(r[i][6])));
		}
		return list;
	}
	
	public boolean newAlertExists() {
		ArrayList<SheepAlert> list = new ArrayList<SheepAlert>();
		String[][] r = processQuery("SELECT * FROM sheep_alert WHERE notified = " + 0 + ";");
		
		if(r == null)
			return false;
		else
			return true;
	}
	
	public void alertNotified(int alertId) {
		try {
			Statement s = conn.createStatement();
				s.executeUpdate("UPDATE sheep_alert SET notified = " + 1 + " WHERE id = " + alertId
						+ ";");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public String getAlertResponderEmail(int farmId) {
		System.out.println(farmId);
		String[][] r = processQuery("SELECT user_id FROM access_rights WHERE farm_id = " + farmId + 
				" AND admin=1;");
		System.out.println(r[0][0]);
		String[][] s = processQuery("SELECT email from user WHERE id = " + Integer.parseInt(r[0][0]));
		return s[0][0];
		
	}
	
	public int getAlertResponderPhone(int farmId) {
		String[][] r = processQuery("SELECT user_id FROM access_rights WHERE farm_id= " + farmId + 
				"AND admin="+ 1 + ";");
		String[][] s = processQuery("SELECT mobile_number from user WHERE id=" + Integer.parseInt(r[0][0]));
		return Integer.parseInt(s[0][0]);
		
	}

	public void insertSheep(String[][] sheep) {
		try {
			Statement s = conn.createStatement();
			for (int i = 0; i < sheep.length; i++) {
				s.executeUpdate("INSERT INTO sheep (id,name,farm_id,date_of_birth,alive,weight" +
						") VALUES (" + ""+sheep[i][0]+"," + "'"+sheep[i][1]+"'," + ""+sheep[i][2]+"," +
						""+sheep[i][3]+"," + ""+sheep[i][4]+"," + ""+sheep[i][5]+");");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public int getNumberOfSheep() {
		String[][] results = processQuery("SELECT * FROM sheep");
		return results.length;
	}

	public void deleteSheep() {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM sheep WHERE 1=1;");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void insertFarms(String[] farms) {
		try {
			Statement s = conn.createStatement();
			for (int i = 0; i < farms.length; i++) {
				s.executeUpdate("INSERT INTO farm (name" +
						") VALUES (" + "'"+farms[i] + "');");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public int getNumberOfFarms() {
		String[][] results = processQuery("SELECT * FROM farm");
		return results.length;
	}
	
	public int getLatestFarm() {

		int latest = -1;
		String[][] results = processQuery("SELECT * FROM farm");
		for (int i = 0; i < results.length; i++) {
			if(Integer.parseInt(results[i][0]) > latest)
				latest = Integer.parseInt(results[i][0]);
		}
		return latest;
	}

	public void deleteFarm() {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM farm WHERE 1=1;");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void insertUser(String[][] users) {
		try {
			Statement s = conn.createStatement();
			for (int i = 0; i < users.length; i++) {
				s.executeUpdate("INSERT INTO user (username,password,name,phone_number,mobile_number,email" +
						") VALUES (" + "'"+users[i][0]+"'," + "'"+users[i][1]+"'," + "'"+users[i][2]+"'," +
						""+users[i][3]+"," + ""+users[i][4]+"," + "'"+users[i][5]+"');");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	

	}

	public void deleteUser() {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM user WHERE 1=1;");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	private String[][] processQuery(String str) {
		try {
			Statement s = conn.createStatement();
			s.execute(str);
			ResultSet r = s.getResultSet();
			//Count number of rows
			r.last();  
			int num_rows = r.getRow(); 
			//Count number of columns
			r.first();
			ResultSetMetaData md = r.getMetaData();
			int columnNum = md.getColumnCount();
			String[][] result = new String[num_rows][columnNum];

			int i=0;
			//Loop through result and return array
			while (true && num_rows!=0) {
				for(int n=1;n<=columnNum;n++) {
					result[i][n-1] = r.getString(n);
				}
				i++;
				if(!r.next()) break;
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
