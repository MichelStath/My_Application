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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements RecyclerViewInterface {
    TextView adminUsernameTV;
    AdminAlerts adminAlerts;
    List<AdminAlerts> testList = new ArrayList<>();
    List<AdminAlerts> list = new ArrayList<>();

    String at;
    String al;
    String ir;
    String reco;
    String desc;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Bundle extras = getIntent().getExtras();
        String currentUsername = null;


        ref = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("AdminAlerts");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                //ΚΟΙΤΑΩ ΤΟ ΚΑΘΕ ALERT ΠΟΥ ΔΕΝ ΕΧΕΙ ΔΙΑΒΑΣΕΙ Ο ADMIN
                for(DataSnapshot postsnap: snapshot.getChildren()){
                    at =  postsnap.child("alertType").getValue().toString();
                    al =  postsnap.child("alertLocation").getValue().toString();
                    ir =  postsnap.child("read").getValue().toString();
                    reco = postsnap.child("reco").getValue().toString();
                    desc = postsnap.child("desc").getValue().toString();
                    if(ir.equals("false")){
                        list.add(new AdminAlerts(at,al,Boolean.parseBoolean(ir),reco,desc));
                    }else {
                        Log.i("isRead","true,Not show");
                    }
                }
                //ΕΜΦΑΝΙΣΗ ΤΩΝ ΑΔΙΑΒΑΣΤΩΝ ALERT ΣΤΟ RECYCLERVIEW
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(),list,AdminHomeActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                //na ftiaksw neo stats gia admin poy na thelei mono mera
            default:
                Toast.makeText(this, "Default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion

    @Override
    public void onAcceptBtnClicked(int position) {
        //O ADMIN ΑΠΟΔΕΧΤΗΚΕ ΤΟΝ ΚΙΝΔΥΝΟ ΟΠΟΤΕ ΠΡΕΠΕΙ ΝΑ ΑΠΟΘΗΚΕΥΤΕΙ ΣΤΗΝ ΒΑΣΗ ACTIVEALERT ΓΙΑ ΝΑ ΤΟ ΕΙΔΟΠΟΙΗΘΟΥΝ ΟΙ ΧΡΗΣΤΕΣ
        Log.i("AcceptBTN","Pressed");
        //ΠΑΙΡΝΩ ΤΑ ΣΤΟΙΧΕΙΑ ΑΠΟ ΕΠΙΛΕΓΜΕΝΟ ALERT
        String alertType = list.get(position).getAlertType();
        String alertLocation = list.get(position).getAlertLocation();
        Boolean isRead = list.get(position).getRead();
        String desc = list.get(position).getDesc();
        String alertReco = list.get(position).getReco();
        //generate date from reco
        String alertDate = alertReco.substring(0,10);
        Log.i("Date From Reco", isRead.toString());
        //get db
        DatabaseReference db = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        //make ActiveAlert & AdminAlert object
        ActiveAlert activeAlert = new ActiveAlert(alertType,alertLocation,alertDate,alertReco);
        AdminAlerts expAdminAlert = new AdminAlerts(alertType,alertLocation,true,alertReco,desc);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prepei to isRead na ginei true
                for(int i = 1; i < snapshot.child("AdminAlerts").getChildrenCount() + 1; i++){
                    String dbReco = snapshot.child("AdminAlerts").child(String.valueOf(i)).child("reco").getValue(String.class);
                    if(dbReco.equals(alertReco)){
                        //ΤΟ ALERT ΠΟΥ ΠΑΤΗΘΗΚΕ ΠΡΕΠΕΙ ΝΑ ΓΙΝΕΙ ΔΙΑΒΑΣΜΕΝΟ ΩΣΤΕ ΝΑ ΜΗΝ ΞΑΝΑΕΜΦΑΝΙΣΤΕΙ ΣΤΟΝ ADMIN
                        db.child("AdminAlerts").child(String.valueOf(i)).setValue(expAdminAlert);
                        recreate();
                        Log.i("Admin read", "False changed to True");
                    }else {
                        Log.i("Not same Reco", "Not same Reco");
                    }

                }
                //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ ALERT ΣΤΟ ACTIVEALERT
                long activeAlertMaxid = snapshot.child("ActiveAlerts").getChildrenCount() + 1;
                db.child("ActiveAlerts").child(String.valueOf(activeAlertMaxid)).setValue(activeAlert);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onDeclineBtnClicked(int position) {
        //ΒΛ ACCEPTBTN
        Log.i("DeclineBTN","Pressed");
        String alertType = list.get(position).getAlertType();
        String alertLocation = list.get(position).getAlertLocation();
        Boolean isRead = list.get(position).getRead();
        String desc = list.get(position).getDesc();
        String alertReco = list.get(position).getReco();
        DatabaseReference db = FirebaseDatabase.getInstance("https://my-application-70087-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        //make ActiveAlert & AdminAlert object
        AdminAlerts expAdminAlert = new AdminAlerts(alertType,alertLocation,true,alertReco,desc);
        //prepei to isRead na ginei true
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i < snapshot.child("AdminAlerts").getChildrenCount() + 1; i++){
                    String dbReco = snapshot.child("AdminAlerts").child(String.valueOf(i)).child("reco").getValue(String.class);
                    if(dbReco.equals(alertReco)){
                        //entopisthke to ID toy adminalert poy exei to reco poy ekane accept
                        db.child("AdminAlerts").child(String.valueOf(i)).setValue(expAdminAlert);
                        Log.i("Admin read", "False changed to True");
                        recreate();
                    }else {
                        Log.i("Not same Reco", "Not same Reco");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}