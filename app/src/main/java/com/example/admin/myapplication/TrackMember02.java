package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.others.Membertrack;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackMember02 extends AppCompatActivity {
    public String ConnectedMember="",Loginnumber="";
    TextView Txt1,Txt2,Txt3,Txt4;
    private DatabaseReference mDatabase;
    public String hide,lock,data;
    public boolean isvaliduser,hideapp,lockapp,dataapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_member02);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Txt1=(TextView)findViewById(R.id.txt1);
        Txt2=(TextView)findViewById(R.id.txt2);
        Txt3=(TextView)findViewById(R.id.txt3);
        Txt4=(TextView)findViewById(R.id.txt4);
        Bundle ab=getIntent().getExtras();
        Loginnumber=ab.getString("LoginPhonenumber");
        Log.e("LoginPhonenumber",""+Loginnumber);
       ConnectedMember="9744960195";

       hide="false";
       lock="false";
       data="false";
       isvaliduser=false;
       lockapp=false;
       hideapp=false;
       dataapp=false;

        Txt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (Txt1.getText().toString().trim().equalsIgnoreCase("Hide")){
                    hide="true";
                    Txt1.setText("UnHide");
                }else{
                    hide="false";
                    Txt1.setText("Hide");
                }
                loadMemberTrackHide();

            }
        });
        Txt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Txt2.getText().toString().trim().equalsIgnoreCase("Lock")){
                    lock="true";
                    Txt2.setText("UnLock");
                }else{
                    lock="false";
                    Txt2.setText("Lock");
                }

               loadMemberTrackLock();

            }
        });
        Txt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Txt3.getText().toString().trim().equalsIgnoreCase("Enable Data")){
                    data="true";
                    Txt3.setText("Disable Data");
                }else{
                    data="false";
                    Txt3.setText("Enable Data");
                }

               loadMemberTrackData();

            }
        });
        Txt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               Intent intent=new Intent(TrackMember02.this,MapsActivity.class);
               intent.putExtra("ConnectedMember",ConnectedMember);
                intent.putExtra("LoginPhonenumber",Loginnumber);
                startActivity(intent);
            }
        });




    }
    public void loadMemberTrackHide() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/membertrack");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGmmm", "root key\t" + root.getKey());
                        Log.e("LOGGGGGGmmm", "root value\t " + root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGmmm", "child_level_1 key\t" + child_level_1.getKey());
                            Log.e("LOGGGGGGmmm", "child_level_1 value\t " + child_level_1.getValue());
                            if (child_level_1.getKey().equalsIgnoreCase(ConnectedMember)) {
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    Log.e("LOGGGGGGmmm", "child_level_2 key\t " + child_level_2.getKey());
                                    Log.e("LOGGGGGGmmm", "child_level_2 value\t " + child_level_2.getValue());
                                    if (child_level_2.getKey().equalsIgnoreCase("hide")) {
                                        // members.add(child_level_2.getValue().toString());
                                        Log.e("MemberTrackStatus ", "key " + child_level_2.getKey() + " status  " + child_level_2.getValue().toString());
                                        // isvaliduser=true;
                                        //  Flag++;
                                        // Log.e("sizeeeeeeeeee", "load member array list()  " + Flag);


                                        Membertrack membertrack = new Membertrack(hide, lock, data);
                                        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membertrack/");
                                        mDatabase.child(ConnectedMember).setValue(membertrack);
                                        mDatabase.child(ConnectedMember).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                try {
                                                    if (hide == "true") {
                                                        hideapp = true;
                                                    }


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(TrackMember02.this, "An error occured, Try again", Toast.LENGTH_LONG).show();
                                            }
                                        });


                                    }


                                }

/*
                                    if(Flag>=2 && Flag<=5)
                                    {
                                        skip.setBackgroundResource(R.color.blue);

                                    }
*/

                            }

                        }


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }


                });
        if (hideapp) {
            Toast.makeText(TrackMember02.this, "Hiding successfull", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TrackMember02.this, "UnHiding successfull", Toast.LENGTH_LONG).show();
        }
    }


    public void loadMemberTrackLock(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/membertrack");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGmmm", "root key\t"+root.getKey());
                        Log.e("LOGGGGGGmmm", "root value\t "+root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGmmm", "child_level_1 key\t"+child_level_1.getKey());
                            Log.e("LOGGGGGGmmm", "child_level_1 value\t "+child_level_1.getValue());
                            if (child_level_1.getKey().equalsIgnoreCase(ConnectedMember)){
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    Log.e("LOGGGGGGmmm", "child_level_2 key\t "+child_level_2.getKey());
                                    Log.e("LOGGGGGGmmm", "child_level_2 value\t "+child_level_2.getValue());
                                    if ( child_level_2.getKey().equalsIgnoreCase("hide") ) {
                                        // members.add(child_level_2.getValue().toString());
                                        Log.e("MemberTrackStatus ","key "+child_level_2.getKey()+" status  "+child_level_2.getValue().toString());
                                        //  Flag++;
                                        // Log.e("sizeeeeeeeeee", "load member array list()  " + Flag);



                                        Membertrack membertrack=new Membertrack(hide,lock,data);
                                        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membertrack/");
                                        mDatabase.child(ConnectedMember).setValue(membertrack);
                                        mDatabase.child(ConnectedMember).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                try {if(lock=="true") {
                                                    lockapp=true;
                                                 }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(TrackMember02.this, "An error occured, Try again", Toast.LENGTH_LONG).show();
                                            }
                                        });



                                    }

/*
                                    if(Flag>=2 && Flag<=5)
                                    {
                                        skip.setBackgroundResource(R.color.blue);

                                    }
*/

                                }

                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }



                });
        if(lockapp) {
            Toast.makeText(TrackMember02.this, "Locked successfuly", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(TrackMember02.this, "UnLocked successfuly", Toast.LENGTH_LONG).show();

        }
    }
    public void loadMemberTrackData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/membertrack");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGmmm", "root key\t"+root.getKey());
                        Log.e("LOGGGGGGmmm", "root value\t "+root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGmmm", "child_level_1 key\t"+child_level_1.getKey());
                            Log.e("LOGGGGGGmmm", "child_level_1 value\t "+child_level_1.getValue());
                            if (child_level_1.getKey().equalsIgnoreCase(ConnectedMember)){
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    Log.e("LOGGGGGGmmm", "child_level_2 key\t "+child_level_2.getKey());
                                    Log.e("LOGGGGGGmmm", "child_level_2 value\t "+child_level_2.getValue());
                                    if ( child_level_2.getKey().equalsIgnoreCase("hide") ) {
                                        // members.add(child_level_2.getValue().toString());
                                        Log.e("MemberTrackStatus ","key "+child_level_2.getKey()+" status  "+child_level_2.getValue().toString());
                                        //  Flag++;
                                        // Log.e("sizeeeeeeeeee", "load member array list()  " + Flag);




                                        Membertrack membertrack=new Membertrack(hide,lock,data);
                                        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membertrack/");
                                        mDatabase.child(ConnectedMember).setValue(membertrack);
                                        mDatabase.child(ConnectedMember).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                try {
                                                    if(data=="true") {
                                                        dataapp=true;
                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(TrackMember02.this, "An error occured, Try again", Toast.LENGTH_LONG).show();
                                            }
                                        });



                                    }



                                }

                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }



                });

        if(dataapp) {
            Toast.makeText(TrackMember02.this, "Data Enabled", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(TrackMember02.this, "Data Disabled", Toast.LENGTH_LONG).show();

        }

    }



}
