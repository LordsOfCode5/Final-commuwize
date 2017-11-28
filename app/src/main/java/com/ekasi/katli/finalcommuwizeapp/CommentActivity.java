package com.ekasi.katli.finalcommuwizeapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText add_comment;
    private ImageButton post_comment;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);

        add_comment = (EditText) findViewById(R.id.addComment);
        post_comment = (ImageButton) findViewById(R.id.postComment);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Comments");
    }

    @Override
    public void onClick(View v) {

        final String commentValue = add_comment.toString().trim();

        if (!TextUtils.isEmpty(commentValue)){
            DatabaseReference newPost = mDatabase.push();
            newPost.child("title").setValue(commentValue);
            Toast.makeText(CommentActivity.this, "Comment added!", Toast.LENGTH_LONG).show();
    }

            if (v == post_comment) {

        Intent intent = new Intent(CommentActivity.this, MainActivity.class);
        startActivity(intent);


        }
    }
}