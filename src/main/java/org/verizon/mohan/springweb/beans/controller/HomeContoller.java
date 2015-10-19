/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.mohan.springweb.beans.controller;

import java.util.Date;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 *
 * @author Mohan Purushothaman
 */
@Controller
public class HomeContoller {
    
    private final Logger l=LoggerFactory.getLogger(getClass());
    
    @Autowired
    DataSource ds;
    
    @RequestMapping({"/","/home"})
    public String home(){
        new JdbcTemplate(ds).update("insert into log(msg,time) values(?,?)", "sample msg",new Date());
        return "home";
    }
    @RequestMapping("/test")
    @ResponseBody
    public Map test(@RequestParam int a,@RequestParam int b){
        Map<String,String> m = new HashMap<String,String>();
/*        l.debug("adding {} and {}",a,b);
        l.error("error - adding {} and {}",a,b);
*/
m.put("test123","1729");
m.put("test1234","1829");

return m;
                
    }
    
    
    
}
