package com.example.admin.myapplication;

import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.myapplication.others.Addresses;
import com.example.admin.myapplication.others.Membertrack;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap,MemberMap;
    private DatabaseReference mDatabase;
    boolean firstcoming=false;
    public String Address1,State2,City3,Country4,KnownName5,Postalcode6;
    public  String Latitude7,Longitude8;
    public double Lat1,Longi2;
    GoogleApiClient mGoogleApiClient;
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private LocationRequest mLocationRequest;
    String ConnectedMember,LoginNumber;
    public MapsDataListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle ab = getIntent().getExtras();
       // ConnectedMember=ab.getString("ConnectedMember");
       // LoginNumber=ab.getString("LoginPhonenumber");
        LoginNumber="9847496192";
        ConnectedMember="9947496192";
        Log.e("Onsuccessss","before" );
       loadMemberTrack();
        Log.e("Onsuccessss","afterr" );
       listener=new MapsDataListener() {
           @Override
           public void onSuccess(DataSnapshot root) {
               // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
               // ProjectEvaluationApp projectEvaluationApp;
               Log.e("LOGGGGGGmmm", "root key\t"+root.getKey());
               Log.e("LOGGGGGGmmm", "root value\t "+root.getValue());
               for (DataSnapshot child_level_1 : root.getChildren()) {
                   Log.e("LOGGGGGGmmm", "child_level_1 key\t"+child_level_1.getKey());
                   Log.e("LOGGGGGGmmm", "child_level_1 value\t "+child_level_1.getValue());
                   if (child_level_1.getKey().equalsIgnoreCase(ConnectedMember)){
                       for (DataSnapshot child_level_2 : child_level_1.getChildren()) {
                           Log.e("LOGGGGGGttt", "child_level_2 key\t "+child_level_2.getKey());
                           Log.e("LOGGGGGGttt", "child_level_2 value\t "+child_level_2.getValue());


                           Log.e("addresssssss",child_level_2.getKey()+child_level_2.getValue().toString());

                           if(child_level_2.getKey().equalsIgnoreCase("Address"))
                           {
                               Address1=child_level_2.getValue().toString();
                               Log.e("locationnnnnnnnnnn","Address"+Address1);
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("State"))
                           {
                               State2=child_level_2.getValue().toString();
                               Log.e("locationnnnnnnnnnn","State"+State2);
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("city"))
                           {
                               City3=child_level_2.getValue().toString();
                               Log.e("locationnnnnnnnnnn","city"+City3);
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("country"))
                           {
                               Country4=child_level_2.getValue().toString();
                               Log.e("locationnnnnnnnnnn","Country"+Country4);
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("knownname"))
                           {
                               KnownName5=child_level_2.getValue().toString();
                               Log.e("locationnnnnnnnnnn","KnownName"+KnownName5);
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("latitude"))
                           {
                               Latitude7=child_level_2.getValue().toString();
                               Lat1=Double.parseDouble(Latitude7) ;
                               Log.e("locationnnnnnnnnnn","Latitude"+Latitude7);
                               Log.e("locationnnnnnnnnnn","Lat1"+Lat1);
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("longitude"))
                           {
                               Longitude8=child_level_2.getValue().toString();
                               Longi2=Double.parseDouble(Longitude8);
                               Log.e("locationnnnnnnnnnn","Longitude"+Longitude8);
                               Log.e("locationnnnnnnnnnnlat",Latitude7+Longitude8);
                                        LatLng User = new LatLng(Lat1, Longi2);
                               Log.e("locationuser","latitude"+User.latitude);
                               Log.e("locationuser","longitude"+User.longitude);
                               Log.e("locationuser",User.toString());
                                        MemberMap.addMarker(new MarkerOptions().position(User).title(ConnectedMember));
                                        MemberMap.moveCamera(CameraUpdateFactory.newLatLng(User));
                           }
                           else if(child_level_2.getKey().equalsIgnoreCase("postalcode"))
                           {
                               Postalcode6=child_level_2.getValue().toString();
                               Log.e("locationnnnnnnnnnn","Postalcode"+Postalcode6);
                           }





                       }


                   }

               }
           }

           @Override
           public void onStart() {

           }

           @Override
           public void onFailure() {

           }
       };

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MemberMap = googleMap;

       /* Log.e("userrrrr","latitude"+Lat1+"longitude"+Longi2);

        LatLng User = new LatLng(Lat1, Longi2);
        Log.e("userrrrr","latitude"+User.latitude);
        Log.e("userrrrr","longitude"+User.longitude);
        Log.e("userrrrr",User.toString());

        MemberMap.addMarker(new MarkerOptions().position(User).title(ConnectedMember));
        MemberMap.moveCamera(CameraUpdateFactory.newLatLng(User));
        */


/*
        if(!firstcoming) {
            // Add a marker in Sydney and move the camera
            LatLng User = new LatLng(Lat1, Longi2);
            MemberMap.addMarker(new MarkerOptions().position(User).title(ConnectedMember));
            MemberMap.moveCamera(CameraUpdateFactory.newLatLng(User));
            Log.e("googleApiiiiiiii", "onMapReady");
            firstcoming = true;
        }
*/
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
                    Toast.makeText(this, "address=" + address + "\n" + "city=" + city + "\n" +
                            "state=" + state + "\n" + "country=" + country + "\n" +
                            "postalCode=" + postalCode + "\n" + "knownName=" + knownName + "\n", Toast.LENGTH_SHORT).show();

                    LatLng User = new LatLng(latitude, longitude);
                   // LatLng memberT = new LatLng(-34, 151);
/*
                    MemberMap.addMarker(new MarkerOptions().position(memberT).snippet("address=" + address + "\n" + "city=" + city + "\n" +
                            "state=" + state + "\n" + "country=" + country + "\n" +
                            "postalCode=" + postalCode + "\n" + "knownName=" + knownName + "\n").title("address=" + address + "\n" + "city=" + city + "\n" +
                            "state=" + state + "\n" + "country=" + country + "\n" +
                            "postalCode=" + postalCode + "\n" + "knownName=" + knownName + "\n"));
*/

                    mMap.addMarker(new MarkerOptions().position(User).snippet("address=" + address + "\n" + "city=" + city + "\n" +
                            "state=" + state + "\n" + "country=" + country + "\n" +
                            "postalCode=" + postalCode + "\n" + "knownName=" + knownName + "\n").title("address=" + address + "\n" + "city=" + city + "\n" +
                            "state=" + state + "\n" + "country=" + country + "\n" +
                            "postalCode=" + postalCode + "\n" + "knownName=" + knownName + "\n"));
                    updateLocation(latitude,longitude,address, city, state, country, postalCode, knownName);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(User));
                    //MemberMap.moveCamera(CameraUpdateFactory.newLatLng(memberT));
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

    public void updateLocation(double lat, double longi,String address, String city, String state, String country, String postalCode, String knownName) {

        Addresses adr = new Addresses( lat,  longi,address, city, state, country, postalCode, knownName);
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/membersCurrentLocation/" );
        mDatabase.child(LoginNumber).setValue(adr);


     /*   Users user = new Users(ConnectedMember);
        mDatabase = FirebaseDatabase.getInstance().getReference("LocateApp/members/");
        mDatabase.child(ConnectedMember).setValue(user);*/



    }
    public void loadMemberTrack() {
        Log.e("Onsuccessss","loadMemberTrack" );
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LocateApp/membersCurrentLocation/");
        Log.e("Onsuccessss","DatabaseReference" );
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot root) {
                        Log.e("Onsuccessss","onDataChange" );
                        listener.onSuccess(root);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                }

        );
    }
}
