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
		help += "sim help - Sim commands \n";
		return help;
	}
	
	/**Returns general help message for the database populator.
	 * 
	 * @return
	 */
	public static String printPopHelp() {
		String help = new String("Following command are available for pop: \n");
		help += "pop add sheep x y - Adds x number of sheep to farm with id y \n";
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
		help += "db ls farms - Lists all farms with Id \n";
		help += "db ls users - Lists all users with name and Id \n";
		help += "db access add x y - Adds access for user id x to farm id y \n";
		return help;
	}

}
