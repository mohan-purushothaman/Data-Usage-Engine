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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.verizon.du.core.BaseConfig;
 
/**
 *
 * @author Akhila Kotcherlakota
 */
@Controller
public class FileUploadController {
 
    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);
 
    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    void uploadFileHandler(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws Exception {
 
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedInputStream ipStream=new BufferedInputStream(file.getInputStream(),BaseConfig.STREAM_BUFFER_SIZE);
                BufferedReader ipReader = new BufferedReader(new InputStreamReader(ipStream),BaseConfig.BUFFER_SIZE);
                String line = ipReader.readLine();
                System.out.println(line);
              
 
            } catch (Exception e) {
                throw new Exception("Exception occured while reading file"+e);
            }
        } else {
            throw new Exception("File is empty");
        }
    }
     
}