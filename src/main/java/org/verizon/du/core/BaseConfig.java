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
public class BaseConfig {
    public static final String SPLIT_STRING="#";
    
    public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    public static final int BUFFER_SIZE=1024*1024 ;
    public static final int STREAM_BUFFER_SIZE=2*1024*1024 ;
    
    
    public static final int HOUR_SEGMENTS=24;
    public static final int DAY_SEGMENTS=31;
    public static int THREAD_POOL_SIZE=3;
    
    public static final String STAR="*";
    
}
