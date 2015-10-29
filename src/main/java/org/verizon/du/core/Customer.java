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
    private final Map<Integer,Usage> hourUsage;
    private final Map<Integer,Usage> dayUsage;
    private long monthUsage;

    public Customer(String customerId, Map<Integer, Usage> hourUsage, Map<Integer, Usage> dayUsage, long monthUsage) {
        this.customerId = customerId;
        this.hourUsage = hourUsage;
        this.dayUsage = dayUsage;
        this.monthUsage = monthUsage;
    }

    public String getCustomerId() {
        return customerId;
    }
    
    
    
    public Usage findDayUsage(DataUsage usage) {
         return dayUsage.get(usage.getEndTime().getDay());
    }

    public Usage findHourUsage(DataUsage usage) {
         return hourUsage.get(usage.getEndTime().getHours());
    }

    public long getMonthUsage() {
        return monthUsage;
    }

    public void setMonthUsage(long monthUsage) {
        this.monthUsage = monthUsage;
    }

    public void addToMonthlyUsage(long usageBytes) {
        this.monthUsage+=usageBytes;
    }

    public Map<Integer, Usage> getHourUsage() {
        return hourUsage;
    }

    public Map<Integer, Usage> getDayUsage() {
        return dayUsage;
    }
    
    
}
