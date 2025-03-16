package com.example.cms.DisplayOptions;
import com.example.cms.Camp.Camp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implements specific sort method via camp registration closing date. Sorts from earliest to latest date (TO BE CONFIRMED).
 */
public class SortByRegCloseDate extends DisplayBySort{
	private ArrayList<Camp> campAfterSorting = new ArrayList<>();

	public ArrayList<Camp> Sorting(ArrayList<Camp> campList) {
		if (campList == null) {
			System.out.println("No camp available yet");
			return null;
		} else {
			Collections.sort(campList, campRegDateComparator.thenComparing(SortByName_Default.campNameComparator));
			campAfterSorting.clear();
			campAfterSorting.addAll(campList);
			return campAfterSorting;
		}
	}
	public static Comparator<Camp> campRegDateComparator = new Comparator<>(){
		public int compare(Camp c1, Camp c2){
			LocalDate camp1Date = c1.getRegCloseDate();
			LocalDate camp2Date = c2.getRegCloseDate();

			return camp1Date.compareTo(camp2Date);
		}
	};

}