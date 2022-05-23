package com.ryx.srnadriver.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ryx.srnadriver.Activity.MainActivity;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Util.Constatnts;
import com.ryx.srnadriver.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    FragmentHomeBinding binding;
    private GoogleMap mMap;
    double lat, lon;
    boolean switchButtonstat;
    private FusedLocationProviderClient fusedLocationClient;
    SharedPreferences prefs;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

                 lat = getArguments().getDouble("latitude",0.0);
            lon = getArguments().getDouble("longitude",0.0);
                //Log.d("FRAGMENTLOOOG",""+userId);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = requireContext().getSharedPreferences(Constatnts.app,MODE_PRIVATE);
        //switchButtonstat = prefs.getBoolean(Constatnts.loggedin,false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //getPermission();
        initView();
        //initEvent();
        //buildGoogleApiClient(lat,lon);
        return binding.getRoot();
    }

    private void initView() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;

            buildGoogleApiClient(lat,lon);
            /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //buildGoogleApiClient(mMap.);
                    //mMap.setMyLocationEnabled(true);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener((Activity) requireContext(), location -> {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    buildGoogleApiClient(location.getLatitude(),location.getLongitude());
                                    location();
                                }
                            });

                }
            }
            else {
                //mMap.getMyLocation().getLatitude();
                //buildGoogleApiClient();
                //mMap.setMyLocationEnabled(true);
            }*/
        });

        //binding.switchRoomAvailability.setChecked(switchButtonstat);
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




    void location()
    {
        Log.d("Location", ""+lon);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Constatnts.KEY_REQUESTING_LOCATION)) {

            switchButtonstat = sharedPreferences.getBoolean(Constatnts.KEY_REQUESTING_LOCATION, false);
        }
    }
}