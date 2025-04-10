package com.example.cms.Student;

import com.example.cms.user_Login.account_Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * When a Camp committee member logs in, they will first see the menu provided by this class.
 */
public class Committee_Account extends Student_Account {
	
	 private String studentID;
	 private Map<String, Committee> existingCommittee;

	    public Committee_Account(String studentID, Map<String, Student_User> existingStudent) {
	        super(studentID, existingStudent);
	        this.studentID = studentID;
	        
	        // Create a new map and copy elements from existingAttendees
	        this.existingCommittee = new HashMap<>();
	        for (Map.Entry<String, Student_User> entry : existingStudent.entrySet()) {
	            if (entry.getValue() instanceof Committee) {
	                this.existingCommittee.put(entry.getKey(), (Committee) entry.getValue());
	            }
	            // If you want to handle the case where an element is not an Attendee, add appropriate logic here
	        }
	    }

	    public void start() {
	    	Scanner scanner = new Scanner(System.in);
	        boolean exit = false;
	        Committee committee = Committee.committeeMap.get(studentID);

	        System.out.println("Welcome," + committee.getName());
	        
	        while (!exit) {
	        	int borderLength = 53;  // Adjust the border length based on your preference

	        	// Print the decorative border
	        	System.out.println("=".repeat(borderLength));

	        	// Print the STAFF header
	        	System.out.println("                        STUDENT - COMMITTEE                       ");

	        	// Print staff information
	        	System.out.println("               Name: " + committee.getName());
	        	System.out.println("               ID: " + committee.getStudentID());

	        	// Print the decorative border again
	        	System.out.println("=".repeat(borderLength));
	        	System.out.println("================STUDENT - COMMITEE HOME PAGE================");

	        	
	            // Display menu options for camp committee members
	            System.out.println("1. Manage Camps including generating reports");
	            System.out.println("2. Manage Suggestions");
	            System.out.println("3. Manage Enquiries");
	            System.out.println("4. Logout");

	            // Read user's choice
	            int choice = getUserChoiceWithExceptionHandling();

	            // Perform actions based on user's choice
	            switch (choice) {
	                case 1:
	                    Committee.manageCamp(committee);
	                    break;
	                case 2:
	                    Committee.manageSuggestions(committee);
	                    break;
	                case 3:
	                    Committee.manageEnquiries(committee);
	                    break;
	                case 4:
	                    exit = true;
	                    System.out.println("Logging out.");
	                    
	                    account_Manager account_Manager = new account_Manager(scanner);
	                    account_Manager.start();
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please select a valid option.");
	                    break;
	            }
	        }
	    }
	    
	 // Utility method to get the user's choice with exception handling
	    private static int getUserChoiceWithExceptionHandling() {
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            try {
	                System.out.print("Enter your choice: ");
	                String input = scanner.nextLine().trim();

	                if (input.isEmpty()) {
	                    System.out.println("Invalid input. Please enter a number.");
	                    continue;
	                }

	                return Integer.parseInt(input);
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a valid number.");
	            }
	        }
	    }
}
