
package TestMethod;

import login.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestmethodsProg_Part2 {
    
    @Test
    public void testMessageLengthSuccess() {
        // Test message within the 250 character limit 
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // Test message exceeding 250 characters
        String longMessage = "A".repeat(251);
        Message msg = new Message(0, "+27718693002", longMessage);
        assertEquals("Please enter a message of less than 250 characters.", msg.checkMessageLength());
    }

    @Test
    public void testRecipientCellSuccess() {
        // Test valid recipient number meeting length (<= 10 characters) and starting with +27
        Message msg = new Message(0, "+271234567", "Test message");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        // Test invalid recipient number format 
        Message msg = new Message(0, "08575975889", "Hi Keegan, did you receive the payment?");
        String result = msg.checkRecipientCell();
        assertTrue(result.contains("incorrectly formatted or exceeds 10 characters"));
    }
    @Test
    public void testMessageIDCreation() {
        // Test that a valid Message ID is automatically generated and passes length checks
        Message msg = new Message(0, "+271234567", "Test message");
        assertNotNull(msg.getMessageID());
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testMessageHashCreation() {
        // Test message hash generation based on Message ID prefix, message number, and words
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.createMessageHash();
        
        assertNotNull(hash);
        // Verify structure format contains the message number and hash elements
        assertTrue(hash.contains(":0:"));
    }
    
}
