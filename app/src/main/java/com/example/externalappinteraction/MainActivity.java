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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void viewButtonClick(){
        String url = "http://curtin.edu.au";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setData(uri);

        PackageManager pm = getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);

        if (resolveInfo != null) {
            Log.d("Name", resolveInfo.activityInfo.packageName);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No suitable package found", Toast.LENGTH_SHORT).show();
        }
    }
}