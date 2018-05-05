package dz.univoran.amd.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView header_name, header_subtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        header_name = (TextView) headerView.findViewById(R.id.header_name_view);
        header_subtext = (TextView) headerView.findViewById(R.id.header_subtext_view);
        header_name.setText(Constants.USERNAME);
        header_subtext.setText(Constants.USERNAME+"@anodaz.com");
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void btimport(View view){
        Intent i = new Intent(MainActivity.this, SelectBloodGroup.class);
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
    public void becomedonor(View view){
        Intent i = new Intent(MainActivity.this, Become_Donor.class);
        //startActivity(i);

        startActivity(i);
        this.finish();
        /*    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="90dp"
        android:layout_height="90dp"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:srcCompat="@android:drawable/ic_dialog_email" />
*/
    }
    public void getprofile(View view){
        Intent i = new Intent(MainActivity.this, ProfileActivity.class);
        //startActivity(i);
        /*Bundle b=new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        //b.putString("name",obj.getString("name"));
        b.putString("id",user_id);
        i.putExtras(b);*/
        startActivity(i);
        this.finish();
        /*    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="90dp"
        android:layout_height="90dp"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:srcCompat="@android:drawable/ic_dialog_email" />
*/
    }
    public void bloodbank(View view) {
        Intent i = new Intent(this, BloodBankActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.home) {

        } else if (id == R.id.home) {

        } else if (id == R.id.home) {

        } else if (id == R.id.home) {

        } else if (id == R.id.logout) {
            SharedPreferences sharedPreferences;
            SharedPreferences.Editor editor;
            sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putBoolean(Constants.KEY_LOGIN, false);
            editor.apply();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
