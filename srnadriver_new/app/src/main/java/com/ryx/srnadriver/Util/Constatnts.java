package com.ryx.srnadriver.Util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.ryx.srnadriver.Service.MyBackGroundService;

public class Constatnts {

    public static String KEY_REQUESTING_LOCATION = "LocationUpdateEnable";

    public static String driverId = "driver_id";
    public static String loggedin = "logged_in";
    public static String useremail = "userEmail";
    public static String useraddress = "userAddress";
    public static String userphone = "userphone";
    public static String username = "userName";
    public static String userphoto = "userphoto";
    public static String glatitude = "latitude";
    public static String glongitude = "longitude";
    public final static String app = "com.app.ryx.srnadriver";
    public final static String locationText = "Srna Driver App is Online";
    public final static String locationErrorText = "Srna Driver App Location is Unknown";
    public final static String switchButtonStatus = "switchButtonStatus";

    public final static String stripe_pk = "pk_test_51JAJspDKYj3RlAWxJVylXnbkhPnKaTnbjQT6B47FpvPBsmvybFz4bEPlfxRw8q7dqxAmef7mzEleAsRyhlnCd85G00sLMuAsjY";

    public static void setRequestLocationUpdates(Context context, boolean b) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION,b)
                .apply();
    }

    public static boolean requestLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION,false);
    }
}
