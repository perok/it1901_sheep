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

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class CustomFilter extends QWidget {

    public static void main(String[] args) {

        QApplication.initialize(args);
        CustomFilter filter = new CustomFilter();
        filter.show();
        QApplication.exec();
    }

    private MySortFilterProxyModel proxyModel;

    QGroupBox sourceGroupBox;
    QGroupBox proxyGroupBox;
    QTreeView sourceView;
    QTreeView proxyView;
    QCheckBox filterCaseSensitivityCheckBox;
    QLabel filterPatternLabel;
    QLabel fromLabel;
    QLabel toLabel;
    QLineEdit filterPatternLineEdit;
    QComboBox filterSyntaxComboBox;
    QDateEdit fromDateEdit;
    QDateEdit toDateEdit;

    public CustomFilter() {
        QStandardItemModel model = createMailModel(this);

        proxyModel = new MySortFilterProxyModel(this);
        proxyModel.setSourceModel(model);
        proxyModel.setDynamicSortFilter(true);

        sourceView = new QTreeView();
        sourceView.setRootIsDecorated(false);
        sourceView.setAlternatingRowColors(true);
        sourceView.setModel(model);

        QHBoxLayout sourceLayout = new QHBoxLayout();
        sourceLayout.addWidget(sourceView);

        sourceGroupBox = new QGroupBox(tr("Original Model"));
        sourceGroupBox.setLayout(sourceLayout);

        filterPatternLineEdit = new QLineEdit("Grace|Sports");
        filterPatternLabel = new QLabel(tr("&Filter pattern:"));
        filterPatternLabel.setBuddy(filterPatternLineEdit);

        filterSyntaxComboBox = new QComboBox();
        filterSyntaxComboBox.addItem(tr("Regular expression"),
                                     QRegExp.PatternSyntax.RegExp);
        filterSyntaxComboBox.addItem(tr("Wildcard"),
                                     QRegExp.PatternSyntax.Wildcard);
        filterSyntaxComboBox.addItem(tr("Fixed string"),
                                     QRegExp.PatternSyntax.FixedString);

        filterCaseSensitivityCheckBox = new QCheckBox(
                tr("Case sensitive filter"));
        filterCaseSensitivityCheckBox.setChecked(true);

        fromDateEdit = new QDateEdit(new QDate(1970, 01, 01));
        fromLabel = new QLabel(tr("F&rom:"));
        fromLabel.setBuddy(fromDateEdit);

        toDateEdit = new QDateEdit(new QDate(2099, 12, 31));
        toLabel = new QLabel(tr("&To:"));
        toLabel.setBuddy(toDateEdit);

        //filterPatternLineEdit.textChanged.connect(this, "textFilterChanged()");
        //filterSyntaxComboBox.currentIndexChanged.connect(this,
        //"textFilterChanged()");
        //filterCaseSensitivityCheckBox.toggled.connect(this, "textFilterChanged()");
        fromDateEdit.dateChanged.connect(this, "dateFilterChanged()");
        toDateEdit.dateChanged.connect(this, "dateFilterChanged()");

        proxyView = new QTreeView();
        proxyView.setRootIsDecorated(false);
        proxyView.setAlternatingRowColors(true);
        proxyView.setModel(proxyModel);
        proxyView.setSortingEnabled(true);
        //proxyView.sortByColumn(1, Qt.SortOrder.AscendingOrder);

        QGridLayout proxyLayout = new QGridLayout();
        proxyLayout.addWidget(proxyView, 0, 0, 1, 3);
        proxyLayout.addWidget(filterPatternLabel, 1, 0);
        proxyLayout.addWidget(filterPatternLineEdit, 1, 1);
        proxyLayout.addWidget(filterSyntaxComboBox, 1, 2);
        proxyLayout.addWidget(filterCaseSensitivityCheckBox, 2, 0, 1, 3);
        proxyLayout.addWidget(fromLabel, 3, 0);
        proxyLayout.addWidget(fromDateEdit, 3, 1, 1, 2);
        proxyLayout.addWidget(toLabel, 4, 0);
        proxyLayout.addWidget(toDateEdit, 4, 1, 1, 2);

        proxyGroupBox = new QGroupBox(tr("Sorted/Filtered Model"));
        proxyGroupBox.setLayout(proxyLayout);

        QVBoxLayout mainLayout = new QVBoxLayout();
        mainLayout.addWidget(sourceGroupBox);
        mainLayout.addWidget(proxyGroupBox);
        setLayout(mainLayout);

        setWindowTitle(tr("Custom Sort/Filter Model"));
        setWindowIcon(new QIcon("classpath:com/trolltech/images/qt-logo.png"));
        resize(500, 450);

        textFilterChanged();
        dateFilterChanged();
    }

    private void addMail(QAbstractItemModel model, String subject,
                         String sender, QDateTime date) {
        model.insertRow(0);
        model.setData(model.index(0, 0), subject);
        model.setData(model.index(0, 1), sender);
        model.setData(model.index(0, 2), date);
    }

    private QStandardItemModel createMailModel(QObject parent) {
        QStandardItemModel model = new QStandardItemModel(0, 3, parent);

        model.setHeaderData(0, Qt.Orientation.Horizontal, tr("Subject"));
        model.setHeaderData(1, Qt.Orientation.Horizontal, tr("Sender"));
        model.setHeaderData(2, Qt.Orientation.Horizontal, tr("Date"));

        addMail(model, "Happy New Year!", "Grace K. <grace@software-inc.com>",
                new QDateTime(new QDate(2006, 12, 31), new QTime(17, 03)));
        addMail(model, "Radically new concept",
                "Grace K. <grace@software-inc.com>",
                new QDateTime(new QDate(2006, 12, 22), new QTime(9, 44)));
        addMail(model, "Accounts", "pascale@nospam.com",
                new QDateTime(new QDate(2006, 12, 31), new QTime(12, 50)));
        addMail(model, "Expenses", "Joe Bloggs <joe@bloggs.com>",
                new QDateTime(new QDate(2006, 12, 25), new QTime(11, 39)));
        addMail(model, "Re: Expenses", "Andy <andy@nospam.com>",
                new QDateTime(new QDate(2007, 01, 02), new QTime(16, 05)));
        addMail(model, "Re: Accounts", "Joe Bloggs <joe@bloggs.com>",
                new QDateTime(new QDate(2007, 01, 03), new QTime(14, 18)));
        addMail(model, "Re: Accounts", "Andy <andy@nospam.com>",
                new QDateTime(new QDate(2007, 01, 03), new QTime(14, 26)));
        addMail(model, "Sports", "Linda Smith <linda.smith@nospam.com>",
                new QDateTime(new QDate(2007, 01, 05), new QTime(11, 33)));
        addMail(model, "AW: Sports", "Rolf Newschweinstein <rolfn@nospam.com>",
                new QDateTime(new QDate(2007, 01, 05), new QTime(12, 00)));
        addMail(model, "RE: Sports", "Petra Schmidt <petras@nospam.com>",
                new QDateTime(new QDate(2007, 01, 05), new QTime(12, 01)));

        return model;
    }

    private void textFilterChanged() {
    	return;
//        QRegExp.PatternSyntax syntax;
//        int index = filterSyntaxComboBox.currentIndex();
//        syntax = (QRegExp.PatternSyntax) filterSyntaxComboBox.itemData(index);
//
//        Qt.CaseSensitivity caseSensitivity;
//        if (filterCaseSensitivityCheckBox.isChecked())
//            caseSensitivity = Qt.CaseSensitivity.CaseSensitive;
//        else
//            caseSensitivity = Qt.CaseSensitivity.CaseInsensitive;
//
//        QRegExp regExp = new QRegExp(filterPatternLineEdit.text(),
//                                     caseSensitivity, syntax);
//        proxyModel.setFilterRegExp(regExp);
    }

    private void dateFilterChanged() {
        proxyModel.setFilterMinimumDate(fromDateEdit.date());
        proxyModel.setFilterMaximumDate(toDateEdit.date());
    }

    /* A NEW CLASS
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    private class MySortFilterProxyModel extends QSortFilterProxyModel {
        private QDateTime minDate = new QDateTime();
        private QDateTime maxDate = new QDateTime();

        private MySortFilterProxyModel(QObject parent) {
            super(parent);
        }

        private void setFilterMinimumDate(QDate date) {
            minDate = new QDateTime(date);
            invalidateFilter();
        }

        private void setFilterMaximumDate(QDate date) {
            maxDate = new QDateTime(date);
            invalidateFilter();
        }

        @Override
        protected boolean filterAcceptsRow(int sourceRow,
                                           QModelIndex sourceParent) {
            QModelIndex index0;
            QModelIndex index1;
            QModelIndex index2;

            index0 = sourceModel().index(sourceRow, 0, sourceParent);
            index1 = sourceModel().index(sourceRow, 1, sourceParent);
            index2 = sourceModel().index(sourceRow, 2, sourceParent);

            QRegExp filter = filterRegExp();
            QAbstractItemModel model = sourceModel();
            boolean matchFound;

            matchFound = filter.indexIn(model.data(index0).toString()) != -1
                         || filter.indexIn(model.data(index1).toString()) != -1;

            return matchFound && dateInRange((QDateTime) (model.data(index2)));
        }

        @Override
        protected boolean lessThan(QModelIndex left, QModelIndex right) {

            boolean result = false;
            Object leftData = sourceModel().data(left);
            Object rightData = sourceModel().data(right);

            if (leftData instanceof QDateTime
                && rightData instanceof QDateTime) {

                QDateTime leftDate = (QDateTime) leftData;
                QDateTime rightDate = (QDateTime) rightData;

                result = leftDate.compareTo(rightDate) < 0;
            } else {

                QRegExp emailPattern = new QRegExp("([\\w\\.]*@[\\w\\.]*)");

                String leftString = leftData.toString();
                if(left.column() == 1 && emailPattern.indexIn(leftString) != -1)
                    leftString = emailPattern.cap(1);

                String rightString = rightData.toString();
                if(right.column() == 1 && emailPattern.indexIn(rightString) != -1)
                    rightString = emailPattern.cap(1);

                result = leftString.compareTo(rightString) < 0;
            }
            return result;
        }

        private boolean dateInRange(QDateTime date) {
            return (minDate.compareTo(date) < 0 && maxDate.compareTo(date) > 0);
        }
    }
}

/* EOF */