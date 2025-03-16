package com.example.cms.Password;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Staff.Staff;
import com.example.cms.Staff.Staff_User;
import com.example.cms.Student.Attendee;
import com.example.cms.Student.Student_User;
import com.example.cms.Student_Role;
import com.example.cms.user_Login.account_Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import com.example.cms.Student_Role;
import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Student.Attendee;
import com.example.cms.Student.Committee;
import com.example.cms.Student.Student_User;
import com.example.cms.user_Login.account_Manager;

/**
 * Controller class to handle passwords for logging in
 * Includes all the standard features such as forgot password, reset password, etc.
 */
public class Password_Manager_Staff {

	 private Scanner scanner; // Add a Scanner field
	 private Staff_User staff_User; // Add a field to hold the Student_User instance
	 private Map<String, Staff_User> existingStaff;
	    // Constructor to initialize the Scanner
	    public Password_Manager_Staff(Scanner scanner, Staff_User staffUser) {
	        this.scanner = scanner;
	        this.staff_User = staff_User;
	    }



	    public boolean checkPassword(String staffID, String enteredPassword) {
	        // Retrieve the staff based on staffID
	        Staff_User staffMember = Staff_User.getExistingStaff().get(staffID);

	        // Check if the staff exists
	        if (staffMember != null) {
	            // Retrieve stored password and salt
	            String storedPassword = staffMember.getPassword();
	            String storedSalt = staffMember.getSalt();

	            // Check if the entered password matches the stored hashed password
	            String enteredPasswordHashed = Password_Hasher.hashPassword(enteredPassword, storedSalt);

	            return enteredPasswordHashed != null && enteredPasswordHashed.equals(storedPassword);
	        }

	        return false;
	    }

	    
	    public boolean updatePassword(String userID, String updatedPassword) {
	        // Find the student based on the userID (you need to implement this part)
	        this.existingStaff= new HashMap<>();
	        CSVDataManager.loadStaffFromCSV();
	        
	        Staff_User staff = Staff_User.getExistingStaff().get(userID);
            
	        if (staff != null) {
	            String oldPasswordHash = staff.getPassword();
	            String oldSalt = staff.getSalt();

	            // Check if the updatedPassword is different from the old password
	            if (!updatedPassword.equals(oldPasswordHash)) {
	                // Check if the updatedPassword meets criteria (8 characters, alphanumeric, and mixed case)
	                if (isValidPassword(updatedPassword)) {
	                    // Generate a new salt
	                    String newSalt = Password_Hasher.generateSalt();

	                    // Hash the updated password with the new salt
	                    String newPasswordHash = Password_Hasher.hashPassword(updatedPassword, newSalt);

	                    // Update the passwordHash and salt in the Staff_User instance
	                    staff.setPassword(newPasswordHash);
	                    staff.setSalt(newSalt);


	                    // Update the CSV file for staff
	                    CSVDataManager.updateStaffCSVFile(staff);

	                    System.out.println("Password reset successful.");
	                    // Return true to indicate success
	                    return true;
	                } else {
	                    System.out.println("Password does not meet the criteria. Please make sure it has 8 characters, includes both upper and lower case letters, and is alphanumeric.");
	                    // Return false to indicate that the password doesn't meet criteria
	                    return false;
	                }
	            } else {
	                System.out.println("New password is the same as the old one. Please choose a different password.");
	                // Return false to indicate that the new password is the same as the old one
	                return false;
	            }
	        } else {
	            System.out.println("User not found. Password reset failed.");
	            // Return false to indicate failure (user not found)
	            return false;
	        }
	    }



    public void forgotPassword(String userID) {
    	
        // First, retrieve the staff based on the userID
    	CSVDataManager.loadStaffFromCSV();
    	
    
        Staff_User staff = Staff_User.getExistingStaff().get(userID);
        

        if (staff != null) {
            System.out.println("Password recovery for " + staff.getName());

            // Get security questions and answers from the staff
            ArrayList<String> securityQuestions = staff.getSecurityQuestion();
            ArrayList<String> securityAnswers = staff.getSecurityAnswers();

            // Maximum wrong attempts allowed per question
            int maxWrongAttempts = 2;
            int correct = 0;

            // Consume the newline character
            scanner.nextLine();

            for (int i = 0; i < securityQuestions.size(); i++) {
                String securityQuestion = securityQuestions.get(i);
                System.out.println("Security Question: " + securityQuestion);

                String userAnswer;
                do {
                    System.out.print("Enter your answer: ");
                    userAnswer = scanner.nextLine().toUpperCase().trim();

                    if (userAnswer.isEmpty()) {
                        System.out.println("Invalid input. Please enter a non-empty value.");
                    }
                } while (userAnswer.isEmpty());

                int wrongAttempts = 0;

                // Allow a limited number of wrong attempts
                while (!userAnswer.equals(securityAnswers.get(i)) && wrongAttempts < maxWrongAttempts) {
                    wrongAttempts++;
                    System.out.println("Wrong answer. Attempts left: " + (maxWrongAttempts - wrongAttempts));

                    do {
                        System.out.print("Try again: ");
                        userAnswer = scanner.nextLine().toUpperCase().trim();

                        if (userAnswer.isEmpty()) {
                            System.out.println("Invalid input. Please enter a non-empty value.");
                        }
                    } while (userAnswer.isEmpty());
                }

                if (userAnswer.equals(securityAnswers.get(i))) {
                    System.out.println("Security question answered correctly.");
                    correct += 1;
                } else {
                    System.out.println("Max wrong attempts reached. Please wait before trying again.");
                    // Implement a waiting period (e.g., using Thread.sleep) before allowing another attempt.
                }
            }

            // If all security questions are answered correctly, allow the user to set a new password
            if (correct == securityQuestions.size()) {
                boolean passwordResetSuccessful = false;

                while (!passwordResetSuccessful) {
                    System.out.print("Enter your new password or press '1' to cancel: ");
                    String newPassword = scanner.next();

                    if (newPassword.equals("1")) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Password reset canceled.");
                        account_Manager account_Manager = new account_Manager(scanner);
                        account_Manager.start();
                        break; // Exit the loop if the user chooses to cancel
                    }

                    if (updatePassword(staff.getStaffID(), newPassword)) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Password reset successful. You can now log in with your new password.");
                        passwordResetSuccessful = true; // Exit the loop when the password meets the criteria

                        account_Manager account_Manager = new account_Manager(scanner);
                        account_Manager.start();

                    } else {
                        System.out.println("Password does not meet the criteria. Please try again.");
                    }
                }
            } else {
                System.out.println("User not found. Password reset failed.");
            }
        }
    }

    public static boolean isValidPassword(String password) {
        // Check if the password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check if the password contains at least one digit
        boolean containsDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }

        // Check if the password contains both uppercase and lowercase characters
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                containsLowerCase = true;
            }
        }

        // Return true if all criteria are met
        return containsDigit && containsUpperCase && containsLowerCase;
    }
}

