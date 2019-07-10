package com.cat.myinstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cat.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    private String imagePath = "PATH GOES HERE";
    private final String TAG = "NewPostActivity";

    private EditText descriptionET;
    private Button saveBT;
    private Button refreshBT;
    private ImageView postImageIV;
    private Button takePictureBT;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_home);

        descriptionET = (EditText) findViewById(R.id.etDescription);
        saveBT = (Button) findViewById(R.id.btSave);
        refreshBT = (Button) findViewById(R.id.btRefresh);
        postImageIV = (ImageView) findViewById(R.id.ivPicture);
        takePictureBT = (Button) findViewById(R.id.btTakePicture);
        
        //queryPosts();

        takePictureBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera(v);
            }
        });

        refreshBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPosts();
            }
        });

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionET.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                if (photoFile == null || postImageIV.getDrawable() == null) {
                    Log.e(TAG, "No photo file");
                    Toast.makeText(NewPostActivity.this, "No photo taken!", Toast.LENGTH_LONG).show();

                } else {
                    createPost(description, photoFile, user);
                }
            }
        });

        //register with parse object - "hey, model is a custom model"
        ParseObject.registerSubclass(Post.class);
    }

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(NewPostActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                postImageIV.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                for(int i =0; i<posts.size(); i++){
                    Log.d(TAG, "Post[" +i +"]: "
                            + posts.get(i).getDescription()
                            + "\nusername = " + posts.get(i).getUser().getUsername()
                    );
                }
            }
        });
    }

    private void createPost(String description, File imageFile, ParseUser user){
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setUser(user);
        newPost.setImage(new ParseFile(photoFile));

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e== null){
                    Log.d("HomeActivity", "Create Post Success");
                }
                else{
                    e.printStackTrace();
                }
                descriptionET.setText("");
                postImageIV.setImageResource(0);
                finish();
                //Intent i = new Intent(NewPostActivity.this, HomeTimeline.class);
                //startActivity(i);
            }
        });
    }
}
