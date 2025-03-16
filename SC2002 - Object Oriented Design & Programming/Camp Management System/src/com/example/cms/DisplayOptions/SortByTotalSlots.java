package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implements specific sort method via total number of attendee slots per camp. Sorts from smallest to largest number (TO BE CONFIRMED).
 */
public class SortByTotalSlots extends DisplayBySort{
	private ArrayList<Camp> campAfterSorting = new ArrayList<>();

	public ArrayList<Camp> Sorting(ArrayList<Camp> campList) {
		if(campList == null){
			System.out.println("No camp available yet");
			return null;
		}else {
			Collections.sort(campList, campSlotsComparator.thenComparing(SortByName_Default.campNameComparator));
			campAfterSorting.clear();
			campAfterSorting.addAll(campList);
			return campAfterSorting;
		}

	}

	public static Comparator<Camp> campSlotsComparator = new Comparator<>(){
		public int compare(Camp c1, Camp c2){
			int c1SlotsLeft = c1.getRemainingSlots();
			int c2SlotsLeft = c2.getRemainingSlots();
			return -1* Integer.compare(c1SlotsLeft, c2SlotsLeft);
		}
	};
}