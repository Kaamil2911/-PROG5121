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

        System.out.println("\nPlease Login into your Account:");
        System.out.println("Enter your Username:");
        String loginName = input.nextLine();

        System.out.println("Enter your Password:");
        String loginPassword = input.nextLine();
        // checks if the user entered correct details, if true, the user is regeistered, if false, they arent registered
        boolean isRegistered;
        if (loginName.equals(userName) && loginPassword.equals(passWord)) {
            System.out.println("Welcome " + firstName + " " + lastName + ", it is great to see you again.");
            isRegistered = true;
        } else {
            System.out.println("Username or password incorrect, please try again.");
            isRegistered = false;
        }

        System.out.println(registerUser(isRegistered));
    }

    // Check username method for validation
    public static boolean checkUserName(String userName) {
        if (userName.contains("_") && userName.length() < 6) {
            System.out.println("Username captured");
            return true;
        } else {
            System.out.println("not captured");
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
        String phoneRegex = "^\\+27[0-9]{8,9}$";

        if (phoneNumber.matches(phoneRegex) && phoneNumber.length() <= 12) {
            System.out.println("Cell phone number successfully added.");
            return true;
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        }
    }

    // Return status message based on registration
    public static String registerUser(boolean registered) {
        if (registered) {
            return "User registered successfully";
        } else {
            return "incorrect username/password, registration incomplete";
        }
    }
}
