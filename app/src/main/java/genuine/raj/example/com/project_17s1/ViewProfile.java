package genuine.raj.example.com.project_17s1;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.net.MailTo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
class Patient
{
    public String name,contact,dob,gender,weight,height,id;

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getDob() {
        return dob;
    }

    public String getWeight() {
        return weight;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
public class ViewProfile extends AppCompatActivity
{
    DatabaseReference mRef,database;
    EditText name,phone,gender,dob,height,weight;
    Button update,save,cancel;
    FirebaseAuth auth;
    String cmpid = auth.getUid();
    String n,p,g,d,h,w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        initiate();//initialize all fields

        update.setBackgroundColor(Color.rgb(46,100,254));
        save.setBackgroundColor(Color.rgb(46,100,254));
        cancel.setBackgroundColor(Color.rgb(227,48,48));

        database = FirebaseDatabase.getInstance().getReference();
        mRef = database.child("patients");

        display();//display data
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.INVISIBLE);
                getValue();
                visible();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getValue();
                        if(name.equals(n) && phone.equals(p) && gender.equals(g) && dob.equals(d) && height.equals(h) && weight.equals(w))
                        {
                            Toast.makeText(ViewProfile.this, "No data has been changed.", Toast.LENGTH_SHORT).show();
                            invisible();
                            update.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            update();
                            Toast.makeText(ViewProfile.this, "Data has been updated successfully.", Toast.LENGTH_SHORT).show();
                            invisible();
                            update.setVisibility(View.VISIBLE);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setText(n,TextView.BufferType.EDITABLE);
                        phone.setText(p,TextView.BufferType.EDITABLE);
                        gender.setText(g,TextView.BufferType.EDITABLE);
                        dob.setText(d,TextView.BufferType.EDITABLE);
                        height.setText(h,TextView.BufferType.EDITABLE);
                        weight.setText(w,TextView.BufferType.EDITABLE);
                        Toast.makeText(ViewProfile.this, "No data has been changed.", Toast.LENGTH_SHORT).show();
                        invisible();
                        update.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    public  void initiate()
    {
        name = findViewById(R.id.Name);
        phone =  findViewById(R.id.Contact);
        gender = findViewById(R.id.Gender);
        dob = findViewById(R.id.Date);
        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);
        update = findViewById(R.id.Update);
        save = findViewById(R.id.Save);
        cancel = findViewById(R.id.Cancel);
        invisible();
    }

    public void invisible()
    {
        name.setEnabled(false);
        phone.setEnabled(false);
        gender.setEnabled(false);
        height.setEnabled(false);
        weight.setEnabled(false);
        dob.setEnabled(false);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
    }

    public void visible()
    {
        name.setEnabled(true);
        phone.setEnabled(true);
        gender.setEnabled(true);
        height.setEnabled(true);
        weight.setEnabled(true);
        dob.setEnabled(true);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
    }

    public void display()
    {
        mRef.child(cmpid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient patient = dataSnapshot.getValue(Patient.class);
                name.setText(patient.getName(),TextView.BufferType.EDITABLE);
                phone.setText(patient.getContact(),TextView.BufferType.EDITABLE);
                gender.setText(patient.getGender(),TextView.BufferType.EDITABLE);
                dob.setText(patient.getDob(),TextView.BufferType.EDITABLE);
                height.setText(patient.getHeight(),TextView.BufferType.EDITABLE);
                weight.setText(patient.getWeight(),TextView.BufferType.EDITABLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewProfile.this, "!!!Data not available!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void update()
    {
        Patient patient = new Patient();
        patient.setName(n);
        patient.setContact(p);
        patient.setGender(g);
        patient.setDob(d);
        patient.setHeight(h);
        patient.setWeight(w);
        mRef.child(cmpid).setValue(patient);
    }

    public void getValue()
    {
        n = name.getText().toString();
        p = phone.getText().toString();
        g = gender.getText().toString();
        d = dob.getText().toString();
        h = height.getText().toString();
        w = weight.getText().toString();
    }
}
