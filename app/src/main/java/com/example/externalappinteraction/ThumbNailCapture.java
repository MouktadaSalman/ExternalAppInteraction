package com.example.externalappinteraction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ThumbNailCapture extends AppCompatActivity {

    ImageView imageView;

    ActivityResultLauncher<Intent> thumbNailLauncher =
            registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    if (image != null) {
                        imageView.setImageBitmap(image);
                    }
                }
            });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thumb_nail_capture);

        Button captureButton = findViewById(R.id.captureImage);
        imageView = findViewById(R.id.imageThumbNile);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                thumbNailLauncher.launch(intent);
            }
        });

    }
}
