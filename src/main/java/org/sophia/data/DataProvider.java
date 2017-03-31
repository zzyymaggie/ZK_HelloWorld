/*
 * @(#)DataProvider.java
 *
 * Copyright (c) 1999-2017 7thOnline, Inc.
 * 24 W 40th Street, 11th Floor, New York, NY 10018, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of 7thOnline,
 * Inc. ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with 7thOnline.
 */
package org.sophia.data;

import java.util.HashMap;
import java.util.Map;

public class DataProvider {
    
    public static Map<Integer, String> getDataMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for(int i=1;i<10;i++) {
            map.put(i, "demo_" + i);
        }
        return map;      
    }
}
