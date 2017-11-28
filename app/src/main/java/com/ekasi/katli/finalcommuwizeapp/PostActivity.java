package com.ekasi.katli.finalcommuwizeapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView imageview;
    private Uri imgUri;
    private Button submit;

    public static final int REQUEST_CODE = 1234;


    private EditText title;
    private StorageReference storageReference;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageview = (ImageView)findViewById(R.id.imageVIew);
        submit = (Button)findViewById(R.id.submit);
        title =(EditText) findViewById(R.id.Title);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getInstance().getReference().child("CommunityApp");

        submit.setOnClickListener(this);


    }
    public void btnBrowse_click(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageview.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {

        final String titleValue = title.getText().toString().trim();


        if (!TextUtils.isEmpty(titleValue)) {
            StorageReference filePath = storageReference.child("PostImage").child(imgUri.getLastPathSegment());
            filePath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                    DatabaseReference newPost = databaseReference.push();
                    newPost.child("title").setValue(titleValue);
                    newPost.child("image").setValue(downloadurl.toString());
                }
            });

            if (v == submit) {

                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }
    }
}