
package login;


public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    
    // constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateRandomID();
    }
    // use math library to generate a random ID numbers 
    private String generateRandomID() {
        long id = (long) (Math.random() * 900000000L) + 100000000L;
        String idString = String.valueOf(id);
        return idString;
    }

    // checkMessageID  method uses boolean to check if message ID is valid or not based on length
    public boolean checkMessageID() {
        boolean isValid = false;
        if (messageID != "null") {
            if (messageID.length() <= 10) {
                isValid = true;
            }
        }
        return isValid;
    }
}
