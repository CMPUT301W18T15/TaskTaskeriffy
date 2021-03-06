package com.example.heesoo.myapplication.task_requester_activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/*
This activity allows the user to assign a location to a task by clicking on a location on the map. If
the task already has a location specified, opening this activity will show a marker at the location
that is associated with this task.
 */

public class TaskMapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng taskLatLng;
    private Task task;
    private String mode;

    private boolean LOCATION_PERMISSION_GRANTED = true;
    private static final int DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("Task");
        mode = intent.getStringExtra("Mode");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();

        if (task.getLatitude().equals(-1.0) && task.getLongitude().equals(-1.0)) {
            getDeviceLocation();
        }
        else {
            goToTaskLocation(task);
        }

        if (mode.equals("AddMarker")) {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    taskLatLng = latLng;
                    mMap.clear();
                    task.setLatitude(taskLatLng.latitude);
                    task.setLongitude(taskLatLng.longitude);
                    mMap.addMarker(new MarkerOptions().position(taskLatLng).title("Task Location"));

                    AlertDialog.Builder popUp = new AlertDialog.Builder(TaskMapActivity.this);
                    popUp.setMessage("Would you like to assign this location to the '" + task.getTaskName() + "' task?");
                    popUp.setCancelable(true);

                    popUp.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    setResult(RESULT_OK);

                                    ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                                    editTask.execute(task);

                                    for (int i = 0; i < MainActivity.user.getRequesterTasks().getSize(); i++) {
                                        Task changedTask = MainActivity.user.getRequesterTasks().getTask(i);

                                        if (changedTask.getId().equals(task.getId())){
                                            changedTask.setLatitude(taskLatLng.latitude);
                                            changedTask.setLongitude(taskLatLng.longitude);
                                        }
                                    }
                                    Intent intent = new Intent(getApplicationContext(), ShowTaskDetailActivity.class);
                                    intent.putExtra("TaskWithLoc", task);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();

                                }
                            });

                    popUp.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mMap.clear();
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = popUp.create();
                    alert11.show();

                }
            });
        }
    }

    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
        }
    }

    private void getDeviceLocation() {
        try {
            if (LOCATION_PERMISSION_GRANTED) {

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    mLastKnownLocation = location;
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(mLastKnownLocation.getLatitude(),
                                                    mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                }
                            }
                        });
            }
        }
        catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void goToTaskLocation(Task t) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(t.getLatitude(), t.getLongitude()), DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().position(new LatLng(t.getLatitude(), t.getLongitude())).title("Task Location"));
    }


}
