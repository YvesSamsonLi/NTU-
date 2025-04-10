package com.example.cms.user_Login;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Camp.Camp;
import com.example.cms.Camp.campData;
import com.example.cms.Staff.Staff_Setup;
import com.example.cms.Student.Attendee;
import com.example.cms.Student.Committee;
import com.example.cms.Student.Student_Account;
import com.example.cms.Student.Student_User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Controller class to route to either student_account, staff_account or user_registration depending on user
 * Menu provided by this class is the first line of interaction between user and the program.
 */
public class account_Manager {
    private Scanner scanner;
    String userId = null;
    String staffId  = null;
    public static HashMap<Camp, ArrayList<String>> registeredAttendeeToCampNameMap = new HashMap<>();
    public static HashMap<Camp, ArrayList<String>> registeredCommitteeToCampNameMap = new HashMap<>();
    // Constructor to initialize the AccountManager with a Scanner
    public account_Manager(Scanner scanner) {
        this.scanner = scanner;
    }

 // Method to start the Account Manager application
    public void start() {
        System.out.println("Welcome to the Account Manager");

        // Get user type choice (student or staff)
        int userTypeChoice = getUserTypeChoice();
        user_Registration userRegistration = new user_Registration(scanner);

        // For Staff User
        if (userTypeChoice == 2) {
            // Print a header for Staff Login
            System.out.println("\n===== Staff Login =====");

            // Load necessary data from CSV files
            System.out.println("Loading data, please wait...");

            CSVDataManager.loadSuggestionFromCSV();
            CSVDataManager.loadEnquiryFromCSV();
            CSVDataManager.loadCampsFromCSV();
           // staffId = getStaffId();
           // System.out.println("This is the staff id entered: " + staffId);
            // Create Staff_Account instance and start staff account functionality
            CSVDataManager.loadStaffFromCSV();
            CSVDataManager.loadCommitteeFromCSV();
            CSVDataManager.loadAttendeeFromCSV();

            // Populate camp data with registered attendees and committee members
            System.out.println("Processing data...");

            if(!registeredAttendeeToCampNameMap.isEmpty()){
                for (Camp camp : campData.getCampList()) {
                    for (String string : registeredAttendeeToCampNameMap.get(camp)) {
                        Attendee registeredAttendee = Attendee.attendeeToNameMap.get(string);
                        camp.setAttendeesRegistered(registeredAttendee);
                    }
                }
            }

            if(!registeredCommitteeToCampNameMap.isEmpty()) {
                for (Camp camp : campData.getCampList()) {
                    for (String string : registeredCommitteeToCampNameMap.get(camp)) {
                        Committee registeredCommittee = Committee.committeeNameMap.get(string);
                        camp.setCommitteeRegistered(registeredCommittee);
                    }
                }
            }

                    // Start Staff Setup functionality
                    System.out.println("Data loaded successfully!\n");

                    Staff_Setup staff_Setup = new Staff_Setup();
                    staff_Setup.start();

                }
                // For Student User
        else if (userTypeChoice == 1) {

                    // Print a header for Student Login
                    System.out.println("\n===== Student Login =====");

                    // Load necessary data from CSV files
                    System.out.println("Loading data, please wait...");

                    CSVDataManager.loadEnquiryFromCSV();
                    CSVDataManager.loadSuggestionFromCSV();
                    CSVDataManager.loadCampsFromCSV();
                    CSVDataManager.loadCommitteeFromCSV();
                    CSVDataManager.loadAttendeeFromCSV();

                    // Populate camp data with registered attendees and committee members
                    System.out.println("Processing data...");


                    for (Camp camp : campData.getCampList()) {
                        for (String string : registeredAttendeeToCampNameMap.get(camp)) {
                            Attendee registeredAttendee = Attendee.attendeeToNameMap.get(string);
                            camp.setAttendeesRegistered(registeredAttendee);
                        }
                    }

                    for (Camp camp : campData.getCampList()) {
                        for (String string : registeredCommitteeToCampNameMap.get(camp)) {
                            Committee registeredCommittee = Committee.committeeNameMap.get(string);
                            camp.setCommitteeRegistered(registeredCommittee);
                        }
                    }

                            // Get account choice (existing student or new registration)

                            int accountChoice = getAccountChoice();

                            // Existing Student Account
                            if (accountChoice == 1) {
                                // Create Student_User instance and load student data from CSV
                                //System.out.println("Loading student data, please wait...");
                                Student_User studentUser = new Student_User();
                                CSVDataManager.loadStudentsFromCSV(studentUser);

                                boolean userFound = false;

                                // Loop until a valid student is found or user chooses to retry or register
                                while (!userFound) {
                                	System.out.println("Student Data Loaded Successfully");
                                	
                                    userId = getStudentId();
                                    System.out.println("This is the student id entered: " + userId);

                                    // Create Student_Account instance and check if the student exists
                                    Student_Account studentAccount = new Student_Account(userId, studentUser.getExistingStudents());

                                    // If student exists, start student account functionality
                                    if (studentAccount.getStudentAccount(userId)) {
                                        System.out.println("Welcome to the Student Account Page!");
                                        studentAccount.start();
                                        userFound = true;
                                    } else {
                                        System.out.println("Student not found.");

                                        int retryChoice;
                                        do {
                                            // Prompt user to retry or register
                                            retryChoice = getRetryChoice();

                                            switch (retryChoice) {
                                                case 1:
                                                    // Continue the loop to retry
                                                    break;
                                                case 2:
                                                    // Start user registration and exit loop
                                                    userRegistration.start();
                                                    userFound = true;
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice. Please enter 1 or 2.");
                                                    break;
                                            }
                                        } while (retryChoice != 1 && retryChoice != 2);
                                    }
                                }
                            }
                            // New Student Registration
                            else if (accountChoice == 0) {
                                userRegistration.start();
                            } else {
                                System.out.println("Invalid choice. Please enter '1' for Yes or '0' for No.");
                            }
                        } else{
                            System.out.println("Invalid user type. Please enter '1' for student or '2' for staff.");
                        }
                    }



