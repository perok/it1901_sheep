/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia Corporation and/or its subsidiary(-ies).
** All rights reserved.
**
** This file is part of Qt Jambi.
**
** 
** Commercial Usage
** Licensees holding valid Qt Commercial licenses may use this file in
** accordance with the Qt Commercial License Agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Nokia.
** 
** GNU Lesser General Public License Usage
** Alternatively, this file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
** 
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
** 
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
** 
** If you are unsure which license is appropriate for your use, please
** contact the sales department at qt-sales@nokia.com.
**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

package misc;

import com.trolltech.qt.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

import java.util.*;

//@QtJambiExample (name = "Icons")
public class Icons extends QMainWindow
{
    QWidget centralWidget;

    QGroupBox previewGroupBox;
    IconPreviewArea previewArea;

    QGroupBox imagesGroupBox;
    QTableWidget imagesTable;

    QGroupBox iconSizeGroupBox;
    QRadioButton smallRadioButton;
    QRadioButton largeRadioButton;
    QRadioButton toolBarRadioButton;
    QRadioButton listViewRadioButton;
    QRadioButton iconViewRadioButton;
    QRadioButton tabBarRadioButton;
    QRadioButton otherRadioButton;
    IconSizeSpinBox otherSpinBox;

    QMenu fileMenu;
    QMenu viewMenu;
    QMenu helpMenu;
    QAction addImagesAct;
    QAction removeAllImagesAct;
    QAction exitAct;
    QAction guessModeStateAct;
    QActionGroup styleActionGroup;
    QAction aboutAct;
    QAction aboutQtAct;

    public Icons()
    {
        centralWidget = new QWidget();
        setCentralWidget(centralWidget);

        createPreviewGroupBox();
        createImagesGroupBox();
        createIconSizeGroupBox();

        createActions();
        createMenus();
        createContextMenu();

        QGridLayout mainLayout = new QGridLayout();
        mainLayout.addWidget(previewGroupBox, 0, 0, 1, 2);
        mainLayout.addWidget(imagesGroupBox, 1, 0);
        mainLayout.addWidget(iconSizeGroupBox, 1, 1);
        centralWidget.setLayout(mainLayout);

        setWindowTitle(tr("Icons"));
        otherRadioButton.click();

        setTextOnRadioButtons();

        resize(minimumSizeHint());
    }

    private void about()
    {
        QMessageBox.about(this, tr("About Icons"),
            tr("The <b>Icons</b> example illustrates how Qt renders an icon in "+
               "different modes (active, normal, disabled, and selected) and "+
               "states (on and off) based on a set of images."));
    }

    private void changeStyle(boolean checked)
    {
        if (!checked)
            return;

        QAction action = (QAction) QSignalEmitter.signalSender();
        QStyle style = QStyleFactory.create((String) action.data());

        if (style != null) {
            QApplication.setStyle(style);
            QApplication.setPalette(style.standardPalette());
        }

        setTextOnRadioButtons();

        changeSize(true);
    }

    private void setTextOnRadioButtons() {
        QStyle style = QApplication.style();

        int smallIconSize = style.pixelMetric(QStyle.PixelMetric.PM_SmallIconSize);
        smallRadioButton.setText("Small (" + smallIconSize + " x " + smallIconSize + ")");

        int largeIconSize = style.pixelMetric(QStyle.PixelMetric.PM_LargeIconSize);
        largeRadioButton.setText("Large (" + largeIconSize + " x " + largeIconSize + ")");

        int toolBarIconSize = style.pixelMetric(QStyle.PixelMetric.PM_ToolBarIconSize);
        toolBarRadioButton.setText("Toolbars (" + toolBarIconSize + " x " + toolBarIconSize + ")");

        int listViewIconSize = style.pixelMetric(QStyle.PixelMetric.PM_ListViewIconSize);
        listViewRadioButton.setText("List views (" + listViewIconSize + " x " + listViewIconSize + ")");

        int iconViewIconSize = style.pixelMetric(QStyle.PixelMetric.PM_IconViewIconSize);
        iconViewRadioButton.setText("Icon views (" + iconViewIconSize + " x " + iconViewIconSize + ")");

        int tabBarIconSize = style.pixelMetric(QStyle.PixelMetric.PM_TabBarIconSize);
        tabBarRadioButton.setText("Tab bars (" + tabBarIconSize + " x " + tabBarIconSize + ")");
    }

