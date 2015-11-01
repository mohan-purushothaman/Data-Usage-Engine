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
import org.verizon.du.core.UsageType;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleAggregator implements Aggregator {

    @Autowired
    CustomerFactory factory;

    @Override
    public Customer aggregate(DataUsage usage)throws Exception {
        Customer customer = factory.findCustomer(usage.getCustomerId());

        synchronized (customer.getCustomerLock()) {
            customer.clearDirtyRead(usage);

            for (UsageType t : UsageType.values()) {
                customer.findUsage(t, usage).addUsage(usage.getUsage());
            }
        }

        return customer;
    }

}
