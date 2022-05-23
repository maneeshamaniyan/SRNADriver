package com.ryx.srnadriver.Service;

import android.location.Location;

public class SendLocationToActivity {
    Location location;

    public SendLocationToActivity(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
