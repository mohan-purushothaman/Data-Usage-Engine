/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
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

    public Customer findCustomer(String custId) {
        Customer customer = customerMap.get(custId);
        if (customer == null) {
            customer = new JdbcTemplate(dataSource).query("select * from CUST where custId=?", new ResultSetExtractor<Customer>() {

                @Override
                public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
                     Customer c=new Customer(rs.getString("CUSTID"), loadColumns("HR_",1,24,rs) , loadColumns("DAY_", 1, 31, rs), rs.getLong("MONTH_AGGR"));
                   
                    
                    return c;
                }
                
                public Map<Integer,Usage> loadColumns(String base,int start,int end,ResultSet rs) throws SQLException{
                    Map<Integer,Usage> usage=new HashMap<Integer, Usage>();
                    for (; start<end; start++) {
                        usage.put(start-1,new Usage(rs.getLong(base+start)));
                    }
                    return usage;
                }
                
                
            }, custId);
            customerMap.put(custId, customer);
        }
        return customer;
    }
    
    
    public void updateCustomer(Customer customer){
        new JdbcTemplate(dataSource).update("update USAGE_INFO set "+getUpdateString(customer)+" where cust_id=?'",customer.getCustomerId() );
    }

    StringBuilder sb=new StringBuilder(500);
    
    private String getUpdateString(Customer customer) {
        sb.setLength(0);
        sb.append(",MONTH_AGGR=").append(customer.getMonthUsage());
        
         for(Entry<Integer,Usage> usage:customer.getHourUsage().entrySet()){
            Usage u=usage.getValue();
            if(u.isUsageChanged()){
                sb.append(",HOUR_").append(usage.getKey()).append('=').append(u.getUsage());
            }
        }
         
        for(Entry<Integer,Usage> usage:customer.getDayUsage().entrySet()){
            Usage u=usage.getValue();
            if(u.isUsageChanged()){
                sb.append(",DAY_").append(usage.getKey()).append('=').append(u.getUsage());
            }
        }
        
       return sb.toString();
    }
}
