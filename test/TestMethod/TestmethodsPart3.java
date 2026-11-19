package TestMethod;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import login.Message;


public class TestmethodsPart3 {

    @Test
    public void testSentMessagesArrayPopulated() {
        // Test data messages 1 to 4; messages 1 and 4 have flag "Sent"
        List<Message> sentMessages = new ArrayList<>();
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        Message m4 = new Message(4, "0838884567", "It is dinner time !");

        sentMessages.add(m1);
        sentMessages.add(m4);

        assertEquals(2, sentMessages.size());
        assertEquals("Did you get the cake?", sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time !", sentMessages.get(1).getMessageText());
    }

    @Test
    public void testDisplayLongestMessage() {
        // Test data messages 1 to 4
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1, "+27834557896", "Did you get the cake?"));
        messages.add(new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time."));
        messages.add(new Message(3, "+27834484567", "Yohoooo, I am at your gate."));
        messages.add(new Message(4, "0838884567", "It is dinner time !"));

        Message longest = messages.get(0);
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getMessageText().length() > longest.getMessageText().length()) {
                longest = messages.get(i);
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessageText());
    }

    @Test
    public void testSearchByMessageID() {
        // Test data message 4
        Message m4 = new Message("0838884567", 4, "+27838884567", "It is dinner time !");
        assertEquals("It is dinner time !", m4.getMessageText());
    }

    @Test
    public void testSearchByRecipient() {
        // Messages 2 and 5 are for recipient +27838884567
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time."));
        messages.add(new Message(5, "+27838884567", "Ok, I am leaving without you."));

        List<String> foundMessages = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getRecipient().equals("+27838884567")) {
                foundMessages.add(messages.get(i).getMessageText());
            }
        }

        assertEquals(2, foundMessages.size());
        assertTrue(foundMessages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(foundMessages.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        // Test data message 2
        List<Message> storedMessages = new ArrayList<>();
        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        storedMessages.add(m2);

        String targetHash = m2.createMessageHash();
        boolean deleted = false;

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).createMessageHash().equals(targetHash)) {
                storedMessages.remove(i);
                deleted = true;
                break;
            }
        }

        assertTrue(deleted);
        assertEquals(0, storedMessages.size());
    }

    @Test
    public void testDisplayReport() {
        // Report includes Message Hash, Recipient, and Message
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        String report = m1.printMessages();

        assertTrue(report.contains(m1.createMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}
