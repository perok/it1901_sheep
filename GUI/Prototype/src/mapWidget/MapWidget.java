package mapWidget;

import com.trolltech.qt.gui.*;

/** Stream an external source of media
 * 
 * @author andesil
 */
public class MapWidget extends QWidget
{
	public MapWidget()
	{
		//HARDCODE:
		QPixmap testImage = new QPixmap("imageArt/sheep.png");
		QLabel tmp = new QLabel(this);
		
		testImage.scaled(100, 100);
		tmp.setPixmap(testImage);
	}

}

/* EOF */