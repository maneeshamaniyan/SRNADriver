package com.ryx.srnadriver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Service.MyBackGroundService;
import com.ryx.srnadriver.Service.SendLocationToActivity;
import com.ryx.srnadriver.Util.Constatnts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    Button on, off;
    SwitchCompat switchCompat;

    MyBackGroundService mService = null;
    boolean mBound = false;
    static Boolean isTouched = false;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBackGroundService.LocalBinder binder = (MyBackGroundService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mService = null;
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
        setContentView(R.layout.activity_test);
       getPermission();



    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Constatnts.KEY_REQUESTING_LOCATION)) {

            setButtonState(sharedPreferences.getBoolean(Constatnts.KEY_REQUESTING_LOCATION, false));
        }
    }

    private void setButtonState(boolean aBoolean) {
        if(aBoolean)
        {
            on.setEnabled(false);
            off.setEnabled(true);
        }
        else {
            on.setEnabled(true);
            off.setEnabled(false);
        }
    }

    void getPermission() {
        Dexter.withContext(TestActivity.this)
                .withPermissions(
                        Arrays.asList
                                (Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION)

                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(TestActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        on = (Button) findViewById(R.id.onbtn);
                        off = (Button)  findViewById(R.id.offbtn);
                        switchCompat = findViewById(R.id.switch_room_availability);

                        on.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mService.requestLocationUpdate();
                            }
                        });
                        off.setOnClickListener(v->{
                            mService.removeLocationUpdate();
                        });


                        switchCompat.setOnTouchListener((view, motionEvent) -> {
                            isTouched = true;
                            return false;
                        });

                        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                        {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                            {
                                if (isTouched) {
                                    isTouched = false;
                                    if (isChecked) {
                                        Toast.makeText(TestActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                                        mService.requestLocationUpdate();
                                    }
                                    else {
                                        Toast.makeText(TestActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                                        mService.removeLocationUpdate();
                                    }
                                }
                            }
                        });
                        setButtonState(Constatnts.requestLocationUpdates(TestActivity.this));
                        bindService(new Intent(TestActivity.this,
                                        MyBackGroundService.class),
                                serviceConnection,
                                Context.BIND_AUTO_CREATE);
                    }
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                token.continuePermissionRequest();
            }
        }).check();
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
            Toast.makeText(this, ""+data, Toast.LENGTH_SHORT).show();
        }
    }
}