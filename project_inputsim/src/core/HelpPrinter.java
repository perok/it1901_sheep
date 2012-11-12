package core;

public final class HelpPrinter {

	public static String printHelp() {
		String help = new String("Following command are available: \n");
		help += "sim help - Simulator commands \n";
		help += "pop help - Populator commands \n";
		help += "alert help - Alert commands \n";
		help += "db help - Database commands \n";
		
		return help;
	}
	
	public static String printSimHelp() {
		String help = new String("Following command are available for sim: \n");
		help += "sim help - Sim commands \n";
		return help;
	}
	
	public static String printPopHelp() {
		String help = new String("Following command are available for pop: \n");
		help += "pop add sheep x y - Adds x number of sheep to farm with id y \n";
		return help;
	}
	
	public static String alertHelp() {
		String help = new String();
		help += "alert invoke x - Invokes alert for farm with id x \n";
		return help;
	}
	
	public static String dbHelp() {
		String help = new String();
		help += "db ls farms - Lists all farms with Id \n";
		help += "db ls users - Lists all users with name and Id \n";
		help += "db access add x y - Adds access for user id x to farm id y \n";
		return help;
	}

}
