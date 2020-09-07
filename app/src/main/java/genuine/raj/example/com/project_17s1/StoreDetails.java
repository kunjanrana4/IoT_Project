package genuine.raj.example.com.project_17s1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StoreDetails extends AppCompatActivity {

    EditText name,weight,height,contact,DOB;
    TextView edittext;
    RadioButton male,female;
    Button submit;
    String gender,uid,dob;
    DatabaseReference root,patient;
    FirebaseAuth auth;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        this.setTitle("Registration");

        myCalendar = Calendar.getInstance();
        edittext= (TextView) findViewById(R.id.DOB);
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(StoreDetails.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        name = findViewById(R.id.name);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        contact = findViewById(R.id.Contact);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        submit = findViewById(R.id.Lock);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="Male";
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="Female";
            }
        });

        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
        Toast.makeText(this,uid,Toast.LENGTH_LONG).show();
        root = FirebaseDatabase.getInstance().getReference();
        patient = FirebaseDatabase.getInstance().getReference("patients");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.DATE) - myCalendar.get(Calendar.DATE);
                if(name.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(StoreDetails.this,"Enter your name",Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
                else if(gender.equalsIgnoreCase("")) {
                    Toast.makeText(StoreDetails.this,"Select a gender",Toast.LENGTH_SHORT).show();
                }
                else if(weight.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(StoreDetails.this,"Enter your weight",Toast.LENGTH_SHORT).show();
                    weight.requestFocus();
                }
                else if(height.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(StoreDetails.this,"Enter your height",Toast.LENGTH_SHORT).show();
                    height.requestFocus();
                }
                else if(contact.getText().toString().length()<10)
                {
                    Toast.makeText(StoreDetails.this,"Enter a valid contact no.",Toast.LENGTH_SHORT).show();
                    contact.requestFocus();
                }
                else if(contact.getText().toString().length()>10)
                {
                    Toast.makeText(StoreDetails.this,"Phone no. is of 10 digits!",Toast.LENGTH_SHORT).show();
                    contact.requestFocus();
                }
                else if(contact.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(StoreDetails.this,"Enter your contact no.",Toast.LENGTH_SHORT).show();
                    contact.requestFocus();
                }
                else if(edittext.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(StoreDetails.this,"Pick your Birth-Date!",Toast.LENGTH_SHORT).show();
                    edittext.requestFocus();
                }
                else if(age<0)
                {
                    Toast.makeText(StoreDetails.this,"Birth-Date is invalid!",Toast.LENGTH_SHORT).show();
                    edittext.requestFocus();
                }
                else {
                    new AlertDialog.Builder(StoreDetails.this)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to lock the details?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    addPatientDetails();
                                    Toast.makeText(StoreDetails.this, "OK", Toast.LENGTH_SHORT).show();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
    }
    private void addPatientDetails()
    {
        String name1 = name.getText().toString();
        String weight1 = weight.getText().toString();
        String height1 = height.getText().toString();
        String phone = contact.getText().toString();

        Patients p = new Patients(uid,name1, weight1, height1, phone, gender,dob);
        patient.child(uid).setValue(p);
        Toast.makeText(this,"Patient Details entered!",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob = sdf.format(myCalendar.getTime()).toString();
        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