    private void changeSize(int value)
    {
        changeSize(true);
    }

    private void changeSize(boolean checked)
    {
        if (!checked)
            return;

        int extent;

        if (otherRadioButton.isChecked()) {
            extent = otherSpinBox.value();
        } else {
            QStyle.PixelMetric metric;

            if (smallRadioButton.isChecked()) {
                metric = QStyle.PixelMetric.PM_SmallIconSize;
            } else if (largeRadioButton.isChecked()) {
                metric = QStyle.PixelMetric.PM_LargeIconSize;
            } else if (toolBarRadioButton.isChecked()) {
                metric = QStyle.PixelMetric.PM_ToolBarIconSize;
            } else if (listViewRadioButton.isChecked()) {
                metric = QStyle.PixelMetric.PM_ListViewIconSize;
            } else if (iconViewRadioButton.isChecked()) {
                metric = QStyle.PixelMetric.PM_IconViewIconSize;
            } else {
                metric = QStyle.PixelMetric.PM_TabBarIconSize;
            }
            extent = QApplication.style().pixelMetric(metric);
        }
        previewArea.setSize(new QSize(extent, extent));
        otherSpinBox.setEnabled(otherRadioButton.isChecked());
    }

    private void changeIcon()
    {
        QIcon icon = new QIcon();

        for (int row = 0; row < imagesTable.rowCount(); ++row) {
            QTableWidgetItem item0 = imagesTable.item(row, 0);
            QTableWidgetItem item1 = imagesTable.item(row, 1);
            QTableWidgetItem item2 = imagesTable.item(row, 2);

            if (item0.checkState() == Qt.CheckState.Checked) {
                QIcon.Mode mode;
                if (item1.text().equals(tr("Normal"))) {
                    mode = QIcon.Mode.Normal;
                } else if (item1.text().equals(tr("Active"))) {
                    mode = QIcon.Mode.Active;
                } else if (item1.text().equals(tr("Disabled"))) {
                    mode = QIcon.Mode.Disabled;
                } else {
                    mode = QIcon.Mode.Selected;
                }

                QIcon.State state;
                if (item2.text().equals(tr("On"))) {
                    state = QIcon.State.On;
                } else {
                    state = QIcon.State.Off;
                }

                String fileName = (String) item0.data(Qt.ItemDataRole.UserRole);
                QImage image = new QImage(fileName);
                if (!image.isNull())
                    icon.addPixmap(QPixmap.fromImage(image), mode, state);
            }
        }

        previewArea.setIcon(icon);
    }

    private void addImages()
    {
        List<String> fileNames = QFileDialog.getOpenFileNames(this,
                                    tr("Open Images"), "",
                                    new QFileDialog.Filter(tr("Images (*.png *.xpm *.jpg);;"+
                                       "All Files (*)")));
        if (!fileNames.isEmpty()) {
            for (String fileName : fileNames) {
                int row = imagesTable.rowCount();
                imagesTable.setRowCount(row + 1);

                String imageName = new QFileInfo(fileName).baseName();
                QTableWidgetItem item0 = new QTableWidgetItem(imageName);
                item0.setData(Qt.ItemDataRole.UserRole, fileName);
                Qt.ItemFlags flags = item0.flags();
                flags.clear(Qt.ItemFlag.ItemIsEditable);
                item0.setFlags(flags);

                QTableWidgetItem item1 = new QTableWidgetItem(tr("Normal"));
                QTableWidgetItem item2 = new QTableWidgetItem(tr("Off"));

                if (guessModeStateAct.isChecked()) {
                    if (fileName.contains("_act")) {
                        item1.setText(tr("Active"));
                    } else if (fileName.contains("_dis")) {
                        item1.setText(tr("Disabled"));
                    } else if (fileName.contains("_sel")) {
                        item1.setText(tr("Selected"));
                    }

                    if (fileName.contains("_on"))
                        item2.setText(tr("On"));
                }

                imagesTable.setItem(row, 0, item0);
                imagesTable.setItem(row, 1, item1);
                imagesTable.setItem(row, 2, item2);
                imagesTable.openPersistentEditor(item1);
                imagesTable.openPersistentEditor(item2);

                item0.setCheckState(Qt.CheckState.Checked);
            }
        }
    }

