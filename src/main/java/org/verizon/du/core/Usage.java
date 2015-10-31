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

    private long nonpersistedUsage;
    private long persistedUsage;

    public Usage(long persistedUsage) {
        this.persistedUsage = persistedUsage;
        this.nonpersistedUsage = persistedUsage;
    }

    public long getPersistedUsage() {
        return persistedUsage;
    }

    public void setPersistedUsage(long persistedUsage) {
        this.persistedUsage = persistedUsage;
    }

    public long getNonpersistedUsage() {
        return nonpersistedUsage;
    }

    
public boolean persistNeeded(){
    return persistedUsage!=nonpersistedUsage;
}
   

    public void addUsage(long usageBytes) {
        assert usageBytes != 0;

        this.nonpersistedUsage += usageBytes;
    }

}
