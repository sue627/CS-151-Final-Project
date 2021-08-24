/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import junit.framework.TestCase;

/**
 *
 * @author Xiaoshu Xiao
 */
public class CheckingTest extends TestCase {
    
    public CheckingTest(String testName) {
        super(testName);
    }


    /**
     * Test of setAccountNumber method, of class Checking.
     */
    public void testSetAccountNumber_String() {
        Checking acc = new Checking();
        String expResult = "5100000001";
        String accNum = "510000000";
        acc.setPhoneNumber(accNum);
        String res = acc.getPhoneNumber() + "1";
        System.out.println(res);
        assertEquals(expResult, res );
    }

    /**
     * Test of getType method, of class Checking.
     */
    public void testGetType() {
        Checking acc = new Checking();
        String expResult = "Checking";
        String res = acc.getType();
        assertEquals(expResult, res);
    }
    
}
