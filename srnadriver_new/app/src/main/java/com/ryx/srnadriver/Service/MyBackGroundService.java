package com.ryx.srnadriver.Service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;

import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ryx.srnadriver.Activity.MainActivity;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Util.Constatnts;

import org.greenrobot.eventbus.EventBus;

public class MyBackGroundService extends Service {
    private static final String CHANNEL_ID = "my_channel";
    private static final long UPDATE_INTERVAL_IN_MIL = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MIL = UPDATE_INTERVAL_IN_MIL / 2;
    private static final int Notification_ID = 123;
    private boolean changingConfiguration = false;
    private NotificationManager notificationManager;

    public LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationCallback locationCallback;
    private Handler serviceHandler;
    private Location location;


    public MyBackGroundService() {
    }

    @Override
    public void onCreate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };

        createLocationRequest();
        getLastLocation();

        HandlerThread handlerThread = new HandlerThread("Srna");
        handlerThread.start();
        serviceHandler = new Handler(handlerThread.getLooper());
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean startFromNotification = intent.getBooleanExtra(Constatnts.app, false);
        if (startFromNotification) {
            removeLocationUpdates();
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changingConfiguration = true;
    }

    private void removeLocationUpdates() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            Constatnts.setRequestLocationUpdates(this, false);
            stopSelf();
        } catch (SecurityException e) {
            Log.e("Error", e.getMessage());
        }
    }

    private void getLastLocation() {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null)
                                location = task.getResult();
                            else
                                Log.d("Failed", "Failed to get Location");
                        }
                    });
        } catch (SecurityException e) {
            Log.e("Error", e.getMessage());
        }
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(UPDATE_INTERVAL_IN_MIL)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MIL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    private void onNewLocation(Location lastLocation) {
        location = lastLocation;
        EventBus.getDefault().postSticky(new SendLocationToActivity(location));
        if (serviceIsRunningInForeground(this))
            notificationManager.notify(Notification_ID, getNotification());
    }

    private Notification getNotification() {
        Intent intent = new Intent(this, MyBackGroundService.class);
        String text = location == null ? Constatnts.locationErrorText : Constatnts.locationText;
        intent.putExtra(Constatnts.app, true);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.drawable.ic_launch, "Launch Srna", activityPendingIntent)
                .addAction(R.drawable.ic_cancel, "Go Offline", pendingIntent)
                .setContentTitle(Constatnts.locationText)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(0)
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        return builder.build();

    }

    private boolean serviceIsRunningInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE))
            if (getClass().getName().equals(serviceInfo.service.getClassName()))
                if (serviceInfo.foreground)
                    return true;
        return false;
    }

    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        stopForeground(true);
        changingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        stopForeground(true);
        changingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (!changingConfiguration && Constatnts.requestLocationUpdates(this))
            startForeground(Notification_ID, getNotification());
        return true;
    }

    @Override
    public void onDestroy() {
        serviceHandler.removeCallbacks(null);
        super.onDestroy();
    }

    public void requestLocationUpdate() {
        Constatnts.setRequestLocationUpdates(this, true);
        startService(new Intent(getApplicationContext(), MyBackGroundService.class));
        try {


            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

        }
        catch (SecurityException e)
        {
            Log.e("Srna",e.getMessage());
        }

    }

    public void removeLocationUpdate() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            Constatnts.setRequestLocationUpdates(this, false);
            stopSelf();
        }
        catch (SecurityException e)
        {
            Constatnts.setRequestLocationUpdates(this, false);
            Log.e("Srna",e.getMessage());
        }
    }

    public class LocalBinder extends Binder {
        public MyBackGroundService getService(){
            return MyBackGroundService.this;
        }
    }
}
