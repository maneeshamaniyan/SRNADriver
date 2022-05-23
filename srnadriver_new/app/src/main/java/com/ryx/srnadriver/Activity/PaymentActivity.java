package com.ryx.srnadriver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import com.ryx.srnadriver.Adapter.SplitFare;
import com.ryx.srnadriver.BaseActivity;
import com.ryx.srnadriver.Model.RideResponse;
import com.ryx.srnadriver.Model.SplitFareModel;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.databinding.ActivityPaymentBinding;
import com.ryx.srnadriver.databinding.ActivityPickupBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends BaseActivity {

    ActivityPaymentBinding binding;
    RideResponse.RideInfo user;
    List<SplitFareModel> list =  new ArrayList<>();
    GoogleMap mMap;
    SplitFare splitAdapter;
    LinearLayoutManager linearLayoutManager;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initView();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        initEvent();
    }

    private void initView() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra("sendparcle");
        list = (List<SplitFareModel>) intent.getExtras().getSerializable("sendsplit");
        //Log.d("ResponseCheck",user.getCustomerName());
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
                            .addOnSuccessListener(PaymentActivity.this, location -> {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    buildGoogleApiClient(location.getLatitude(),location.getLongitude());
                                }
                            });

                }
            }
            else {
                //mMap.getMyLocation().getLatitude();
                //buildGoogleApiClient();
                //mMap.setMyLocationEnabled(true);
            }
        });


        linearLayoutManager = new LinearLayoutManager(PaymentActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.bottomPaymentSheet.splitFare.setHasFixedSize(true);
        binding.bottomPaymentSheet.splitFare.setLayoutManager(linearLayoutManager);
    }

    private void initEvent() {
        binding.bottomPaymentSheet.tvAddressName.setText(user.getSourceAddress());
        binding.bottomPaymentSheet.tvAddressNameDrop.setText(user.getDestinationAddress());
        binding.bottomPaymentSheet.tvPrice.setText(user.getEstimatedFare().toString()+"\nAED");
        binding.bottomPaymentSheet.tvYourTripPrice.setText(user.getEstimatedFare().toString()+" AED");
        binding.bottomPaymentSheet.tvDriverName.setText(user.getCustomerName());
        binding.bottomPaymentSheet.tvBookingId.setText("Booking ID: "+user.getBookingNo());
        Glide.with(PaymentActivity.this)
                .load(user.getDriverPicture())
                .placeholder(R.drawable.profile_demo)
                .into(binding.bottomPaymentSheet.imgDriver);

        binding.bottomPaymentSheet.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //buildGoogleApiClient(mMap.);
                        //mMap.setMyLocationEnabled(true);
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(PaymentActivity.this, location -> {

                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        buildGoogleApiClient(location.getLatitude(),location.getLongitude());
                                        JSONObject jsonObject = new JSONObject();
                                        try {

                                            jsonObject.put("rideRequestId", user.getRequestId());
                                            jsonObject.put("paymentType", "Cash");


                                        } catch (JSONException e)  {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
                                        Log.d("statuschange",gsonObject.toString());
                                        completePayment(PaymentActivity.this,gsonObject);
                                    }
                                });

                    }
                }
                else {
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(PaymentActivity.this, location -> {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {

                                }
                            });
                }
            }
        });

        splitAdapter = new SplitFare(getApplicationContext(),list);

        binding.bottomPaymentSheet.splitFare.setAdapter(splitAdapter);
    }

    protected synchronized void buildGoogleApiClient(double latitude, double locationLatitude) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude,locationLatitude))// Sets the center of the map to Geolocation
                .zoom(17)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,locationLatitude))
                // below line is use to add custom marker on our map.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_pin)));
    }
}