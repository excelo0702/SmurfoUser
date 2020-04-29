package com.example.chhots;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.example.chhots.bottom_navigation_fragments.favorite;
import com.example.chhots.bottom_navigation_fragments.instructor;
import com.example.chhots.bottom_navigation_fragments.trending;
import com.example.chhots.category_view.routine.routine;
import com.example.chhots.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    BottomNavigationView bottomNavigationView;
    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notification, R.id.nav_subscription,
                R.id.nav_category, R.id.nav_setting, R.id.nav_feedback,R.id.nav_about,
                R.id.nav_support)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

/*
        String path = "android.resource://com.example.chhots"+R.raw.vid;
        Uri u = Uri.parse(path);
        videoView.setVideoURI(u);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
*/

        Fragment f=new HomeFragment();
        Fragment f2=new trending();
        Fragment f1=new instructor();
        Fragment f3=new favorite();
        FragmentManager fe=getSupportFragmentManager();
        Fragment active=f;
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,f,"1").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,f1,"2").hide(f1).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,f2,"3").hide(f2).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,f3,"4").hide(f3).commit();

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_dashboard:

                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                Fragment f=new HomeFragment();

                                fragmentTransaction.replace(R.id.nav_host_fragment,f);
                                fragmentTransaction.commit();

                                Toast.makeText(getApplicationContext(), "Dashboard", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_favorites:

                                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                                Fragment f1=new favorite();
                                fragmentTransaction1.replace(R.id.nav_host_fragment,f1);
                                fragmentTransaction1.commit();
                                Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_trending:

                                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                                Fragment f2=new trending();
                                fragmentTransaction2.replace(R.id.nav_host_fragment,f2);
                                fragmentTransaction2.commit();
                                Toast.makeText(getApplicationContext(), "Trending", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.action_instructor:

                                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                                Fragment f3=new instructor();
                                fragmentTransaction3.replace(R.id.nav_host_fragment,f3);
                                fragmentTransaction3.commit();
                                Toast.makeText(getApplicationContext(), "Instructor", Toast.LENGTH_SHORT).show();
                                break;


                            default:
                                FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                                Fragment f5=new HomeFragment();

                                fragmentTransaction5.replace(R.id.nav_host_fragment,f5);
                                fragmentTransaction5.commit();

                                Toast.makeText(getApplicationContext(), "Dashboard", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }

                });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}