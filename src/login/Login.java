package login;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("""
                           Welcome User !
                           You are required to Create an Account
                           Please do the following:
                           """);
        
        

        System.out.print("Enter a username: ");
        String userName = input.nextLine();
        
        System.out.print("Enter a password: ");
        String passWord = input.nextLine();
        
        System.out.print("Enter a phone number: ");
        String phoneNumber = input.nextLine();
        boolean isvalid = checkUserName(userName);
        boolean passwordValid = checkPasswordComplexity(passWord);
        
        
        
        
        
    
    
    }
    public static boolean checkUserName(String userName){
         boolean isvalid = false;
        if (userName.contains("_") && userName.length()< 6){
               System.out.println("Username captured");
               isvalid = true;
               
           }
           else{
               System.out.println("not captured");}
        return isvalid;
        
    }
    
    public static boolean checkPasswordComplexity(String Password){
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
        boolean passwordValid = false;
        
        if (Password.matches(regex)) {
            System.out.println("Password successfully captured.");
            passwordValid = true; 
            
        } 
        else {
            
        System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
        return passwordValid;
    }
        
    }
