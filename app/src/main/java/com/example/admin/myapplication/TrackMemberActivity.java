package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackMemberActivity extends AppCompatActivity {
    String Loginphnumber = "";
    ArrayList<String> obj,members,CMobj;
    ArrayList<String> alNew;
    private DatabaseReference mDatabase;
    TextView T1,T2,T3,T4,T5,T6,T7;
    public OnGetDataListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_member);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Loginphnumber = "9847496192";
        obj=new ArrayList<>();
        alNew=new ArrayList<>();
        T1=(TextView) findViewById(R.id.txt1);
        T2=(TextView) findViewById(R.id.txt2);
        T3=(TextView) findViewById(R.id.txt3);
        T4=(TextView) findViewById(R.id.txt4);
        T5=(TextView) findViewById(R.id.txt5);
        T6=(TextView) findViewById(R.id.txt6);
        T7=(TextView) findViewById(R.id.txt7);
        listener=new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot root) {
                Log.e("LOGGGGGGccck", "root key\t" + root.getKey());
                Log.e("LOGGGGGGccck", "root value\t " + root.getValue());
                for (DataSnapshot child_level_1 : root.getChildren()) {
                    Log.e("LOGGGGGGccc", "child_level_1 key\t" + child_level_1.getKey());
                    Log.e("LOGGGGGGccc", "child_level_1 value\t " + child_level_1.getValue());

                    for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                        Log.e("LOGGGGGGccc", "child_level_2 key\t " + child_level_2.getKey());
                        Log.e("LOGGGGGGccc", "child_level_2 value\t " + child_level_2.getValue());
                        if (child_level_2.getValue().toString().equalsIgnoreCase(Loginphnumber))
                        {
                            Log.e("LOGGGGGGccct", " " + child_level_2.getValue().toString());

                            String ss=child_level_1.getKey();
                            Log.e("LOGGGGGGccct", "ssssssss" + ss);
                            alNew.add(ss);
                            Log.e("LOGGGGGGttttf", "alNewalNew" + alNew.size());
                        }



                    }


                }
                /*----------settext Textview Start----------*/


                for (int i = 0; i < alNew.size(); i++)
                {
                    if (i==0&&alNew.get(i)!=null)
                    {
                        T1.setText(alNew.get(i));
                    }else if (i==1&&alNew.get(i)!=null)
                    {
                        T2.setText(alNew.get(i));
                    }
                    else if (i==2&&alNew.get(i)!=null)
                    {
                        T3.setText(alNew.get(i));
                    }
                    else if (i==3&&alNew.get(i)!=null)
                    {
                        T4.setText(alNew.get(i));
                    }
                    else if (i==4&&alNew.get(i)!=null)
                    {
                        T5.setText(alNew.get(i));
                    }
                    else if (i==5&&alNew.get(i)!=null)
                    {
                        T6.setText(alNew.get(i));
                    }
                    else if (i==6&&alNew.get(i)!=null)
                    {
                        T7.setText(alNew.get(i));
                    }
                }


                /*----------settext Textview End----------*/
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        };

        downloadconnectedStatus();
        Log.e("LOGGGGGGtttt", " alNew.size()" + alNew.size());
        //trackmembers();





        /* ----------------        Textview on click     START      ---------------*/



        T1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T1.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });
        T2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T2.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });
        T3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T3.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });
        T4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T1.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });
        T5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T2.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });
        T6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T3.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });
        T7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackMemberActivity.this,TrackMember02.class);
                intent.putExtra("ConnectedPhoneNumber",T3.getText().toString());
                intent.putExtra("LoginPhonenumber",Loginphnumber);
                startActivity(intent);

            }
        });


      /* ----------------    ----------    Textview on click     END   ------------     ---------------*/

    }

    public void downloadconnectedStatus() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/ConnectedMembers/");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        listener.onSuccess(root);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }


                });
        Log.e("LOGGGGGG  t", " " + alNew.size());
    }
/*
    public void trackmembers() {
        Log.e("LOGGGGGGtttt", " " + alNew.size());
        for (int i = 0; i < alNew.size(); i++)
        {
            if (i==0&&alNew.get(i)!=null)
            {
                T1.setText(alNew.get(i));
            }else if (i==1&&alNew.get(i)!=null)
            {
                T2.setText(alNew.get(i));
            }
            else if (i==2&&alNew.get(i)!=null)
            {
                T3.setText(alNew.get(i));
            }
            else if (i==3&&alNew.get(i)!=null)
            {
                T4.setText(alNew.get(i));
            }
            else if (i==4&&alNew.get(i)!=null)
            {
                T5.setText(alNew.get(i));
            }
            else if (i==5&&alNew.get(i)!=null)
            {
                T2.setText(alNew.get(i));
            }
            else if (i==6&&alNew.get(i)!=null)
            {
                T2.setText(alNew.get(i));
            }
        }
    }
*/
}
