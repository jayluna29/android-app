package com.thesis.luna.vacationtracker;

/**
 * Created by Luna on 5/3/17.
 */

public class Singleton {

    private String place;
    private String address;
    private String note;
    private static Singleton instance = null;
    private Singleton()
    {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
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
