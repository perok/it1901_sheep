package com.net;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * 
 * @author Lars Erik
 *
 */
public class ServerGUI extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 3L;
	private JButton stopStart;
	private JTextField command;
	private JButton cmdButton;
	private JTextArea event;
	private JTextField tPortNumber;
	private Server server;

	/**Constructor for GUI with desiered portname.
	 * 
	 * @param port
	 */
	ServerGUI(int port) {
		super("Sheep Shield");
		server = null;

		//Startpanel
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField("  " + port);
		north.add(tPortNumber);
		stopStart = new JButton("Start");
		stopStart.addActionListener(this);
		north.add(stopStart);
		add(north, BorderLayout.NORTH);

		//Command panel
		JPanel commandPanel = new JPanel();
		command = new JTextField("",20);
		command.setEditable(true);
		cmdButton = new JButton("Execute command");
		commandPanel.add(command);
		commandPanel.add(cmdButton);
		add(commandPanel, BorderLayout.SOUTH);

		//Event viewer
		JPanel center = new JPanel(new GridLayout(1,1));
		event = new JTextArea(80,80);
		event.setEditable(false);
		appendEvent("Events log.\n");
		center.add(new JScrollPane(event));	
		add(center);

		// Add listner
		addWindowListener(this);
		setSize(400, 600);
		setVisible(true);
	}		

	// append message to the two JTextArea
	// position at the end

	void appendEvent(String str) {
		event.append(str);
		//		event.setCaretPosition(command.getText().length() - 1);

	}

	/**Action listener for the buttons
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == stopStart){
			if(server != null) {
				server.stop();
				server = null;
				tPortNumber.setEditable(true);
				stopStart.setText("Start");
				return;
			}
			// OK start the server	
			int port;
			try {
				port = Integer.parseInt(tPortNumber.getText().trim());
			}
			catch(Exception er) {
				appendEvent("Invalid port number");
				return;
			}
			// ceate a new Server
			server = new Server(port, this);
			// and start it as a thread
			new ServerRunning().start();
			stopStart.setText("Stop");
			tPortNumber.setEditable(false);
		}
		if(e.getSource() == cmdButton){
			command.setText("");
		}
	}

	// entry point to start the Server
	public static void main(String[] arg) {
		// start server default port 1500
		new ServerGUI(1500);
	}

	/*
	 * If the user click the X button to close the application
	 * I need to close the connection with the server to free the port
	 */
	public void windowClosing(WindowEvent e) {
		// if my Server exist
		if(server != null) {
			try {
				server.stop();			// ask the server to close the conection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		// dispose the frame
		dispose();
		System.exit(0);
	}
	// I can ignore the other WindowListener method
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/*
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {
		public void run() {
			server.start();
			stopStart.setText("Start");
			tPortNumber.setEditable(true);
			appendEvent("Server crashed\n");
			server = null;
		}
	}

}


