package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import static android.Manifest.permission.CALL_PHONE;

public class PerActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> someActivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        check_all();
                    }
                });
check_all();

    }
void check_all() {
        boolean all=true;
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
        all = false;
        findViewById(R.id.send_sms_per).setVisibility(View.VISIBLE);
}
    else
        findViewById(R.id.send_sms_per).setVisibility(View.GONE);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
        all=false;
        findViewById(R.id.ACCESS_BACKGROUND_LOCATION).setVisibility(View.VISIBLE);
    }
    else
        findViewById(R.id.ACCESS_FINE_LOCATION).setVisibility(View.GONE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            all=false;
            findViewById(R.id.ACCESS_BACKGROUND_LOCATION).setVisibility(View.VISIBLE);
        }
        else
            findViewById(R.id.ACCESS_BACKGROUND_LOCATION).setVisibility(View.GONE);

    }
     if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
     {
         all=false;
         findViewById(R.id.CALL_PHONE).setVisibility(View.VISIBLE);
     }
    else
        findViewById(R.id.CALL_PHONE).setVisibility(View.GONE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if(!Settings.canDrawOverlays(this)){
            all=false;
            findViewById(R.id.POVERX).setVisibility(View.VISIBLE);
        }
        else
            findViewById(R.id.POVERX).setVisibility(View.GONE);
    }
    if(all){
        Intent intent = new Intent(PerActivity.this, splashActivity.class);
        startActivity(intent);
        finish();
    }

}
    public void send_sms(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

    }

    public void ACCESS_FINE_LOCATION(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    public void ACCESS_BACKGROUND_LOCATION(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 3);
    }

    public void CALL_PHONE(View view) {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, 4);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        check_all();

    }

    public void POVERX(View view) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        //startActivityForResult(intent, 5);
        someActivityResultLauncher.launch(intent);

    }
}