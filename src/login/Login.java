package login;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        
        
        System.out.println("""
                           Welcome User !
                           You are required to Create an Account
                           Please do the following:
                           """);
        
        
        System.out.println("Enter your first name");
        firstName = input.nextLine();
        
        System.out.println("Enter your last name");
        lastName = input.nextLine();
        System.out.print("Create a username: ");
        String userName = input.nextLine();
        if (userName.contains("_") && userName.length()< 6){
               System.out.println("Username captured");
            }
           else{
               System.out.println("not captured");}
        
        
        
        System.out.print("Create a password: ");
        String passWord = input.nextLine();
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
        if (passWord.matches(regex)) {
            System.out.println("Password successfully captured.");
            } 
        else {
        System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
        
        System.out.print("Enter a phone number: ");
        String phoneNumber = input.nextLine();
        
        
        System.out.println("Please Login into your Account:");
        
        System.out.println("Enter your Username: ");
        
        String loginName = input.nextLine();
        
        System.out.println("Enter your Password: ");
        
        String loginPassword = input.nextLine();
        
        if (loginName.equals(userName) && loginPassword.equals(passWord)){
            System.out.println("Welcome " + firstName + " " + lastName + " it is great to see you again");
            
        }
        else{
                System.out.println("Username or password incorrect, please try again");
                
                }
    }
    // check username method for validation
    public static boolean checkUserName(String userName){
         boolean isvalid = false;
         // using && operator to check for an underscore and length of input
        if (userName.contains("_") && userName.length()< 6){
               System.out.println("Username captured");
               isvalid = true;
               
           }
           else{
               System.out.println("not captured");}
        return isvalid;
        
    }
    // check password for validation
    public static boolean checkPasswordComplexity(String Password){
        //used regex to check for capital letter, length and special character
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
    
    //public static boolean checkCellPhoneNumber(String phoneNumber){
        
        
    
        
    }
