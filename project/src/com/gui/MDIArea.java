package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QMdiArea;
import com.trolltech.qt.gui.QMdiSubWindow;
import com.trolltech.qt.gui.QResizeEvent;

/** Class to hold Subwindows
 * 
 * @author Gruppe 10 <3
 */
public class MDIArea extends QMdiArea implements Iterable<SubWindow> 
{
	private List<SubWindow> lSubWindows;
	
	private int iNegativeDecrease = 0,
				iPositiveIncrease = 0;
	
	/** Constructor. Initialize..
	 */
	public MDIArea()
	{
		super();
		this.lSubWindows = new ArrayList<SubWindow>();
	}
	
	/** Add a subwindow to THIS
	 * 
	 * @param swWin windows to add
	 */
	// TODO: adding a subwindow might enforce resize behaviour (?)
	public void addSubWindow(SubWindow swWin)
	{
		swWin.setParent(this);
		this.lSubWindows.add(swWin);
	}

	/** Add a list of SubWindow to THIS
	 * 
	 * @param lWindows the list of SubWindows to add
	 */
	public void addWindows(ArrayList<SubWindow> lWindows)
	{
		for(QMdiSubWindow window : lWindows)
		{
			super.addSubWindow(window);
			window.setParent(this);
		}
	}

	/** Arrange all the windows so that they take full-width 
	 *  and divide the height equally (so that they all combined use full-height
	 */
	public void cascadeWindows()
	{
		/** Get the max size for each window if they should cover the screen with equal height */
		int iWindowHeight = (super.height() / this.lSubWindows.size());
		
		/* For each subwindow */
		for(int iPos = 0; iPos < lSubWindows.size(); iPos++)
		{
			   SubWindow cur = this.lSubWindows.get(iPos);
			   
			   cur.setGeometry(0 ,  0,
					   		  super.width(),
							  iWindowHeight);
			   
			   cur.move(0, (iPos * iWindowHeight));
		}
	}

	/** Retrieve a list of all subwindows
	 * 
	 * @return a list of all subwindows.
	 */
	public List<SubWindow> getChildren()
	{
		return this.lSubWindows;
	}

	/** Get the height of the parent of THIS */
	private int getParentHeight() { return ((MainWindow)super.parent()).height(); }

	/** Get the width of the parent of THIS */
	private int getParentWidth()  { return ((MainWindow)super.parent()).width();  }

	/** Check if one subwindow is maximized
	 * @return true if one window is maximized, false if not
	 */
	public boolean hasMaximized()
	{
		/* For each subwindow */
		for(SubWindow cur : this)
		{
			if(cur.isMaximized()) { return true; }
		}
		
		return false;
	}
	
	@Override
	/** Get the iterator for the list holding all subwindows.
	 * 
	 * @return the iterator for the list holding all subwindows.
	 */
	public Iterator<SubWindow> iterator() 
	{
		return this.lSubWindows.iterator();
	}

	/** When we're resizing the windows, in most cases,
	 * there will be a need for moving only the lowest subwindows.
	 * 
	 * @param iIncrement by pixels, how far to move each subwindow
	 */
	private void moveLowerChildren(int iIncrement)
	{
		Collections.sort(this.lSubWindows); // Note: sorts by y-position
				
		/* For each subwindow below the top-one */
		for(int iPos = 1; iPos < this.lSubWindows.size(); iPos++)
		{
			SubWindow cur = this.lSubWindows.get(iPos);
			cur.move(cur.x(), (cur.y() + iIncrement));
		}
	}

