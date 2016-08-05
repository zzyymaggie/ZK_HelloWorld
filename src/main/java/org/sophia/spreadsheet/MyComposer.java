/**
 * Copyright © sophia. All Rights Reserved.
 */
package org.sophia.spreadsheet;

import java.io.File;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zss.api.CellOperationUtil;
import org.zkoss.zss.api.Importer;
import org.zkoss.zss.api.Importers;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Range.DeleteShift;
import org.zkoss.zss.api.Range.InsertCopyOrigin;
import org.zkoss.zss.api.Range.InsertShift;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zss.ui.event.CellMouseEvent;

/**
 * 
 * @author Sophia
 * @date 08/05/16
 */
@SuppressWarnings("serial")
public class MyComposer extends SelectorComposer<Component> {

    @Wire
    Spreadsheet ss;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp); // wire variables and event listeners
        Importer importer = Importers.getImporter();
        Book book = importer.imports(getFile(), "sample");
        ss.setBook(book);
    }

    private File getFile() {
        // get a file
        return new File(WebApps.getCurrent().getRealPath(
                "/WEB-INF/books/startzss.xlsx"));
    }

    /**
     * click "add/ ..." cell will insert 10 rows and "del ..." cell will delete 10 rows
     */
    @Listen("onCellClick = #ss")
    public void onCellClick(CellMouseEvent event) {
        int row = event.getRow();
        int col = event.getColumn();
        Range selectedRange = Ranges.range(ss.getSelectedSheet(), row, col);
        String cellValue = (String) selectedRange.getCellValue();
        if (!cellValue.startsWith("add") && !cellValue.startsWith("del")) {
            return;
        }
        if (cellValue.startsWith("add")) {
            cellValue = cellValue.replace("add", "del");
            selectedRange.setCellValue(cellValue);
            insert10Rows(row, col);
        } else {
            cellValue = cellValue.replace("del", "add");
            selectedRange.setCellValue(cellValue);
            delete10Rows(row, col);
        }
    }

    /**
     * dynamic insert 10 rows and set cell value
     */
    private void insert10Rows(int row, int col) {
        int startRow = row + 1;
        int endRow = startRow + 9;
        int maxCol = 9;
        // mark to insert to the range that contains 10 row
        Range range = Ranges.range(ss.getSelectedSheet(), startRow, col,
                endRow, maxCol);

        // to affect all columns
        range = range.toRowRange();
        range.setAutoRefresh(false);
        // shift existing row down and copy style from above cell
        CellOperationUtil.insert(range, InsertShift.DOWN, InsertCopyOrigin.FORMAT_LEFT_ABOVE);
        ss.setMaxVisibleRows(ss.getMaxVisibleRows() + 10);
        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j <= maxCol; j++) {
                Range cellRange = Ranges.range(ss.getSelectedSheet(), i, j);
                cellRange.setAutoRefresh(false);
                cellRange.setCellValue(i * j);
            }
        }
    }

    /**
     * dynamic delete 10 rows
     */
    private void delete10Rows(int row, int col) {
        int startRow = row + 1;
        int endRow = startRow + 9;

        // mark to insert to the range that contains 10 row
        Range range = Ranges.range(ss.getSelectedSheet(), startRow, col,
                endRow, ss.getMaxVisibleColumns());
        range = range.toRowRange();
        range.setAutoRefresh(false);
        CellOperationUtil.delete(range, DeleteShift.UP);
        ss.setMaxVisibleRows(ss.getMaxVisibleRows() - 10);
    }
}
