package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;
import com.example.cms.Faculty;

import java.util.ArrayList;

/**
 * Implements specific search method via user group. ie, which school(s) the camp belong to.
 */
public class SearchByUserGroup extends DisplayBySearch<Faculty, ArrayList<Camp>>{
    private ArrayList<Camp> campAfterSearch = new ArrayList<>();

    /**
     * Search method via school(s) the camp belong to. If there is a match with the user input, include the camp in the list.
     * @param userGroup
     * @param campList
     * @return list of camps with school(s) matching that of user input
     */
    public ArrayList<Camp> Searching(Faculty userGroup, ArrayList<Camp> campList){
        campAfterSearch.clear();
        if (campList == null) {
            System.out.println("No camp available yet");
            return null;
        }else {
            for(Camp camp:campList) {
                for (int i = 0; i < camp.getUserGroup().size(); i++)
                    if (camp.getUserGroup().get(i).compareTo(userGroup) == 0) {
                        campAfterSearch.add(camp);
                        break;
                    }
            }
        }
        return campAfterSearch;
    }
}