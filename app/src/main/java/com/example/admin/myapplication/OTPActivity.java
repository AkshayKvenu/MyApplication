package com.example.admin.myapplication;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekalips.fancybuttonproj.FancyButton;
import com.example.admin.myapplication.others.Membertrack;
import com.example.admin.myapplication.others.Smslistener;
import com.example.admin.myapplication.others.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tubitv.ui.TubiLoadingView;

import java.text.CollationElementIterator;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    String myPhoneNumber = "+919744960195";
    TubiLoadingView cpb;
    TextView tvProceed;
    LinearLayout linearLayout;
    TextView tvCount;
    ImageView imvTick;
    EditText Phonenumber, otp1, otp2, otp3, otp4, otp5, otp6;
    TextView ResendCode;
    ImageView PhonenumberEdit;
    FancyButton ok;
    private DatabaseReference mDatabase;
    EditText Otp;
    String phonenumber;
    Button btn;
    TextView resend;
    CollationElementIterator mTextField;
    String comingoTP;
    FrameLayout fl;
    CountDownTimer downTimer;
    private FirebaseAuth auth;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
       // phonenumber="9744960195";

       // Bundle ab = getIntent().getExtras();
       // String username = ab.getString("uname");
        //phonenumber = ab.getString("pnumber");
        phonenumber="9947496192";
        cpb = (TubiLoadingView) findViewById(R.id.tubiLoadingView1);
        tvProceed = (TextView) findViewById(R.id.tvProceed);
        linearLayout = (LinearLayout) findViewById(R.id.llProgress);
        tvCount = (TextView) findViewById(R.id.tvCount);
        fl = (FrameLayout) findViewById(R.id.fl);
        imvTick = (ImageView) findViewById(R.id.imageviewTick);
        Phonenumber = (EditText) findViewById(R.id.pnumber);
        ResendCode = (TextView) findViewById(R.id.resend);
        PhonenumberEdit = (ImageView) findViewById(R.id.img);
        otp1 = (EditText) findViewById(R.id.edt1);
        otp2 = (EditText) findViewById(R.id.edt2);
        otp3 = (EditText) findViewById(R.id.edt3);
        otp4 = (EditText) findViewById(R.id.edt4);
        otp5 = (EditText) findViewById(R.id.edt5);
        otp6 = (EditText) findViewById(R.id.edt6);
        otp1.requestFocus();
        Phonenumber.setText(phonenumber);
        membertrackfunction();

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                if (pm.isScreenOn()) {
                    DevicePolicyManager policy = (DevicePolicyManager)
                            getSystemService(Context.DEVICE_POLICY_SERVICE);
                    ComponentName admin = new ComponentName(OTPActivity.this, AdminReceiver.class);

                    if (policy.isAdminActive(admin)) {

                        Log.e("issssss", "permission is granteddddddd");


                        String myOtp=otp1.getText().toString().trim()+otp2.getText().toString().trim()+otp3.getText().toString().trim()+otp4.getText().toString().trim()+otp5.getText().toString().trim()+otp6.getText().toString().trim();
                        Log.e("CHECKINGGGGG",""+myOtp);
                        Log.e("CHECKINGGGGG","comingoTP"+comingoTP);

                        if (comingoTP.equalsIgnoreCase(myOtp)){
                            goAsMember();
                           // membertrackfunction();
                        }
                        else{
                            Toast.makeText(OTPActivity.this, "OTP Mismatch", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent=new Intent(OTPActivity.this,HomeActivity.class);
                        startActivity(intent);

                    }

                    else {
                       // Toast.makeText(this,"must enable device administrator", Toast.LENGTH_LONG).show();
                        ComponentName admin1 = new ComponentName(OTPActivity.this, AdminReceiver.class);
                        Intent intent = new Intent(
                                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                                DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin1);
                        startActivity(intent);
                        Log.e("issssss", "permission is denied");
                    }
                }

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.e("OTPOld", "onVerificationCompleted: credential" + credential);
                Log.e("OTPOld", "onVerificationCompleted: getProvider" + credential.getProvider());
                Log.e("OTPOld", "onVerificationCompleted: getSmsCode" + credential.getSmsCode());

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("OTPOld", "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("OTPOld", "onCodeSent: verificationId" + verificationId);
                Log.d("OTPOld", "onCodeSent: token" + token);
            }
        };


        linearLayout.setVisibility(View.VISIBLE);
        imvTick.setVisibility(View.GONE);
        linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
        Log.d("phonenumberphonenumber", ""+phonenumber);
        reSsendotp(phonenumber);
        downTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCount.setText(millisUntilFinished / 1000 + " seconds remaining...");
            }

            public void onFinish() {
                linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                linearLayout.setVisibility(View.GONE);
                imvTick.setVisibility(View.VISIBLE);
            }

        };
        downTimer.start();


        PhonenumberEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Phonenumber.requestFocus();
            }
        });
        ResendCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reSsendotp(phonenumber);
                downTimer = new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        imvTick.setVisibility(View.GONE);
                        linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                        linearLayout.setVisibility(View.VISIBLE);
                        tvCount.setText(millisUntilFinished / 1000 + " seconds remaining...");
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        // tvProceed.setVisibility(View.GONE);
                        linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                        linearLayout.setVisibility(View.GONE);
                        imvTick.setVisibility(View.VISIBLE);
                    }

                };
                downTimer.start();
            }

        });
        Smslistener BR_smsreceiver = null;
        BR_smsreceiver = new Smslistener();
        BR_smsreceiver.activityhandler(this);
        IntentFilter fltr_smsreceived = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(BR_smsreceiver, fltr_smsreceived);

    }

    public void goAsMember() {

        Users user = new Users(phonenumber);
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/members/");
        mDatabase.setValue(user);



            mDatabase.child(phonenumber).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        Toast.makeText(OTPActivity.this,"You are registered as a member now", Toast.LENGTH_LONG).show();

                        Intent i=new Intent(OTPActivity.this,HomeActivity.class);
                        startActivity(i);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(OTPActivity.this,"An error occured, Try again in goAs ", Toast.LENGTH_LONG).show();
                }
            });


    }

    public void setotp(String kk) {
        downTimer.cancel();
        comingoTP=kk;
        char[] digits1 = kk.toCharArray();
        otp1.setText(String.valueOf(digits1[0]));
        otp2.setText(String.valueOf(digits1[1]));
        otp3.setText(String.valueOf(digits1[2]));
        otp4.setText(String.valueOf(digits1[3]));
        otp5.setText(String.valueOf(digits1[4]));
        otp6.setText(String.valueOf(digits1[5]));
        linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
        linearLayout.setVisibility(View.GONE);
        imvTick.setVisibility(View.VISIBLE);
    }

    public void reSsendotp(String ss) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                ss,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }
    public void membertrackfunction()
    {




        Membertrack membertrack=new Membertrack("false","false","false");
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membertrack/");
        mDatabase.child(phonenumber).setValue(membertrack);
        mDatabase.child(phonenumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Toast.makeText(OTPActivity.this, "values updated", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(OTPActivity.this, "An error occured, Try again", Toast.LENGTH_LONG).show();
            }
        });


    }

}
