/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
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
    private final Map<String, Customer> customerMap = new HashMap<String, Customer>();

    public Customer findCustomer(final String custId) {
        Customer customer = customerMap.get(custId);
        if (customer == null) {
            customer = new JdbcTemplate(dataSource).queryForObject("select * from CUST_DETAILS CD,USAGE_INFO UI where CD.CUSTID=UI.CUSTID AND CD.CUSTID=?", new RowMapper<Customer>() {

                @Override
                public Customer mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    Map<UsageType, Usage[]> usage = new EnumMap<UsageType, Usage[]>(UsageType.class);

                    for (UsageType t : UsageType.values()) {
                        usage.put(t, loadColumns(t, rs));
                    }

                    Customer c = new Customer(custId, usage, rs.getString("EMAIL"), rs.getInt("TN"));

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
            synchronized (customerMap) {
                customerMap.put(custId, customer);
            }
        }
        return customer;
    }

    private String addUpdateSection(Customer customer, StringBuilder sb) {

        for (Usage u : customer.getHourUsage()) {
            sb.append(UsageType.HOUR.getDbColumnPrefix()).append('=').append(u.getNonpersistedUsage()).append(',');
        }

        for (Usage u : customer.getHourUsage()) {
            sb.append(UsageType.HOUR.getDbColumnPrefix()).append('=').append(u.getNonpersistedUsage()).append(',');
        }

        sb.append(UsageType.MONTH.getDbColumnPrefix()).append('=').append(customer.getMonthUsage().getNonpersistedUsage());
        return sb.toString();
    }

    public long store() throws SQLException {
        StringBuilder updateBuilder = new StringBuilder(customerMap.size() * 10); // just wild guess and starting size for updateBuilder
        for (Customer c : customerMap.values()) {
            addUpdateStatments(c, updateBuilder);
        }

        try (Connection c = dataSource.getConnection()) {
            //using native allowMultiQueries connection for optimization, need to write fallback
            c.setClientInfo("allowMultiQueries", "true");
            c.createStatement().executeUpdate(updateBuilder.toString());
            if (!c.getAutoCommit()) {
                c.commit();
            }
        }
        return customerMap.size();
    }

    public Collection<Customer> getLoadedCusomerList() {
        return customerMap.values();
    }

    private void addUpdateStatments(Customer c, StringBuilder updateBuilder) {
        if (c.getMonthUsage().persistNeeded()) {
            updateBuilder.append("update USAGE_INFO set ");
            addUpdateSection(c, updateBuilder);
            updateBuilder.append(" where CUSTID='").append(c.getCustomerId()).append("';");
            //bill cycle logic, and month usage archive
        }
    }
}
