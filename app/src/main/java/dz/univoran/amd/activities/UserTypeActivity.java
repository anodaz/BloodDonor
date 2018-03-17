package dz.univoran.amd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;


public class UserTypeActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean isBloodBank = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences(Constants.PREFS, MODE_PRIVATE).getBoolean(Constants.DARK_THEME, false))
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_usertype);

        final Button b = (Button) findViewById(R.id.Yes);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isBloodBank = true;
                setsharedpreference();
                Intent i = new Intent(UserTypeActivity.this, ProfileActivity.class);
                i.putExtra("isfromsignup",true);
                startActivity(i);
                finish();

            }

        });

        final Button b1 = (Button) findViewById(R.id.No);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isBloodBank = false;
                setsharedpreference();
                Intent j = new Intent(UserTypeActivity.this, ProfileActivity.class);
                j.putExtra("isfromsignup",true);
                startActivity(j);
                finish();

            }
        });
    }

    public void setsharedpreference() {
        prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        editor = prefs.edit();
        editor.putBoolean(Constants.ISBLOODBANK, isBloodBank);
        editor.apply();

    }


}


