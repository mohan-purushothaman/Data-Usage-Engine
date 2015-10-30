/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.test;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.verizon.du.beans.impl.SimpleAlertSystem;
import org.verizon.du.core.Alert;
import org.verizon.du.core.Customer;

/**
 *
 * @author Administrator
 */
public class SimpleAlertSystemTest extends TestCase {
    
    public SimpleAlertSystemTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of processAlerts method, of class SimpleAlertSystem.
     */
    public void testProcessAlerts75() {
        System.out.println("processAlerts");
        Customer customer = new Customer("testCustomer", null, null, 112L*1024*1024*1024,"a@b",123);
        customer.addToMonthlyUsage(1L*1024*1024*1024);
        SimpleAlertSystem instance = new SimpleAlertSystem();
        Alert a  = instance.processAlerts(customer);
        Assert.assertNotNull(a);
        int value = a.getAlertPercentage();
        assertEquals(value,75);        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    public void testProcessAlertsInsideLimit() {
        System.out.println("processAlerts");
        Customer customer = new Customer("testCustomer", null, null, 1123400L,"a@b",1234567890);
        customer.addToMonthlyUsage(2230L);
        SimpleAlertSystem instance = new SimpleAlertSystem();
        Alert a  = instance.processAlerts(customer);
        Assert.assertNull(a);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    public void testProcessAlerts85() {
        System.out.println("processAlerts");
        Customer customer = new Customer("testCustomer", null, null, 128L*1024*1024*1024,"a@b",1234567890);
        customer.addToMonthlyUsage((long) 2*1024*1024*1024);
        SimpleAlertSystem instance = new SimpleAlertSystem();
        Alert a  = instance.processAlerts(customer);
        Assert.assertNotNull(a);
        int value = a.getAlertPercentage();
        assertEquals(value,85);
        // TODO review the generated test code and remove the default call to fail.
        
    }
}
