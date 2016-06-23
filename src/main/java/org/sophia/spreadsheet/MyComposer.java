/**
 * Copyright © sophia. All Rights Reserved.
 */
package org.sophia.spreadsheet;

import java.io.File;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zss.api.Importer;
import org.zkoss.zss.api.Importers;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.ui.Spreadsheet;

/**
 * Demonstrate Importer and display the spreadsheet originally
 * @author Sophia
 * 
 */
@SuppressWarnings("serial")
public class MyComposer extends SelectorComposer<Component> {

	@Wire
	Spreadsheet ss;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  //wire variables and event listeners
		//access components after calling super.doAfterCompose()
		//import and load the template excel file "/WEB-INF/books/startzss.xlsx"
		Importer importer = Importers.getImporter();
		Book book = importer.imports(getFile(), "sample");
		ss.setBook(book);
	}
	
	
	private File getFile() {
		//get a file 
		return new File(WebApps.getCurrent().getRealPath("/WEB-INF/books/startzss.xlsx"));
	}
}
