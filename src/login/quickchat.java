package login;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class quickchat {
    
    private static final Scanner input = new Scanner(System.in);
    
    // Required Arrays 
    private static final List<Message> SENT_MESSAGES = new ArrayList<>();
    private static final List<Message> DISREGARDED_MESSAGES = new ArrayList<>();
    private static final List<Message> STORED_MESSAGES = new ArrayList<>();
    private static final List<String> MESSAGE_HASHES = new ArrayList<>();
    private static final List<String> MESSAGE_IDS = new ArrayList<>();
    
    private static int sentCount = 0;
    private static int processedCount = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to QuickChat.");

        boolean loginSuccess = Login.runLoginProcess(input);
        if (loginSuccess == false) {
            System.out.println("Login failed. You cannot access QuickChat.");
            return;
        }

        System.out.print("How many messages would you like to enter? ");
        int messageLimit = readPositiveInteger();
        boolean running = true;

        while (running == true) {
            System.out.println("\n QUICKCHAT MENU ");
            System.out.println("Press 1 : Send Messages");
            System.out.println("Press 2 : Show recently sent messages");
            System.out.println("Press 3 : Stored Messages");
            System.out.println("Press 4 : Quit");
            System.out.print("Choose an option (1-4): ");
            String option = input.nextLine();
            option = option.trim();

            if (option.equals("1")) {
                sendMessages(messageLimit);
            } else if (option.equals("2")) {
                showRecentlySentMessages();
            } else if (option.equals("3")) {
                loadStoredMessagesFromFile();
                runStoredMessagesMenu();
            } else if (option.equals("4")) {
                running = false;
                System.out.println("Thank you for using QuickChat.");
            } else {
                System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    private static int readPositiveInteger() {
        int validNumber = 0;
        boolean gotNumber = false;
        
        while (gotNumber == false) {
            String value = input.nextLine();
            value = value.trim();
            try {
                int number = Integer.parseInt(value);
                if (number > 0) {
                    validNumber = number;
                    gotNumber = true;
                } else {
                    System.out.print("Enter a number greater than zero: ");
                }
            } catch (NumberFormatException ex) {
                System.out.print("That is not a whole number. Try again: ");
            }
        }
        return validNumber;
    }

    private static void sendMessages(int messageLimit) {
        if (processedCount >= messageLimit) {
            System.out.println("You have reached the message limit you selected.");
            return;
        }
        int remaining = messageLimit - processedCount;
        System.out.println("You may send or process up to " + remaining + " more message(s).");

        for (int i = processedCount; i < messageLimit; i++) {
            int msgNum = i + 1;
            System.out.println("\n Message " + msgNum + " of " + messageLimit );
            
            System.out.print("Enter recipient cell number (max 10 chars, starting with +27): ");
            String recipient = input.nextLine();
            recipient = recipient.trim();

            Message draft = new Message(i, recipient, "placeholder");
            String recipientStatus = draft.checkRecipientCell();
            
            boolean isRecipientOk = recipientStatus.startsWith("Cell phone number successfully");
            if (isRecipientOk == false) {
                System.out.println(recipientStatus);
                i = i - 1;
                continue;
            }

            System.out.print("Enter your message (maximum 250 characters): ");
            String text = input.nextLine();
            
            Message message = new Message(i, recipient, text);
            String lengthStatus = message.checkMessageLength();
            System.out.println(lengthStatus);
            
            boolean isLengthOk = lengthStatus.equals("Message ready to send.");
            if (isLengthOk == false) {
                i = i - 1;
                continue;
            }

            System.out.println("\nChoose what to do with this message:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Your choice: ");
            String choice = input.nextLine();
            choice = choice.trim();

            if (choice.equals("1")) {
                SENT_MESSAGES.add(message);
                MESSAGE_HASHES.add(message.createMessageHash());
                MESSAGE_IDS.add(message.getMessageID());
                sentCount = sentCount + 1;
                processedCount = i + 1;
                System.out.println("Message successfully sent");
                System.out.println(message.printMessages());
            } else if (choice.equals("2")) {
                System.out.println("Press 0 to delete the message.");
                System.out.print("Enter 0 to confirm disregard: ");
                String confirm = input.nextLine();
                confirm = confirm.trim();
                
                if (confirm.equals("0")) {
                    DISREGARDED_MESSAGES.add(message);
                    System.out.println("Message disregarded.");
                    processedCount = i + 1;
                } else {
                    System.out.println("Disregard cancelled; message was not sent.");
                    i = i - 1;
                }
            } else if (choice.equals("3")) {
                try {
                    storeMessage(message);
                    STORED_MESSAGES.add(message);
                    MESSAGE_HASHES.add(message.createMessageHash());
                    MESSAGE_IDS.add(message.getMessageID());
                    System.out.println("Message successfully stored");
                    processedCount = i + 1;
                    System.out.println(message.printMessages());
                } catch (IOException ex) {
                    System.out.println("The message could not be stored: " + ex.getMessage());
                }
            } else {
                System.out.println("Invalid message option. This message was not sent.");
                i = i - 1;
            }
        }
    }

    private static void showRecentlySentMessages() {
        if (SENT_MESSAGES.isEmpty()) {
            System.out.println("No messages have been sent yet.");
            return;
        }
        System.out.println("\n Recently Sent Messages ");
        for (int i = 0; i < SENT_MESSAGES.size(); i++) {
            System.out.println(SENT_MESSAGES.get(i).printMessages());
            
        }
    }

    private static void storeMessage(Message message) throws IOException {
        Path file = Path.of("storedMessages.json");
        String existing = "[]";
        
        if (Files.exists(file) == true) {
            existing = Files.readString(file, StandardCharsets.UTF_8);
            existing = existing.trim();
        }
        
        String object = message.toJson();
        String updated = "";
        
        if (existing.equals("[]") || existing.equals("")) {
            updated = "[" + object + "]";
        } else {
            int lastIndex = existing.length() - 1;
            String sub = existing.substring(0, lastIndex);
            updated = sub + "," + object + "]";
        }
        
        Files.writeString(file, updated, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static void loadStoredMessagesFromFile() {
        STORED_MESSAGES.clear();
        Path file = Path.of("storedMessages.json");
        if (Files.exists(file) == false) {
            return;
        }
        try {
            String content = Files.readString(file, StandardCharsets.UTF_8).trim();
            if (content.equals("[]") || content.equals("")) {
                return;
            }
            if (content.startsWith("[")) {
                content = content.substring(1);
            }
            if (content.endsWith("]")) {
                content = content.substring(0, content.length() - 1);
            }
            
            String[] items = content.split("\\},\\s*\\{");
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                if (item.startsWith("{") == false) {
                    item = "{" + item;
                }
                if (item.endsWith("}") == false) {
                    item = item + "}";
                }
                
                String msgID = extractJsonField(item, "messageID");
                int msgNum = Integer.parseInt(extractJsonField(item, "messageNumber"));
                String recipient = extractJsonField(item, "recipient");
                String text = extractJsonField(item, "messageText");
                
                Message msg = new Message(msgID, msgNum, recipient, text);
                STORED_MESSAGES.add(msg);
                
                if (MESSAGE_IDS.contains(msgID) == false) {
                    MESSAGE_IDS.add(msgID);
                }
                String hash = msg.createMessageHash();
                if (MESSAGE_HASHES.contains(hash) == false) {
                    MESSAGE_HASHES.add(hash);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static String extractJsonField(String json, String field) {
        String searchKey = "\"" + field + "\":";
        int start = json.indexOf(searchKey);
        if (start == -1) return "";
        start = start + searchKey.length();
        
        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end).replace("\\\"", "\"");
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) {
                end = json.indexOf("}", start);
            }
            return json.substring(start, end).trim();
        }
    }

    // option four menu 
    private static void runStoredMessagesMenu() {
        boolean storedRunning = true;
        while (storedRunning == true) {
            System.out.println("STORED MESSAGES MENU ");
            System.out.println("1 : Display sender and recipient");
            System.out.println("2 : Display the longest stored message");
            System.out.println("3 : Search for a message ID and display recipient and message");
            System.out.println("4 : Search for all messages");
            System.out.println("5 : Delete a message");
            System.out.println("6 : Display a report ");
            System.out.println("7: Return to Main Menu");
            System.out.print("Choose an option (1-6): ");
            String choice = input.nextLine().trim().toLowerCase();

            if (choice.equals("1")) {
                displaySenderAndRecipient();
            } else if (choice.equals("2")) {
                displayLongestStoredMessage();
            } else if (choice.equals("3")) {
                searchByMessageID();
            } else if (choice.equals("4")) {
                searchByRecipient();
            } else if (choice.equals("5")) {
                deleteByMessageHash();
            } else if (choice.equals("6")) {
                displayFullReport();
            } else if (choice.equals("7")) {
                storedRunning = false;
            } else {
                System.out.println("Invalid option. Please enter a letter from a to g.");
            }
        }
    }

    // display sender and recipient details
    private static void displaySenderAndRecipient() {
        if (STORED_MESSAGES.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.println("\n Sender & Recipient ");
        for (int i = 0; i < STORED_MESSAGES.size(); i++) {
            Message m = STORED_MESSAGES.get(i);
            System.out.println("Sender: User | Recipient: " + m.getRecipient());
        }
    }

    // display longest stored nessage
    private static void displayLongestStoredMessage() {
        if (STORED_MESSAGES.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        Message longest = STORED_MESSAGES.get(0);
        for (int i = 0; i < STORED_MESSAGES.size(); i++) {
            Message m = STORED_MESSAGES.get(i);
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        System.out.println("Longest Stored Message");
        System.out.println(longest.printMessages());
        System.out.println("Length: " + longest.getMessageText().length() + " characters");
    }

    // search for messages
    private static void searchByMessageID() {
        if (STORED_MESSAGES.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.print("Enter Message ID to search: ");
        String searchID = input.nextLine().trim();
        
        boolean found = false;
        for (int i = 0; i < STORED_MESSAGES.size(); i++) {
            Message m = STORED_MESSAGES.get(i);
            if (m.getMessageID().equals(searchID)) {
                System.out.println("\nMessage Found:");
                System.out.println("Recipient: " + m.getRecipient());
                System.out.println("Message: " + m.getMessageText());
                found = true;
                break;
            }
        }
        if (found == false) {
            System.out.println("No message found with ID: " + searchID);
        }
    }

    // search by recipient
    private static void searchByRecipient() {
        if (STORED_MESSAGES.size() == 0) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.print("Enter recipient cell number to search: ");
        String targetRecipient = input.nextLine().trim();
        
        boolean found = false;
        System.out.println("\n Messages for " + targetRecipient + " ");
        for (int i = 0; i < STORED_MESSAGES.size(); i++) {
            Message m = STORED_MESSAGES.get(i);
            if (m.getRecipient().equalsIgnoreCase(targetRecipient)) {
                System.out.println(m.printMessages());
                
                found = true;
            }
        }
        if (found == false) {
            System.out.println("No messages found for this recipient.");
        }
    }

    // delete by message hash
    private static void deleteByMessageHash() {
        if (STORED_MESSAGES.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.print("Enter Message Hash to delete: ");
        String targetHash = input.nextLine().trim();
        
        Message toRemove = null;
        for (int i = 0; i < STORED_MESSAGES.size(); i++) {
            Message m = STORED_MESSAGES.get(i);
            if (m.createMessageHash().equalsIgnoreCase(targetHash)) {
                toRemove = m;
                break;
            }
        }
        
        if (toRemove != null) {
            STORED_MESSAGES.remove(toRemove);
            MESSAGE_HASHES.remove(targetHash);
            MESSAGE_IDS.remove(toRemove.getMessageID());
            
            try {
                Path file = Path.of("storedMessages.json");
                String sb = "[";
                for (int i = 0; i < STORED_MESSAGES.size(); i++) {
                    sb = sb + STORED_MESSAGES.get(i).toJson();
                    if (i < STORED_MESSAGES.size() - 1) {
                        sb = sb + ",";
                    }
                }
                sb = sb + "]";
                Files.writeString(file, sb, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Message deleted successfully using hash: " + targetHash);
            } catch (IOException e) {
                System.out.println("Error updating file: " + e.getMessage());
            }
        } else {
            System.out.println("No message found matching hash: " + targetHash);
        }
    }

    // display a report
    private static void displayFullReport() {
        if (STORED_MESSAGES.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        System.out.println("STORED MESSAGES REPORT");
        
        for (int i = 0; i < STORED_MESSAGES.size(); i++) {
            Message m = STORED_MESSAGES.get(i);
            System.out.println("Record " + (i + 1));
            System.out.println(m.printMessages());
            
        }
        System.out.println("Total Stored Messages: " + STORED_MESSAGES.size());
        
    }
}


