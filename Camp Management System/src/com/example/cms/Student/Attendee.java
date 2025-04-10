package com.example.cms.Student;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Camp.Camp;
import com.example.cms.Camp.campData;
import com.example.cms.DisplayOptions.DisplayApp;
import com.example.cms.Enquiries.Enquiry;
import com.example.cms.Faculty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * When the Camp Attendee user chooses an option from the menu, they will be directed to this class to complete the required functions.
 */
public class Attendee extends Student_User {

	 public static Map<String, Attendee> attendeesMap = new HashMap<>(); //student ID
    public static HashMap<String, Attendee> attendeeToNameMap = new HashMap<>();
     private ArrayList<Camp> registeredCamps;

     private ArrayList<Enquiry> enquirySubmitted;

     public Attendee() {
         super();
         // Initialize the map only if it's null
         if (attendeesMap == null) {
             attendeesMap = new HashMap<>();
         }
         
         if (attendeeToNameMap == null) {
        	 attendeeToNameMap = new HashMap<>();
         }
         
         this.registeredCamps = new ArrayList<>();
         this.enquirySubmitted = new ArrayList<>();
         attendeesMap.put(studentID, this); // Use studentID as the key
         
 
     }

  // Method to add an attendee
     public void addAttendee(Attendee newAttendee) {
         if (newAttendee != null) {
             // Add the new attendee to the map
             attendeesMap.put(newAttendee.getStudentID(), newAttendee);

             // Print success message and details about the added attendee
             System.out.println("\nNew Attendee added successfully:");
             System.out.println("Student ID: " + newAttendee.getStudentID());
             System.out.println("Name: " + newAttendee.getName());
         } else {
             System.out.println("Failed to add attendee. The provided attendee is null.");
         }
     }

     
  // Method to get an Attendee by ID
     public Attendee getAttendeeByID(String attendeeID) {
         if (attendeesMap.containsKey(attendeeID)) {
             // Print success message and details about the found attendee
             System.out.println("\nAttendee with ID " + attendeeID + " found:");
             System.out.println("Student ID: " + attendeesMap.get(attendeeID).getStudentID());
             System.out.println("Name: " + attendeesMap.get(attendeeID).getName());
  
             return attendeesMap.get(attendeeID);
         } else {
             // Print failure message
             System.out.println("Attendee with ID " + attendeeID + " not found.");
             return null;
         }
     }

     
     // Method to get existing attendees
     public Map<String, Attendee> existingAttendees() {
         return attendeesMap;
     }
     
    public ArrayList<Camp> getRegisteredCamps() {
        return registeredCamps;
    }

    public void setRegisteredCamps(Camp camp){
            registeredCamps.add(camp);
    }

    @Override
    public ArrayList<Enquiry> getEnquirySubmitted() {
        return enquirySubmitted;
    }

    public void setEnquirySubmitted(Enquiry enquiry) {
        enquirySubmitted.add(enquiry);
    }

 // New method to display registered camps for a specific student
    public static void displayRegisteredCamps(Attendee attendee) {
    	int borderLength = 53;  // Adjust the border length based on your preference

    	// Print the decorative border
    	System.out.println("=".repeat(borderLength));

    	// Print the STAFF header
    	System.out.println("                        STUDENT                       ");

    	// Print staff information
    	System.out.println("               Name: " + attendee.getName());
    	System.out.println("               ID: " + attendee.getStudentID());

    	// Print the decorative border again
    	System.out.println("=".repeat(borderLength));
    	System.out.println("================DISPLAY REGISTERED CAMPS================");

    	
        if (attendee.getRegisteredCamps().isEmpty()) {
            // Print a message when the attendee has not registered for any camps
            System.out.println("You have not registered for any camps.");
            return;
        } else {
            // Print information for each registered camp
            System.out.println("Registered Camps:");
            for (Camp camp : attendee.getRegisteredCamps()) {
                Camp.printAllCampInfo(camp);
            }
        }
    }

	    

