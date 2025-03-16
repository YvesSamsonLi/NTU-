package com.example.cms.Suggestions;

import com.example.cms.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Entity class to handle each suggestion of a camp.
 * Has get and set methods for information about each suggestion, as well as a method to print all the information about the suggestion.
 */
public class Suggestion {
    private String suggestion_Subject;
    private String submitter;
    private String content;
    private LocalDate dateSubmitted;
    private Status status;
    private boolean processed = false;

    // Use suggestion_Subject as the key for the HashMap
    private static HashMap<String, Suggestion> suggestionHashMap = new HashMap<>();

    private static ArrayList<Suggestion> suggestionArrayList = new ArrayList<>();

    /**
     * Creates new suggestion with all the specific information provided.
     * @param suggestion_Subject Header of this suggestion
     * @param committee Which committee this suggestion came from
     * @param content Body of this suggestion
     * @param dateSubmitted Date on which this suggestion was submitted
     */
    public Suggestion(String suggestion_Subject, String committee, String content, LocalDate dateSubmitted, Status status, boolean processed) {
        this.suggestion_Subject = suggestion_Subject;
        this.submitter = submitter;
        this.content = content;
        this.dateSubmitted = dateSubmitted;
        this.status = status;
        this.processed = processed;
        // Add the suggestion to the HashMap using suggestion_Subject as the key
    }

    /**
     * Prints all the relevant information on the particular suggestion.
     * @param suggestion The suggestion specified by the user.
     */
    public static void printSuggestionInfo(Suggestion suggestion){
        System.out.println("The subject: " + suggestion.getSuggestion_Subject());
        System.out.println("The submitter: " + suggestion.getSubmitter());
        System.out.println("The date submitted: " + suggestion.getDateSubmitted());
        System.out.println("The content: "+ suggestion.getContent());
        System.out.println("The status: " + suggestion.getStatus());
    }

    // Getter and setter for suggestion_Subject

    /**
     * Gets the header of the suggestion
     * @return header of the suggestion
     */
    public String getSuggestion_Subject() {
        return suggestion_Subject;
    }

    /**
     * Sets the header of the suggestion
     * @param suggestion_Subject New header of suggestion
     */
    public void setSuggestion_Subject(String suggestion_Subject) {
        this.suggestion_Subject = suggestion_Subject;
    }

    // Getter and setter for submitter

    /**
     * Get the sender of the suggestion
     * @return Sender of the suggestion
     */
    public String getSubmitter() {
        return this.submitter;
    }

    /**
     * Sets the sender of the suggestion
     * @param submitter sender of the suggestion
     */
    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    // Getter and setter for content

    /**
     * Gets body of the suggestion
     * @return body of the suggestion
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets body of the suggestion
     * @param content New body of the suggestion
     */
    public void setContent(String content) {
        this.content = content;
    }

    // Getter and setter for dateSubmitted

    /**
     * Gets date on which the suggestion was submitted
     * @return Date of submission for the suggestion
     */
    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    /**
     * Sets date of submission of suggestion
     * @param dateSubmitted New date of submission for the suggestion
     */
    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    // Getter and setter for status

    /**
     * Get status of suggestion: Is it approved, rejected, or pending?
     * @return status of suggestion
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status of suggestion
     * Scenarios: Initialising status(pending), after processing (approved/rejected)
     * @param status New status of suggestion
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    // Getter and setter for processed

    /**
     * Gets True or False on whether suggestion is processed or not
     * @return True or False on whether suggestion is processed or not
     */
    public boolean getProcessed() {
        return processed;
    }

    /**
     * Set processing status on suggestion
     * @param processed New processing status
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * Get hashmap containing subjects matched with corresponding suggestion objects
     * @return hashmap containing subjects matched with corresponding suggestion objects
     */
    // Getter for suggestionHashMap
    public static HashMap<String, Suggestion> getSuggestionHashMap() {
        return suggestionHashMap;
    }

    /**
     * Retrieve suggestion object via suggestion subject
     * @param subject Requested subject of suggestion
     * @return Suggestion object matched to the given subject
     */
    public static Suggestion getSuggestionBySubject(String subject) {
        return suggestionHashMap.get(subject);
    }

    /**
     * Get list of suggestion objects
     * @return list of suggestion objects
     */
    public static ArrayList<Suggestion> getSuggestionArrayList() {
        return suggestionArrayList;
    }

    /**
     * Sets list of suggestion objects
     * @param suggestionArrayList Specified list of suggestion objects
     */
    public static void setSuggestionArrayList(ArrayList<Suggestion> suggestionArrayList) {
        Suggestion.suggestionArrayList = suggestionArrayList;
    }
}
