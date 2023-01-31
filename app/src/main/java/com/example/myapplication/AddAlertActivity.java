package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    int testCount = 0;
    AlertClass alertclass;
    Location currentLocation;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        testCount = 0;
        mDatabase = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Alerts");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = snapshot.getChildrenCount();
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
                //Toast.makeText(parent.getContext(), "Selected: " + selected, Toast.LENGTH_LONG).show();
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
            //isws to baloyme kapoy
            //Metraei poses fwties exoyme synolika
            mDatabase.orderByChild("alertType").equalTo("Fire").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Toast.makeText(AddAlertActivity.this, "Snap Count " + snapshot.getValue(), Toast.LENGTH_SHORT).show();
                    Log.i("Test",snapshot.getValue().toString());
                    //long a = snapshot.h.getChildrenCount();
                    //Log.i("test count",String.valueOf(a));
                    //https://stackoverflow.com/questions/26700924/query-based-on-multiple-where-clauses-in-firebase/26701282#26701282
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            alertclass = new AlertClass(currentUsername,currentFixedCity,currentDatetime,selected,currentAlertDesc);
            //write to db//
            mDatabase.child(String.valueOf(maxid + 1)).setValue(alertclass);
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

}