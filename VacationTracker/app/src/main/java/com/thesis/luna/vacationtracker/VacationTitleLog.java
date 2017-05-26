package com.thesis.luna.vacationtracker;

/**
 * Created by Luna on 4/28/17.
 */


public class VacationTitleLog
{
    //private variables
    private int id;
    private String vacationTit;

    // constructor
    public VacationTitleLog()
    {

    }

    // constructor
    public VacationTitleLog(String vacationTit)
    {
        this.vacationTit = vacationTit;

    }

    // setting id
    public void setID(int id)
    {
        this.id = id;
    }
    // getting ID
    public int getID()
    {
        return id;
    }

    // setting vacation title
    public void setTitle(String vacationTit)
    {
        this.vacationTit = vacationTit;
    }
    // getting vacation title
    public String getTitle()
    {
        return vacationTit;
    }







}
