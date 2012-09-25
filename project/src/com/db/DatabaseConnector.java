package com.db;

import core.settings.*;
import java.io.File;
import java.sql.*;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04

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
	
<<<<<<< HEAD
=======
	public User loginQuery(String username, String password) {
		String[][] r = processQuery("SELECT * FROM user WHERE password = '" + user.getPassword() + "'");
		for (int i = 0; i < r.length; i++) {
			list.add(new Sheep(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5]));
		return null;
	}
	
	public ArrayList<Sheep> getShepp(Farm farm) {
		ArrayList<Sheep> list = new ArrayList<Sheep>();
		String[][] r = processQuery("SELECT * FROM sheep WHERE farm_id = '" + farm.getFarmId() + "'");
		for (int i = 0; i < r.length; i++) {
			list.add(new Sheep(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5]));
		}
		return list;
	}
	
	public boolean removeSheep(Sheep sheep) {
		Statement s = conn.createStatement();
		s.executeUpdate("DELETE FROM sheep WHERE id = '" + sheep.getId + "'");
	}
	
	public ArrayList<Farm> getFarms(User user) {
		ArrayList<Farm> list = new ArrayList<Farm>();
		String[][] r = processQuery("SELECT * FROM access_rights WHERE user_id = '" + user.getUserId + "'");
		for (int i = 0; i < r.length; i++) {
			list.add(new Farm(r[i][0],r[i][1]));
		}
		return list;
	}
	
	public boolean addAccessRights(User user, Farm farm) {
		Statement s = conn.createStatement();
		try{
			s.executeUpdate("INSERT INTO access_rights (user_id,farm_id) VALUES('" + user.getUserId + "','" + farm.getFarmId + "')");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean removeAccessRights(User user, Farm farm) {
		Statement s = conn.createStatement();
		try{
			s.executeUpdate("DELETE FROM access_rights WHERE '" + user.getUserId + "' = user_id AND '" + farm.getFarmId + "' = farm_id");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean editUser(int userId, User user) {
		Statement s = conn.createStatement();
		try{
			s.executeUpdate("UPDATE user SET name = '" + user.getName() + "', password = '" + user.getPassword + "', phone_number = '" + user.getPhoneNumber + "', "+
					"mobile_number = '" + user.mobileNumber + "', email = '" + user.getEmail + "' WHERE id = " + userId);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public ArrayList<SheepStatus> getSheepStatus(Farm farm) {
		ArrayList<SheepStatus> list = new ArrayList<SheepStatus>();
		String[][] r = processQuery("SELECT * FROM sheep_status WHERE farm_id = '" + farm.getFarmId + "'");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepStatus(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5],r[i][6]));
		}
		return list;
	}
	
	public ArrayList<SheepAlarm> getSheepAlarm(Farm farm) {
		ArrayList<SheepAlarm> list = new ArrayList<SheepAlarm>();
		String[][] r = processQuery("SELECT * FROM sheep_alert WHERE farm_id = '" + farm.getFarmId + "'");
		for (int i = 0; i < r.length; i++) {
			list.add(new SheepAlert(r[i][0],r[i][1],r[i][2],r[i][3],r[i][4],r[i][5],r[i][6]));
		}
		return list;
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
