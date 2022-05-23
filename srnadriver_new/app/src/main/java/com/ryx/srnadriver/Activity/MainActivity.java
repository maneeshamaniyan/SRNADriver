package com.ryx.srnadriver.Activity;

import static java.sql.DriverManager.println;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ryx.srnadriver.BaseActivity;
import com.ryx.srnadriver.Fragment.HomeFragment;
import com.ryx.srnadriver.Fragment.TripFragment;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Service.MyBackGroundService;
import com.ryx.srnadriver.Service.SendLocationToActivity;
import com.ryx.srnadriver.Util.Constatnts;
import com.ryx.srnadriver.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    ActivityMainBinding binding;
    Fragment fragment;
    SharedPreferences.Editor preferencesEditor;
    SharedPreferences prefs;
    MyBackGroundService myBackGroundService = null;
    boolean mBound = false;
    FusedLocationProviderClient fusedLocationClient;
    static Boolean isTouched = false;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBackGroundService.LocalBinder binder = (MyBackGroundService.LocalBinder) iBinder;
            myBackGroundService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            myBackGroundService = null;
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(mBound)
        {
            unbindService(serviceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(Constatnts.app, MODE_PRIVATE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getPermission();
        initView();
        binding.appbar.bottomMenu.homeIcon.setSelected(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //buildGoogleApiClient(mMap.);
                //mMap.setMyLocationEnabled(true);
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MainActivity.this, location -> {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Bundle bundle = new Bundle();
                                bundle.putDouble("latitude", location.getLatitude());
                                bundle.putDouble("longitude", location.getLongitude());
                                fragment = new HomeFragment();
                                fragment.setArguments(bundle);
                                loadFragment(fragment);
                            }
                        });

            }
        }
        else {
            //mMap.getMyLocation().getLatitude();
            //buildGoogleApiClient();
            //mMap.setMyLocationEnabled(true);
        }
        fragment = new HomeFragment();
        loadFragment(fragment);




        initEvent();

    }

    private void initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        binding.appbar.bottomMenu.homeIcon.setOnClickListener(
                v -> {
                    binding.appbar.bottomMenu.homeIcon.setSelected(true);
                    binding.appbar.bottomMenu.ridesIcon.setSelected(false);
                    binding.appbar.bottomMenu.moneyIcon.setSelected(false);
                    binding.appbar.bottomMenu.profileIcon.setSelected(false);
                    binding.appbar.tvHeaderTitle.setVisibility(View.VISIBLE);
                    binding.appbar.btnBack.setVisibility(View.VISIBLE);
                    binding.appbar.switchRoomAvailability.setVisibility(View.VISIBLE);
                    loadFragment(fragment);

                }
        );
        binding.appbar.bottomMenu.ridesIcon.setOnClickListener(
                v -> {
                    binding.appbar.bottomMenu.homeIcon.setSelected(false);
                    binding.appbar.bottomMenu.ridesIcon.setSelected(true);
                    binding.appbar.bottomMenu.moneyIcon.setSelected(false);
                    binding.appbar.bottomMenu.profileIcon.setSelected(false);
                    binding.appbar.bottomMenu.profileIcon.setSelected(false);
                    binding.appbar.tvHeaderTitle.setVisibility(View.GONE);
                    binding.appbar.btnBack.setVisibility(View.GONE);
                    binding.appbar.switchRoomAvailability.setVisibility(View.GONE);
                    loadFragment(new TripFragment());
                }
        );
        binding.appbar.bottomMenu.moneyIcon.setOnClickListener(
                v -> {
                    binding.appbar.bottomMenu.homeIcon.setSelected(false);
                    binding.appbar.bottomMenu.ridesIcon.setSelected(false);
                    binding.appbar.bottomMenu.moneyIcon.setSelected(true);
                    binding.appbar.bottomMenu.profileIcon.setSelected(false);
                    binding.appbar.tvHeaderTitle.setVisibility(View.GONE);
                    binding.appbar.btnBack.setVisibility(View.GONE);
                    binding.appbar.switchRoomAvailability.setVisibility(View.GONE);
                }
        );
        binding.appbar.bottomMenu.profileIcon.setOnClickListener(
                v -> {
                    binding.appbar.bottomMenu.homeIcon.setSelected(false);
                    binding.appbar.bottomMenu.ridesIcon.setSelected(false);
                    binding.appbar.bottomMenu.moneyIcon.setSelected(false);
                    binding.appbar.bottomMenu.profileIcon.setSelected(true);
                    binding.appbar.tvHeaderTitle.setVisibility(View.GONE);
                    binding.appbar.btnBack.setVisibility(View.GONE);
                    binding.appbar.switchRoomAvailability.setVisibility(View.GONE);
                }
        );
    }

    private void initEvent() {

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Constatnts.KEY_REQUESTING_LOCATION)) {

            setButtonState(sharedPreferences.getBoolean(Constatnts.KEY_REQUESTING_LOCATION, false));
            checkSwitch(sharedPreferences.getBoolean(Constatnts.KEY_REQUESTING_LOCATION, false));
        }
    }



    void getPermission() {
        Dexter.withContext(MainActivity.this)
                .withPermissions(
                        Arrays.asList
                                (Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION)

                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        binding.appbar.switchRoomAvailability.setOnTouchListener((view, motionEvent) -> {
                            isTouched = true;
                            return false;
                        });

                        binding.appbar.switchRoomAvailability.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isTouched) {
                                isTouched = false;
                                if (isChecked) {
                                    //Toast.makeText(TestActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                                    myBackGroundService.requestLocationUpdate();
                                    binding.appbar.tvOffline.setVisibility(View.GONE);
                                    binding.appbar.tvOnline.setVisibility(View.VISIBLE);

                                }
                                else {

                                    //Toast.makeText(TestActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                                    myBackGroundService.removeLocationUpdate();
                                    binding.appbar.tvOffline.setVisibility(View.VISIBLE);
                                    binding.appbar.tvOnline.setVisibility(View.GONE);
                                }
                            }
                        });

                        setButtonState(Constatnts.requestLocationUpdates(MainActivity.this));
                        bindService(new Intent(MainActivity.this,
                                        MyBackGroundService.class),
                                serviceConnection,
                                Context.BIND_AUTO_CREATE);
                    }
                    else {

                    }
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError dexterError) {

                Log.e("Error",dexterError.toString());
            }
        }).check();

    }

    private void setButtonState(boolean aBoolean) {
        statuschange(MainActivity.this,aBoolean);
        if(aBoolean)
        {
            binding.appbar.switchRoomAvailability.setChecked(true);
            binding.appbar.tvOffline.setVisibility(View.GONE);
            binding.appbar.tvOnline.setVisibility(View.VISIBLE);
        }
        else {
            binding.appbar.switchRoomAvailability.setChecked(false);
            binding.appbar.tvOffline.setVisibility(View.VISIBLE);
            binding.appbar.tvOnline.setVisibility(View.GONE);
        }

    }

    private void checkSwitch(boolean aBoolean) {
        if(aBoolean)
        {
            myBackGroundService.requestLocationUpdate();
            binding.appbar.tvOffline.setVisibility(View.GONE);
            binding.appbar.tvOnline.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToActivity event)
    {
        if(event !=null)
        {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            Log.d("PICHUP",""+data);
            //Log.d("LocationChange:",data);
            //Toast.makeText(this, ""+data, Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("driverId", prefs.getInt(Constatnts.driverId,0));
                jsonObject.put("status",1);
                jsonObject.put("currentLatitude",event.getLocation().getLatitude());
                jsonObject.put("currentLongitude",event.getLocation().getLongitude());

            } catch (JSONException e)  {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
            Log.d("positionchange",gsonObject.toString());
            updatedposition(MainActivity.this,gsonObject);
            rideRequest(MainActivity.this,binding.getRoot());
        }
    }
}

