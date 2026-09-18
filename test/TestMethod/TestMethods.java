package TestMethod;

import login.Login;
import org.junit.Test; 
import static org.junit.Assert.*;

public class TestMethods {
    
    @Test
    public void testUserName() {
        String testInput = "kyl_1";
        boolean actual = Login.checkUserName(testInput);
        // Expecting true for valid username format
        assertEquals(true, actual);
    }

    @Test
    public void testIncorrectUserName() {
        String testInput = "kyle!!!!!";
        boolean actual = Login.checkUserName(testInput);
        // Expecting false for invalid username format
        assertEquals(false, actual);
    }
    
    @Test
    public void testPassword() {
        String testInput = "Ch&&sec@ke99!";
        boolean actual = Login.checkPasswordComplexity(testInput);
        // Expecting true for valid password format
        assertEquals(true, actual);
    }

    @Test
    public void testIncorrectPassword() {
        String testInput = "password";
        boolean actual = Login.checkPasswordComplexity(testInput);
        // Expecting false for invalid password format
        assertEquals(false, actual);
    }
    
    @Test
    public void testCellPhoneNumberCorrect() {
        String testInput = "+27838968976";
        boolean actual = Login.checkCellPhoneNumber(testInput);
        // Expecting true for a correctly formatted cell phone number
        assertEquals(true, actual);
    }
    
    @Test
    public void testCellPhoneNumberIncorrect() {
        String testInput = "08966553";
        boolean actual = Login.checkCellPhoneNumber(testInput);
        // Expecting false for an incorrectly formatted cell phone number
        assertEquals(false, actual);
    }

}
