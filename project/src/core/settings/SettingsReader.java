package core.settings;	

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SettingsReader {
	private String settingsFile = "resources/settings.txt";
	
	public String[] readSettings() {

		String[] input = new String[2];
		int i = 0;
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new FileInputStream(settingsFile));
			try {
				while (scanner.hasNextLine()){
					scanner.nextLine();
					input[i] = scanner.nextLine();
					i++;
				}
			}
			finally{
				scanner.close();
			}

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input;
	}
}
