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
public class CreditTest extends TestCase {
    
    public CreditTest(String testName) {
        super(testName);
    }


    /**
     * Test of setAccountNumber method, of class Credit.
     */
    public void testSetAccountNumber_String() {
        Credit acc = new Credit();
        String expResult = "5100000003";
        String accNum = "510000000";
        acc.setPhoneNumber(accNum);
        String res = acc.getPhoneNumber() + "3";
        System.out.println(res);
        assertEquals(expResult, res );
    }

    /**
     * Test of getType method, of class Credit.
     */
    public void testGetType() {
        Credit acc = new Credit();
        String expResult = "Credit";
        String res = acc.getType();
        assertEquals(expResult, res);
    }
    
}
