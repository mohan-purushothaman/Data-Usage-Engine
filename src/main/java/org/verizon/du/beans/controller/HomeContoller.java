/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mohan Purushothaman
 */
@Controller
public class HomeContoller {
    
    private final Logger l=LoggerFactory.getLogger(getClass());
    
   @RequestMapping({"/home","/"})
   public String home(){
       return "home";
   }
    
   @RequestMapping({"/query"})
   public String query(){
       return "query";
   }
    
    
}
