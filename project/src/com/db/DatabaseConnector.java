package com.db;

import java.sql.*;

public class DatabaseConnector {
	private String url;
	private String username;
	private String database;
	private String password;
	private Connection conn;

	public DatabaseConnector() {
		try{
			conn = DriverManager.getConnection (url, username, password);
			System.out.println ("Database connection established");
		}
		catch(Exception e)
		{
			System.err.println ("Cannot connect to database server"+e.toString());
		}
	}

}
