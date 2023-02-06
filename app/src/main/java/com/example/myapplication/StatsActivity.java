package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {
    TextView totalFireAlertsTV,totalRainAlertsTV,totalHotAlertsTV,totalAlertsTV,
                totalFireAlertsTodayTV,totalRainAlertsTodayTV,totalHotAlertsTodayTV,totalAlertsTodayTV,
                totalFireAlertsTodayCityTV,totalRainAlertsTodayCityTV,totalHotAlertsTodayCityTV,totalAlertsTodayCityTV;
    int totalFireAlertsCounter, totalRainAlertsCounter, totalHotAlertsCounter,totalAlertsCounter,
            totalFireAlertsTodayCounter,totalRainAlertsTodayCounter,totalHotAlertsTodayCounter,totalAlertsTodayCounter,
            totalFireAlertsTodayCityCounter,totalRainAlertsTodayCityCounter,totalHotAlertsTodayCityCounter,totalAlertsTodayCityCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

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

        //getStats();
    }

    public void getStats(){
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