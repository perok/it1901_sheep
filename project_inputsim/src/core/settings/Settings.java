package core.settings;

/**Class containing system settings
 * 
 * @author Svenn
 *
 */
public class Settings {
	
	private String dbUrl;
	private String dbDatabase;
	private String dbUser;
	private String dbPassword;
	private String skypeUser;
	private String skypePassword;
	private String googleUser;
	private String googlePassword;
	 
	public Settings() {
		init();
		
	}
	/**Init methode
	 * 
	 */
	private void init() {
		SettingsReader reader = new SettingsReader();
		String[] settings = reader.readSettings();
		dbUrl = settings[0];
		dbDatabase = settings[1];
		dbUser = settings[2];
		dbPassword = settings[3];
		skypeUser = settings[4];
		skypePassword = settings[5];
		googleUser = settings[6];
		googlePassword = settings[7];
	}
	
	/**Prints database URL
	 * 
	 */
	public void print() {
		System.out.println(dbUrl);
	}
	
	/**Returns database URL
	 * 
	 * @return
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**Sets database URL
	 * 
	 * @param dbUrl
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**Returns database name
	 * 
	 * @return
	 */
	public String getDbDatabase() {
		return dbDatabase;
	}

	/**Sets database name
	 * 
	 * @param dbDatabase
	 */
	public void setDbDatabase(String dbDatabase) {
		this.dbDatabase = dbDatabase;
	}

	/**Returns database password
	 * 
	 * @return
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**Sets database password
	 * 
	 * @param dbPassword
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**Returns dbUser
	 * 
	 * @return
	 */
	public String getDbUser() {
		return dbUser;
	}

	/**Set dbUser
	 * 
	 * @param dbUser
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	/**Returns skypeUser
	 * 
	 * @return
	 */
	public String getSkypeUser() {
		return skypeUser;
	}

	/**Set skype user
	 * 
	 * @param skypeUser
	 */
	public void setSkypeUser(String skypeUser) {
		this.skypeUser = skypeUser;
	}

	/**Return skype password
	 * 
	 * @return
	 */
	public String getSkypePassword() {
		return skypePassword;
	}

	/**Set skype password
	 * 
	 * @param skypePassword
	 */
	public void setSkypePassword(String skypePassword) {
		this.skypePassword = skypePassword;
	}

	/**Returns Google user
	 * 
	 * @return
	 */
	public String getGoogleUser() {
		return googleUser;
	}

	/**Sets Google user
	 * 
	 * @param googleUser
	 */
	public void setGoogleUser(String googleUser) {
		this.googleUser = googleUser;
	}

	/**Returns Google password
	 * 
	 * @return
	 */
	public String getGooglePassword() {
		return googlePassword;
	}

	/**Set Google password
	 * 
	 * @param googlePassword
	 */
	public void setGooglePassword(String googlePassword) {
		this.googlePassword = googlePassword;
	}
	
	/**Returns string with dbUrl, dbDatabase, dbUser, dbPassword
	 * 
	 */
	public String toString() {
		return dbUrl + "  " + dbDatabase + "  " + dbUser + "  " + dbPassword;
	}

}
