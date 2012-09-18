package statisticsWidget;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class StatisticsWidget extends QWidget 
{
	QPushButton btnOk = new QPushButton(tr("ok"));
	
	public StatisticsWidget()
	{
		QHBoxLayout qhbMainLayout = new QHBoxLayout();
		
		btnOk.setMinimumHeight(20);
		qhbMainLayout.addWidget(btnOk);
		
		super.setLayout(qhbMainLayout);
	}
}

/* EOF */