package dz.univoran.amd.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.AsyncTask;

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
import dz.univoran.amd.widget.DateDialog;

public class Become_Donor extends AppCompatActivity {
    EditText Name,Email,Address,Phone,DOB;
    Spinner spinner_sex;
    Spinner spinner_group;
    ArrayAdapter<CharSequence> adapter_sex;
    ArrayAdapter<CharSequence> adapter_group;
    TextInputLayout birthday_text;
    Button submit;
    String user_id,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become__donor);
        user_id= Constants.ID;
        username=Constants.USERNAME;
        password=Constants.PASSWORD;
        Name=(EditText)findViewById(R.id.name);
        Email=(EditText)findViewById(R.id.email);
        Address=(EditText)findViewById(R.id.address);
        Phone=(EditText)findViewById(R.id.phone);
        DOB=(EditText)findViewById(R.id.dob);
        submit=(Button)findViewById(R.id.sbmt);
        spinner_sex=(Spinner)findViewById(R.id.selec_sex);
        spinner_group=(Spinner)findViewById(R.id.selec_group);
        adapter_sex=ArrayAdapter.createFromResource(this,R.array.Sex,android.R.layout.simple_spinner_item);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter_sex);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
        adapter_group=ArrayAdapter.createFromResource(this,R.array.Group,android.R.layout.simple_spinner_item);
        adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_group.setAdapter(adapter_group);
        spinner_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        birthday_text=(TextInputLayout) findViewById(R.id.donor_date);
        birthday_text.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myurl1 = Constants.IP+"AddDonor"  ;
               new MyAsyncTaskgetNews().execute(myurl1);

            }
        });
    }
    public void nextPage(String data){
        try {
            JSONObject obj = new JSONObject(data);
            if (!obj.getString("donor_Id").equals("0")){
                Intent i = new Intent(this, ProfileActivity.class);
               // Bundle b=new Bundle();

                Constants.ID=obj.getString("id");
                Constants.NAME=obj.getString("name");
                Constants.DOB=obj.getString("dob");
                Constants.SEX=obj.getString("sex");
                Constants.GROUP=obj.getString("group");
                Constants.ADRESSE=obj.getString("addresse");
                Constants.PHONE=obj.getString("phone");
                Constants.MAIL=obj.getString("mail");
                Constants.USERNAME=obj.getString("user");
                Constants.PASSWORD=obj.getString("password");

              /*  b.putString("donor_Id",obj.getInt("donor_Id")+"");
                b.putString("nom",obj.getString("nom"));
                b.putString("dateNaissance",obj.getString("dateNaissance"));
                b.putString("sex",obj.getString("sex"));
                b.putString("groupeSanguin",obj.getString("groupeSanguin"));
                b.putString("address",obj.getString("address"));
                b.putString("numero",obj.getString("numero"));
                b.putString("adresseMail",obj.getString("adresseMail"));
                b.putString("username",username);
                b.putString("password",password);
                //b.putString("name",obj.getString("name"));
                b.putString("id",user_id);*/
               // i.putExtras(b);
                startActivity(i);
                this.finish();
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
                JSONObject jsonParam1 = new JSONObject();
                JSONObject jsonParam2 = new JSONObject();
                jsonParam.put("donor_Id", user_id);
                jsonParam.put("nom", Name.getText());
                jsonParam.put("dateNaissance",DOB.getText());
                jsonParam.put("address", Address.getText());
                jsonParam.put("numero",Phone.getText());
                jsonParam.put("adresseMail", Email.getText());
                jsonParam.put("sex",spinner_sex.getSelectedItem().toString());
                jsonParam.put("groupeSanguin", spinner_group.getSelectedItem().toString());

                jsonParam1.put("id", user_id);
                jsonParam1.put("username",username);
                jsonParam1.put("password", password);
                jsonParam2.putOpt("a",jsonParam);
                jsonParam2.putOpt("u",jsonParam1);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    //System.out.print(jsonParam2);
                out.write(jsonParam2.toString());
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
