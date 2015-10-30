/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.verizon.du.beans.impl.CustomerFactory;
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
    public Set<Customer> snapshot(@RequestParam String customerIds){
        Set<Customer> customer=new HashSet<Customer>();
        for(String custId:customerIds.split(BaseConfig.SPLIT_STRING)){
            customer.add(custFactory.findCustomer(custId));
        }
        return customer;
    }
    
    
}
