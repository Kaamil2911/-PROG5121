package TestMethod;

import login.Login;
import org.junit.Test; 
import static org.junit.Assert.*;

/**
 * @author kaami
 */
public class TestMethods {
    
    @Test
    public void testUserName() {
        String testInput = "kyl_1";
        boolean actual = Login.checkUserName(testInput);
        assertEquals("EXPECT CORRECT FORMAT", true, actual);
        
        
    }
    @Test
    public void testIncorrectUserName() {
        String testInput = "kyle!!!!!";
        boolean actual = Login.checkUserName(testInput);
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and no more than 5 characters in length", true, actual);
        
        
    }
    
    @Test
    public void testPassword() {
        String testInput = "Ch&&sec@ke99";
        boolean actual = Login.checkPasswordComplexity(testInput);
        assertEquals("EXPECT CORRECT FORMAT", true, actual);
        
        
    }
    @Test
    public void testIncorrectPassword() {
        String testInput = "password";
        boolean actual = Login.checkPasswordComplexity(testInput);
        assertEquals("Password is not correctly formatted; please ensure that the pasword contains at least eight chracters, a capital letter, a number and a special chracter.", true, actual);
        
        
    }

   
}