package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatsActivity extends AppCompatActivity {
    TextView totalFireAlertsTV,totalRainAlertsTV,totalHotAlertsTV,totalAlertsTV,
                totalFireAlertsTodayTV,totalRainAlertsTodayTV,totalHotAlertsTodayTV,totalAlertsTodayTV,
                totalFireAlertsTodayCityTV,totalRainAlertsTodayCityTV,totalHotAlertsTodayCityTV,totalAlertsTodayCityTV;
    public int totalFireAlertsCounter, totalRainAlertsCounter, totalHotAlertsCounter,totalAlertsCounter,
            totalFireAlertsTodayCounter,totalRainAlertsTodayCounter,totalHotAlertsTodayCounter,totalAlertsTodayCounter,
            totalFireAlertsTodayCityCounter,totalRainAlertsTodayCityCounter,totalHotAlertsTodayCityCounter,totalAlertsTodayCityCounter;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
    public String currentDatetime;
    public String currentFixedCity;

    /*
    totalFireAlertsCounter          OK
    totalRainAlertsCounter          OK
    totalHotAlertsCounter           OK
    totalAlertsCounter              OK
    totalFireAlertsTodayCounter     OK
    totalRainAlertsTodayCounter     OK
    totalHotAlertsTodayCounter      OK
    totalAlertsTodayCounter         OK
    totalFireAlertsTodayCityCounter OK
    totalRainAlertsTodayCityCounter OK
    totalHotAlertsTodayCityCounter  OK
    totalAlertsTodayCityCounter     OK
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentFixedCity = extras.getString("currentFixedCity");
        }

        //region LAYOUT ITEMS
        totalFireAlertsTV = findViewById(R.id.totalFireAlerts);
        totalRainAlertsTV = findViewById(R.id.totalRainAlerts);
        totalHotAlertsTV = findViewById(R.id.totalHotAlerts);
        totalAlertsTV = findViewById(R.id.totalAlerts);
        totalFireAlertsTodayTV = findViewById(R.id.totalFireAlertsToday);
        totalRainAlertsTodayTV = findViewById(R.id.totalRainAlertsToday);
        totalHotAlertsTodayTV = findViewById(R.id.totalHotAlertsToday);
        totalAlertsTodayTV = findViewById(R.id.totalAlertsToday);
        totalFireAlertsTodayCityTV = findViewById(R.id.totalFireAlertsTodayCity);
        totalRainAlertsTodayCityTV = findViewById(R.id.totalRainAlertsTodayCity);
        totalHotAlertsTodayCityTV = findViewById(R.id.totalHotAlertsTodayCity);
        totalAlertsTodayCityTV = findViewById(R.id.totalAlertsTodayCity);
        //endregion

        getStats();
    }

    public void getStats(){
        //Set Counters to ZERO
        totalFireAlertsCounter = totalRainAlertsCounter = totalHotAlertsCounter =
            totalAlertsCounter = totalFireAlertsTodayCounter = totalRainAlertsTodayCounter =
            totalHotAlertsTodayCounter = totalAlertsTodayCounter = totalFireAlertsTodayCityCounter =
            totalRainAlertsTodayCityCounter = totalHotAlertsTodayCityCounter = totalAlertsTodayCityCounter = 0;

        currentDatetime = dateformat.format(c.getTime());
        Log.i("Date", currentDatetime);
        Log.i("City", currentFixedCity);

        DatabaseReference db = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Stats");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalAlertsCounter = (int) snapshot.getChildrenCount();
                Log.i("Total Alert Stats" ,String.valueOf(totalAlertsCounter));
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Statistic stat ;
                    stat = postSnapshot.getValue(Statistic.class);
                    Log.i("Date",stat.getAlertDate());
                    Log.i("Type",stat.getAlertType());
                    Log.i("Today",currentDatetime);
                    switch (stat.getAlertType()){
                        case "Fire":
                            //fire counter +1
                            totalFireAlertsCounter +=1;
                            Log.i("Fire Counter","+1");
                            if(stat.getAlertDate().equals(currentDatetime)){
                                //today fire counter +1
                                totalFireAlertsTodayCounter +=1;
                                Log.i("Today Fire Counter","+1");
                                if(stat.getAlertLocation().equals(currentFixedCity)){
                                    //today fire nearby counter +1
                                    totalFireAlertsTodayCityCounter +=1;
                                    Log.i("Today Fire Nearby Counter","+1");
                                }
                            }
                            break;
                        case "Hot":
                            //Hot counter +1
                            totalHotAlertsCounter +=1;
                            Log.i("Hot Counter","+1");
                            if(stat.getAlertDate().equals(currentDatetime)){
                                //today Hot counter +1
                                totalHotAlertsTodayCounter +=1;
                                Log.i("Today Hot Counter","+1");
                                if(stat.getAlertLocation().equals(currentFixedCity)){
                                    //today Hot nearby counter +1
                                    totalHotAlertsTodayCityCounter +=1;
                                    Log.i("Today Hot Nearby Counter","+1");
                                }
                            }
                            break;
                        case "Rain":
                            //Rain counter +1
                            totalRainAlertsCounter +=1;
                            Log.i("Rain Counter","+1");
                            if(stat.getAlertDate().equals(currentDatetime)){
                                //today Rain counter +1
                                totalRainAlertsTodayCounter +=1;
                                Log.i("Today Rain Counter","+1");
                                if(stat.getAlertLocation().equals(currentFixedCity)){
                                    //today Rain nearby counter +1
                                    totalRainAlertsTodayCityCounter +=1;
                                    Log.i("Today Rain Nearby Counter","+1");
                                }
                            }
                            break;
                        default:
                            //do nothing
                    }
                }
                Log.i("Fire Counter",String.valueOf(totalFireAlertsCounter));
                Log.i("Today Fire Counter",String.valueOf(totalFireAlertsTodayCounter));
                Log.i("Today City Fire Counter",String.valueOf(totalFireAlertsTodayCityCounter));
                Log.i("Fire Counter",String.valueOf(totalHotAlertsCounter));
                Log.i("Today Fire Counter",String.valueOf(totalHotAlertsTodayCounter));
                Log.i("Today City Fire Counter",String.valueOf(totalHotAlertsTodayCityCounter));
                Log.i("Fire Counter",String.valueOf(totalRainAlertsCounter));
                Log.i("Today Fire Counter",String.valueOf(totalRainAlertsTodayCounter));
                Log.i("Today City Fire Counter",String.valueOf(totalRainAlertsTodayCityCounter));
                totalAlertsTodayCounter = totalFireAlertsTodayCounter + totalHotAlertsTodayCounter + totalRainAlertsTodayCounter;
                totalAlertsTodayCityCounter = totalFireAlertsTodayCityCounter + totalHotAlertsTodayCityCounter + totalRainAlertsTodayCityCounter;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Set Values to TextViews
        totalFireAlertsTV.setText(String.valueOf(totalFireAlertsCounter));
        totalRainAlertsTV.setText(String.valueOf(totalRainAlertsCounter));
        totalHotAlertsTV.setText(String.valueOf(totalHotAlertsCounter));
        totalAlertsTV.setText(String.valueOf(totalAlertsCounter));
        totalFireAlertsTodayTV.setText(String.valueOf(totalFireAlertsTodayCounter));
        totalRainAlertsTodayTV.setText(String.valueOf(totalRainAlertsTodayCounter));
        totalHotAlertsTodayTV.setText(String.valueOf(totalHotAlertsTodayCounter));
        totalAlertsTodayTV.setText(String.valueOf(totalAlertsTodayCounter));
        totalFireAlertsTodayCityTV.setText(String.valueOf(totalFireAlertsTodayCityCounter));
        totalRainAlertsTodayCityTV.setText(String.valueOf(totalRainAlertsTodayCityCounter));
        totalHotAlertsTodayCityTV.setText(String.valueOf(totalHotAlertsTodayCityCounter));
        totalAlertsTodayCityTV.setText(String.valueOf(totalAlertsTodayCityCounter));
    }

}