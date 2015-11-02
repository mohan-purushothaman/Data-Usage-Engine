/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Mohan Purushothaman
 */
@Controller
public class DBController {

    private final Logger logger = LoggerFactory.getLogger(DBController.class);

    @Autowired
    private DataSource dataSource;

    @RequestMapping({"/executeQuery"})
    @ResponseBody
    public String executeQuery(@RequestParam("querytext") String query) throws SQLException {
        
       try(Connection c=dataSource.getConnection()){
           c.setAutoCommit(false);
           Statement st=c.createStatement();
        for (String s : query.split(";")) {
            st.addBatch(s);
        }
        st.executeBatch();
        c.commit();
       }
        return "Completed Successfully";
    }

}