	/** Resize THIS
	 *
	 * @param qsNewSize
	 */
	public void resizeWidget(QSize qsNewSize)
	{
		int iNewWidth  = qsNewSize.width();
		int iNewHeight = qsNewSize.height();
		
		/** The width-increment; how many pixels the old size changes to the new one */
		//HARDCODE: why?
		int iWidthInc = (iNewWidth < getParentWidth()) ? (-1 * (getParentWidth() - iNewWidth) - 4)
												   	   :       (iNewWidth - super.width());
		/** The height-increment; how many pixels the old size changes to the new one */
		int iHeightInc = (iNewHeight - super.height());

		/* We're resizing THIS by setting the maximum width and height - which also resizes the QMdiArea */
		super.setMaximumWidth(qsNewSize.width());
		super.setMaximumHeight(qsNewSize.height());
		
		/* Perform re-sizing of children */
		updateChildrenSize(iWidthInc, iHeightInc);
		moveLowerChildren(super.height() - iNewHeight);
	}
	
	/** Resize THIS
	 * 
	 * @param qreSizeInfo the old size of our parent. 
	 * 					 The current is presumed accesible through super.width() and super.height() 
	 * @param iMaxWidth the max-width THIS is allowed to have is dictated by our parent.
	 */
	public void resizeWidget(QResizeEvent qreSizeInfo, int iMaxWidth)
	{
		/* We're resizing THIS by setting the maximum width and height - which also resizes the QMdiArea */
		super.setMaximumWidth(iMaxWidth);
		super.setMaximumHeight(getParentHeight());
		
		/** Set the increase in height by pixels */
		int iHeightIncrement = (getParentHeight() - qreSizeInfo.oldSize().height());
		/** Set the increase in width by pixels */
		int iWidthIncrement  = (getParentWidth()  - qreSizeInfo.oldSize().width());
		/** When THIS (MDIArea) changes by 3 pixels, and there are 3 windows, each window should increase by only 1 pixels.
		 *  This variable holds the respective change to each subwindow.
		 */
		int iScaledHeightIncrement = (iHeightIncrement / this.lSubWindows.size());

		// TODO: E.g. if there are 3 windows, we can't increase until 
		// we have 3 pixels. This can happen from a 1+1+1 and a 1+2...
		// current solution only supports 1+1+1+1
		// TODO: is there a way to sex this up? (conditional if is in general
		//		 not desired, particularly not several nested in each other)
		/* Because we have to use integers for resizing (e.g. 0.5 pixels won't do), 
		 * a problem arises when there are more than 1 subwindows and we only increase window size by 1.
		 * The scaled height increment will then be e.g. 1 / 3 = 0 and no resizing is done.
		 * Therefore, we check to see if the height increment on the MDIArea exists,
		 * but the scaled height increment is zero (bad math)
		 */
		// FIXME: there are more cases when the scaled increment fails, this causes some minor, ugly resizing behaviour.
		if(iHeightIncrement != 0 && iScaledHeightIncrement == 0)
		{
			/* If we are decreasing the size of THIS */
			if(iHeightIncrement < 0) 
			{ 
				this.iPositiveIncrease = 0;
				this.iNegativeDecrease++; 
			}
			else /* If we are increasing the size of THIS */
			{
				this.iNegativeDecrease = 0;
				this.iPositiveIncrease++; 
			}
			
			/** @see iScaledHeightIncrement */
			// If the positive increment can let itself get performed */
			if(this.iPositiveIncrease == this.lSubWindows.size())
			{
				this.iPositiveIncrease = 0;
				iScaledHeightIncrement += 1;
			}
			// If the negative increment can let itself get performed */
			else if(this.iNegativeDecrease == this.lSubWindows.size())
			{
				this.iNegativeDecrease = 0;
				iScaledHeightIncrement -= 1;
			}
		}
		
		/* Now that we have the numbers for resizing subwindows, act on the windows */
		updateChildrenSize(iWidthIncrement, iScaledHeightIncrement);
		moveLowerChildren(iScaledHeightIncrement);
	}
	
	/** Update the size of all subwindows
	 * 
	 * @param iWidthInc the increase (or decrease) for width by pixels
	 * @param iHeightInc the increase (or decrease) for height by pixels
	 */
	private void updateChildrenSize(int iWidthInc, int iHeightInc)
	{
		for(SubWindow cur : this)
		{
			cur.setWidthIncrement(iWidthInc);
			cur.setHeightIncrement(iHeightInc);
		}
	}
}

/* EOF */