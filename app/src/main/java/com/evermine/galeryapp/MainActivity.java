package com.evermine.galeryapp;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public ActivityResultLauncher<Intent> galleryResultLauncher = null;
    ActivityResultLauncher<Intent> cameraResultLauncher = null;
    public static int RC_PHOTO_PICKER = 0;
    public static int REQUEST_IMAGE_CAPTURE = 0;
    public String currentPhotoPath;
    private Uri photoURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting the 2 buttons to set the actionsListener
        Button galleryButton = findViewById(R.id.galleryButton);
        Button cameraButton = findViewById(R.id.cameraB);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryResultLauncher(null);

            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraResultLauncher(null);
            }
        });
        //Camera result launcher
        this.cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageURI(photoURI);
                            System.out.println(photoURI);
                        }
                    }
                });
        //Gallery result launcher
        this.galleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageURI(uri);
                        }
                    }
                });
    }
    /*
    * Method to open gallery result launcher to select a image
    * from gallery app
     */
    public void openGalleryResultLauncher(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Setting image type
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        // Launching app
        galleryResultLauncher.launch(intent);

    }
    /*
    * Method to open a camera result launcher to
    * take a picture to load it to ImageView
     */
    public void openCameraResultLauncher(View view) {

        //Create Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Generating File dir
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = null;
        try {
            //Creating temp file
            photoFile = File.createTempFile("foto",".jpg",storageDir);
        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
        }
        if (photoFile != null) {
            // Getting the photoUri
            photoURI = FileProvider.getUriForFile(this,
                    "com.evermine.galeryapp.fileprovider",
                    photoFile);
            // Addinf the photo URI to the intent
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            //Launching the camera
            cameraResultLauncher.launch(intent);
        }
    }







}