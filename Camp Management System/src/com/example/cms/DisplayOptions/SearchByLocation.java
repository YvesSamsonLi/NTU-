package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;

/**
 * Implements searching for camps via location.
 * Does not require full string matching due to use of contains() method, but requires that the keyword be spelled correctly and exactly.
 */
public class SearchByLocation extends DisplayBySearch<String, ArrayList<Camp>>{
    private ArrayList<Camp> campAfterSearch = new ArrayList<>();

    /**
     * Method that includes camp in search if its location matches with user input
     * Does not require full string matching due to use of contains() method, but requires that the keyword be spelled correctly and exactly.
     * @param location
     * @param campList
     * @return list of camps where location matches with user input
     */
    public ArrayList<Camp> Searching(String location, ArrayList<Camp> campList){
        campAfterSearch.clear();
        if (campList == null) {
            System.out.println("No camp available yet");
            return null;
        }else {
            for(Camp camp:campList) {
                if (camp.getLocation().contains(location)) {
                    campAfterSearch.add(camp);
                }
            }
        }
        return campAfterSearch;
    }
}