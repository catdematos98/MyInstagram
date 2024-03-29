package com.cat.myinstagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cat.myinstagram.model.Post;
import com.cat.myinstagram.model.PostDetailActivity;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;


public class PostAdaptor extends RecyclerView.Adapter<PostAdaptor.ViewHolder> {

    List<Post> posts;
    Context context;

    public PostAdaptor(List<Post> posts, Context context){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageIV;
        public TextView userTV;
        public TextView descriptionTV;
        public TextView likesTV;
        public ImageView likeBT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //check for valid position
                    if (position != RecyclerView.NO_POSITION){
                        Post post = posts.get(position);
                        Intent i = new Intent(context, PostDetailActivity.class);
                        //serialize the movie using parceler, use its short name as a key
                        i.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                        i.putExtra("image", post.getImage());
                        // show the activity
                        context.startActivity(i);
                    }
                }
            });


            likeBT = (ImageView) itemView.findViewById(R.id.ivLikeBT);
            likeBT.setOnClickListener(new View.OnClickListener() {
                int button01pos = 1;
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Post post = posts.get(position);
                        if (button01pos == 0) {
                            likeBT.setImageResource(R.drawable.ufi_heart);
                            post.decrementLikes();
                            button01pos = 1;
                        } else if (button01pos == 1) {
                            likeBT.setImageResource(R.drawable.ufi_heart_active);
                            post.incrementLikes();
                            button01pos = 0;
                        }
                        notifyDataSetChanged();
                    }
                }
            });

            likesTV = (TextView) itemView.findViewById(R.id.tvLikes);
            imageIV = (ImageView) itemView.findViewById(R.id.ivPicture);
            userTV = (TextView) itemView.findViewById(R.id.tvUsername);
            descriptionTV = (TextView) itemView.findViewById(R.id.tvDesciption);
        }

        public void bind(Post post){
            userTV.setText(post.getUser().getUsername());
            descriptionTV.setText(post.getDescription());
            likesTV.setText("Liked by " + post.getLikes() + " people.");
            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(imageIV);
            }
        }
    }


    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
