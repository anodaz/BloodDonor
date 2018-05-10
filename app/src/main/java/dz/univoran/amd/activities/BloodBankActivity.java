package dz.univoran.amd.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dz.univoran.amd.Constants;
import dz.univoran.amd.DBSqliteCon;
import dz.univoran.amd.R;
import dz.univoran.amd.objects.BankBlood;
import dz.univoran.amd.objects.BloodBank;
import dz.univoran.amd.objects.Donor_item;

public class BloodBankActivity extends AppCompatActivity {
    private ArrayList<BankBlood> rentalProperties=new ArrayList<BankBlood>();
    private ArrayAdapter adapter;
    public ListView listview;
    public Spinner spinner;
    public String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        listview=(ListView)findViewById(R.id.list);
        spinner=(Spinner)findViewById(R.id.selec_city);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                city=parentView.getItemAtPosition(position).toString();
                String url= Constants.IP+"getBank/"+parentView.getItemAtPosition(position).toString();
                new BloodBankActivity.MyAsyncTaskgetNews().execute(url);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
    }
    public void sqlite(){
        try {

            DBSqliteCon db=new DBSqliteCon(this);
            ArrayList<BankBlood> objs=db.getBanks(city);
            rentalProperties.clear();
            // adapter.clear();
            for (int i = 0 ; i < objs.size(); i++){
                rentalProperties.add(objs.get(i));

            }
            adapter = new BloodBankActivity.propertyArrayAdapter(this, 0, rentalProperties);
            listview.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }



    }
    public void nextPage(String data){

        try {
            System.out.println("data : "+ data);
            JSONObject obj = new JSONObject(data);
            JSONArray objs = obj.getJSONArray("banks");
            DBSqliteCon db=new DBSqliteCon(this);
            rentalProperties.clear();
           // adapter.clear();
            for (int i = 0 ; i < objs.length(); i++){
                db.addBankBlood(objs.getJSONObject(i).getInt("bloodBankId"),objs.getJSONObject(i).getString("bloodBankName"),objs.getJSONObject(i).getString("address"),objs.getJSONObject(i).getString("phone"),objs.getJSONObject(i).getString("city"));
                rentalProperties.add(new BankBlood(objs.getJSONObject(i).getInt("bloodBankId"),objs.getJSONObject(i).getString("bloodBankName"),objs.getJSONObject(i).getString("address"),objs.getJSONObject(i).getString("phone"),objs.getJSONObject(i).getString("city")));

            }
            adapter = new BloodBankActivity.propertyArrayAdapter(this, 0, rentalProperties);
            listview.setAdapter(adapter);
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

            HttpURLConnection urlConnection=null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestProperty("Content-Type","application/json");

                //  urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                urlConnection.connect();
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


    class propertyArrayAdapter extends ArrayAdapter<BankBlood> {

        private Context context;
        private List<BankBlood> rentalProperties;

        //constructor, call on creation
        public propertyArrayAdapter(Context context, int resource, ArrayList<BankBlood> objects) {
            super(context, resource, objects);

            this.context = context;
            this.rentalProperties = objects;
        }
        //called when rendering the list
        public View getView(final int position, View convertView, ViewGroup parent) {

            //get the property we are displaying
            final BankBlood bankblood = rentalProperties.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(BloodBankActivity.LAYOUT_INFLATER_SERVICE);

            //conditionally inflate either standard or special template
            View view;
            view = inflater.inflate(R.layout.activity_blood_bank_item, null);

            TextView BloodBankName = (TextView) view.findViewById(R.id.BloodBankName);
            TextView Address = (TextView) view.findViewById(R.id.Address);
            TextView Phone = (TextView) view.findViewById(R.id.Phone);
            TextView City = (TextView) view.findViewById(R.id.City);
            BloodBankName.setText(bankblood.getBloodBankName());
            Address.setText(bankblood.getAddress());
            Phone.setText(bankblood.getPhone());
            City.setText(bankblood.getCity());
            //get the image associated with this property
            //int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
            //image.setImageResource(imageID);

            return view;
        }
    }
}
