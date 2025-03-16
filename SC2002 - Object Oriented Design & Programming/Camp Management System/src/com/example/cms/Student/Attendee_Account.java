package com.example.cms.Student;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Staff.Staff;
import com.example.cms.user_Login.account_Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * When a Camp Attendee logs in, they will first see the menu provided by this class.
 */
public class Attendee_Account extends Student_Account {

    private String studentID;
    private Map<String, Attendee> existingAttendees;
    
    public boolean getAttendeeAccount(String studentID) {
        // Use the existingStudents map to check if the student exists
        return existingAttendees.containsKey(studentID);
    }
    
    public Attendee_Account(String studentID, Map<String, Student_User> existingStudent) {
        super(studentID, existingStudent);
        this.studentID = studentID;

        // Initialize existingAttendees by filtering Attendee objects
        this.existingAttendees = new HashMap<>();
        for (Map.Entry<String, Student_User> entry : existingStudent.entrySet()) {
            if (entry.getValue() instanceof Attendee) {
                this.existingAttendees.put(entry.getKey(), (Attendee) entry.getValue());
            }
            // Handle other cases if needed
        }
    }


	public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        Attendee attendee = Attendee.attendeesMap.get(studentID);

        if (attendee != null) {
        //    System.out.println("Attendee in attendee account:");
        //    System.out.println("Student ID: " + attendee.getStudentID());
        //    System.out.println("Name: " + attendee.getName());  // Assuming you have a getName method in Attendee
        } else {
       //     System.out.println("Attendee not found. Logging out.");
            running = false;
            account_Manager account_Manager = new account_Manager(scanner);
			account_Manager.start();
        }

        while (running) {
        	
        	int borderLength = 53;  // Adjust the border length based on your preference

        	// Print the decorative border
        	System.out.println("=".repeat(borderLength));

        	// Print the STAFF header
        	System.out.println("                        STUDENT - ATTENDEE                       ");

        	// Print staff information
        	System.out.println("               Name: " + attendee.getName());
        	System.out.println("               ID: " + attendee.getStudentID());

        	// Print the decorative border again
        	System.out.println("=".repeat(borderLength));
        	System.out.println("================STUDENT - ATTENDEE HOME PAGE================");

            System.out.println("1. Display Registered Camps");
            System.out.println("2. Manage Camp");
            System.out.println("3. Manage Enquiries");
            System.out.println("4. Logout");

            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    // Use the Attendee class method to display registered camps
                    Attendee.displayRegisteredCamps(attendee);
                    break;
                case 2:
                    // Use the Attendee class method to manage camps
                    Attendee.manageCamp(attendee);
                    break;
                case 3:
                    // Use the Attendee class method to manage enquiries
                    Attendee.manageEnquiries(attendee);
                    break;
                case 4:
                    running = false;
                    System.out.println("Logging out.");
                    
                    account_Manager account_Manager = new account_Manager(scanner);
                    account_Manager.start();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }
    
    public static int getUserChoice(Scanner scanner) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter your choice: ");
                String inputLine = scanner.nextLine().trim();

                if (inputLine.isEmpty()) {
                    System.out.println("Invalid input. Please enter a number.");
                } else {
                    choice = Integer.parseInt(inputLine);
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return choice;
    }

}
