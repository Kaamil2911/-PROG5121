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

    // These are the assert true/false unit tests according to assignment criteria
    @Test
    public void testLoginSuccessful() {
        // Verifies login successful returns True
        boolean actual = Login.loginUser("kyl_1", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!");
        assertTrue(actual);
    }

    @Test
    public void testLoginFailed() {
        // Verifies login failed returns False
        boolean actual = Login.loginUser("kyl_1", "wrongPassword", "kyl_1", "Ch&&sec@ke99!");
        assertFalse(actual);
    }

    @Test
    public void testUsernameCorrectlyFormattedAssertTrue() {
        // Verifies username correctly formatted returns True
        boolean actual = Login.checkUserName("kyl_1");
        assertTrue(actual);
    }

    @Test
    public void testUsernameIncorrectlyFormattedAssertFalse() {
        // Verifies username incorrectly formatted returns False
        boolean actual = Login.checkUserName("kyle!!!!!");
        assertFalse(actual);
    }

    @Test
    public void testPasswordRequirementsAssertTrue() {
        // Verifies password meets complexity requirements returns True
        boolean actual = Login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(actual);
    }

    @Test
    public void testPasswordNotRequirementsAssertFalse() {
        // Verifies password does not meet complexity requirements returns False
        boolean actual = Login.checkPasswordComplexity("password");
        assertFalse(actual);
    }
    @Test
    public void testCellPhoneNumberCorrectlyFormattedAssertTrue() {
        // Verifies cell phone number correctly formatted returns True
        boolean actual = Login.checkCellPhoneNumber("+27838968976");
        assertTrue(actual);
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormattedAssertFalse() {
        // Verifies cell phone number incorrectly formatted returns False
        boolean actual = Login.checkCellPhoneNumber("08966553");
        assertFalse(actual);
    }
}
