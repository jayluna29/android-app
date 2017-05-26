package com.thesis.luna.vacationtracker;

import android.graphics.Bitmap;

/**
 * Created by Luna on 5/1/17.
 */

public class SingletonModel {

    private Bitmap Image;
    private static SingletonModel instance = null;

    private SingletonModel()
    {

    }

    public static SingletonModel getInstance() {
        if (instance == null) {
            instance = new SingletonModel();
        }
        return instance;
    }

    public void setImage(Bitmap ImageIn) {
        this.Image = ImageIn;
    }

    public Bitmap getImage() {
        return this.Image;
    }
}
