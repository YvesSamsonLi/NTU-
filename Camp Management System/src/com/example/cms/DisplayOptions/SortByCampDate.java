package com.example.cms.DisplayOptions;
import com.example.cms.Camp.Camp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implements specific sort method via camp date. Sorts from earliest to latest camp starting date (TO BE CONFIRMED).
 */
public class SortByCampDate extends DisplayBySort{
    private ArrayList<Camp> campAfterSorting = new ArrayList<>();

    /**
     *
     * @param campList
     * @return
     */
    public ArrayList<Camp> Sorting(ArrayList<Camp> campList) {
        if (campList.isEmpty()) {
            System.out.println("No camp available yet");
            return null;
        } else {
            Collections.sort(campList, campDateComparator.thenComparing(SortByName_Default.campNameComparator));
            campAfterSorting.clear();
            campAfterSorting.addAll(campList);
            return campAfterSorting;
        }
    }

    public static Comparator<Camp> campDateComparator = new Comparator<>(){
        public int compare(Camp c1, Camp c2){
            LocalDate camp1Date = c1.getCampDates().get(0);
            LocalDate camp2Date = c2.getCampDates().get(0);

            return camp1Date.compareTo(camp2Date);
        }
    };

}