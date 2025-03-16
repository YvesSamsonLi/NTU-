package com.example.cms.Camp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents entity class campData to hold and process data of camps.
 */
public class campData {
	private static ArrayList<Camp> campList = new ArrayList<>();
	private static HashMap<String, Camp> campHashMap= new HashMap<String, Camp>();

	/**
	 * Gets ArrayList of Camp objects.
	 * @return list of camps.
	 */
	public static ArrayList<Camp> getCampList() {
		return campList;
	}

	/**
	 * Adds camp object to existing ArrayList of camp objects.
	 * @param camp Camp to add
	 */
	public static void setCampList(Camp camp) {campList.add(camp);}

	/**
	 * Adds a key value pair (camp name, same camp object) of a camp into the existing hashmap.
	 * @param name name of camp
	 * @param camp same camp object
	 */
	public static void addCampToMap(String name, Camp camp){
		campHashMap.put(name, camp);
	}

	/**
	 * Gets hashmap of camp names (key) tied to their corresponding camp objects (value)
	 * @return hashmap of camp names (key) tied to their corresponding camp objects (value)
	 */
	public static HashMap<String, Camp> getCampHashMap() {
		return campHashMap;
	}
}