package com.thesis.luna.vacationtracker;


/**
 * Created by Luna on 4/5/17.
 */

public class VacationInfoLog
{

    //private variables
    private int id;
    private String vacationFrom;
    private String vacationTo;
    private String vacationStart;
    private String vacationEnd;

    public VacationInfoLog()
    {

    }

    // constructor
    public VacationInfoLog(int id, String vacationFrom, String vacationTo, String vacationStart, String vacationEnd){
        this.id = id;
        this.vacationFrom = vacationFrom;
        this.vacationTo = vacationTo;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;

    }

    // constructor
    public VacationInfoLog(String vacationFrom, String vacationTo, String vacationStart, String vacationEnd){
        this.vacationFrom = vacationFrom;
        this.vacationTo = vacationTo;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }
    // getting ID
    public int getID()
    {
        return id;
    }


    // setting from location
    public void setFrom(String vacationFrom)
    {
        this.vacationFrom = vacationFrom;
    }
    // getting from location
    public String getFrom()
    {
        return vacationFrom;
    }


    // setting to location
    public void setTo(String vacationTo)
    {
        this.vacationTo = vacationTo;
    }
    // getting to location
    public String getTo()
    {
        return vacationTo;
    }


    // setting start date
    public void setStart(String vacationStart)
    {
        this.vacationStart = vacationStart;
    }
    // getting to location
    public String getStart()
    {
        return vacationStart;
    }

    // setting end date
    public void setEnd(String vacationEnd)
    {
        this.vacationEnd = vacationEnd;
    }
    // getting to location
    public String getEnd()
    {
        return vacationEnd;
    }



}
