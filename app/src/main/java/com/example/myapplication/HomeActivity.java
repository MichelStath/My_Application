package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTV;
    FloatingActionButton addAlertBTN;
    public String currentUsername = null;
    public String currentFixedCity = null;
    DatabaseReference db = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("ActiveAlerts");
    public String currentDatetime;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addAlertBTN = findViewById(R.id.addAlertBTN);
        usernameTV = findViewById(R.id.usernameLB);
        currentFixedCity = getCurrentFixedCity();
        currentDatetime = dateformat.format(c.getTime());
        //showAlert("Rain");
        Toast.makeText(this, currentFixedCity, Toast.LENGTH_LONG).show();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUsername = extras.getString("intentUsername");
            usernameTV.setText(currentUsername);
        }else {
            Log.i("UserLogin","Unable to load extras");
        }

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnap: snapshot.getChildren()){
                    ActiveAlert dbactiveAlert;
                    dbactiveAlert = postsnap.getValue(ActiveAlert.class);
                    if(dbactiveAlert.getAlertDate().equals(currentDatetime)){
                        //einai shmerinos kindinos
                        //twra tsekarw topothesia
                        if(dbactiveAlert.getAlertLocation().equals(currentFixedCity)){
                            //yparxei kindionos shmera edw poy eimai
                            //prepei na emfanisw alert me bash to eidos
                            showAlert(dbactiveAlert.getAlertType());
                        }
                    }else {
                        //den einai shmerinos kindinos
                        //prepei na ginei exp
                        ActiveAlert expActiveAlert = new ActiveAlert("exp","exp","exp",dbactiveAlert.getAlertReco());
                        db.child(postsnap.getKey()).setValue(expActiveAlert);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void goToAlertPage(View view) {
        Intent intent = new Intent(HomeActivity.this,AddAlertActivity.class);
        intent.putExtra("intentUsername",currentUsername);
        intent.putExtra("currentFixedCity",currentFixedCity);
        startActivity(intent);
    }

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "item1 pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.stats:
                Toast.makeText(this, "item1 pressed", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this,StatsActivity.class);
                intent2.putExtra("currentFixedCity",currentFixedCity);
                startActivity(intent2);
                //na pernaei kai thn polh
                return true;
            default:
                Toast.makeText(this, "Default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion


    public String getCurrentFixedCity(){
        ArrayList<String> cityList = new ArrayList<String>();
        cityList.add("athens");
        cityList.add("lamia");
        cityList.add("thessaloniki");
        cityList.add("patra");
        cityList.add("giannena");
        cityList.add("peiraias");
        cityList.add("city1");
        cityList.add("city2");
        cityList.add("city3");
        cityList.add("city4");

        int index = (int)(Math.random() * cityList.size());
        return cityList.get(index);
    }

    public void showAlert(String alertType){

        switch (alertType){
            case "Fire":
                //do fire alert
                dialogBuilder(getString(R.string.fire_alert_title),getString(R.string.fire_alert_message));
                break;
            case "Hot":
                //do hot alert
                dialogBuilder(getString(R.string.hot_alert_title),getString(R.string.hot_alert_message));
                break;
            case "Rain":
                //do rain alert
                dialogBuilder(getString(R.string.rain_alert_title),getString(R.string.rain_alert_message));
                break;
            default:
                break;
        }
    }

    public void dialogBuilder(String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "OK".
            }
        });
        AlertDialog alert2 = dialog.create();
        alert2.show();
    }



}