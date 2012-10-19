package com.gui;

import java.awt.Dimension;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
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
	private QHBoxLayout qhblSwingLayout;
	private QComponentHost qchHost;
	
	private JXMapKit mapKit;
	
	/** Constructor. Initialize..
	 */
	public MapWidget()
	{
		/* Initialize graphic components */
		initWidget();
		initLayout();
	}
	
	private void initWidget()
	{
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		JXMapViewer mapKitChild;		
			
		this.mapKit  = new JXMapKit();
		this.qchHost = new QComponentHost(mapKit);
		
		 mapKitChild = (JXMapViewer) this.mapKit.getComponent(0);
		
		this.mapKit.setTileFactory(tileFactory);		
		this.mapKit.setVisible(true);
		this.mapKit.setZoom(1);
		this.mapKit.setAddressLocation(new GeoPosition(50.11, 8.68));
		
		mapKitChild.setMinimumSize(new Dimension(800, 800));
		mapKitChild.setPreferredSize(new Dimension(800, 800));
		
		// This line is order specific - adding it at the top of this function causes an error
		// why?
		tileFactory.setThreadPoolSize(8);
	}
	
	private void initLayout()
	{
		this.qhblSwingLayout = new QHBoxLayout();
		
		this.qhblSwingLayout.addWidget(this.qchHost);
		super.setLayout(this.qhblSwingLayout);
	}
}

/* EOF */