package com.thesis.luna.vacationtracker;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class TabbedActivity extends TabActivity {
    TextView tv_from, tv_to, tv_start, tv_end;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);


        //Assign id to Tabhost.
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        viewItem();

        //Creating tab menu.
        final TabHost.TabSpec TabMenu1 = tabHost.newTabSpec("First tab");
        final TabHost.TabSpec TabMenu2 = tabHost.newTabSpec("Second Tab");
        final TabHost.TabSpec TabMenu3 = tabHost.newTabSpec("Third Tab");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("Trip");
        //Set tab 1 activity to tab 1 menu.
        //TabMenu1.setContent(new Intent(this,TabActivity_1.class));
        TabMenu1.setContent(R.id.tab1);


        //Setting up tab 2 name.
        TabMenu2.setIndicator("Map");
        //Set tab 3 activity to tab 1 menu.
        //TabMenu2.setContent(new Intent(this, TabActivity_2.class));
        //TabMenu2.setContent(R.id.tab2);
        //TabMenu2.setContent(new Intent(this,DialogInMap.class));
        TabMenu2.setContent(new Intent(this,TabActivity_2.class));




        //Setting up tab 2 name.
        TabMenu3.setIndicator("Record");
        //Set tab 3 activity to tab 3 menu.
        //TabMenu3.setContent(R.id.tab3);
        TabMenu3.setContent(new Intent(this, TabActivity_3.class));




        //Adding tab1, tab2, tab3 to tabhost view.

        tabHost.addTab(TabMenu1);
        tabHost.addTab(TabMenu2);
        tabHost.addTab(TabMenu3);




    }

    private void viewItem() {

        tv_from = (TextView)findViewById(R.id.tv_from);
        tv_to = (TextView)findViewById(R.id.tv_to);
        tv_start = (TextView)findViewById(R.id.tv_start);
        tv_end = (TextView)findViewById(R.id.tv_end);

        // tv = (TextView) findViewById(R.id.textView);

        Intent intent2 = getIntent();
        Bundle bd = intent2.getExtras();

        if(bd != null)
        {
            String fr = bd.getString("f");
            String toooo= bd.getString("t");
            String st = bd.getString("s");
            String en = bd.getString("e");


            tv_from.setText("From: " + SingletonFromTo.getInstance().getFrom());
            tv_to.setText("To: " + SingletonFromTo.getInstance().getTo());
            //tv_from.setText("From " + fr);
            //tv_to.setText("To " + toooo);
            tv_start.setText("Start: " +SingletonFromTo.getInstance().getStart());
            //tv_start.setText("Start " + st);

            // tv_end.setText("End " + en);

            tv_end.setText("End: " + SingletonFromTo.getInstance().getEnd());

//            String f = bd.getString("f");
            //          tv.setText(f);
        }
    }


}




