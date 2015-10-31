/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * // all usage updates should happen through this object
 *
 * @author Administrator
 */
public class Customer {

    private final String customerId;
    private final Map<UsageType, Usage[]> usage;  //0-23 && 0-30

    private final String email;
    private final int TN;

    public Customer(String customerId, Map<UsageType, Usage[]> usage, String email, int TN) {
        this.customerId = customerId;
        this.email = email;
        this.TN = TN;
        this.usage = usage;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Usage findUsage(UsageType type, DataUsage usage) {
        return this.usage.get(type)[type.findIndex(usage)];
    }

    public Usage[] getHourUsage() {
        return usage.get(UsageType.HOUR);
    }

    public Usage[] getDayUsage() {
        return usage.get(UsageType.HOUR);
    }

    public Usage getMonthUsage() {
        return usage.get(UsageType.MONTH)[0];
    }

    public void setPersisted() {

        for (Usage[] ua : usage.values()) {
            for (Usage u : ua) {
                u.setPersistedUsage(u.getNonpersistedUsage());
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public int getTN() {
        return TN;
    }

}
