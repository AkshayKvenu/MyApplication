package com.example.admin.myapplication.others;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ADMIN on 24-11-2017.
 */

@IgnoreExtraProperties
public class Group {


    public String Phonenumber1,Phonenumber2,Phonenumber3,Phonenumber4,Phonenumber5;

    // Default constructor required for calls to
    // DataSnapshot.getValue(ProjectEvaluationApp.class)
    public Group() {
    }

    public Group(String phonenumber1,String phonenumber2,String phonenumber3,String phonenumber4,String phonenumber5) {
        this.Phonenumber1 = phonenumber1;
        this.Phonenumber2 = phonenumber2;
        this.Phonenumber3 = phonenumber3;
        this.Phonenumber4 = phonenumber4;
        this.Phonenumber5 = phonenumber5;


    }
}
