/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.verizon.du.core.BaseConfig;
import org.verizon.du.core.DataUsage;

/**
 *
 * @author Administrator
 */
@Component
public class Processor {

    @Autowired
    private Engine engine;

    private ExecutorService executor = Executors.newFixedThreadPool(BaseConfig.THREAD_POOL_SIZE);

    public void process(final String line) throws Exception {
        executor.submit(new ProcessRunnable(line, engine));
    }
    
    
  
        
    

}

class ProcessRunnable implements Runnable {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Processor.class);

    private final String line;

    private final Engine engine;
    private static final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public ProcessRunnable(String line, Engine engine) {
        this.line = line;
        this.engine = engine;
    }

    @Override
    public void run() {
        try {
            DateFormat df = threadLocal.get();
            if (df == null) {
                df = new SimpleDateFormat(BaseConfig.DATE_FORMAT);
                threadLocal.set(df);

            }
            engine.process(createDataUsage(line, df));
        } catch (Exception ex) {
            logger.debug("Error occured", ex);
        }
    }

    private static DataUsage createDataUsage(String usageString, DateFormat dateFormat) throws Exception {
        String[] split = usageString.split(BaseConfig.SPLIT_STRING);
        return new DataUsage(split[0], split[1], split[2], Long.parseLong(split[3]), parseDate(split[4], dateFormat), parseDate(split[5], dateFormat));
    }

    private static Date parseDate(String dateString, DateFormat dateFormat) throws Exception {
        return dateFormat.parse(dateString);
    }
    
}
