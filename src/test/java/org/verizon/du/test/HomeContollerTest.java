/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.test;

import junit.framework.TestCase;
import org.verizon.du.beans.controller.HomeContoller;

/**
 *
 * @author Administrator
 */
public class HomeContollerTest extends TestCase {
    
    public HomeContollerTest(String testName) {
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
     * Test of home method, of class HomeContoller.
     */
    public void testHome() {
        System.out.println("home page testing");
        HomeContoller instance = new HomeContoller();
        String expResult = "home";
        String result = instance.home();
        assertEquals(expResult, result);

    }
    
}
