package com.example.cms.Student;

import com.example.cms.CSVConverter.CSVDataManager;
import com.example.cms.Camp.Camp;
import com.example.cms.Enquiries.Enquiry;
import com.example.cms.Faculty;
import com.example.cms.Student_Role;
import com.example.cms.Suggestions.Suggestion;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Entity class, provides information about the Student User regardless of Camp Attendee or committee member.
 * Routed from account_Manager
 * As part of Open-Closed Principle, we close this class to modification,
 * but open it to extension to different student types,
 * in this case, Camp Attendee and Camp Committee
 */
public class Student_User {

    protected String studentID;
    private String password = "password";
    private String salt;
    private String name;
    private Faculty faculty;
    private Student_Role userGroup;
    private int points;
    protected static List<Camp> registeredCamps;
    public static Map<String, Student_User> existingStudents = new HashMap<>(); // Use a HashMap instead of a List
    protected List<String> campAccessibility; // This indicates which camp the attendee can see
    private boolean campCommittee;
    private ArrayList<String> securityQuestion;
    private ArrayList<String> securityAnswers;
    private List<Enquiry> enquirySubmitted;
    private List<Suggestion> suggestionSubmitted;

    public Student_User() {
    	
    	 // Initialize the map only if it's null
        if (existingStudents == null) {
            existingStudents = new HashMap<>();
        }
        
        this.studentID = null; // You can set an initial value or leave it empty
        this.password = "password";
        this.salt = null;
        this.name = "";
        this.faculty = Faculty.NIL; // Set a default faculty or choose based on your requirements
        this.userGroup = Student_Role.NIL; // Set a default role or choose based on your requirements
        this.registeredCamps = new ArrayList<>();
        this.campAccessibility = new ArrayList<>();
        this.campCommittee = false; // Set to true if the student is initially part of the camp committee
        this.securityQuestion = new ArrayList<>();
        this.securityAnswers = new ArrayList<>();
        this.enquirySubmitted = new ArrayList<>();
  
    }

    public String getStudentID() {
        return this.studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return this.faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Student_Role getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(Student_Role userGroup) {
        this.userGroup = userGroup;
    }

    public void setRegisteredCamps(List<Camp> registeredCamps2) {
        this.registeredCamps = registeredCamps2;
    }

    public List<Camp> getRegisteredCamps() {
        return this.registeredCamps ;
    }

    
    public List<String> getCampAccessibility() {
        return this.campAccessibility;
    }

    public void setCampAccessibility(List<String> campAccessibility) {
        this.campAccessibility = campAccessibility;
    }

    public boolean getCampCommittee() {
        return this.campCommittee;
    }

    public void setCampCommittee(boolean campCommittee) {
        this.campCommittee = campCommittee;
    }

    public ArrayList<String> getSecurityQuestion() {
        return this.securityQuestion;
    }

    public void setSecurityQuestions(List<String> securityQuestions) {
        this.securityQuestion = new ArrayList<>(securityQuestions);
    }

    public ArrayList<String> getSecurityAnswers() {
        return this.securityAnswers;
    }

    public void setSecurityAnswers(List<String> securityAnswers) {
        this.securityAnswers = new ArrayList<>(securityAnswers);
    }

    public List<Enquiry> getEnquirySubmitted() {
        return this.enquirySubmitted;
    }

    public void setEnquirySubmitted(List<Enquiry> enquirySubmitted2) {
        this.enquirySubmitted = enquirySubmitted2;
    }
    
    public List<Suggestion> getSuggestionSubmitted() {
        return this.suggestionSubmitted;
    }

    public void setSuggestionSubmitted(List<Suggestion> suggestionSubmitted2) {
        this.suggestionSubmitted = suggestionSubmitted2;
    }

    public  Student_User getStudentById(String StudentId) {
    	 for (Student_User student : existingStudents.values()) {
    	        if (student.getStudentID().equals(studentID)) {
    	            return student;
    	        }
    	    }
    	    return null; // If no student with the given ID is found
    }
    
    public Map<String, Student_User> getExistingStudents() {
        return existingStudents;
    }
    
    public boolean isStudentExist(String userID) {
        System.out.println("Checking for student existence. Given ID: " + userID);
        
     // Load students from CSV before checking existence
        CSVDataManager.loadStudentsFromCSV(this);

        // Use the instance-specific existingStudents map
        for (String existingID : this.existingStudents.keySet()) {
            System.out.println("Comparing with existing ID: " + existingID);
        }

        return this.existingStudents.containsKey(userID);
    }

    public  void addStudent(Student_User newStudent) {
        existingStudents.put(newStudent.getStudentID(), newStudent);
        //saveStudentsToFile(existingStudents.values());
    }

    public  String getName(String userID) {
        Student_User student = existingStudents.get(userID);
        return (student != null) ? student.name : null;
    }
    
    
//    public void updateCSVFile(String studentId) {
//        // Get the path to the CSV file
//        String csvFilePath = "student.csv";
//
//        // Create a StringBuilder to store the updated content
//        StringBuilder updatedContent = new StringBuilder();
//
//        // Find the student based on the provided studentId
//        Student_User student = existingStudents.get(studentId);
//
//        // Check if the student exists
//        if (student != null) {
//            // Append the information for the specific student to the StringBuilder
//            updatedContent.append(student.getStudentID()).append(",");
//            updatedContent.append(student.getName()).append(",");
//            updatedContent.append(student.getPassword()).append(",");
//            updatedContent.append(student.getUserGroup()).append(",");
//            updatedContent.append(student.getFaculty()).append(",");
//            updatedContent.append(String.join("|", student.getCampAccessibility())).append(",");
//            updatedContent.append(student.getCampCommittee()).append(",");
//            updatedContent.append(String.join("|", student.getRegisteredCamps().stream().map(Camp::getCampName).toArray(String[]::new))).append(",");
//            updatedContent.append(String.join("|", student.getSecurityQuestion())).append(",");
//            updatedContent.append(String.join("|", student.getSecurityAnswers())).append(",");
//            updatedContent.append(String.join("|", student.getEnquirySubmitted().stream().map(Enquiry::getEnquiry_Subject).toArray(String[]::new))).append(",");
//            updatedContent.append(String.join("|", student.getSuggestionSubmitted().stream().map(Suggestion::getSuggestion_Subject).toArray(String[]::new))).append("\n");
//
//            // Write the updated content to the CSV file
//            try (FileWriter writer = new FileWriter(csvFilePath)) {
//                writer.write(updatedContent.toString());
//                System.out.println("CSV file updated successfully.");
//            } catch (IOException e) {
//                System.out.println("An error occurred while updating the CSV file.");
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Student with ID " + studentId + " not found. CSV file not updated.");
//        }
//    }

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

   

}