package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {
    TextView adminUsernameTV;
    AdminAlerts adminAlerts;
    List<AdminAlerts> alertList = new ArrayList<>();
    String at;
    String al;
    String ir;
    String reco;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Bundle extras = getIntent().getExtras();
        String currentUsername = null;
        mDatabase = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("AdminAlerts");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AdminAlerts> items2 = new ArrayList<AdminAlerts>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    at =  postSnapshot.child("alertType").getValue().toString();
                    al =  postSnapshot.child("alertLocation").getValue().toString();
                    ir =  postSnapshot.child("read").getValue().toString();
                    reco = postSnapshot.child("reco").getValue().toString();
                    items2.add(new AdminAlerts(at,al,Boolean.parseBoolean(ir),reco));
                    Log.i("Alert Type ",at);
                    Log.i("Alert Location ",al);
                    Log.i("isRead",ir);
                    //Log.i("Reco",reco);

                }
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });

        if(extras != null){
            currentUsername = extras.getString("intentUsername");
            adminUsernameTV = findViewById(R.id.adminUsernameLB);
            adminUsernameTV.setText(currentUsername);
        }else {
            Log.i("AdminLogin","Unable to load extras");
        }

    }

    //region menu
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



}