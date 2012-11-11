package com.net;

import javax.swing.*;

import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
import core.*;
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
	}		

	// append message to the two JTextArea
	// position at the end

	void appendEvent(String str) {
		event.append(str);
	}

		/**Action listener for the buttons
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
		System.out.println(e.getActionCommand());
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

	/**Performs commands given by the Admin GUI.
	 * 
	 */
	public void performCommand(String cmd) {
		String[] decoded = cmd.split("\\s+");
		int length = decoded.length;
		switch(decoded[0]){
		case("help"):
			appendEvent(HelpPrinter.printHelp());
		case("sim"):
			appendEvent(HelpPrinter.printSimHelp());
		case("pop"):
			appendEvent(HelpPrinter.printPopHelp());
		default:
			appendEvent("Invalid command. Try 'help'");
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


