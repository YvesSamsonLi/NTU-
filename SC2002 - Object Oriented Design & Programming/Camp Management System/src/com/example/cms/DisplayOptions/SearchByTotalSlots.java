package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;

/**
 * Implements specific search method via total number of attendee slots in each camp.
 */
public class SearchByTotalSlots extends DisplayBySearch<Integer, ArrayList<Camp>>{
    private ArrayList<Camp> campAfterSearch = new ArrayList<>();

    /**
     * Search method via total number of attendees slots per camp. If it is larger or equals to the value specified by user, include it in the search.
     * @param totalSlots
     * @param campList
     * @return list of camps with total number of attendee slots greater than value specified by user
     */
    public ArrayList<Camp> Searching(Integer totalSlots, ArrayList<Camp> campList){
        campAfterSearch.clear();
        if (campList == null) {
            System.out.println("No camp available yet");
            return null;
        }else {
            for(Camp camp:campList) {
                if (camp.getTotalSlots() >= totalSlots) {
                    campAfterSearch.add(camp);
                }
            }
        }
        return campAfterSearch;
    }
}