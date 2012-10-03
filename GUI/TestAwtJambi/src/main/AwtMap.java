package main;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AwtMap extends JPanel{
	AwtMap(){
		this.setLayout(new GridLayout(2, 4));

		this.add(new JLabel("Phone number:"));
		this.add(jTextFieldFactory());
	}
	
	private JTextField jTextFieldFactory() {
		JTextField j = new JTextField();
		// Exception on Toolkit thread: sun.awt.X11.XException: Cannot write XdndAware property
		j.setDropTarget(null);  // Sun BUG-ID 7027598
		return j;
	}
	

}
