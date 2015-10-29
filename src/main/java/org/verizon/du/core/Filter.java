/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.util.Map;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class Filter {

   
    private final String customerId;
    private final String webSite;
    private final int startHr;
    private final int startMin;
    private final int endHr;
    private final int endMin;
    private Pattern custIdPattern;
    private Pattern webSitePattern;
    
     public Filter(String customerId, String webSite, int startHr, int startMin,int endHr,int endMin) {
        this.customerId = customerId;
        this.webSite = webSite;
        this.startHr = startHr;
        this.startMin = startMin;
        this.endHr = endHr;
        this.endMin = endMin;
        this.custIdPattern=Pattern.compile(customerId);
        this.webSitePattern=Pattern.compile(webSite);
    }
    
    public boolean canExclude(DataUsage usage) {
          if(custIdPattern.matcher(usage.getCustomerId()).matches())
           return true; 
          else if(webSitePattern.matcher(usage.getWebsite()).matches())
           return true; 
          else if(usage.getStartTime().getHours()>=(this.startHr)&&
                  usage.getStartTime().getMinutes()>=(this.startMin)&&
                  usage.getEndTime().getHours()<=(this.endHr)&&
                  usage.getEndTime().getMinutes()<=(this.endMin))
           return true;
          else return false;
     }
    
     
     
     

}
