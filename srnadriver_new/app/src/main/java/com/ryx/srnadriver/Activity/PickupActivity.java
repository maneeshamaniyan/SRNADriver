package com.ryx.srnadriver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ryx.srnadriver.BaseActivity;
import com.ryx.srnadriver.Model.RideResponse;
import com.ryx.srnadriver.Model.SplitFareModel;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Service.MyBackGroundService;
import com.ryx.srnadriver.Service.SendLocationToActivity;
import com.ryx.srnadriver.Util.Constatnts;
import com.ryx.srnadriver.databinding.ActivityMainBinding;
import com.ryx.srnadriver.databinding.ActivityPickupBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickupActivity extends BaseActivity {

    ActivityPickupBinding binding;
    RideResponse.RideInfo user;
    List<SplitFareModel> list = new ArrayList<>();
    GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPickupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initView();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        initEvent();
    }

    private void initView() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra("sendparcle");
        list = (List<SplitFareModel>) intent.getExtras().getSerializable("sendsplit");
        Log.d("ResponseCheck", "" + list.size());
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //buildGoogleApiClient(mMap.);
                    //mMap.setMyLocationEnabled(true);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(PickupActivity.this, location -> {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    buildGoogleApiClient(location.getLatitude(), location.getLongitude());
                                }
                            });

                }
            } else {
                //mMap.getMyLocation().getLatitude();
                //buildGoogleApiClient();
                //mMap.setMyLocationEnabled(true);
            }
        });
    }

    private void initEvent() {
        binding.bottomPickupSheet.tvAddressName.setText(user.getSourceAddress());
        binding.bottomPickupSheet.tvAddressNameDrop.setText(user.getDestinationAddress());
        binding.bottomPickupSheet.estimatedAmount.setText("AED " + user.getEstimatedFare().toString());
        binding.bottomPickupSheet.estimatedTime.setText(user.getEstimatedTime().toString() + " mins");
        binding.bottomPickupSheet.tvDriverName.setText(user.getCustomerName());
        binding.bottomPickupSheet.tvBookingId.setText("Booking ID: " + user.getBookingNo());

        if (user.getIsScheduleRide()) {
            binding.bottomPickupSheet.btnConfirm.setText(getText(R.string.confirm));
            Log.d("statuschange",user.getRequestId().toString());
            binding.bottomPickupSheet.btnConfirm.setOnClickListener(v ->
                    confirmSchedule(PickupActivity.this, user.getRequestId()));

        } else {
            binding.bottomPickupSheet.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            //buildGoogleApiClient(mMap.);
                            //mMap.setMyLocationEnabled(true);
                            fusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(PickupActivity.this, location -> {

                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            buildGoogleApiClient(location.getLatitude(), location.getLongitude());
                                            JSONObject jsonObject = new JSONObject();
                                            try {

                                                jsonObject.put("rideRequestId", user.getRequestId());
                                                jsonObject.put("pickupLatitude", location.getLatitude());
                                                jsonObject.put("pickupLongitude", location.getLongitude());

                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }

                                            JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
                                            Log.d("statuschange", gsonObject.toString());
                                            pickupposition(PickupActivity.this, gsonObject, user, list);
                                        }
                                    });

                        }
                    } else {
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(PickupActivity.this, location -> {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {

                                    }
                                });
                    }
                }
            });
        }

        Glide.with(PickupActivity.this)
                .load(user.getDriverPicture())
                .placeholder(R.drawable.profile_demo)
                .into(binding.bottomPickupSheet.imgDriver);


    }

    protected synchronized void buildGoogleApiClient(double latitude, double locationLatitude) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, locationLatitude))// Sets the center of the map to Geolocation
                .zoom(17)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, locationLatitude))
                // below line is use to add custom marker on our map.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_pin)));
    }


}