package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Enables functionality to sort camps based on specific criteria from user input.
 */
public class SortApp {
    /**
     * Implements functionality to sort camps based on specific criteria from user input.
     * via a menu, combining all the different sorting methods
     * @param campArrayList
     * @return customised list of camps according to user specified sorting
     */
    public static ArrayList<Camp> startSorting(ArrayList<Camp> campArrayList){
        Scanner input = new Scanner(System.in);
        System.out.println("=== what camp feature would you like to sort for?");
        System.out.println("1. Camp location");
        System.out.println("2. Camp start date");
        System.out.println("3. Camp registration close date");
        System.out.println("4. Camp remaining committee slots");
        System.out.println("5. Camp remaining attendee slots");
        System.out.println("6. Camp eligible user group");
        int choice;
        do{
            choice = input.nextInt();
            if(choice != 1 && choice != 2 && choice != 3){
                System.out.println("Invalid input, please enter a valid option (1, 2, 3)");
            }
        }while(choice != 1 && choice != 2 && choice != 3);

        DisplayBySort sorter;

        switch(choice){
            case(1):
                System.out.println("Sorting by camp location...");
                sorter = new SortByLocation();
                break;
            case(2):
                System.out.println("Sorting by camp starting date...");
                sorter = new SortByCampDate();
                break;
            case(3):
                System.out.println("Sorting by camp registration close date...");
                sorter = new SortByRegCloseDate();
                break;
            case(4):
                System.out.println("Sorting by remaining committee slots...");
                sorter = new SortByCommitteeSlots();
                break;
            case(5):
                System.out.println("Sorting by remaining attendee slots...");
                sorter = new SortByTotalSlots();
                break;
            case(6):
                System.out.println("Sorting by eligible user group...");
                sorter = new SortByUserGroup();
                break;
            default:
                return null;
        }

        return sorter.Sorting(campArrayList);
    }
}