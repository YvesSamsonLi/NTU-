package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements search by registration closing date.
 */
public class SearchByRegCloseDate extends DisplayBySearch<LocalDate, ArrayList<Camp>>{
    private ArrayList<Camp> campAfterSearch = new ArrayList<>();

    /**
     * Method that searches via registration closing date. Unlike CampDate which takes on a range of dates, registration closing date only takes one date value.
     * @param regCloseDate
     * @param campList
     * @return Customised list of camps according to the registration closing date specified by user input.
     */
    public ArrayList<Camp> Searching(LocalDate regCloseDate, ArrayList<Camp> campList){
        campAfterSearch.clear();
        if (campList == null) {
            System.out.println("No camp available yet");
            return null;
        }else {
            for(Camp camp:campList) {
                if (camp.getRegCloseDate().compareTo(regCloseDate) == 0) {
                    campAfterSearch.add(camp);
                }
            }
        }
        return campAfterSearch;
    }
}