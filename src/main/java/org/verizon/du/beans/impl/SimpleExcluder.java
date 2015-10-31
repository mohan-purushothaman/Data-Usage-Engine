/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import static org.verizon.du.core.BaseConfig.*;
import org.verizon.du.core.DataUsage;
import org.verizon.du.core.ExcludeFilter;
import org.verizon.du.core.Filter;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleExcluder implements ExcludeFilter {

    private final Map<String, Filter> customerMap = new HashMap<>();
    private final Map<String, Filter> websiteMap = new HashMap<>();
    private final Filter[] timeFilterList;

    @Autowired
    public SimpleExcluder(DataSource dataSource) {
        timeFilterList = loadExcludeFilters(dataSource);
        loadExcludeFilters(dataSource);
    }

    @Override
    public boolean canExclude(DataUsage usage) {
        Filter filter = customerMap.get(usage.getCustomerId());
        if (filter == null) {
            filter = websiteMap.get(usage.getWebsite());
        }
        if (filter != null && filter.canExclude(usage)) {
            return true;
        }

        for (Filter fltr : timeFilterList) {
            if (fltr.canExclude(usage)) {
                return true;
            }
        }
        return false;
    }

    private Filter[] loadExcludeFilters(DataSource dataSource) {
        final List<Filter> filters = new LinkedList<>();

        new JdbcTemplate(dataSource).query("SELECT * FROM EXCLUDE_USAGE_FILTERS", new RowCallbackHandler() {

            @Override
            public void processRow(ResultSet rs) throws SQLException {
                String custId = rs.getString("CUSTID");
                String webSite = rs.getString("WEBSITE");
                int startTimeHour = rs.getInt("START_TIME_HR");
                int startTimerMinute = rs.getInt("START_TIME_MIN");
                int endTimeHour = rs.getInt("END_TIME_HR");
                int endTimerMinute = rs.getInt("END_TIME_MIN");

                if (custId.equals(STAR)) {
                    if (webSite.equals(STAR)) {
                        filters.add(new Filter(STAR, startTimeHour, startTimerMinute,
                                endTimeHour, endTimerMinute));
                    } else {
                        websiteMap.put(webSite, new Filter(STAR, startTimeHour, startTimerMinute,
                                endTimeHour, endTimerMinute));

                    }
                } else {
                    customerMap.put(custId, new Filter(webSite, startTimeHour, startTimerMinute,
                            endTimeHour, endTimerMinute));
                }
            }
        });

        return filters.toArray(new Filter[0]);
    }
}
