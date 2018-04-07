package com.example.heesoo.myapplication.task_provider_activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by manuelakm on 2018-03-28.
 */

public class FindNearbyTasksActivity extends AppCompatActivity
        implements OnMapReadyCallback {


    private GoogleMap mMap;
    private Location mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private TaskList taskList;
    private boolean LOCATION_PERMISSION_GRANTED = true;
    private static final int DEFAULT_ZOOM = 15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
         getDeviceLocation();
    }

    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            Log.d("MapError", "Have permission");
        } else {
            // Show rationale and request permission.
        }
    }

    private void getDeviceLocation() {
        try {
            if (LOCATION_PERMISSION_GRANTED) {
                Log.d("MapError", "Permission is granted");

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    Log.d("MapError", "HERE");
                                    Log.d("MapError", location.toString());
                                    mLastKnownLocation = location;
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(mLastKnownLocation.getLatitude(),
                                                    mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                    Circle circle = mMap.addCircle(new CircleOptions()
                                        .center(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                                        .radius(5000)
                                        .strokeColor(Color.BLACK)
                                        .fillColor(Color.WHITE));

                                    ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
                                    getAllTasks.execute("");

                                    try {
                                        taskList = getAllTasks.get();
                                    } catch (Exception e) {}

                                    for(int i = 0; i < taskList.getSize(); i++) {

                                        Task t = taskList.getTask(i);

                                        Location taskLocation = new Location("");
                                        taskLocation.setLongitude(t.getLongitude());
                                        taskLocation.setLatitude(t.getLatitude());

                                        if (!t.getLongitude().equals(-1.0)
                                                && !t.getLatitude().equals(-1.0)
                                                && !t.getTaskRequester().equals(SetPublicCurrentUser.getCurrentUser().getUsername())
                                                && (mLastKnownLocation.distanceTo(taskLocation) <= 5000)) {
                                            mMap.addMarker(new MarkerOptions().position(new LatLng(t.getLatitude(), t.getLongitude())).title(t.getTaskName()));

                                        }
                                    }
                                }
                            }
                        });
            }
        }
        catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
