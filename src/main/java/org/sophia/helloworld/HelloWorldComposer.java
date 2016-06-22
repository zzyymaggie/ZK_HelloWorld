/**
 * 
 */
package org.sophia.helloworld;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;


/**
 * @author Sophia
 *
 */
public class HelloWorldComposer extends GenericForwardComposer{
    
    private Label lbl;
    
    public void onClick$btn(Event event){
        lbl.setValue("Hello World !!!!!!!!!!!!");
    }

}
