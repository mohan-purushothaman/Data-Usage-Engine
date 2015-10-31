/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.core;

import java.util.regex.Pattern;

/**
 *
 * "*" in DB means exclude all for that field
 *
 * 
 * as of now starttime can't more than end, good to have
 * @author Administrator
 */
public class Filter {

    private final String webSite;
    private final int startHr;
    private final int startMin;
    private final int endHr;
    private final int endMin;

    private final boolean ignoreWebsiteCheck;
    //private Pattern webSitePattern;

    public Filter(String webSite, int startHr, int startMin, int endHr, int endMin) {
        ignoreWebsiteCheck = BaseConfig.STAR.equals(webSite);
        this.webSite = ignoreWebsiteCheck ? null : webSite;
        this.startHr = startHr;
        this.startMin = startMin;
        this.endHr = endHr;
        this.endMin = endMin;
        //this.webSitePattern = Pattern.compile(webSite);
    }

    public boolean canExclude(DataUsage usage) {
        //planned functionality , good to have
//        if (webSitePattern.matcher(usage.getWebsite()).matches()) {
//            return true;
//        } else 
        
        if (!ignoreWebsiteCheck && webSite.equalsIgnoreCase(usage.getWebsite())) {
            //websites are case incensitive // for performance boost sender can agree to send fixed case

            return true;
        }
        return usage.getStartTime().getHours() >= (this.startHr)
                && usage.getStartTime().getMinutes() >= (this.startMin)
                && usage.getEndTime().getHours() <= (this.endHr)
                && usage.getEndTime().getMinutes() <= (this.endMin);

    }

}
