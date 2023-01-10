package com.evermine.galeryapp;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public ActivityResultLauncher<Intent> someActivityResultLauncher = null;
    ActivityResultLauncher<Intent> someActivityResultLauncher2 = null;
    public static int RC_PHOTO_PICKER = 0;
    public static int REQUEST_IMAGE_CAPTURE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button galleryButton = findViewById(R.id.galleryButton);
        Button cameraButton = findViewById(R.id.galleryButton);
        this.someActivityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageBitmap(imageBitmap);

                        }
                    }
                });

        this.someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            ImageView imagen = findViewById(R.id.img);
                            imagen.setImageBitmap(imageBitmap);
                        }
                    }
                });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openSomeActivityForResult(null);
                openSomeActivityForResult2(null);
            }
        });
    }
    public void openSomeActivityForResult(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        someActivityResultLauncher.launch(intent);
    }

    public void openSomeActivityForResult2(View view) {

        //Create Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Launch activity to get result
        someActivityResultLauncher2.launch(intent);
    }





}