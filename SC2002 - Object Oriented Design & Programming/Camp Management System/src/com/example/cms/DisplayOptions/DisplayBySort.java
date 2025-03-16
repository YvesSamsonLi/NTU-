package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;

/**
 * Abstract class for subclasses to implement specific sorting methods.
 */
public abstract class DisplayBySort {
    /**
     * Abstract method for subclasses to override with specific sorting methods.
     * @param campList
     * @return
     */
    public abstract ArrayList<Camp> Sorting(ArrayList<Camp> campList);
}