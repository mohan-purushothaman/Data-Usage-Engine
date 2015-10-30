/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.verizon.du.core.Alert;
import org.verizon.du.core.AlertSystem;
import org.verizon.du.core.Customer;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleAlertSystem implements AlertSystem {

    private static final long MAX_SIZE = 150;
    private static final long alertStartSize = MAX_SIZE * 1024 * 1024 * 1024 * 7 / 10; //70% of MAX_SIZE

    private static final long ONE_PERCENT_OF_MAX_SIZE = MAX_SIZE * 1024 * 1024 * 1024 / 100;

    private static final Logger log = LoggerFactory.getLogger(SimpleAlertSystem.class);

    @Override
    public Alert processAlerts(Customer customer) {

        long monthlyUsage = customer.getMonthUsage();
        if (monthlyUsage < alertStartSize) {
            return null;
        }
        int after = (int) (customer.getMonthUsage() / ONE_PERCENT_OF_MAX_SIZE);
        int before = (int) (customer.getMonthUsageBeforeUpdate() / ONE_PERCENT_OF_MAX_SIZE);

        if (before > 100) {
            return null;
        }

        if ((before % 5 > after % 5 || ((after - before) > 5))) {
            //need to do alery stff here
            log.info("need to alert this customer for " + customer.getCustomerId() + " for " + after + " % usage of " + MAX_SIZE + " GB");
            return new Alert(after, customer);
        }
        return null;
    }

}
