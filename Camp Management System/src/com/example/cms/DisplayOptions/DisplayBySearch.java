package com.example.cms.DisplayOptions;

import com.example.cms.Camp.Camp;

import java.util.ArrayList;
/**
 * Abstract class for subclasses to implement specific search methods.
 * @param <T>
 * @param <U>
 */
public abstract class DisplayBySearch <T, U>{
    /**
     * Abstract method for subclasses to override with specific search methods.
     * @param campList
     * @param variableParameter
     * @return
     */
    public abstract ArrayList<Camp> Searching(T campList, U variableParameter);
    }

