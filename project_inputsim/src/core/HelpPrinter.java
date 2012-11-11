package core;

public final class HelpPrinter {

	public static String printHelp() {
		String help = new String("Following command are available: \n");
		help += "sim help - sim commands";
		
		return help;
	}
	
	public static String printSimHelp() {
		String help = new String("Following command are available for sim: \n");
		help += "sim help - sim commands";
		return help;
	}
	
	public static String printPopHelp() {
		String help = new String("Following command are available for pop: \n");
		help += "pop add x - adds x number of sheep to random farms";
		help += "pop add x y - adds x number of sheep to farm with id y";
		return help;
	}
	
	public static String alertHelp() {
		String help = new String();
		return help;
	}

}
