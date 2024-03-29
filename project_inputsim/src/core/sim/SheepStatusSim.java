package core.sim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.Timer;
import com.db.*;
import com.net.Server;

import core.classes.GPSPosition;
import core.classes.Message;
import core.classes.Sheep;
import core.classes.SheepAlert;
import core.classes.SheepStatus;
import core.settings.*;


/** Simulator to create sheep statuses and invoke alerts.
 * 
 * @category Simulator/Server
 * @author Lars Erik
 * @version 0.1
 */
public class SheepStatusSim {
	// Longditude
	private static final double map_x_max = 10.22485;
	private static final double map_x_min = 8.8173;
	private static final double x_diff = 1.40755;
	private static final int x_diff_int = 140755;
	// Latitude
	private static final double map_y_max = 62.80961;
	private static final double map_y_min = 62.23547;
	private static final double y_diff = 0.57414;
	private static final int y_diff_int = 57414;
	
	private static final int DEFAULT_INTERVAL = 1440;
	private static final int ALERT_INTERVAL = 1440;
	private int statusInterval;
	private DatabaseConnector sq;
	private Random rand;
	public Server server;
	private Timer statusTimer, alertTimer;
	
	private HashMap<String,GPSPosition> lastPositions;
	private ArrayList<Sheep> livingSheep;
	private String[][] farms;
	

	/** Default constructor. Sets interval to one hour.
	 * 
	 */
	public SheepStatusSim(Server server) {
		statusInterval = DEFAULT_INTERVAL*60000;
		sq = new DatabaseConnector(new Settings());
		rand = new Random();
		this.server = server;
		statusTimer = new Timer(statusInterval, updateStatus);
		alertTimer = new Timer(ALERT_INTERVAL, updateStatus);
		String [][] farmInput = sq.listFarms();
		farms = new String[farmInput.length][2];
		for (int i = 0; i < farmInput.length; i++) {
			farms[i][0] = farmInput[i][0];
			farms[i][1] = farmInput[i][1];
		}
		livingSheep = sq.listSheep();
		farms = sq.listFarms();

	}

	/** Constructor that sets the status intervals equal to the 
	 * parameter in minutes.
	 * 
	 * @param interval
	 */
	public SheepStatusSim(int interval, Server server) {
		statusInterval = interval*60000;
		sq = new DatabaseConnector(new Settings());
		rand = new Random();
		this.server = server;
		statusTimer = new Timer(statusInterval, updateStatus);	
		alertTimer = new Timer(ALERT_INTERVAL, updateStatus);
		String [][] farmInput = sq.listFarms();
		farms = new String[farmInput.length][2];
		for (int i = 0; i < farmInput.length; i++) {
			farms[i][0] = farmInput[i][0];
			farms[i][1] = farmInput[i][1];
		}
		livingSheep = sq.listSheep();
		farms = sq.listFarms();
	}

	/** Returns timer interval.
	 * 
	 * @return
	 */
	public int getTimerInterval() {
		return statusInterval;
	}

	/** Sets the timer interval to specified amount and notifies the server.
	 * Also restarts the timer so the proper interval is selected.
	 * 
	 * @param timerInterval
	 */
	public void setTimerInterval(int timerInterval) {
		this.statusInterval = timerInterval;
		server.display("Simulator interval set to " + timerInterval +" minutes");
		statusTimer.setDelay(statusInterval);
		statusTimer.restart();
	}

	/** Starts the timer and keeps it running until the program terminates or stop is called.
	 * 
	 */
	public void start() {
		statusTimer.start();
		server.display("Simulator started. Interval: " + statusInterval);
	}

	/** Stops the timer.
	 * 
	 */
	public void stop() {
		statusTimer.stop();
		server.display("Simulator stopped");
	}

