package login;

public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    
    // Constructor for new messages
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateRandomID();
    }

    // Constructor for loading messages from the JSON file
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }
    
    // Generates a random ID using Math.random[cite: 1]
    private String generateRandomID() {
        long id = (long) (Math.random() * 900000000L) + 100000000L;
        String idString = String.valueOf(id);
        return idString;
    }

    // Checks if message ID is valid based on length[cite: 1]
    public boolean checkMessageID() {
        boolean isValid = false;
        if (messageID != null) {
            if (messageID.length() <= 10) {
                isValid = true;
            }
        }
        return isValid;
    }

    // Checks if recipient number is valid[cite: 1]
    public String checkRecipientCell() {
        boolean goodLength = recipient.length() <= 12;
        boolean hasPlus = recipient.startsWith("+27");
        
        if (goodLength == true) {
            if (hasPlus == true) {
                return "Cell phone number successfully captured.";
            }
        }
        
        return "The cell phone number is incorrectly formatted or exceeds 10 characters. Please ensure it contains an international code and is up to 10 characters.";
    }

    // Checks if message is <= 250 characters[cite: 1]
    public String checkMessageLength() {
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        } else {
            return "Please enter a message of less than 250 characters.";
        }
    }

    // Creates the message hash[cite: 1]
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

    // Prints message details[cite: 1]
    public String printMessages() {
        String output = "Message ID: " + messageID + "\n" +
                        "Message Hash: " + createMessageHash() + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText;
        return output;
    }

    // Converts message to JSON format[cite: 1]
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

    public int getMessageNumber() {
        return messageNumber;
    }
    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
}

