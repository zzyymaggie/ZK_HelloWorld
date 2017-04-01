/**
 * Copyright © sophia. All Rights Reserved.
 */
package org.sophia.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;


/**
 * @author Sophia
 *
 */
public class HelloWorldComposer extends SelectorComposer<Component>{
    @Wire
    private Label lbl;
    
    @Listen("onClick=#btn")
    public void onShowContent(Event event){
        lbl.setValue("Hello World !!!!!!!!!!!!");
    }

}
