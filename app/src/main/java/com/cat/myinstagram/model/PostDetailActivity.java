package com.cat.myinstagram.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cat.myinstagram.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetailActivity extends AppCompatActivity {

    Post post;
    TextView userTV;
    TextView descriptionTV;
    ImageView imageIV;
    TextView likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        ParseFile image = getIntent().getParcelableExtra("image");

        userTV = (TextView) findViewById(R.id.tvUsername);
        descriptionTV = (TextView) findViewById(R.id.tvDesciption);
        imageIV = (ImageView) findViewById(R.id.ivPicture);
        likes = (TextView) findViewById(R.id.tvLikes);

        likes.setText("Liked by " + post.getLikes() + " people.");
        userTV.setText(post.getUser().getUsername());
        descriptionTV.setText(post.getDescription());
        Glide.with(getApplicationContext())
                .load(image.getUrl())
                .into(imageIV);
    }
}
