package com.thesis.luna.vacationtracker;

/**
 * Created by Luna on 5/4/17.
 */

public class SingletonFromTo {

    private String from;
    private String to;
    private String start;
    private String end;
    private static SingletonFromTo instance = null;

    private SingletonFromTo()
    {

    }

    public static SingletonFromTo getInstance() {
        if (instance == null) {
            instance = new SingletonFromTo();
        }
        return instance;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return this.from;
    }


    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return this.to;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return this.start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return this.end;
    }
}

