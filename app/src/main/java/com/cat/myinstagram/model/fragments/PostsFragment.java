package com.cat.myinstagram.model.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cat.myinstagram.PostAdaptor;
import com.cat.myinstagram.R;
import com.cat.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    PostAdaptor adaptor;
    RecyclerView rvPosts;
    List<Post> mPosts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = (RecyclerView) view.findViewById(R.id.recyclerView);
        mPosts = new ArrayList<>();
        adaptor = new PostAdaptor(mPosts, getContext());
        rvPosts.setAdapter(adaptor);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    public void queryPosts(){
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                mPosts.addAll(posts);
                adaptor.notifyDataSetChanged();
                for(int i =0; i<posts.size(); i++){
                    Log.d("Post Fragment", "Post[" +i +"]: "
                            + posts.get(i).getDescription()
                            + "\nusername = " + posts.get(i).getUser().getUsername()
                    );
                }
            }
        });
    }
}
