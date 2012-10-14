package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		// TODO: Add your awt-component here, e.g
		
		/* *****																****
		 * **** qhblSwingLayout.addWidget(new QComponentHost(MyAwtComponent));   ***
		 * *****																****
		 */
	}
	
	private void poop()
	{
		QHBoxLayout lay = new QHBoxLayout();
		JPanel jPanel = new JPanel();
		jPanel.add(new JLabel("Hello"));
		lay.addWidget(new QComponentHost(jPanel));
		
		super.setLayout(lay);			
	}
	
	

}

/* EOF */