package dz.univoran.amd.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

public class ProfileActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 1;
    int PLACE_PICKER_REQUEST = 2;

    //private FloatingActionButton profileEditDone;
    private FloatingActionButton locateFab;

    private TextInputLayout profileName;
    private TextInputLayout profileSurname;
    private TextInputLayout profilePhone;
    private TextInputLayout profileBloodGroup;
    private TextInputLayout profileAddress;
    private TextInputLayout profileBbName;
    private TextView birthDateEditText;
    private Uri imageUrl;
    boolean t;
    SharedPreferences prefs;
    boolean isBloodBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        t = i.getBooleanExtra("isfromsignup",false);
        prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        if(!prefs.getBoolean(Constants.ISBLOODBANK,false)){
            isBloodBank = false;
            if (getSharedPreferences(Constants.PREFS, MODE_PRIVATE).getBoolean(Constants.DARK_THEME, false))
                setTheme(R.style.AppTheme_Dark);
            setContentView(R.layout.activity_profile);

            birthDateEditText=(TextView) findViewById(R.id.birthday_text);
            //profileEditDone=(FloatingActionButton)findViewById(R.id.profile_edit_done_fab);
            profileName = (TextInputLayout)findViewById(R.id.profile_name);
            profileSurname = (TextInputLayout)findViewById(R.id.profile_surname);
            profilePhone = (TextInputLayout)findViewById(R.id.profile_phone);
            profileBloodGroup = (TextInputLayout)findViewById(R.id.profile_blood_group);
            profileAddress = (TextInputLayout)findViewById(R.id.profile_address);

            locateFab = (FloatingActionButton)findViewById(R.id.locateFab);
            locateFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


                }
            });

            final Calendar myCalendar = Calendar.getInstance();

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(myCalendar);
                }

            };

            findViewById(R.id.birthday_button).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(ProfileActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            birthDateEditText.setInputType(InputType.TYPE_NULL);
            birthDateEditText.requestFocus();

            /*profileEditDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        startActivityForResult(builder.build(ProfileActivity.this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });*/
        }

        else{
            isBloodBank = true;
            setContentView(R.layout.activity_profilebloodbank);
            profileName = (TextInputLayout)findViewById(R.id.profile_name);
            profileSurname = (TextInputLayout)findViewById(R.id.profile_surname);
            profileBbName = (TextInputLayout)findViewById(R.id.profile_bb_name);
            profilePhone = (TextInputLayout)findViewById(R.id.profile_phone);
            profileAddress = (TextInputLayout)findViewById(R.id.profile_address);
            locateFab = (FloatingActionButton)findViewById(R.id.locateFab);

            locateFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();



                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        if(!t) {
            super.onBackPressed();
        } else Toast.makeText(this, R.string.backpress, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                if (!t)
                onBackPressed();
                else Toast.makeText(this, R.string.backpress, Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    public void getPicture(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    private void updateLabel(Calendar myCalendar) {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthDateEditText.setText(sdf.format(myCalendar.getTime()));
    }



}
