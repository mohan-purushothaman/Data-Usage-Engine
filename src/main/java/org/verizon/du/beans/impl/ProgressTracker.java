/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mohan Purushothaman
 */
@Component
public class ProgressTracker {

    private final LinkedHashMap<ThreadPoolExecutor, String> executionTracker = new LinkedHashMap<ThreadPoolExecutor, String>();

    private ThreadPoolExecutor latestExecutorService;
    private long startTime;

    public void registerExecution(ThreadPoolExecutor e) {
        executionTracker.put(e, null);
        latestExecutorService = e;
        startTime = System.currentTimeMillis();
    }

    public void markCompletion(ThreadPoolExecutor e) {
        executionTracker.put(e, "Completed in " + (System.currentTimeMillis() - startTime) + " ms , submitted at " + new Date(startTime));
    }

    public void markError(ThreadPoolExecutor e, String error) {
        executionTracker.put(e, "submitted at " + new Date(startTime) + " Error msg =" + error);
    }

    public String getCompletionMessage() {
        ThreadPoolExecutor e = latestExecutorService;

        if (e != null) {
            return executionTracker.get(e);
        }
        return null;
    }

    public Progress getRecentJobProgress() throws Exception {
        ThreadPoolExecutor e = latestExecutorService;
        if (e != null) {
            if (getCompletionMessage() == null) {
                long total = e.getTaskCount();
                long completed = e.getCompletedTaskCount();
                return new Progress(total, completed);
            }
            return null;
        }
        throw new Exception("No Job in progress");
    }

    public class Progress {

        private final long total;
        private final long completed;

        public Progress(long total, long completed) {
            this.total = total;
            this.completed = completed;
        }

        public long getTotal() {
            return total;
        }

        public long getCompleted() {
            return completed;
        }

    }
}
