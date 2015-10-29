/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class Engine {
    
    @Autowired
    Aggregator aggregator;
    
    @Autowired
    ExcludeFilter filter;
    
    @Autowired
    AlertSystem alertSystem;
    
    public void process(DataUsage usage) throws Exception{
        
        if(filter.canExclude(usage)){
            //need to plan on storing excluded data
        }else{
            //we need to aggregate this datausage
            alertSystem.processAlerts(aggregator.aggregate(usage));
        }
        
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
