package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddAlertActivity extends AppCompatActivity {
    EditText usernameET, locationET, dateTimeET, descriptionET;
    Spinner alertCategory;
    String currentUsername = null;
    String currentFixedCity = null;
    public String selected;
    public String city;
    public String currentDatetime;
    public String currentAlertDesc;
    private DatabaseReference mDatabase;
    long maxid = 0;
    long adminMaxid =0;
    long statMaxid = 0;
    int testCount = 0;
    AlertClass alertclass;
    Location currentLocation;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        testCount = 0;
        mDatabase = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = snapshot.child("Alerts").getChildrenCount();
                    statMaxid = snapshot.child("Stats").getChildrenCount();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //region identify layout items
        usernameET = findViewById(R.id.usernameLB);
        locationET = findViewById(R.id.locationTB);
        dateTimeET = findViewById(R.id.alertDate);
        descriptionET = findViewById(R.id.alertDescription);
        alertCategory = findViewById(R.id.spinner);
        //endregion

        currentDatetime = dateformat.format(c.getTime());
        dateTimeET.setText(currentDatetime.toString());
        //locationET.setText(getLocation(currentLocation));//δουλευειιιιιιιι<3

        //region alert type categories
        ArrayList<String> catArrayList = new ArrayList<>();
        catArrayList.add(getString(R.string.choose));
        catArrayList.add(getString(R.string.fire));
        catArrayList.add(getString(R.string.hot));
        catArrayList.add(getString(R.string.rain));

        ArrayAdapter<String> catArrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,catArrayList);
        catArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        catArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alertCategory.setAdapter(catArrayAdapter);
        alertCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentUsername = extras.getString("intentUsername");
            currentFixedCity = extras.getString("currentFixedCity");
            usernameET.setText(currentUsername);
            locationET.setText(currentFixedCity);
        }

    }

    public void addAlert(View view) {
        currentAlertDesc = descriptionET.getText().toString();
        if(selected.equals(getString(R.string.choose))){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle(getString(R.string.empty_alertType_title));
            dialog.setMessage(getString(R.string.empty_alertType_mess));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Action for "OK".
                        }
                    });

            final AlertDialog alert = dialog.create();
            alert.show();
            removeExpiredAlerts();
        }else {
            alertclass = new AlertClass(currentUsername,currentFixedCity,currentDatetime,selected,currentAlertDesc);
            Statistic stat = new Statistic(selected,currentFixedCity,currentDatetime);
            //write to db//
            //edw tha prepei na ksanagraftei gia ta statistika
            mDatabase.child("Alerts").child(String.valueOf(maxid + 1)).setValue(alertclass);
            mDatabase.child("Stats").child(String.valueOf(statMaxid + 1)).setValue(stat);
            removeExpiredAlerts();
            addAlertToAdmin(alertclass,3);
        }
    }

    public String getLocation(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        latitude = 37.9838; //αθηνα
        longitude = 23.7275;//αθηνα
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            city = addresses.get(0).getLocality();
        }catch (IOException e){
            e.printStackTrace();
        }
        return city;
    }

    public void removeExpiredAlerts(){
        DatabaseReference expdb = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        String today = dateformat.format(c.getTime());
        AlertClass expAlert = new AlertClass("Exp","Exp","Exp","Exp","Exp");
        ActiveAlert expActiveAlert = new ActiveAlert("Exp","Exp","Exp","Exp");
        Log.i("today",today);
        expdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //edw expire ta alert
                for(int i = 1; i < snapshot.child("Alerts").getChildrenCount() + 1 ; i++){
                    AlertClass testitem = snapshot.child("Alerts").child(String.valueOf(i)).getValue(AlertClass.class);
                    if(testitem.getAlertTime().equals(today)){
                        Log.i("itemFromTodat",testitem.getAlertTime());
                    }else {
                        Log.i("itemFromOtherDay",testitem.getAlertTime());
                        expdb.child("Alerts").child(String.valueOf(i)).setValue(expAlert);
                    }
                }
                //edw expire ta activeAlerts Doyleyei kai ayto
                for(int i = 1; i < snapshot.child("ActiveAlerts").getChildrenCount() + 1 ; i++){
                    ActiveAlert testitem = snapshot.child("ActiveAlerts").child(String.valueOf(i)).getValue(ActiveAlert.class);
                    if(testitem.getAlertDate().equals(today)){
                        Log.i("itemFromTodat",testitem.getAlertDate());
                    }else {
                        Log.i("itemFromOtherDay",testitem.getAlertDate());
                        expdb.child("ActiveAlerts").child(String.valueOf(i)).setValue(expActiveAlert);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addAlertToAdmin(AlertClass alertclass, int threshold ){
        //get alerts db
        DatabaseReference db = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        //Check number of same type alerts in current location
        String city = alertclass.getAlertLocation();
        String type = alertclass.getAlertType();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int count = 0;
                for(int i = 1; i < snapshot.child("Alerts").getChildrenCount() + 1; i++){
                    final String alertType = snapshot.child("Alerts").child(String.valueOf(i)).child("alertType").getValue(String.class);
                    final String alertCity = snapshot.child("Alerts").child(String.valueOf(i)).child("alertLocation").getValue(String.class);
                    Log.i("",alertType);
                    Log.i("",alertCity);
                    Log.i("id",String.valueOf(i));
                    if(alertType.equals(type) && alertCity.equals(city)){
                        count +=1;
                        Log.i("Count",String.valueOf(count));
                    }
                }
                if(count>threshold){
                    //send to adminAlerts
                    Log.i("threshold","threshold reached");
                    String reco = alertclass.getAlertTime() + alertclass.getAlertLocation() + alertclass.getAlertType();
                    AdminAlerts adminAlert = new AdminAlerts(type,city,false,reco);
                    //Log.i("testType",adminAlert.getAlertType());
                    adminMaxid = snapshot.child("AdminAlerts").getChildrenCount();
                    Boolean alreadyExist = false;
                    //twra prepei na ksanabalw to check gia to reco
                    for(int i = 1; i < snapshot.child("AdminAlerts").getChildrenCount() + 1; i++){
                        String childRec = snapshot.child("AdminAlerts").child(String.valueOf(i)).child("reco").getValue(String.class);
                        if(childRec.equals(reco)){
                            //ayto shmainei oti idi yparxei
                            alreadyExist =true;
                            Log.i("ChildRec",childRec);
                            Log.i("reco",reco);
                            Log.i("yparxei hdh","den graftike kati");
                            break;
                        }else{
                            //Log.i("Unique Reco","den yparxei .twra tha graftei");
                            //db.child("AdminAlerts").child(String.valueOf(adminMaxid + 1)).setValue(adminAlert);
                        }
                    }
                    if (alreadyExist){
                        Log.i("yparxei hdh","den graftike kati");
                    }else {
                        Log.i("Unique Reco","den yparxei .twra tha graftei");
                        db.child("AdminAlerts").child(String.valueOf(adminMaxid + 1)).setValue(adminAlert);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}