package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;
import com.example.cms.Camp.campData;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Boundary class to display camps
 */
public class DisplayApp {

    // the method returns null either there is no camp or the user exits, therefore the com.example.cms.main should go back.

    /**
     * Asks user how they want to view list of camps. Returns customised ArrayList of camps according to user preference.
     * the method returns null either there is no camp or the user exits, therefore the com.example.cms.main should go back.
     * @return list of camps according to user preference
     */
    public static ArrayList<Camp> viewAllCamp() {
        DisplayBySort aOb = new SortByName_Default();
        if (campData.getCampList().isEmpty()) {
            System.out.println("No camp is available to view");
            return null;
        } else {
            Scanner input = new Scanner(System.in);
            int choice;
            System.out.println("Please choose how to view camps:");
            System.out.println("1. Default: alphabetical order of camp name");
            System.out.println("2. Search for keywords");
            System.out.println("3. Exiting...");
            do{
                choice = input.nextInt();
                if(choice != 1 && choice != 2 && choice != 3){
                    System.out.println("Invalid input, please enter a valid option (1, 2, 3)");
                }
            }while(choice != 1 && choice != 2 && choice != 3);

            switch (choice) {
                case (1):
                    return aOb.Sorting(campData.getCampList());
                case (2):
                    ArrayList<Camp> afterSearchCamp;
                    do {
                        afterSearchCamp = SearchApp.startSearch(campData.getCampList());
                        if (afterSearchCamp == null) {
                            System.out.println("Please choose a correct option");
                        }
                    } while (afterSearchCamp == null);
                    boolean sorting = false;
                    String sortingStr;
                    do {
                        System.out.println("Do you want to view the camp in any specific sequence i.e. sorting? Enter Yes or No");
                        sortingStr = input.nextLine();
                        if(sortingStr.equalsIgnoreCase("Yes")){
                            sorting = true;
                        }else if(sortingStr.equalsIgnoreCase("No")){
                            sorting = false;
                        }
                        else{
                            System.out.println("Please enter Yes or No");
                        }
                    }while(!sortingStr.equalsIgnoreCase("Yes") && !sortingStr.equalsIgnoreCase("No"));
                    if(!sorting){
                        return aOb.Sorting(afterSearchCamp);
                    }else {
                        ArrayList<Camp> afterSortCamp;
                        do {
                            afterSortCamp = SortApp.startSorting(afterSearchCamp);
                            if (afterSortCamp == null) {
                                System.out.println("Please choose a correct option");
                            }
                        } while (afterSortCamp == null);
                        return afterSearchCamp;
                    }

                case (3):
                    System.out.println("Action terminated by the user");
                    System.out.println("Exiting...");
                    return null;
                default:
                    System.out.println("Please insert a valid choice");
                    return null;
            }
        }
    }
}