package com.example.cms.Enquiries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Entity class to handle each enquiry of a camp.
 * Has get and set methods for information about each enquiry, as well as a method to print all the information about the enquiry.
 */
public class Enquiry {
    private String enquiry_Subject; 
    private String submitter;
    private String content;
    private LocalDate dateSent;
    private String reply;
    private boolean processed;

    // Initialize a HashMap to store individual student inquiries with enquiry_Subject as the key
    private static HashMap<String, Enquiry> enquiryHashMap = new HashMap<>();

    private static ArrayList<Enquiry> enquiryArrayList = new ArrayList<>();

    public static void printAllEnquiryInfo(Enquiry enquiry) {
        System.out.println("Submitted by: " + enquiry.getSubmitter());
        System.out.println("Date sent: " + enquiry.getDateSent().toString());
        System.out.println("Enquiry content: " + enquiry.getContent());
        System.out.println("Processed: " + enquiry.isProcessed());
        if(enquiry.getReply() != null){
            System.out.println("Reply: " + enquiry.getReply());
        }
    }

    public Enquiry(String enquiry_Subject, String content, LocalDate dateSent, String submitter, String reply, boolean processed) {
        this.enquiry_Subject = enquiry_Subject;
        this.content = content;
        this.dateSent = dateSent;
        this.submitter = submitter;
        this.reply = reply;
        this.processed = processed;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getEnquiry_Subject() {
        return enquiry_Subject;
    }

    public void setEnquiry_Subject(String enquiry_Subject) {
        this.enquiry_Subject = enquiry_Subject;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public static ArrayList<Enquiry> getEnquiryArrayList() {
        return enquiryArrayList;
    }

    public static void setEnquiryArrayList(Enquiry enquiry) {
        Enquiry.enquiryArrayList.add(enquiry);
    }

    public static HashMap<String, Enquiry> getEnquiryHashMap() {
        return enquiryHashMap;
    }

    public static Enquiry getEnquiryBySubject(String enquirySubject) {
        return enquiryHashMap.get(enquirySubject);
    }

}