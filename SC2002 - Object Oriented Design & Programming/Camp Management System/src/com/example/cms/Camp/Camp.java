package com.example.cms.Camp;

import com.example.cms.Enquiries.Enquiry;
import com.example.cms.Faculty;
import com.example.cms.Student.Attendee;
import com.example.cms.Student.Committee;
import com.example.cms.Suggestions.Suggestion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *  Camp class is to represent a camp.
 *  Includes information on the camp and methods to get and modify camp info
 */


public class Camp {
	private static Map<String, Camp> campMap = new HashMap<>();

	private String campName;
	private static ArrayList<Camp> campList= new ArrayList<>(); // to keep track of all of the camps
	private ArrayList<LocalDate> campDates = new ArrayList<>();
	private LocalDate regCloseDate;
	private ArrayList<Faculty> userGroup = new ArrayList<>();
	private String location;
	private int totalSlots;
	private int remainingSlots;
	private int committeeSlots;
	private int remainingCommitteeSlots;
	private String description;
	private String staffInCharge;
	private ArrayList<Attendee> attendeesRegistered = new ArrayList<>();
	private ArrayList<Committee> committeeRegistered = new ArrayList<>();
	private boolean visibility;
	private ArrayList<Enquiry> enquiry = new ArrayList<>();
	private ArrayList<Suggestion> suggestion = new ArrayList<>();
	//private static int counter = -1;

	/**
	 * Creates a new Camp without setting any specific info/data
	 */

	public Camp(){

	}

	/**
	 * Creates a new Camp with all the specified info
	 *
	 * @param campName Name of camp
	 * @param campDate Dates for which the camp take place
	 * @param regCloseDate Registration closing date of camp
	 * @param userGroup Schools for which students can join the camp
	 * @param location Location of camp
	 * @param totalSlots Total number of attendees that can participate in the camp
	 * @param staff Staff-in-charge of the camp
	 * @param visibility Camp visibility to students
	 */


	public Camp(String campName, ArrayList<LocalDate> campDate, LocalDate regCloseDate, ArrayList<Faculty> userGroup, String location, int totalSlots, int remainingSlots, int committeeSlots, int remainingCommitteeSlots, String staff, boolean visibility){
		this.campName = campName;
		this.campDates = campDate;
		this.regCloseDate = regCloseDate;
		this.userGroup = userGroup;
		this.location = location;
		this.totalSlots = totalSlots;
		this.remainingSlots = remainingSlots;
		committeeSlots = 10;
		this.committeeSlots = committeeSlots;
		this.remainingCommitteeSlots = remainingCommitteeSlots;
		staffInCharge = staff;
		this.visibility = visibility;
		campMap.put(campName, this);
	}



//	public static int getCounter() {
//		return counter;
//	}
//
//	public static void setCounter(int counter){
//		Camp.counter = counter;
//	}

	/**
	 * Prints out all relevant information about the camp.
	 * @param camp Camp specified
	 */

	public static void printAllCampInfo(Camp camp) {
	    if (camp != null) {
	        // Print table border
	        printTableBorder();

	        // Print table headers
	        System.out.printf("| %-20s | %-15s | %-15s | %-25s | %-20s | %-15s | %-20s | %-20s |%n",
	                "Camp Name", "Start Date", "End Date", "Reg Close Date", "Eligible Faculty/s", "Location", "Remaining Slot", "Staff In Charge", "Description");

	        // Print table border
	        printTableBorder();

	        // Print camp information
	        System.out.printf("| %-20s | %-15s | %-15s | %-25s | %-20s | %-15s | %-20s | %-20s |%n",
	                camp.getCampName(),
	                camp.getCampDates().get(0),
	                camp.getCampDates().get(1),
	                camp.getRegCloseDate(),
	                String.join(", ", camp.getUserGroup().stream().map(Enum::name).toArray(String[]::new)),
	                camp.getLocation(),
	                camp.getRemainingSlots(),
	                camp.getStaffInCharge(),
	                camp.getDescription());

	        // Print table border
	        printTableBorder();
	        System.out.println(); // Add an empty line for better readability
	    } else {
	        System.out.println("Camp not found.");
	    }
	}

