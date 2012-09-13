package test;

import sun.awt.X11.Screen;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Name extends QGraphicsView 
{
	//TODO: This class is 100 % copy, understand and purge excessive code.
    private static class RoundRectItem extends QGraphicsRectItem 
    {
        private QColor qcCol;
        private QTimeLine timeLine = new QTimeLine(75);
        private double lastVal = 0;
        private double opa = 1;
        /** A proxy-layer to simplify handling */
        private QGraphicsProxyWidget proxyWidget;
        private QPixmap pix = null;
        // QPixmap is best for showing images on screen, as opposed to QImages

        public Signal0 activated = new Signal0();

        public RoundRectItem(QRectF rect, QColor color, QWidget embeddedWidget) {
            super(rect);
            qcCol = color;

            timeLine.valueChanged.connect(this, "updateValue(double)");

            if (embeddedWidget != null) {
                proxyWidget = new QGraphicsProxyWidget(this);
                proxyWidget.setFocusPolicy(Qt.FocusPolicy.StrongFocus);
                proxyWidget.setWidget(embeddedWidget);
            }
        }

        /** Animate the rectangle. 
         * 
         * @param painter
         * @param option
         * @param widget
         */
        public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) 
        {
            QTransform x = painter.worldTransform();

            QLineF unit = x.map(new QLineF(0, 0, 1, 1));
            if (unit.p1().x() > unit.p2().x() || unit.p1().y() > unit.p2().y()) {
                if (proxyWidget != null && proxyWidget.isVisible()) {
                    proxyWidget.hide();
                    proxyWidget.setGeometry(rect());
                }
                return;
            }

            if (proxyWidget != null && !proxyWidget.isVisible()) {
                proxyWidget.show();
                proxyWidget.setFocus(Qt.FocusReason.OtherFocusReason);
            }

            if (proxyWidget != null && !proxyWidget.pos().isNull())
                proxyWidget.setGeometry(boundingRect().adjusted(25, 25, -25, -25));

            /* Configure and draw inner rectangle */
            painter.setOpacity(roundRectOpacity());
            painter.setPen(QPen.NoPen);
            painter.setBrush(new QColor(0, 0, 0, 64));
            //painter.drawRoundRect(rect().translated(2, 2));

            /* Brush-settings */
            if (proxyWidget == null) {
                QLinearGradient gradient = new QLinearGradient(rect().topLeft(), rect().bottomRight());
                gradient.setColorAt(0, qcCol);
                gradient.setColorAt(1, qcCol.darker((int) (200 + lastVal * 50)));
                painter.setBrush(gradient);
            } else {
                painter.setBrush(qcCol);
            }
            
            //painter.setBrush(QColor.white);

            /* Do the actual painting */
            painter.setPen(new QPen(QColor.black, 1));
            painter.drawRoundRect(rect());

            /* Draw the pixmap, if it is given */
            if (pix != null) {
                //painter.scale(.70, .70);
            	/* drawPixmap at positiion x, y, with given pixmap */
            	//painter.drawPixmap(0, 0, (int)Name.RECT_WIDTH - 15, (int)Name.RECT_HEIGHT - 15, pix);
                //HARDCODE:
                painter.drawPixmap(-(int)Name.RECT_WIDTH / 2 + 17, -(int)Name.RECT_HEIGHT / 2 - 22, (int)Name.RECT_WIDTH - 14, (int)Name.RECT_HEIGHT - 15, pix);
                
            }
        }

        public QRectF boundingRect() {
            double penW = 0.5;
            double shadowW = 2.0;
            return rect().adjusted(-penW, -penW, penW + shadowW, penW + shadowW);
        }

        /** Load the QPixmap to display in this rect.
         * Note that scaling and resizing is handled by the painter, thus 
         * using this.pix.scale* will not give the user any changes. See this.paint()
         * 
         * @param sFilePath location of the image
         */
        public void setPixmap(String sFilePath)
        {
        	this.pix = new QPixmap();
        	this.pix.load(sFilePath);
        	
        	/* Only if our scene is on stage and visible, update the image */
        	if(scene() != null && isVisible()) { update(null); }
        }

        public final double roundRectOpacity() {
            double opacity = opa;
            if (parentItem() != null)
                opacity += ((RoundRectItem) parentItem()).roundRectOpacity();
            return opacity;
        }

        public final void setRoundRectOpacity(double opacity) {
            opa = opacity;
            update(null);
        }


        public void keyPressEvent(QKeyEvent event) {
            if (event.isAutoRepeat() || event.key() != Qt.Key.Key_Return.value()
                    || (timeLine.state() == QTimeLine.State.Running && timeLine.direction() == QTimeLine.Direction.Forward)) {
                super.keyPressEvent(event);
                return;
            }

            timeLine.stop();
            timeLine.setDirection(QTimeLine.Direction.Forward);
            timeLine.start();
            activated.emit();
        }

        public void keyReleaseEvent(QKeyEvent event) {
            if (event.key() != Qt.Key.Key_Return.value()) {
                super.keyReleaseEvent(event);
                return;
            }

            timeLine.stop();
            timeLine.setDirection(QTimeLine.Direction.Backward);
            timeLine.start();
        }

        public void updateValue(double value) {
            lastVal = value;
            if (proxyWidget == null)
                setTransform(new QTransform().scale(1 - value / 10.0, 1 - value / 10.0), false);
        }
    }
	//@END-TODO
    
    /** Constructor. Initialize..
     * 
     * @param parent parent off this window, for modular use.
     */
    public Name(QWidget parent)
    {
        initRects();
        createGrid();
        
        initLabels();
        
        super.setWindowTitle(tr("Menu with labels"));
    }

    /** Main
     * 
     * @param args parameters for this program
     */
    public static void main(String[] args) {
        QApplication.initialize(args);

        Name testName = new Name(null);
        testName.show();

        QApplication.exec();
    }
    
    private void initLabels()
    {
    	//HARDCODE:
    	((RoundRectItem) this.menuGrid[0][SELECTED_COL]).setPixmap("ImageArt/txtKart.png");
    	((RoundRectItem) this.menuGrid[1][SELECTED_COL]).setPixmap("ImageArt/txtStatistikk.png");
    	((RoundRectItem) this.menuGrid[2][SELECTED_COL]).setPixmap("../../../Pictures/barbecue-tux-gentleman-1891.png");
    }
    
    /** Handle key-strokes.
     * 
     * @param event QKeyEvent holding information
     */
    public void keyPressEvent(QKeyEvent event)
    {
    	/* The enter key is better handled by qt.core */
    	if (event.key() == Qt.Key.Key_Return.value()) 
    	{ 
    		if(menuGrid[0][0].hasFocus()) System.out.println("hey");
    		
    		super.keyPressEvent(event); 
    		return; 
    	}
    	
    	/* Obtain what row to go to, based upon whether user hits up or down arrow */
    	iSelectedRow = (iSelectedRow + NUM_BOXES_HEIGHT
    				   + (event.key() == Qt.Key.Key_Down.value() ? KEY_PRESSED : KEY_NOT_PRESSED)
    				   - (event.key() == Qt.Key.Key_Up.  value() ? KEY_PRESSED : KEY_NOT_PRESSED)) % NUM_BOXES_HEIGHT;
    	
    	/* Set focus to selected box */
    	menuGrid[iSelectedRow][SELECTED_COL].setFocus(Qt.FocusReason.MenuBarFocusReason);
    	
    	/* Animation timer and positions */
    	qtlSelectionTimeLine.stop();
        qpStartPos = selectorRect.pos();
        qpEndPos = posForLocation(SELECTED_COL + (WIDTH / 2), iSelectedRow);
        qtlSelectionTimeLine.start();
    }
    
    /** Create static rectangles
     *     
     */
    private void initRects()
    {
    	/* Set the edges of the window, setting first left-edge, then top-ledge, so forth */
    	QRectF bounds = new QRectF(-SCREEN_WIDTH, -SCREEN_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);
    	
        
    	this.qgsScene = new QGraphicsScene(bounds, this);
        setScene(qgsScene);
        
        /* Background */
        // HARDCODE:
        this.backgroundRect = new RoundRectItem(bounds, new QColor(226, 255, 92, 64), null);
        this.backgroundRect.setRoundRectOpacity(1);
        this.backgroundRect.setPixmap("ImageArt/geeks.jpg");
        this.qgsScene.addItem(backgroundRect);
              
        /* Selector */
        this.selectorRect = new RoundRectItem(new QRectF(RECT_LEFT, RECT_TOP, RECT_WIDTH, RECT_HEIGHT),
        									   QColor.gray, null);
        this.selectorRect.setParentItem(backgroundRect);
        this.selectorRect.setZValue(-1);
        this.selectorRect.setPos(posForLocation( (WIDTH / 2), 0));
        this.qpStartPos = selectorRect.pos();
    }
    
    /** Create the grid holding the menu items
     * 
     */
    private void createGrid()
    {
    	this.menuGrid = new QGraphicsItemInterface[NUM_BOXES_HEIGHT][MENU_COLUMNS];
    	this.qtlSelectionTimeLine = new QTimeLine(150, this);
    	
    	for(int yPos = 0; yPos < NUM_BOXES_HEIGHT; yPos++)
    	{
    		//HARDCODE:
    		RoundRectItem labelBackRect = new RoundRectItem(new QRectF(RECT_LEFT + 5, RECT_TOP + 6,
    																	RECT_WIDTH - 11, RECT_HEIGHT - 11),	
															new QColor(214, 240, 110, 128),
															null);
    		
    		labelBackRect.setPos(posForLocation( (SCREEN_WIDTH / 2),  yPos));
    		labelBackRect.setParentItem(this.backgroundRect);
    		labelBackRect.setFlag(QGraphicsItem.GraphicsItemFlag.ItemIsFocusable, true);
    		
    		this.menuGrid[yPos][SELECTED_COL] = labelBackRect;
    	}
    	
    	/* Initial settings */
    	this.menuGrid[0][0].setFocus(Qt.FocusReason.OtherFocusReason);
    	this.qtlSelectionTimeLine.valueChanged.connect(this, "updateSelectionStep(double)");
    }
    
    /** Update the position of selectionItem
     * This function is called from valueChanged event in "creategrid"
     * 
     * @param val determines where previous item was located, and is handled by the
     * 			   environment.
     * @usage qtClass.*.connect(this, "updateSelectionStep(double)");
     */
    public void updateSelectionStep(double val) {
        double dNewXpos = qpStartPos.x() + (qpEndPos.x() - qpStartPos.x()) * val;
        double dNewYpos = qpStartPos.y() + (qpEndPos.y() - qpStartPos.y()) * val;

        QPointF newPos = new QPointF(dNewXpos, dNewYpos);
        this.selectorRect.setPos(newPos);
    }
    
    /** Obtain a suitable location from two coordinates...
     * 
     * @param x the x-position
     * @param y the y-position
     * @return QPointF from the coordinates
     */
    //TODO: avoid repeated arithmetics by making them constants
    //FIXME: after going full-screen mode to normal windowed, the bottom of the screen
    //		  won't automatically show again (set SCREEN_HEIGHT to 1000)
    public QPointF posForLocation(int iX, int iY) 
    {
    	int yPadding = 60;
    	/** The height from the top box to the bottom box */
    	int iTotalHeight = (NUM_BOXES_HEIGHT * yPadding) + (NUM_BOXES_HEIGHT * (int)RECT_HEIGHT);
    	
    	iY++; // I wish I knew why this is needed
    	
    	return new QPointF( ((-1 * SCREEN_WIDTH) / 2),		 /* Why is 4 the magic number? */
    						((-1 * SCREEN_HEIGHT) / 2) - (iTotalHeight / 4) /* Starting point for first rect */
    						+ (iY * yPadding)); /* The dynamical yPos */
    }

    /** Screen size in pixels */
    private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
    /** Determines how many boxes there are room for */
    private static final int WIDTH = 5, HEIGHT = 4;
    /** How many boxes that are placed on screen */
    private static final int NUM_BOXES_HEIGHT = 3;
    private 		        int iSelectedRow = 0;
    private static final int SELECTED_COL = 0;
    private static final int MENU_COLUMNS = 1;
    private static final int KEY_PRESSED = 1, KEY_NOT_PRESSED = 0;
    
    public static final double RECT_LEFT = -90, RECT_TOP = -60, RECT_WIDTH = 200, RECT_HEIGHT = 60;
    
    
    private QGraphicsItemInterface menuGrid[][];
    private QGraphicsScene qgsScene;
    private QPointF qpStartPos;
    private QPointF qpEndPos = new QPointF();
    private QTimeLine qtlSelectionTimeLine;

    private RoundRectItem backgroundRect;
    private RoundRectItem selectorRect;
}

/* EOF */