package com.example.admin.myapplication;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dx.dxloadingbutton.lib.LoadingButton;

public class SignUpActivity extends AppCompatActivity {

    LoadingButton lb;
    EditText username, phonenumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.editText);
        phonenumber=(EditText)findViewById(R.id.editText2);
        lb = (LoadingButton)findViewById(R.id.loading_btn);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CHANGE_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.MODIFY_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

        }

        else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET,
                    android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.GET_ACCOUNTS,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.CHANGE_NETWORK_STATE, android.Manifest.permission.MODIFY_PHONE_STATE}, 20);
        }
        lb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lb.startLoading();
                if (!username.getText().toString().isEmpty()&&phonenumber.getText().toString().length()==10&&
                        username.getText().toString()!=null){

                    Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
                    intent.putExtra("uname",username.getText().toString());
                    intent.putExtra("pnumber",phonenumber.getText().toString());
                    startActivity(intent);

                }



            }
        });

    }
 }