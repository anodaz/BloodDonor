package dz.univoran.amd.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

public class BloodBankDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String item;
    List <String> categories;
    TextView opos, oneg, apos, aneg, bpos, bneg, abpos, abneg, bloodGroupTextView,bbName,bbLocation;
    EditText bloodQuantity;
    Button submitBloodQuantity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences(Constants.PREFS, MODE_PRIVATE).getBoolean(Constants.DARK_THEME, false))
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_blood_bank_details);

        opos=(TextView) findViewById(R.id.opos);
        oneg=(TextView) findViewById(R.id.oneg);
        apos=(TextView) findViewById(R.id.apos);
        aneg=(TextView) findViewById(R.id.aneg);
        bpos=(TextView) findViewById(R.id.bpos);
        bneg=(TextView) findViewById(R.id.bneg);
        abpos=(TextView) findViewById(R.id.abpos);
        abneg=(TextView) findViewById(R.id.abneg);
        bbName = (TextView) findViewById(R.id.bbName);
        bbLocation = (TextView) findViewById(R.id.bbLocation);

        bloodGroupTextView = (TextView) findViewById(R.id.bloodGroupTextView);

        submitBloodQuantity = (Button) findViewById(R.id.submitBloodQuantity);

        bloodQuantity = (EditText) findViewById(R.id.bloodQuantity);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories = new ArrayList <String>();
        categories.add("O+");
        categories.add("O-");
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        final Intent i = getIntent();
        String name = i.getStringExtra("name");
        String location = i.getStringExtra("location");

    }

    public void updateColour(TextView tv){
        if(!tv.getText().toString().equals("")){
            int val = Integer.parseInt(tv.getText().toString());
            if(val<=10){
                tv.setBackgroundResource(R.color.lt10);
            }
            else if(val<=20){
                tv.setBackgroundResource(R.color.lt20);
            }
            else if(val<=30){
                tv.setBackgroundResource(R.color.lt30);
            }
            else if(val<=40){
                tv.setBackgroundResource(R.color.lt40);
            }
            else if(val<=50){
                tv.setBackgroundResource(R.color.lt50);
            }
            else if(val<=60){
                tv.setBackgroundResource(R.color.lt60);
            }
            else if(val<=70){
                tv.setBackgroundResource(R.color.lt70);
            }
            else if(val<=80){
                tv.setBackgroundResource(R.color.lt80);
            }
        }
        else{
            tv.setBackgroundResource(R.color.colorAccent);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}
