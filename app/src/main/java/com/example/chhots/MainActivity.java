package com.example.chhots;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.example.chhots.bottom_navigation_fragments.Favorite.favorite;
import com.example.chhots.bottom_navigation_fragments.Explore.explore;
import com.example.chhots.bottom_navigation_fragments.trending;
import com.example.chhots.ui.About_Deprrita.about;
import com.example.chhots.ui.Category.category;
import com.example.chhots.ui.Feedback.feedback;
import com.example.chhots.ui.Setting.setting;
import com.example.chhots.ui.Subscription.subscription;
import com.example.chhots.ui.SupportUs.support;
import com.example.chhots.ui.dashboard;
import com.example.chhots.ui.home.HomeFragment;
import com.example.chhots.User_Profile.userprofile;
import com.example.chhots.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private AppBarConfiguration mAppBarConfiguration;
    BottomNavigationView bottomNavigationView;
    VideoView videoView;
    TextView login;
    FirebaseAuth auth;
    ImageView user_profile_header;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button chatBtn;
    ActionBarDrawerToggle t;
    private static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().add(R.id.drawer_layout,new HomeFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer);
        t = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.Open,R.string.Close);
        t.setDrawerIndicatorEnabled(true);

        drawer.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        setFragment(new dashboard());
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_notification:
                        setFragment(new NotificationsFragment());
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_subscription:
                        setFragment(new subscription());
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_category:
                        setFragment(new category());
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_setting:
                        setFragment(new setting());
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_feedback:
                        setFragment(new feedback());
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_about:
                        setFragment(new about());
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_support:
                        setFragment(new support());
                        drawer.closeDrawers();
                        break;
                }
                return true;
            }
        });

        View headerview = navigationView.getHeaderView(0);
        login = (TextView) headerview.findViewById(R.id.login_textview);
        user_profile_header = headerview.findViewById(R.id.imageView_header);
        SpannableString content = new SpannableString("Content");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("");
            login.setText(user.getEmail());
            login.setPaintFlags(login.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

            Query query = databaseReference.child("Users").orderByChild("email").equalTo(user.getEmail().toString().trim());

            if(user.getEmail().equals("tanish@gmail.com"))
            {
                Log.d("1111",user.getEmail().toLowerCase().trim());

            }

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.d("999",postSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Toast.makeText(getApplicationContext(),dataSnapshot.getChildrenCount()+" hh ",Toast.LENGTH_SHORT).show();
                    Log.d("12345678",dataSnapshot.getChildrenCount()+"");

                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String image = ""+ds.child("image").getValue();
                        Log.d("123456",image);
                        try{
                            if(image.equals(""))
                            {
                                Picasso.get().load(R.drawable.ic_username2).into(user_profile_header);
                            }
                            else {
                                Picasso.get().load(image).into(user_profile_header);
                            }
                        }
                        catch (Exception e){

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,new userprofile(),"1");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    drawer.closeDrawers();

                }
            });

        }
        else
        {
            login.setText("Login|Signup");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  FragmentTransaction fe=getSupportActionBar();
                    // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fe,).addToBackStack(null).commit();
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                }
            });
        }

    /*    mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notification, R.id.nav_subscription,
                R.id.nav_category, R.id.nav_setting, R.id.nav_feedback,R.id.nav_about,
                R.id.nav_support)
                .setDrawerLayout(drawer)
                .build();

*/



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


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_dashboard:
                                setFragment(new HomeFragment());

                                Toast.makeText(getApplicationContext(), "Dashboard", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_favorites:
                                setFragment(new favorite());
                                Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_trending:
                                setFragment(new trending());
                                Toast.makeText(getApplicationContext(), "Trending", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.action_instructor:
                                setFragment(new explore());
                                Toast.makeText(getApplicationContext(), "Instructor", Toast.LENGTH_SHORT).show();
                                break;


                            default:
                                setFragment(new HomeFragment());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,fragment);
    //    fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    @Override
    public void onPaymentSuccess(String s) {
        try {
            Toast.makeText(getApplicationContext(), "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }    }

    @Override
    public void onPaymentError(int i, String s) {
        //   Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
        try {
            Toast.makeText(getApplicationContext(), "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }    }



}