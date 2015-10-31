/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

/**
 *
 * @author Administrator
 */
@Component
public class CustomerFactory {

    
    
    @Autowired
    DataSource dataSource;
    private Map<String, Customer> customerMap = new HashMap<String, Customer>();

    public Customer findCustomer(final String custId) {
        Customer customer = customerMap.get(custId);
        if (customer == null) {
            customer = new JdbcTemplate(dataSource).queryForObject("select * from CUST_DETAILS CD,USAGE_INFO UI where CD.CUSTID=UI.CUSTID AND CD.CUSTID=?", new RowMapper<Customer>() {

                @Override
                public Customer mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    Customer c = new Customer(custId, loadColumns("HR_", BaseConfig.HOUR_SEGMENTS, rs), loadColumns("DAY_", BaseConfig.DAY_SEGMENTS, rs), rs.getLong("MONTH_AGGR"),rs.getString("EMAIL"),rs.getInt("TN"));

                    return c;
                }

                public Usage[] loadColumns(String base, int arraySize, ResultSet rs) throws SQLException {
                    Usage[] usage=new Usage[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        usage[i]=new Usage(rs.getLong(base+i));
                    }
                    return usage;
                }

            }, custId);
            customerMap.put(custId, customer);
        }
        return customer;
    }

    
    
    
//this should be called when all threads are competed //assumption
    
    private final StringBuilder sb = new StringBuilder(500);

    private String getUpdateString(Customer customer) {

        sb.setLength(0);
        sb.append("MONTH_AGGR=").append(customer.getMonthUsage());

        
        Usage[] hourUsage=customer.getHourUsage();
        for (int i = 0; i < BaseConfig.HOUR_SEGMENTS; i++) {
            Usage u=hourUsage[i];
            if (u.isUsageChanged()) {
                sb.append(",HR_").append(i).append('=').append(u.getUsage());
            }
        }
        
        
        Usage[] dayUsage=customer.getDayUsage();
        for (int i = 0; i < BaseConfig.DAY_SEGMENTS; i++) {
            Usage u=dayUsage[i];
            if (u.isUsageChanged()) {
                sb.append(",DAY_").append(i).append('=').append(u.getUsage());
            }
        }

        

        return sb.toString();
    }

    public long store() {
        List<String> updateList=new ArrayList<String>();
        for (Customer c : customerMap.values()) {
            addUpdateStatments(c,updateList);
        }
        return customerMap.size();
    }
    
    public Collection<Customer> getLoadedCusomerList(){
        return customerMap.values();
    }

    private void addUpdateStatments(Customer c, List<String> updateList) {
         if (c.isPersistPending()) {
            updateList.add("update USAGE_INFO set " + getUpdateString(c) + " where CUSTID='"+ c.getCustomerId()+'\'');
            //bill cycle logic, and month usage archive
        }
    }
}
