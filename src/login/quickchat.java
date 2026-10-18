
package login;
//imported libraries needed
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class quickchat {
    //scanner to read user input
    private static  Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        
        boolean running = true;

        // Main application loop keeping the menu active until the user chooses to quit
        while (running) {
            System.out.println("\n QUICKCHAT MENU ");
            System.out.println("Press 1 : Send Messages");
            System.out.println("Press 2 : Show recently sent messages");
            System.out.println("Press 3) Quit");
            System.out.print("Choose an option (1-3): ");
            String option = input.nextLine().trim();

            // Handle user menu selection using a switch statement
            switch (option) {
                case "1":
                    //sendMessages(messageLimit);
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
}
