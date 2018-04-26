package dz.univoran.amd.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

/**
 * Created by Ikram .
 */

public class LoginActivity extends AppCompatActivity  {

   AutoCompleteTextView Email;
    AutoCompleteTextView Password;
    private Button SignIn;
    private int counter=0;

    Boolean backPressFlag = false;
    SharedPreferences pref;
    String email;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressText;
    private View mProgressView;
    private View mLoginFormView;
    AutoCompleteTextView user;
    AutoCompleteTextView pass;
    TextView stat;

    private ProgressDialog dialog;
    boolean check;

    private String isBank;

    private static final int RC_SIGN_IN = 9001;
    String user_id,username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       /* Bundle b=getIntent().getExtras();
        user_id=b.getString("id");
        username=b.getString("username");
        password=b.getString("password");
*/
       // user=(AutoCompleteTextView)findViewById(R.id.email);
       // pass=(AutoCompleteTextView)findViewById(R.id.password);
        stat=(TextView)findViewById(R.id.notLogin);
        Email=(AutoCompleteTextView)findViewById(R.id.email);
        Password=(AutoCompleteTextView)findViewById(R.id.password);
        SignIn=(Button)findViewById(R.id.email_sign_in_button);
        //good
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Validate(Email.getText().toString().trim(),Password.getText().toString().trim());
                String myurl = Constants.IP+"login"  ;
                new  MyAsyncTaskgetNews().execute(myurl);
            }
        });

    }

    public void nextPage(String data){
        try {
            JSONObject obj = new JSONObject(data);
            if (!obj.getString("id").equals("0")){
                Intent i = new Intent(this, MainActivity.class);
                Constants.USERNAME=obj.getString("user");
                Constants.PASSWORD=obj.getString("password");
                Constants.ID=obj.getString("id");

                startActivity(i);
                this.finish();
            }else {//
                stat.setText("The username or password is incorrect.");

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void signup(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

            // String url="https://api.ipify.org?format=json";
            //     stat.setText(url);

        }
        @Override
        protected String  doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();


            //String http = "http://android.schoolportal.gr/Service.svc/SaveValues";


            HttpURLConnection urlConnection=null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestProperty("Content-Type","application/json");

                //  urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                urlConnection.connect();

                //Create JSONObject here
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", "1");
                jsonParam.put("username", Email.getText());
               // jsonParam.put("name", "null");
                jsonParam.put("password", Password.getText());
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonParam.toString());
                out.close();

                int HttpResult =urlConnection.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    publishProgress(sb.toString());
                }else{
                    publishProgress(urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
            catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {

                //    stat.setText(progress[0]
                nextPage(progress[0]);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        protected void onPostExecute(String  result2){

        }




    }


    private void Validate (String Email,String Password){
        if(Email.equals("admin") && Password.equals("1234")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            counter--;
            // info.setText("No Essai remaining !"+String.valueOf(counter));
            if(counter==0)
                SignIn.setEnabled(false);
        }
        }




    }




