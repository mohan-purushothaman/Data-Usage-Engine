/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(Processor.class);

    @Autowired
    private Engine engine;

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public void process(final String line) throws Exception {
        executor.execute(
                new Runnable() {
                    private SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConfig.DATE_FORMAT);

                    private DataUsage createDataUsage(String usageString) throws Exception {
                        String[] split = usageString.split(BaseConfig.SPLIT_STRING);
                        return new DataUsage(split[0], split[1], split[2], Long.parseLong(split[3]), parseDate(split[4]), parseDate(split[5]));
                    }
                    private Date parseDate(String dateString) throws Exception {
                        return dateFormat.parse(dateString);
                    }

                    @Override
                    public void run() {
                        try {
                            logger.info(Thread.currentThread().getName()+" End.");
                            engine.process(createDataUsage(line));
                        } catch (Exception ex) {
                            logger.error("Exception", ex);
                        }
                    }
                }
        );
    }

    public void shutDownExecutor() {
        executor.shutdown();
        //executor.awaitTermination(2,TimeUnit.MINUTES);
    }
}
