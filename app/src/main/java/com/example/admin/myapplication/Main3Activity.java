package com.example.admin.myapplication;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class Main3Activity extends AppCompatActivity {

    protected Handler handler;
    Runnable mRunnable;
    int i = 0;
    ArrayList<String> dobj;
    boolean lockIt = true;
    Button btn1, btn2, btn3;
    public String ConnectedMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAppIcon();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockScreen();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewService();
            }
        });
        ConnectedMember="9744960195";

      /*  handler = new Handler();
        mRunnable = new Runnable(){
            @Override
            public void run() {
                i++;
                Log.e("insideHandler","ACTIVITYY-->"+"hello, this is"+i+"th testing");

                downloadmembertrackstatus();


                //  Toast.makeText(MyService.this, "hello, this is"+i+"th testing ",Toast.LENGTH_LONG).show();
                handler.postDelayed(mRunnable, 500);// move this inside the run method

            }
        };
        mRunnable.run();*/
       // downloadStatus();
        //downloadmembertrackstatus();
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }


    public void startNewService() {
        try {
            OnOffMobileData(true);
            Log.e("Main activity",""+getMobileDataState()

            );
            setMobileDataState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 20) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                    } else {


                    }
                }
            }
        }
    }

    public void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.example.admin.myapplication.Main3Activity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }
    public void ShowAppIcon()
    {
        PackageManager p = getPackageManager(); ComponentName componentName = new ComponentName(this, com.example.admin.myapplication.Main3Activity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public void lockScreen() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            DevicePolicyManager policy = (DevicePolicyManager)
                    getSystemService(Context.DEVICE_POLICY_SERVICE);
            try {
                policy.lockNow();
            } catch (SecurityException ex) {
                Toast.makeText(
                        this,
                        "must enable device administrator",
                        Toast.LENGTH_LONG).show();
                ComponentName admin = new ComponentName(Main3Activity.this, AdminReceiver.class);
                Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
                startActivity(intent);
            }
        }
    }


    private void OnOffMobileData(boolean enabled) throws ClassNotFoundException, NoSuchFieldException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try {
            final ConnectivityManager conman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
            connectivityManagerField.setAccessible(true);
            final Object connectivityManager = connectivityManagerField.get(conman);
            final Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

/*
    public void testing(Context context) {
        final Class<?> conmanClass = Class.forName(context.getConnectivityService(context).getClass().getName());
        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(GlobalService.getConnectivityService(context));
        final Class<?> iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        final Method[] methods = iConnectivityManagerClass.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.toGenericString().contains("set")) {
                Log.i("TESTING", "Method: " + method.getName());
            }
        }
    }
*/
public void setMobileDataState(boolean mobileDataEnabled)
{
    try
    {
        TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

        if (null != setMobileDataEnabledMethod)
        {
            setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
        }
    }
    catch (Exception ex)
    {
        Log.e("Main3Activity", "Error setting mobile data state", ex);
    }
}

    public boolean getMobileDataState()
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            Method getMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("getDataEnabled");

            if (null != getMobileDataEnabledMethod)
            {
                boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);

                return mobileDataEnabled;
            }
        }
        catch (Exception ex)
        {
            Log.e("Main3Activity", "Error getting mobile data state", ex);
        }

        return false;
    }




    public void downloadmembertrackstatus(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/membertrack");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        Log.e("LOGGGGGGtr", "root key\t"+root.getKey());
                        Log.e("LOGGGGGGtr", "root value\t "+root.getValue());
                        for (DataSnapshot child_level_1 : root.getChildren()) {
                            Log.e("LOGGGGGGtr", "child_level_1 key\t"+child_level_1.getKey());
                            Log.e("LOGGGGGGtr", "child_level_1 value\t "+child_level_1.getValue());

                            if (child_level_1.getKey().equalsIgnoreCase(ConnectedMember)) {
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    if (child_level_2.getKey().equalsIgnoreCase("lock")) {
                                        if (child_level_2.getValue().toString().equalsIgnoreCase("true")) {
                                            lockScreen();
                                        }
                                    }
                                    if (child_level_2.getKey().equalsIgnoreCase("onoffdata")) {
                                        if (child_level_2.getValue().toString().equalsIgnoreCase("true")) {
                                            startNewService();
                                        }
                                    }

                                  /*  Log.e("LOGGGGGGtr", "child_level_2 key\t "+child_level_2.getKey());
                                    Log.e("LOGGGGGGtr", "child_level_2 value\t "+child_level_2.getValue());
                                    for (DataSnapshot child_level_3 : child_level_2.getChildren()) {

                                        Log.e("LOGGGGGGtr", "child_level_3 key\t "+child_level_3.getKey());
                                        Log.e("LOGGGGGGtr", "child_level_3 value\t "+child_level_3.getValue());
                                    }*/
                                }
                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }



                });
    }


}
