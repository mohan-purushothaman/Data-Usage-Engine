/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.verizon.du.beans.impl.CustomerFactory;
import org.verizon.du.core.BaseConfig;
import org.verizon.du.beans.impl.Processor;

/**
 *
 * @author Akhila Kotcherlakota
 */
@Controller
public class FileUploadController {

    
    @Autowired
    private Processor processor;
    
    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);

    @Autowired
    private CustomerFactory custFactory;

    /**
     * Upload single file using Spring Controller
     *
     * @param name
     * @param file
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam MultipartFile file) throws Exception {
        logger.info("uploadFileHandler");
        long startTime = System.currentTimeMillis();
        if (!file.isEmpty()) {
            try {
                BufferedInputStream ipStream = new BufferedInputStream(file.getInputStream(), BaseConfig.STREAM_BUFFER_SIZE);
                BufferedReader ipReader = new BufferedReader(new InputStreamReader(ipStream), BaseConfig.BUFFER_SIZE);
                long lines = 0;
                while (ipReader.ready()) {
                    String line = ipReader.readLine();
                    processor.process(line);
                    lines++;
                }
                
               
                
                long updaterCustomerCount = custFactory.store();
                return ((System.currentTimeMillis() - startTime) + " ms taken to upload " + lines + " records]");

            } catch (Exception e) {
                logger.error("Exception", e);
                throw e;
            }
        } else {
            throw new Exception("File is empty");
        }
    }

    
}
