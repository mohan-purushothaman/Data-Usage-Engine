/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class Engine {
    public boolean process(String string) throws Exception{
        DataUsage usage=createDataUsage(string);
        return true;
    }

    /**
     * basic factory method for creating data usage object
     * @param usageString  
     * @return 
     */
    private static DataUsage createDataUsage(String usageString) throws Exception {
        String [] split=usageString.split(BaseConfig.SPLIT_STRING);
        return new DataUsage(split[0], split[1], split[2], Long.parseLong(split[3]), parseDate(split[4]), parseDate(split[5]));
    }
    
    
    
    private static Date parseDate(String dateString) throws Exception{
        return new SimpleDateFormat(BaseConfig.dateFormat).parse(dateString);
    }
}