    public static void manageCamp(Attendee attendee) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        do {
        	int borderLength = 53;  // Adjust the border length based on your preference

        	// Print the decorative border
        	System.out.println("=".repeat(borderLength));

        	// Print the STAFF header
        	System.out.println("                        STUDENT                       ");

        	// Print staff information
        	System.out.println("               Name: " + attendee.getName());
        	System.out.println("               ID: " + attendee.getStudentID());

        	// Print the decorative border again
        	System.out.println("=".repeat(borderLength));
        	System.out.println("================MANAGE CAMPS MENU===============");

            // Display menu options
            System.out.println("1. View Camps");
            System.out.println("2. Register for a Camp. Note: if you do not know the camp name, please select view camps to check the camp name first");
            System.out.println("3. Withdraw from a Camp");
            System.out.println("4. Back to Main Menu");

            int choice = getNumericInput(scanner);

            switch (choice) {
                case 1:
                    // Delegate to specific functionality in the Attendee class
                    viewCamp(attendee);
                    break;
                case 2:
                    // Delegate to specific functionality in the Attendee class
                    registerForCamp(attendee);
                    break;
                case 3:
                    // Delegate to specific functionality in the Attendee class
                    withdrawFromCamp(attendee);
                    break;
                case 4:
                    exit = true;
                    Attendee_Account attendee_Account = new Attendee_Account(attendee.studentID, attendee.existingStudents);

                    // Start the Attendee_Account functionality
                    attendee_Account.start();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (!exit);
    }

   
    public static int getNumericInput(Scanner scanner) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter your choice: ");
                String inputLine = scanner.nextLine().trim();

                if (inputLine.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                } else {
                    input = Integer.parseInt(inputLine);
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return input;
    }


    public static void viewCamp(Attendee attendee) {
    	int borderLength = 53;  // Adjust the border length based on your preference

    	// Print the decorative border
    	System.out.println("=".repeat(borderLength));

    	// Print the STAFF header
    	System.out.println("                        STUDENT                       ");

    	// Print staff information
    	System.out.println("               Name: " + attendee.getName());
    	System.out.println("               ID: " + attendee.getStudentID());

    	// Print the decorative border again
    	System.out.println("=".repeat(borderLength));
    	System.out.println("================VIEW ALL CAMP================");

        try {
            ArrayList<Camp> campArrayList = DisplayApp.viewAllCamp();

            if (campArrayList == null || campArrayList.isEmpty()) {
                System.out.println("No camp available to view");
                manageCamp(attendee);
                return; // Return to avoid further execution
            }

            ArrayList<Camp> eligibleCamp = new ArrayList<>();
            for (Camp camp : campArrayList) {
                if (camp != null && camp.getVisibility() && (camp.getUserGroup().contains(Faculty.ALL) || camp.getUserGroup().contains(attendee.getFaculty()))) {
                    eligibleCamp.add(camp);
                }
            }

            if (eligibleCamp.isEmpty()) {
                System.out.println("No eligible camp available to view, exiting...");
                manageCamp(attendee);
                return; // Return to avoid further execution
            }

            for (Camp camp : eligibleCamp) {
                Camp.printAllCampInfo(camp);
            }
            // You can add more code here if needed
        } catch (NullPointerException e) {
            System.out.println("An error occurred while retrieving camp data. Exiting to main menu.");
            manageCamp(attendee);
        }
    }



    public static void registerForCamp(Attendee attendee) {
        Scanner input = new Scanner(System.in);

        System.out.println("Do you know the camp name of the camp that you want to register for?");
        System.out.println("Enter \"Yes\" or any other key for no");

        try {
            String campNameKnown = input.next().toUpperCase().trim(); // Convert input to uppercase

            switch (campNameKnown) {
                case "YES":
                    // Continue with registration process
                    Camp campToRegister;

                    do {
                        System.out.println("Please enter the camp name EXACTLY as it is displayed, or enter \" exit \" to go back");
                        String campName = input.nextLine();

                        if (campName.equalsIgnoreCase("exit")) {
                            System.out.println("Action terminated by the user, exiting...");
                            manageCamp(attendee);
                            return;
                        }

                        campToRegister = Camp.getCampByName(campName);

                        if (campToRegister == null) {
                            System.out.println("Camp not found, please check the camp name");
                        } else {
                            System.out.println("Enter \"confirm\" to register or enter \"0\" to cancel");
                            String confirm = input.next();

                            if (confirm.equalsIgnoreCase("confirm") || confirm.equals("1")) {
                                attendee.setRegisteredCamps(campToRegister);
                                campToRegister.setAttendeesRegistered(attendee);
                                int originalSlot = campToRegister.getRemainingSlots();
                                campToRegister.setRemainingSlots(originalSlot - 1);
                                System.out.println("Camp registration successful");
                                
                                attendee = Attendee.attendeesMap.get(attendee.getStudentID());
                                
                                attendee.setRegisteredCamps(campToRegister);
                                // Update the CSV file for attendee
    	                        CSVDataManager.updateAttendeeCSVFile(attendee);
    	                        
    	                        Camp camp = Camp.getCampMap().get(campName);
    	                        camp.setAttendeesRegistered(attendee);
    	                        
    	                        // Update the CSV file for Camp;
    	                        CSVDataManager.updateCampCSVFile(camp);
    	                        
                            } else {
                                System.out.println("Action canceled by the user, exiting...");
                                manageCamp(attendee);
                                return;
                            }
                        }
                    } while (campToRegister == null);

                    break;
                default:
                    System.out.println("Please view all camps to check the camp name first");
                    manageCamp(attendee);
                    return;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input. Exiting...");
            manageCamp(attendee);
            return;
        }
    }

    public static int getConfirmationInput(Scanner scanner) {
        int input = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter your choice (1 to confirm, 0 to cancel): ");
                input = Integer.parseInt(scanner.nextLine().trim());

                if (input == 0 || input == 1) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter either 1 to confirm or 0 to cancel.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return input;
    }


    public static void withdrawFromCamp(Attendee attendee) {
        Scanner input = new Scanner(System.in);

        try {
            if (attendee.getRegisteredCamps().isEmpty()) {
                System.out.println("You have not registered any camp, cannot withdraw. Exiting...");
                manageCamp(attendee);
                return;
            }

            for (Camp camp : attendee.getRegisteredCamps()) {
                Camp.printAllCampInfo(camp);
            }

            System.out.println("Which camp do you want to withdraw from?");
            Camp campToWithdraw;

            do {
                System.out.println("Please enter the camp name EXACTLY as it is displayed, or enter \"exit\" to go back");
                String campName = input.nextLine().trim().toUpperCase(); // Trim and convert input to uppercase

                if (campName.equalsIgnoreCase("exit")) {
                    System.out.println("Action terminated by the user, exiting...");
                    manageCamp(attendee);
                    return;
                }

                campToWithdraw = Camp.getCampByName(campName);

                if (campToWithdraw == null) {
                    System.out.println("Camp not found, please check the camp name");
                } else {
                    System.out.println("Enter \"confirm\" to withdraw or enter any key to go back");
                    String confirm = input.next().trim().toUpperCase(); // Trim and convert input to uppercase

                    if (confirm.equals("CONFIRM")) {
                    	  // Remove the camp from the attendee
                        attendee.getRegisteredCamps().remove(campToWithdraw);

                        // Remove the attendee from the camp
                        campToWithdraw.getAttendeesRegistered().remove(attendee);

                        // Update remaining slots for the camp
                        int originalSlots = campToWithdraw.getRemainingSlots();
                        campToWithdraw.setRemainingSlots(originalSlots + 1);

                        // Update the CSV file for the attendee
                        CSVDataManager.updateAttendeeCSVFile(attendee);

                        // Update the CSV file for the camp
                        CSVDataManager.updateCampCSVFile(campToWithdraw);
                        
                        System.out.println("Camp withdrawal successful");
                    } else {
                        System.out.println("Action terminated by the user, exiting...");
                        manageCamp(attendee);
                        return;
                    }
                }
            } while (campToWithdraw == null);
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input. Exiting...");
            manageCamp(attendee);
            return;
        }
    }


    /**************** FOR ENQUIRIES ***********************************/
    
    public static void manageEnquiries(Attendee attendee) {
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
    	System.out.println("================STUDENT - CAMP ENQUIRIES MENU================");

    	
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("1. View Enquiries");
            System.out.println("2. Make Enquiries");
            System.out.println("3. Edit Enquiries");
            System.out.println("4. Delete Enquiries");
            System.out.println("5. Back to Main Menu");

            int choice = getUserChoiceWithExceptionHandling(scanner);

            switch (choice) {
                case 1:
                    // Delegate to specific functionality in the Attendee class
                    Attendee.viewEnquiry(attendee);
                    break;
                case 2:
                    // Delegate to specific functionality in the Attendee class
                    Attendee.makeEnquiry(attendee);
                    break;
                case 3:
                    Attendee.editEnquiry(attendee);
                    break;
                case 4:
                    // Delegate to specific functionality in the Attendee class
                    Attendee.deleteEnquiry(attendee);
                    break;
                case 5:
                    // Back to the main menu
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (!exit);
    }

    private static int getUserChoiceWithExceptionHandling(Scanner scanner) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter your choice: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                choice = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return choice;
    }

    
    public static void viewEnquiry(Attendee attendee) {
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
    	System.out.println("================STUDENT - VIEW ENQURIY ================");

    	
    	
        if(attendee.getEnquirySubmitted().isEmpty()){
            System.out.println("No enquiry has been submitted");
            manageEnquiries(attendee);
            return;
        }

        for(Enquiry enquiry : attendee.getEnquirySubmitted()){
            Enquiry.printAllEnquiryInfo(enquiry);
        }
        
        manageEnquiries(attendee);
    }

    public static void makeEnquiry(Attendee attendee){
    	
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
    	System.out.println("================STUDENT - MAKE ENQUIRIES MENU================");

    	
    	
        System.out.println("Please firstly select the camp that you want to submit the enquiry to");
        System.out.println("Please note down the camp name of the interested camp");
        ArrayList<Camp> campArrayList = DisplayApp.viewAllCamp();
        if(campArrayList == null){
        	manageEnquiries(attendee);
            return;
        }
        ArrayList<Camp> eligibleCamp = new ArrayList<>();
        for(Camp camp : campArrayList){
            if(camp.getVisibility() && (camp.getUserGroup().contains(Faculty.ALL) || camp.getUserGroup().contains(attendee.getFaculty()))){
                eligibleCamp.add(camp);
            }
        }
        if(eligibleCamp.isEmpty()){
            System.out.println("No camp available to view, exiting...");
            manageEnquiries(attendee);
            return;
        }

        for(Camp camp : eligibleCamp){
            Camp.printAllCampInfo(camp);
            System.out.println();
        }

        Scanner input = new Scanner(System.in);
        Camp campToEnquire;
        do {
            System.out.println("Please enter the camp name, that you want to enquire,EXACTLY as it is displayed, or enter \" exit \" to go back");
            String campName = input.nextLine();
            if(campName.equalsIgnoreCase("exit")){
                System.out.println("Action terminated by the user, exiting...");
                manageEnquiries(attendee);
                return;
            }
            campToEnquire = Camp.getCampByName(campName);
            if(campToEnquire == null){
                System.out.println("Camp not found, please check the camp name");
            }else{
                System.out.println("Enter \"confirm\" to choose the selected camp or any key to go back: ");
                String confirm = input.next();
                if(!confirm.equalsIgnoreCase("confirm")){
                    System.out.println("Action terminated by the user, exiting...");
                    manageEnquiries(attendee);
                    return;
                }
                System.out.println("you are about to submit an enquiry for the camp: " + campName);
            }
        }while(campToEnquire == null);

        String subject;
        boolean uniqueSubject; // a do-while loop to make sure that the subject name is unique
        do {System.out.println("Insert an unique enquiry subject and try to keep it short, or \"exit\"  to exit");
            subject = input.nextLine();
            if(subject.equals("exit")){
                System.out.println("Action terminated by the user, exiting...");
                manageEnquiries(attendee);
                return;
            }
            if(Enquiry.getEnquiryHashMap().containsKey(subject)){
                System.out.println("This name has already been taken, please have an unique subject name");
                uniqueSubject = false;
            }else{
                uniqueSubject = true;
            }
        }while(!uniqueSubject);

        System.out.print("Enter your enquiry content or \"exit\" in to exit");
        String content = input.nextLine();
        if(content.equalsIgnoreCase("exit")){
            System.out.println("Action terminated by the user..., exiting");
            manageEnquiries(attendee);
            return;
        }
        LocalDate todayDate = LocalDate.now();
        Enquiry newEnquiry = new Enquiry(subject, content, todayDate, attendee.getName(), null, false);

        Enquiry.getEnquiryHashMap().put(subject, newEnquiry); //add enquiry to hashmap
        Enquiry.setEnquiryArrayList(newEnquiry); //add enquiry to the total enquiry
        attendee.setEnquirySubmitted(newEnquiry); //add enquiry to the attendee
        campToEnquire.setEnquiry(newEnquiry); // add enquiry to the camp
        System.out.println("The enquiry has been sent successfully");
        CSVDataManager.updateEnquiryCSVFile(newEnquiry);
        CSVDataManager.updateAttendeeCSVFile(Attendee.attendeeToNameMap.get(newEnquiry.getSubmitter()));
        CSVDataManager.updateCampCSVFile(campToEnquire);
        manageEnquiries(attendee);
    }

    public static void editEnquiry(Attendee attendee){
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
    	System.out.println("================STUDENT - EDIT ENQUIRIE MENU================");

    	
    	
        Scanner input = new Scanner(System.in);
        // if no enquiry has been submitted, exit.
        if(attendee.getEnquirySubmitted().isEmpty()){
            System.out.println("No enquiry has been submitted");
            System.out.println("exiting...");
            manageEnquiries(attendee);
            return;
        }
        //print all submitted enquiry.
        System.out.println("These are the enquiries that are yet to be processed");
        for(Enquiry enquiry : attendee.getEnquirySubmitted()){
            if(!enquiry.isProcessed()){
                Enquiry.printAllEnquiryInfo(enquiry);
            }
        }


        Enquiry enquiryToBeEdit;
        boolean exitEnquiryEdit = false;
        do {
            System.out.println("Please select the enquiry that you want to edit by SUBJECT or enter exit to go back");
            String enquiryToBeEditStr = input.nextLine();
            if (enquiryToBeEditStr.equalsIgnoreCase("exit")) {
                System.out.println("Action terminated by user");
                System.out.println("exiting...");
                manageEnquiries(attendee);
                return;
            }
            enquiryToBeEdit = Enquiry.getEnquiryBySubject(enquiryToBeEditStr);
            if (enquiryToBeEdit == null) {
                System.out.println("Please insert the correct subject name");
            }
        } while (enquiryToBeEdit == null);

        if (enquiryToBeEdit.isProcessed()) {
            System.out.println("Cannot be edited, the enquiry has been processed");
            System.out.println("exiting...");
            manageEnquiries(attendee);
            return;
        }

        boolean exitEditing = false;

        outerLoop:do {
            System.out.println("What do you want to edit?");
            System.out.println("1. Subject");
            System.out.println("2. Content");
            System.out.println("3. Exit");
            int editAttr = input.nextInt();
            switch (editAttr) {
                case (1):
                    System.out.println("The original subject is " + enquiryToBeEdit.getEnquiry_Subject());
                    String newSubject;
                    boolean uniqueSubject = true; // a do-while loop to make sure that the subject name is unique
                    do {
                        System.out.println("Insert an unique subject of the suggestion or \"exit\"  to go back");
                        newSubject = input.nextLine();
                        if (newSubject.equals("exit")) {
                            System.out.println("Action terminated by the user");
                            System.out.println("exiting...");
                            break outerLoop;
                        }
                        if (Enquiry.getEnquiryHashMap().containsKey(newSubject)) {
                            System.out.println("This name has already been taken, please have an unique subject name");
                            uniqueSubject = false;
                        } else {
                            uniqueSubject = true;
                        }
                    } while (!uniqueSubject);

                    System.out.println("Your new subject is: " + newSubject);
                    System.out.println("Enter confirm or any other key to cancel");
                    if (input.nextLine().equalsIgnoreCase("confirm")) {
                       Camp campofEnquiry = null;

                        for(Camp camp: campData.getCampList()){
                            for(Enquiry enquiry : camp.getEnquiry()){
                                if(enquiry.getEnquiry_Subject().equals(enquiryToBeEdit.getEnquiry_Subject())){
                                    campofEnquiry = camp;
                                    break;
                                }
                            }
                            if(campofEnquiry != null) break;
                        }

                        Enquiry.getEnquiryHashMap().remove(enquiryToBeEdit.getEnquiry_Subject());

                        enquiryToBeEdit.setEnquiry_Subject(newSubject);
                        Enquiry.getEnquiryHashMap().put(newSubject, enquiryToBeEdit);
                        CSVDataManager.updateEnquiryCSVFile(enquiryToBeEdit);
                        CSVDataManager.updateCampCSVFile(campofEnquiry);

                        System.out.println("Suggestion subject edited successfully");
                    } else {
                        System.out.println("Action terminated by user");
                        System.out.println("exiting...");
                        break outerLoop;
                    }
                    break;
                case (2):
                    String newContent;
                    System.out.println("The original enquiry is: " + enquiryToBeEdit.getContent());
                    System.out.println("Please insert the new content for the enquiry within the same line");
                    newContent = input.nextLine();
                    System.out.println("Enter confirm or any other key to cancel");
                    if (input.nextLine().equalsIgnoreCase("confirm")) {
                        enquiryToBeEdit.setContent(newContent);
                        CSVDataManager.updateEnquiryCSVFile(enquiryToBeEdit);
                        System.out.println("Enquiry content edited successfully");
                    } else {
                        System.out.println("Action terminated by user");
                        System.out.println("exiting...");
                        break outerLoop;
                    }
                    break;
                case (3):
                    System.out.println("Action terminated by user");
                    exitEditing = true;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (!exitEditing);
    }

    public static void deleteEnquiry(Attendee attendee) {
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
    	System.out.println("================DELETE ENQUIRIES- CAMP ENQUIRIES MENU================");

    	
    	
    	
        Scanner input = new Scanner(System.in);

        try {
            // if no enquiry has been submitted, exit.
            if (attendee.getEnquirySubmitted().isEmpty()) {
                System.out.println("No enquiry has been submitted");
                System.out.println("Exiting...");
                manageEnquiries(attendee);
                return;
            }

            // Print all submitted enquiries.
            System.out.println("These are the enquiries that are yet to be processed");
            for (Enquiry enquiry : attendee.getEnquirySubmitted()) {
                if (!enquiry.isProcessed()) {
                    Enquiry.printAllEnquiryInfo(enquiry);
                }
            }

            Enquiry enquiryToDelete;

            do {
                System.out.println("Please select the enquiry that you want to delete by SUBJECT or enter \"exit\" to go back");
                String enquiryToBeDelStr = input.nextLine();
                if (enquiryToBeDelStr.equals("exit")) {
                    System.out.println("Action terminated by user, exiting...");
                    manageEnquiries(attendee);
                    return;
                }

                enquiryToDelete = Enquiry.getEnquiryHashMap().get(enquiryToBeDelStr);

                if (enquiryToDelete == null) {
                    System.out.println("Please insert the correct subject name");
                }
            } while (enquiryToDelete == null);

            if (enquiryToDelete.isProcessed()) {
                System.out.println("Cannot be deleted, the suggestion has been processed. Exiting...");
                manageEnquiries(attendee);
                return;
            }

            System.out.println("Do you confirm to delete the suggestion: " + enquiryToDelete.getEnquiry_Subject());
            System.out.println("Enter \"confirm\" or any other key to cancel");

            if (input.nextLine().equalsIgnoreCase("confirm")) {
                attendee.getEnquirySubmitted().remove(enquiryToDelete); // delete in attribute
                Enquiry.getEnquiryArrayList().remove(enquiryToDelete); // delete in arraylist
                Enquiry.getEnquiryHashMap().remove(enquiryToDelete.getEnquiry_Subject()); // delete in hashmap

                CSVDataManager.updateAttendeeCSVFile(attendee);
                for(Enquiry enquiry : Enquiry.getEnquiryArrayList()){
                    CSVDataManager.updateEnquiryCSVFile(enquiry);
                }

                for (Camp camp : attendee.getRegisteredCamps()) {
                    if (camp.getEnquiry().contains(enquiryToDelete)) {
                        camp.getEnquiry().remove(enquiryToDelete);
                        CSVDataManager.updateCampCSVFile(camp);
                    }
                } // delete in the camp that has it

                System.out.println("Enquiry deleted successfully, exiting...");
                manageEnquiries(attendee);
            } else {
                System.out.println("Action terminated by user, exiting");
                manageEnquiries(attendee);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input. Exiting...");
            manageEnquiries(attendee);
        }
    }


    }