    private void removeAllImages()
    {
        imagesTable.setRowCount(0);
        changeIcon();
    }

    private void createPreviewGroupBox()
    {
        previewGroupBox = new QGroupBox(tr("Preview"));

        previewArea = new IconPreviewArea();

        QVBoxLayout layout = new QVBoxLayout();
        layout.addWidget(previewArea);
        previewGroupBox.setLayout(layout);
    }

    private void createImagesGroupBox()
    {
        imagesGroupBox = new QGroupBox(tr("Images"));

        imagesTable = new QTableWidget();
        imagesTable.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection);
        imagesTable.setItemDelegate(new ImageDelegate(this));

        List<String> labels = new LinkedList<String>();
        labels.add(tr("Image"));
        labels.add(tr("Mode"));
        labels.add(tr("State"));

        imagesTable.horizontalHeader().setDefaultSectionSize(90);
        imagesTable.setColumnCount(3);
        imagesTable.setHorizontalHeaderLabels(labels);
        imagesTable.horizontalHeader().setResizeMode(0, QHeaderView.ResizeMode.Stretch);
        imagesTable.horizontalHeader().setResizeMode(1, QHeaderView.ResizeMode.Fixed);
        imagesTable.horizontalHeader().setResizeMode(2, QHeaderView.ResizeMode.Fixed);
        imagesTable.verticalHeader().hide();

        imagesTable.itemChanged.connect(this, "changeIcon()");

