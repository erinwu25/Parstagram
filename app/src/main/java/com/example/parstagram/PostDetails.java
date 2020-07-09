package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostDetails extends AppCompatActivity {
    // post to display
    Post post;
    TextView tvDisplayUsername;
    ImageView ivDisplayImage;
    TextView tvDisplayDescription;
    TextView tvCreatedAt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // resolve view options
        tvDisplayUsername = findViewById(R.id.tvDisplayUsername);
        ivDisplayImage = findViewById(R.id.ivDisplayImage);
        tvDisplayDescription = findViewById(R.id.tvDisplayDescription);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);

        // get post
        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        // fill in details
        tvDisplayUsername.setText(post.getUser().getUsername());
        tvDisplayDescription.setText(post.getDescription());
        tvCreatedAt.setText(getRelativeTimeAgo(post.getKeyCreatedKey().toString()));

        // set image
        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivDisplayImage);

    }

    // manipulate created at
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String igFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(igFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            int flag = DateUtils.FORMAT_ABBREV_RELATIVE;
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, flag).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}