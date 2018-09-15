package mad.com.inclass13test;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.*;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    ArrayList<LatLng> latLngArrayList;
    private Marker mStart;
    private Marker mEnd;
    Location myLocation;
    boolean flag = true;
    LatLng st, ed;
    Double lat,lng;
    String best;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (flag == true) {
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, locationListener);
                    latLngArrayList.clear();
                    mMap.clear();
                    Toast.makeText(getApplicationContext(), " Tracking has started", Toast.LENGTH_LONG).show();
                    flag = false;
                    myLocation = locationManager.getLastKnownLocation(best);
                    if(myLocation!=null) {
                        lat = myLocation.getLatitude();
                        lng = myLocation.getLongitude();
                        st = new LatLng(lat,lng);
                        latLngArrayList.add(st);
                        mStart = mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(0)).title("Start Location"));
                        mStart.showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(st));
                    }
                }
                else{
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    ed = latLng;
                    //mEnd.setPosition(ed);
/*
                    for(int i=0;i<latLngArrayList.size()-1;i++) {
                        mMap.addPolyline(new PolylineOptions().add(latLngArrayList.get(i)).add(latLngArrayList.get(i+1)).width(5).color(Color.BLUE).geodesic(true));
                    }
*/
                    //mStart = mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(0)).title("Marker Start"));
                    mEnd = mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(latLngArrayList.size()-1)).title("End Location"));
                    mEnd.showInfoWindow();
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for(int i=0;i<latLngArrayList.size()-1;i++) {
                        builder.include(latLngArrayList.get(i));
                    }
                    builder.include(mEnd.getPosition());
                    LatLngBounds bo = builder.build();
                    CameraUpdate cu2 = CameraUpdateFactory.newLatLngBounds(bo,50);
                    mMap.moveCamera(cu2);
                    locationManager.removeUpdates(locationListener);
                    Toast.makeText(getApplicationContext(), " Tracking has stopped", Toast.LENGTH_LONG).show();
                    flag=true;
                }
            }
        });
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        myLocation = locationManager.getLastKnownLocation(best);
        if(myLocation!=null) {
            lat = myLocation.getLatitude();
            lng = myLocation.getLongitude();
            st = new LatLng(lat,lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(st,14.0f));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        latLngArrayList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, // Activity
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }else{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        best = locationManager.getBestProvider(criteria, true);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Enable").setMessage("Do you want to enable GPS ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10.0f));
                    LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
                    latLngArrayList.add(l);
                    mMap.addPolyline(new PolylineOptions().add(latLngArrayList.get(latLngArrayList.size()-1)).add(latLngArrayList.get(latLngArrayList.size()-2)).width(5).color(Color.BLUE).geodesic(true));
                    Log.d("Demo", location.getLatitude() + "," + location.getLongitude());
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
        }
    }

    // Get permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted

                } else {
                    // permission was denied
                }
                return;
            }
        }
    }

}
