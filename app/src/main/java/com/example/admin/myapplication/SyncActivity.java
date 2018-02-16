package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.others.AddMember;
import com.example.admin.myapplication.others.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SyncActivity extends AppCompatActivity {
    Button add,skip,remove;
    EditText addphnumber;
    public TextView T1,T2,T3,T4,T5;
    private DatabaseReference mDatabase;
   public ArrayList<String> obj,members,CMobj;
    boolean isvaliduser=false,compare=false,removeph=false;

    String Loginphnumber="";
    int Flag=0,count=0;
    AddMember addmember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle ab = getIntent().getExtras();
       // Loginphnumber=ab.getString("pnumber");
        Loginphnumber="9744960195";

        obj=new ArrayList<>();
        members=new ArrayList<>();
        addphnumber=(EditText)findViewById(R.id.addmember);
        add=(Button)findViewById(R.id.addbtn);
        skip=(Button)findViewById(R.id.skip);
        T1=(TextView)findViewById(R.id.T1);
        T2=(TextView)findViewById(R.id.T2);
        T3=(TextView)findViewById(R.id.T3);
        T4=(TextView)findViewById(R.id.T4);
        T5=(TextView)findViewById(R.id.T5);
        remove=(Button)findViewById(R.id.remove);
        downloadStatus();
        loadNoToMembersArrayList();
       // Removeconnectedmembers();
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Removeconnectedmembers();
                loadNoToMembersArrayList();
            }
            });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Flag>=2 && Flag<=5)
                {

                    goAsMember();
                 /*  Intent intent = new Intent(SyncActivity.this, HomeActivity.class);

                    startActivity(intent);
*/
                }
                for (int i=0;i<members.size();i++)
                {
                    Log.e("members","adedd member"+members.get(i));
                }
            }
        });


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



    public void connectedcountingmember(View v) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/ConnectedMembers/");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGccck", "root key\t" + root.getKey());
                        Log.e("LOGGGGGGccck", "root value\t " + root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGccc", "child_level_1 key\t" + child_level_1.getKey());
                            Log.e("LOGGGGGGccc", "child_level_1 value\t " + child_level_1.getValue());

                            for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                Log.e("LOGGGGGGccc", "child_level_2 key\t " + child_level_2.getKey());
                                Log.e("LOGGGGGGccc", "child_level_2 value\t " + child_level_2.getValue());
                                if (child_level_2.getValue().toString().equalsIgnoreCase(addphnumber.getText().toString()))
                                {
                                    Log.e("LOGGGGGGccct", " " + child_level_2.getValue().toString());
                                    Log.e("LOGGGGGGccct", " " + child_level_1.getKey());
                                    count++;
                                }



                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }


                });
        if(Flag<5) {
            if (!addphnumber.getText().toString().isEmpty() && addphnumber.getText().toString().length() == 10) {
                isvaliduser = false;
                Log.e("sizeeeeeeeeee","testttt flag<5"+obj.size());
                Log.e("sizeeeeeeeeee","addphnumber.getText().toString()"+addphnumber.getText().toString());

                for (int i = 0; i < obj.size(); i++)
                {
                    Log.e("sizeeeeeeeeee","testttt flag<5"+obj.get(i));

                    if (obj.get(i).equalsIgnoreCase(addphnumber.getText().toString()))
                    {
                        for (int j = 0; j < members.size(); j++)
                        {
                            Log.e("comparisonequal","member "+members.get(j));
                            Log.e("comparisonequal","member "+addphnumber.getText().toString());

                            if(members.get(j).equalsIgnoreCase(addphnumber.getText().toString()))
                            {
                                compare=true;

                                break;
                            }
                        }
                        if(!compare) {

                            members.add(addphnumber.getText().toString());
                            Flag++;
                            addphnumber.setText("");
                            isvaliduser = true;
                            Log.e("sizeeeeeeeeee", "matched");
                            break;
                        }

                    }


                }
                if (isvaliduser) {
                    Toast.makeText(SyncActivity.this, "Added phonenumber" + Flag, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SyncActivity.this, "not a member,member add()", Toast.LENGTH_SHORT).show();

                }


            }
        }
        else
        {
            Toast.makeText(SyncActivity.this, "limit exceeded", Toast.LENGTH_SHORT).show();

        }
        if(Flag>=2 && Flag<=5)
        {
            skip.setBackgroundResource(R.color.blue);

        }


    }










    public void goAsMember() {
        if(members.size()==1) {
            addmember = new AddMember(members.get(0), "","","","");

        }


        if(members.size()==2) {
             addmember = new AddMember(members.get(0), members.get(1),"","","");

        }
        if(members.size()==3) {
            addmember = new AddMember(members.get(0), members.get(1),members.get(2),"","");

        }
        if(members.size()==4) {
            addmember = new AddMember(members.get(0), members.get(1),members.get(2),members.get(3),"");

        }
        if(members.size()==5) {
            addmember = new AddMember(members.get(0), members.get(1),members.get(2),members.get(3),members.get(4));

        }
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/ConnectedMembers/");
        mDatabase.child(Loginphnumber).setValue(addmember);
        mDatabase.child(Loginphnumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Toast.makeText(SyncActivity.this, "members added", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(SyncActivity.this, "An error occured, Try again in goAs ", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void loadNoToMembersArrayList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/ConnectedMembers");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGccc", "root key\t"+root.getKey());
                        Log.e("LOGGGGGGccc", "root value\t "+root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGccc", "child_level_1 key\t"+child_level_1.getKey());
                            Log.e("LOGGGGGGccc", "child_level_1 value\t "+child_level_1.getValue());
                            if (child_level_1.getKey().equalsIgnoreCase(Loginphnumber)){
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    Log.e("LOGGGGGGctct", "child_level_2 key\t "+child_level_2.getKey());
                                    Log.e("LOGGGGGGctct", "child_level_2 value\t "+child_level_2.getValue());
                                    if (!child_level_2.getValue().toString().isEmpty() && child_level_2.getValue().toString().length() == 10) {
                                        String ss=child_level_2.getValue().toString();
                                        Log.e("ssssss",""+ss);
                                        members.add(ss);
                                        Log.e("mmembers",""+members.size());
                                        Log.e("comparisonloadtonumber ","member "+child_level_2.getValue().toString());
                                        Flag++;
                                        Log.e("sizeeeeeeeeee", "load member array list()  " + Flag);
                                    }
                                    for (int i=0;i<members.size();i++)
                                    {
                                        Log.e("Memberss",members.get(i));
                                        if(i==0&&members.get(i)!=null)
                                        {
                                            T1.setText(members.get(i));
                                        }
                                        else if(i==1&&members.get(i)!=null)
                                        {
                                            T2.setText(members.get(i));
                                        }
                                        else if(i==2&&members.get(i)!=null)
                                        {
                                            T3.setText(members.get(i));
                                        }
                                        else if(i==3&&members.get(i)!=null)
                                        {
                                            T4.setText(members.get(i));
                                        }
                                        else if(i==4&&members.get(i)!=null)
                                        {
                                            T5.setText(members.get(i));
                                        }


                                    }


                                    if(Flag>=2 && Flag<=5)
                                    {
                                        skip.setBackgroundResource(R.color.blue);

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

    public void Removeconnectedmembers(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/ConnectedMembers");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGccc", "root key\t"+root.getKey());
                        Log.e("LOGGGGGGccc", "root value\t "+root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGccc", "child_level_1 key\t"+child_level_1.getKey());
                            Log.e("LOGGGGGGccc", "child_level_1 value\t "+child_level_1.getValue());
                            if (child_level_1.getKey().equalsIgnoreCase(Loginphnumber)){
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    Log.e("LOGGGGdddd", "child_level_2 key\t "+child_level_2.getKey());
                                    Log.e("LOGGGGdddd", "child_level_2 value\t "+child_level_2.getValue());
                                    if (child_level_2.getValue().toString().equalsIgnoreCase(addphnumber.getText().toString()))
                                    {try
                                    {String key=child_level_2.getKey();
                                        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/ConnectedMembers/");
                                        mDatabase.child(Loginphnumber).child(key).setValue("");
                                        mDatabase.child(Loginphnumber).child(key).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                try {
                                                    Toast.makeText(SyncActivity.this, "Successfully supplied ", Toast.LENGTH_LONG).show();
                                                    addphnumber.setText("");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(SyncActivity.this, "An error occured, Try again", Toast.LENGTH_LONG).show();
                                            }
                                        });


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
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










    /*
    public void memberadd(View v)
    {connectedcountingmember();
        Log.e("sizeeeeeeeeee","member add  "+Flag);
            if(Flag<5) {
                if (!addphnumber.getText().toString().isEmpty() && addphnumber.getText().toString().length() == 10) {
                    isvaliduser = false;
                    Log.e("sizeeeeeeeeee","testttt flag<5"+obj.size());
                    Log.e("sizeeeeeeeeee","addphnumber.getText().toString()"+addphnumber.getText().toString());

                    for (int i = 0; i < obj.size(); i++)
                    {
                        Log.e("sizeeeeeeeeee","testttt flag<5"+obj.get(i));

                        if (obj.get(i).equalsIgnoreCase(addphnumber.getText().toString()))
                        {
                            for (int j = 0; j < members.size(); j++)
                            {
                                Log.e("comparisonequal","member "+members.get(j));
                                Log.e("comparisonequal","member "+addphnumber.getText().toString());

                                if(members.get(j).equalsIgnoreCase(addphnumber.getText().toString()))
                                {
                                    compare=true;

                                    break;
                                }
                            }
                            if(!compare) {

                                members.add(addphnumber.getText().toString());
                                Flag++;
                                addphnumber.setText("");
                                isvaliduser = true;
                                Log.e("sizeeeeeeeeee", "matched");
                                break;
                            }

                        }


                    }
                    if (isvaliduser) {
                        Toast.makeText(SyncActivity.this, "Added phonenumber" + Flag, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SyncActivity.this, "not a member,member add()", Toast.LENGTH_SHORT).show();

                    }


                }
            }
            else
            {
                Toast.makeText(SyncActivity.this, "limit exceeded", Toast.LENGTH_SHORT).show();

            }
            if(Flag>=2 && Flag<=5)
            {
                skip.setBackgroundResource(R.color.blue);

            }

    }
*/



}
