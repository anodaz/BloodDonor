package dz.univoran.amd.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

public class SelectBloodGroup extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_blood_group);


    }
    public void testb(View view ){
        String group="A+";
        Intent i = new Intent(SelectBloodGroup.this, Donor_List.class);

        //Bundle b=new Bundle();
        switch (view.getId()){
            case R.id.aplu:
                group="A+";
                break;
            case R.id.aneg:
                group="A+";
                break;
            case R.id.bplu:
                group="B+";
                break;
            case R.id.bneg:
                group="B-";
                break;
            case R.id.opos:
                group="O+";
                break;
            case R.id.oneg:
                group="O-";
                break;
                case R.id.abpos:
                group="AB+";
                break;
            case R.id.abneg:
                group="AB-";
                break;
        }
        Constants.GROUPP=group;
        startActivity(i);
        /*    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="90dp"
        android:layout_height="90dp"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:srcCompat="@android:drawable/ic_dialog_email" />
*/
    }

}


