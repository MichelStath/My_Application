package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameTB, passwordTB;
    CheckBox isAdminCB;
    Button registerBTN;

    private DatabaseReference mDatabase;



    // creating a variable for
    // our object class
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //region initializing our edittext , checkbox and button
        usernameTB = findViewById(R.id.usernameTB);
        passwordTB = findViewById(R.id.passwordTB);
        isAdminCB = findViewById(R.id.isAdmin);
        registerBTN = findViewById(R.id.regiserBTN);
        //endregion

        users =new Users();

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTB.getText().toString();
                String password = passwordTB.getText().toString();
                Boolean isAdmin = isAdminCB.isChecked();

                // Must check if username already exist
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
                    Users user = new Users(username,password,isAdmin);
                    mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if username already registered
                            if(snapshot.hasChild(username)){
                                Toast.makeText(RegisterActivity.this, getString(R.string.username_ar_reg), Toast.LENGTH_SHORT).show();
                            }else {
                                writeNewUser(user);
                                goToLogin(view);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });
                }
            }
        });
    }

    public void writeNewUser(Users user){
        //Table named users
        //Unique id set as username
        mDatabase.child("users").child(user.getUsername()).child("pass").setValue(user.getPassword()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterActivity.this, "All good", Toast.LENGTH_SHORT).show();
                //Log.i("Register", "Register Succ :"+username+" "+password+" "+isAdmin);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Fuck", Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.child("users").child(user.getUsername()).child("isADmin").setValue(user.getAdmin()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterActivity.this, "All good", Toast.LENGTH_SHORT).show();
                //Log.i("Register", "Register Succ :"+username+" "+password+" "+isAdmin);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Fuck", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToLogin(View view) {
        Log.i("Register","Login BTN pressed");
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}