package com.example.externalappinteraction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    private void callButtonClicked() {
        int phone = 1239870;
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(uri);
        startActivity(intent);
    }

    private void viewMapButtonClicked() {
        double latitude = 32.0058;
        double longtitude = 115.8974;
        Uri uri = Uri.parse("geo:" + latitude + "," + longtitude);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private void sendTextButtonClicked() {
        int phone = 1239870;
        String smsText = "Assalam Alaikum";
        Uri uri = Uri.parse(String.format("smsto:" + phone));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra("sms_body", smsText);
        startActivity(intent);

    }

    private void sendEmailButtonClicked() {
        String[] mailto = {"secretEmail@gmail.com"};
        String subject = "Test Email";
        String mailbody = "We are testing our email, don't tell anyone";
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_SEND);
        intent.setType("message/rfc882");
        intent.putExtra(Intent.EXTRA_EMAIL, mailto);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, mailbody);
        startActivity(intent);
    }


    private void viewWebButtonClicked() {
        String url = "http://curtin.edu.au";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);

        PackageManager pm = getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        if(resolveInfo!=null) {
            Log.d("Name", resolveInfo.activityInfo.packageName);
            startActivity(intent);
        }else {
            Toast.makeText(this, "No app found", Toast.LENGTH_SHORT).show();
        }
    }
}