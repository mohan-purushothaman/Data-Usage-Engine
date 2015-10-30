/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Customer {
    private final String customerId;
    private final Usage[] hourUsage;  //0-23
    private final Usage[] dayUsage; //0-30
    private long monthUsage;

    private boolean persistPending;
    
    public Customer(String customerId, Usage[] hourUsage, Usage[] dayUsage, long monthUsage) {
        this.customerId = customerId;
        this.hourUsage = hourUsage;
        this.dayUsage = dayUsage;
        this.monthUsage = monthUsage;
    }

    public String getCustomerId() {
        return customerId;
    }
    
    
    
    public Usage findDayUsage(DataUsage usage) {
         return dayUsage[usage.getEndTime().getDate()];
    }

    public Usage findHourUsage(DataUsage usage) {
         return hourUsage[usage.getEndTime().getHours()];
    }

    public long getMonthUsage() {
        return monthUsage;
    }

   

    public void addToMonthlyUsage(long usageBytes) {
        this.monthUsage+=usageBytes;
         persistPending=true;
    }

    public Usage[] getHourUsage() {
        return hourUsage;
    }

    public Usage[] getDayUsage() {
        return dayUsage;
    }
    
    public void statePersisited(){
        for(Usage u:dayUsage){
            u.setUsageChanged(true);
        }
        for(Usage u:dayUsage){
            u.setUsageChanged(false);
        }
        persistPending=false;
    }

    public boolean isPersistPending() {
        return persistPending;
    }
    
}
