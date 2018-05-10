package dz.univoran.amd.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dz.univoran.amd.Constants;
import dz.univoran.amd.DBSqliteCon;
import dz.univoran.amd.R;
import dz.univoran.amd.objects.Donor_item;

public class ProfileActivity extends AppCompatActivity {

    private TextView Name,Email,Address,Phone,DOB,Sex,Group;
    String user_id,username,password;
    private Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_id=Constants.ID;
        username=Constants.USERNAME;
        password=Constants.PASSWORD;
        Name=(TextView)findViewById(R.id.name);
        Email=(TextView)findViewById(R.id.email);
        Address=(TextView)findViewById(R.id.address);
        Phone=(TextView)findViewById(R.id.phone);
        DOB=(TextView)findViewById(R.id.dob);
        Sex=(TextView)findViewById(R.id.sex);
        Group=(TextView)findViewById(R.id.group);
        retour=(Button)findViewById(R.id.rtrn) ;
        retour.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openMain();
    }
});

        String myurl1 = Constants.IP+"getprofile"  ;
        new ProfileActivity.MyAsyncTaskgetNews().execute(myurl1);


    }
    public void openMain(){
        Intent intent = new Intent(this,MainActivity.class);
        /*Bundle b=new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        //b.putString("name",obj.getString("name"));
        b.putString("id",user_id);
        intent.putExtras(b);*/
        startActivity(intent);
        this.finish();
        startActivity(intent);
    }
    public void nextPage(String data){

        try {
            JSONObject obj = new JSONObject(data);
            DBSqliteCon db=new DBSqliteCon(this);
            if (!obj.getString("donor_Id").equals("0")){
                //                                                                                                                                       linda
                db.addDonor(obj.getInt("donor_Id"),obj.getString("nom"),obj.getString("dateNaissance"),obj.getString("sex"),obj.getString("groupeSanguin"),obj.getString("address"),obj.getString("numero"),obj.getString("adresseMail"));
                Name.setText(obj.getString("nom"));
                DOB.setText(obj.getString("dateNaissance"));
                Sex.setText(obj.getString("sex"));
                Group.setText(obj.getString("groupeSanguin"));
                Address.setText(obj.getString("address"));
                Phone.setText(obj.getString("numero"));
                Email.setText(obj.getString("adresseMail"));


            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void sqlite(){

        try {

            DBSqliteCon db=new DBSqliteCon(this);
            Donor_item obj=db.getProfile(Integer.parseInt(Constants.ID));
            Name.setText(obj.getNom());
            DOB.setText(obj.getDateNaissance());
            Sex.setText(obj.getSex());
            Group.setText(obj.getGroupeSanguins());
            Address.setText(obj.getAddress());
            Phone.setText(obj.getNumero());
            Email.setText(obj.getEmail_Address());
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    /*public void retour (View view ){
       Intent i = new Intent(this,MainActivity.class);
       // startActivity(new Intent("dz.univoran.amd.activities.MainActivity"));
        startActivity(i);

    }*/

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

                jsonParam.put("id", user_id);
                jsonParam.put("username",username);
                jsonParam.put("password", password);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                //System.out.print(jsonParam2);
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
                    publishProgress(sb.toString(),"online");

                }else{
                    publishProgress("","offline");
                }
            } catch (Exception e) {
                publishProgress("","offline");
                // e.printStackTrace();
            }finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                if(progress[1]=="offline"){
                    //Toast.makeText(getApplicationContext(),"Exception : "+progress[1],Toast.LENGTH_LONG).show();
                    sqlite();
                }
                else nextPage(progress[0]);

            }  catch (Exception ex) {
                Toast.makeText(getApplicationContext(),"Exception",Toast.LENGTH_LONG).show();

            }

        }
        protected void onPostExecute(String  result2){

        }




    }







}
