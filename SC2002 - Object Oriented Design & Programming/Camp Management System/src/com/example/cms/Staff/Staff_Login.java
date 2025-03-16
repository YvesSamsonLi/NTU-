package com.example.cms.Staff;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Password.Password_Manager_Staff;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Functionality for staff log in
 */
public class Staff_Login {
	Scanner scanner = new Scanner(System.in);

	public void start(String staffID) {
	    // Print header for staff information
	    System.out.println("=================STAFF=================");

	    // Load existing staff data from CSV
	    CSVDataManager.loadStaffFromCSV();

	    // Retrieve staff information based on staffID
	    Staff staff = Staff.existingStaff.get(staffID);

	    // Initialize login attempts variables
	    int maxAttempts = 3;
	    int attempts = 0;

	    // Continue login attempts until maxAttempts is reached
	    while (attempts < maxAttempts) {
	        try {
	            // Prompt for password
	            System.out.print("Enter your password: ");
	            String enteredPassword = scanner.next();

	            // Consume the newline character
	            scanner.nextLine();

	            // Validate entered password
	            if (enteredPassword.isEmpty()) {
	                throw new InputMismatchException("Password cannot be empty.");
	            }

	            // Retrieve existing staff member
	            com.example.cms.Staff.Staff_User existingStaffMember = com.example.cms.Staff.Staff_User.getExistingStaff().get(staffID);

	            // Check if staff member exists
	            if (existingStaffMember != null) {
	                // Initialize password manager
	                Password_Manager_Staff passwordManager = new Password_Manager_Staff(scanner, existingStaffMember);

	                // Check the password
	                System.out.println("Your password is, " + enteredPassword);
	                if (passwordManager.checkPassword(staffID, enteredPassword)) {
	                    // Successful login
	                	String welcomeMessage = "=== Successful login. Welcome ===";
	                	int borderLength = welcomeMessage.length() + 20;  // Adjust the border length based on your preference
	                	System.out.println(borderLength);
	                	// Print the decorative border
	                	System.out.println("=".repeat(borderLength));

	                	// Print the welcome message
	                	System.out.println(welcomeMessage);

	                	// Print the decorative border again
	                	System.out.println("=".repeat(borderLength));
	                	
	                	// Add new lines for the appearance of entering a new page
	                	System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed
	                	
	                    // Create and start staff account
	                    com.example.cms.Staff.Staff_Account staff_Account = new com.example.cms.Staff.Staff_Account();
	                    staff_Account.start(staff);

	                    // Reset the attempts counter
	                    attempts = 0;
	                    break;
	                } else {
	                    // Incorrect password, increment attempts
	                    attempts++;

	                    if (attempts == maxAttempts) {
	                        // Max attempts reached, prompt for password reset
	                        System.out.print("Forgot password (1 for Yes, 0 for No): ");
	                        int forgotPasswordChoice = scanner.nextInt();

	                        if (forgotPasswordChoice == 1) {
	                            // Implement the logic for password reset here
	                            passwordManager.forgotPassword(staffID);
	                        }

	                        break;
	                    }
	                }
	            } else {
	                // Staff member not found
	                System.out.println("Staff member not found. Please enter a valid staff ID.");
	                break;
	            }
	        } catch (InputMismatchException e) {
	            // Invalid input exception
	            System.out.println("Invalid input. " + e.getMessage());
	            // Consume the invalid input
	            scanner.nextLine();
	        } catch (NoSuchElementException e) {
	            // Invalid input exception
	            System.out.println("Invalid input. Please enter a password.");
	            // Consume the invalid input
	            scanner.nextLine();
	        }
	    }
	}
}
