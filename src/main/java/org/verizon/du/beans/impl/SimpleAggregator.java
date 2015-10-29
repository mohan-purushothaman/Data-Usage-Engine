/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.verizon.du.core.Aggregator;
import org.verizon.du.core.Customer;
import org.verizon.du.core.DataUsage;
import org.verizon.du.core.Usage;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleAggregator implements Aggregator{

    @Autowired
    CustomerFactory factory;
    
    @Override
    public Customer aggregate(DataUsage usage) {
        Customer customer=factory.findCustomer(usage.getCustomerId());
        long usageBytes=usage.getUsage();
       customer.findHourUsage(usage).addUsage(usageBytes);
       customer.findDayUsage(usage).addUsage(usageBytes);
       
        return customer;
    }
    
}
