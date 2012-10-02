package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class SheepStatusSim {
	private int timerInterval;
	private final int numberOfSheep;
	private SqlConnection sq;
	private Random rand;
	private int numberOfFarms;

	public SheepStatusSim() {
		timerInterval = 60;
		sq = new SqlConnection();
		numberOfSheep = sq.getNumberOfSheep();
		rand = new Random();
		numberOfFarms = sq.getNumberOfFarms()	;
	}

	public SheepStatusSim(int interval) {
		timerInterval = interval;
		sq = new SqlConnection();
		numberOfSheep = sq.getNumberOfSheep();
		rand = new Random();
		numberOfFarms = sq.getNumberOfFarms();
	}

	public void init() {
		new Timer(timerInterval, updateStatus).start();	
		while (true) {

		}
	}

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
		}
		sq.insertSheepAlert(alerts);
	}

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
		sq.insertSheepStatus(stats);
	}

	public void kill() {

	}

	ActionListener updateStatus = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			System.out.println("Action registered");
//			addStatus();
		}
	};

}

