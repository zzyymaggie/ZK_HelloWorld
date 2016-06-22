/**
 * 
 */
package org.louis.helloworld;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;


/**
 * @author lli
 *
 */
public class HelloWorldComposer extends GenericForwardComposer{
    
    private Label lbl;
    
    public void onClick$btn(Event event){
        lbl.setValue("Hello World !!!!!!!!!!!!");
    }

}
