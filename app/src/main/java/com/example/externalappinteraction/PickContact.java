package com.example.externalappinteraction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PickContact extends AppCompatActivity {
    private long contactId;

    ActivityResultLauncher<Intent> pickContactLauncher =
            registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        processPickContractResult(data);
                    }
                });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void pickContactButtonClicked(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        pickContactLauncher.launch(intent);
    }

    private void processPickContractResult(Intent data){
        Uri contactUri = data.getData();
        String[] queryFields = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor cursor = getContentResolver().query(
                contactUri, queryFields, null, null, null);
        try{
            if (cursor.getCount() > 1) {
                cursor.moveToFirst();
                this.contactId = cursor.getInt(0);
                String contactName = cursor.getString(1);


            }
        }
        finally {
            cursor.close();
        }

    }
}
