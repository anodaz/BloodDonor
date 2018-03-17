package dz.univoran.amd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

/**
 * Created by DELL on 31/03/2017.
 */

public class SignUpActivity extends AppCompatActivity{


    EditText emailEdiText ,passwordEditText,name_editText;
    Button signupButton;//loginButton,;

    //Button signout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences(Constants.PREFS, MODE_PRIVATE).getBoolean(Constants.DARK_THEME, false))
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        setContentView(R.layout.activity_sign_up);


        emailEdiText = (EditText)findViewById(R.id.email_editText);
        passwordEditText =(EditText)findViewById(R.id.password_editText);
        signupButton= (Button)findViewById(R.id.signinButton);
        name_editText = (EditText)findViewById(R.id.name_editText);
        //loginButton = (Button)findViewById(R.id.loginButton);
        //signout = (Button)findViewById(R.id.signout);
        /*signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });*/


        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });*/


    }




}
