package com.example.cms.generate_report;

import com.example.cms.Camp.Camp;

import com.example.cms.Format;
/**
 * For concrete subclasses to implement and override with specific generate report methods,
 * To generate different reports for different personnel.
 */
public interface GenerateReport {
    void generateAttendeeList(Camp camp, Format format);
    void generateCommitteeList(Camp camp, Format format);

}

