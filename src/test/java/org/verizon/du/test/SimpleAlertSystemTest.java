/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.test;

import java.util.EnumMap;
import java.util.Map;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.verizon.du.beans.impl.SimpleAlertSystem;
import org.verizon.du.core.Alert;
import org.verizon.du.core.Customer;
import org.verizon.du.core.Usage;
import org.verizon.du.core.UsageType;

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
        Customer customer = new Customer("testCustomer", createUsageMap(112L * 1024 * 1024 * 1024),1, "a@b", 123,null);
        customer.findUsage(UsageType.MONTH,null).addUsage(1L * 1024 * 1024 * 1024);
        SimpleAlertSystem instance = new SimpleAlertSystem();
        Alert a = instance.processAlerts(customer);
        Assert.assertNotNull(a);
        int value = a.getAlertPercentage();
        assertEquals(value, 75);        
    }

    private Map<UsageType, Usage[]> createUsageMap(long monthlyUsage) {
        Map<UsageType, Usage[]> usage = new EnumMap<UsageType, Usage[]>(UsageType.class);

        for (UsageType t : UsageType.values()) {
            Usage[] u = new Usage[t.getSegmentSize()];
            for (int i = 0; i < u.length; i++) {
                u[i] = new Usage(0);
            }
            usage.put(t, u);
        }
        usage.put(UsageType.MONTH, new Usage[]{new Usage(monthlyUsage)});
        return usage;

    }

    public void testProcessAlertsInsideLimit() {
        System.out.println("processAlerts");
        Customer customer = new Customer("testCustomer", createUsageMap(1123400L),1, "a@b", 1234567890,null);
        customer.findUsage(UsageType.MONTH,null).addUsage(2230L);
        SimpleAlertSystem instance = new SimpleAlertSystem();
        Alert a = instance.processAlerts(customer);
        Assert.assertNull(a);
    }
    
    
    public void testBillCycleLogics(){
        try{
            new Customer("testCustomer", createUsageMap(1123400L),0, "a@b", 1234567890,null);
            fail("0 suppoted now ? rewrite testcase ");
        }catch(AssertionError e){
            //expected so ignore
        }
    }

    public void testProcessAlerts85() {
        System.out.println("processAlerts");
        Customer customer = new Customer("testCustomer", createUsageMap(126L * 1024 * 1024 * 1024), 1,"a@b", 1234567890,null);
        customer.findUsage(UsageType.MONTH,null).addUsage(2L * 1024 * 1024 * 1024);
        SimpleAlertSystem instance = new SimpleAlertSystem();
        Alert a = instance.processAlerts(customer);
        Assert.assertNotNull(a);
        int value = a.getAlertPercentage();
        assertEquals(value, 85);
        // TODO review the generated test code and remove the default call to fail.

    }
}
