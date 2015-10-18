/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.mohan.springweb.test;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.verizon.mohan.springweb.beans.controller.HomeContoller;

/**
 *
 * @author Mohan Purushothaman
 */
public class AddTest extends TestCase {

    HomeContoller c = new HomeContoller();

    public AddTest(String testName) {
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

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    public void testAdd() {
        Assert.assertEquals(132,(int) c.test(11, 121));
    }

}
