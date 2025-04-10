package com.example.cms.DisplayOptions;
import com.example.cms.Camp.Camp;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * Implements specific sort method via location of camp. Sorts from smallest to largest starting Alphabet (TO BE CONFIRMED).
 */
public class SortByLocation extends DisplayBySort{
	private ArrayList<Camp> campAfterSorting = new ArrayList<>();
	public ArrayList<Camp> Sorting(ArrayList<Camp> campList) {
		if(campList == null){
			System.out.println("No camp available yet");
			return null;
		}else {
			Collections.sort(campList, campLocationComparator.thenComparing(SortByName_Default.campNameComparator));
			campAfterSorting.clear();
			campAfterSorting.addAll(campList);
			return campAfterSorting;
		}
	}

	public static Comparator<Camp> campLocationComparator = new Comparator<>(){
		public int compare(Camp c1, Camp c2){
			String campLocation1 = c1.getLocation().toUpperCase();
			String campLocation2 = c2.getLocation().toUpperCase();

			return campLocation1.compareTo(campLocation2);
		}
	};

}