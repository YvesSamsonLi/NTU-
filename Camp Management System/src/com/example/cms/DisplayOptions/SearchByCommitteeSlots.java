package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;

/**
 * Implements specific search method via total number of committee slots in each camp.
 */
public class SearchByCommitteeSlots extends DisplayBySearch<Integer, ArrayList<Camp>>{
    private ArrayList<Camp> campAfterSearch = new ArrayList<>();

    /**
     * Search method via total number of committee slots per camp. If it is larger or equals to the value specified by user, include it in the search.
     * @param committeeSlot
     * @param campList
     * @return list of camps with total number of committee slots greater than value specified by user
     */
    public ArrayList<Camp> Searching(Integer committeeSlot, ArrayList<Camp> campList){
        campAfterSearch.clear();
        if (campList == null) {
            System.out.println("No camp available yet");
            return null;
        }else {
            for(Camp camp:campList) {
                if (camp.getCommitteeSlots() >= committeeSlot) {
                    campAfterSearch.add(camp);
                }
            }
        }
        return campAfterSearch;
    }
}