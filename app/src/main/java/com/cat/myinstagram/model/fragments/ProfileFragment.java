package com.cat.myinstagram.model.fragments;

import android.util.Log;

import com.cat.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {
    @Override
    public void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder("createdAt");
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                mPosts.addAll(posts);
                adaptor.notifyDataSetChanged();
                for(int i =0; i<posts.size(); i++){
                    Log.d("Post Fragment", "Post[" +i +"]: "
                            + posts.get(i).getDescription()
                            + "\nusername = " + posts.get(i).getUser().getUsername()
                            + "\nLikes: " + posts.get(i).getLikes()
                    );
                }
            }
        });
    }

}
