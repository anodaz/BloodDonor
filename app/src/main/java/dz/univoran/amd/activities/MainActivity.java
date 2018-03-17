package dz.univoran.amd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import dz.univoran.amd.R;

public class MainActivity extends AppCompatActivity {

    private static final int PLACE_PICKER_REQUEST = 1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button fab;
    TextView header_name, header_subtext;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    ArrayList list;


    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences pref;
    boolean bbMode;

    final String requests = "notificationRequests";
    private boolean backPressFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // pref = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
       // if (pref.getBoolean(Constants.DARK_THEME, false))
        //    setTheme(R.style.AppTheme_Dark_Translucent);

       // if(!pref.getBoolean(Constants.ISPROFILEFILLED,false)){
        //    startActivity(new Intent(this, ProfileActivity.class));
       // }
//
        setContentView(R.layout.activity_main);
      //  bbMode = pref.getBoolean(Constants.ISBLOODBANK, false);

     //   if(!pref.getBoolean(Constants.ISFORMFILLED,false)&&!bbMode){
        //    startActivity(new Intent(this, FormActivity.class));
       //     pref.edit().putBoolean(Constants.ISFORMFILLED, true).apply();
       // }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout= (DrawerLayout)findViewById(R.id.drawer_layout) ;
        navigationView = (NavigationView) findViewById(R.id.nvView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.drawer_open,R.string.drawer_close);

        fab = (Button) findViewById(R.id.fab);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setupDrawer();


        header_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_name_view);
        header_subtext = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_subtext_view);
        header_name.setText("Hatab Anodaz");
        header_subtext.setText("anodaz@yahoo.com");

    }
    private void setupDrawer(){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        Intent i = new Intent(MainActivity.this,MainActivity.class);
                        i.putExtra("isfromsignup",false);
                        startActivity(i);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        break;
                    case R.id.compatibility:
                        startActivity(new Intent(MainActivity.this,FormActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            if (backPressFlag)
                finishAffinity();
            else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                backPressFlag = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressFlag = false;
                    }
                }, 2000);
            }
        }
    }
}
