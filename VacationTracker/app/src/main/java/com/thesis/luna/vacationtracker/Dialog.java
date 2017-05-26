package com.thesis.luna.vacationtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Dialog extends AppCompatActivity {

    /** Called when the activity is first created. */

    // this context will use when we work with Alert Dialog
    final Context context = this;

    // just for test, when we click this button, we will see the alert dialog.
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        button = (Button) findViewById(R.id.button_show_alert_dialog);
        //Create onclick listener class

        open();
    }
    public void open()
    {
        button.setOnClickListener(new View.OnClickListener() {
            //When you click the button, Alert dialog will be showed
            public void onClick(View v) {

                /* Alert Dialog Code Start*/
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Alert DIalog With EditText"); //Set Alert dialog title here
                alert.setMessage("Enter your Name Here"); //Message here

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // String value = input.getText().toString();
                        // Do something with value!
                        //You will get input data in this variable.
                        Dialog.this.finish();

                    }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }
        });

    }
}

