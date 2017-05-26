package com.thesis.luna.vacationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TabActivity_1 extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> ad;
    TextView t;
    TextView d;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_1);

        ad = new ArrayAdapter<String>(TabActivity_1.this, android.R.layout.simple_list_item_1, listItems);


        t = (TextView) findViewById(R.id.textView);



        d = (TextView) findViewById(R.id.textVieww);

        lst = (ListView) findViewById(R.id.listView);
        lst.setBackgroundColor(Color.LTGRAY);


        Date e = new Date();

        DateFormat dateFormat = new SimpleDateFormat();

        d.setText(dateFormat.toString());
        d.setText(dateFormat.format(e) + " ");


        t.setText(Singleton.getInstance().getAddress());

        // create the ArrayList to store the titles of nodes

        add();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentThis = new Intent(TabActivity_1.this, RecordFromListview.class);

                //launch the intent to Main
                startActivity(intentThis);
            }
        });


    }

    public void add()
    {
        listItems.add(d.getText().toString() + "\n" + t.getText().toString());
        lst.setAdapter(ad);
        ad.notifyDataSetChanged();

    }



}
