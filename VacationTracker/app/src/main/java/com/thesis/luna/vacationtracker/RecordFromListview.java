package com.thesis.luna.vacationtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordFromListview extends AppCompatActivity {

    ImageView imageView;
    TextView iamat,place,add,address,note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_from_listview);


        imageView = (ImageView)findViewById(R.id.imageViewHere);



        iamat = (TextView)findViewById(R.id.Iamat);
        place = (TextView)findViewById(R.id.placeT);
        add = (TextView)findViewById(R.id.addressT);
        address = (TextView)findViewById(R.id.actualAddress);
        note = (TextView)findViewById(R.id.noteT);

        place.setText(Singleton.getInstance().getPlace());

        address.setText(Singleton.getInstance().getAddress());

        note.setText(Singleton.getInstance().getNote());


        imageView = (ImageView)findViewById(R.id.imageViewHere);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);


        imageView.setImageBitmap(SingletonModel.getInstance().getImage());

    }


}
