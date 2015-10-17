/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.mohan.springweb.beans.controller;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Mohan Purushothaman
 */
@Controller
public class HomeContoller {
    
    @RequestMapping({"/","/home"})
    public String home(){
        return "home";
    }
    @RequestMapping("/test")
    @ResponseBody
    public Integer test(@RequestParam int a,@RequestParam int b){
        return a+b;
                
    }
    
    
    
}
