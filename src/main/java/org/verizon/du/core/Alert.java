/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

/**
 *
 * @author Administrator
 */
public class Alert {
    
    private final Customer customer;
    //more implementation required for different type of messages
    
    private final int alertPercentage;

    public Alert( int alertPercentage,Customer customer) {
        this.alertPercentage = alertPercentage;
        this.customer=customer;
    }

    public Customer getCustomer() {
        return customer;
    }
    
   

    public int getAlertPercentage() {
        return alertPercentage;
    }
    
    
    
    
}
