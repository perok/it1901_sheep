package core.settings;	

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**Class for reading system settings
 *
 * 
 * @author Svenn
 *
 */
public class SettingsReader {
	private String settingsFile = "resources/settings.txt";
	
	/**Reads settingf from settings file
	 * 
	 * @return
	 */
	public String[] readSettings() {

		String[] input = new String[8];
		int i = 0;
		try {
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
