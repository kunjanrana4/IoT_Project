package genuine.raj.example.com.project_17s1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class CheckHealth extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView temprature,accelerometer;
    private Firebase firebase,firebase2;
    String temp,acc;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_health);
        this.setTitle("Check Health");
        temprature=(TextView)findViewById(R.id.HEALTHCHECK_TEMP);
        accelerometer=(TextView)findViewById(R.id.HEALTHCHECK_ACC);
        Firebase.setAndroidContext(this);
        firebase=new Firebase("https://project17s1-74179.firebaseio.com/Sensor1");
        firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                temp= ((dataSnapshot.getValue(String.class)));
                temprature.setText("Temprature is "+temp);
            }

            @Override
            public void onCancelled(com.firebase.client.FirebaseError firebaseError) {
            }
        });
        firebase2=new Firebase("https://project17s1-74179.firebaseio.com/Sensor2");
        firebase2.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                acc= ((dataSnapshot.getValue(String.class)));
                accelerometer.setText("Motion is "+acc);
            }

            @Override
            public void onCancelled(com.firebase.client.FirebaseError firebaseError) {
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.MAINACTIVITY_DRAWER);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.MAINACTIVITY_NAVIGATION);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.ViewProfile)
        {
            Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(mainIntent);
        }
        if(id==R.id.CheckHealth)
        {
            Intent checkHealthIntent=new Intent(getApplicationContext(),CheckHealth.class);
            startActivity(checkHealthIntent);
        }
        if(id==R.id.SignOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(CheckHealth.this, LoginActivity.class));
            finish();
        }
        return false;
    }


}
