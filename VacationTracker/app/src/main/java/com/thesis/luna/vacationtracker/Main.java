package com.thesis.luna.vacationtracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Calendar;
import java.util.List;


public class Main extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper myDB;
    Button btnDatePicker, btnDatePicker2, btnViewAll, btnDelete, forward, backward;
    TextView title, from, to;
    EditText txtDate, txtDate2;//,  id;
    private int mYear, mMonth, mDay;
    private int mYear2, mMonth2, mDay2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myDB = new DatabaseHelper(this);


        btnDatePicker = (Button) findViewById(R.id.in_button);
        btnDatePicker2 = (Button) findViewById(R.id.out_button);


        btnDatePicker.setOnClickListener(this);
        btnDatePicker2.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tit);

        forward = (Button) findViewById(R.id.forwardBtn);
        backward = (Button) findViewById(R.id.backBtn);


        Intent intent1 = getIntent();
        Bundle bd = intent1.getExtras();
        if (bd != null) {
            String vt = bd.getString("vacationtitlenamed");

            title.setText("Vacation Title " + "\n" + vt);

        }


        from = (TextView) findViewById(R.id.editText);
        to = (TextView) findViewById(R.id.editText2);

        txtDate = (EditText) findViewById(R.id.txtDate);
        txtDate2 = (EditText) findViewById(R.id.txtDate2);

        btnViewAll = (Button) findViewById(R.id.viewAllButton);
        btnDelete = (Button) findViewById(R.id.deleteButton);




        PlaceAutocompleteFragment places = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                from.setText(place.getName().toString());
                System.out.println(place.getName());
                //from.setVisibility(View.INVISIBLE);
                SingletonFromTo.getInstance().setFrom(from.getText().toString());
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();

            }


        });

        PlaceAutocompleteFragment places2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        places2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place2) {

                to.setText(place2.getName().toString());
                System.out.println(place2.getName());
                //to.setVisibility(View.INVISIBLE);
                SingletonFromTo.getInstance().setTo(to.getText().toString());


            }

            @Override
            public void onError(Status status2) {
                Toast.makeText(getApplicationContext(), status2.toString(), Toast.LENGTH_SHORT).show();

            }

        });


        previous();
        addData();
        viewAll();
        deleteAll();


    }
    private void previous() {
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Main.super.onBackPressed();

            }
        });

    }


    public void deleteAll() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteAllData();
            }
        });

    }


    public void addData() {
        LoadPreferences();
        forward.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if (from.getText().length() == 0) {
                    TastyToast.makeText(getBaseContext(), "You need to enter in a from location", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);

                } else if (to.getText().length() == 0) {
                    TastyToast.makeText(getBaseContext(), "You need to enter in a to location", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);

                } else if (txtDate.getText().length() == 0) {
                    TastyToast.makeText(getBaseContext(), "You need to enter in a start date", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);


                } else if (txtDate2.getText().length() == 0) {
                    TastyToast.makeText(getBaseContext(), "You need to enter in a end date", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } else {

                    System.out.println(txtDate.getText().toString());

                    System.out.println(txtDate2.getText().toString());

                    myDB.insertData(new VacationInfoLog(from.getText().toString(), to.getText().toString(), txtDate.getText().toString(), txtDate2.getText().toString()));



                    /*List<VacationInfoLog> contacts = myDB.getAllData();
                    for (VacationInfoLog cn : contacts) {
                        String name1 = "Id: " + cn.getID() + " ,From: " + cn.getFrom() + " ,To: " + cn.getTo() + " ,Start: " + cn.getStart() + " ,End: " + cn.getEnd();
                        // Writing Contacts to log
                        Log.d("Vacation: ", name1);
                        // myDB.getAllData();
                        //System.out.println(myDB.getAllData());
                    }*/



                    //store the data here
                    Intent intent2 = new Intent(Main.this, TabbedActivity.class);
                    intent2.putExtra("title", title.getText().toString());
                    SingletonFromTo.getInstance().setFrom(from.getText().toString());
                    SingletonFromTo.getInstance().setTo(to.getText().toString());
                    SingletonFromTo.getInstance().setStart(txtDate.getText().toString());
                    SingletonFromTo.getInstance().setEnd(txtDate2.getText().toString());

                    SavePreferences("From", from.getText().toString());

                    SavePreferences("To", to.getText().toString());

                    SavePreferences("Start", txtDate.getText().toString());

                    SavePreferences("End", txtDate2.getText().toString());


                    startActivity(intent2);


                }

            }
        });

    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<VacationInfoLog> res = myDB.getAllData();


                StringBuffer buffer = new StringBuffer();
                for (VacationInfoLog info : res) {
                    buffer.append("ID : " + info.getID() + "\n");
                    buffer.append("From : " + info.getFrom() + "\n");
                    buffer.append("To : " + info.getTo() + "\n");
                    buffer.append("Start : " + info.getStart() + "\n");
                    buffer.append("End : " + info.getEnd() + "\n\n");
                }


                showMessage("Vacations", buffer.toString());
            }
        });

    }


    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {
            // Get Current Date
            java.util.Calendar c = Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            txtDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == btnDatePicker2) {
            // Get Current Date
            java.util.Calendar c2 = java.util.Calendar.getInstance();
            mYear2 = c2.get(java.util.Calendar.YEAR);
            mMonth2 = c2.get(java.util.Calendar.MONTH);
            mDay2 = c2.get(java.util.Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    txtDate2.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);


                }
            }, mYear2, mMonth2, mDay2);

            datePickerDialog.show();

        }




    }



    private void SavePreferences(String key, String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void LoadPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String f = preferences.getString("From", "");
        String t = preferences.getString("To", "");
        String s = preferences.getString("Start", "");
        String e = preferences.getString("End", "");

        from.setText(f);
        to.setText(t);
        txtDate.setText(s);
        txtDate2.setText(e);
    }



}


