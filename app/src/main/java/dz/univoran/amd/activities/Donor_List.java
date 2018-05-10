package dz.univoran.amd.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import dz.univoran.amd.objects.Donor_item;

public class Donor_List extends AppCompatActivity {
private ArrayList<Donor_item> rentalProperties=new ArrayList<Donor_item>();
private ArrayAdapter adapter;
public ListView listview;
public String group;
public   String user_id,username,password;
private Button retour;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor__list);
    Bundle b=getIntent().getExtras();
    group=b.getString("group");
    user_id=Constants.ID;
    username=Constants.USERNAME;
    password=Constants.PASSWORD;
        String url= Constants.IP+"getDonor/"+group;
        new MyAsyncTaskgetNews().execute(url);
        listview=(ListView)findViewById(R.id.dnrlist);
       // retour=(Button)findViewById(R.id.rtrn);
        //listview.set;


    }
   /* public void openForm(){
        Intent intent = new Intent(this,SelectBloodGroup.class);
        Bundle b=new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        //b.putString("name",obj.getString("name"));
        b.putString("id",user_id);
        intent.putExtras(b);
        startActivity(intent);
        this.finish();
        startActivity(intent);
    }*/
    public void nextPage(String data){
        try {
            JSONObject objs = new JSONObject(data);
            JSONArray allp = objs.getJSONArray("donors");
            DBSqliteCon db=new DBSqliteCon(this);
            for (int i = 0 ; i < allp.length(); i++){
                /*
                 "address": "n 90 marseille",
            "adresseMail": "zaho@gmail.com",
            "dateNaissance": "2018-03-07",
            "donor_Id": 2,
            "groupeSanguin": "B+",
            "nom": "zaho",
            "numero": "578923021",
            "sex": "femme"
            */
                db.addDonor(allp.getJSONObject(i).getInt("donor_Id"),allp.getJSONObject(i).getString("nom"),allp.getJSONObject(i).getString("dateNaissance"),allp.getJSONObject(i).getString("sex"),allp.getJSONObject(i).getString("address"),allp.getJSONObject(i).getString("groupeSanguin"),allp.getJSONObject(i).getString("numero"),allp.getJSONObject(i).getString("adresseMail"));
                rentalProperties.add(new Donor_item(allp.getJSONObject(i).getInt("donor_Id"),allp.getJSONObject(i).getString("nom"),allp.getJSONObject(i).getString("dateNaissance"),allp.getJSONObject(i).getString("sex"),allp.getJSONObject(i).getString("address"),allp.getJSONObject(i).getString("groupeSanguin"),allp.getJSONObject(i).getString("numero"),allp.getJSONObject(i).getString("adresseMail")));

            }
            adapter = new Donor_List.propertyArrayAdapter(this, 0, rentalProperties);

            listview.setAdapter(adapter);
            //Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
            //showDialog(rentalProperties.get(1),1);

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void sqlite(){
        try {

            DBSqliteCon db=new DBSqliteCon(this);
            ArrayList<Donor_item> allp=db.selectBloodGroup(group);
            for (int i = 0 ; i < allp.size(); i++){
                /*
                 "address": "n 90 marseille",
            "adresseMail": "zaho@gmail.com",
            "dateNaissance": "2018-03-07",
            "donor_Id": 2,
            "groupeSanguin": "B+",
            "nom": "zaho",
            "numero": "578923021",
            "sex": "femme"
            */
                rentalProperties.add(allp.get(i));

            }
            adapter = new Donor_List.propertyArrayAdapter(this, 0, rentalProperties);

            listview.setAdapter(adapter);
            //Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
            //showDialog(rentalProperties.get(1),1);

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


    class propertyArrayAdapter extends ArrayAdapter<Donor_item> {

        private Context context;
        private List<Donor_item> rentalProperties;

        //constructor, call on creation
        public propertyArrayAdapter(Context context, int resource, ArrayList<Donor_item> objects) {
            super(context, resource, objects);

            this.context = context;
            this.rentalProperties = objects;
        }
        //called when rendering the list
        public View getView(final int position, View convertView, ViewGroup parent) {

            //get the property we are displaying
            final Donor_item donor = rentalProperties.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Donor_List.LAYOUT_INFLATER_SERVICE);

            //conditionally inflate either standard or special template
            View view;
            view = inflater.inflate(R.layout.donor_item, null);

            TextView name = (TextView) view.findViewById(R.id.name);
            TextView dateofbirth = (TextView) view.findViewById(R.id.dateofbirth);
            TextView address = (TextView) view.findViewById(R.id.address);
            TextView sex = (TextView) view.findViewById(R.id.sex);
            name.setText(donor.getNom());
            dateofbirth.setText(donor.getDateNaissance());
            address.setText(donor.getAddress());
            sex.setText(donor.getSex());
            //get the image associated with this property
            //int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
            //image.setImageResource(imageID);

            return view;
        }
    }
}
