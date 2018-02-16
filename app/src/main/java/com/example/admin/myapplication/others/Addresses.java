package com.example.admin.myapplication.others;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ADMIN on 24-11-2017.
 */

@IgnoreExtraProperties
public class Addresses {


    public String Address,State,city,country,postalcode,knownname;

    public double latitude;
    public double longitude;

    // Default constructor required for calls to
    // DataSnapshot.getValue(ProjectEvaluationApp.class)
    public Addresses() {
    }

    public Addresses(double lat,double longitude,String p1, String p2, String p3, String p4, String p5, String p6) {
        this.latitude = lat;
        this.longitude = longitude;
        this.Address = p1;
        this.city = p2;
        this.State = p3;
        this.country = p4;
        this.postalcode = p5;
        this.knownname=p6;


    }
}
