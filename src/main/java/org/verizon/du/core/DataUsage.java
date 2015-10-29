/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class DataUsage {
    private final String customerId;
    private final String deviceId;
    private final String website;
    private final long usage; //in bytes
    private final Date startTime;
    private final Date endTime;

    public DataUsage(String customerId, String deviceId, String website, long usage, Date startTime, Date endTime) {
        this.customerId = customerId;
        this.deviceId = deviceId;
        this.website = website;
        this.usage = usage;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getWebsite() {
        return website;
    }

    public long getUsage() {
        return usage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "DataUsage{" + "customerId=" + customerId + ", deviceId=" + deviceId + ", website=" + website + ", usage=" + usage + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }

   
    
}
