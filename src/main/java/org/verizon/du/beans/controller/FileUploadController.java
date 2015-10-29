/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.verizon.du.core.BaseConfig;
import org.verizon.du.core.DataUsage;
import org.verizon.du.beans.impl.Engine;
 
/**
 *
 * @author Akhila Kotcherlakota
 */
@Controller
public class FileUploadController {
   @Autowired
    Engine engine;
    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);
 
    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    void uploadFileHandler(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws Exception {
          logger.info("uploadFileHandler");
            long startTime=System.currentTimeMillis();
        if (!file.isEmpty()) {
            try {
                BufferedInputStream ipStream=new BufferedInputStream(file.getInputStream(),BaseConfig.STREAM_BUFFER_SIZE);
                BufferedReader ipReader = new BufferedReader(new InputStreamReader(ipStream),BaseConfig.BUFFER_SIZE);
                while (ipReader.ready()){
                    String line=ipReader.readLine();
                 engine.process(createDataUsage(line));
                 }
                logger.info("Time taken----"+(System.currentTimeMillis()-startTime)+"ms");
 
            } catch (Exception e) {
                logger.error("Exception", e);
                throw e;
            }
        } else {
            throw new Exception("File is empty");
        }
    }
     private DataUsage createDataUsage(String usageString) throws Exception {
        String [] split=usageString.split(BaseConfig.SPLIT_STRING);
        return new DataUsage(split[0], split[1], split[2], Long.parseLong(split[3]), parseDate(split[4]), parseDate(split[5]));
    }
     SimpleDateFormat dff=new SimpleDateFormat(BaseConfig.DATE_FORMAT);
      private Date parseDate(String dateString) throws Exception{
        return dff.parse(dateString);
    }
}