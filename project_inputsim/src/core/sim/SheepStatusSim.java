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
import core.classes.Sheep;
import core.classes.SheepAlert;
import core.settings.*;


/** Simulator to create sheep statuses and invoke alerts.
 * 
 * @category Simulator/Server
 * @author Lars Erik
 * @version 0.1
 */
public class SheepStatusSim {
	private static final double map_x_max = 50;
	private static final double map_x_min = 40;
	private static final double map_y_max = 70;
	private static final double map_y_min = 60;
	
	private static final int DEFAULT_INTERVAL = 60;
	private static final int ALERT_INTERVAL = 1440;
	private int statusInterval;
	private DatabaseConnector sq;
	private Random rand;
	private Server server;
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
		statusTimer = new Timer(timerInterval, updateStatus);
		statusTimer.start();
	}

	/** Starts the timer and keeps it running until the program terminates or stop is called.
	 * 
	 */
	public void start() {
		statusTimer.start();
		server.display("Simulator started");
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
			alerts[i][2] = Integer.toString(rand.nextInt(4)+37);
			alerts[i][3] = Integer.toString(rand.nextInt(40)+140);
			alerts[i][4] = Integer.toString(rand.nextInt(400));
			alerts[i][5] = Integer.toString(rand.nextInt(400));
			alerts[i][6] = Integer.toString(farmId);
			server.notifier.recieveAlert(new SheepAlert(Integer.parseInt(alerts[i][0]), Integer.parseInt(alerts[i][1]), 
					Integer.parseInt(alerts[i][2]), Integer.parseInt(alerts[i][3]), new GPSPosition(Integer.parseInt(alerts[i][4]), 
							Integer.parseInt(alerts[i][5])), Integer.parseInt(alerts[i][6])));
		}
		return true;
	}

	/** Adds alert for a random farm via database constructor.
	 * 
	 * @param amount
	 */
	public boolean addAlert(int amount) {
		String[][] alerts = new String[amount][7];

		for (int i = 0; i < amount; i++) {
			alerts[i][0] = Integer.toString(i+1);
			alerts[i][1] = Long.toString(System.currentTimeMillis()/1000);
			alerts[i][2] = Integer.toString(rand.nextInt(4)+37);
			alerts[i][3] = Integer.toString(rand.nextInt(40)+140);
			alerts[i][4] = Integer.toString(rand.nextInt(0));
			alerts[i][5] = Integer.toString(rand.nextInt(0));
			alerts[i][6] = Integer.toString(rand.nextInt(farms.length)+1);
			server.notifier.recieveAlert(new SheepAlert(Integer.parseInt(alerts[i][0]), Integer.parseInt(alerts[i][1]), 
					Integer.parseInt(alerts[i][2]), Integer.parseInt(alerts[i][3]), new GPSPosition(Integer.parseInt(alerts[i][4]), 
							Integer.parseInt(alerts[i][5])), Integer.parseInt(alerts[i][6])));
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
			stats[i][2] = Integer.toString(rand.nextInt(4)+37);
			stats[i][3] = Integer.toString(rand.nextInt(40)+140);
			stats[i][4] = Integer.toString(rand.nextInt(400));
			stats[i][5] = Integer.toString(rand.nextInt(400));
			stats[i][6] = Integer.toString(livingSheep.get(i).getFarmId());
		}
		server.notifier.recieveStatus(stats);
	}

	/**Action listener class that listens for the timer interval.
	 * When it catches an event, it calls addStatus() to update the database.
	 * 
	 */
	ActionListener updateStatus = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == statusTimer){
				addStatus();
			}
			if(evt.getSource() == alertTimer){
				addAlert();
			}
		}
	};

}

