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

public class CreateAccountActivity extends AppCompatActivity {


    private EditText name;
    private EditText surname;
    private EditText phone;
    private EditText desc;
    private ImageView imageview;
    private Uri imgUri;
    private Button submit;

    public static final int REQUEST_CODE = 1234;

    private StorageReference storageReference;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        name = (EditText) findViewById(R.id.name_field);
        surname = (EditText) findViewById(R.id.surname_field);
        phone = (EditText) findViewById(R.id.phone_field);
        desc = (EditText) findViewById(R.id.desc_field);
        imageview = (ImageView) findViewById(R.id.profile_image);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getInstance().getReference().child("UserProfileList");


    }

    public void btnBrowse_click(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageview.setImageBitmap(bm);
                imageview.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void submitButtonClicked(View view) {

        final String nameValue = name.getText().toString().trim();
        final String surnameValue = surname.getText().toString().trim();
        final String phoneValue = phone.getText().toString().trim();
        final String descValue = desc.getText().toString().trim();


        if (!TextUtils.isEmpty(nameValue)) {
            StorageReference filePath = storageReference.child("PostImage").child(imgUri.getLastPathSegment());
            filePath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(CreateAccountActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                    DatabaseReference newPost = databaseReference.push();

                    newPost.child("title").setValue(nameValue);
                    newPost.child("title").setValue(surnameValue);
                    newPost.child("title").setValue(phoneValue);
                    newPost.child("title").setValue(descValue);
                    newPost.child("image").setValue(downloadurl.toString());

                }
                //The code does not post to the database, Java is getting confused because of we set two onClicks on on button"submit"
            });
        }
    }
}