	/** Adds alert via database constructor. 
	 * 
	 * @param farmId
	 * @param amount
	 * @return 
	 */
	public boolean addAlert(int farmId, int amount) {
		String[][] alerts = new String[amount][7];
		ArrayList<Sheep> thisFarm = new ArrayList<>();
		
		for (int k = 0; k < livingSheep.size(); k++) {
			if(livingSheep.get(k).getFarmId() == farmId){
				thisFarm.add(livingSheep.get(k));
			}
		}

		for (int i = 0; i < amount; i++) {
			alerts[i][0] = Integer.toString(thisFarm.get(rand.nextInt(thisFarm.size())).getId());
			alerts[i][1] = Long.toString(System.currentTimeMillis()/1000);
			alerts[i][2] = Integer.toString(rand.nextInt(2)+39);
			alerts[i][3] = Integer.toString(rand.nextInt(30)+75);
			int intlat = rand.nextInt(y_diff_int);
			double doublelat = (double) intlat;
			doublelat /= 100000;
			alerts[i][5] = Double.toString(doublelat+map_y_min);
			int intlong = rand.nextInt(x_diff_int);
			double doublelong = (double) intlong;
			doublelong /= 100000;
			alerts[i][4] = Double.toString(doublelong+map_x_min);
			alerts[i][6] = Integer.toString(farmId);
			server.notifier.recieveAlert(new SheepAlert(Integer.parseInt(alerts[i][0]), Integer.parseInt(alerts[i][1]), 
					Float.parseFloat(alerts[i][2]), Integer.parseInt(alerts[i][3]), new GPSPosition(Double.parseDouble(alerts[i][4]), 
							Double.parseDouble(alerts[i][5])), Integer.parseInt(alerts[i][6])));
		}
		return true;
	}

	/** Adds alert for a random farm via database constructor.
	 * 
	 * @param amount
	 * @deprecated
	 */
	public boolean addAlert(int amount) {
		String[][] alerts = new String[amount][7];
		ArrayList<Message> alertList = new ArrayList<>();

		for (int i = 0; i < livingSheep.size(); i++) {
			alerts[i][0] = Integer.toString(livingSheep.get(i).getId());
			alerts[i][1] = Long.toString(System.currentTimeMillis()/1000);
			alerts[i][2] = Integer.toString(rand.nextInt(1)+39);
			alerts[i][3] = Integer.toString(rand.nextInt(30)+60);
			int intlat = rand.nextInt(y_diff_int);
			double doublelat = (double) intlat;
			doublelat /= 100000;
			alerts[i][5] = Double.toString(doublelat+map_y_min);
			int intlong = rand.nextInt(x_diff_int);
			double doublelong = (double) intlong;
			doublelong /= 100000;
			alerts[i][4] = Double.toString(doublelong+map_x_min);
			alerts[i][6] = Integer.toString(livingSheep.get(i).getFarmId());
			alertList.add(new SheepAlert(Integer.parseInt(alerts[i][0]),Integer.parseInt(alerts[i][1])
			, Float.parseFloat(alerts[i][2]),Integer.parseInt(alerts[i][3])
			, new GPSPosition(Double.parseDouble(alerts[i][4]),Double.parseDouble(alerts[i][5])),Integer.parseInt(alerts[i][6])));
		}
		
		return true;
	}


	/**Adds status via SqlConection to the database. Randomly generates statuses
	 * for all the sheep in the database
	 * 
	 */
	private void addStatus() {
		String[][] stats = new String[livingSheep.size()][7];
		
		for (int i = 0; i < livingSheep.size(); i++) {
			stats[i][0] = Integer.toString(livingSheep.get(i).getId());
			stats[i][1] = Long.toString(System.currentTimeMillis()/1000);
			stats[i][2] = Integer.toString(rand.nextInt(1)+39);
			stats[i][3] = Integer.toString(rand.nextInt(30)+60);
			int intlat = rand.nextInt(y_diff_int);
			double doublelat = (double) intlat;
			doublelat /= 100000;
			stats[i][5] = Double.toString(doublelat+map_y_min);
			int intlong = rand.nextInt(x_diff_int);
			double doublelong = (double) intlong;
			doublelong /= 100000;
			stats[i][4] = Double.toString(doublelong+map_x_min);
			stats[i][6] = Integer.toString(livingSheep.get(i).getFarmId());
		}
		server.notifier.recieveStatus(stats);
	}

	/** Action listener class that listens for the timer interval.
	 * When it catches an event, it calls addStatus() to update the database.
	 * 
	 */
	ActionListener updateStatus = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == statusTimer){
				addStatus();
			}
			if(evt.getSource() == alertTimer){
				addAlert(rand.nextInt(farms.length)+1,1);
			}
		}
	};
	
	/** This method is not used in this project. Instead we use the GUI to demo
	 * Alert functionality. This method would in theory let an alarm be simulated
	 * once every 1440 minutes.
	 * 
	 */
	public void startAlertTimer() {
		alertTimer.start();
	}
}

