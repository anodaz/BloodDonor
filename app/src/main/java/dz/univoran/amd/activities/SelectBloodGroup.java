package dz.univoran.amd.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dz.univoran.amd.R;

public class SelectBloodGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_blood_group);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