        QVBoxLayout layout = new QVBoxLayout();
        layout.addWidget(imagesTable);
        imagesGroupBox.setLayout(layout);
    }

    private void createIconSizeGroupBox()
    {
        iconSizeGroupBox = new QGroupBox(tr("Icon Size"));

        smallRadioButton = new QRadioButton();
        largeRadioButton = new QRadioButton();
        toolBarRadioButton = new QRadioButton();
        listViewRadioButton = new QRadioButton();
        iconViewRadioButton = new QRadioButton();
        tabBarRadioButton = new QRadioButton();
        otherRadioButton = new QRadioButton(tr("Other:"));

        otherSpinBox = new IconSizeSpinBox();
        otherSpinBox.setRange(8, 128);
        otherSpinBox.setValue(64);

        smallRadioButton.toggled.connect(this, "changeSize(boolean)");
        largeRadioButton.toggled.connect(this, "changeSize(boolean)");
        toolBarRadioButton.toggled.connect(this, "changeSize(boolean)");
        listViewRadioButton.toggled.connect(this, "changeSize(boolean)");
        iconViewRadioButton.toggled.connect(this, "changeSize(boolean)");
        tabBarRadioButton.toggled.connect(this, "changeSize(boolean)");
        otherRadioButton.toggled.connect(this, "changeSize(boolean)");
        otherSpinBox.valueChanged.connect(this, "changeSize(int)");

        QHBoxLayout otherSizeLayout = new QHBoxLayout();
        otherSizeLayout.addWidget(otherRadioButton);
        otherSizeLayout.addWidget(otherSpinBox);
        otherSizeLayout.addStretch();

        QGridLayout layout = new QGridLayout();
        layout.addWidget(smallRadioButton, 0, 0);
        layout.addWidget(largeRadioButton, 1, 0);
        layout.addWidget(toolBarRadioButton, 2, 0);
        layout.addWidget(listViewRadioButton, 0, 1);
        layout.addWidget(iconViewRadioButton, 1, 1);
        layout.addWidget(tabBarRadioButton, 2, 1);
        layout.addLayout(otherSizeLayout, 3, 0, 1, 2);
        layout.setRowStretch(4, 1);
        iconSizeGroupBox.setLayout(layout);
    }

    private void createActions()
    {
        addImagesAct = new QAction(tr("&Add Images..."), this);
        addImagesAct.setShortcut(tr("Ctrl+A"));
        addImagesAct.triggered.connect(this, "addImages()");

        removeAllImagesAct = new QAction(tr("&Remove All Images"), this);
        removeAllImagesAct.setShortcut(tr("Ctrl+R"));
        removeAllImagesAct.triggered.connect(this, "removeAllImages()");

        exitAct = new QAction(tr("&Quit"), this);
        exitAct.setShortcut(tr("Ctrl+Q"));
        exitAct.triggered.connect(this, "close()");

        styleActionGroup = new QActionGroup(this);
        for (String styleName : QStyleFactory.keys()) {
            QAction action = new QAction(styleActionGroup);
            action.setText(styleName + " Style");
            action.setData(styleName);
            action.setCheckable(true);
            action.triggered.connect(this, "changeStyle(boolean)");
        }

        guessModeStateAct = new QAction(tr("&Guess Image Mode/State"), this);
        guessModeStateAct.setCheckable(true);
        guessModeStateAct.setChecked(true);

        aboutAct = new QAction(tr("&About"), this);
        aboutAct.triggered.connect(this, "about()");

        aboutQtAct = new QAction(tr("About &Qt"), this);
        aboutQtAct.triggered.connect(QApplication.instance(), "aboutQt()");
    }

    private void createMenus()
    {
        fileMenu = menuBar().addMenu(tr("&File"));
        fileMenu.addAction(addImagesAct);
        fileMenu.addAction(removeAllImagesAct);
        fileMenu.addSeparator();
        fileMenu.addAction(exitAct);

        viewMenu = menuBar().addMenu(tr("&View"));
        for (QAction action : styleActionGroup.actions())
            viewMenu.addAction(action);
        viewMenu.addSeparator();
        viewMenu.addAction(guessModeStateAct);

        menuBar().addSeparator();

        helpMenu = menuBar().addMenu(tr("&Help"));
        helpMenu.addAction(aboutAct);
        helpMenu.addAction(aboutQtAct);
    }

    private void createContextMenu()
    {
        imagesTable.setContextMenuPolicy(Qt.ContextMenuPolicy.ActionsContextMenu);
        imagesTable.addAction(addImagesAct);
        imagesTable.addAction(removeAllImagesAct);
    }

    private void checkCurrentStyle()
    {
        for (QAction action : styleActionGroup.actions()) {
            String styleName = action.data().toString();
            QStyle candidate = QStyleFactory.create(styleName);

            if (candidate.objectName().equals(
                    QApplication.style().objectName())) {
                action.trigger();
                return;
            }
        }
    }

    class IconSizeSpinBox extends QSpinBox
    {
        @Override
        public int valueFromText(String text)
        {
            QRegExp regExp = new QRegExp(tr("(\\d+)(\\s*[xx]\\s*\\d+)?"));

            if (regExp.exactMatch(text)) {
                return Integer.parseInt(regExp.cap(1));
            } else {
                return 0;
            }
        }

        @Override
        public String textFromValue(int value)
        {
            return "" + value +" x " + value;
        }
    }

    class ImageDelegate extends QItemDelegate
    {
        public ImageDelegate(QWidget widget)
        {
            super(widget);
        }

        @Override
        public QWidget createEditor(QWidget parent, QStyleOptionViewItem option,
                                    QModelIndex index)
        {
            QComboBox comboBox = new QComboBox(parent);
            if (index.column() == 1) {
                comboBox.addItem(tr("Normal"));
                comboBox.addItem(tr("Active"));
                comboBox.addItem(tr("Disabled"));
                comboBox.addItem(tr("Selected"));
            } else if (index.column() == 2) {
                comboBox.addItem(tr("Off"));
                comboBox.addItem(tr("On"));
            }

            comboBox.activated.connect(this, "emitCommitData()");

            return comboBox;
        }

        @Override
        public void setEditorData(QWidget editor, QModelIndex index)
        {
            QComboBox comboBox = (QComboBox) editor;
            if (comboBox == null)
                return;

            int pos = comboBox.findText((String) index.model().data(index),
                                         Qt.MatchFlag.MatchExactly);
            comboBox.setCurrentIndex(pos);
        }

        @Override
        public void setModelData(QWidget editor, QAbstractItemModel model,
                                 QModelIndex index)
        {
            QComboBox comboBox = (QComboBox) editor;
            if (comboBox == null)
                return;

            model.setData(index, comboBox.currentText());
        }

        private void emitCommitData()
        {
            commitData.emit((QWidget) QSignalEmitter.signalSender());
        }
    }

    class IconPreviewArea extends QWidget
    {
        private QIcon icon;
        private QSize size;
        private QLabel stateLabels[] = new QLabel[NumStates];
        private QLabel modeLabels[] = new QLabel[NumModes];
        private QLabel pixmapLabels[][] = new QLabel[NumModes][NumStates];

        public static final int NumModes = 4, NumStates = 2;

        public IconPreviewArea()
        {
            QGridLayout mainLayout = new QGridLayout();
            setLayout(mainLayout);

            icon = new QIcon();

            stateLabels[0] = createHeaderLabel(tr("Off"));
            stateLabels[1] = createHeaderLabel(tr("On"));

            modeLabels[0] = createHeaderLabel(tr("Normal"));
            modeLabels[1] = createHeaderLabel(tr("Active"));
            modeLabels[2] = createHeaderLabel(tr("Disabled"));
            modeLabels[3] = createHeaderLabel(tr("Selected"));

            for (int j = 0; j < NumStates; ++j)
                mainLayout.addWidget(stateLabels[j], j + 1, 0);

            for (int i = 0; i < NumModes; ++i) {
                mainLayout.addWidget(modeLabels[i], 0, i + 1);

                for (int j = 0; j < NumStates; ++j) {
                    pixmapLabels[i][j] = createPixmapLabel();
                    mainLayout.addWidget(pixmapLabels[i][j], j + 1, i + 1);
                }
            }
        }

        public void setIcon(QIcon icon)
        {
            this.icon = icon;
            updatePixmapLabels();
        }

        public void setSize(QSize size)
        {
            if (size != this.size) {
                this.size = size;
                updatePixmapLabels();
            }
        }

        private QLabel createHeaderLabel(String text)
        {
            QLabel label = new QLabel(tr("<b>"+text+"</b>"));
            label.setAlignment(Qt.AlignmentFlag.AlignCenter);
            return label;
        }

        private QLabel createPixmapLabel()
        {
            QLabel label = new QLabel();
            label.setEnabled(false);
            label.setAlignment(Qt.AlignmentFlag.AlignCenter);
            label.setFrameShape(QFrame.Shape.Box);
            label.setSizePolicy(QSizePolicy.Policy.Expanding,
                                QSizePolicy.Policy.Expanding);
            label.setBackgroundRole(QPalette.ColorRole.Base);
            label.setAutoFillBackground(true);
            label.setMinimumSize(132, 132);
            return label;
        }

        private void updatePixmapLabels()
        {
            for (int i = 0; i < NumModes; ++i) {
                QIcon.Mode mode;
                if (i == 0) {
                    mode = QIcon.Mode.Normal;
                } else if (i == 1) {
                    mode = QIcon.Mode.Active;
                } else if (i == 2) {
                    mode = QIcon.Mode.Disabled;
                } else {
                    mode = QIcon.Mode.Selected;
                }

                for (int j = 0; j < NumStates; ++j) {
                    QIcon.State state = (j == 0) ? QIcon.State.Off : QIcon.State.On;
                    QPixmap pixmap = icon.pixmap(size, mode, state);
                    pixmapLabels[i][j].setPixmap(pixmap);
                    pixmapLabels[i][j].setEnabled(!pixmap.isNull());
                }
            }
        }
    }

    public static void main(String args[])
    {
        QApplication.initialize(args);

        new Icons().show();

        QApplication.exec();
    }
}