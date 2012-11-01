package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.MouseInputListener;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.input.CenterMapListener;
import org.jdesktop.swingx.input.PanKeyListener;
import org.jdesktop.swingx.input.PanMouseInputListener;
import org.jdesktop.swingx.input.ZoomMouseWheelListenerCenter;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.wms.WMSService;
import org.jdesktop.swingx.mapviewer.wms.WMSTileFactory;

import com.mapWidgetExtras.FancyWaypointRenderer;
import com.mapWidgetExtras.MyWaypoint;
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
		String wmsStatensKartVerk = "http://openwms.statkart.no/skwms1/wms.kartdata2?";
		String layer = "Kartdata2_WMS";
		GeoPosition trondheimLocation = new GeoPosition( 63.430515, 10.395053);
		WMSService wmsService = new WMSService(wmsStatensKartVerk, layer);
		WMSTileFactory wmsTileFactory = new WMSTileFactory(wmsService);
		
		JXMapViewer mapKitChild;		
			
		this.mapKit  = new JXMapKit();
		this.qchHost = new QComponentHost(mapKit);
		
		 mapKitChild = (JXMapViewer) this.mapKit.getComponent(0);
		 
		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapKitChild);
		this.mapKit.addMouseListener(mia);
		this.mapKit.addMouseMotionListener(mia);
		this.mapKit.addMouseListener(new CenterMapListener(mapKitChild));
		this.mapKit.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapKitChild));
		this.mapKit.addKeyListener(new PanKeyListener(mapKitChild));
		
		this.mapKit.setTileFactory(wmsTileFactory);	
		this.mapKit.setVisible(true);
		this.mapKit.setZoom(1);
		this.mapKit.setAddressLocation(trondheimLocation);
		
		mapKitChild.setMinimumSize(new Dimension(800, 800));
		mapKitChild.setPreferredSize(new Dimension(800, 800));
		
		// This line is order specific - adding it at the top of this function causes an error
		// why?
		wmsTileFactory.setThreadPoolSize(8);
	}
	
	private void initLayout()
	{
		this.qhblSwingLayout = new QHBoxLayout();
		
		this.qhblSwingLayout.addWidget(this.qchHost);
		super.setLayout(this.qhblSwingLayout);
	}
	
	public void setSheepGeoPositionsOnMap(){
		// Create waypoints from the geo-positions
		//Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(Arrays.asList(
				/*new MyWaypoint("F", Color.ORANGE, frankfurt),
				new MyWaypoint("W", Color.CYAN, wiesbaden),
				new MyWaypoint("M", Color.GRAY, mainz),
				new MyWaypoint("D", Color.MAGENTA, darmstadt),
				new MyWaypoint("O", Color.GREEN, offenbach)));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new FancyWaypointRenderer());

		mapKit.setOverlayPainter(waypointPainter);*/
	}
}

/* EOF */