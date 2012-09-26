package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import core.settings.Settings;

public class SqlConnection {
	private Connection conn;
	private Settings settings;
	
	public SqlConnection() {
		try
		{
			settings = new Settings();
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			String url = "jdbc:mysql://" + settings.getDbUrl() +"/" + settings.getDbDatabase();
			conn = DriverManager.getConnection (url, settings.getDbUser(), settings.getDbPassword());
			System.out.println ("Database connection established");
		}
		catch (Exception e)
		{
			System.err.println ("Cannot connect to database server"+e.toString());
		}
	}
	
	public void insertSheepStatus() {
		
	}
	
	public void insertSheepAlert() {
		
	}
	
	public int getNumberOfSheep() {
		String[][] results = processQuery("SELECT * FROM sheep");
		return results.length;
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
