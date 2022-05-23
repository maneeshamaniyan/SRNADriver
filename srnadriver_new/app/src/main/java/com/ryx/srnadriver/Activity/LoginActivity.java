package com.ryx.srnadriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ryx.srnadriver.BaseActivity;
import com.ryx.srnadriver.R;
import com.ryx.srnadriver.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {


    ActivityLoginBinding binding;
    String firebasetoken;
    private final String TAG = "SrnaDriver_" + this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
        initEvent();
    }


    private void initView() {
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

    }


    private void initEvent() {
        binding.btnSubmit.setOnClickListener(v -> {
            String phoneno;

            phoneno = binding.etPhone.getText().toString();



            if(phoneno.isEmpty())
            {
                snackmessage(binding.getRoot(),"Field is empty");
            }


            else {
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("mobile", phoneno);

                    jsonObject.put("firebaseToken", firebasetoken);

                } catch (JSONException e)  {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
                Log.d("LoginUser",gsonObject.toString());
                loginUser(getApplicationContext(),gsonObject);
            }
        });
    }


}