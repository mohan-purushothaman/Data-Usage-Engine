/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.verizon.du.beans.impl.CustomerFactory;
import org.verizon.du.beans.impl.ProgressTracker;
import org.verizon.du.core.BaseConfig;
import org.verizon.du.core.Customer;

/**
 *
 * @author Administrator
 */
@Controller
public class ReportController {
    
    
    @Autowired
    CustomerFactory factory;
    
    @RequestMapping("/allUsage")
    @ResponseBody
    public Collection<Customer> getUsageReport(@RequestParam String custId){
        return factory.getLoadedCusomerList();
    }
    
    
     @Autowired
   CustomerFactory custFactory;
   
    @RequestMapping("/usageData")
    @ResponseBody
    public Map snapshot(@RequestParam String customerIds) throws Exception{
        
            Map<String,List<Customer>> map=new HashMap<String,List<Customer>>();
        
        List<Customer> customer=new ArrayList<Customer>();
        for(String custId:customerIds.split(BaseConfig.SPLIT_STRING)){
            customer.add(custFactory.findCustomer(custId));
        }
        map.put("data", customer);
        return map;
        //return customer;
    }
    
    @Autowired
    private ProgressTracker p;
    
    @RequestMapping("currentProgress")
    @ResponseBody
    public ProgressTracker.Progress currentProgress() throws Exception{
            return p.getRecentJobProgress();
            
    }
}
