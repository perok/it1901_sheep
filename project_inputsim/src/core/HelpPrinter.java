package core;

/** Static class for less strings in the ServerGUI class
 * @author Lars Erik
 *
 */
public final class HelpPrinter {

	/**Returns general help message.
	 * 
	 * @return
	 */
	public static String printHelp() {
		String help = new String("Following command are available: \n");
		help += "sim help - Simulator commands \n";
		help += "pop help - Populator commands \n";
		help += "alert help - Alert commands \n";
		help += "db help - Database commands \n";
		
		return help;
	}
	
	/**Returns general help message for the status simulator.
	 * 
	 * @return
	 */
	public static String printSimHelp() {
		String help = new String("Following command are available for sim: \n");
		help += "sim start - Starts the simulator \n";
		help += "sim stop - Stops the simulator \n";
		help += "sim interval x - Sets interval of sim to x minutes \n";
		return help;
	}
	
	/**Returns general help message for the database populator.
	 * 
	 * @return
	 */
	public static String printPopHelp() {
		String help = new String("Following command are available for pop: \n");
		help += "pop add sheep x y - Adds x number of sheep to farm with id y \n";
		help += "pop sheep alive x y - Sets sheep with id x to status y(0/1)";
		help += "pop add user a b c d e f - Adds a user with username a, name b, \n password c, phonenumber d, email e";
		return help;
	}
	
	/**Returns general help message for alert invoking.
	 * 
	 * @return
	 */
	public static String printAlertHelp() {
		String help = new String();
		help += "alert invoke x - Invokes alert for farm with id x \n";
		return help;
	}
	
	/**Returns general help message for db manipulation.
	 * 
	 * @return
	 */
	public static String printDbHelp() {
		String help = new String();
		help += "db ls farms - Lists all farms with id \n";
		help += "db ls users - Lists all users with name and id \n";
		help += "db ls users - Lists all sheep with name, id and farm id \n";
		help += "db access add x y - Adds access for user id x to farm id y \n";
		help += "db access remove x y - Removes access for user id x to farm id y \n";
		help += "db purge sheep - Purges all sheep from database \n";
		help += "db purge status - Purges all statuses from database \n";
		return help;
	}

}
