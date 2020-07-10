package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostStream extends AppCompatActivity {
    private static final String TAG = "PostStream";
    RecyclerView rvPostStream;
    ArrayList<Post> posts;
    PostAdapter adapter;
    SwipeRefreshLayout swipeContainer;

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
        setContentView(R.layout.activity_post_stream);

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

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // on swipe
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data");
                queryPosts();
            }
        });

        rvPostStream = findViewById(R.id.rvPostStream);

        // dividers between recycler view items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPostStream.getContext(), LinearLayoutManager.VERTICAL);
        rvPostStream.addItemDecoration(dividerItemDecoration);

        // initialize a list of tweets and adapter
        posts = new ArrayList<>();
        adapter = new PostAdapter(this, posts);

        // configure the recycler view: layout manager and adapter
        rvPostStream.setAdapter(adapter);
        rvPostStream.setLayoutManager(new LinearLayoutManager(this));

        // query posts
        queryPosts();
    }

    // query posts to populate stream
    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postObjects, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                adapter.clear();
                // for debugging purposes let's print every post description to logcat
//                for (Post post : postObjects) {
//                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
//                }
                // save received posts to list and notify adapter of new data
                posts.addAll(postObjects);
                swipeContainer.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

    }

}