package com.example.admin.myapplication.others;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ADMIN on 24-11-2017.
 */

@IgnoreExtraProperties
public class Users {


    public String Phonenumber;

    // Default constructor required for calls to
    // DataSnapshot.getValue(ProjectEvaluationApp.class)
    public Users() {
    }

    public Users(String phonenumber) {
        this.Phonenumber = phonenumber;
    }
}
