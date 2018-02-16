package com.example.admin.myapplication;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by ADMIN on 12-02-2018.
 */

public interface MapsDataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
