/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.util.Date;

/**
 *
 * @author Mohan Purushothaman
 */
public enum UsageType {

    HOUR("HR_",BaseConfig.HOUR_SEGMENTS),
    DAY("DAY_",BaseConfig.DAY_SEGMENTS),
    MONTH("MONTH_",1);
    
    
    private final  String dbColumnPrefix;
    private final int segmentSize;

    private UsageType(String dbColumnPrefix, int segmentSize) {
        this.dbColumnPrefix = dbColumnPrefix;
        this.segmentSize = segmentSize;
    }

    
    
    
    
    public String getDbColumnPrefix() {
        return dbColumnPrefix;
    }

    public int getSegmentSize() {
        return segmentSize;
    }
    
     public String getDbColumn(int index) {
        return getDbColumnPrefix()+index;
    }
    
    
    public int findIndex(DataUsage usage){
        switch(this){
            case HOUR:
                return usage.getEndTime().getHours();
            case DAY:
                return usage.getEndTime().getDate()-1;
            case MONTH:
                return 0;
        }
        throw new UnsupportedOperationException("not implemented index conversion");
    } 

}
