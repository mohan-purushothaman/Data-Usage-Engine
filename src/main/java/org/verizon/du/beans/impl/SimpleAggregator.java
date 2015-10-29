/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import org.springframework.stereotype.Component;
import org.verizon.du.core.Aggregator;
import org.verizon.du.core.Customer;
import org.verizon.du.core.DataUsage;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleAggregator implements Aggregator{

    @Override
    public Customer aggregate(DataUsage usage) {
        //Customer customer=
        return null;
    }
    
}
