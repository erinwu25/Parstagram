package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.parstagram.fragments.ComposeFragment;
import com.example.parstagram.fragments.PostsFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    // to manage fragments
    final FragmentManager fragmentManager = getSupportFragmentManager();

    // only bottom navigation left in actual activity; everything else relegated to fragments
    BottomNavigationView bottomNavigationView;

    // for logout
//    Button btnLogout;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.NewPost:
                // new post option has been chosen
                Intent toNewPost = new Intent(this, MainActivity.class);
                startActivity(toNewPost);
                break;
            case R.id.LogOut:
                logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // log out
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();  // will be null

        // go back to login page
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ffffff"));
        // set action bar color
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        // set instagram logo in action bar?
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.font_instagram);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // set icon in action bar left
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.camera);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bottomNavigationView = findViewById(R.id.bottomNavigation);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch(menuItem.getItemId()) {
                    case R.id.actionCompose:
                        // go to compose activity (main activity)
                        fragment = new ComposeFragment();
                        break;
                    case R.id.actionProfile:
                        // go to profile
                        fragment = new ProfileFragment();
                        break;
                    case R.id.actionHome:
                    default:
                        // go to stream
                        fragment = new PostsFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.actionHome);

    }






}


