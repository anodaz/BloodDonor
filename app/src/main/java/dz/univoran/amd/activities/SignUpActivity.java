package dz.univoran.amd.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;


/**
 * Created by Ikram.
 */

public class SignUpActivity extends AppCompatActivity{


    EditText emailEdiText ,passwordEditText,name_editText;
    TextView stat;
    Button signupButton;//loginButton,;

    //Button signout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences(Constants.PREFS, MODE_PRIVATE).getBoolean(Constants.DARK_THEME, false))
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        setContentView(R.layout.activity_sign_up);


        emailEdiText = (EditText)findViewById(R.id.etemail);
        passwordEditText =(EditText)findViewById(R.id.etpass);
        signupButton= (Button)findViewById(R.id.signupbtn);
        name_editText = (EditText)findViewById(R.id.etname);
        stat = (TextView) findViewById(R.id.state);
        //loginButton = (Button)findViewById(R.id.loginButton);
        //signout = (Button)findViewById(R.id.signout);
        /*signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });*/


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myurl = Constants.IP+"AddUser"  ;
                new MyAsyncTaskgetNews().execute(myurl);
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
                stat.setText("account exist");//

            }

        }catch (Exception e){
            e.printStackTrace();
        }


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
                jsonParam.put("name", name_editText.getText());
                jsonParam.put("username", emailEdiText.getText());
                jsonParam.put("password", passwordEditText.getText());
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




}
