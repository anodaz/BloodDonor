package dz.univoran.amd.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import dz.univoran.amd.Constants;
import dz.univoran.amd.R;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button fab;
    TextView header_name, header_subtext;

   /* RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    ArrayList list;


    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences pref;
    boolean bbMode;*/

    final String requests = "notificationRequests";
    private boolean backPressFlag;
    ViewPager slidShow;
    String user_id,username,password;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id= Constants.ID;
        username=Constants.USERNAME;
        password=Constants.PASSWORD;

       // pref = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
       // if (pref.getBoolean(Constants.DARK_THEME, false))
        //    setTheme(R.style.AppTheme_Dark_Translucent);

       // if(!pref.getBoolean(Constants.ISPROFILEFILLED,false)){
        //    startActivity(new Intent(this, ProfileActivity.class));
       // }
//
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.btimport);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SelectBloodGroup.class);

                startActivity(i);
                // MainActivity.this.finish();
            }
        });
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

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        slidShow=(ViewPager)findViewById(R.id.slidShow);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new Mytesk(),2000,4000);
        AdaptPage adaptPage=new AdaptPage(this);
        slidShow.setAdapter(adaptPage);
        setupDrawer();


        header_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_name_view);
        header_subtext = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_subtext_view);
        header_name.setText("Ikram Belabid");
        header_subtext.setText("Ikram@yahoo.com");

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
        Bundle b=new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        //b.putString("name",obj.getString("name"));
        b.putString("id",user_id);
        i.putExtras(b);
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
                    case R.id.logout:
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
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

    public void Becom(View view) {
        Intent i = new Intent(this, Become_Donor.class);
        startActivity(i);
    }

    public class AdaptPage extends PagerAdapter{
        private Context context;
        private LayoutInflater layoutInflater;
        private Integer [] images={R.drawable.first_pic,R.drawable.first_pic1};

        public AdaptPage(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.slidshow,null);
            ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
            imageView.setImageResource(images[position]);

            ViewPager vp=(ViewPager) container;
            vp.addView(view,0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewPager vp=(ViewPager) container;
            View view=(View) object;
            vp.removeView(view);
        }
    }
    public class Mytesk extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (slidShow.getCurrentItem()==1){
                        slidShow.setCurrentItem(0);
                    }else {
                        slidShow.setCurrentItem(1);
                    }
                }
            });
        }
    }
}
