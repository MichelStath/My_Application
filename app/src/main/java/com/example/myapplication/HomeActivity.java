package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTV;
    FloatingActionButton addAlertBTN;
    public String currentUsername = null;
    public String currentFixedCity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addAlertBTN = findViewById(R.id.addAlertBTN);
        usernameTV = findViewById(R.id.usernameLB);
        currentFixedCity = getCurrentFixedCity();

        Toast.makeText(this, currentFixedCity, Toast.LENGTH_LONG).show();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUsername = extras.getString("intentUsername");
            usernameTV.setText(currentUsername);
        }else {
            Log.i("UserLogin","Unable to load extras");
        }

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



}