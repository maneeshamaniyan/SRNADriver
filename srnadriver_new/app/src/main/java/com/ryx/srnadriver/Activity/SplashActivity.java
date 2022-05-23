package com.ryx.srnadriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.Util.Constatnts;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences prefs;
    boolean loggedin;
    String firebasetoken;
    private final String TAG = "srnadriver_" + this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(Constatnts.app,MODE_PRIVATE);
        loggedin = prefs.getBoolean(Constatnts.loggedin,false);
        setContentView(R.layout.activity_splash);

        initView();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    firebasetoken = task.getResult();
                    Log.d("FirebaseToken",firebasetoken);

                    // Log and toast


                });

        //getQueryMap("https://entegrasyon.paratika.com.tr/paratika/api/v2/success3d/gfdfgfgdfgdfgdf");

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if(loggedin)
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            else {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }
            finish();
        }, 2500);
    }

    private void initView() {
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(findViewById(R.id.img_app_icon));
    }
}