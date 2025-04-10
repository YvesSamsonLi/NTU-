package com.example.cms.CSVConverter;

import com.example.cms.Camp.Camp;
import com.example.cms.Enquiries.Enquiry;
import com.example.cms.Staff.Staff_User;
import com.example.cms.Student.Student_User;
import com.example.cms.Suggestions.Suggestion;

import java.io.*;
import java.util.List;

/**
 * Controller class to write new information for the first time to CSV (data) of several classes, such as Student User, Attendee, Committee, etc.
 */
public class CSVWriter {

    // Method to write user information to a CSV file

    /**
     * Writes student user information to CSV file
     * @param student Student user object with the information to be written
     * @param appendHeader unused parameter at the moment
     */
    public static void writeStudentUserToCSV(Student_User student, boolean appendHeader) {
        String csvFilePath = "student.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            // Check if the CSV file is empty
            if (isFileEmpty(csvFilePath)) {
                // Add the CSV header
                writer.write("StudentID,Name,Password,Salt,UserGroup,Faculty,Points,CampAccessibility,CampCommittee,RegisteredCamps,"
                        + "SecurityQuestion,SecurityAnswers,EnquirySubmitted,SuggestionSubmitted");
                writer.newLine();
            }
        	
        	// Convert ArrayList<Camp> to an array of CharSequence with null check
            CharSequence[] campAccessibilityArray = toArrayWithNullCheckList(student.getCampAccessibility());
            CharSequence[] registeredCampsArray = toArrayWithNullCheckCamp(student.getRegisteredCamps());
            CharSequence[] enquirySubmittedArray = toArrayWithNullCheckEnquiry(student.getEnquirySubmitted());
            CharSequence[] suggestionSubmittedArray = toArrayWithNullCheckSuggestion(student.getSuggestionSubmitted());

            // Append the user information to the CSV file
            writer.write(student.getStudentID() + ","
                    + student.getName() + ","
                    + student.getPassword() + ","
                    + student.getSalt() + ","        
                    + student.getUserGroup() + ","
                    + student.getFaculty() + ","
                    + student.getPoints() + ","
                    + String.join("|", campAccessibilityArray) + ","
                    + student.getCampCommittee() + ","
                    + String.join("|", registeredCampsArray) + ","
                    + String.join("|", student.getSecurityQuestion()) + ","
                    + String.join("|", student.getSecurityAnswers()) + ","
                    + String.join("|", enquirySubmittedArray) + ","
                    + String.join("|", suggestionSubmittedArray));
            writer.newLine();

            System.out.println("User information written to " + csvFilePath + " successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }
    /**
     * Writes Camp Attendee user information to CSV file
     * @param student Student user object with the information to be written
     * @param appendHeader unused parameter at the moment
     */
    public static void writeAttendeeToCSV(Student_User student, boolean appendHeader) {
        String csvFilePath = "attendee.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            // Check if the CSV file is empty
            if (isFileEmpty(csvFilePath)) {
                // Add the CSV header
                writer.write("StudentID,Name,Password,UserGroup,Faculty,CampAccessibility,CampCommittee,RegisteredCamps,"
                        + "SecurityQuestion,SecurityAnswers,EnquirySubmitted");
                writer.newLine();
            }

            // Convert ArrayList<Camp> to an array of CharSequence with null check
            CharSequence[] campAccessibilityArray = toArrayWithNullCheckList(student.getCampAccessibility());
            CharSequence[] registeredCampsArray = toArrayWithNullCheckCamp(student.getRegisteredCamps());
            CharSequence[] enquirySubmittedArray = toArrayWithNullCheckEnquiry(student.getEnquirySubmitted());

            // Append the user information to the CSV file
            writer.write(student.getStudentID() + ","
                    + student.getName() + ","
                    + student.getPassword() + ","
                    + student.getUserGroup() + ","
                    + student.getFaculty() + ","
                    + String.join("|", campAccessibilityArray) + ","
                    + student.getCampCommittee() + ","
                    + String.join("|", registeredCampsArray) + ","
                    + String.join("|", student.getSecurityQuestion()) + ","
                    + String.join("|", student.getSecurityAnswers()) + ","
                    + String.join("|", enquirySubmittedArray));
            writer.newLine();
            
          

            System.out.println("Attendee information written to " + csvFilePath + " successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }
    /**
     * Writes Camp committee member information to CSV file
     * @param student Student user object with the information to be written
     * @param appendHeader unused parameter at the moment
     */
    public static void writeCommitteeToCSV(Student_User student, boolean appendHeader) {
        String csvFilePath = "committee.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            // Check if the CSV file is empty
            if (isFileEmpty(csvFilePath)) {
                // Add the CSV header
                writer.write("StudentID,Name,Password,UserGroup,Faculty,Points,CampAccessibility,CampCommittee,RegisteredCamps,"
                        + "SecurityQuestion,SecurityAnswers,SuggestionSubmitted");
                writer.newLine();
            }

            // Convert ArrayList<Camp> to an array of CharSequence with null check
            CharSequence[] campAccessibilityArray = toArrayWithNullCheckList(student.getCampAccessibility());
            CharSequence[] registeredCampsArray = toArrayWithNullCheckCamp(student.getRegisteredCamps());
            CharSequence[] suggestionSubmittedArray = toArrayWithNullCheckSuggestion(student.getSuggestionSubmitted());

            // Append the user information to the CSV file
            writer.write(student.getStudentID() + ","
                    + student.getName() + ","
                    + student.getPassword() + ","
                    + student.getUserGroup() + ","
                    + student.getFaculty() + ","
                    + student.getPoints() + ","
                    + String.join("|", campAccessibilityArray) + ","
                    + student.getCampCommittee() + ","
                    + String.join("|", registeredCampsArray) + ","
                    + String.join("|", student.getSecurityQuestion()) + ","
                    + String.join("|", student.getSecurityAnswers()) + ","
                    + String.join("|", suggestionSubmittedArray));
            writer.newLine();

            System.out.println("Attendee information written to " + csvFilePath + " successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

    
/******************************FOR STAFF *******************************************/
    public static void writeStaffToCSV(Staff_User staff, boolean appendHeader) {
        String csvFilePath = "staff.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {

            boolean isFileEmpty = isFileEmpty(csvFilePath);


            // Check if the CSV file is empty
            if (!isFileEmpty && !staffExistsInCSV(staff.getStaffID(), csvFilePath)) {
                // Add a new line before appending the new staff information
                writer.newLine();
            }

            // Check if the CSV file is empty or if a header needs to be added
            if (isFileEmpty || (isFileEmpty && appendHeader)) {
                // Add the CSV header
                writer.write("StaffID,Name,Password,Salt,Faculty,CampsCreated,SecurityQuesion,SecurityAnswers");
                writer.newLine();
            }

            // Convert ArrayList<Camp> to an array of CharSequence with null check
            CharSequence[] campCreatedArray = toArrayWithNullCheckCamp(staff.getCampsCreated());

            // Append the user information to the CSV file
            writer.write(staff.getStaffID() + ","
                    + staff.getName() + ","
                    + staff.getPassword() + ","
                    + staff.getSalt()+ ","
                    + staff.getFaculty() + ","
                    + String.join("|", campCreatedArray) + ","
                    + String.join("|", staff.getSecurityQuestion()) + ","
                    + String.join("|", staff.getSecurityAnswers()) + "");
           

            System.out.println("Staff information written to " + csvFilePath + " successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

    private static boolean staffExistsInCSV(String staffID, String csvFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true; // flag for header

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line (headers)
                }

                String[] data = line.split(",", -1); // Use -1 to keep empty fields

                if (data.length > 0 && data[0].equals(staffID)) {
                    // Staff ID exists in the CSV
                    return true;
                }
            }
        }
        return false;
    }

    /******************************FOR STAFF *******************************************/
    
    // Helper method to handle null check and conversion to array
    /**
     * Helper method to handle null check and conversion to array
     * @param list Camp list format
     * @return  Character Sequence Array format
     */
    private static CharSequence[] toArrayWithNullCheckCamp(List<Camp> list) {
        if (list != null) {
            return list.toArray(new CharSequence[0]);
        } else {
            return new CharSequence[0];
        }
    }


    // Helper method to handle null check and conversion to array
    /**
     * Helper method to handle null check and conversion to array
     * @param list Enquiry list format
     * @return  Character Sequence Array format
     */
    private static CharSequence[] toArrayWithNullCheckEnquiry(List<Enquiry> list) {
        if (list != null) {
            return list.toArray(new CharSequence[0]);
        } else {
            return new CharSequence[0];
        }
    }
    
 // Helper method to handle null check and conversion to array
    /**
     * Helper method to handle null check and conversion to array
     * @param list Suggestion list format
     * @return  Character Sequence Array format
     */
    private static CharSequence[] toArrayWithNullCheckSuggestion(List<Suggestion> list) {
        if (list != null) {
            return list.toArray(new CharSequence[0]);
        } else {
            return new CharSequence[0];
        }
    }
    
    // Helper method to handle null check and conversion to array
    /**
     * Helper method to handle null check and conversion to array
     * @param list String list format
     * @return  Character Sequence Array format
     */
    private static CharSequence[] toArrayWithNullCheckList(List<String> list) {
        if (list != null) {
            return list.toArray(new CharSequence[0]);
        } else {
            return new CharSequence[0];
        }
    }


    
 // Helper method to handle null check and conversion to array for a single Camp object
    private static CharSequence[] toArrayWithNullCheckCamp(Camp camp) {
        if (camp != null) {
            return new CharSequence[]{camp.toString()}; // Assuming you have a meaningful way to convert a Camp object to a string
        } else {
            return new CharSequence[0];
        }
    }

    /**
     * Helper method to check if csv file in the filepath is empty or not
     * @param filePath Filepath to find the specified file
     * @return True if file is empty, false if not
     */
    public static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return file.length() == 0;
    }
    
    
 
    
}
