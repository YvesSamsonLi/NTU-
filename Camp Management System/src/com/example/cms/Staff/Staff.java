package com.example.cms.Staff;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Camp.Camp;
import com.example.cms.Camp.campData;
import com.example.cms.DisplayOptions.*;
import com.example.cms.Enquiries.Enquiry;
import com.example.cms.Faculty;
import com.example.cms.Format;
import com.example.cms.Status;
import com.example.cms.Student.Committee;
import com.example.cms.Suggestions.Suggestion;
import com.example.cms.generate_report.GenerateReport;
import com.example.cms.generate_report.StaffGenerateReport;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
 * When the Staff user chooses an option from the menu, they will be directed to this class to complete the required functions.
 */
public class Staff extends Staff_User{

	public Staff(String staffID, String name, Faculty userGroup, String securityQuestion, String securityAns){
		super(staffID, name, userGroup, securityQuestion, securityAns);
		existingStaff.put(staffID, this);
	}

	 private static Scanner input = new Scanner(System.in);

	protected void createCamp() {
    	// Add new lines for the appearance of entering a new page
        System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

        int borderLength = 53;  // Adjust the border length based on your preference

        // Print the decorative border
        System.out.println("=".repeat(borderLength));

        // Print the STAFF header
        System.out.println("                        STAFF                        ");

        // Print staff information
        System.out.println("               Name: " + this.getName());
        System.out.println("               ID: " + this.getStaffID());

        // Print the decorative border again
        System.out.println("=".repeat(borderLength));

        // Beautiful println statement for the Camp Management Menu
        System.out.println("================CAMP MANAGEMENT MENU================");
 
	    Scanner scanner = new Scanner(System.in);
	    input = new Scanner(System.in);

	    // Get a unique camp name
	    String campName = null;
	    boolean uniqueCampName;

	    // Loop until a unique and valid camp name is entered
	    do {
	        System.out.println("Please insert the new camp name that is unique:");
	        String campNameInput = input.nextLine().trim().toUpperCase();

	        // Validate camp name (only letters and spaces allowed)
	        if (campNameInput.matches("^[a-zA-Z\\s]+$")) {
	            campName = campNameInput;
	            uniqueCampName = campData.getCampList().stream().noneMatch(camp -> camp.getCampName().equalsIgnoreCase(campNameInput));
	            if (!uniqueCampName) {
	                System.out.println("This name has already been taken, please choose a unique camp name.");
	            }
	        } else {
	            System.out.println("Invalid characters in the camp name. Please use only letters and spaces.");
	            uniqueCampName = false;
	        }
	    } while (!uniqueCampName);

	    System.out.println("Camp name: " + campName);

	    
	    // Get and validate start date
	    LocalDate startDate = getDateFromUser("Please insert the starting date of the camp in the format yyyy-mm-dd:");

	    // Get and validate end date
	    LocalDate endDate = getDateFromUser("Please insert the ending date of the camp in the format yyyy-mm-dd:");

	    // Get and validate registration close date
	    LocalDate regCloseDate = getDateFromUser("Please insert the registration close date of the camp in the format yyyy-mm-dd:");

	    // Determine user groups
	    ArrayList<Faculty> userGroups = getUserGroups();

	    // Get location
	    System.out.println("Nearly there... Insert the location of the camp:");
	    String location = input.nextLine().trim().toUpperCase();
	    System.out.println("Location: " + location);

	    // Get total slots
	    int slots = getNumericInput("Enter the total slot of the attendee:");
	    System.out.println("The total slot is " + slots);

	    // Get visibility
	    boolean visibility = getVisibility();

	    ArrayList<LocalDate> campDate = new ArrayList<>();
	    campDate.add(startDate);
	    campDate.add(endDate);

	    // Create new camp
	    Camp newCamp = new Camp(campName, campDate, regCloseDate, userGroups, location, slots, slots, 10, 10, this.getName(), visibility);

	    // Update camp data
	    campData.setCampList(newCamp);
	    campData.addCampToMap(campName, newCamp);
	    this.setCampsCreated(newCamp);

	    // Update CSV file for the new camp
	    CSVDataManager.updateCampCSVFile(newCamp);
	    CSVDataManager.updateStaffCSVFile(this);
	}

