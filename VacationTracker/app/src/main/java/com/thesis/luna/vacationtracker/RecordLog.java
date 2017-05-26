package com.thesis.luna.vacationtracker;

/**
 * Created by Luna on 4/28/17.
 */


public class RecordLog
{
    //private variables
    private int ID;
    private String place;
    private String address;
    private String note;

    //empty constructor
    public RecordLog()
    {

    }

    //constructor
    public RecordLog(int ID, String place, String address, String note)
    {
        this.ID = ID;
        this.place = place;
        this.address = address;
        this.note = note;
    }

    //constructor
    public RecordLog(String place, String address, String note)
    {
        this.place = place;
        this.address = address;
        this.note = note;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }
    public int getID()
    {
        return ID;
    }


    public void setPlace(String place)
    {
        this.place = place;
    }
    public String getPlace()
    {
        return place;
    }



    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getAddress()
    {
        return address;
    }



    public void setNote(String note)
    {
        this.note = note;
    }

    public String getNote()
    {
        return note;
    }





}
