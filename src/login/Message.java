
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
   // 2. checkRecipientCell() - checks if recipients number is valid
    
    public String checkRecipientCell() {
        boolean goodLength = recipient.length() <= 10;
        boolean hasPlus = recipient.startsWith("+27");
        
        // 3 checks to see if length is valid and has the correct symbols 
        if (goodLength == true) {
            if (hasPlus == true) {
                return "Cell phone number successfully captured.";
            }
        }
        
        return "The cell phone number is incorrectly formatted or exceeds 10 characters. Please ensure it contains an international code and is up to 10 characters.";
    }

    // 3. checkMessageLength() checks if the message is less than or = to 250 characters
    public String checkMessageLength() {
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        } else {
            return "Please enter a message of less than 250 characters.";
        }
    }

    // 4. createMessageHash() - if else to create message hash
    public String createMessageHash() {
        String prefix = "";
        if (messageID.length() >= 2) {
            prefix = messageID.substring(0, 2);
        } else {
            prefix = messageID;
        }
        
        String trimmedText = messageText.trim();
        String firstWord = "";
        String lastWord = "";
        
        if (trimmedText.equals("") == false) {
            String[] words = trimmedText.split(" ");
            firstWord = words[0];
            lastWord = words[words.length - 1];
        }
        
        String combined = firstWord + lastWord;
        String upperCombined = combined.toUpperCase();
        
        String hashResult = prefix + ":" + messageNumber + ":" + upperCombined;
        return hashResult;
    }
        //  printMessages()
    public String printMessages() {
        String output = "Message ID: " + messageID + "\n" +
                        "Message Hash: " + createMessageHash() + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText;
        return output;
    }

    // JSON export
    public String toJson() {
        String escapedText = messageText.replace("\"", "\\\"");
        String jsonString = "{" +
                "\"messageID\":\"" + messageID + "\"," +
                "\"messageNumber\":" + messageNumber + "," +
                "\"recipient\":\"" + recipient + "\"," +
                "\"messageText\":\"" + escapedText + "\"," +
                "\"messageHash\":\"" + createMessageHash() + "\"" +
                "}";
        return jsonString;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageText() {
        return messageText;
    }
}

