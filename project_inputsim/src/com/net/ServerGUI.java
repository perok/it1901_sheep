package com.net;

import javax.swing.*;
import core.*;
import core.sim.DatabasePopulator;
import java.awt.*;
import java.awt.event.*;

/**GUI for the Server. Also allows admin commands.
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
	private DatabasePopulator populator;

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
		command.addActionListener(this);
		cmdButton = new JButton("Execute command");
		cmdButton.addActionListener(this);
		cmdButton.registerKeyboardAction(cmdButton.getActionForKeyStroke(
				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
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
		appendEvent("Start the server before entering commands \n");
	}		

	/** Appends an event given as string to the log window
	 * 
	 * @param str
	 */
	public void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(event.getDocument().getLength());
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
			else{
				int port;
				try {
					port = Integer.parseInt(tPortNumber.getText().trim());
				}
				catch(Exception er) {
					appendEvent("Invalid port number");
					return;
				}
				server = new Server(port, this);
				new ServerRunning().start();
				stopStart.setText("Stop");
				tPortNumber.setEditable(false);
				populator = new DatabasePopulator();
			}
		}
		if(e.getSource() == cmdButton || e.getSource() == command){
			performCommand(command.getText());
			command.setText("");
		}
	}

	/**Starts the GUI
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		new ServerGUI(1500);
	}

	/**Method to free the port when closing application
	 * 
	 */
	public void windowClosing(WindowEvent e) {
		if(server != null) {
			try {
				server.stop();
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		dispose();
		System.exit(0);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/**Performs commands given by the Admin GUI. Splits up the string into
	 * an array split by whitespaces. The following switches decode the command
	 * and give the proper respone. 
	 */
	public void performCommand(String cmd) {
		String[] decoded = cmd.split("\\s+");
		switch(decoded[0]){

		// General help
		case("help"):
			appendEvent(HelpPrinter.printHelp());
		break;

		// Alert invoker
		case("alert"):
			switch(decoded[1]){
			case("help"): 
				appendEvent(HelpPrinter.printAlertHelp());
			break;
			}
		break;

		// Simulator
		case("sim"):
			switch(decoded[1]){
			case("help"):
				appendEvent(HelpPrinter.printSimHelp());
			break;
			case("start"):
				server.simulator.start();
			break;
			case("stop"):
				server.simulator.stop();
				break;
			case("interval"):
				server.simulator.setTimerInterval(Integer.parseInt(decoded[2]));
				break;
			}
		break;

		// Populator
		case("pop"):
			switch(decoded[1]){
			case("help"):
				appendEvent(HelpPrinter.printPopHelp());
			break;

			case("add"):
				if(decoded[2].equalsIgnoreCase("sheep")) {
					populator.addSheep(Integer.parseInt(decoded[3]), Integer.parseInt(decoded[4]));
				}
			break;
			
			case("sheep"):
				if(decoded[2].equalsIgnoreCase("alive")) {
					if(Integer.parseInt(decoded[4]) == 1)
						populator.reviveSheep(Integer.parseInt(decoded[3]));
					else
						populator.killSheep(Integer.parseInt(decoded[3]));
				}
			break;

			default:
				appendEvent("Invalid command \n");
				appendEvent(HelpPrinter.printPopHelp());
				break;
			}
		break;

		// Database
		case("db"):
		switch(decoded[1]){
		case("help"):
			appendEvent(HelpPrinter.printDbHelp());
		break;

		case("ls"):
			if(decoded[2].equalsIgnoreCase("users")) {
				appendEvent("--List of users-- \n");
				appendEvent(populator.listUsers());
			}
			else if(decoded[2].equalsIgnoreCase("farms")) {
				appendEvent("--List of farms-- \n");
				appendEvent(populator.listFarms());
			}
		break;

		case("access"):
			if(decoded[2].equalsIgnoreCase("add")) {
				populator.addAccessRights(Integer.parseInt(decoded[3]), Integer.parseInt(decoded[4]));
			}
			else if(decoded[2].equalsIgnoreCase("delete")) {
				populator.removeAccessRights(Integer.parseInt(decoded[3]), Integer.parseInt(decoded[4]));
			}

		break;
		
		case("purge"):
			if(decoded[2].equalsIgnoreCase("sheep")) {
				populator.deleteSheep();
			}
			else if(decoded[2].equalsIgnoreCase("status")) {
				populator.deleteStatus();
			}

		break;

		}
		break;
		default:
			appendEvent("Invalid command. Try 'help' \n");
		}

	}

	/**Internal class that runs server as a Thread.
	 *
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


