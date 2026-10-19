package login;

// Imported libraries needed
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class quickchat {
    
    // Scanner to read user input
    private static Scanner input = new Scanner(System.in);
    private static final List<Message> SENT_MESSAGES = new ArrayList<>();
    private static int sentCount = 0;
    private static int processedCount = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to QuickChat.");

        // added user registration and login check
        boolean loginSuccess = Login.runLoginProcess(input);
        if (loginSuccess == false) {
            System.out.println("Login failed. You cannot access QuickChat.");
            return;
        }

        System.out.print("How many messages would you like to enter? ");
        int messageLimit = readPositiveInteger();
        boolean running = true;

        // Main application loop keeping the menu active until the user chooses to quit
        while (running == true) {
            System.out.println("\n QUICKCHAT MENU ");
            System.out.println("Press 1 : Send Messages");
            System.out.println("Press 2 : Show recently sent messages");
            System.out.println("Press 3) Quit");
            System.out.print("Choose an option (1-3): ");
            String option = input.nextLine();
            option = option.trim();

            // Handle user menu selection using a switch statement
            switch (option) {
                case "1":
                    sendMessages(messageLimit);
                    break;
                case "2":
                    // Feature still in development as per assignment
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    // Terminate the menu loop and exit application
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;
                default:
                    // Handle invalid menu inputs
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    // Method to safely read a whole number greater than zero from the user
    private static int readPositiveInteger() {
        int validNumber = 0;
        boolean gotNumber = false;
        
        // Loop continuously until the user enters a valid positive integer
        while (gotNumber == false) {
            String value = input.nextLine();
            value = value.trim();
            try {
                int number = Integer.parseInt(value);
                // Check if the number is greater than zero
                if (number > 0) {
                    validNumber = number;
                    gotNumber = true; // Exit the loop
                } else {
                    System.out.print("Enter a number greater than zero: ");
                }
            } catch (NumberFormatException ex) {
                // Handle cases where the user types letters or symbols instead of a number
                System.out.print("That is not a whole number. Try again: ");
            }
        }
        return validNumber;
    }

    // Method to handle sending, validating, and processing messages up to the user's limit
    private static void sendMessages(int messageLimit) {
        // Check if the user has already hit their maximum allowed messages
        if (processedCount >= messageLimit) {
            System.out.println("You have reached the message limit you selected.");
            return;
        }
        // Calculate and display how many message slots are still left to process
        int remaining = messageLimit - processedCount;
        System.out.println("You may send or process up to " + remaining + " more message(s).");

        // Loop through each remaining message slot based on the user's chosen limit
        for (int i = processedCount; i < messageLimit; i++) {
            int msgNum = i + 1;
            System.out.println("\n--- Message " + msgNum + " of " + messageLimit + " ---");
            
            // Prompt user to enter the recipient's phone number
            System.out.print("Enter recipient cell number (max 10 chars, starting with +27): ");
            String recipient = input.nextLine();
            recipient = recipient.trim();

            // Create a message to test the recipient cell format
            Message draft = new Message(i, recipient, "placeholder");
            String recipientStatus = draft.checkRecipientCell();
            
            // Check if the recipient cell validation passed
            boolean isRecipientOk = recipientStatus.startsWith("Cell phone number successfully");
            if (isRecipientOk == false) {
                System.out.println(recipientStatus);
                i = i - 1; // Decrement index 'i' so the user can try this same slot
                continue;  // Skip the rest of the loop and try again
            }

            // Prompt user to enter the body text of the message
            System.out.print("Enter your message (maximum 250 characters): ");
            String text = input.nextLine();
            
            // Create the official message object with the validated recipient and text
            Message message = new Message(i, recipient, text);
            String lengthStatus = message.checkMessageLength();
            System.out.println(lengthStatus);
            
            // Check if the message text length validation passed (<= 250 characters)
            boolean isLengthOk = lengthStatus.equals("Message ready to send.");
            if (isLengthOk == false) {
                i = i - 1; // Decrement index 'i' to let the user re-try this slot
                continue;  // Skip the rest of the loop and try again
            }

            // Present the menu options for what action to take with the validated message
            System.out.println("\nChoose what to do with this message:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Your choice: ");
            String choice = input.nextLine();
            choice = choice.trim();

            // Option 1: Send the message immediately and save 
            if (choice.equals("1") == true) {
                SENT_MESSAGES.add(message);
                sentCount = sentCount + 1;
                processedCount = i + 1;
                System.out.println("Message successfully sent");
                String printed = message.printMessages();
                System.out.println(printed);
            } 
            // Option 2: Disregard/delete the message (requires confirmation)
            else if (choice.equals("2") == true) {
                System.out.println("Press 0 to delete the message.");
                System.out.print("Enter 0 to confirm disregard: ");
                String confirm = input.nextLine();
                confirm = confirm.trim();
                
                // If confirmed with '0', discard it; otherwise, cancel and let them re-try
                if (confirm.equals("0") == true) {
                    System.out.println("Message disregarded.");
                    processedCount = i + 1;
                } else {
                    System.out.println("Disregard cancelled; message was not sent.");
                    i = i - 1;
                }
            } 
            // Option 3: Store the message into the local JSON file for later
            else if (choice.equals("3") == true) {
                try {
                   storeMessage(message);
                    System.out.println("Message successfully stored");
                    processedCount = i + 1;
                    String printedMsg = message.printMessages();
                    System.out.println(printedMsg);
                } catch (IOException ex) {
                    // Handle file writing errors 
                    String errMsg = ex.getMessage();
                    System.out.println("The message could not be stored: " + errMsg);
                }
            } 
            // Handle invalid menu selections by forcing a re-try on this slot
            else {
                System.out.println("Invalid message option. This message was not sent.");
                i = i - 1;
            }
    
    

        
    }
    }
    private static void storeMessage(Message message) throws IOException {
        Path file = Path.of("storedMessages.json");
        String existing = "[]";
        
        boolean fileExists = Files.exists(file);
        if (fileExists == true) {
            existing = Files.readString(file, StandardCharsets.UTF_8);
            existing = existing.trim();
        }
        String object = message.toJson();
        String updated = "";
        
        boolean isEmptyOrArray = existing.isEmpty() || existing.equals("[]");
        if (isEmptyOrArray == true) {
            updated = "[" + object + "]";
        } else {
            boolean endsWithBracket = existing.endsWith("]");
            if (endsWithBracket == true) {
                int lastIndex = existing.length() - 1;
                String sub = existing.substring(0, lastIndex);
                updated = sub + "," + object + "]";
            } else {
                throw new IOException("storedMessages.json is not a valid JSON array.");
            }
        }
        
        Files.writeString(file, updated, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
}


