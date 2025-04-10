package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implements specific sorting method via user group. ie, which school(s) the camp belong to. Sorts from smallest to largest starting Alphabet (TO BE CONFIRMED).
 */
public class SortByUserGroup extends DisplayBySort{
	private ArrayList<Camp> campAfterSorting = new ArrayList<>();

	public ArrayList<Camp> Sorting(ArrayList<Camp> campList) {

		if(campList == null){
			System.out.println("No camp available yet");
			return null;
		}else {
			Collections.sort(campList, campUserGroupComparator.thenComparing(SortByName_Default.campNameComparator));
			campAfterSorting.clear();
			campAfterSorting.addAll(campList);
			return campAfterSorting;
		}
	}


	public static Comparator<Camp> campUserGroupComparator = new Comparator<>(){
		public int compare(Camp c1, Camp c2){
			int c1UserGroup = c1.getUserGroup().size();
			int c2UserGroup = c2.getUserGroup().size();
			return -1* Integer.compare(c1UserGroup, c2UserGroup);
		}
	};
}