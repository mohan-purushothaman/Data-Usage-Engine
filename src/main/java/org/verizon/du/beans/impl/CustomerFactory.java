/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.verizon.du.core.BaseConfig;
import org.verizon.du.core.Customer;
import org.verizon.du.core.Usage;
import org.verizon.du.core.UsageType;

/**
 *
 * @author Administrator
 */
@Component
public class CustomerFactory {

    @Autowired
    DataSource dataSource;
    private final ConcurrentHashMap<String, Customer> customerMap = new ConcurrentHashMap<String, Customer>(1024);

    private static final Logger log = LoggerFactory.getLogger(CustomerFactory.class);

    public Customer findCustomer(final String custId) throws Exception {
        Customer customer = customerMap.get(custId);
        if (customer == null) {
            try {
                customer = new JdbcTemplate(dataSource).queryForObject("select * from CUST_DETAILS CD,USAGE_INFO UI where CD.CUSTID=UI.CUSTID AND CD.CUSTID=?", new RowMapper<Customer>() {

                    @Override
                    public Customer mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                        Map<UsageType, Usage[]> usage = new EnumMap<UsageType, Usage[]>(UsageType.class);

                        for (UsageType t : UsageType.values()) {
                            usage.put(t, loadColumns(t, rs));
                        }

                        Customer c = new Customer(custId, usage, Integer.parseInt(rs.getString("BILL_CYCLE")),
                                rs.getString("EMAIL"), rs.getLong("TN"), rs.getTimestamp("LAST_USAGE_UPDATE_TIME"));

                        return c;
                    }

                    public Usage[] loadColumns(UsageType type, ResultSet rs) throws SQLException {
                        Usage[] usage = new Usage[type.getSegmentSize()];
                        for (int i = 0; i < usage.length; i++) {
                            usage[i] = new Usage(rs.getLong(type.getDbColumnPrefix() + i));
                        }
                        return usage;
                    }

                }, custId);
                customerMap.put(custId, customer);
            } catch (Exception e) {
                log.error("error occured", e);
                throw new Exception("Customer with customerId " + custId + " not found");
            }
        }

        return customer;
    }

    private void addUpdateSection(Customer customer, StringBuilder sb) {
        Usage[] usage = customer.getHourUsage();
        for (int i = 0; i < usage.length; i++) {
            Usage u = usage[i];
            if (u.persistNeeded()) {
                sb.append(UsageType.HOUR.getDbColumn(i)).append('=').append(u.getNonpersistedUsage()).append(',');
            }
        }

        usage = customer.getDayUsage();
        for (int i = 0; i < usage.length; i++) {
            Usage u = usage[i];
            if (u.persistNeeded()) {
                sb.append(UsageType.DAY.getDbColumn(i)).append('=').append(u.getNonpersistedUsage()).append(',');
            }
        }

        sb.append(UsageType.MONTH.getDbColumn(0)).append('=').append(customer.getMonthUsage().getNonpersistedUsage());
    }

    public String handlePersistFailure(Exception e) {
        customerMap.clear();
        return "Internal error occured, No changes made to DB, resubmit after correction";
    }

    public long persist() throws Exception {
        StringBuilder updateBuilder = new StringBuilder(customerMap.size() * 10); // just wild guess and starting size for updateBuilder
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        for (Customer c : customerMap.values()) {
            addUpdateStatments(c, updateBuilder, df);
        }
        log.info(updateBuilder.toString());
        try (Connection c = dataSource.getConnection()) {
            //using native allowMultiQueries connection for optimization, need to write fallback
            c.setClientInfo("allowMultiQueries", "true");
            long updateCount = c.createStatement().executeUpdate(updateBuilder.toString());
            if (!c.getAutoCommit()) {
                c.commit();
            }

            for (Customer cust : customerMap.values()) {
                cust.setPersisted();
            }

            return updateCount;
        }

    }

    private void addUpdateStatments(Customer c, StringBuilder updateBuilder, DateFormat df) {
        if (c.isNeedToBill()) {
            updateBuilder.append("insert into MONTH_USAGE_HISTORY(CUSTID,MONTHLY_USAGE,BILLED_DATE) values('").append(c.getCustomerId()).append("','")
                    .append(c.getMonthUsage().getPersistedUsage()).append(",")
                    .append(df.format(c.getNextBilledDate().getTime())).append("');");
        }
        if (c.persistNeeded()) {
            updateBuilder.append("update USAGE_INFO set ");
            addUpdateSection(c, updateBuilder);
            updateBuilder.append(" where CUSTID='").append(c.getCustomerId()).append("';");
        }

    }

    public Collection<Customer> getLoadedCusomerList() {
        return customerMap.values();
    }
}
