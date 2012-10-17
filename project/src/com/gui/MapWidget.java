package com.gui;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.research.qtjambiawtbridge.QComponentHost;

/** Stream an external source of media
 * 
 * @author Gruppe 10 <3
 */
public class MapWidget extends QWidget
{
	QHBoxLayout qhblSwingLayout;
	
	JXMapKit mapKit;
	
	/** Constructor. Initialize..
	 */
	public MapWidget()
	{
		/* Initialize graphic components */
		initWidget();
		initLayout();
		
		/*DAWdwaijdpawdwaodjwaødwadaw**/poop();/*dawdadawdad*kosfsef*/
	}
	
	private void initWidget()
	{
		// TODO: Initialize your awt-component here
		//		 and make sure it's declared as a class field.
		
	}
	
	private void initLayout()
	{
		this.qhblSwingLayout = new QHBoxLayout();
		
		// TODO: Add your awt-component here, e.g.
		
		/* *****																****
		 * **** qhblSwingLayout.addWidget(new QComponentHost(MyAwtComponent));   ***
		 * *****																****
		 */
	}
	
	private void poop()
	{
		
		mapKit = new JXMapKit();
		
		mapKit.setVisible(true);
		
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapKit.setTileFactory(tileFactory);
		
		
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);

		// Set the focus
		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

		mapKit.setZoom(7);
		mapKit.setAddressLocation(frankfurt);
		
		
		
		QHBoxLayout lay = new QHBoxLayout();
//		JPanel jPanel = new JPanel();
//		jPanel.add(new JLabel("Hello"));
		lay.addWidget(new QComponentHost(mapKit));
		
		super.setLayout(lay);			
	}
	
	

}

/* EOF */