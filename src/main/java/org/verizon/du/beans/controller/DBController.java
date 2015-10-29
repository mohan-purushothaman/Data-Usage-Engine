/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

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
    
    
    @Autowired
    DataSource dataSource;
       
    
   @RequestMapping({"/executeQuery"})
   @ResponseBody
   public String executeQuery(@RequestParam("querytext") String query){
    
       new JdbcTemplate(dataSource).execute(query);
       return "success";
   }
    
    
    
}
