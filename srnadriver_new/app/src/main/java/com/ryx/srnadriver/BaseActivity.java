package com.ryx.srnadriver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ryx.srnadriver.API.ApiClient;
import com.ryx.srnadriver.API.ApiInterface;
import com.ryx.srnadriver.Activity.CompleteActivity;
import com.ryx.srnadriver.Activity.LoginActivity;
import com.ryx.srnadriver.Activity.MainActivity;
import com.ryx.srnadriver.Activity.PaymentActivity;
import com.ryx.srnadriver.Activity.PickupActivity;
import com.ryx.srnadriver.Activity.RegisterActivity;
import com.ryx.srnadriver.AllInterface.interface_splitfare;
import com.ryx.srnadriver.Model.DriverRideResponse;
import com.ryx.srnadriver.Model.LoginResponse;
import com.ryx.srnadriver.Model.PostitionResponse;
import com.ryx.srnadriver.Model.RideResponse;
import com.ryx.srnadriver.Model.SplitFareModel;
import com.ryx.srnadriver.Model.StatusResponse;
import com.ryx.srnadriver.Util.Constatnts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity {
    public ApiInterface api = ApiClient.getApi();
    public CompositeDisposable disposables = new CompositeDisposable();
    SharedPreferences prefs;
    AlertDialog alertDialog;
    SharedPreferences.Editor preferencesEditor;
    //private final String TAG = getString(R.string.app_name) + this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(Constatnts.app, MODE_PRIVATE);
        preferencesEditor = prefs.edit();

    }

    //success
    public void snackmessage(View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }


    //error
    public void error(Context context, String msg) {
        Toast toast = Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT);
        toast.show();

    }


    //login
    public void loginUser(Context context, JsonObject jsonObject) {

        api.loginDetails(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        Log.d("LoginResponse", new Gson().toJson(loginResponse));
                        if (loginResponse.getSuccess() && loginResponse.getData().getDriver().getIsActive() && loginResponse.getData().getDriver().getMobileVerified()) {
                            //Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show();
                            preferencesEditor.putInt(Constatnts.driverId, loginResponse.getData().getDriver().getId());
                            preferencesEditor.putBoolean(Constatnts.loggedin, true);
                            preferencesEditor.putString(Constatnts.useremail, loginResponse.getData().getDriver().getEmail());
                            preferencesEditor.putString(Constatnts.userphone, loginResponse.getData().getDriver().getMobileNumber());
                            preferencesEditor.putString(Constatnts.username, loginResponse.getData().getDriver().getName());
                            preferencesEditor.putString(Constatnts.userphoto, loginResponse.getData().getDriver().getPicture());
                            preferencesEditor.apply();
                            startActivity(new Intent(context, MainActivity.class));
                            finishAffinity();
                        } else if (loginResponse.getSuccess() && !loginResponse.getData().getDriver().getIsActive()) {

                            error(context, loginResponse.getData().getDriver().getInactiveReason());
                            //Toast.makeText(context, ""+loginResponse.getData().getDriver().getInactiveReason(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, RegisterActivity.class));
                            finishAffinity();
                        } else {
                            error(context, loginResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        ;
    }

    //statuschange
    public void statuschange(Context context, boolean status) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("driverId", prefs.getInt(Constatnts.driverId, 0));

            jsonObject.put("driverOnline", status);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
        Log.d("statuschange", gsonObject.toString());

        api.driverstatus(gsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(StatusResponse statusResponse) {
                        Log.d("StatusResponse", new Gson().toJson(statusResponse));
                        if (statusResponse.getSuccess()) {
                            Log.d("Status", "" + statusResponse.getData().getIsOnline());
                        } else {
                            error(context, statusResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        ;
    }

    //update position
    public void updatedposition(Context context, JsonObject jsonObject) {


        api.positionUpdate(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostitionResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(PostitionResponse postitionResponse) {
                        Log.d("PostitionResponse", new Gson().toJson(postitionResponse));
                        if (postitionResponse.getSuccess()) {
                            Log.d("Status", "" + postitionResponse.getData().getCurrentStatus());
                        } else {
                            error(context, postitionResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    //ride request
    public void rideRequest(Context context, View view) {


        api.riderrequestcheck(prefs.getInt(Constatnts.driverId, 0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RideResponse rideResponse) {
                        Log.d("RideResponse", new Gson().toJson(rideResponse));
                        if (rideResponse.getSuccess()) {

                            showriderequestDialog(context, view, rideResponse);
                        } else {
                            //error(context,rideResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void showriderequestDialog(Context context, View view, RideResponse rideResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.ride_request_dialog, viewGroup, false);
        builder.setView(dialogView);

        TextView estimated_fare, estimated_time, tv_pickup, tv_dropoff;
        AppCompatButton acceptbtn, rejectbtn;
        estimated_fare = dialogView.findViewById(R.id.estimated_amount);
        estimated_time = dialogView.findViewById(R.id.estimated_time);
        tv_pickup = dialogView.findViewById(R.id.tv_address_name);
        tv_dropoff = dialogView.findViewById(R.id.tv_address_name_drop);
        acceptbtn = dialogView.findViewById(R.id.btn_confirm);
        rejectbtn = dialogView.findViewById(R.id.btn_cancel_request);

        estimated_fare.setText(rideResponse.getRideInfo().getEstimatedFare().toString());
        estimated_time.setText(rideResponse.getRideInfo().getEstimatedTime().toString() + " mins");
        tv_pickup.setText("" + rideResponse.getRideInfo().getSourceAddress());
        tv_dropoff.setText("" + rideResponse.getRideInfo().getDestinationAddress());

        acceptbtn.setOnClickListener(v -> acceptrideRequest(context, rideResponse, true));
        rejectbtn.setOnClickListener(v -> {
            cancelrideRequest(context, rideResponse);
        });


        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }


    //accept request
    public void acceptrideRequest(Context context, RideResponse rideResponse, boolean type) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("rideRequestId", rideResponse.getRideInfo().getRequestId());
            jsonObject.put("driverId", prefs.getInt(Constatnts.driverId, 0));
            jsonObject.put("responseType", type);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
        Log.d("statuschange", gsonObject.toString());

        api.driverRideupdate(gsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DriverRideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(DriverRideResponse driverRideResponse) {
                        Log.d("RideResponse", new Gson().toJson(driverRideResponse));
                        if (driverRideResponse.getIsSuccess()) {

                            Intent yourIntent = new Intent(context, PickupActivity.class);
                            yourIntent.putExtra("sendparcle", rideResponse.getRideInfo());
                            yourIntent.putExtra("sendsplit", (Serializable) rideResponse.getRideInfo().getSplitFare());

                            //yourIntent.putExtras(b); //pass bundle to your intent
                            startActivity(yourIntent);
                        } else {
                            error(context, driverRideResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    //cancel request
    private void cancelrideRequest(Context context, RideResponse rideResponse) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("rideRequestId", rideResponse.getRideInfo().getRequestId());


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JsonObject gsonObject = JsonParser.parseString(jsonObject.toString()).getAsJsonObject();
        Log.d("statuschange", gsonObject.toString());

        api.cancelRideupdate(gsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DriverRideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(DriverRideResponse driverRideResponse) {
                        Log.d("RideResponse", new Gson().toJson(driverRideResponse));
                        if (driverRideResponse.getIsSuccess()) {

                            alertDialog.cancel();
                        } else {
                            error(context, driverRideResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    //pickup
    public void pickupposition(Context context, JsonObject jsonObject, RideResponse.RideInfo rideInfo, List<SplitFareModel> list) {


        api.pickupUpdate(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DriverRideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(DriverRideResponse driverRideResponse) {
                        Log.d("PickupResponse", new Gson().toJson(driverRideResponse));
                        if (driverRideResponse.getIsSuccess()) {
                            Log.d("Status", "" + driverRideResponse.getMessage());
                            Intent yourIntent = new Intent(context, CompleteActivity.class);
                            yourIntent.putExtra("sendparcle", rideInfo);
                            yourIntent.putExtra("sendsplit", (Serializable) list);
                            //yourIntent.putExtras(b); //pass bundle to your intent
                            startActivity(yourIntent);
                        } else {
                            error(context, driverRideResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //drop
    public void completeposition(Context context, JsonObject jsonObject, RideResponse.RideInfo rideInfo, List<SplitFareModel> list) {


        api.completeUpdate(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DriverRideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(DriverRideResponse driverRideResponse) {
                        Log.d("PickupResponse", new Gson().toJson(driverRideResponse));
                        if (driverRideResponse.getIsSuccess()) {
                            Log.d("Status", "" + driverRideResponse.getMessage());
                            Intent yourIntent = new Intent(context, PaymentActivity.class);
                            yourIntent.putExtra("sendparcle", rideInfo);
                            yourIntent.putExtra("sendsplit", (Serializable) list);
                            startActivity(yourIntent);
                        } else {
                            error(context, driverRideResponse.getMessage());
                            //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //payment
    public void completePayment(Context context, JsonObject jsonObject) {


        api.confirmDriverPayment(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DriverRideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(DriverRideResponse driverRideResponse) {
                        Log.d("PickupResponse", new Gson().toJson(driverRideResponse));
                        try {
                            if (driverRideResponse.getIsSuccess()) {
                                Log.d("Status", "" + driverRideResponse.getMessage());
                                Intent yourIntent = new Intent(context, MainActivity.class);
                                //yourIntent.putExtras(b); //pass bundle to your intent
                                startActivity(yourIntent);
                                finishAffinity();
                            } else {
                                error(context, driverRideResponse.getMessage());
                                //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    //Confirm Schedule
    public void confirmSchedule(Context context, int rideRequestId ) {


        api.confirmSchedule(rideRequestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RideResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RideResponse mRideResponse) {
                        Log.d("PickupResponse", new Gson().toJson(mRideResponse));
                        try {
                            if (mRideResponse.getSuccess()) {
                                Log.d("Status", "" + mRideResponse.getMessage());
                                Intent yourIntent = new Intent(context, PaymentActivity.class);
                                yourIntent.putExtra("sendparcle", mRideResponse.getRideInfo());
                                yourIntent.putExtra("sendsplit", (Serializable) mRideResponse.getRideInfo().getSplitFare());
                                startActivity(yourIntent);
                                finishAffinity();
                            } else {
                                error(context, mRideResponse .getMessage());
                                //Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }


                        //Toast.makeText(getApplicationContext(),""+productDetailsRes.getData().getProduct().getBrand().getName(),Toast.LENGTH_LONG).show();


                    }


                    @Override
                    public void onError(Throwable e) {


                        Log.e("Response", "onNext: " + e.getMessage());
                        error(context, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
