package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;


public class splashActivity extends AppCompatActivity {

    public static splashActivity th;
    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TestService.APP_STOP = false;
        String IMEINumber = getDeviceId(splashActivity.this);
        TestService.imei=IMEINumber;

        AlertDialog alertDialog;

        if(!Taxi.is_connected){
            server.connect(true);
            Taxi.get();
            Taxi.is_connected=true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




        String s = getApplicationInfo().dataDir;
        if((!s.equalsIgnoreCase("/data/data/uz.aladdin.taxi") && s.startsWith("/data/data/")) ||
                (!s.equalsIgnoreCase("/data/user/0/uz.aladdin.taxi") && s.startsWith("/data/user/")) ){
            TestService.APP_STOP = true;
            alertDialog = new AlertDialog.Builder(splashActivity.this).create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle("Диққат!!!");

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ОК",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
        }

        th=this;
        if (TestService.id.equals("") && (!TestService.APP_STOP)){
            Thread my_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.sendData(("accept|iron_check2|" + TestService.imei + "|2.83").getBytes());
                        System.out.println("IRONCHECK2");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            my_thread.start();
            try {
                my_thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else{
            Intent intent = new Intent(splashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }



    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {

        String deviceId;

        deviceId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();

    }
}