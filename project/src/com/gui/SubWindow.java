package com.gui;

import com.trolltech.qt.gui.QMdiSubWindow;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QWidget;

/** Extending QMdiSubWindow for custom behavoir
 * 
 * @author Gruppe 10 <3
 *
 */
public class SubWindow extends QMdiSubWindow implements Comparable<SubWindow>
{
	/** Constructor. Initialize..
	 * 
	 * @param qChild default QWdiget to add as content in this
	 */
	public SubWindow(QWidget qChild)
	{
		super.setWidget(qChild);		
		
		super.setSizePolicy(QSizePolicy.Policy.Maximum, QSizePolicy.Policy.Preferred);
	}
	
	@Override
	/** Compare this window to another relative to the current position.
	 * 
	 * @param arg0 the window we're comparing to.
	 * @return less than zero if this window is under another
	 * 		   more than zero if this window is over another window
	 */
	public int compareTo(SubWindow arg0) 
	{
		return super.y() - arg0.y();
	}

	/** Increase (or decrease) the height of this window.
	 * 
	 * @param iHeightInc how many pixels to alter window-height
	 */
	public void setHeightIncrement(int iHeightInc)
	{
		super.setGeometry(super.x(), super.y(),
				super.width(),
				(super.height() + iHeightInc));
	}
	
	/** Increase (or decrease) the width of this window.
	 * 
	 * @param iWidthInc how many pixels to alter window-width
	 */
	public void setWidthIncrement(int iWidthInc)
	{
		super.setGeometry(super.x(), super.y(),
				(super.width() + iWidthInc),
				super.height());
	}
	
}

/* EOF */