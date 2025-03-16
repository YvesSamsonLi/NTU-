package com.example.cms.Student;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Password.Password_Manager;
import com.example.cms.Staff.Staff;
import com.example.cms.Student_Role;

import java.util.Map;
import java.util.Scanner;
/**
 * This class deals more with logging in features, regardless of whether it is camp attendee or committee member.
 * As part of Open-Closed Principle, we close this class to modification,
 * but open it to extension to different student types,
 * in this case, Attendee_Account and Committee_Account
 */
public class Student_Account {
    protected String userId; // Add a field to hold the student ID
    private Scanner scanner;
    private Map<String, Student_User> existingStudents;
    

    public Student_Account(Map<String, Student_User> existingStudents) {
        this.existingStudents = existingStudents;
    }

    public boolean getStudentAccount(String userId) {
        // Use the existingStudents map to check if the student exists
        return existingStudents.containsKey(userId);
    }

    // Constructor to initialize the scanner
 // Constructor to initialize the scanner
    public Student_Account(String userId, Map<String, Student_User> existingStudents) {
        this.userId = userId;
        this.existingStudents = existingStudents;
        this.scanner = new Scanner(System.in); // Initialize the scanner here
    }

    public void start() {
        int maxAttempts = 3; // Maximum allowed password attempts
        int attempts = 0; // Counter for password attempts
        int roleChoice = 0; // Initialize to an invalid value
        Student_Role role = null; // Declare the role variable here
        
        Student_User existingStudent = existingStudents.get(userId);
        CSVDataManager.loadStudentsFromCSV(existingStudent);
        
   
        
        System.out.println("Welcome," + existingStudent.getName());
        role = existingStudent.getUserGroup();
        System.out.println("Role: ," + role);
        
        while (attempts < maxAttempts) {

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.next();

            // Consume the newline character
            scanner.nextLine();

            // Debugging information
           // System.out.println("Debugging - Before passwordManager.checkPassword");

            // Use the existingStudents map to retrieve the student information


            if (existingStudent != null) {
                Password_Manager passwordManager = new Password_Manager(scanner, existingStudent);

                // Debugging information
               // System.out.println("Debugging - Before calling passwordManager.checkPassword");

                // Check the password and get the student's name
                System.out.println("Your password is, " + enteredPassword);
                if (passwordManager.checkPassword(userId, role, enteredPassword)) {
                    //System.out.println("This is check password manager");
                    String studentName = existingStudent.getName();

                    if ("COMMITTEE".equals(role.name())) {
                    	System.out.println("Login Successful!");
                    	System.out.println("Welcome, Committee Member " + studentName + "!");
                        Committee_Account committee_Account = new Committee_Account(userId, existingStudent.getExistingStudents());
						committee_Account.start();
                        // Redirect to the committee class here
                    } else if ("ATTENDEE".equals(role.name())) {
                    	System.out.println("Login Successful!");
                    	System.out.println("Welcome, Attendee " + userId+ ","+studentName+"!");

                        // Redirect to the attendee class here
                        
                        Attendee_Account attendee_Account = new Attendee_Account(userId, existingStudent.getExistingStudents());
						attendee_Account.start();
                    } else {
                        System.out.println("Invalid role. Please enter 'committee' or 'attendee'.");
                    }

                    // Successful login, reset the attempts counter
                    attempts = 0;
                    break;
                } else {
                    attempts++;

                    if (attempts == maxAttempts) {
                        System.out.print("Forgot password (1 for Yes, 0 for No): ");
                        int forgotPasswordChoice = scanner.nextInt();

                        if (forgotPasswordChoice == 1) {
                            // Implement the logic for password reset here
                            passwordManager.forgotPassword(userId);
                        }

                        break;
                    }
                }
            } else {
                System.out.println("User not found. Please enter a valid user ID.");
                break;
            }
        }
    }
}
