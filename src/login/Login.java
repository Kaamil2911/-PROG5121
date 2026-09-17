
package login;

// Scanner library for user input
import java.util.Scanner;

public class Login {

     static void main(String[] args) {
        
        // object made to scan user input
        Scanner input = new Scanner(System.in);
        
        String userName = "";
        String passWord = "";
        String phoneNumber = "";
        
        System.out.println("""
                           Welcome User !
                           You are required to Create an Account
                           Please do the following:
                           """);
        System.out.println("Enter a username: ");
        userName = input.nextLine();
        
        System.out.println("Enter a password: ");
        passWord = input.nextLine();
        
        System.out.println("Enter a phone number: ");
        phoneNumber = input.nextLine();
        
        
    
    }
    
}