 // Method to get user type choice (staff or student)
    private int getUserTypeChoice() {
        int userTypeChoice = 0;

        while (true) {
            try {
                // Prompt the user to enter '1' for student or '2' for staff
                System.out.print("Select your role:\n1. Student\n2. Staff\nEnter the corresponding number: ");

                // Read the input as a string and trim leading/trailing spaces
                String input = scanner.nextLine().trim();

                // Check if the input is empty
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter '1' or '2'.");
                    continue; // Restart the loop if the input is empty
                }

                // Parse the input as an integer
                userTypeChoice = Integer.parseInt(input);

                // Check if the input is within the valid range (1 for student, 2 for staff)
                if (userTypeChoice == 1 || userTypeChoice == 2) {
                    break; // Exit the loop only if the input is valid
                } else {
                    System.out.println("Invalid input. Please enter '1' or '2'.");
                }
            } catch (NumberFormatException e) {
                // Handle the exception if the input is not a valid integer
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Return the user type choice
        return userTypeChoice;
    }


 // Method to get and validate staff ID input
    private String getStaffId() {
        // Define a regex for special characters
        String specialCharacters = "[!@#$%^&*(),.?\":{}|<>]";
        String staffId = null;

        // Continue looping until a valid staff ID is entered
        while (true) {
            try {
                // Prompt the user to enter their staff ID
                System.out.print("Enter your staff ID: ");
                staffId = scanner.nextLine();

                // Check if the input is empty
                if (staffId.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid staff ID.");
                    continue; // Restart the loop if the input is empty
                }

                // Check if the input contains special characters
                if (staffId.matches(".*" + specialCharacters + ".*")) {
                    System.out.println("Invalid input. Please enter a staff ID without special characters.");
                    continue; // Restart the loop if the input contains special characters
                }

                // Exit the loop if the input is not empty and doesn't contain special characters
                break;
            } catch (InputMismatchException e) {
                // Handle the exception if the input is not of the expected type (String)
                System.out.println("Invalid input. Please enter a valid staff ID.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        // Return the valid staff ID
        return staffId;
    }


 // Method to get and validate user's choice for existing account
    private int getAccountChoice() {
        int accountChoice = 0;

        // Continue looping until a valid choice is entered
        while (true) {
            try {
                // Prompt the user to enter '1' for existing account or '0' for new account
                System.out.print("Do you have an existing account? (1 for Yes, 0 for No): ");
                String input = scanner.nextLine().trim(); // Read the input as a string and trim leading/trailing spaces

                // Check if the input is empty
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter '1' or '0'.");
                    continue; // Restart the loop if the input is empty
                }

                // Parse the input as an integer
                accountChoice = Integer.parseInt(input);

                // Check if the input is within the valid range (0 for No, 1 for Yes)
                if (accountChoice == 0 || accountChoice == 1) {
                    break; // Exit the loop only if the input is valid
                } else {
                    System.out.println("Invalid input. Please enter '1' or '0'.");
                }
            } catch (NumberFormatException e) {
                // Handle the exception if the input is not a valid integer
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Return the user's choice for existing account
        return accountChoice;
    }


 // Method to get and validate student ID input
    private String getStudentId() {
        String studentId = null;
        // Define a regex for special characters
        String specialCharacters = "[!@#$%^&*(),.?\":{}|<>]";

        // Continue looping until a valid student ID is entered
        while (true) {
            try {
                // Prompt the user to enter their student ID
                System.out.print("Enter your student ID: ");
                // Read the input as a string, convert to uppercase, and trim leading/trailing spaces
                studentId = scanner.nextLine().toUpperCase().trim();

                // Check if the input is empty
                if (studentId.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid student ID.");
                    continue; // Restart the loop if the input is empty
                }

                // Check if the input contains special characters
                if (studentId.matches(".*" + specialCharacters + ".*")) {
                    System.out.println("Invalid input. Please enter a student ID without special characters.");
                    continue; // Restart the loop if the input contains special characters
                }

                // Exit the loop if the input is not empty and doesn't contain special characters
                break;
            } catch (InputMismatchException e) {
                // Handle the exception if the input is not of the expected type (String)
                System.out.println("Invalid input. Please enter a valid student ID.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        // Return the valid student ID
        return studentId;
    }


 // Method to get and validate user's choice for retrying or going to student registration
    private int getRetryChoice() {
        int retryChoice = 0;

        // Continue looping until a valid choice is entered
        while (true) {
            try {
                // Display options for the user
                System.out.println("1. Try again");
                System.out.println("2. Go to student registration");
                System.out.print("Enter your choice: ");
                
                // Read the input as a string and trim leading/trailing spaces
                String input = scanner.nextLine().trim();

                // Check if the input is empty
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter '1' or '2'.");
                    continue; // Restart the loop if the input is empty
                }

                // Parse the input as an integer
                retryChoice = Integer.parseInt(input);

                // Check if the input is within the valid range (1 for Try again, 2 for Student Registration)
                if (retryChoice == 1 || retryChoice == 2) {
                    break; // Exit the loop only if the input is valid
                } else {
                    System.out.println("Invalid input. Please enter '1' or '2'.");
                }
            } catch (NumberFormatException e) {
                // Handle the exception if the input is not a valid integer
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Return the user's choice for retrying or going to student registration
        return retryChoice;
    }


    public static void main(String[] args) {
        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);

        // Create an instance of the AccountManager with the Scanner
        account_Manager accountManager = new account_Manager(scanner);

        // Start the Account Manager application
        accountManager.start();

        // Close the Scanner to release resources
        scanner.close();
    }

}
