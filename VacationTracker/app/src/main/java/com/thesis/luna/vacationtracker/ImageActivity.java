package com.thesis.luna.vacationtracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends Activity {
    ImageView i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        i = (ImageView)findViewById(R.id.captureImage);

        i.setImageBitmap(SingletonModel.getInstance().getImage());

    }
}
