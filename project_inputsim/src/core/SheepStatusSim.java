package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SheepStatusSim {
	private int timerInterval;
	private int numberOfSheep;
	SqlConnection sq;
	
	public SheepStatusSim() {
		timerInterval = 60;
		new Timer(timerInterval*1000, updateStatus).start();
		 sq = new SqlConnection();
	}
	
	public SheepStatusSim(int interval) {
		timerInterval = interval;
		new Timer(timerInterval*1000, updateStatus).start();
		 sq = new SqlConnection();
	}


	public void addAlert() {
		

	}
	
	public void kill() {
		
	}

	ActionListener updateStatus = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          
	      }
	  };

}

