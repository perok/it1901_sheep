package core.settings;

public class Settings {
	
	private String host;
	private int port;
	 
	public Settings() {
		SettingsReader reader = new SettingsReader();
		String[] settings = reader.readSettings();
		host = settings[0];
		port = Integer.parseInt(settings[1]);
		
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
