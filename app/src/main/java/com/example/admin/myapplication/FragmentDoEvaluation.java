/*
package com.example.admin.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmResults;
import test.com.projectevaluation.Candidate;
import test.com.projectevaluation.CustomProgressDialog;
import test.com.projectevaluation.R;
import test.com.projectevaluation.Users;
import test.com.projectevaluation.realm.MembersTable;

public class FragmentDoEvaluation extends Fragment {

    CustomProgressDialog progressDialog;
    Candidate interfaceCandidate;
    Bundle args;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    Realm realm;
    antistatic.spinnerwheelpresentation.AbstractWheel wheelPresentation;
    antistatic.spinnerwheelreport.AbstractWheel wheelPresentationReport;
    antistatic.spinnerwheeloverall.AbstractWheel wheelPresentationoverall;
    TextView textview_total_score_is;
    TextView textviewProjectName;
    TextView textviewCandidName;
    int totalScore = 0;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    String grpMembers;
    String projectName;
    Button btnSubmit;
    String comingName;
    String projectname = null;
    String IMEI = null;
    String IMEIIs = null;
    String setProjectName = null;
    String setCandidName = null;
    String setMarkPresentation = "5";
    String setMarkReporting = "5";
    String setMarkOverallProject = "5";
    String setMarkTotal = "0";
    boolean isIMEISame = false;
    boolean isCandidNameSame = false;
    boolean isProjectNameSame = false;
    String userIdIndex = null;
    String nameIndex = null;

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof Candidate) {
            interfaceCandidate = (Candidate) activity;
        } else
            throw new ClassCastException();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof Candidate) {
                interfaceCandidate = (Candidate) activity;
            } else
                throw new ClassCastException();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_mark, container, false);
        args = getArguments();
        comingName = args.getString("candidId");
        IMEIIs = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("IMEIIs", "" + IMEIIs);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnSubmit = (Button) view.findViewById(R.id.button_submit);
        textviewProjectName = (TextView) view.findViewById(R.id.proNamee);
        textviewCandidName = (TextView) view.findViewById(R.id.Candidnamee);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("comingName", "" + comingName);
                    String userId = mDatabase.push().getKey();
                    IMEI = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (IMEI != null) {
                        Users user = new Users(comingName, projectname, String.valueOf(score1), String.valueOf(score2), String.valueOf(score3), textview_total_score_is.getText().toString(), IMEI);
                        mDatabase = FirebaseDatabase.getInstance().getReference("ProjectEvaluationApp/evaluatedList/");
                        mDatabase.child(userId).setValue(user);
                        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Toast.makeText(getActivity(), "Marks updated", Toast.LENGTH_LONG).show();
                                    btnSubmit.setText("Already Scored");
                                    btnSubmit.setOnClickListener(null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(getActivity(), "An error occured, Try again", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Unable to read device IMEI", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        textview_total_score_is = (TextView) view.findViewById(R.id.textview_total_score_is);
        wheelPresentation = (antistatic.spinnerwheelpresentation.AbstractWheel) view.findViewById(R.id.wheel_presentation);
        wheelPresentationReport = (antistatic.spinnerwheelreport.AbstractWheel) view.findViewById(R.id.wheel_presentation2);
        wheelPresentationoverall = (antistatic.spinnerwheeloverall.AbstractWheel) view.findViewById(R.id.wheel_presentation3);
        antistatic.spinnerwheelpresentation.adapters.NumericWheelAdapter minAdapter = new antistatic.spinnerwheelpresentation.adapters.NumericWheelAdapter(getActivity(), 1, 10, "%02d");
        minAdapter.setItemResource(R.layout.wheel_text_centered_dark_back);
        minAdapter.setItemTextResource(R.id.text);
        antistatic.spinnerwheelreport.adapters.NumericWheelAdapter minAdapterReport = new antistatic.spinnerwheelreport.adapters.NumericWheelAdapter(getActivity(), 1, 10, "%02d");
        minAdapterReport.setItemResource(R.layout.wheel_text_centered_dark_back);
        minAdapterReport.setItemTextResource(R.id.text);
        antistatic.spinnerwheeloverall.adapters.NumericWheelAdapter minAdapterOverall = new antistatic.spinnerwheeloverall.adapters.NumericWheelAdapter(getActivity(), 1, 10, "%02d");
        minAdapterOverall.setItemResource(R.layout.wheel_text_centered_dark_back);
        minAdapterOverall.setItemTextResource(R.id.text);
        wheelPresentation.setViewAdapter(minAdapter);
        wheelPresentation.setCurrentItem(4);
        score1 = 5;
        wheelPresentationoverall.setViewAdapter(minAdapterOverall);
        wheelPresentationoverall.setCurrentItem(4);
        score3 = 5;
        wheelPresentationReport.setViewAdapter(minAdapterReport);
        wheelPresentationReport.setCurrentItem(4);
        score2 = 5;
        int tot = score1 + score2 + score3;
        textview_total_score_is.setText(String.valueOf(tot));
        wheelPresentation.addChangingListener(new antistatic.spinnerwheelpresentation.OnWheelChangedListener() {
            @Override
            public void onChanged(antistatic.spinnerwheelpresentation.AbstractWheel wheel, int oldValue, int newValue) {
                Log.e("wheel.getCurrentItem()", "" + wheel.getCurrentItem());
                score1 = wheel.getCurrentItem() + 1;
                totalScore = 0;
                totalScore = score1 + score2 + score3;
                textview_total_score_is.setText("" + totalScore);
            }
        });
        wheelPresentationReport.addChangingListener(new antistatic.spinnerwheelreport.OnWheelChangedListener() {
            @Override
            public void onChanged(antistatic.spinnerwheelreport.AbstractWheel wheel, int oldValue, int newValue) {
                Log.e("wheel.getCurrentItem()", "" + wheel.getCurrentItem());
                score2 = wheel.getCurrentItem() + 1;
                totalScore = 0;
                totalScore = score1 + score2 + score3;
                textview_total_score_is.setText("" + totalScore);
            }
        });
        wheelPresentationoverall.addChangingListener(new antistatic.spinnerwheeloverall.OnWheelChangedListener() {
            @Override
            public void onChanged(antistatic.spinnerwheeloverall.AbstractWheel wheel, int oldValue, int newValue) {
                Log.e("wheel.getCurrentItem()", "" + wheel.getCurrentItem());
                score3 = wheel.getCurrentItem() + 1;
                totalScore = 0;
                totalScore = score1 + score2 + score3;
                textview_total_score_is.setText("" + totalScore);
            }
        });

*//*
*/
/*

*//*

*/
/*
        mins.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(AbstractWheel wheel) {
                Log.e("wheel.getCurrentItem()",""+wheel.getCurrentItem());
                Log.e("wheel.getCurrentItem()",""+wheel.getViewAdapter().);
            }

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {
            }
        });
*//*
*/
/*
*//*

*/
/*

        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

      *//*
*/
/*

*//*

*/
/* final  RealmResults<MembersTable> r1 = realm.where(MembersTable.class)
                .findAll();*//*
*/
/*
*//*

*/
/*



      *//*
*/
/*

*//*

*/
/*  for (int i=0;i<r1.size();i++){
            realm.beginTransaction();
            r1.deleteFromRealm(0);
            realm.commitTransaction();

        }*//*
*/
/*
*//*

*/
/*


     *//*
*/
/*

*//*

*/
/*   realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i=0;i<r1.size();i++){
                    //realm.beginTransaction();
                    r1.deleteFromRealm(0);
                    //realm.commitTransaction();

                }
            }
        });*//*
*/
/*
*//*


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<MembersTable> r1 = realm.where(MembersTable.class)
                        .findAll();
                r1.deleteAllFromRealm();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    } // end of onActivityCreated

    @Override
    public void onResume() {
        super.onResume();
        showProgress("Getting Projects", true);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ProjectEvaluationApp");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Toast.makeText(getActivity(), "DB reading success", Toast.LENGTH_LONG).show();
                        // ProjectEvaluationApp projectEvaluationApp;
                        showProgress("", false);
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            // Log.e("KEYYYYYYYYY", ""+postSnapshot.getKey());

                            if (postSnapshot.getKey().equalsIgnoreCase("evaluatedList")) {
                                for (DataSnapshot evaluatedListChild : postSnapshot.getChildren()) {
                                    Log.e("GGGGGGGG", "KEY " + evaluatedListChild.getKey());
                                    EvaluatedTable evaluatedTable = new EvaluatedTable();
                                    realm.beginTransaction();
                                    //Log.e("name---projects--->", " VALUE" + projects.getValue());
                                    for (DataSnapshot evaluatedListInnerChild : evaluatedListChild.getChildren()) {
                                        // Log.e("name---groupMembers--->", "KEY " + groupMembers.getKey());
                                        // Log.e("name---groupMembers--->", " VALUE" + groupMembers.getValue());
                                        Log.e("GGGGGGGG  FFFF", "KEY " + evaluatedListInnerChild.getKey());
                                        Log.e("GGGGGGGG  FFFF", "VALUE " + evaluatedListInnerChild.getValue());
                                        Log.e("=========", "======================== " + evaluatedListInnerChild.getValue());
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("IMEI")) {
                                            evaluatedTable.setImeiNumber(evaluatedListInnerChild.getValue().toString());
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("name")) {
                                            evaluatedTable.setCandidName(evaluatedListInnerChild.getValue().toString());
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("projectname")) {
                                            evaluatedTable.setProjectName(evaluatedListInnerChild.getValue().toString());
                                        }
                                        realm.copyToRealm(evaluatedTable);
                                        realm.commitTransaction();
                                    }
                                }
                            }



                            if (postSnapshot.getKey().equalsIgnoreCase("projects")) {
                                for (DataSnapshot projects : postSnapshot.getChildren()) {
                                    // Log.e("name---projects--->", "KEY " + projects.getKey());
                                    //Log.e("name---projects--->", " VALUE" + projects.getValue());
                                    for (DataSnapshot groupMembers : projects.getChildren()) {
                                        // Log.e("name---groupMembers--->", "KEY " + groupMembers.getKey());
                                        // Log.e("name---groupMembers--->", " VALUE" + groupMembers.getValue());
                                        if (groupMembers.getKey().equalsIgnoreCase("groupmembers")) {
                                            MembersTable membersTable = new MembersTable();
                                            projectName = projects.getKey();
                                            for (DataSnapshot members : groupMembers.getChildren()) {
                                                grpMembers = members.getKey();
                                                Log.e("TEST_ABC", "projectName " + projectName);
                                                Log.e("TEST_ABC", "grpMembers " + grpMembers);
                                                realm.beginTransaction();
                                                membersTable.setProjectName(projectName);
                                                membersTable.setGroupMembersName(grpMembers);
                                                realm.copyToRealm(membersTable);
                                                realm.commitTransaction();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        realm.beginTransaction();
                        RealmResults<MembersTable> r1 = realm.where(MembersTable.class)
                                .equalTo("groupMembersName", comingName)
                                .findAll();
                        if (r1.size() > 0) {
                            projectname = r1.get(0).getProjectName();
                        }
                        textviewProjectName.setText(projectname);
                        textviewCandidName.setText(comingName);
                        realm.commitTransaction();
                        if (projectname == null) {
                            wheelPresentation.setCurrentItem(4);
                            wheelPresentationReport.setCurrentItem(4);
                            wheelPresentationoverall.setCurrentItem(4);
                            // textview_total_score_is.setText("There is no project to evaluate");
                            btnSubmit.setText("There is no project to evaluate");
                            btnSubmit.setOnClickListener(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        showProgress("", false);
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.e("------->", "Top Key" + postSnapshot.getKey());
                            if (postSnapshot.getKey().equalsIgnoreCase("evaluatedList")) {
                                for (DataSnapshot evaluatedListChild : postSnapshot.getChildren()) {
                                    Log.e("------->", "evaluatedListChild.getKey()" + evaluatedListChild.getKey());
                                    for (DataSnapshot evaluatedListInnerChild : evaluatedListChild.getChildren()) {
                                        Log.e("------->", "evaluatedListInnerChild.getKey() " + evaluatedListInnerChild.getKey());
                                        Log.e("------->", "evaluatedListInnerChild.getValue() " + evaluatedListInnerChild.getValue());
                                        Log.e("=========", "======================== " + evaluatedListInnerChild.getValue());
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("IMEI")) {
                                            if (evaluatedListInnerChild.getValue().toString().equalsIgnoreCase(IMEIIs)) {
                                                isIMEISame = true;
                                                // userIdIndex=evaluatedListChild.getKey();
                                            } else {
                                                isIMEISame = false;
                                            }
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("name")) {
                                            if (evaluatedListInnerChild.getValue().toString().equalsIgnoreCase(comingName)) {
                                                if (isIMEISame) {
                                                    isCandidNameSame = true;
                                                    userIdIndex = evaluatedListChild.getKey();
                                                } else {
                                                    isCandidNameSame = false;
                                                    userIdIndex = null;
                                                }
                                            }
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("projectname")) {
                                            if (evaluatedListInnerChild.getValue().toString().equalsIgnoreCase(projectname)) {
                                                if (userIdIndex != null) {
                                                    if (userIdIndex.equalsIgnoreCase(evaluatedListChild.getKey())
                                                            ) {
                                                        isProjectNameSame = true;
                                                        setProjectName = evaluatedListInnerChild.getValue().toString();
                                                        Log.e("------->", "userindex " + userIdIndex);
                                                        Log.e("------->", "setProjectName " + setProjectName);
                                                    }
                                                }
                                            }
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("presentationmark")) {
                                            if (userIdIndex != null) {
                                                if (userIdIndex.equalsIgnoreCase(evaluatedListChild.getKey())
                                                        ) {
                                                    if (isCandidNameSame) {
                                                        setMarkPresentation = evaluatedListInnerChild.getValue().toString();
                                                        Log.e("------->", "setMarkPresentation " + setMarkPresentation);
                                                    }
                                                }
                                            }
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("reportingmark")) {
                                            if (userIdIndex != null) {
                                                if (userIdIndex.equalsIgnoreCase(evaluatedListChild.getKey())
                                                        ) {
                                                    if (isCandidNameSame) {
                                                        setMarkReporting = evaluatedListInnerChild.getValue().toString();
                                                        Log.e("------->", "setMarkReporting " + setMarkReporting);
                                                    }
                                                }
                                            }
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("overallprojectmark")) {
                                            if (userIdIndex != null) {
                                                if (userIdIndex.equalsIgnoreCase(evaluatedListChild.getKey())) {
                                                    if (isCandidNameSame) {
                                                        setMarkOverallProject = evaluatedListInnerChild.getValue().toString();
                                                        Log.e("------->", "setMarkOverallProject " + setMarkOverallProject);
                                                    }
                                                }
                                            }
                                        }
                                        if (evaluatedListInnerChild.getKey().equalsIgnoreCase("totalmark")) {
                                            if (userIdIndex != null) {
                                                if (userIdIndex.equalsIgnoreCase(evaluatedListChild.getKey())
                                                        ) {
                                                    if (isCandidNameSame) {
                                                        setMarkTotal = evaluatedListInnerChild.getValue().toString();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (isIMEISame && isCandidNameSame && isProjectNameSame) {
                            btnSubmit.setText("Already Scored");
                            btnSubmit.setOnClickListener(null);
                            Log.e("-------> booleannn", "\tisIMEISame \t" + isIMEISame);
                            Log.e("-------> booleannn", "\tisCandidNameSame \t" + isCandidNameSame);
                            Log.e("-------> booleannn", "\tisProjectNameSame \t" + isProjectNameSame);
                            Log.e("------->", "inner setMarkPresentation " + setMarkPresentation);
                            Log.e("------->", "inner setMarkReporting " + setMarkReporting);
                            Log.e("------->", "inner setMarkOverallProject " + setMarkOverallProject);
                            isIMEISame = false;
                            isCandidNameSame = false;
                            isProjectNameSame = false;
                            wheelPresentation.setCurrentItem(Integer.parseInt(setMarkPresentation) - 1);
                            wheelPresentationReport.setCurrentItem(Integer.parseInt(setMarkReporting) - 1);
                            wheelPresentationoverall.setCurrentItem(Integer.parseInt(setMarkOverallProject) - 1);
                            textview_total_score_is.setText("" + setMarkTotal);


  String setProjectName= null;
                            String setCandidName = null;
                            String setMarkPresentation = null;
                            String setMarkReporting = null;
                            String setMarkOverallProject = null;
                            String setMarkTotal= null;



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    } // end of onResume

    private void showProgress(String msg, boolean status) {
        if (!status) {
            progressDialog.cancel();
            return;
        }
        if (progressDialog != null) {
            progressDialog = null;
        }
        progressDialog = new CustomProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }
}


*/
