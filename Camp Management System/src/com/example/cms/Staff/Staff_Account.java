package com.example.cms.Staff;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * When a Staff logs in, they will first see the menu provided by this class.
 */
public class Staff_Account {
	
    protected void start(Staff existingStaffMember){
    	
    	int borderLength = 53;  // Adjust the border length based on your preference

    	// Print the decorative border
    	System.out.println("=".repeat(borderLength));

    	// Print the STAFF header
    	System.out.println("                        STAFF                        ");

    	// Print staff information
    	System.out.println("               Name: " + existingStaffMember.getName());
    	System.out.println("               ID: " + existingStaffMember.getStaffID());

    	// Print the decorative border again
    	System.out.println("=".repeat(borderLength));
    	System.out.println("================STAFF HOME PAGE================");
        
        Scanner input = new Scanner(System.in);
        boolean exit = false;

        do{
            System.out.println("1. Manage Camp");
            System.out.println("2. Manage Enquiries");
            System.out.println("3. Manage Suggestions");
            System.out.println("4. Logout");

            int choice;
            do {
                try {
                    System.out.print("Enter your choice: ");
                    choice = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    input.nextLine(); // Consume the invalid input
                    choice = -1; // Set a default value or use a flag to handle the loop
                }
            } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);

            switch (choice) {
                case 1:
                    manageCamp(existingStaffMember);

                    break;
                case 2:
                    manageEnquiry(existingStaffMember);
                    // Use the Attendee class method to manage camps

                    break;
                case 3:
                    // Use the Attendee class method to manage enquiries
                    manageSuggestion(existingStaffMember);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

        }while(!exit);
    }

    private void manageCamp(Staff existingStaffMember) {
               Scanner input = new Scanner(System.in);
        boolean exit = false;

        do {
            try {
            	// Add new lines for the appearance of entering a new page
                System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

                int borderLength = 53;  // Adjust the border length based on your preference

                // Print the decorative border
                System.out.println("=".repeat(borderLength));

                // Print the STAFF header
                System.out.println("                        STAFF                        ");

                // Print staff information
                System.out.println("               Name: " + existingStaffMember.getName());
                System.out.println("               ID: " + existingStaffMember.getStaffID());

                // Print the decorative border again
                System.out.println("=".repeat(borderLength));

                // Beautiful println statement for the Camp Management Menu
                System.out.println("================CAMP MANAGEMENT MENU================");
         
                
                // Display the Camp Management Menu
                System.out.println("1. Create New Camp");
                System.out.println("2. Edit Existing Camp");
                System.out.println("3. Delete Existing Camp");
                System.out.println("4. View All Camps");
                System.out.println("5. View Camps Created");
                System.out.println("6. Generate Camp Report");
                System.out.println("7. Back To the Main Page");

                // Get user input
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();

                // Process user choice
                switch (choice) {
                    case 1:
                        existingStaffMember.createCamp();
                        break;
                    case 2:
                        existingStaffMember.editCamp(existingStaffMember);
                        break;
                    case 3:
                        existingStaffMember.deleteCamp(existingStaffMember);
                        break;
                    case 4:
                        existingStaffMember.viewAllCamps();
                        break;
                    case 5:
                        existingStaffMember.viewCampCreated(existingStaffMember);
                        break;
                    case 6:
                        existingStaffMember.generateReport(existingStaffMember);
                        break;
                    case 7:
                        exit = true;
                        System.out.println("Going back to Staff Home Page");
                        start(existingStaffMember);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine(); // Consume the invalid input
            }
        } while (!exit);
    }


    private void manageEnquiry(Staff existingStaffMember) {
        // Add new lines for the appearance of entering a new page
        System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

        int borderLength = 53;  // Adjust the border length based on your preference

        // Print the decorative border
        System.out.println("=".repeat(borderLength));

        // Print the STAFF header
        System.out.println("                        STAFF                        ");

        // Print staff information
        System.out.println("               Name: " + existingStaffMember.getName());
        System.out.println("               ID: " + existingStaffMember.getStaffID());

        // Print the decorative border again
        System.out.println("=".repeat(borderLength));

        // Beautiful println statement for the Enquiries Management Menu
        System.out.println("================ENQUIRIES MANAGEMENT MENU================");

        Scanner input = new Scanner(System.in);
        boolean exit = false;

        do {
            try {
                // Display the Enquiries Management Menu
                System.out.println("1. View Enquiry");
                System.out.println("2. Reply to Enquiry");
                System.out.println("3. Back To the Main Page");

                // Get user input
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();

                // Process user choice
                switch (choice) {
                    case 1:
                        // Delegate to specific functionality in the Staff class
                        existingStaffMember.viewEnquiry(existingStaffMember);
                        break;
                    case 2:
                        // Delegate to specific functionality in the Staff class
                        existingStaffMember.replyEnquiry(existingStaffMember);
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Going back to Staff Home Page");
                        start(existingStaffMember);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine(); // Consume the invalid input
            }
        } while (!exit);
    }


    private void manageSuggestion(Staff existingStaffMember) {
        // Add new lines for the appearance of entering a new page
        System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

        int borderLength = 53;  // Adjust the border length based on your preference

        // Print the decorative border
        System.out.println("=".repeat(borderLength));

        // Print the STAFF header
        System.out.println("                        STAFF                        ");

        // Print staff information
        System.out.println("               Name: " + existingStaffMember.getName());
        System.out.println("               ID: " + existingStaffMember.getStaffID());

        // Print the decorative border again
        System.out.println("=".repeat(borderLength));

        // Beautiful println statement for the Suggestion Management Menu
        System.out.println("================SUGGESTION MANAGEMENT MENU================");

        Scanner input = new Scanner(System.in);
        boolean exit = false;

        do {
            try {
                // Display the Suggestion Management Menu
                System.out.println("Suggestion Management Menu:");
                System.out.println("1. View Suggestions");
                System.out.println("2. Approve Suggestions");
                System.out.println("3. Back To the Main Page");

                // Get user input
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();

                // Process user choice
                switch (choice) {
                    case 1:
                        // Delegate to specific functionality in the Staff class
                        existingStaffMember.viewSuggestion(existingStaffMember);
                        break;
                    case 2:
                        // Delegate to specific functionality in the Staff class
                        existingStaffMember.approveSuggestion(existingStaffMember);
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Going back to Staff Home Page");
                        start(existingStaffMember);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine(); // Consume the invalid input
            }
        } while (!exit);
    }

}
