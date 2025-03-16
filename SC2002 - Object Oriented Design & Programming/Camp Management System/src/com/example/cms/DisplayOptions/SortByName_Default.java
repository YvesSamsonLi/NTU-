package com.example.cms.DisplayOptions;
import com.example.cms.Camp.Camp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implements specific sort method via camp name. Sorts from smallest to largest starting Alphabet (TO BE CONFIRMED).
 */
public class SortByName_Default extends DisplayBySort{
	private ArrayList<Camp> campAfterSorting = new ArrayList<>();
	public ArrayList<Camp> Sorting(ArrayList<Camp> campList){
		if (campList == null) {
			System.out.println("No camp available yet");
			return null;
		} else {
			Collections.sort(campList, campNameComparator);
			campAfterSorting.clear();
			campAfterSorting.addAll(campList);
			return campAfterSorting;
		}
	}

	public static Comparator<Camp> campNameComparator = new Comparator<>(){
		public int compare(Camp c1, Camp c2){
			String campName1 = c1.getCampName().toUpperCase();
			String campName2 = c2.getCampName().toUpperCase();

			return campName1.compareTo(campName2);
		}
	};

}