package com.example.cms.user_Login;

import com.example.cms.CSVConverter.CSVWriter;
import com.example.cms.Faculty;
import com.example.cms.Password.Password_Hasher;
import com.example.cms.Password.Password_Manager;
import com.example.cms.Password.password_Data;
import com.example.cms.Student.Student_User;
import com.example.cms.Student_Role;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
/**
 * Controller class to handle and process registration of new users
 * Routed from account_Manager
 */
public class user_Registration {
	private Scanner scanner;
	String studentID = null;
	String studentName = null; 
	Student_Role userGroup  = null;
	//String password = null;   
	int points = 0;
	password_Data password = null;
	
	   public user_Registration (Scanner scanner) {
	        this.scanner = scanner;
	    }
	 
	public void start() {
	    // Collect user information for registration
		Scanner scanner = new Scanner(System.in);
	    System.out.println("Student Registration:");

	    studentID = getStudentId();

	    // Check if the student already exists
	    Student_User existingStudent = new Student_User();
	    if (existingStudent.isStudentExist(studentID)) {
	        System.out.println("This student already has an account.");
	        account_Manager account_Manager = new account_Manager(scanner);
			account_Manager.start();
	    }

	    studentName = getName();
	    
	    userGroup = getUserGroup();
	    
	    if(userGroup == Student_Role.COMMITTEE) {
	    	boolean campCommittee = true;
	    	password = createAndConfirmPassword();
	    	
	    	int facultyChoice = getFacultyChoice();
	        Faculty facultyInput = getFacultyInput(facultyChoice);

	        // Set default campAccessibility based on faculty
	        List<String> campAccessibility = getDefaultCampAccessibility(facultyInput);
	        
	        // Get security questions and answers
	        List<String> securityQuestions = new ArrayList<>();
	        List<String> securityAnswers = new ArrayList<>();
	        getSecurityQuestionsAndAnswers(securityQuestions, securityAnswers);
	        
	        // Store the confirmed information in the student instance
		    Student_User newStudent = new Student_User();
	        newStudent.setStudentID(studentID);
	        newStudent.setName(studentName);
	        newStudent.setUserGroup(userGroup);
	        newStudent.setPassword(password.getPassword());
	        newStudent.setSalt(password.getSalt());
	        newStudent.setFaculty(facultyInput);
	        newStudent.setPoints(points);
	        newStudent.setCampAccessibility(campAccessibility);
	        newStudent.setRegisteredCamps(null);
	        newStudent.setCampCommittee(campCommittee);
	        newStudent.setSecurityQuestions(securityQuestions);
	        newStudent.setSecurityAnswers(securityAnswers);
	        newStudent.setEnquirySubmitted(null);
	        newStudent.setSuggestionSubmitted(null);

		 // Add the new student to the list of existing students
		  //  Student_User.addStudent(newStudent);
		    
		    CSVWriter.writeStudentUserToCSV(newStudent, true);
		    CSVWriter.writeCommitteeToCSV(newStudent, true);
		    
		    
		    System.out.println("Registration successful!");
		    
		    account_Manager accountManager = new account_Manager(scanner);
		    accountManager.start();
	    }
	    else {
	    	boolean campCommittee = false;
	    	password = createAndConfirmPassword();
	    	
	    	int facultyChoice = getFacultyChoice();
	        Faculty facultyInput = getFacultyInput(facultyChoice);

	        // Set default campAccessibility based on faculty
	        List<String> campAccessibility = getDefaultCampAccessibility(facultyInput);
	        // Get security questions and answers
	        List<String> securityQuestions = new ArrayList<>();
	        List<String> securityAnswers = new ArrayList<>();
	        getSecurityQuestionsAndAnswers(securityQuestions, securityAnswers);
	        
	        // Store the confirmed information in the student instance
		    Student_User newStudent = new Student_User();
	        newStudent.setStudentID(studentID);
	        newStudent.setName(studentName);
	        newStudent.setUserGroup(userGroup);
	        newStudent.setPassword(password.getPassword());
	        newStudent.setSalt(password.getSalt());
	        newStudent.setFaculty(facultyInput);
	        newStudent.setPoints(points);
	        newStudent.setCampAccessibility(campAccessibility);
	        newStudent.setRegisteredCamps(null);
	        newStudent.setCampCommittee(campCommittee);
	        newStudent.setSecurityQuestions(securityQuestions);
	        newStudent.setSecurityAnswers(securityAnswers);
	        newStudent.setEnquirySubmitted(null);
	        newStudent.setSuggestionSubmitted(null);

		 // Add the new student to the list of existing students
		  //  Student_User.addStudent(newStudent);
		    
		    CSVWriter.writeStudentUserToCSV(newStudent, true);
		    CSVWriter.writeAttendeeToCSV(newStudent, true);
		    
		    System.out.println("Registration successful!");
		    
		    account_Manager accountManager = new account_Manager(scanner);
		    accountManager.start();
	    }
	    
	
		}
	
