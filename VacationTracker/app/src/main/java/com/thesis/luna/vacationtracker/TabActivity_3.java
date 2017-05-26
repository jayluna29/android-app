package com.thesis.luna.vacationtracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.List;

public class TabActivity_3 extends AppCompatActivity {

    TextView placeText,addressText, captureLocationText,writeNoteText,captureText;
    EditText place , writeNote;
    ImageButton capturePhotoButton;
    Button saveButton, cancelButton,viewAll;
    ImageView image;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DatabaseHelper dbM;
    // GPSTracker class
    GPSTracker gps;
    Bitmap imageBitmap;

    Marker marker;
    GoogleMap mGoogleMap;



    TabHost t;

    DatabaseHelper d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_3);


        dbM = new DatabaseHelper(this);

        place = (EditText)findViewById(R.id.placeEdit);
        captureLocationText = (TextView) findViewById(R.id.captureLoc);
        writeNote = (EditText)findViewById(R.id.writeNoteEdit);

        image = (ImageView)findViewById(R.id.capturedImage);

        placeText = (TextView) findViewById(R.id.placeText);
        addressText = (TextView) findViewById(R.id.addressText);
        writeNoteText = (TextView) findViewById(R.id.writeNoteText);
        captureText = (TextView)findViewById(R.id.captureText);


        capturePhotoButton = (ImageButton)findViewById(R.id.captureRecordButton);
        saveButton = (Button)findViewById(R.id.saveRecordButton);
        cancelButton = (Button)findViewById(R.id.cancelRecordButton);
        viewAll = (Button)findViewById(R.id.buttonViewAll);

        d = new DatabaseHelper(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        gps = new GPSTracker(TabActivity_3.this);
        // check if GPS enabled
        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

           // final String originalText = "Your Location is - \n Latitude: " + latitude + "\nLongitude: " + longitude;
            final String o = gps.getCompleteAddressString(latitude,longitude);

            captureLocationText.setText("Capturing current location...");
            captureLocationText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    captureLocationText.setText(o);
                }
            }, 5000);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        capturePhoto();
        saveRecord();
        cancelRecord();
        viewAllRecords();




    }


    public void capturePhoto()
{
    capturePhotoButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    });

}




public void saveRecord()
{

    saveButton.setOnClickListener(new View.OnClickListener() {
        //When you click the button, Alert dialog will be showed
        public void onClick(View v) {



            if (place.getText().length() == 0) {

                TastyToast.makeText(getBaseContext(), "You need to enter in a place name", TastyToast.LENGTH_LONG,TastyToast.DEFAULT);

            } else {
                if (writeNote.getText().length() == 0) {
                    TastyToast.makeText(getBaseContext(), "You need to write a note so we know what you are doing here", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } else {


                    System.out.println(place.getText().toString());

                    System.out.println(captureLocationText.getText().toString());
                    System.out.println(writeNote.getText().toString());


                    d.insertData3(new RecordLog(place.getText().toString(), captureLocationText.getText().toString(), writeNote.getText().toString()));

                    Intent intent4 = new Intent(TabActivity_3.this,DialogInMap.class);
                    //pass the vacation title to the next page

                    intent4.putExtra("thistheplaceandnote", place.getText().toString());

                    intent4.putExtra("thistheplaceandnote2", writeNote.getText().toString());
                    Singleton.getInstance().setPlace(place.getText().toString());


                    SingletonModel.getInstance().setImage(imageBitmap);

                    Singleton.getInstance().setAddress(captureLocationText.getText().toString());

                    Singleton.getInstance().setNote(writeNote.getText().toString());



                    startActivity(intent4);

                }
            }









        }
    });


}


    public void dropThisMarkerOnMyCurrentLocation() throws IOException {

        //-------------

        gps = new GPSTracker(TabActivity_3.this);

        Geocoder currentLocation = new Geocoder(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        String loc = gps.getCompleteAddressString(latitude, longitude);

        List<Address> l = currentLocation.getFromLocationName(loc, 1);
        Address a = l.get(0);
        String local = a.getLocality();
        Toast.makeText(this, local, Toast.LENGTH_LONG).show();

        double la = a.getLatitude();
        double ln = a.getLongitude();
        goToLocationZoom(la, ln, 8);

        setMarker(local, la, ln);


        System.out.println(a.getLatitude());
        System.out.println(a.getLongitude());


        //-------------

    }


    private void setMarker(String locality, double lat, double lng) {
        //take this off to keep track of all the markers
        /*if(marker != null)
        {
            marker.remove();
        }*/

        //adding marker
        MarkerOptions option = new MarkerOptions()
                .title(locality)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .position(new LatLng(lat,lng))
                .snippet("I searched this location")
                .draggable(true);
        marker = mGoogleMap.addMarker(option);

    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);


    }


    public void cancelRecord()
    {

    cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            place.setText("");
            writeNote.setText("");

            gps = new GPSTracker(TabActivity_3.this);

            // check if GPS enabled
            if(gps.canGetLocation()){

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                // final String originalText = "Your Location is - \n Latitude: "
                // + latitude + "\nLongitude: " + longitude;
                final String o = gps.getCompleteAddressString(latitude,longitude);

                captureLocationText.setText("Capturing current location...");
                captureLocationText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //captureLocationText.setText(originalText);
                        captureLocationText.setText(o);
                    }
                }, 3000);


            }
            else
            {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        }
    });

}


    public void viewAllRecords() {
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<RecordLog> res = d.getAllData2();


                StringBuffer buffer = new StringBuffer();
                for (RecordLog info : res) {
                    buffer.append("ID : " + info.getID() + "\n");
                    buffer.append("Place : " + info.getPlace() + "\n");
                    buffer.append("Address : " + info.getAddress());
                    buffer.append("Note : " + info.getNote() + "\n\n");
                }


                showMessage("Records", buffer.toString());
            }
        });

    }


    public void showMessage(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap= (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);


        }

    }

    


}

