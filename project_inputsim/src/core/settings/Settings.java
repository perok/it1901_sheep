package core.settings;

public class Settings {
	
	private String dbUrl;
	private String dbDatabase;
	private String dbUser;
	private String dbPassword;
	 
	public Settings() {
		SettingsReader reader = new SettingsReader();
		String[] settings = reader.readSettings();
		dbUrl = settings[0];
		dbDatabase = settings[1];
		dbUser = settings[2];
		dbPassword = settings[3];
	}
	
	
	public void print() {
		System.out.println(dbUrl);
	}
	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbDatabase() {
		return dbDatabase;
	}

	public void setDbDatabase(String dbDatabase) {
		this.dbDatabase = dbDatabase;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

}
