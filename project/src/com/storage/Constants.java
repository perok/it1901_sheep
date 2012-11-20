package com.storage;

/**
 * Application constants
 * @author perok
 *
 */
public class Constants {
	/** Preferred default window size-properties */
	public  static final int INIT_SCREEN_WIDTH      = 900, 
							 INIT_SCREEN_HEIGHT 	= 800;
	
	public static final int sheepStatus = 0;
	public static final int sheepAlert = 1;

	
	/**
	 * Value is 32.
	 * Valuas >= 32 are private data not handles by Qt.
	 */
	public final static int QtSheepDataRole = 32;
	
	/** The title of this application */
	public static final String title = "SSA© (Sheep Surveilance Application)";
	
	/** About-message shown in our about-window */
	public static final String about = "The Greatest and Only SSA© (Sheep Surveilance Application).\n\n" 
    		+ "Created by: \n\tAnders Sildnes \n\tLars erik Grasdal \n\tTor Økland Barstad"
    		+ "\n\tSvenn Kvelstad \n\tPer Øyvind Kanestrøm";
	
}
