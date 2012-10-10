package GuiDesign;

import com.db.DatabaseConnector;

public class LoginWindow {

	String username;
	String password;
	DatabaseConnector connector = new DatabaseConnector();
	
	loginUser User (String username, String password){
		return connector.loginQuery(username, password);
	}
	
}
