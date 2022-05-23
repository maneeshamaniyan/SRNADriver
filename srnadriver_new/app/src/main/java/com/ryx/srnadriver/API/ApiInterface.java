package com.ryx.srnadriver.API;


import com.google.gson.JsonObject;
import com.ryx.srnadriver.Model.CurrentTripResponse;
import com.ryx.srnadriver.Model.DriverRideResponse;
import com.ryx.srnadriver.Model.LoginResponse;
import com.ryx.srnadriver.Model.PastItemModel;
import com.ryx.srnadriver.Model.PostitionResponse;
import com.ryx.srnadriver.Model.RideResponse;
import com.ryx.srnadriver.Model.StatusResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("driver/phone/register")
    Observable<LoginResponse> loginDetails(@Body JsonObject jsonObject);

    @POST("driver/online-status/change")
    Observable<StatusResponse> driverstatus(@Body JsonObject jsonObject);

    @POST("driver/vehicle/position")
    Observable<PostitionResponse> positionUpdate(@Body JsonObject jsonObject);

    @GET("driver/get-present-ride")
    Observable<RideResponse> riderrequestcheck(@Query("driverId") int driverid);

    @POST("driver/ride/response")
    Observable<DriverRideResponse> driverRideupdate(@Body JsonObject jsonObject);

    @POST("driver/ride/cancel")
    Observable<DriverRideResponse> cancelRideupdate(@Body JsonObject jsonObject);

    @POST("driver/ride/pickup")
    Observable<DriverRideResponse> pickupUpdate(@Body JsonObject jsonObject);

    @POST("driver/ride/complete")
    Observable<DriverRideResponse> completeUpdate(@Body JsonObject jsonObject);

    @GET("driver/get-present-ride")
    Observable<CurrentTripResponse> currentTripRequest(@Query("driverId") int driverid);

    @GET("driver/ride/past-list")
    Observable<PastItemModel> pastTripRequest(@Query("driverId") int driverid);

    @POST("driver/ride/confirm-driver-payment")
    Observable<DriverRideResponse> confirmDriverPayment(@Body JsonObject jsonObject);

    @POST("member/scheduleride/schedule-ride-confirmation")
    Observable<RideResponse> confirmSchedule(@Query("rideRequestId") int rideRequestId);
}
