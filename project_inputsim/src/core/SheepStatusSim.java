package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SheepStatusSim {
	private int timerInterval;
	
	public SheepStatusSim() {
		timerInterval = 60;
		new Timer(timerInterval*1000, updateStatus).start();
	}
	
	public SheepStatusSim(int interval) {
		timerInterval = interval;
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

