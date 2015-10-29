/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import org.springframework.stereotype.Component;
import org.verizon.du.core.AlertSystem;
import org.verizon.du.core.Customer;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleAlertSystem implements AlertSystem{

    @Override
    public void processAlerts(Customer customer) {
        return;
    }
    
}
