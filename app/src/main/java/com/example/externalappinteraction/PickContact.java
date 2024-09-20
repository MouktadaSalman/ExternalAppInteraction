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
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PickContact extends AppCompatActivity {
    int contactId;
    Button pickContact;
    Button more;
    TextView phone;
    TextView email;
    TextView name;

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

        name = findViewById(R.id.nameView);
        email = findViewById(R.id.emailView);
        phone = findViewById(R.id.phoneView);
        more = findViewById(R.id.moreButton);
        pickContact = findViewById(R.id.pickContact);
        name.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        more.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);

    }

    private void pickContactButtonClicked(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        pickContactLauncher.launch(intent);
    }

    private void morButtonClicked(){
        String result = "";
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] queryFields = new String[]{
                ContactsContract.CommonDataKinds.Email.ADDRESS
        };

        String whereClause = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
          String.valueOf(this.contactId)
        };

        Cursor cursor = getContentResolver().query(
                emailUri, queryFields, whereClause, whereValues, null);

        try{
            cursor.moveToFirst();
            do{
                String emailAddress = cursor.getString(0);
                result = result + emailAddress + " ";
            }while (cursor.moveToNext());
        }
        finally {
            cursor.close();
        }

        email.setText(result);
        email.setVisibility(View.VISIBLE);
    }

    private void getPhoneNumber(){
        String result = "";
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] queryFields = new String[]{
          ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String[] whereValue = new String[]{
                String.valueOf(this.contactId)
        };

        Cursor cursor = getContentResolver().query(
                phoneUri, queryFields, whereClause, whereValue, null
        );

        try{
            cursor.moveToFirst();

            do{
                String phoneNumber = cursor.getString(0);
                result = result + phoneNumber + " ";
            }while (cursor.moveToNext());
        }
        finally {
            cursor.close();
        }

        phone.setText(result);
        phone.setVisibility(View.VISIBLE);
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
                name.setVisibility(View.VISIBLE);
                name.setText(contactName);
                more.setVisibility(View.VISIBLE);
            }
        }
        finally {
            cursor.close();
        }
    }
}