	private String getStudentId() {
	    String studentId = null;
	    String specialCharacters = "[!@#$%^&*(),.?\":{}|<>]"; // Define a regex for special characters

	    while (true) {
	        try {
	            System.out.print("Enter your student ID: ");
	            studentId = scanner.nextLine().trim();

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

	            // Add additional checks if needed

	            break; // Exit the loop if the input is not empty and doesn't contain special characters
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid student ID.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }

	    return studentId.toUpperCase().trim();
	}
	
	private String getName() {
	    String name = null;
	    String allowedCharactersRegex = "[A-Za-z]+([\\-\\.\\s]?[A-Za-z]+)*"; // Alphabets, hyphen, period, and space are allowed

	    while (true) {
	        try {
	            System.out.print("Enter your name: ");
	            name = scanner.nextLine().trim();

	            // Check if the input is empty
	            if (name.isEmpty()) {
	                System.out.println("Invalid input. Please enter a valid name.");
	                continue; // Restart the loop if the input is empty
	            }

	            // Check if the input matches the allowed pattern
	            if (!name.matches(allowedCharactersRegex)) {
	                System.out.println("Invalid input. Please enter a valid name with only alphabets, hyphen, period, and space.");
	                continue; // Restart the loop if the input contains disallowed characters
	            }

	            // Add additional checks if needed

	            break; // Exit the loop if the input is not empty and matches the allowed pattern
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid name.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }

	    System.out.println("Inputted name: " + name);

	    return name.toUpperCase().trim();
	}

	
	private Student_Role getUserGroup() {
	    Student_Role userGroup = null;
	    int confirmation = 0;
	    int userGroups = 0;
	    boolean campCommittee = false;
	    boolean confirmed = false;

	    while (!confirmed) {
	        try {
	            System.out.print("Enter your user group (1 for Attendee, 2 for Committee): ");
	            if (scanner.hasNextInt()) {
	                String userGroupChoice = scanner.nextLine().trim();

	                // Check if the input is empty
	                if (userGroupChoice.isEmpty()) {
	                    System.out.println("Invalid input. Please enter '1' or '2'.");
	                    continue; // Restart the loop if the input is empty
	                }

	                userGroups = Integer.parseInt(userGroupChoice);

	                if (userGroups == 1) {
	                    userGroup = Student_Role.ATTENDEE;
	                    System.out.println("You have selected 'Attendee'. Is this correct? (1 for Yes, 2 for No): ");
	                    String confirmationChoice = scanner.nextLine().trim();

	                    // Check if the input is empty
	                    if (confirmationChoice.isEmpty()) {
	                        System.out.println("Invalid input. Please enter '1' or '2'.");
	                        continue; // Restart the loop if the input is empty
	                    }

	                    confirmation = Integer.parseInt(confirmationChoice);

	                    if (confirmation == 1) {
	                        confirmed = true; // User confirmed the choice, exit the loop.
	                        campCommittee = false;
	                    } else if (confirmation == 2) {
	                        System.out.print("Please re-select your user group (1 for Attendee, 2 for Committee): ");
	                    } else {
	                        System.out.println("Invalid confirmation choice. Please enter '1' for Yes or '2' for No.");
	                    }
	                } else if (userGroups == 2) {
	                    userGroup = Student_Role.COMMITTEE;
	                    System.out.println("You have selected 'Committee'. Is this correct? (1 for Yes, 2 for No): ");
	                    String confirmationChoice = scanner.nextLine().trim();

	                    // Check if the input is empty
	                    if (confirmationChoice.isEmpty()) {
	                        System.out.println("Invalid input. Please enter '1' or '2'.");
	                        continue; // Restart the loop if the input is empty
	                    }

	                    confirmation = Integer.parseInt(confirmationChoice);

	                    if (confirmation == 1) {
	                        confirmed = true; // User confirmed the choice, exit the loop.
	                        campCommittee = true;
	                    } else if (confirmation == 2) {
	                        System.out.print("Please re-select your user group (1 for Attendee, 2 for Committee): ");
	                    } else {
	                        System.out.println("Invalid confirmation choice. Please enter '1' for Yes or '2' for No.");
	                    }
	                } else {
	                    System.out.println("Invalid user group choice. Please enter '1' for Attendee or '2' for Committee.");
	                }
	            } else {
	                System.out.println("Invalid input. Please enter a number.");
	                scanner.nextLine(); // Consume the invalid input
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a number.");
	            scanner.nextLine(); // Consume the invalid input
	        }
	    }

	    return userGroup;
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

	            if (Password_Manager.isValidPassword(password)) {
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

	private List<String> getDefaultCampAccessibility(Faculty faculty) {
	    List<String> defaultAccessibility = new ArrayList<>();

	    // Set default campAccessibility based on faculty
	    switch (faculty) {
	        case ADM:
	            defaultAccessibility.add("ADM");
	            break;
	        case ASE:
	            defaultAccessibility.add("ASE");
	            break;
	        case CCEB:
	            defaultAccessibility.add("CCEB");
	            break;
	        case CEE:
	            defaultAccessibility.add("CEE");
	            break;
	        case EEE:
	            defaultAccessibility.add("EEE");
	            break;
	        case IGS:
	            defaultAccessibility.add("IGS");
	            break;
	        case NBS:
	            defaultAccessibility.add("NBS");
	            break;
	        case MAE:
	            defaultAccessibility.add("MAE");
	            break;
	        case MSE:
	            defaultAccessibility.add("MSE");
	            break;
	        case NIE:
	            defaultAccessibility.add("NIE");
	            break;
	        case RSIS:
	            defaultAccessibility.add("RSIS");
	            break;
	        case SBS:
	            defaultAccessibility.add("SBS");
	            break;
	        case SCSE:
	            defaultAccessibility.add("SCSE");
	            break;
	        case SPMS:
	            defaultAccessibility.add("SPMS");
	            break;
	        case SSS:
	            defaultAccessibility.add("SSS");
	            break;
	        case SOH:
	            defaultAccessibility.add("SOH");
	            break;
	        case WKWSCI:
	            defaultAccessibility.add("WKWSCI");
	            break;
	        case ALL:
	            defaultAccessibility.add("ALL");
	            break;
	        case NIL:
	            // Handle NIL case, e.g., set a default value or leave it empty
	            break;
	    }

	    return defaultAccessibility;
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

	  
	

}
