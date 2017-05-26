package com.thesis.luna.vacationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView lv;
    EditText nameTxt;
    ImageView savebtn;
    ArrayList<String> arr = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arr);


        //reference to the database to make sure the vacation titles are stored
        db = new DatabaseHelper(this);


        //edit text to enter a vacation
        //--XML is vacationTitleTB--//
        nameTxt = (EditText) findViewById(R.id.vacationTitleTB);

        //save button to save into the listview later on
        //--XML is createVacationTitleButton--//
        savebtn = (ImageView) findViewById(R.id.planeimage);


        //list view to store all of the vacation titles in, as long as the emulator stays running
        //--XML is vacationTitlesListView--//
        lv = (ListView) findViewById(R.id.vacationTitlesListView);
        lv.setBackgroundColor(Color.LTGRAY);


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inserting the vacation title into the database
                System.out.println(nameTxt.getText().toString());

                //getting the vacation title name
                String getText = nameTxt.getText().toString();
                //checking to make sure that something was typed into the edit text,but is not the same edit text as already entered
                if (arr.contains(getText)) {
                    TastyToast.makeText(getBaseContext(), "Same vacation?? Click on the one you already made. Just add onto it.", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                }
                //checking to make sure that the edit text is not left blank
                else if (getText == null || getText.trim().equals("")) {
                    TastyToast.makeText(getBaseContext(), "Excuse me, you need a Vacation Title for your vacation.", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                }
                //otherwise add this to the array list
                else {
                    db.insertData2(new VacationTitleLog(nameTxt.getText().toString()));
                    arr.add(getText);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(MainActivity.this, Main.class);
                //pass the vacation title to the next page
                intent1.putExtra("vacationtitlenamed", nameTxt.getText().toString());
                //launch the intent to Main
                startActivity(intent1);


            }
        });


    }
}
