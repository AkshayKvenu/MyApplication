package com.example.admin.myapplication;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by ADMIN on 07-02-2018.
 */

public interface OnGetDataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
