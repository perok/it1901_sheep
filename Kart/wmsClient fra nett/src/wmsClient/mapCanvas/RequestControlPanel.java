package wmsClient.mapCanvas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

// import wmsClient.net.*;
import wmsClient.layerTree.*;
import wmsClient.layerList.*;
import wmsClient.gui.*;

public class RequestControlPanel extends JPanel 
    implements RequestParameter, ActionListener, ZoomListener, MoveListener, ListDataListener
{

    protected JTextField jtfWidth  = new JTextField();
    protected JTextField jtfHeight = new JTextField();

    protected JTextField bounding[] = { new JTextField(),new JTextField(),new JTextField(),new JTextField() };

    protected JButton resetButton = new JButton("Start values");
    protected JButton submitButton = new JButton("Load layers");
    protected JButton prevButton = new JButton("<=");
    protected JButton zoomOutButton = new JButton("Zoom out");
    protected JCheckBox autoload = new JCheckBox("Autoload", true );
    
    protected LayerList layerList;  
    protected float layerBounds[];
    protected WmsImageCanvas canvas;

    protected int lastPixelWidth;
    protected int lastPixelHeight;
    protected float lastBounding[];

    protected String lastFormat;
    protected String lastSRS;

    protected StatusBar statusBar;

    protected Vector zoomHistory;
    protected int zoomHistoryPointer;
    protected int zoomHistoryMax = 5;


    public RequestControlPanel(LayerList layerList, WmsImageCanvas canvas, StatusBar statusBar ) {
        super( );	
        this.statusBar = statusBar;
        this.canvas = canvas;
        this.layerList = layerList;
        this.layerBounds = layerList.getLayerBounds();
        
        zoomHistory = new Vector();
        submitButton.addActionListener( this );
        resetButton.addActionListener( this );
        prevButton.addActionListener( this );
        zoomOutButton.addActionListener( this );
        // layerList.addListDataListener( this );
        makeLayout();
    }

    protected void makeLayout() {

        FlowLayout mainLayout = new FlowLayout();
        mainLayout.setAlignment( FlowLayout.LEFT );
        setLayout( mainLayout );
        
        
        Box mainPanel = Box.createHorizontalBox();
        
        JPanel boundingPanel = new JPanel( new BorderLayout() );
        boundingPanel.add( new JLabel("BoundingBox" ), BorderLayout.NORTH );
        JPanel boundingInner = new JPanel( new GridLayout(2,2, 10, 0) );
        for(int i=0; i<=3; i++) {
            bounding[i].setColumns( 6 );
        }
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 0, 0);
        JPanel labelPanel = new JPanel( fl );
        labelPanel.add( new JLabel("x1:"));
        labelPanel.add( bounding[0] );
        boundingInner.add( labelPanel );
        
        labelPanel = new JPanel(fl );
        labelPanel.add( new JLabel("x2:") );
        labelPanel.add( bounding[2] );
        boundingInner.add( labelPanel );
        
        labelPanel = new JPanel(fl );
        labelPanel.add( new JLabel("y1:") );
        labelPanel.add( bounding[1] );
        boundingInner.add( labelPanel );
        
        labelPanel = new JPanel(fl );
        labelPanel.add( new JLabel("y2:") );
        labelPanel.add( bounding[3] );
        boundingInner.add( labelPanel );
        
        boundingPanel.add( boundingInner, BorderLayout.CENTER );
        
        mainPanel.add( boundingPanel );
        
        // nicht schön, mir fällt aber nichts besseres ein
        mainPanel.add( new JLabel("  ") );
        
        JPanel pixelPanel = new JPanel( new BorderLayout() );
        pixelPanel.add( new JLabel("Pixel" ), BorderLayout.NORTH );
        JPanel innderPixelPanel = new JPanel( new GridLayout(2,2) );	
        jtfWidth.setColumns( 5 );
        jtfHeight.setColumns( 5 );
        jtfHeight.setEditable( false );       
        innderPixelPanel.add( new JLabel(" Width: " ) );
        innderPixelPanel.add( jtfWidth );
        innderPixelPanel.add( new JLabel(" Height: " ) );
        innderPixelPanel.add( jtfHeight );	
        pixelPanel.add( innderPixelPanel, BorderLayout.CENTER );
        mainPanel.add( pixelPanel );
        
        // nicht schön, mir fällt aber nichts besseres ein
        mainPanel.add( new JLabel("  ") );
        
        Box buttonPanel = Box.createVerticalBox();
        buttonPanel.add( Box.createVerticalGlue() );
        buttonPanel.add( resetButton );
        buttonPanel.add( submitButton );
        mainPanel.add( buttonPanel );
        
        
        Box buttonPanel2 = Box.createVerticalBox();
        Box buttonPanel2Inner1 = Box.createHorizontalBox();
        buttonPanel2Inner1.add( autoload );
        buttonPanel2.add( buttonPanel2Inner1 );
        
        Box buttonPanel2Inner2 = Box.createHorizontalBox();
        buttonPanel2Inner2.add( prevButton );
        buttonPanel2Inner2.add( zoomOutButton );
        buttonPanel2.add( buttonPanel2Inner2 );
        mainPanel.add( buttonPanel2 );
        
        add( mainPanel);	
    }


    
    // Funktionen fürs Interface RequestParameter

    public int getPixelWidth() {	
        return lastPixelWidth;
    }
    // Dummy funktion noch nicht richtig implementiert
    public int getPixelHeight() {
        return lastPixelHeight;
    }

    /** X-Koordinate der Linken, oberen Ecke */
    // Dummy funktion noch nicht richtig implementiert
    public float getBoundingX1() {
        return lastBounding[0];
    }

    /** Y-Koordinate der Linken, oberen Ecke */
    // Dummy funktion noch nicht richtig implementiert
    public float getBoundingY1() {
        return lastBounding[1];
    }

    // Dummy funktion noch nicht richtig implementiert
    public float getBoundingX2() {
        return lastBounding[2];
    }
    // Dummy funktion noch nicht richtig implementiert
    public float getBoundingY2() {
        return lastBounding[3];
    }

    // Dummy funktion noch nicht richtig implementiert
    public float[] getBounding() {
        return lastBounding;
    }

    // Dummy funktion noch nicht richtig implementiert
    public String getImageFormat() {
        return  lastFormat;
    }

    // Dummy funktion noch nicht richtig implementiert
    public String getSRS() {
        return lastSRS;
    }    


    /////// Funktionen um die neuen Eingabewerte fest zu halten
    public int getNewPixelWidth() {	
        int out = 250;
        try {
            out = Integer.parseInt( jtfWidth.getText() );
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( this, "The field 'Width' is not valid. The request was done with default values. The 'start values' Button can be used vor vlid start values." );
        }
        return out;
    }
    
    public int getNewPixelHeight() {
        int out = 250;
        try {
            out = Integer.parseInt( jtfHeight.getText() );
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( this, "The field 'Height' is not valid. The request was done with default values. The 'start values' Button can be used vor vlid start values." );
        }
        return out;
    }

    public float getNewBoundingX1() {
        return getNewBounding(0);
    }
    public float getNewBoundingY1() {
        return getNewBounding(1);
    }
    public float getNewBoundingX2() {
        return getNewBounding(2);
    }
    public float getNewBoundingY2() {
        return getNewBounding(3);
    }
    
    public float[] getNewBounding() {
        float bounds[]= this.layerBounds;
        for(int i=0;i<4;i++)
            bounds[i] = getNewBounding(i);
        return bounds;
    }
    
    public float getNewBounding(int i) {
        float out = this.layerBounds[i];
        
        float bout = 0;
        try {
            out = Float.parseFloat( bounding[i].getText() );
            bout = out;
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( this, "One bounding box field is not valid. The request was done with default values. The 'start values' Button can be used vor valid start values." );
        }
        // 	int j = i & 2;
        // 	if ( out < this.layerBounds[0+j])
        // 		out = this.layerBounds[0+j]; 
        // 	if ( out > this.layerBounds[1 + j]) 
        // 		out = this.layerBounds[1+j];
        
        // 	if (bout != out)
        // 		bounding[i].setText(""+out);
        return out;
    }

    public String getNewImageFormat() {
        boolean supportGif = true;
        boolean supportJpeg = true;
        boolean supportPng = true;
        
    	Enumeration layerInfos = layerList.getLayers();
        while( layerInfos.hasMoreElements() ) {	    
            LayerInformation current = (LayerInformation)layerInfos.nextElement();
            if (! current.getServerInformation().supportsGetMapFormat( "image/png" ) )
                supportPng = false;
            if (! current.getServerInformation().supportsGetMapFormat( "image/jpeg" ) )
                supportJpeg = false;
            if (! current.getServerInformation().supportsGetMapFormat( "image/gif" ) )
                supportGif = false;
        }
        if ( supportPng ) 
            return "image/png";
        else if ( supportGif ) 
            return "image/gif";	
        else 
            return "image/jpeg";
    }
    
    public String getNewSRS() {
        return "EPSG:4326";
    }    
    
    public boolean autoload() {
        if ( autoload.isSelected() ) {
            submit();
            return true;
        }
        return false;
    }
    
    public void submit( )
    {
        lastPixelWidth = getNewPixelWidth();
        lastBounding = getNewBounding();
        lastFormat = getNewImageFormat();
        lastSRS = getNewSRS();
        
        jtfHeight.setText( ""+calculatePixelHeight( lastBounding, lastPixelWidth ));
        lastPixelHeight = getNewPixelHeight();
        
        String[] zoomData = new String[]{ lastBounding[0]+"", lastBounding[1]+"", lastBounding[2]+"", lastBounding[3]+"" };
        addToZoomHistory( zoomData );
        
        canvas.initLayers();
    	Enumeration layerInfos = layerList.getLayers();	
        statusBar.clear();
        statusBar.setMessage( "Loading images .." );
        statusBar.setIsLoading( true );
        int i = 0;
        while( layerInfos.hasMoreElements() ) {	    
            LayerInformation current = (LayerInformation)layerInfos.nextElement();	   	    
            LayerLoader wmsWorker = new LayerLoader( current , canvas, this, i++, statusBar );
            wmsWorker.start();	    
        }
        // layerBounds = layerList.getLayerBounds();
        statusBar.setMax( i );
        // perhaps lock interface...
    }
    
    public void submitDone( )
    {
        // layerBounds = layerList.getLayerBounds();
        statusBar.setMessage( "Request done.." );
        // perhaps unlock interface...
    }
    
    private void maximizePixelSizes() {
        int w = canvas.getImageWidth();
        int maxHeight = canvas.getImageHeight();
        
        int h = calculatePixelHeight( layerBounds, w );   
        
        if ( h > maxHeight ) {
            float fact = maxHeight/(float)h;
            w = (int)(w * fact);
        }
        h = calculatePixelHeight( layerBounds, w );
        
        jtfWidth.setText(""+w);
        jtfHeight.setText( ""+h);
        
        for(int i=0;i<4;i++)
            bounding[i].setText( ""+ layerBounds[i] );
    }
    
    /**
     * Setzt die Eingabefelder auf Ausgangswerte, die 
     * aus den LayerInfos ermittelt werden
     */
    public void resetButton_actionPerformed( )
    {
        // finden der kleinsten gemeinsamen BoundingBox
        layerBounds = layerList.getLayerBounds();
        maximizePixelSizes();
        autoload();
    }

    public int calculatePixelHeight(float b[],int w) {
        return calculatePixelHeight(b[0],b[1],b[2],b[3],w);
    }

    public int calculatePixelHeight(float x1, float y1, float x2, float y2, int width) {
        float boundingWidth = 	x2-x1;
        float boundingHeight = 	y2-y1;
        
        return Math.round( width * (boundingHeight/boundingWidth) );
    }
    
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        
        if (obj == submitButton) {
            submit();
        } else if (obj == resetButton) {
            resetButton_actionPerformed();
        } else if (obj == prevButton) {
            String[] zoomData = getPrevZoomData();
            if ( zoomData != null )
                setBoundingBoxFields( zoomData );	   
            if ( autoload() ) {
                //getPrevZoomData();
            }
        } else if (obj == zoomOutButton) {
            String[] zoomData = biggerZoomData();
            if ( zoomData != null )
                setBoundingBoxFields( zoomData );
            autoload();	    
        }
    }

     // Funktionen zum setzen einer neuen BoundingBox

    public void moveRelative( float xPercent, float yPercent ) {
        if ( lastPixelWidth == 0 )
            return;
        
        float lastBoundingWidth = Math.abs(lastBounding[2] - lastBounding[0]);
        float lastBoundingHeight = Math.abs(lastBounding[3] - lastBounding[1]);
        
        float xAdvance = xPercent * lastBoundingWidth;
        float yAdvance = -1 * yPercent * lastBoundingHeight;
        
        layerBounds[0] += xAdvance;
        layerBounds[1] += yAdvance;
        layerBounds[2] += xAdvance;
        layerBounds[3] += yAdvance;
        
        maximizePixelSizes();
        autoload();
        //	submit();
        
    }
    
    /** X-Koordinate der Linken, oberen Ecke in Pixeln */
    public void setZoomFrame(int x1, int y1, int x2, int y2) {
        
        if ( lastPixelWidth == 0 )
            return;
        
        float lastBoundingWidth = Math.abs(lastBounding[2] - lastBounding[0]);
        float lastBoundingHeight = Math.abs(lastBounding[3] - lastBounding[1]);
        
        layerBounds[0] += ((float)x1/lastPixelWidth)*(lastBoundingWidth);
        layerBounds[1] += ((float)(lastPixelHeight-y2)/lastPixelHeight)*(lastBoundingHeight);
        layerBounds[2] -= ((float)(lastPixelWidth-x2)/lastPixelWidth)*(lastBoundingWidth);
        layerBounds[3] -= ((float)y1/lastPixelHeight)*(lastBoundingHeight);
        
        maximizePixelSizes();
        autoload();
    }

    public void addToZoomHistory( String[] data ) {
        boolean contains = false;
        for ( int i=0; i<zoomHistory.size(); i++ ) {
            boolean equals = true;
            String[] coords = (String[])zoomHistory.elementAt( i );
            if ( coords == null ) 
                equals = false;
            else
                for ( int j=0; j<coords.length; j++ )
                    if ( ! coords[j].equals( data[j] ) ) {
                        equals = false;
                        break;
                    }
            if ( equals ) {
                contains = true;
                break;
            }		
        }
        if ( ! contains ) {
            while ( zoomHistoryPointer > 0 ) {
                zoomHistoryPointer--;
                zoomHistory.remove( zoomHistoryPointer );
            }
            zoomHistory.add( 0, data );
            zoomHistory.setSize( zoomHistoryMax );	
        }
    }

    public String[] getPrevZoomData() {	
        try {
            return (String[])zoomHistory.elementAt( ++zoomHistoryPointer );
        } catch ( IndexOutOfBoundsException e ) {
            zoomHistoryPointer = zoomHistory.size()-1;
            return null;
        }
    }
    
    public String[] getNextZoomData() {
        try {
            return (String[])zoomHistory.elementAt( --zoomHistoryPointer );
        } catch ( IndexOutOfBoundsException e ) {
            zoomHistoryPointer = 0;
            return null;
        }
    }

    public String[] biggerZoomData() {
        float lastBoundingWidth = Math.abs(lastBounding[2] - lastBounding[0]);
        float lastBoundingHeight = Math.abs(lastBounding[3] - lastBounding[1]);
        return new String[] {
            ""+(lastBounding[0]-lastBoundingWidth),
            ""+(lastBounding[1]-lastBoundingHeight),
            ""+(lastBounding[2]+lastBoundingWidth),
            ""+(lastBounding[3]+lastBoundingHeight),
		};
    }

    public void setBoundingBoxFields( String[] zoomData ) {
        for(int i=0;i<4;i++)
            bounding[i].setText( zoomData[i] );
    }
    
    public void contentsChanged( ListDataEvent e ) {
        if ( "".equals( bounding[0].getText() )
             || "".equals( bounding[1].getText() )
             || "".equals( bounding[2].getText() )
             || "".equals( bounding[3].getText() )
             || ((ListModel)e.getSource()).getSize() <= 1 ) 
            resetButton_actionPerformed();
        autoload();
    }
    public void intervalAdded(ListDataEvent e) { }
    public void intervalRemoved(ListDataEvent e) { }

}
