package com.example.cms.Staff;

import com.example.cms.Camp.Camp;
import com.example.cms.Faculty;

import java.util.*;
/**
 * Entity class, provides basic functionality to get and set information about the Staff
 * Routed from account_Manager
 * As part of Open-Closed Principle, we close this class to modification,
 * but open it to extension to Staff class
 */
public class Staff_User {
    private String staffID;
    private String password = "password";
    private String salt;
    private String name;
    private Faculty faculty;

    protected ArrayList<Camp> campsCreated = new ArrayList<>();
    private ArrayList<String> securityQuestion;
    private ArrayList<String> securityAnswers;
	private Scanner input;

    public static Map<String, Staff> existingStaff = new HashMap<>();

    public Staff_User(){
        this.staffID = null;
        this.name = "";
        this.password = "password";
        this.salt = null;
        this.faculty = null;
        this.campsCreated = new ArrayList<>();
        this.securityQuestion = new ArrayList<>();
        this.securityAnswers = new ArrayList<>();
        
     // Initialize the map only if it's null
        if (existingStaff == null) {
        	existingStaff = new HashMap<>();
        }
    }

    public Staff_User(String staffID2, String name2, Faculty faculty2, String securityQuestion2, String securityAns) {
        this.staffID = staffID2;
        this.name = name2;
        this.faculty = faculty2;

        // Assuming these methods exist in your Staff_User class to set security questions and answers
        setSecurityQuestions(Collections.singletonList(securityQuestion2));
        setSecurityAnswers(Collections.singletonList(securityAns));
    }

	public String getStaffID() {
        return this.staffID;
    }
    
    public void setStaffID(String staffId) {
        this.staffID = staffId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Faculty getFaculty() {
        return this.faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public ArrayList<Camp> getCampsCreated() {
        return this.campsCreated;
    }

    public void setCampsCreated(Camp camp) {
    	campsCreated.add(camp);
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

    public static Map<String, Staff> getExistingStaff() {
        return existingStaff;
    }
   
    
    public void addStaff(Staff newStaff) {
    	 if (newStaff != null) {
 	        existingStaff.put(newStaff.getStaffID(),newStaff);

 	        // Print success message and details about the added attendee
 	        System.out.println("New Staff added successfully  in addStaff method:");
 	        System.out.println("Staff ID: " + newStaff.getStaffID());
 	        System.out.println("Name: " + newStaff.getName());  // Assuming you have a getName method in Attendee

 	        // You can add more details based on your Attendee class properties

 	        // You may want to save the updated attendeesMap to a file or perform other actions.
 	    } else {
 	        System.out.println("Failed to add Staff. The provided Stafff is null.");
 	    }
    }

    protected Staff_User getStaffByID(String staffID){
        for(Staff_User staff : existingStaff.values()){
            if(staff.getStaffID().equals(staffID)){
                return staff;
            }
        }
        System.out.println("No staff has been found");
        return null;
    }

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void viewEnquiry(Staff_User existingStaffMember) {
		// TODO Auto-generated method stub
		
	}
	
}