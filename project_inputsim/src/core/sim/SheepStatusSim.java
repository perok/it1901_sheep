package core.sim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import com.db.*;
import com.net.Server;

import core.classes.GPSPosition;
import core.classes.SheepAlert;
import core.settings.*;


/** Simulator to create sheep statuses and invoke alerts.
 * 
 * @category Simulator/Server
 * @author Lars Erik
 * @version 0.1
 */
public class SheepStatusSim {
	private final int DEFAULT_INTERVAL = 60;
	private int timerInterval;
	private final int numberOfSheep;
	private DatabaseConnector sq;
	private Random rand;
	private int numberOfFarms;
	private Server server;
	private Timer timer;

	/** Default constructor. Sets interval to one minute
	 * 
	 */
	public SheepStatusSim(Server server) {
		timerInterval = DEFAULT_INTERVAL*60000;
		sq = new DatabaseConnector(new Settings());
		numberOfSheep = sq.getNumberOfSheep();
		rand = new Random();
		numberOfFarms = sq.getNumberOfFarms();
		this.server = server;
	}

	/** Constructor that sets the status intervals equal to the 
	 * parameter in minutes.
	 * 
	 * @param interval
	 */
	public SheepStatusSim(int interval,Server server) {
		timerInterval = interval*60000;
		sq = new DatabaseConnector(new Settings());
		numberOfSheep = sq.getNumberOfSheep();
		rand = new Random();
		numberOfFarms = sq.getNumberOfFarms();
		this.server = server;
		timer = new Timer(timerInterval, updateStatus);	
	}

	/** Starts the timer and keeps it running until the program terminates or stop is called.
	 * 
	 */
	public void start() {
		timer.start();
		server.display("Simulator started");
	}
	
	/** Stops the timer.
	 * 
	 */
	public void stop() {
		timer.stop();
		server.display("Simulator stopped");
	}

	/** Adds alert via DatabaseConnector to the database. Randomly generates alerts for
	 * all the sheep in the database. 
	 * @deprecated
	 */
	private void addAlert() {
		String[][] alerts = new String[numberOfSheep][7];

		for (int i = 0; i < numberOfSheep; i++) {
			alerts[i][0] = Integer.toString(i+1);
			alerts[i][1] = Integer.toString(rand.nextInt(3600)+ 1389080800);
			alerts[i][2] = Integer.toString(rand.nextInt(4)+37);
			alerts[i][3] = Integer.toString(rand.nextInt(40)+140);
			alerts[i][4] = Integer.toString(rand.nextInt(400));
			alerts[i][5] = Integer.toString(rand.nextInt(400));
			alerts[i][6] = Integer.toString(rand.nextInt(numberOfFarms)+1);
			server.notifier.recieveAlert(new SheepAlert(Integer.parseInt(alerts[i][0]), Integer.parseInt(alerts[i][1]), 
					Integer.parseInt(alerts[i][2]), Integer.parseInt(alerts[i][3]), new GPSPosition(Integer.parseInt(alerts[i][4]), 
							Integer.parseInt(alerts[i][5])), Integer.parseInt(alerts[i][6])));
		}
	}
	
	/** Adds alert via database constructor. 
	 * 
	 * @param farmId
	 * @param amount
	 */
	public void addAlert(int farmId, int amount) {
		String[][] alerts = new String[amount][7];

		for (int i = 0; i < amount; i++) {
			alerts[i][0] = Integer.toString(i+1);
			alerts[i][1] = Integer.toString(rand.nextInt(3600)+ 1389080800);
			alerts[i][2] = Integer.toString(rand.nextInt(4)+37);
			alerts[i][3] = Integer.toString(rand.nextInt(40)+140);
			alerts[i][4] = Integer.toString(rand.nextInt(400));
			alerts[i][5] = Integer.toString(rand.nextInt(400));
			alerts[i][6] = Integer.toString(rand.nextInt(numberOfFarms)+1);
			server.notifier.recieveAlert(new SheepAlert(Integer.parseInt(alerts[i][0]), Integer.parseInt(alerts[i][1]), 
					Integer.parseInt(alerts[i][2]), Integer.parseInt(alerts[i][3]), new GPSPosition(Integer.parseInt(alerts[i][4]), 
							Integer.parseInt(alerts[i][5])), Integer.parseInt(alerts[i][6])));
		}
	}
	
	/** Adds alert for a random farm via database constructor.
	 * 
	 * @param amount
	 */
	public void addAlert(int amount) {
		String[][] alerts = new String[amount][7];

		for (int i = 0; i < amount; i++) {
			alerts[i][0] = Integer.toString(i+1);
			alerts[i][1] = Integer.toString(rand.nextInt(3600)+ 1389080800);
			alerts[i][2] = Integer.toString(rand.nextInt(4)+37);
			alerts[i][3] = Integer.toString(rand.nextInt(40)+140);
			alerts[i][4] = Integer.toString(rand.nextInt(400));
			alerts[i][5] = Integer.toString(rand.nextInt(400));
			alerts[i][6] = Integer.toString(rand.nextInt(numberOfFarms)+1);
			server.notifier.recieveAlert(new SheepAlert(Integer.parseInt(alerts[i][0]), Integer.parseInt(alerts[i][1]), 
					Integer.parseInt(alerts[i][2]), Integer.parseInt(alerts[i][3]), new GPSPosition(Integer.parseInt(alerts[i][4]), 
							Integer.parseInt(alerts[i][5])), Integer.parseInt(alerts[i][6])));
		}
	}


	/**Adds status via SqlConection to the database. Randomly generates statuses
	 * for all the sheep in the database
	 * 
	 */
	private void addStatus() {
		String[][] stats = new String[numberOfSheep][7];

		for (int i = 0; i < numberOfSheep; i++) {
			stats[i][0] = Integer.toString(i+1);
			stats[i][1] = Integer.toString(rand.nextInt(3600)+ 1389080800);
			stats[i][2] = Integer.toString(rand.nextInt(4)+37);
			stats[i][3] = Integer.toString(rand.nextInt(40)+140);
			stats[i][4] = Integer.toString(rand.nextInt(400));
			stats[i][5] = Integer.toString(rand.nextInt(400));
			stats[i][6] = Integer.toString(rand.nextInt(numberOfFarms)+1);
		}
		server.notifier.recieveStatus(stats);
	}

	/**Action listener class that listens for the timer interval.
	 * When it catches an event, it calls addStatus() to update the database.
	 * 
	 */
	ActionListener updateStatus = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			System.out.println("Action registered");
						addStatus();
		}
	};

}

