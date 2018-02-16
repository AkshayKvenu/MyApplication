package com.example.admin.myapplication;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
String myNo="9446677880";
EditText Loginphnumber;
TextView Createtext;
    SharedPreferences sp;
Button Login;
boolean isvaliduser=false,permission1=false,permission2=false;
ArrayList<String> obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        Bundle ab = getIntent().getExtras();
       /* if (ab.getString("isLoggingOut")!=null){
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            fAuth.signOut();
        }*/
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser()!=null){

            Log.e("userrrrrrr","not logged inn");
        }else{
            Log.e("userrrrrrr","logged out");
        }
        obj=new ArrayList<>();
        Loginphnumber=(EditText)findViewById(R.id.loginnumber);
        Createtext=(TextView)findViewById(R.id.createtxt);
        Login=(Button)findViewById(R.id.loginbtn);
/*

                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                if (pm.isScreenOn()) {
                    DevicePolicyManager policy = (DevicePolicyManager)
                            getSystemService(Context.DEVICE_POLICY_SERVICE);
                    ComponentName admin = new ComponentName(LoginActivity.this, AdminReceiver.class);

                    if (policy.isAdminActive(admin)) {


                        Log.e("issssss", "permission is granteddddddd");
*/
        Log.e("SetPermissionb4", ""+permission2+permission1);
      SetPermission();
        Log.e("SetPermissionftr", ""+permission2+permission1);

        if (permission2) {
            downloadStatus();
            Log.e("SetPermissionin", "" + Loginphnumber);
            Login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Log.e("issssss", "click" + Loginphnumber);


                    if (!Loginphnumber.getText().toString().isEmpty() && Loginphnumber.getText().toString().length() == 10) {
                        Log.e("loginmember", "" + Loginphnumber);
                        for (int i = 0; i < obj.size(); i++) {
                            if (obj.get(i).equalsIgnoreCase(Loginphnumber.getText().toString())) {
                                isvaliduser = true;
                                Log.e("loginmember1", "");
                            }
                        }
                        if (isvaliduser) {

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("pnumber", Loginphnumber.getText().toString());
                            startActivity(intent);
                            Log.e("loginmember2", "");

                        } else {
                            Toast.makeText(LoginActivity.this, "Not a member", Toast.LENGTH_LONG).show();
                        }
                    }
                    sp = getSharedPreferences("Contact_details", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("Phone", Loginphnumber.toString());
                    editor.apply();
                    Log.e("sp", "" + Loginphnumber);

                 /*   else {


                    //    Toast.makeText(this, "must enable device administrator", Toast.LENGTH_LONG).show();
                        ComponentName admin1 = new ComponentName(LoginActivity.this, AdminReceiver.class);
                        Intent intent = new Intent(
                                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                                DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin1);
                        startActivity(intent);
                        Log.e("issssss", "permission is denied");
                    }*/
                }


            });
            Createtext.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }




    }
    public void downloadStatus(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/members");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGG", "root key\t"+root.getKey());
                        Log.e("LOGGGGGG", "root value\t "+root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGG", "child_level_1 key\t"+child_level_1.getKey());
                            Log.e("LOGGGGGG", "child_level_1 value\t "+child_level_1.getValue());
                            obj.add(child_level_1.getKey());

                            if (child_level_1.getKey().equalsIgnoreCase("status")) {
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    Log.e("LOGGGGGG", "child_level_2 key\t "+child_level_2.getKey());
                                    Log.e("LOGGGGGG", "child_level_2 value\t "+child_level_2.getValue());
                                    for (DataSnapshot child_level_3 : child_level_2.getChildren()) {

                                        Log.e("LOGGGGGG", "child_level_3 key\t "+child_level_3.getKey());
                                        Log.e("LOGGGGGG", "child_level_3 value\t "+child_level_3.getValue());
                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }



                });
    }
    public void SetPermission()
    {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            DevicePolicyManager policy = (DevicePolicyManager)
                    getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = new ComponentName(LoginActivity.this, AdminReceiver.class);
            if (policy.isAdminActive(admin)) {
                Log.e("isssssst", "permission is granteddddddd");

                permission2=true;



            } else {
                Toast.makeText(
                        this,
                        "must enable device administrator",
                        Toast.LENGTH_LONG).show();
                ComponentName admin1 = new ComponentName(LoginActivity.this, AdminReceiver.class);
                Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin1);
                startActivity(intent);
                Log.e("isssssst", "permission is denied");
            }


        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // permission1=true;

        }

        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS,Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CHANGE_NETWORK_STATE,Manifest.permission.MODIFY_PHONE_STATE}, 20);

        }
    }

}

