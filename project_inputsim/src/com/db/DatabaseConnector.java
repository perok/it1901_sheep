package com.db;

import core.settings.*;
import core.classes.*;
import java.io.File;
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
	
	/*
	 * CLIENT SECTION
	 */
	
	public User loginQuery(String username, String password) {
		String[][] r = processQuery("SELECT * FROM user WHERE password = '" + user.getPassword() + "'");
		for (int i = 0; i < r.length; i++) {
			list.add(new Sheep(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5]));
		return null;
	}
	
	public ArrayList<Sheep> getShepp(Farm farm) {
		ArrayList<Sheep> list = new ArrayList<Sheep>();
		String[][] r = processQuery("SELECT * FROM sheep WHERE farm_id = " + farm.getId() + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new Sheep(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5]));
		}
		return list;
	}
	
	public boolean removeSheep(Sheep sheep) {
		Statement s = conn.createStatement();
		s.executeUpdate("DELETE FROM sheep WHERE id = " + sheep.getId + "");
	}
	
	public ArrayList<Farm> getFarms(User user) {
		ArrayList<Farm> list = new ArrayList<Farm>();
		String[][] r = processQuery("SELECT * FROM access_rights WHERE user_id = " + user.getId() + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new Farm(r[i][0],r[i][1]));
		}
		return list;
	}
	
	public boolean addAccessRights(User user, Farm farm) {
		Statement s = conn.createStatement();
		try{
			s.executeUpdate("INSERT INTO access_rights (user_id,farm_id) VALUES(" + user.getId() + "," + farm.getId() + ")");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean removeAccessRights(User user, Farm farm) {
		Statement s = conn.createStatement();
		try{
			s.executeUpdate("DELETE FROM access_rights WHERE " + user.getId() + " = user_id AND " + farm.getId() + " = farm_id");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean editUser(int userId, User user) {
		Statement s = conn.createStatement();
		try{
			s.executeUpdate("UPDATE user SET name = '" + user.getName() + "', password = '" + user.getPassword() + "', phone_number = " + user.getPhoneNumber() + ", "+
					"mobile_number = " + user.getMobileNumber() + ", email = '" + user.getEmail() + "' WHERE id = " + userId);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public ArrayList<SheepStatus> getSheepStatus(Farm farm) {
		ArrayList<SheepStatus> list = new ArrayList<SheepStatus>();
		String[][] r = processQuery("SELECT * FROM sheep_status WHERE farm_id = " + farm.getFarmId() + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepStatus(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5],r[i][6]));
		}
		return list;
	}
	
	public ArrayList<SheepAlarm> getSheepAlarm(Farm farm) {
		ArrayList<SheepAlarm> list = new ArrayList<SheepAlarm>();
		String[][] r = processQuery("SELECT * FROM sheep_alert WHERE farm_id = " + farm.getFarmId + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepAlert(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5],r[i][6]));
		}
		return list;
	}
	
	
	/*
	 * SERVER SECTION
	 */

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
