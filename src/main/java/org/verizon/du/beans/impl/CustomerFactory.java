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
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.verizon.du.core.Customer;

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
                    //do extraction logic;
                    
                    return null;
                }
            }, custId);
            customerMap.put(custId, customer);
        }
        return customer;
    }
}
