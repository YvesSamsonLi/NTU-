package com.example.cms.DisplayOptions;
import com.example.cms.Camp.Camp;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Implements specific sort method via total number of committee slots per camp. Sorts from smallest to largest number (TO BE CONFIRMED).
 */
public class SortByCommitteeSlots extends DisplayBySort{
	private ArrayList<Camp> campAfterSorting = new ArrayList<>();

	public ArrayList<Camp> Sorting (ArrayList<Camp> campList){
		if(campList == null){
			System.out.println("No camp available yet");
			return null;
		}
		else{
			Collections.sort(campList, campCommitteeSlotsComparator.thenComparing(SortByName_Default.campNameComparator));
			campAfterSorting.clear();
			campAfterSorting.addAll(campList);
			return campAfterSorting;
		}
	}




	public static Comparator<Camp> campCommitteeSlotsComparator = new Comparator<>(){
		public int compare(Camp c1, Camp c2){
			int campCommitteeSlot1 = c1.getCommitteeSlots();
			int campCommitteeSLot2 = c2.getCommitteeSlots();

			return -1*Integer.compare(campCommitteeSLot2, campCommitteeSlot1);
		}
	};

}