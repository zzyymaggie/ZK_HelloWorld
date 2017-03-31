package org.sophia.composer;

import java.util.Map;

import org.sophia.data.DataProvider;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zss.ui.event.CellMouseEvent;
import org.zkoss.zss.ui.event.KeyEvent;
import org.zkoss.zss.ui.event.StopEditingEvent;
import org.zkoss.zul.Messagebox;


@SuppressWarnings("serial")
public class DeleteKeyComposer extends SelectorComposer<Component> {

	@Wire
	Spreadsheet ss;
	
	private Map<Integer, String> dataMap = DataProvider.getDataMap();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  //wire variables and event listeners
		populate();
	}
    
	private void populate() {
	    Sheet sheet = ss.getBook().getSheetAt(0);
        for(Integer row: dataMap.keySet()) {
            this.setCellValue(sheet, row, 0, dataMap.get(row));
        }
	}
	
    public String getCellValue(Sheet sheet, int row, int col) {
    	Range range = Ranges.range(sheet, row, col);
        Object value = range.getCellValue();
        if(value != null){
            value = value.toString().trim();
        }
        
        return value==null ? "" : value.toString();
    }
    
    public void setCellValue(Sheet sheet, int row, int col, Object val) {
        if(row < 0 || col < 0){
            return;
        }
        
        Range range = Ranges.range(sheet, row, col);
        range.setCellValue(val);
        range.notifyChange();
    }
    
    private boolean validateInput(Object inputValue) {
        if(inputValue == null) {
            return false;
        }
        String input = (String)inputValue;
        if(input.isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Listen("onStopEditing = #ss")
    public void onStopEditing(StopEditingEvent event){
        Sheet sheet = ss.getBook().getSheetAt(0);
        String originValue = getCellValue(sheet, event.getRow(), event.getColumn());
        Object editingValue = event.getEditingValue();
        //We assume this field is a required field. If edit value is empty, the cell will roll back to its origin value
        if(!validateInput(editingValue)) {
            Messagebox.show("Number cannot be blank", "Error", Messagebox.OK, Messagebox.ERROR);
            event.cancel();
        }else { //when edit value is not empty, we will update dataMap to new value for this row.
            dataMap.put(event.getRow(), event.getEditingValue().toString());
        }
    }
    
    @Listen("onCtrlKey = #ss")
    public void onCtrlKey(KeyEvent event){
        int editRow = event.getSelection().getRow();
        int editCol = event.getSelection().getColumn();
        Sheet sheet = ss.getBook().getSheetAt(0);
        String originValue = getCellValue(sheet, editRow, editCol);
        switch (event.getKeyCode()){
            case KeyEvent.DELETE:
                //We assume this field is a required field. If edit value is empty, the cell will roll back to its origin value
                if(!validateInput("")){ 
                    //pop up message
                    Messagebox.show("Number cannot be blank", "Error", Messagebox.OK, Messagebox.ERROR);
                    //FIXME: I want to roll back to origin value, but origin value has changed to ""
                    this.setCellValue(sheet, editRow, editCol, originValue);
                }else { //When edit value is not empty, we will update dataMap to new value for this row.
                    dataMap.put(editRow, this.getCellValue(sheet, editRow, editCol));
                }
            break;
        }
    }
    
    /**
     * display the map to simulate save to database operation
     */
    @Listen("onCellClick = #ss")
    public void onCellClick(CellMouseEvent event) {
        Sheet sheet = ss.getBook().getSheetAt(0);
        String value = this.getCellValue(sheet, event.getRow(), event.getColumn());
        if(value.contains("save")) {
            System.out.println(dataMap);
        }
    }
}
