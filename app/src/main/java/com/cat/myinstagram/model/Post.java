package com.cat.myinstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_USER = "user";
    private static final String KEY_LIKES = "likes";
    private static final String KEY_CREATIONTIME = "createdAt";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public int getLikes(){
        return getInt(KEY_LIKES);
    }
    public void incrementLikes(){
        put(KEY_LIKES, getLikes()+1);
    }
    public void decrementLikes(){
        put(KEY_LIKES, getLikes()-1);
    }

    public String getTimeStamp(){
        return getString(KEY_CREATIONTIME);
    }

}
