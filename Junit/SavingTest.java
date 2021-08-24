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
public class SavingTest extends TestCase {
    
    public SavingTest(String testName) {
        super(testName);
    }


    /**
     * Test of setAccountNumber method, of class Saving.
     */
    public void testSetAccountNumber_String() {
        Saving acc = new Saving();
        String expResult = "5100000002";
        String accNum = "510000000";
        acc.setPhoneNumber(accNum);
        String res = acc.getPhoneNumber() + "2";
        System.out.println(res);
        assertEquals(expResult, res );
    }

    /**
     * Test of getType method, of class Saving.
     */
    public void testGetType() {
        Saving acc = new Saving();
        String expResult = "Saving";
        String res = acc.getType();
        assertEquals(expResult, res);

        
    }
    
}
