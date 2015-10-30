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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author Administrator
 */
@Controller
public class CustomerController {
    
    private static final Logger logger = LoggerFactory
            .getLogger(CustomerController.class);
    
    @RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
    public @ResponseBody
    String createCustomer() throws Exception {
        logger.info("uploadFileHandler");
        return "addCustomer";
    }
}