	private LocalDate getDateFromUser(String message) {
	    LocalDate date = null;
	    do {
	        System.out.println(message);
	        String dateString = input.nextLine().trim();
	        try {
	            date = LocalDate.parse(dateString);
	            System.out.println("Date: " + date);
	        } catch (DateTimeParseException e) {
	            System.out.println("Please insert the date in the correct format: yyyy-mm-dd");
	        }
	    } while (date == null);
	    return date;
	}

	private ArrayList<Faculty> getUserGroups() {
	    ArrayList<Faculty> userGroups = new ArrayList<>();
	    boolean continueInput = true;
	    String answer;

	    do {
	        // Ask the user to insert the school/faculty
	        System.out.println("Please insert the school/faculty that has access to the camp (enter one at a time):");

	        // Read user input for one faculty at a time
	        String userGroupInput = input.nextLine().trim();

	        // Check if the entered faculty is valid
	        if (isValidFaculty(userGroupInput)) {
	            // Add the valid faculty to the userGroups list
	            userGroups.add(Faculty.valueOf(userGroupInput.toUpperCase()));
	        } else {
	            System.out.println("Invalid input. The entered faculty is not valid.");
	        }

	        // Display the faculties that can view and register
	        if (!userGroups.isEmpty()) {
	            System.out.println("The following school/faculty can view and register: " + joinFaculties(userGroups));
	        }

	        // Ask if the user wants to continue entering faculties
	        do {
	            System.out.println("\nDo you want to enter another faculty? (Yes/No):");
	            answer = input.nextLine().trim();
	            continueInput = answer.equalsIgnoreCase("Yes");
	        } while (!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No"));

	    } while (continueInput);

	    return userGroups;
	}

	// Check if the entered faculty is valid
	private boolean isValidFaculty(String facultyInput) {
	    for (Faculty enumUserGroup : Faculty.values()) {
	        if (facultyInput.equalsIgnoreCase(enumUserGroup.name())) {
	            return true;
	        }
	    }
	    return false;
	}

	// Join multiple faculties with '|'
	private String joinFaculties(ArrayList<Faculty> faculties) {
	    return faculties.stream().map(Enum::name).collect(Collectors.joining("|"));
	}


	private int getNumericInput(String message) {
	    int inputNumber = 0;
	    boolean validInput = false;

	    while (!validInput) {
	        try {
	            System.out.println(message);
	            String inputLine = input.nextLine().trim();

	            if (!inputLine.isEmpty()) {
	                inputNumber = Integer.parseInt(inputLine);
	                validInput = true;
	            } else {
	                System.out.println("Invalid input. Please enter a valid number.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	        }
	    }

	    return inputNumber;
	}

	private boolean getVisibility() {
	    boolean visibility;
	    do {
	        System.out.println("Enter the visibility of the camp: On or Off");
	        String visibilityStr = input.next().trim();
	        visibility = visibilityStr.equalsIgnoreCase("On") || visibilityStr.equalsIgnoreCase("Off");
	    } while (!visibility);
	    return visibility;
	}

	protected void viewCampCreated(Staff staff) {
	    // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Camp Management Menu
	    System.out.println("================VIEW CAMP CREATED MENU================");

	    if (staff.getCampsCreated().isEmpty()) {
	        System.out.println("No camp has been created, please create one before editing");
	    } else {
	        System.out.println("Please view the camp that you have created with the following ways:");
	        System.out.println("1. Default: alphabetical order of camp name");
	        System.out.println("2. Search for keywords");
	        System.out.println("3. Sort by camp features");

	        // Get user choice with input validation
	        int choice = getUserChoice();
	        switch (choice) {
	            case (1):
	                // Sort camps by name in default order
	                DisplayBySort aOb = new SortByName_Default();
	                ArrayList<Camp> sorted = aOb.Sorting(staff.getCampsCreated());
	                for (Camp camp : sorted) {
	                    Camp.printAllCampInfo(camp);
	                }
	                break;
	            case (2):
	                // Search camps based on user-specified keywords
	                ArrayList<Camp> afterSearchCamp;
	                do {
	                    afterSearchCamp = SearchApp.startSearch(staff.getCampsCreated());
	                    if (afterSearchCamp == null) {
	                        System.out.println("Please choose a correct option");
	                    } else {
	                        for (Camp camp : afterSearchCamp) {
	                            Camp.printAllCampInfo(camp);
	                        }
	                    }
	                } while (afterSearchCamp == null);
	                break;
	            case (3):
	                // Sort camps based on user-specified features
	                ArrayList<Camp> afterSortCamp;
	                do {
	                    afterSortCamp = SortApp.startSorting(staff.getCampsCreated());
	                    if (afterSortCamp == null) {
	                        System.out.println("Please choose a correct option");
	                    } else {
	                        for (Camp camp : afterSortCamp) {
	                            Camp.printAllCampInfo(camp);
	                        }
	                    }
	                } while (afterSortCamp == null);
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	        }
	    }
	}

	// Get user choice with input validation
	private int getUserChoice() {
	    int choice;
	    do {
	        try {
	            System.out.print("Enter your choice: ");
	            choice = input.nextInt();
	            if (choice < 1 || choice > 3) {
	                System.out.println("Invalid input. Please enter a valid option (1-3).");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid integer.");
	            input.nextLine(); // Consume the invalid input
	            choice = -1; // Set a default value or use a flag to handle the loop
	        }
	    } while (choice < 1 || choice > 3);

	    return choice;
	}

	
	private Camp selectCamp(ArrayList<Camp> campArrayList) {
	    String campToBeSelectedStr;
	    Camp campToBeSelected;

	    // Continue prompting until a valid camp is selected or the user cancels the selection
	    while (true) {
	        try {
	            // Prompt the user to insert the camp name or enter 'exit' to cancel
	            System.out.println("Please insert the camp name or enter 'exit' to cancel (in lower case):");
	            campToBeSelectedStr = input.nextLine().trim().toUpperCase(); // Capitalize and trim the input

	            // Check if the user wants to cancel the selection
	            if (campToBeSelectedStr.equals("EXIT")) {
	                return null;
	            }

	            // Check if the camp name contains special characters
	            if (!campToBeSelectedStr.matches("[A-Z0-9 ]+")) {
	                throw new IllegalArgumentException("Invalid characters in the camp name. Please use only letters, numbers, and spaces.");
	            }

	            // Get the camp based on the user input
	            campToBeSelected = campData.getCampHashMap().get(campToBeSelectedStr);

	            // Check if the selected camp exists
	            if (campToBeSelected == null) {
	                System.out.println("The camp does not exist. Please verify the CAMP NAME.");
	                continue; // Continue the loop to prompt the user again
	            }

	            // Exit the loop if the input is valid
	            break;
	        } catch (IllegalArgumentException e) {
	            // Handle the case where the camp name contains invalid characters
	            System.out.println(e.getMessage());
	        } catch (Exception e) {
	            // Handle unexpected errors and prompt the user to try again
	            System.out.println("An unexpected error occurred. Please try again.");
	        } finally {
	            // Consume the input to prevent an infinite loop
	            input.nextLine();
	        }
	    }

	    // Return the selected camp
	    return campToBeSelected;
	}


	public void editCamp(Staff staff) {
	    // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Edit Camp
	    System.out.println("================EDIT CAMP MENU================");

	    Scanner input = new Scanner(System.in);

	    viewCampCreated(staff);
	    boolean completedEditing = false;

	    do {
	        Camp campToBeEdited = selectCamp(staff.getCampsCreated());
	        if (campToBeEdited == null) {
	            System.out.println("Action terminated, exiting...");
	            return;
	        }

	        System.out.println("====What camp information would you like to edit====");
	        System.out.println("1. Camp name");
	        System.out.println("2. Camp location");
	        System.out.println("3. Camp start date");
	        System.out.println("4. Camp end date");
	        System.out.println("5. Camp registration close date");
	        System.out.println("6. Camp total slots");
	        System.out.println("7. Camp eligible user group");
	        System.out.println("8. Exiting");

	        int thingsToEdit;
	        try {
	            thingsToEdit = input.nextInt();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid integer.");
	            input.nextLine(); // Consume the invalid input
	            continue;
	        }

	        switch (thingsToEdit) {
	        case 1:
	            // Initialize a variable to store the new camp name
	            String newCampName;

	            // Continue prompting the user until a valid camp name is provided
	            do {
	                // Prompt the user to enter the new camp name
	                System.out.println("Enter the new camp name");
	                newCampName = input.nextLine().trim().toUpperCase();

	                // Check if the input is null or contains special characters
	                if (newCampName == null || !newCampName.matches("^[A-Z0-9 ]+$")) {
	                    System.out.println("Invalid input. Please enter a valid camp name without special characters.");
	                }
	            } while (newCampName == null || !newCampName.matches("^[A-Z0-9 ]+$"));

	            // Get the old camp name, remove it from the map, and update with the new camp name
	            String oldCampName = campToBeEdited.getCampName();
	            campData.getCampHashMap().remove(oldCampName);
	            campToBeEdited.setCampName(newCampName);
	            campData.addCampToMap(newCampName, campToBeEdited);

	            // Display the updated camp name
	            System.out.println("The new camp name is now: " + campToBeEdited.getCampName());
	            break;


	        case 2:
	            // Initialize a variable to store the new camp location
	            String newLocation;

	            // Continue prompting the user until a valid location is provided
	            do {
	                // Prompt the user to enter the new camp location
	                System.out.println("Enter the new camp location");
	                newLocation = input.nextLine().trim().toUpperCase();;

	                // Check if the input is null or contains special characters
	                if (newLocation == null || !newLocation.matches("^[a-zA-Z0-9 ]+$")) {
	                    System.out.println("Invalid input. Please enter a valid location without special characters.");
	                }
	            } while (newLocation == null || !newLocation.matches("^[a-zA-Z0-9 ]+$"));
	            
	            // Set the new location for the camp
	            campToBeEdited.setLocation(newLocation);

	            // Display the updated camp location
	            System.out.println("The new camp location is now: " + campToBeEdited.getLocation());

	            break;

	        case 3:
	            // Initialize a variable to hold the new camp start date
	            LocalDate newCampStartDate = null;

	            // Continue prompting until a valid date is entered
	            do {
	                // Prompt the user to insert the new camp start date
	                System.out.println("Please insert the new camp start date in the format yyyy-mm-dd");
	                String regCloseString = input.nextLine();

	                // Check if the input is not null and matches the expected format
	                if (regCloseString != null && regCloseString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	                    try {
	                        // Parse the input into a LocalDate object
	                        newCampStartDate = LocalDate.parse(regCloseString);

	                        // Set the new camp start date in the campToBeEdited object
	                        campToBeEdited.getCampDates().set(0, newCampStartDate);

	                        // Display the new camp start date
	                        System.out.println("New camp start date: " + campToBeEdited.getCampDates().get(0));
	                        System.out.println("Any other camp to edit? Yes or No");
	                    } catch (DateTimeParseException e) {
	                        // Handle the case where the input is not a valid date
	                        System.out.println("Invalid date. Please enter a valid date in the correct format: yyyy-mm-dd");
	                    }
	                } else {
	                    // Handle the case where the input does not match the expected format
	                    System.out.println("Invalid input. Please enter a valid date in the correct format: yyyy-mm-dd");
	                }
	            } while (newCampStartDate == null); // Continue looping until a valid date is entered
	            break;

	        case 4:
	            // Initialize a variable to hold the new camp end date
	            LocalDate newCampEndDate = null;

	            // Continue prompting until a valid date is entered
	            do {
	                // Prompt the user to insert the new camp end date
	                System.out.println("Please insert the new camp end date in the format yyyy-mm-dd");
	                String newCampEndDateString = input.nextLine();

	                // Check if the input is not null and matches the expected format
	                if (newCampEndDateString != null && newCampEndDateString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	                    try {
	                        // Parse the input into a LocalDate object
	                        newCampEndDate = LocalDate.parse(newCampEndDateString);

	                        // Set the new camp end date in the campToBeEdited object
	                        campToBeEdited.getCampDates().set(1, newCampEndDate);

	                        // Display the new camp end date
	                        System.out.println("New camp end date: " + campToBeEdited.getCampDates().get(1));
	                        System.out.println("Any other camp to edit? Yes or No");
	                    } catch (DateTimeParseException e) {
	                        // Handle the case where the input is not a valid date
	                        System.out.println("Invalid date. Please enter a valid date in the correct format: yyyy-mm-dd");
	                    }
	                } else {
	                    // Handle the case where the input does not match the expected format
	                    System.out.println("Invalid input. Please enter a valid date in the correct format: yyyy-mm-dd");
	                }
	            } while (newCampEndDate == null); // Continue looping until a valid date is entered
	            break;
	        case 5:
	            // Initialize a variable to hold the new registration close date
	            LocalDate newRegCloseDate = null;

	            // Continue prompting until a valid date is entered
	            do {
	                // Prompt the user to insert the new registration close date
	                System.out.println("Please insert the new camp registration close date in the format yyyy-mm-dd");
	                String newRegDateString = input.nextLine();

	                // Check if the input is not null and matches the expected format
	                if (newRegDateString != null && newRegDateString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	                    try {
	                        // Parse the input into a LocalDate object
	                        newRegCloseDate = LocalDate.parse(newRegDateString);

	                        // Set the new registration close date in the campToBeEdited object
	                        campToBeEdited.setRegCloseDate(newRegCloseDate);

	                        // Display the new registration close date
	                        System.out.println("New camp reg close date: " + campToBeEdited.getRegCloseDate());
	                        System.out.println("Any other camp to edit? Yes or No");
	                    } catch (DateTimeParseException e) {
	                        // Handle the case where the input is not a valid date
	                        System.out.println("Invalid date. Please enter a valid date in the correct format: yyyy-mm-dd");
	                    }
	                } else {
	                    // Handle the case where the input does not match the expected format
	                    System.out.println("Invalid input. Please enter a valid date in the correct format: yyyy-mm-dd");
	                }
	            } while (newRegCloseDate == null); // Continue looping until a valid date is entered
	            break;
	        case 6:
	            boolean eligibleSlot = false;

	            // Continue prompting until a valid integer is entered
	            do {
	                // Prompt the user to insert the new camp attendee slots
	                System.out.println("Enter the new camp attendee slots");

	                // Check if the next input is an integer
	                if (input.hasNextInt()) {
	                    int newAttendeeSlot = input.nextInt();

	                    // Check if the number of registered attendees has already passed the new slot number
	                    if (campToBeEdited.getAttendeesRegistered().size() > newAttendeeSlot) {
	                        System.out.println("The number of registered attendees has already passed the new slot number");
	                    } else {
	                        eligibleSlot = true;

	                        // Set the new camp attendee slots in the campToBeEdited object
	                        campToBeEdited.setRemainingSlots(newAttendeeSlot - campToBeEdited.getAttendeesRegistered().size());
	                        campToBeEdited.setTotalSlots(newAttendeeSlot);

	                        // Display the new camp attendee slots
	                        System.out.println("The new camp attendee slots is now: " + campToBeEdited.getTotalSlots());
	                        System.out.println("Any other camp to edit? Yes or No");
	                    }
	                } else {
	                    // Handle the case where the input is not an integer
	                    System.out.println("Invalid input. Please enter a valid integer for the new camp attendee slots.");
	                    input.next(); // Consume the invalid input to prevent an infinite loop
	                }
	            } while (!eligibleSlot); // Continue looping until a valid integer is entered
	            break;

	        case 7:
	            ArrayList<Faculty> userGroups = getUserGroups();

	            // Set the userGroups in campToBeEdited
	            campToBeEdited.setUserGroup(userGroups);
	            break;

	            case 8:
	                System.out.println("Action terminated by the user, exiting...");
	                return;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	                break;
	        }

	        String ans;
	        boolean validInput;
	        do {
	            // Prompt the user to input Yes or No
	            System.out.println("Any other camp to edit? Yes or No");
	            ans = input.nextLine().trim().toUpperCase(); // Trim and convert to uppercase

	            // Validate the input
	            validInput = ans.equals("YES") || ans.equals("NO");
	            
	            if (!validInput) {
	                System.out.println("Invalid input. Please enter Yes or No.");
	            }
	        } while (!validInput);

	        // Update the flag based on the user's response
	        completedEditing = ans.equals("NO");

	        // Update the camp CSV file if editing is completed
	        if (completedEditing) {
	            CSVDataManager.updateCampCSVFile(campToBeEdited);
	        }

	    } while (!completedEditing);

	    System.out.println("Edit successful!");
	    return;
	}

	public void deleteCamp(Staff staff) {
		  // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Edit Camp
	    System.out.println("================DELETE CAMP MENU================");

	    
	    if (staff.getCampsCreated().isEmpty()) {
	        System.out.println("No camp has been created, exiting");
	        return;
	    } else {
	        viewCampCreated(staff);
	        Camp campToBeDeleted = selectCamp(staff.getCampsCreated());
	        if (campToBeDeleted == null) {
	            return;
	        } else {
	            String campNameToDeleteStr = campToBeDeleted.getCampName();
	            if ((campToBeDeleted.getAttendeesRegistered().isEmpty() ||
	            	    campToBeDeleted.getAttendeesRegistered().get(0) == null ||
	            	    campToBeDeleted.getAttendeesRegistered().get(0).getName() == null ) &&(
	            	    campToBeDeleted.getCommitteeRegistered().isEmpty()||
	            	    campToBeDeleted.getCommitteeRegistered().get(0) == null ||
	            	    campToBeDeleted.getCommitteeRegistered().get(0).getName() == null )) {
	            	
	            	System.out.println("Confirm to delete Camp: " + campNameToDeleteStr + ". Insert Confirm or any other key");

	                // Ensure that the input is not null, does not contain special characters or numbers, and is capitalized
	                String confirmationInput = getValidInput();

	                if (confirmationInput.equals("CONFIRM")) {
	                    staff.getCampsCreated().remove(campToBeDeleted);
	                    campData.getCampList().remove(campToBeDeleted);
	                    campData.getCampHashMap().remove(campNameToDeleteStr);
	                    campToBeDeleted.setCampName(campNameToDeleteStr);
	                    CSVDataManager.updateStaffCSVFile(staff);

	                    System.out.println("Camp deleted successfully");
	                } else {
	                    System.out.println("Action terminated by user, exiting...");
	                }
	            } else {
	                System.out.println("There are already attendees or committee registered; the camp cannot be deleted.");
	                System.out.println("Action terminated, exiting...");
	            }
	        }
	    }
	}

	// Helper method to get valid input
	private String getValidInput() {
	    String userInput;

	    do {
	    	System.out.println("Please enter 'confirm' to delete");
	        userInput = input.nextLine();

	        // Check if the input is not null, does not contain special characters or numbers, and is capitalized
	        if (userInput != null &&
	            userInput.matches("^[a-zA-Z ]+$")) {
	            userInput = userInput.toUpperCase(); // Capitalize the input
	        } else {
	            System.out.println("Invalid input. Please enter a valid input without special characters or numbers.");
	        }
	    } while (userInput == null || !userInput.matches("^[a-zA-Z ]+$"));

	    return userInput;
	}



	protected void viewAllCamps() {
		  // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Edit Camp
	    System.out.println("================VIEW ALL CAMP================");

		ArrayList<Camp> campArrayList = DisplayApp.viewAllCamp();
		if(campArrayList == null){
			System.out.println("Action terminated");
		}else{
			for(Camp camp: campArrayList){
				Camp.printAllCampInfo(camp);
			}
		}

	}

	protected void viewEnquiry(Staff staff) {
		  // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Edit Camp
	    System.out.println("================VIEW ENQUIRY================");
	    
	    // Display the camps created by the staff
	    viewCampCreated(staff);

	    // Select a camp to view its enquiries
	    Camp campToViewEnquiry = selectCamp(staff.getCampsCreated());
	    if (campToViewEnquiry == null) {
	        // If no camp is selected, return from the method
	        return;
	    }

	    // Check if the selected camp has any enquiries
	    if (campToViewEnquiry.getEnquiry().isEmpty()) {
	        System.out.println("No enquiry has been submitted for this camp");
	        System.out.println("Exiting...");
	    } else {
	        // Display all enquiries for the selected camp
	        for (Enquiry enquiry : campToViewEnquiry.getEnquiry()) {
	            Enquiry.printAllEnquiryInfo(enquiry);
	        }
	    }
	}






	public void replyEnquiry(Staff staff) {
		
		  // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Reply Camp
	    System.out.println("================REPLY ENQUIRY================");
	    
	    
	    Scanner input = new Scanner(System.in);
	    
	    
	    
	    // Display the camps created by the staff
	    viewCampCreated(staff);
	    
	    // Initialize variables
	    Enquiry enquiryToReply = null;
	    Camp campToReplyEnquiry = selectCamp(staff.getCampsCreated());
	    
	    if (campToReplyEnquiry == null) {
	        // If no camp is selected, return from the method
	        return;
	    }
	    
	    // Check if the selected camp has any enquiries
	    if (campToReplyEnquiry.getEnquiry().isEmpty()) {
	        System.out.println("No enquiry has been submitted for this camp, exiting...");
	        return;
	    }

	    // Display all enquiries for the selected camp
	    for (Enquiry enquiry : campToReplyEnquiry.getEnquiry()) {
	        Enquiry.printAllEnquiryInfo(enquiry);
	    }

	    do {
	        // Prompt the user to select an enquiry by entering the subject
	        System.out.println("Which enquiry would you like to reply to? Insert enquiry SUBJECT. Enter \"exit\" to go back");
	        String enquirySubjectStr = input.nextLine().trim().toUpperCase();

	        // Check if the user wants to exit
	        if (enquirySubjectStr.equalsIgnoreCase("exit")) {
	            System.out.println("Action terminated by the user, exiting...");
	            return;
	        }

	        // Get the enquiry based on the subject
	        enquiryToReply = Enquiry.getEnquiryHashMap().get(enquirySubjectStr);

	        if (enquiryToReply == null) {
	            System.out.println("Please check the subject of the enquiry and try again");
	        }
	    } while (enquiryToReply == null);

	    // Display the selected enquiry content
	    System.out.println("The enquiry selected is: " + enquiryToReply.getContent());
	    System.out.println();

	    // Prompt the user to insert the reply
	    System.out.println("Insert your reply:");
	    String reply = input.nextLine().trim();

	    // Prompt the user to confirm or cancel the reply
	    System.out.println("Enter \"confirm\" to submit your reply or any other key to cancel");
	    
	    // Read the user's response
	    String confirmation = input.nextLine().trim();

	    if (confirmation.equalsIgnoreCase("confirm")) {
	        // Set the reply and update the CSV file
	        enquiryToReply.setReply(reply);
	        CSVDataManager.updateEnquiryCSVFile(enquiryToReply);
	    } else {
	        System.out.println("Action terminated by the user, exiting...");
	    }
	}

	
	public void viewSuggestion(Staff staff) {
		  // Add new lines for the appearance of entering a new page
	    System.out.println("\n".repeat(10));  // Adjust the number of new lines as needed

	    int borderLength = 53;  // Adjust the border length based on your preference

	    // Print the decorative border
	    System.out.println("=".repeat(borderLength));

	    // Print the STAFF header
	    System.out.println("                        STAFF                        ");

	    // Print staff information
	    System.out.println("               Name: " + this.getName());
	    System.out.println("               ID: " + this.getStaffID());

	    // Print the decorative border again
	    System.out.println("=".repeat(borderLength));

	    // Beautiful println statement for the Reply Camp
	    System.out.println("================VIEW SUGGESTION================");
	    
	    
	    
	    // Display the camps created by the staff
	    viewCampCreated(staff);

	    // Select a camp to view suggestions
	    Camp campToView = selectCamp(staff.getCampsCreated());

	    // Check if a valid camp is selected
	    if (campToView == null) {
	        return;
	    }

	    // Check if there are any suggestions for the selected camp
	    if (campToView.getSuggestion().isEmpty()) {
	        System.out.println("No suggestion has been submitted for " + campToView.getCampName());
	    } else {
	        // Display information for each suggestion
	        for (Suggestion suggestion : campToView.getSuggestion()) {
	            Suggestion.printSuggestionInfo(suggestion);
	        }
	    }
	}

	public void approveSuggestion(Staff staff) {
	    // Display suggestions for approval
	    viewSuggestion(staff);

	    // Initialize variables
	    Suggestion suggestionToApprove;

	    do {
	        // Prompt the user to select a suggestion by entering the subject
	        System.out.println("Which suggestion would you like to approve/disapprove? Insert suggestion SUBJECT. Enter \"exit\" to go back");
	        String suggestionSubjectStr = input.nextLine().trim();

	        // Check if the user wants to exit
	        if (suggestionSubjectStr.equalsIgnoreCase("exit")) {
	            System.out.println("Action terminated by the user, exiting...");
	            return;
	        }

	        // Get the suggestion based on the subject
	        suggestionToApprove = Suggestion.getSuggestionHashMap().get(suggestionSubjectStr);

	        if (suggestionToApprove == null) {
	            System.out.println("Please enter the SUBJECT of the suggestion and try again");
	        }
	    } while (suggestionToApprove == null);

	    // Display the selected suggestion content
	    System.out.println("The suggestion selected is: " + suggestionToApprove.getContent());
	    System.out.println();

	    // Display options for response
	    System.out.println("Please select your response from the following:");
	    System.out.println("1. Approve");
	    System.out.println("2. Disapprove");
	    System.out.println("3. Pending");
	    System.out.println("4. Cancel and exit");

	    int decision;

	    do {
	        try {
	            // Prompt the user to enter a valid integer choice
	            System.out.print("Enter your choice: ");
	            decision = input.nextInt();

	            // Consume the newline character
	            input.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid integer.");
	            input.nextLine(); // Consume the invalid input
	            decision = -1; // Set a default value or use a flag to handle the loop
	        }
	    } while (decision != 1 && decision != 2 && decision != 3 && decision != 4);

	    Status response = null;

	    // Set the response based on the user's choice
	    switch (decision) {
	        case 1:
	            response = Status.Approved;
	            break;
	        case 2:
	            response = Status.Disapproved;
	            break;
	        case 3:
	            response = Status.Pending;
	            break;
	        case 4:
	            System.out.println("Action terminated by the user, exiting");
	            return;
	    }

	    // Prompt the user to confirm or cancel the decision
	    System.out.println("Enter \"confirm\" to submit your decision or any other key to cancel");

	    // Read the user's response and capitalize it
	    String confirmation = input.nextLine().trim().toUpperCase();

	    if (confirmation.equals("CONFIRM")) {
	        // Update the suggestion, process it, and update related CSV files
	        suggestionToApprove.setStatus(response);
	        suggestionToApprove.setProcessed(true);
	        Committee submitter = Committee.committeeNameMap.get(suggestionToApprove.getSubmitter());
	        int points = submitter.getPoints();
	        submitter.setPoints(points + 1);
	        CSVDataManager.updateSuggestionCSVFile(suggestionToApprove);
	        CSVDataManager.updateCommitteeCSVFile(submitter);
	        System.out.println("The suggestion has been processed, and your response has been successfully recorded");
	    } else {
	        System.out.println("Action terminated by the user, exiting...");
	    }
	}

	public void generateReport(Staff staff) {
		viewCampCreated(staff);
		Camp campToReport = selectCamp(staff.getCampsCreated());
		Scanner scanner = new Scanner(System.in);
		GenerateReport staffGenerator = new StaffGenerateReport();
		boolean exit = false;
		do {
			System.out.println("Generate Report Menu:");
			System.out.println("1. View Camp Committee Reports");
			System.out.println("2. View Camp Attendee Reports");
			System.out.println("3. Back to Main Menu");

			int choice;
			do {
				try {
					System.out.print("Enter your choice: ");
					choice = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Please enter a valid integer.");
					scanner.nextLine(); // Consume the invalid input
					choice = -1; // Set a default value or use a flag to handle the loop
				}
			} while (choice != 1 && choice != 2 && choice != 3);
			switch (choice) {
				case 1:
					String format = null;
					do {
						System.out.println("What format do you want to use, CSV or TXT? Please enter CSV or TXT");
						format = scanner.next();
						if (format.equalsIgnoreCase("CSV")) {
							staffGenerator.generateCommitteeList(campToReport, Format.CSV);
						} else if (format.equalsIgnoreCase("TXT")) {
							staffGenerator.generateCommitteeList(campToReport, Format.TXT);
						} else {
							System.out.println("Only CSV and TXT are accepted");
						}
					}while(!format.equalsIgnoreCase("CSV") && !format.equalsIgnoreCase("TXT"));
					break;
				case 2:
					String format2 = null;
					do {
						System.out.println("What format do you want to use, CSV or TXT? Please enter CSV or TXT");
						format2 = scanner.next();
						if (format2.equalsIgnoreCase("CSV")) {
							staffGenerator.generateAttendeeList(campToReport, Format.CSV);
						} else if (format2.equalsIgnoreCase("TXT")) {
							staffGenerator.generateAttendeeList(campToReport, Format.CSV);
						} else {
							System.out.println("Only CSV and TXT are accepted");
						}
					}while(!format2.equalsIgnoreCase("CSV") && !format2.equalsIgnoreCase("TXT"));
					break;
				case 3:
					exit = true;
					break;
				default:
					System.out.println("Invalid choice. Please enter a valid option.");
					break;
			}
		}while(!exit);
	}


}