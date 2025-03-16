package com.example.cms;

/**
 * Entity class to handle information on CSV data file paths
 */
public class FilePath {
    final String STUDFILENAME = "D:\\GitHub\\OOP-camp-management-system\\student.csv";
    final String STAFFFILENAME = "D:\\GitHub\\OOP-camp-management-system\\staff.csv";

    /**
     * Gets file path of CSV containing all the student user information
     * @return student.csv file path
     */
    public String getSTUDFILENAME() {
        return STUDFILENAME;
    }

    /**
     * Gets file path of CSV containing all the staff user information
     * @return staff.csv file path
     */
    public String getSTAFFFILENAME() {
        return STAFFFILENAME;
    }
}