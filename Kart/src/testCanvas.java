import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.research.qtjambiawtbridge.QComponentHost;


public class testCanvas extends QWidget{


	// You must retain a hard-reference to your AWT object graph
	//  that has been offered up to Qt.
	private JXMapKit mapKit;
	
	// You must retain a hard-reference to the Qt object graph
	//  as well while you expect to be available via the Qt EventLoop.
	private QGridLayout layout;

	public testCanvas() {
		super.setMinimumWidth(200);
		super.setMinimumHeight(300);
		layout = new QGridLayout(this);
		layout.setGeometry(new QRect(0, 0, 1000, 1000));
		mapKit = new JXMapKit();//this);

		//JFrame frame = new JFrame();
		
		
		
		//mapKit.setSize(800, 600);
		//mapKit.setBounds(0, 0, 200, 300);
		mapKit.setVisible(true); 	
		
		
		System.out.println("CAKE "+this.width() + "  " +this.height());
		
		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapKit.setTileFactory(tileFactory);
		
		
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);

		// Set the focus
		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

		mapKit.setZoom(7);
		mapKit.setAddressLocation(frankfurt);
		
//		frame.add(mapKit);
//		frame.pack();
//		frame.setSize(400, 600);
//		frame.setVisible(true);
		
		
		layout.addWidget(new QComponentHost(mapKit));
	}

	public static void doWork() {
		System.out.println("Initializing GUI");
		testCanvas map = new testCanvas();
		
		map.show();
		
		QApplication.exec();

		// We destroy the hard-references to the objects we no longer need
		//  (before shutting down main QApplication)
/*		testCanvas.layout = null;

		if(testCanvas.mapViewer != null) {
			testCanvas.mapViewer.setVisible(false);
			//awtInQt.panel1.set
			testCanvas.mapViewer = null;
		}*/
	}

	public static void main(String[] args) {
		System.out.println("Application starting");
		QApplication.initialize(args);

		// Using a method to ensure QtInAwt destruction before QApplication
		doWork();

//		QApplication.shutdown();

		java.awt.Toolkit.getDefaultToolkit().sync();

		System.exit(0);
	}

}
