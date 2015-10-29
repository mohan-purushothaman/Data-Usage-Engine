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
public class Usage {
   
    private long usage;
 private boolean usageChanged;

    public Usage(long usage, boolean usageChanged) {
        this.usage = usage;
        this.usageChanged = usageChanged;
    }

    public long getUsage() {
        return usage;
    }

    public boolean isUsageChanged() {
        return usageChanged;
    }
   
    
    public void addUsage(long usageBytes){
        this.usage+=usageBytes;
        this.usageChanged=true;
    }
    
}
