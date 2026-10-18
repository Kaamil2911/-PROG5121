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

        
    }
}
