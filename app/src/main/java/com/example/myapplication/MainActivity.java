package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText usernameTB,passTB;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        usernameTB = findViewById(R.id.usernameTB);
        passTB = findViewById(R.id.passwordTB);

    }

    public void login(View view) {
        String username = usernameTB.getText().toString();
        String password = passTB.getText().toString();

        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Enter username or pass", Toast.LENGTH_SHORT).show();
        }else{
            mDatabase = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
            mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(username)){
                        //user exist in db
                        //get pass from db
                        final String dbPass = snapshot.child(username).child("pass").getValue(String.class);
                        final Boolean isAdmin = snapshot.child(username).child("isADmin").getValue(Boolean.class); //////////Change typo isADmin to isAdmin

                        if (dbPass.equals(password)){
                            //login success
                            Toast.makeText(MainActivity.this, "login ok", Toast.LENGTH_SHORT).show();
                            if(isAdmin){
                                Intent intent = new Intent(MainActivity.this,AdminHomeActivity.class);
                                intent.putExtra("intentUsername",username);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                intent.putExtra("intentUsername",username);
                                startActivity(intent);
                                finish();
                            }

                        }else {
                            Toast.makeText(MainActivity.this, "Wrong Pass", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Username not Registered", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void register(View view) {
        Log.i("Login","Register BTN pressed");
        //redirect to register page
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    } // go to register page


}