	private static void printTableBorder() {
	    System.out.println("+---------------------+-----------------+-----------------+-------------------------+----------------------+-----------------+----------------------+----------------------+");
	}


	/**
	 * Gets name of the camp.
	 * @return Name of the camp.
	 */

	public String getCampName() {
		return this.campName;
	}

	/**
	 * Changes/sets name of Camp.
	 * @param campName Intended name of camp
	 */
	public void setCampName(String campName) {
		this.campName = campName;
	}

	/**
	 * Gets dates for which the camp takes place.
	 * @return Dates for which the camp takes place.
	 */
	public ArrayList<LocalDate> getCampDates() {
		return this.campDates;
	}

	/**
	 * Sets dates for which the camp takes place.
	 * @param dates List of dates involving the camp
	 */
	public void setCampDates(ArrayList<LocalDate> dates) {
		campDates = dates;
	}

	/**
	 * Gets registration closing date of Camp
	 * @return Registration closing date of Camp
	 */
	public LocalDate getRegCloseDate() {
		return this.regCloseDate;
	}

	/**
	 * Sets registration closing date of Camp
	 * @param regCloseDate Intended registration closing date
	 */
	public void setRegCloseDate(LocalDate regCloseDate) {
		this.regCloseDate = regCloseDate;
	}

	/**
	 * User group that the camp belongs to. Can be either own school, or whole University.
	 * If whole university, it is represented as an ArrayList of ALL schools.
	 * @return Schools that the camp involves
	 */
	public ArrayList<Faculty> getUserGroup() {
		return this.userGroup;
	}

