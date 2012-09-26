/* This class is copied and purged for evaluation purposes =) */
package main;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.research.qtjambiawtbridge.QComponentHost;

public class AwtInQt extends QWidget {

	// You must retain a hard-reference to your AWT object graph
	//  that has been offered up to Qt.
	private JPanel panel1;
	private JPanel panel2;
	// You must retain a hard-reference to the Qt object graph
	//  as well while you expect to be available via the Qt EventLoop.
	private QGridLayout layout;

	public AwtInQt() {
		super.setMinimumWidth(300);
		super.setMinimumHeight(150);
		layout = new QGridLayout(this);

		// A few Qt widgets
		layout.addWidget(new QLabel("First name:"), 0, 0);
		layout.addWidget(new QLineEdit(), 0, 1);
		layout.addWidget(new QLabel("Last name:"), 1, 0);
		layout.addWidget(new QLineEdit(), 1, 1);

		// The AWT part of the GUI
		{
			JPanel panel = new JPanel();

			panel.setLayout(new GridLayout(1, 2));

			panel.add(new JLabel("Social security number:"));
			panel.add(jTextFieldFactory());

			// Add the AWT panel to Qt's layout
			layout.addWidget(new QComponentHost(panel), 2, 0, 1, 2);

			panel1 = panel;
		}

		{
			JPanel panel = new JPanel();

			panel.setLayout(new GridLayout(2, 4));

			panel.add(new JLabel("Phone number:"));
			panel.add(jTextFieldFactory());

			panel.add(new JLabel("Address:"));
			panel.add(jTextFieldFactory());

			panel.add(new JLabel("Credit card #:"));
			panel.add(jTextFieldFactory());

			panel.add(new JLabel("Expiration date:"));
			panel.add(jTextFieldFactory());

			// Add the AWT panel to Qt's layout
			layout.addWidget(new QComponentHost(panel), 4, 0, 1, 2);

			panel2 = panel;
		}

	}

	private JTextField jTextFieldFactory() {
		JTextField j = new JTextField();
		// Exception on Toolkit thread: sun.awt.X11.XException: Cannot write XdndAware property
		j.setDropTarget(null);  // Sun BUG-ID 7027598
		return j;
	}

	public static void doWork() {
		AwtInQt awtInQt = new AwtInQt();
		awtInQt.show();

		QApplication.exec();

		// We destroy the hard-references to the objects we no longer need
		//  (before shutting down main QApplication)
		awtInQt.layout = null;

		if(awtInQt.panel1 != null) {
			awtInQt.panel1.setVisible(false);
			awtInQt.panel1 = null;
		}
		if(awtInQt.panel2 != null) {
			awtInQt.panel2.setVisible(false);
			awtInQt.panel2 = null;
		}
	}

	public static void main(String[] args) {
		QApplication.initialize(args);

		// Using a method to ensure QtInAwt destruction before QApplication
		doWork();

//		QApplication.shutdown();

		java.awt.Toolkit.getDefaultToolkit().sync();

		System.exit(0);
	}

}
