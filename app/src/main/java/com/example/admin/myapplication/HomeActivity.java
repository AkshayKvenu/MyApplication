package com.example.admin.myapplication;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.myapplication.others.Addresses;
import com.example.admin.myapplication.others.Membertrack;
import com.example.admin.myapplication.others.Users;
import com.example.admin.myapplication.recycler.Adapter_;
import com.example.admin.myapplication.recycler.Data_Holding_Class;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions  ;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity  implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private ArrayList<Data_Holding_Class> DataHoldingClass_ArrayList = new ArrayList<Data_Holding_Class>();
    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private RecyclerView recyclerViewObj;
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private Adapter_ adapterObj;

    private boolean loading = true,activate=false;

    private RecyclerView.LayoutManager mLayoutManager;
    protected Handler handler;
    Runnable mRunnable;
    int i=0;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    TextView title;
    ImageView imgv;
   public String Loginphnumber="";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refresh token" ,"Refreshed token: " + refreshedToken);
        Bundle ab = getIntent().getExtras();
        //Loginphnumber=ab.getString("pnumber");

            Loginphnumber="9744960195";
     //  membertrackfunction();
      // updateLocation();
        Log.e("phnumber",""+Loginphnumber);

        Log.e("In", "Main_Activity    Oncreate");
        //A progress bar can also be made indeterminate.
        // In indeterminate mode, the progress bar shows a cyclic animation without an indication of progress.
        // This mode is used by applications when the length of the task is unknown.
        // The indeterminate progress bar can be either a spinning wheel or a horizontal bar.
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//  FEATURE_INDETERMINATE_PROGRESS is the Flag for indeterminate progress

        setContentView(R.layout.activity_home);

        Log.e("Hidecheckkk","before hide");
        hideAppIcon();



        /*------ Device Administration Permission Start ----------*/



   /* PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    if (pm.isScreenOn()) {
        DevicePolicyManager policy = (DevicePolicyManager)
                getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(HomeActivity.this, AdminReceiver.class);
        if (policy.isAdminActive(admin)) {
            Log.e("issssss", "permission is granteddddddd");


        } else {
            Toast.makeText(
                    this,
                    "must enable device administrator",
                    Toast.LENGTH_LONG).show();
            ComponentName admin1 = new ComponentName(HomeActivity.this, AdminReceiver.class);
            Intent intent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                    DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin1);
            startActivity(intent);
            Log.e("issssss", "permission is denied");
        }
    }
*/

                /*------ Device Administration Permission End ----------*/



                 /*------ Recycler View ----------*/


            recyclerViewObj = (RecyclerView) findViewById(R.id.recycler_view);
            mLayoutManager = new LinearLayoutManager(this);
            recyclerViewObj.setLayoutManager(mLayoutManager);
            if (checkPlayServices()) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API).build();
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(UPDATE_INTERVAL);
                mLocationRequest.setFastestInterval(FATEST_INTERVAL);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
            }


            Data_Holding_Class DataHoldingClassObj = new Data_Holding_Class();
            for(int h=0;h<4;h++)
            {
                DataHoldingClassObj.setTitle("title");
                DataHoldingClassObj.setThumbnail("thumbnail");
                DataHoldingClass_ArrayList.add(DataHoldingClassObj);
            }
            adapterObj = new Adapter_(HomeActivity.this, DataHoldingClass_ArrayList,Loginphnumber);
            recyclerViewObj.setAdapter(adapterObj);


            recyclerViewObj.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                {
                    if(dy > 0) //check for scroll down
                    {
                        visibleItemCount = mLayoutManager.getChildCount();
                        Log.e("visibleItemCount",String.valueOf(visibleItemCount));
                        totalItemCount = mLayoutManager.getItemCount();
                        // pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                        if (loading)
                        {
                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                            {
                                loading = false;
                                Log.v("...", "Last Item Wow !");
                                //Do pagination.. i.e. fetch new data
                            }
                        }
                    }
                }
            });



                 /*------ Handler ----------*/



            handler = new Handler();
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
            mRunnable.run();




                /*------ Handler End ----------*/








    }
    public void membertrackfunction()
    {



        Membertrack membertrack=new Membertrack("false","false","false");

       /* mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membertrack/");
        mDatabase.child("7777").setValue(membertrack);
        mDatabase.child("7777").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Toast.makeText(HomeActivity.this,"You can be tracked", Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(HomeActivity.this,"An error occured, Try again in track", Toast.LENGTH_LONG).show();
                Log.e("trackerror","cannot track");
            }
        });*/
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membertrack/");
        mDatabase.child(Loginphnumber).setValue(membertrack);
        mDatabase.child(Loginphnumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Toast.makeText(HomeActivity.this, "values updated", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(HomeActivity.this, "An error occured, Try again", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        1000).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("googleApiiiiiiii", "onConnected");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                Location mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    double latitude = mLastLocation.getLatitude();
                    double longitude = mLastLocation.getLongitude();

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(this, Locale.getDefault());

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    Toast.makeText(HomeActivity.this, "address=" + address + "\n" + "city=" + city + "\n" +
                            "state=" + state + "\n" + "country=" + country + "\n" +
                            "postalCode=" + postalCode + "\n" + "knownName=" + knownName + "\n", Toast.LENGTH_SHORT).show();

                    LatLng chembmukku = new LatLng(latitude, longitude);
                    updateLocation(latitude,longitude,address, city, state, country, postalCode, knownName);
                    Log.e("address", "  "+address);
                    Log.e("googleApiiiiiiii", "onConnected");
                } else {
                    Log.e("googleApiiiiiiii", "Couldn't get the location. Make sure location is enabled on the device");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("googleApiiiiiiii", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("googleApiiiiiiii", "onConnectionFailed");
    }

    public void updateLocation(double lat, double longi,String address, String city,
                               String state, String country, String postalCode, String knownName) {
        Log.e("addresssssss", "  "+address);
        Addresses adr = new Addresses( lat,  longi,address, city, state, country, postalCode, knownName);
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membersCurrentLocation/" );
        mDatabase.child(Loginphnumber).setValue(adr);


     /*   Users user = new Users(Loginphnumber);
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/members/");
        mDatabase.child(Loginphnumber).setValue(user);*/



    }

    public void goAsMember() {

        Users user = new Users(Loginphnumber);
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/members/");
        mDatabase.child(Loginphnumber).setValue(user);




        mDatabase.child(Loginphnumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Toast.makeText(HomeActivity.this,"You are registered as a member now", Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(HomeActivity.this,"An error occured, Try again in goAs ", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.example.admin.myapplication.HomeActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        Log.e("Hidecheckkk","hided");


    }
    public void ShowAppIcon()
    {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.example.admin.myapplication.HomeActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Log.e("Hidecheckkk","unhided");
    }


    public void lockScreen() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            DevicePolicyManager policy = (DevicePolicyManager)
                    getSystemService(Context.DEVICE_POLICY_SERVICE);
            try {

               policy.lockNow();
            }
            catch (SecurityException ex) {
                Toast.makeText(
                        this,
                        "must enable device administrator",
                        Toast.LENGTH_LONG).show();
                ComponentName admin = new ComponentName(HomeActivity.this, AdminReceiver.class);
                Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
                startActivity(intent);
            }
        }
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

                            if (child_level_1.getKey().equalsIgnoreCase(Loginphnumber)) {
                                for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                                    if (child_level_2.getKey().equalsIgnoreCase("lock")) {
                                        if (child_level_2.getValue().toString().equalsIgnoreCase("true")) {
                                            lockScreen();
                                        }
                                    }
                                    if (child_level_2.getKey().equalsIgnoreCase("hide")) {
                                        if (child_level_2.getValue().toString().equalsIgnoreCase("true")) {
                                            hideAppIcon();
                                        }
                                        else{
                                            ShowAppIcon();
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
