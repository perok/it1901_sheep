package com.gui.widgets;

import javax.swing.event.MouseInputListener;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.input.CenterMapListener;
import org.jdesktop.swingx.input.PanKeyListener;
import org.jdesktop.swingx.input.PanMouseInputListener;
import org.jdesktop.swingx.input.ZoomMouseWheelListenerCenter;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.wms.WMSService;
import org.jdesktop.swingx.mapviewer.wms.WMSTileFactory;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QResizeEvent;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.research.qtjambiawtbridge.QComponentHost;

/** Stream an external source of media
 * 
 * @author Gruppe 10 <3
 */
public class MapWidget extends QWidget
{
	private QHBoxLayout qhbLayout;
	private QComponentHost qcHost;
	
	private JXMapKit mapKit;
	
	private JXMapViewer mapKitChild;
	
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
		
		this.mapKit  = new JXMapKit();
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
		
		this.setSizePolicy(Policy.Maximum, Policy.Maximum);
		
		//mapKitChild.setMinimumSize(new Dimension(100, 100));
		//mapKitChild.setPreferredSize(new Dimension(800, 800));
		
		//this.qcHost = new QComponentHost(mapKit, this);
		
		// This line is order specific - adding it at the top of this function causes an error
		// why?
		wmsTileFactory.setThreadPoolSize(8);
	}
	
	private void initLayout()
	{
		this.qhbLayout = new QHBoxLayout();
		this.setLayout(qhbLayout);
		this.qhbLayout.addWidget(new QComponentHost(mapKit, this));
		super.setLayout(this.qhbLayout);
	}
	
	@Override
    protected void resizeEvent(QResizeEvent e) {
       //System.out.println("The new size: (" + e.size().width() + ", " + e.size().height() + ")");
       
       
       mapKit.setBounds(0, 0, e.size().width(), e.size().height());
       mapKit.setSize(e.size().width(), e.size().height());
       mapKit.mainMap.setBounds(0, 0, e.size().width(), e.size().height());
       mapKit.mainMap.setSize(e.size().width(), e.size().height());
       mapKit.mainMap.updateUI();

       //mapKit.miniMap.setBounds(0, 0, e.size().width(), e.size().height());
       //mapKit.setSize(e.size().width(), e.size().height());

       //mapKit.jPanel1.setBounds(0, 0, e.size().width(), e.size().height());
      // mapKitChild.resize(new Dimension(e.size().width(), e.size().height()));
       //mapKitChild.setBounds(0, 0, e.size().width(), e.size().height());
       //qhbLayout.update();
       //mapKit.repaint();
       
       //mapKitChild.setMaximumSize(new Dimension(e.size().width(), e.size().height()));
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