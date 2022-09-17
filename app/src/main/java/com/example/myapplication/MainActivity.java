package com.example.myapplication;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static android.Manifest.permission.CALL_PHONE;
import static com.example.myapplication.TestService.RAYON;
import static com.example.myapplication.TestService.fr3;
import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity{
    DrawerLayout drawer;

    private AppBarConfiguration mAppBarConfiguration;
    static boolean stopped=false;
    static Intent serv;
    TextView name,balance;
    static MainActivity thisactivity;
    View headerView;
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thisactivity = this;

        serv = new Intent(this, TestService.class);
        startService(serv);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        System.out.println("DOWEL1");
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder().build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        headerView = navigationView.getHeaderView(0);
        System.out.println("DOWEL2");

        if(TestService.id.equalsIgnoreCase("")){
            TestService.fr2=false;
            fragment_switch(new Fragment7());
        }
        else{
            System.out.println(TestService.id);
            name = headerView.findViewById(R.id.pozivnoy);
            name.setText("ID:"+TestService.id);
            balance = headerView.findViewById(R.id.textView);
            balance.setText("\uD83D\uDCB0"+TestService.balance);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            if(!TestService.zakaz_id.equals("")){
                Intent dialogIntent = new Intent(MainActivity.thisactivity, taksometr.class);
                startActivity(dialogIntent);
            }
            TestService.fr2=true;
            TestService.fr3=false;
            System.out.println("fragment2");
            fragment_switch(new Fragment2());
        }

        if(!TestService.isbalans_started) {
            TestService.isbalans_started=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!TestService.APP_STOP) {
                        try {
                            System.out.println("OKOKOK");
                            sleep(300);
                            if(TestService.id.equals("") || is_auto_order){
                                continue;
                            }
                            if(Taxi.send_gps_list){
                                connection.sendData(("send_gps_list4|"+TestService.id+"|"+TestService.zakaz_id + "|" +Taxi.summa+"|"+ Taxi.xz+"|"+TestService.taksometr_ojidaniya_summa+"|"+ RAYON+"||***").getBytes());
                                continue;
                            }
                            if(Taxi.zakaz_is_free){
                                try {
                                    connection.sendData(("zakaz_is_free2|"+Taxi.zakaz_is_free_id+"|"+TestService.id+"|"+ RAYON+"***").getBytes());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Taxi.zakaz_is_free=false;
                                continue;
                            }
                            ggwp();
                            if(TestService.loc2!=null && TestService.zakaz_id.equals("")
                                    && Integer.parseInt(TestService.balance)>0)
                                auto_order();
                            if(TestService.fr3)
                            {
                                connection.sendData((TestService.id + "|zakaz_get7|" + TestService.region + "|" + TestService.avto_class+"|"+ RAYON+"***").getBytes());
                                continue;
                            }

                            if(TestService.fr2)
                                connection.sendData((TestService.id+"|region_get3|"+ RAYON+"***").getBytes());

                            if (TestService.loc2 == null || TestService.loc==null) {
                                connection.sendData(("refresh_balans3|" + TestService.id + "|null|null|"+TestService.imei+"|"+ RAYON+"|***").getBytes());
                            }
                            else
                                connection.sendData(("refresh_balans3|" + TestService.id + "|" + TestService.loc2.getLatitude() + "|" + TestService.loc2.getLongitude() + "|"+TestService.imei+"|"+ RAYON+"|***").getBytes());



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    TestService.isbalans_started=false;
                }
            }).start();
        }

        if(!TestService.get_trev){
            TestService.get_trev=true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!TestService.APP_STOP) {
                        try {
                            if(TestService.id.equalsIgnoreCase("") || !TestService.zakaz_id.equalsIgnoreCase("")){
                                Thread.sleep(60000);
                                continue;
                            }

                            Taxi.get_trevoga_text = "get_trevoga2|" + TestService.id+"|"+RAYON;
                            Taxi.get_trevoga = true;
                            Thread.sleep(60000);
                        }
                        catch (Exception e) {
                            try {
                                Thread.sleep(60000);
                            } catch (InterruptedException ex) {
                                //ex.printStackTrace();
                            }
                            //e.printStackTrace();
                        }
                    }
                }

            }).start();

        }

    }
    boolean refresh(){
        try {
            //sleep(300);
            if(TestService.id.equals("") || is_auto_order){
                return false;
            }
            if(Taxi.send_gps_list){
                connection.sendData(("send_gps_list4|"+TestService.id+"|"+TestService.zakaz_id + "|" +Taxi.summa+"|"+ Taxi.xz+"|"+TestService.taksometr_ojidaniya_summa+"|"+ RAYON+"||***").getBytes());
                return true;
            }
            if(Taxi.zakaz_is_free){
                try {
                    connection.sendData(("zakaz_is_free2|"+Taxi.zakaz_is_free_id+"|"+TestService.id+"|"+ RAYON+"***").getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Taxi.zakaz_is_free=false;
                return true;
            }
            ggwp();
            if(TestService.loc2!=null && TestService.zakaz_id.equals("")
                    && Integer.parseInt(TestService.balance)>0)
                auto_order();
            if(TestService.fr3)
            {
                connection.sendData((TestService.id + "|zakaz_get7|" + TestService.region + "|" + TestService.avto_class+"|"+ RAYON+"***").getBytes());
                return true;
            }

            if(TestService.fr2)
                connection.sendData((TestService.id+"|region_get3|"+ RAYON+"***").getBytes());

            if (TestService.loc2 == null || TestService.loc==null) {
                connection.sendData(("refresh_balans3|" + TestService.id + "|null|null|"+TestService.imei+"|"+ RAYON+"|***").getBytes());
            }
            else
                connection.sendData(("refresh_balans3|" + TestService.id + "|" + TestService.loc2.getLatitude() + "|" + TestService.loc2.getLongitude() + "|"+TestService.imei+"|"+ RAYON+"|***").getBytes());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean is_auto_order=false;
    long ao=0;
    String last_adress="";
    void auto_order(){
        long ao2= System.currentTimeMillis();
if(ao2-30000>ao)
        try {
          connection.sendData(("auto_zakaz3|" +TestService.id  + "|" + TestService.avto_class+"|"+ RAYON+"|"+TestService.loc2.getLatitude()+"|"+TestService.loc2.getLongitude()+"|***").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
else{
    try {
        connection.sendData(("***auto_zakaz4|" + TestService.id  + "|" + TestService.avto_class+"|"+ RAYON+"|"+TestService.loc.getLatitude()+"|"+TestService.loc.getLongitude()+"|***").getBytes());
    } catch (Exception e) {
        e.printStackTrace();
    }


}
    }
    static void trev(String[] split){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.thisactivity).create();
        alertDialog.setTitle("ВНИМАНИЕ ВОДИТЕЛЬ В ОПАСНОСТИ");
        alertDialog.setMessage("Машина "+split[4]+" "+split[5]+" "+split[6]+"\n"+"Номер телефона: "+split[3]);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Позвонить",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:"+split[3]));

                        if (ContextCompat.checkSelfPermission(MainActivity.thisactivity.getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MainActivity.thisactivity.startActivity(i);
                        }
                        trev(split);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Показать на карте",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent dialogIntent = new Intent(MainActivity.thisactivity, MapsActivity.class);
                        dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.thisactivity.startActivity(dialogIntent);
                        trev(split);
                    }
                });
        alertDialog.show();

    }
    void ggwp(){

        if(!Taxi.auto_order_accept.equals("")){
            connection.sendData(Taxi.auto_order_accept.getBytes());
            Taxi.auto_order_accept="";
        }


        if(Taxi.decline_zakaz){
            connection.sendData(("decline_zakaz|"+Taxi.decline_zakaz_id+"|"+TestService.id+"|"+ RAYON+"***").getBytes());
            Taxi.decline_zakaz=false;
        }

        if (Taxi.get_usluga) {
            try {
                connection.sendData((Taxi.get_usluga_text+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.get_usluga=false;
        }
        if(!Taxi.send_na_meste.equals("")){
            try {
                connection.sendData((Taxi.send_na_meste+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.send_na_meste="";
        }
        if(!Taxi.send_zakaz_start.equals("")){
            try {
                connection.sendData((Taxi.send_zakaz_start+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.send_zakaz_start="";
        }
        if(!Taxi.send_set_column.equals("")){
            try {
                connection.sendData((Taxi.send_set_column+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.send_set_column="";
        }
        if(!Taxi.send_set_language.equals("")){
            try {
                connection.sendData((Taxi.send_set_language+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.send_set_language="";
        }

        if(!Taxi.trevoga.equals("")){
            try {
                connection.sendData((Taxi.trevoga+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.trevoga="";
        }

        if(!Taxi.send_set_lotin.equals("")){
            try {
                connection.sendData((Taxi.send_set_lotin+"***").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Taxi.send_set_lotin="";
        }
        if(Taxi.get_trevoga){
            connection.sendData((Taxi.get_trevoga_text+"***").getBytes());
            Taxi.get_trevoga=false;
        }
        if(Taxi.history){
            connection.sendData((Taxi.history_text+"***").getBytes());
            Taxi.history=false;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    void fragment_switch(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }
    void menu_switch(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    public void regions(View view) {

        if(!TestService.fr2){
            TestService.fr2=true;
            TestService.fr3=false;
            TestService.fr4=false;
            menu_switch(new Fragment2());
        }

    }

    public void fragment5(View view)
    {
        TestService.fr2=false;
        menu_switch(new Fragment5());
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

    public void ScanButton(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //ActivityCompat.requestPermissions(MainActivity.thisactivity, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.thisactivity);
                intentIntegrator.initiateScan();
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null){
                    if (intentResult.getContents() != null)
                    {
                        connection.sendData(("accept|get_qr2|"+intentResult.getContents()+"|"+TestService.imei+"***").getBytes());

                        connection.sendData(("accept|iron_check2|" + TestService.imei + "|2.82***").getBytes());
                        Intent intent = new Intent(MainActivity.this, splashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }}).start();
    }


    public void fragment9 (View view)
    {
        TestService.fr2=false;
        menu_switch(new Fragment9());
    }

    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        System.out.println("backed");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(!TestService.id.equals("") && !Fragment3.clicked) {
                if(TestService.fr3){
                    TestService.fr3=false;
                    TestService.fr2=true;
                    fragment_switch(new Fragment2());
                }
                else if(TestService.fr4){

                }
                else {
                    if (drawer.isOpen())
                        drawer.close();
                    else
                        drawer.open();
                }
            }
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void client_tel_b (View view){
        if (!TestService.zakaz_tel.equalsIgnoreCase("")) {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + TestService.zakaz_tel));

            if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(i);
            } else {
                requestPermissions(new String[]{CALL_PHONE}, 1);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void operator_tel_b (View view){
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+TestService.operator));

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }

    public void trevoga (View view){
        if(TestService.loc2!=null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            if(TestService.lang_is_uz) {
                alertDialog.setTitle("Хавф Хатар");
                alertDialog.setMessage("Сиз ростдан хам хавф-хатарни қўшмоқчимисиз?");
            }
            else if(TestService.lang_is_uz_lotin) {
                alertDialog.setTitle("Xavf xatar");
                alertDialog.setMessage("Siz rostdan ham xavf-xatarni qo'shmoqchimisiz?");
            }
            else {
                alertDialog.setTitle("Тревога");
                alertDialog.setMessage("Вы действительно хотите включить тревогу?");

            }
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Taxi.trevoga = "trevoga|" + TestService.id+"|"+ RAYON;
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    public void app_close (View view){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        if(TestService.lang_is_uz){
            alertDialog.setTitle("Ишдан чиқиш.");
            alertDialog.setMessage("Сиз ростдан хам ишдан чиқмоқчимисиз?");
        }
        else{
            alertDialog.setTitle("Выйти из работы.");
            alertDialog.setMessage("Вы действительно хотите выйти?");
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        TestService.APP_STOP = true;
                        stopped = true;
                        stopService(serv);
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();


                    }
                });
        alertDialog.show();

    }
    public void history (View view){
        TestService.fr2=false;
        menu_switch(new Fragment10());
    }
    @Override
    protected void onStop() {
        System.out.println("свернута");

        super.onStop();
        }

    @Override
    protected void onResume() {
        System.out.println("развернута");
        super.onResume();

    }

    public void zakaz(View view) {
        if (!TestService.zakaz_id.equals("")) {
            if (!TestService.zakazdop_id.equals("")) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                String zkz1 = "";
                String zkz2 = "";
                if(TestService.lang_is_uz) {
                    alertDialog.setTitle("Буюртмалар");
                    alertDialog.setMessage("Буюртмани танланг");
                    zkz1="Дастлабки буюртма";
                    zkz2="Иккинчи буюртма";
                }
                else if(TestService.lang_is_uz_lotin) {
                    alertDialog.setTitle("Buyurtmalar");
                    alertDialog.setMessage("Buyurtmani tanlang");
                    zkz1="Dastlabki buyurtma";
                    zkz2="Ikkinchi buyurtma";
                }
                else {
                    alertDialog.setTitle("Заказы");
                    alertDialog.setMessage("Выберите заказ");
                    zkz1="Текущий заказ";
                    zkz2="Второй заказ";

                }


                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, zkz1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent dialogIntent = new Intent(MainActivity.thisactivity, taksometr.class);
                                startActivity(dialogIntent);

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, zkz2,
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (!TestService.zakazdop_tel.equalsIgnoreCase("")) {
                                    Intent i = new Intent(Intent.ACTION_CALL);
                                    i.setData(Uri.parse("tel:" + TestService.zakazdop_tel));

                                    if (ContextCompat.checkSelfPermission(MainActivity.thisactivity.getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                        startActivity(i);
                                    } else {
                                        requestPermissions(new String[]{CALL_PHONE}, 1);
                                        if (ContextCompat.checkSelfPermission(MainActivity.thisactivity.getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                            startActivity(i);
                                        } else {
                                            requestPermissions(new String[]{CALL_PHONE}, 1);
                                        }
                                    }
                                }
                            }
                        });
                alertDialog.show();
            }
            else{
                Intent dialogIntent = new Intent(MainActivity.thisactivity, taksometr.class);
                startActivity(dialogIntent);
            }

        }
        else if (!TestService.zakazdop_id.equals("")) {
            TestService.zakaz_id=TestService.zakazdop_id;
            TestService.zakaz_class=TestService.zakazdop_class;
            TestService.zakaz_orientir=TestService.zakazdop_orientir;
            TestService.zakaz_reg_date=TestService.zakazdop_reg_date;
            TestService.zakaz_region=TestService.zakazdop_region;
            TestService.zakaz_tel=TestService.zakazdop_tel;
            TestService.zakaz_min_summ=TestService.zakazdop_min_summ;
            TestService.zakaz_uchtaxi=TestService.zakazdop_uchtaxi;
            TestService.zakazdop_id="";
            TestService.zakazdop_class="";
            TestService.zakazdop_orientir="";
            TestService.zakazdop_reg_date="";
            TestService.zakazdop_region="";
            TestService.zakazdop_tel="";
            TestService.last_summa=0;
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog1);

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    TestService.usluga_start();
                    TextView textView1 = dialog.findViewById(R.id.dialogtextView);
                    TextView timer_text = (TextView)dialog.findViewById(R.id.editTextTime);
                    Button na_meste = dialog.findViewById(R.id.na_meste);
                    Button textView2 = dialog.findViewById(R.id.klient);

                    timer_text.setText("");

                    if(TestService.lang_is_uz){
                        textView1.setText("Мижозга боряпмиз");
                        na_meste.setText("Жойида");
                        textView2.setText("Мижоз билан уланиш");
                    }
                    else if(TestService.lang_is_uz_lotin){
                        textView1.setText("Mijozga boryapmiz");
                        na_meste.setText("Joyida");
                        textView2.setText("Mijoz bilan ulanish");
                    }
                    if(TestService.theme){
                        ConstraintLayout cos = dialog.findViewById(R.id.dialog);
                        cos.setBackgroundColor(Color.parseColor("#ACBDE8"));
                        na_meste.setBackgroundColor(Color.parseColor("#165E04"));
                        timer_text.setTextColor(Color.parseColor("#050505"));
                        textView1.setTextColor(Color.BLACK);
                        textView2.setBackgroundColor(Color.parseColor("#027867"));
                    }
                    textView2.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            if (!TestService.zakaz_tel.equalsIgnoreCase("")) {
                                Intent i = new Intent(Intent.ACTION_CALL);
                                i.setData(Uri.parse("tel:" + TestService.zakaz_tel));

                                if (ContextCompat.checkSelfPermission(MainActivity.thisactivity.getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                    startActivity(i);
                                } else {
                                    requestPermissions(new String[]{CALL_PHONE}, 1);
                                }
                            }
                        }});
                    na_meste.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //TestService.loc=null;
                            TestService.do_start_ojidaniya = true;
                            Taxi.send_na_meste = "na_meste|"+TestService.zakaz_id+"|"+ RAYON;
                            TestService.usluga_start();
                            MainActivity.thisactivity.runOnUiThread(new Runnable() {
                                public void run() {
                                    Intent dialogIntent = new Intent(MainActivity.thisactivity, taksometr.class);
                                    startActivity(dialogIntent);
                                    TestService.fr2=true;

                                }
                            });
                            dialog.cancel();
                        }});

                }
            }).start();

        }
    }

    public void pred_zakaz_menu(View view)
    {
        TestService.fr2=false;
        menu_switch(new Fragment11());
    }


    public void fragment8(View view) {
        TestService.fr2=false;
        menu_switch(new Fragment8());
    }

    public void settings_notification(View view) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", this.getPackageName());
            intent.putExtra("app_uid", this.getApplicationInfo().uid);
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
        }
        this.startActivity(intent);
    }


}