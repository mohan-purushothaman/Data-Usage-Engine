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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private Map<String,Customer>  customerMap=new HashMap<String,Customer>();
    
    public Customer findCustomer(String custId){
        Customer customer=customerMap.get(custId);
        if(customer ==null){
            new JdbcTemplate(dataSource).query("select * from CUST where custId"+custId, new RowMapper<Customer>() {

                @Override
                public Customer  mapRow(ResultSet rs, int i) throws SQLException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
        return customer;
    }
}
