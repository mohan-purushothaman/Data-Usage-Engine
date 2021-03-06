/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.verizon.du.core.Aggregator;
import org.verizon.du.core.Alert;
import org.verizon.du.core.AlertSystem;
import org.verizon.du.core.DataUsage;
import org.verizon.du.core.ExcludeFilter;

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

    public void process(DataUsage usage) throws Exception {

        if (filter.canExclude(usage)) {
            //do we need to store excluded data ??!!
        } else {
            Alert alert = alertSystem.processAlerts(aggregator.aggregate(usage));
            if (alert != null) {
                // alert generated 
            }
        }

    }

}