	/**
	 * Sets schools that the camp is involved with.
	 * @param userGroup Intended userGroups
	 */
	public void setUserGroup(ArrayList<Faculty> userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * Gets location of the camp.
	 * @return Location of the camp
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Sets location of the camp.
	 * @param location Intended location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Get total numbers of slots of the camp.
	 * @return total number of slots of camp
	 */
	public int getTotalSlots() {
		return this.totalSlots;
	}

	/**
	 * Sets total number of slots of the camp.
	 * @param totalSlots Intended total number of slots
	 */
	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	/**
	 * Get remaining numbers of slots of the camp.
	 * @return remaining numbers of slots of the camp.
	 */
	public int getRemainingSlots(){return this.remainingSlots;}

	/**
	 * Sets remaining number of slots for a camp.
	 * @param remainingSlots Intended remaining number of slots
	 */
	public void setRemainingSlots(int remainingSlots){this.remainingSlots = remainingSlots;}

	/**
	 * Gets total number of slots for the committee of a camp.
	 * @return total number of slots for the committee of a camp
	 */
	public int getCommitteeSlots() {
		return this.committeeSlots;
	}

	/**
	 * Sets total number of slots for the committee of a camp.
	 * @param committeeSlots Intended total number for committee slots
	 */
	public void setCommitteeSlots(int committeeSlots) {
		this.committeeSlots = committeeSlots;
	}

	/**
	 * Gets remaining number of slots for the committee of a camp.
	 * @return remaining number of slots for the committee of a camp
	 */
	public int getRemainingCommitteeSlots() {
		return remainingCommitteeSlots;
	}

	/**
	 * Sets remaining number of slots for the committee of a camp.
	 * @param remainingCommitteeSlots Intended remaining number of slots for a camp
	 */
	public void setRemainingCommitteeSlots(int remainingCommitteeSlots){
		this.remainingCommitteeSlots = remainingCommitteeSlots;
	}

	/**
	 * Gets description of the camp.
	 * @return description of the camp
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets description of the camp.
	 * @param description Intended description for the camp
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets Staff-in-charge of the camp.
	 * Note that only 1 staff can be in charge of a camp
	 * @return Designated Staff-in-charge of the camp
	 */
	public String getStaffInCharge() {
		return this.staffInCharge;
	}

	/**
	 * Sets Staff-in-charge of the camp.
	 * @param staffInCharge Intended Staff-in-charge of the camp
	 */
	public void setStaffInCharge(String staffInCharge) {
		this.staffInCharge = staffInCharge;
	}

	/**
	 * Gets all the attendees registered for the camp, in the form of an ArrayList.
	 * @return all the attendees registered for the camp
	 */
	public ArrayList<Attendee> getAttendeesRegistered() {
		return this.attendeesRegistered;
	}

	/**
	 * Adds one new attendee to existing ArrayList of attendees
	 * @param attendee New attendee
	 */
	public void setAttendeesRegistered(Attendee attendee) {
		attendeesRegistered.add(attendee);
	}

	/**
	 * Get list of committee members registered for a camp
	 * @return list of committee members registered for a camp
	 */
	public ArrayList<Committee> getCommitteeRegistered() {
		return committeeRegistered;
	}

	/**
	 * Adds new committee member to existing ArrayList of committee members of a camp
	 * @param committeeMember New committee member
	 */
	public void setCommitteeRegistered(Committee committeeMember) {
		this.committeeRegistered.add(committeeMember);
	}

	/**
	 * Get visibility of camp. If FALSE, visibility is toggled off, meaning students cannot see it.
	 * Controlled by staff-in-charge of the camp.
	 * @return Visibility of camp
	 */
	public boolean getVisibility() {
		return this.visibility;
	}

	/**
	 * Sets visibility of camp, to either on or off (true or false)
	 * @param visibility Desired visibility to set
	 */
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	/**
	 * Gets list of enquiries for a camp.
	 * Note that Enquiries can only be made by Camp attendees.
	 * @return list of all enquiries for a camp
	 */
	public ArrayList<Enquiry> getEnquiry() {
		return this.enquiry;
	}

	/**
	 * Adds new enquiry to existing list of enquiries for a camp.
	 * Note that Enquiries can only be made by Camp attendees.
	 * @param enquiry New enquiry
	 */
	public void setEnquiry(Enquiry enquiry) {
		this.enquiry.add(enquiry);
	}

	/**
	 * Gets list of suggestions for a camp.
	 * Note that Suggestions can only be made by Camp committee members.
	 * @return List of all suggestions for a camp.
	 */
	public ArrayList<Suggestion> getSuggestion() {
		return this.suggestion;
	}

	/**
	 * Sets ArrayList of suggestions based on data from CSV (TO BE CONFIRMED).
	 * @param suggestion ArrayList of suggestions provided
	 */
	public void setSuggestion(ArrayList<Suggestion> suggestion) {
		this.suggestion = suggestion;
	}

	/**
	 * Gets specified Camp object based on user input of name of camp into the hashmap
	 * @param campName Name of camp specified by user input
	 * @return specified Camp object based on user input of name of camp
	 */
	public static Camp getCampByName(String campName) {
		return campMap.get(campName);
	}

	/**
	 * Get hashmap containing names matched with corresponding camp objects
	 * @return hashmap containing names matched with corresponding camp objects
	 */
	// Add a method to get all camps in the map
	public static Map<String, Camp> getCampMap() {
		return campMap;
	}

	/**
	 * Takes all the relevant information on a camp, combines them into a type of string that is readable like a CSV
	 * @param campContent Mutable string containing all relevant information on the camp, assembled by concatenation
	 * @param camp The specific camp provided
	 */
	public static void generateCampInfo(StringBuilder campContent, Camp camp){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		campContent.append("Camp Name, Start Date, End Date, Reg Close Date, Faculty, Location, remaining attendee slots, remaining committee slots, description, staff").append("\n");
		campContent.append(camp.getCampName()).append(",");
		campContent.append(camp.getCampDates().get(0).format(formatter)).append(",");
		campContent.append(camp.getCampDates().get(1).format(formatter)).append(",");
		campContent.append(camp.getRegCloseDate().format(formatter)).append(",");

		String facultyString = String.join("|", camp.getUserGroup().stream().map(Enum::name).toArray(String[]::new));
		campContent.append(facultyString).append(",");
		campContent.append(camp.getLocation()).append(",");
		campContent.append(camp.getRemainingSlots()).append(",");
		campContent.append(camp.getRemainingCommitteeSlots()).append(",");
		campContent.append(camp.getDescription()).append(",");
		campContent.append(camp.getStaffInCharge()).append("\n");
		campContent.append("\n");
	}
}