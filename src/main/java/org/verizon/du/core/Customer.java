/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *
 *
 * @author Administrator
 */
public class Customer {

    private final String customerId;
    private final Map<UsageType, Usage[]> usage;  //0-23 && 0-30

    private final int billCycleDay; //1 means first day upto 28 just to be on safe side
    private final String email;
    private final int TN;

    private Date lastUsageTime;

    private Date persistedUsageTime;

    private final Calendar nextBilledDate;

    private boolean needToBill;
    
    
    private final Object  customerLock=new Object();
    

    public Customer(String customerId, Map<UsageType, Usage[]> usage, int billCycleDay, String email, int TN, Date lastUsageTime) {
        this.customerId = customerId;
        this.email = email;
        this.TN = TN;
        this.usage = usage;

        assert billCycleDay > 0 && billCycleDay <= 28 : "Billcycle should be between 0-28 (both inclusive), 0 not suppoted yet";

        this.billCycleDay = billCycleDay;
        this.lastUsageTime = (lastUsageTime==null)?new Date():lastUsageTime;
        this.persistedUsageTime = this.lastUsageTime;
        this.nextBilledDate = calculateNextBillCycleDate(persistedUsageTime, billCycleDay);
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

    public Date getLastUsageTime() {
        return lastUsageTime;
    }

    public void setLastUsageTime(Date lastUsageTime) {
        this.lastUsageTime = lastUsageTime;
    }

    public int getBillCycleDay() {
        return billCycleDay;
    }

    public boolean persistNeeded() {
        return getMonthUsage().persistNeeded();
    }

    /**
     * responsible for clearing dirty reads and archive month usage
     *
     * @param usage
     */
    public void clearDirtyRead(DataUsage usage) {
        if (lastUsageTime == null) {
            lastUsageTime = usage.getEndTime();
        } else if (usage.getEndTime().after(lastUsageTime)) {
            lastUsageTime = usage.getEndTime();
            //check whether customer bill cycle crossed
            if (!needToBill) {
                if (lastUsageTime.getTime() > (nextBilledDate.getTimeInMillis())) {
                    needToBill = true;
                    Usage monthUsage = this.usage.get(UsageType.MONTH)[0];
                    monthUsage.setPersistedUsage(getMonthUsage().getNonpersistedUsage()); //copy current usage to temp
                    monthUsage.clearNonPersistUsage();

                    for (UsageType type : new UsageType[]{UsageType.HOUR, UsageType.DAY}) {
                        for (Usage u : this.usage.get(type)) {
                            u.clearNonPersistUsage();
                        }
                    }

                } else {
                    //check for clearing hour fields
                    long hourDiff = (this.lastUsageTime.getTime() - this.persistedUsageTime.getTime()) / (60 * 60 * 1000);
                    if (hourDiff > 23) {
                        int startHour = this.persistedUsageTime.getHours();
                        Usage[] hourUsage = this.usage.get(UsageType.HOUR);
                        int processedSize = 0;
                        while (hourDiff-- > 23 && processedSize++ < 24) {
                            hourUsage[(startHour + processedSize) % 24].clearNonPersistUsage();
                        }
                    }
                }
            }
        }

    }

    private static Calendar calculateNextBillCycleDate(Date lastUnBilledDate, int billCycleDay) {
        Calendar nextBilledDate = Calendar.getInstance();
        nextBilledDate.setTime(lastUnBilledDate);
        nextBilledDate.set(Calendar.HOUR_OF_DAY, 0);
        nextBilledDate.set(Calendar.MINUTE, 0);
        nextBilledDate.set(Calendar.SECOND, 0);

        nextBilledDate.set(Calendar.DAY_OF_MONTH, billCycleDay); //one of the bill cycle date of customer, either recent or next
        if (nextBilledDate.before(lastUnBilledDate)) { // if recent find next
            nextBilledDate.add(Calendar.MONTH, 1);
        }
        return nextBilledDate;
    }

    public Date getPersistedUsageTime() {
        return persistedUsageTime;
    }

    public void setPersistedUsageTime(Date persistedUsageTime) {
        this.persistedUsageTime = persistedUsageTime;
    }

    public boolean isNeedToBill() {
        return needToBill;
    }
    
    @JsonIgnore
    public final Object getCustomerLock(){
        return customerLock;
    }

    public Calendar getNextBilledDate() {
        return nextBilledDate;
    }

}
