package com.example.cms.Staff;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.CSVConverter.CSVWriter;
import com.example.cms.Faculty;
import com.example.cms.Password.Password_Hasher;
import com.example.cms.Password.Password_Manager_Staff;
import com.example.cms.Password.password_Data;
import com.example.cms.Student.Student_User;
import com.example.cms.user_Login.account_Manager;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Functionality for staff registration
 */
public class Staff_Setup {
	Scanner scanner = new Scanner(System.in);
	password_Data password;

    public Staff_Setup() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
    	System.out.println("==========STAFF=========");
    	  
          // Get staff ID from the staff
          System.out.print("Enter Staff ID: ");
          String staffID = scanner.nextLine().trim().toUpperCase();
          System.out.println("Staff id inputted: " + staffID);
                  // Check if staff ID already exists
          com.example.cms.Staff.Staff_User staff = com.example.cms.Staff.Staff_User.getExistingStaff().get(staffID);
          
          // Check if staff is not null and staffID is not null
          if (staff != null && staff.getStaffID() != null && staff.getStaffID().equals(staffID)) {
              System.out.println("Staff ID already exists. Do you want to update your account? (yes/no)");
              String updateChoice = scanner.nextLine().toLowerCase();

              if (updateChoice.equals("yes")) {
                  updateAccount(staffID);
              } else {
                  System.out.println("Redirecting to staff login.");
                  com.example.cms.Staff.Staff_Login staff_Login = new com.example.cms.Staff.Staff_Login();
                  // Redirect to staff login logic
                  staff_Login.start(staffID);
              }
          } else {
        	    // Staff ID doesn't exist
        	    System.out.println("Staff ID does not exist.");
        	    
        	    Student_User studentUser = new Student_User();
                CSVDataManager.loadStudentsFromCSV(studentUser);
                
        	    // Check if the ID is not from a student
        	    if (!studentUser.getExistingStudents().containsKey(staffID)) {
        	        System.out.println("The entered ID is not associated with a student account.");

        	        System.out.print("Do you want to create a new account? (yes/no): ");
        	        String createNewAccountChoice = scanner.nextLine().toLowerCase();

        	        if ("yes".equals(createNewAccountChoice)) {
        	            setupNewStaffAccount(staffID);
        	        } else {
        	            System.out.println("Redirecting to account manager.");
        	            
        	            account_Manager accountManager = new account_Manager(scanner);
        	            accountManager.start();
        	        }
        	    } else {
        	        // The entered ID is associated with a student account
        	        System.out.println("The entered ID belongs to a student. Redirecting to account Manager.");
        	        account_Manager accountManager = new account_Manager(scanner);
    	            accountManager.start();;
        	    }
        	}
      }

    private void setupNewStaffAccount(String staffID) {
        // Get name
    	String name = getValidNameInput();
    	
    	//Get password
    	password = createAndConfirmPassword();

    	// Get faculty
    	int facultyChoice = getFacultyChoice();
        Faculty facultyInput = getFacultyInput(facultyChoice);
        
     // Get security questions and answers
        List<String> securityQuestions = new ArrayList<>();
        List<String> securityAnswers = new ArrayList<>();
        getSecurityQuestionsAndAnswers(securityQuestions, securityAnswers);

     // Store the confirmed information in the staff instance
	    com.example.cms.Staff.Staff_User staff_Information = new com.example.cms.Staff.Staff_User();
	    staff_Information.setStaffID(staffID);
	    staff_Information.setName(name);
	    staff_Information.setPassword(password.getPassword());
	    staff_Information.setSalt(password.getSalt());
	    staff_Information.setFaculty(facultyInput);
	    staff_Information.setSecurityQuestions(securityQuestions);
	    staff_Information.setSecurityAnswers(securityAnswers);
	    staff_Information.setCampsCreated(null);
	    
	    // Add the new student to the list of existing students
		  //  Student_User.addStudent(newStudent);
		    
		    CSVWriter.writeStaffToCSV(staff_Information, true);
		    
		    System.out.println("Registration successful!");
		    
		    account_Manager accountManager = new account_Manager(scanner);
		    accountManager.start();

        System.out.println("Staff setup completed successfully. Redirecting to staff login.");
        // Redirect to staff login logic
        // staffLogin();
    }
    
    private String getValidNameInput() {
        String name = null;

        while (true) {
            try {
                System.out.print("Enter Name: ");
                name = scanner.nextLine().trim();

                // Check if the input is empty or contains only whitespace
                if (name.isEmpty() || name.isBlank()) {
                    throw new IllegalArgumentException("Invalid input. Please enter a non-empty value.");
                }

                // Check if the input contains numerical values
                if (name.matches(".*\\d.*")) {
                    throw new IllegalArgumentException("Invalid input. Please enter a name without numerical values.");
                }

                // Add additional checks if needed

                break; // Exit the loop if the input is not empty, contains only allowed characters, and follows the specified pattern
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return name;
    }
    
    private password_Data createAndConfirmPassword() {
	    String password = null;
	    String salt = null;

	    while (true) {
	        try {
	            System.out.print("Create a password: ");
	            password = scanner.nextLine().trim();

	            // Check if the input is empty
	            if (password.isEmpty()) {
	                System.out.println("Invalid input. Please enter a non-empty password.");
	                continue; // Restart the loop if the input is empty
	            }

	            if (Password_Manager_Staff.isValidPassword(password)) {
	            	System.out.println("Entered Password is: " + password);

	                break; // Exit the loop if the password is valid
	            } else {
	                System.out.println("Password does not meet the criteria. Please make sure it has 8 characters, includes both upper and lower case letters, and is alphanumeric.");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid password.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }

	    while (true) {
	        try {
	            // Confirm the password
	            System.out.print("Confirm your password: ");
	            String confirmPassword = scanner.nextLine().trim();
	            System.out.println("This is your confirm password: "+confirmPassword);

	            // Check if the input is empty
	            if (confirmPassword.isEmpty()) {
	                System.out.println("Invalid input. Please enter a non-empty password confirmation.");
	                continue; // Restart the loop if the input is empty
	            }

	            if (password.equals(confirmPassword)) {
	            	// Generate salt using Password_Manager
                    salt = Password_Hasher.generateSalt();
                    System.out.println("This is salt: " + salt); 
                    // Hash the password using Password_Manager
                    password = Password_Hasher.hashPassword(password, salt);
	                break; // Exit the confirmation loop if the passwords match
	            } else {
	                System.out.println("Password confirmation doesn't match. Please try again.");
	                System.out.println("Enter '1' to create a new password or any other key to try again: ");
	                String choice = scanner.nextLine().trim();
	                if (!choice.equals("1")) {
	                    continue; // Re-enter the confirmation password
	                } else {
	                	System.out.println("Re-entered Password: " + choice);
	                    // User wants to create a new password
	                    
	                }
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid password.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }

	    return new password_Data(password, salt);
	}
    
    private int getFacultyChoice() {
	    int facultyChoice = 0;
	    boolean validChoice = false;

	    while (!validChoice) {
	        try {
	            System.out.println("Select your faculty:");
	            System.out.println("1. ADM");
	            System.out.println("2. ASE");
	            System.out.println("3. CCEB");
	            System.out.println("4. CEE");
	            System.out.println("5. EEE");
	            System.out.println("6. IGS");
	            System.out.println("7. NBS");
	            System.out.println("8. MAE");
	            System.out.println("9. MSE");
	            System.out.println("10. NIE");
	            System.out.println("11. RSIS");
	            System.out.println("12. SBS");
	            System.out.println("13. SCSE");
	            System.out.println("14. SPMS");
	            System.out.println("15. SSS");
	            System.out.println("16. SOH");
	            System.out.println("17. WKWSCI");
	            System.out.println("18. ALL");

	            System.out.print("Enter the number corresponding to your faculty: ");
	            String input = scanner.nextLine().trim();

	            if (!input.isEmpty()) {
	                facultyChoice = Integer.parseInt(input);

	                if (facultyChoice >= 1 && facultyChoice <= 18) {
	                    validChoice = true;
	                } else {
	                    System.out.println("Invalid input. Please enter a number between 1 and 18.");
	                }
	            } else {
	                System.out.println("Invalid input. Please enter a non-empty value.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	        }
	    }

	    return facultyChoice;
	}

	private Faculty getFacultyInput(int facultyChoice) {
	    switch (facultyChoice) {
	        case 1:
	            return Faculty.ADM;
	        case 2:
	            return Faculty.ASE;
	        case 3:
	            return Faculty.CCEB;
	        case 4:
	            return Faculty.CEE;
	        case 5:
	            return Faculty.EEE;
	        case 6:
	            return Faculty.IGS;
	        case 7:
	            return Faculty.NBS;
	        case 8:
	            return Faculty.MAE;
	        case 9:
	            return Faculty.MSE;
	        case 10:
	            return Faculty.NIE;
	        case 11:
	            return Faculty.RSIS;
	        case 12:
	            return Faculty.SBS;
	        case 13:
	            return Faculty.SCSE;
	        case 14:
	            return Faculty.SPMS;
	        case 15:
	            return Faculty.SSS;
	        case 16:
	            return Faculty.SOH;
	        case 17:
	            return Faculty.WKWSCI;
	        case 18:
	            return Faculty.ALL;
	        default:
	            return Faculty.NIL; // Handle invalid choices
	    }
	}
	
	private void getSecurityQuestionsAndAnswers(List<String> securityQuestions, List<String> securityAnswers) {
	    boolean confirmed = false;
	    int numSecurityQuestions = 3;

	    while (!confirmed) {
	        try {
	            for (int i = 1; i <= numSecurityQuestions; i++) {
	                System.out.print("Enter your security question " + i + ": ");
	                String question = getValidSecurityInput();
	                securityQuestions.add(question);

	                System.out.print("Enter your answer to the question '" + question + "': ");
	                String answer = getValidSecurityInput();
	                securityAnswers.add(answer);
	            }

	            // Display the collected security questions and answers for confirmation
	            System.out.println("Please confirm your security questions and answers:");
	            for (int i = 0; i < numSecurityQuestions; i++) {
	                System.out.println("Question " + (i + 1) + ": " + securityQuestions.get(i));
	                System.out.println("Answer " + (i + 1) + ": " + securityAnswers.get(i));
	            }

	            System.out.print("Is everything correct? (1 for Yes, 2 for No): ");
	            int confirmationChoice = scanner.nextInt();
	            if (confirmationChoice == 1) {
	                confirmed = true; // User confirmed the choices, exit the loop.
	            } else if (confirmationChoice == 2) {
	                System.out.print("Enter the number of the security question you want to update (1 to " + numSecurityQuestions + ", 0 to finish): ");
	                int questionNumber = scanner.nextInt();
	                if (questionNumber == 0) {
	                    break; // Exit the loop to confirm or finish updating.
	                }
	                if (questionNumber >= 1 && questionNumber <= numSecurityQuestions) {
	                    System.out.print("Enter your updated security question " + questionNumber + ": ");
	                    securityQuestions.set(questionNumber - 1, getValidSecurityInput());

	                    System.out.print("Enter your updated answer to the question '" + securityQuestions.get(questionNumber - 1) + "': ");
	                    securityAnswers.set(questionNumber - 1, getValidSecurityInput());
	                } else {
	                    System.out.println("Invalid question number. Please enter a number between 1 and " + numSecurityQuestions + " or 0 to finish.");
	                }
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }
	}

	private String getValidSecurityInput() {
	    String input;
	    String allowedCharactersRegex = "^[A-Za-z0-9?][A-Za-z0-9\\s?]*[A-Za-z0-9?]$";

	    while (true) {
	        try {
	            input = scanner.nextLine().trim();

	            // Check if the input is empty or contains only whitespace
	            if (input.isEmpty() || input.isBlank()) {
	                System.out.println("Invalid input. Please enter a non-empty value.");
	                continue; // Restart the loop if the input is empty or contains only whitespace
	            }

	            // Check if the input matches the allowed pattern
	            if (!input.matches(allowedCharactersRegex)) {
	                System.out.println("Invalid input. Special characters and leading/trailing spaces are not allowed.");
	                continue; // Restart the loop if the input contains disallowed characters or leading/trailing spaces
	            }

	            // Convert the input to uppercase
	            input = input.toUpperCase();

	            // Add additional checks if needed

	            break; // Exit the loop if the input is not empty, contains only allowed characters, and follows the specified pattern
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid value.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }

	    return input;
	}


	private void updateAccount(String staffID) {
		
		Scanner scanner = new Scanner(System.in);
	    // Get current staff information
	    //this.getExistingStaff = new HashMap<>();
	    //Staff_User staff = CSVDataManager.loadStaffFromCSV(getExistingStaff.get(staffID));
		com.example.cms.Staff.Staff_User staff = com.example.cms.Staff.Staff_User.getExistingStaff().get(staffID);
	    // Display current information
	    System.out.println("Current Faculty: " + staff.getFaculty());
	    System.out.println("Current Password: " + staff.getPassword());
	    System.out.println("Current Security Question: " + staff.getSecurityQuestion());

	    System.out.print("Do you want to update Faculty? (yes/no): ");
	    String updateFacultyChoice = scanner.nextLine().toLowerCase();
	    Faculty newFaculty = staff.getFaculty();

	    if (updateFacultyChoice.equals("yes")) {
	        newFaculty = getFacultyWithConfirmation();
	        staff.setFaculty(newFaculty);
	    }

	    // Ask if the user wants to update the password
	    System.out.print("Do you want to update Password? (yes/no): ");
	    String updatePasswordChoice = scanner.nextLine().toLowerCase();
	    String newPassword = staff.getPassword();
	    if (updatePasswordChoice.equals("yes")) {
	        // Get new password information
	        password_Data newPasswordData = createAndConfirmPassword();
	        newPassword = newPasswordData.getPassword();
	        staff.setPassword(newPassword);
	        staff.setSalt(newPasswordData.getSalt());
	    }

	    // Ask if the user wants to update the security questions
	    System.out.print("Do you want to update Security Questions? (yes/no): ");
	    String updateSecurityQuestionsChoice = scanner.nextLine().toLowerCase();
	    List<String> newSecurityQuestions = staff.getSecurityQuestion();
	    List<String> newSecurityAnswers = staff.getSecurityAnswers();
	    if (updateSecurityQuestionsChoice.equals("yes")) {
	        // Get new security questions and answers
	        getSecurityQuestionsAndAnswers(newSecurityQuestions, newSecurityAnswers);
	        staff.setSecurityQuestions(newSecurityQuestions);
	        staff.setSecurityQuestions(newSecurityQuestions);
	    }

	    // Update the CSV file with the updated staff information
	    CSVDataManager.updateStaffCSVFile(staff);

	    System.out.println("Staff account updated successfully. Redirecting to staff login.");
	    
	    account_Manager accountManager = new account_Manager(scanner);
	    accountManager.start();
	    // Redirect to staff login logic
	    // staffLogin();
	}
	
	private Faculty getFacultyWithConfirmation() {
	    Faculty newFaculty = null;
	    boolean confirmed = false;

	    while (!confirmed) {
	        try {
	            int facultyChoice = getFacultyChoice();
	            newFaculty = getFacultyInput(facultyChoice);

	            // Confirm the new faculty choice
	            System.out.print("Confirm update to " + newFaculty + "? (yes/no): ");
	            String confirmationChoice = scanner.nextLine().toLowerCase();

	            if (confirmationChoice.equals("yes")) {
	                confirmed = true;
	            } else {
	                System.out.println("Update canceled. Please choose a different faculty.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	        }
	    }

	    return newFaculty;
	}


}
