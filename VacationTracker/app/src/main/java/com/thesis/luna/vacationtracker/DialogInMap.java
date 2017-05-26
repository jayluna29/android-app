package com.thesis.luna.vacationtracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DialogInMap extends Activity implements OnMapReadyCallback {
    final Context context = this;

    ImageView i;
    GoogleMap mGoogleMap;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_in_map);

        getDialog();

        if (googleServicesAvailable()) {
            //Toast.makeText(this, "Perfect", Toast.LENGTH_LONG).show();
            //setContentView(R.layout.activity_tab_2);


            initMap();

        } else {
            //nothing
        }

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

        listViewTimeAndDate();

    }




    public void listViewTimeAndDate()
    {

        Intent intentHere = new Intent(DialogInMap.this,TabActivity_1.class);

        startActivity(intentHere);

    }




    public void getDialog()
    {
        i = (ImageView) findViewById(R.id.imageViewThis);

        //insert into database
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //append in a listview with the current time that you created the recor
                          /* Alert Dialog Code Start*/


        //pass the vacation title to the next page
        final Intent intent4 = getIntent();
        Bundle bd = intent4.getExtras();


        if (bd != null) {

            final String place = bd.getString("thistheplaceandnote");
            final String note = bd.getString("thistheplaceandnote2");



            alert.setTitle("I am at " + place); //Set Alert dialog title here
            alert.setMessage("Currently doing " + note); //Message here


            alert.setNegativeButton("Search a location you would like to visit next then click GO on the map! ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // String value = input.getText().toString();
                    // Do something with value!
                    //placeText.setText(Singleton.getInstance().getPlace());
                    //noteText.setText(Singleton.getInstance().getNote());

                    mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            View v = getLayoutInflater().inflate(R.layout.infowindow2, null);


                            View v2 = getLayoutInflater().inflate(R.layout.info_window, null);

                            TextView tvLocality = (TextView) v2.findViewById(R.id.tv_locality);
                            TextView tvLat = (TextView) v2.findViewById(R.id.tv_lat);
                            TextView tvLng = (TextView) v2.findViewById(R.id.tv_lng);
                            TextView tvSnippet = (TextView) v2.findViewById(R.id.tv_snippet);

                            LatLng ll = marker.getPosition();
                            tvLocality.setText(marker.getTitle());
                            tvLat.setText("Latitude: " + ll.latitude);
                            tvLng.setText("Longitude: " + ll.longitude);
                            tvSnippet.setText(marker.getSnippet());


                            i = (ImageView) v.findViewById(R.id.imageViewThis);
                            i.setImageBitmap(SingletonModel.getInstance().getImage());



                            return v;
                        }
                    });


                }
            });


            AlertDialog alertDialog = alert.create();
            alertDialog.show();

        }

    }

    private void initMap()
    {

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(this);


    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            android.app.Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        try {
            dropThisMarkerOnMyCurrentLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);


        if (mGoogleMap != null) {
            mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc = new Geocoder(DialogInMap.this);
                    LatLng ll = marker.getPosition();
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();

                }
            });
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + ll.latitude);
                    tvLng.setText("Longitude: " + ll.longitude);
                    tvSnippet.setText(marker.getSnippet());


                    return v;
                }
            });


        }

        goToLocationZoom(42.624085, -87.822315, 8);


    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);


    }
    public void geoLocate(View view) throws IOException {


        //locateTo();
        //locateFrom();


        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();

        //object that takes any string converts to lat and long
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        //just the locality that you fetched
        String locality = address.getLocality();
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat, lng, 8);

        setMarker(locality, lat, lng);


        System.out.println(address.getLatitude());
        System.out.println(address.getLongitude());







    }

    public void dropThisMarkerOnMyCurrentLocation() throws IOException {

        //-------------

        gps = new GPSTracker(DialogInMap.this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        //gets the item ID and depending which one you choose it will set map type to whichever you choose
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}