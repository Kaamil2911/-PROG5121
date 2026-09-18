package login;

import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("""
                           Welcome User!
                           You are required to Create an Account.
                           Please do the following:
                           """);

        System.out.println("Enter your first name:");
        String firstName = input.nextLine();

        System.out.println("Enter your last name:");
        String lastName = input.nextLine();

        System.out.println("Create a username:");
        String userName = input.nextLine();
        checkUserName(userName);

        System.out.println("Create a password:");
        String passWord = input.nextLine();
        checkPasswordComplexity(passWord);

        System.out.println("Enter a phone number:");
        String phoneNumber = input.nextLine();
        checkCellPhoneNumber(phoneNumber);

        // Process Registration
        String registrationStatus = registerUser(userName, passWord, phoneNumber);
        System.out.println("\n" + registrationStatus);

        // Only proceed to Login if registration succeeded
        if (registrationStatus.equals("The two above conditions have been met, and the user has been registered successfully.")) {
            System.out.println("\nPlease Login into your Account:");
            System.out.println("Enter your Username:");
            String loginName = input.nextLine();

            System.out.println("Enter your Password:");
            String loginPassword = input.nextLine();

            boolean isLoggedIn = loginUser(loginName, loginPassword, userName, passWord);
            System.out.println(returnLoginStatus(isLoggedIn, firstName, lastName));
        }
    }

    // Check username method for validation
    public static boolean checkUserName(String userName) {
        if (userName.contains("_") && userName.length() <= 5) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        }
    }

    // Check password for complexity validation
    public static boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";

        if (password.matches(regex)) {
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }
    }

    // Check cell phone number for validation
    public static boolean checkCellPhoneNumber(String phoneNumber) {
        // Regex for South african number
        String phoneRegex = "^\\+27[0-9]{9}$";

        if (phoneNumber.matches(phoneRegex)) {
            System.out.println("Cell phone number successfully added.");
            return true;
        } else {
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            return false;
        }
    }

    // Evaluates criteria and returns registration status
    public static String registerUser(String userName, String passWord, String phoneNumber) {
        if (!checkUserName(userName)) {
            return "The username is incorrectly formatted.";
        } else if (!checkPasswordComplexity(passWord)) {
            return "The password does not meet the complexity requirements.";
        } else {
            return "The two above conditions have been met, and the user has been registered successfully.";
        }
    }

    // Verifies entered credentials match stored credentials
    public static boolean loginUser(String loginUsername, String loginPassword, String storedUsername, String storedPassword) {
        return loginUsername.equals(storedUsername) && loginPassword.equals(storedPassword);
    }

    // Returns login outcome messaging
    public static String returnLoginStatus(boolean isLoggedIn, String firstName, String lastName) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " ," + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, Login failed, please try again.";
        }
    }
}
