package com.example.myapplication;
import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.CALL_PHONE;
import org.apache.commons.io.FileUtils;

public class TestService extends Service implements LocationListener {

    public static String zakaz_name;
    public static String sms_na_meste_text="Sizga [car] yetib keldi.";
    public static String sms_finish_text="Sizning yo'l kirangiz [sum] so'm.";
    public static String zakaz_cost,zakaz_kuda;

    public static boolean isbalans_started=false;
    public static String zakaz_kogda;
    public static boolean internet=true;
    public static String imei="";
    public static boolean alertik=false;
    public static String tel="";
    public static boolean fr3=false,fr2=false,fr4=false;
    public static String zakazdop_note;
    public static String zakaz_note;
    public static double dop2_summa=0;
    protected LocationManager locationManager;
    public static Location loc,loc2;
    public static int dop_summa=0;
    public static boolean APP_STOP=false;
    public static String operator="";
    public static boolean taksometr_bagaj=false,taksometr_dostavka=false;
    public static double taksometr_summa,taksometr_ojidaniya_summa,taksometr_km=0.0;
    public static int taksometr_ojidaniya=0,taksometr_vputi=0,taksometr_ojidaniya_do_starta=0;
    public static String taksometr_status="Старт";
    public static String RAYON="";
    public static boolean theme=false;

    public static String zakaz_id="",zakaz_class="",zakaz_orientir="",zakaz_reg_date,zakaz_region="",zakaz_tel="";
    public static String zakazdop_id="",zakazdop_class="",zakazdop_orientir="",zakazdop_reg_date,zakazdop_region="",zakazdop_tel="";

    public static String id="",balance="0",name="Anonymous",avto="no avto",date="",region;
    public static String avto_class="Эконом";
    public static int _min=0;
    public static boolean started = false, stopped = false, paused = false,ojidaniya=false,do_start_ojidaniya=false;
    public static ArrayList<Location> gps_list = new ArrayList<Location>();

    public static boolean usluga_started=false;

    public static int zakaz_min_summ = 5000;
    public static int zakazdop_min_summ = 5000;

    public static int zakaz_first_km_2 = 2500;
    public static int zakaz_last_km = 1200;
    public static int zakaz_out_km = 1500;
    public static int zakaz_free_wait = 180;
    public static double zakaz_wait_summ=5.8;

    public static boolean zakaz_uchtaxi=false;
    public static boolean zakazdop_uchtaxi=false;

    public static String company="Aladdin";
    public static String tarif = "750";
    public static boolean get_trev=false;
    public static double trevoga_lat;
    public static double trevoga_lng;
    public static double last_summa = 0;
    public static double last_km=0,last_out_km=0;
    public static double last_ojidaniya_summa=0;
    public static boolean in_rayon=true;
    public static int grid_column=3;
    public static int bagaj_summa = 2000;
    public static int dostavka_summa = 2000;
    public static boolean is_musr=false;
    public static String musr="";
    public static boolean lang_is_uz=false;
    public static boolean lang_is_uz_lotin=false;
    public static boolean gps_started=false;

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ActivityCompat.requestPermissions(MainActivity.thisactivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);

        if (ActivityCompat.checkSelfPermission(MainActivity.thisactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.thisactivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.thisactivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println(location);
        if(Fragment2.gps!=null)
        Fragment2.gps.post(new Runnable() {
            public void run() {
                Fragment2.gps.setText("\uD83D\uDFE2");
            }});
        if(loc2!=null && loc!=null) {
            if (!(location.getLatitude() == loc2.getLatitude() && location.getLongitude() == loc2.getLongitude())) {
                loc2 = location;
            }
        }
        else {
            loc = location;
            loc2 = location;
        }
    }
    @Override
    public void onProviderDisabled(String provider) {
        loc=null;
        loc2=null;
        if(Fragment2.gps!=null)
        Fragment2.gps.post(new Runnable() {
            public void run() {
                Fragment2.gps.setText("\uD83D\uDD34");
            }});

    }
    @Override
    public void onProviderEnabled(String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public static void usluga_start(){
        if(!usluga_started) {
            usluga_started = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (!TestService.zakaz_id.equalsIgnoreCase("")) {
                        try {
                            if(loc!=null){
                                if (!coordinate_is_inside_polygon(loc.getLatitude(),loc.getLongitude(),lat_array,long_array)) {
                                    if (in_rayon) {
                                        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.thisactivity, R.raw.za_gorod);
                                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
                                                mPlayer.stop();
                                                try {
                                                    mPlayer.prepare();
                                                    mPlayer.seekTo(0);
                                                } catch (Throwable t) {
                                                }
                                            }
                                        });
                                        mPlayer.start();
                                    }
                                    in_rayon = false;


                                } else
                                    in_rayon = true;
                            }


                            String[] tt = String.valueOf(taksometr_km).split("\\.");

                            int ind = 1;
                            if (tt[1].length() > 3)
                                ind = 3;

                            double summ = taksometr_summa;
                            if (taksometr_summa - last_ojidaniya_summa < zakaz_min_summ)
                                summ = zakaz_min_summ + last_ojidaniya_summa;
                            if (taksometr_dostavka)
                                summ += dostavka_summa;
                            if (taksometr_bagaj)
                                summ += bagaj_summa;

                            if(loc==null)
                                Taxi.get_usluga_text = "get_usluga4|" + zakaz_id + ("|" + ((summ + dop_summa+dop2_summa) + "").split("\\.")[0]) + "|" + taksometr_ojidaniya + "|" + taksometr_vputi + "|" + tt[0] + "." + tt[1].substring(0, ind) + ("|" + last_ojidaniya_summa).split("\\.")[0] + "|" + RAYON + "|null|null|" + id+"|"+last_out_km;
                            else
                                Taxi.get_usluga_text="get_usluga4|" + zakaz_id + ("|" + ((summ + dop_summa+dop2_summa) + "").split("\\.")[0]) + "|" + taksometr_ojidaniya + "|" + taksometr_vputi + "|" + tt[0] + "." + tt[1].substring(0, ind) + ("|" + last_ojidaniya_summa).split("\\.")[0] + "|" + RAYON + "|"+loc.getLatitude()+"|"+loc.getLongitude()+"|" + id+"|"+last_out_km;
                            Taxi.get_usluga=true;
                            if(Taxi.get_usluga){
                                Thread.sleep(30);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    usluga_started=false;
                    System.out.println("GET USLUGA STOPPED2");
                }
            }).start();

        }
    }

    public static void start() {
        if(taksometr_status.equalsIgnoreCase("Старт")) {
            if(!started) {
                loc = null;
                loc2=null;
                started=true;
                do_start_ojidaniya=false;
                Taxi.send_zakaz_start = "zakaz_start|"+zakaz_id+"|"+RAYON;

                if(TestService.lang_is_uz || TestService.lang_is_uz_lotin) {

                    MediaPlayer mPlayer = MediaPlayer.create(MainActivity.thisactivity, R.raw.ogg);
                    if(TestService.zakaz_uchtaxi){
                        mPlayer = MediaPlayer.create(MainActivity.thisactivity, R.raw.uchtaxi);
                    }
                    MediaPlayer finalMPlayer = mPlayer;
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            finalMPlayer.stop();
                            try {
                                finalMPlayer.prepare();
                                finalMPlayer.seekTo(0);
                            } catch (Throwable t) {
                            }
                        }
                    });
                    AudioManager mAudioManager = (AudioManager)MainActivity.thisactivity.getSystemService(Context.AUDIO_SERVICE);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
                    mPlayer.start();
                }
                else{

                    t1=new TextToSpeech(MainActivity.thisactivity, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if(status != TextToSpeech.ERROR) {
                                t1.setLanguage(new Locale("ru_RU"));
                                String toSpeak = "Здравствуйте. спасибо что воспользовались нашей службой.";
                                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    });

                }

            }
            taksometr_status= "Пауза";
            paused=false;
        }
        else if(taksometr_status.equalsIgnoreCase("Пауза")) {
            AlertDialog alertDialog = new AlertDialog.Builder(taksometr.t).create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle("Пауза килинсинми");
            alertDialog.setMessage("");

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ха",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            taksometr_status =  "Продолжить";
                            paused=true;
                            Button taksometr_start = (Button) taksometr.t.findViewById(R.id.taksometr_start);
                            taksometr_start.setText(TestService.taksometr_status);
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Йоқ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
        else if(taksometr_status.equalsIgnoreCase("Продолжить")) {
            taksometr_status = "Пауза";
            paused=false;
        }
    }


    public static double Angle2D(double y1, double x1, double y2, double x2)
    {
        double dtheta,theta1,theta2;

        theta1 = Math.atan2(y1,x1);
        theta2 = Math.atan2(y2,x2);
        dtheta = theta2 - theta1;
        while (dtheta > Math.PI)
            dtheta -= 2*Math.PI;
        while (dtheta < -Math.PI)
            dtheta += 2*Math.PI;

        return(dtheta);
    }


    static ArrayList<Double> lat_array = new ArrayList<Double>();
    static ArrayList<Double> long_array = new ArrayList<Double>();
    public static boolean coordinate_is_inside_polygon(
            double latitude, double longitude,
            ArrayList<Double> lat_array, ArrayList<Double> long_array)
    {
        int i;
        double angle=0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i=0;i<n;i++) {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i+1)%n) - latitude;
            point2_long = long_array.get((i+1)%n) - longitude;
            angle += Angle2D(point1_lat,point1_long,point2_lat,point2_long);
        }

        if (Math.abs(angle) < Math.PI)
            return false;
        else
            return true;
    }



    static TextToSpeech t1;
    public static void stop() {
        if(!stopped) {
            stopped=true;

            AudioManager mAudioManager = (AudioManager)MainActivity.thisactivity.getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
            final ProgressDialog[] progressDialog = {null};
            taksometr.t.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog[0] = new ProgressDialog(taksometr.t);
                    progressDialog[0].setTitle("Завершение заказа");
                    progressDialog[0].setCancelable(false);
                    progressDialog[0].setCanceledOnTouchOutside(false);
                    progressDialog[0].setMessage("Пожалуйста, подождите.");
                    progressDialog[0].show();
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < gps_list.size(); i += 1) {
                            message.append(gps_list.get(i).getLatitude()).append(";").append(gps_list.get(i).getLongitude()).append("\n");
                        }
                        String[] tt = String.valueOf(taksometr_km).split("\\.");
                        int ind=1;
                        if(tt[1].length()>3)
                            ind = 3;

                        double summ=taksometr_summa;
                        if (taksometr_summa-last_ojidaniya_summa < zakaz_min_summ && !zakaz_uchtaxi)
                            summ = zakaz_min_summ+last_ojidaniya_summa;
                        if(taksometr_dostavka)
                            summ+=dostavka_summa;
                        if(taksometr_bagaj)
                            summ+=bagaj_summa;
                        String s_summa = ((summ+dop_summa+dop2_summa)+"").split("\\.")[0];
                        if(is_musr){
                            s_summa=musr;
                        }
                        String summa = s_summa;
                        usluga_started=false;

                        Taxi.xz = tt[0]+"."+tt[1].substring(0,ind);
                        Taxi.summa=summa;
                        Taxi.message=message.toString();
                        Taxi.send_gps_list=true;
                        long time1= System.currentTimeMillis();
                        while (Taxi.send_gps_list){
                            Thread.sleep(500);
                        }




                        taksometr.t.runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog[0].cancel();
                                AlertDialog alertDialog = new AlertDialog.Builder(taksometr.t).create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.setCancelable(false);
                                if (TestService.lang_is_uz)
                                    alertDialog.setTitle("Буюртманинг тугаши");
                                else if (TestService.lang_is_uz_lotin)
                                    alertDialog.setTitle("Buyurtmaning tugashi");
                                else
                                    alertDialog.setTitle("Завершение заказа");

                                if (TestService.lang_is_uz) {
                                    String mes = "Умумий нарх: " + (summa + "").split("\\.")[0] + " сўм\n";
                                    mes += "Кутиш: " + (TestService.last_ojidaniya_summa + "").split("\\.")[0] + " сўм\n";
                                    mes += "Доставка: ";
                                    if (TestService.taksometr_dostavka)
                                        mes += TestService.dostavka_summa;
                                    else
                                        mes += "0";
                                    mes += " сўм\n";

                                    mes += "Багаж: ";
                                    if (TestService.taksometr_bagaj)
                                        mes += TestService.bagaj_summa;
                                    else
                                        mes += "0";
                                    mes += "сўм\n";
                                    mes += "Бошқа хизматлар: " + TestService.dop_summa;
                                    mes += "сўм";
                                    alertDialog.setMessage(mes);
                                } else if (TestService.lang_is_uz_lotin) {
                                    String mes = "Umumiy narx: " + (summa + "").split("\\.")[0] + " so'm\n";
                                    mes += "Kutish: " + (TestService.last_ojidaniya_summa + "").split("\\.")[0] + " so'm\n";
                                    mes += "Dostavka: ";
                                    if (TestService.taksometr_dostavka)
                                        mes += TestService.dostavka_summa;
                                    else
                                        mes += "0";
                                    mes += " so'm\n";

                                    mes += "Bagaj: ";
                                    if (TestService.taksometr_bagaj)
                                        mes += TestService.bagaj_summa;
                                    else
                                        mes += "0";
                                    mes += "so'm\n";
                                    mes += "Boshqa xizmatlar: " + TestService.dop_summa;
                                    mes += "so'm";
                                    alertDialog.setMessage(mes);
                                } else {
                                    String mes = "Общая сумма: " + (summa + "").split("\\.")[0] + " сум\n";
                                    mes += "Ожидание: " + (TestService.last_ojidaniya_summa + "").split("\\.")[0] + " сум\n";
                                    mes += "Доставка: ";
                                    if (TestService.taksometr_dostavka)
                                        mes += TestService.dostavka_summa;
                                    else
                                        mes += "0";
                                    mes += " сум\n";

                                    mes += "Багаж: ";
                                    if (TestService.taksometr_bagaj)
                                        mes += TestService.bagaj_summa;
                                    else
                                        mes += "0";
                                    mes += "сум\n";
                                    mes += "Доп.услуги: " + TestService.dop_summa;
                                    mes += "сум";
                                    alertDialog.setMessage(mes);
                                }

                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                taksometr.t.finish();
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }});

                        String sms_text = TestService.sms_finish_text;
                        sms_text = sms_text.replace("[id]",TestService.id);
                        sms_text = sms_text.replace("[car]",TestService.avto);
                        sms_text = sms_text.replace("[order_class]",TestService.zakaz_class);
                        sms_text = sms_text.replace("[order_id]",TestService.zakaz_id);
                        sms_text = sms_text.replace("[order_navigate]",TestService.zakaz_orientir);
                        sms_text = sms_text.replace("[order_region]",TestService.zakaz_region);
                        sms_text = sms_text.replace("[sum]",(summa + "").split("\\.")[0]);
                        sms_text = sms_text.replace("[wait_sum]",TestService.last_ojidaniya_summa+"");
                        if(!sms_text.equalsIgnoreCase("")){
                            try{
                                SmsManager smgr = SmsManager.getDefault();
                                smgr.sendTextMessage(TestService.zakaz_tel,null,sms_text,null,null);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                        }


                        is_musr=false;

                        zakaz_id="";
                        alertik=true;
                        do_start_ojidaniya=false;
                        taksometr_ojidaniya_do_starta=0;
                        taksometr_summa=0;
                        last_summa=0;
                        last_km=0;
                        dop2_summa=0;
                        last_out_km=0;
                        last_ojidaniya_summa=0;
                        taksometr_km=0.0;
                        taksometr_ojidaniya=0;
                        taksometr_vputi=0;
                        zakaz_region="";
                        zakaz_orientir="";
                        zakaz_class="";
                        zakaz_reg_date="";
                        zakaz_tel="";
                        taksometr_status="Старт";
                        ojidaniya=false;
                        started=false;
                        stopped =false;
                        paused=false;
                        gps_list.clear();

                        if(TestService.lang_is_uz || TestService.lang_is_uz_lotin){
                                voice(R.raw.tolov);
                                Thread.sleep(500);
                                if (summa.length() == 4) {
                                    _son_to_voice(summa.charAt(0));
                                    if (summa.charAt(0) != '0') {
                                        Thread.sleep(500);
                                        voice(R.raw.ming);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(1) != '0') {
                                        _son_to_voice(summa.charAt(1));
                                        Thread.sleep(500);
                                        voice(R.raw.yuz);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(2) != '0') {
                                        _son_on_to_voice(summa.charAt(2));
                                        Thread.sleep(500);
                                    }

                                    if (summa.charAt(3) != '0') {
                                        _son_to_voice(summa.charAt(3));
                                        Thread.sleep(500);
                                    }

                                }
                                else if (summa.length() == 5) {
                                    _son_on_to_voice(summa.charAt(0));
                                    Thread.sleep(500);
                                    _son_to_voice(summa.charAt(1));
                                    Thread.sleep(500);

                                    if (summa.charAt(1) != '0') {
                                        voice(R.raw.ming);
                                        Thread.sleep(500);
                                    }
                                    _son_to_voice(summa.charAt(2));

                                    if (summa.charAt(2) != '0') {
                                        voice(R.raw.yuz);
                                        Thread.sleep(500);
                                    }

                                    if (summa.charAt(0) != '0') {
                                        _son_on_to_voice(summa.charAt(3));
                                        Thread.sleep(500);
                                    }

                                    if (summa.charAt(4) != '0') {
                                        _son_to_voice(summa.charAt(4));
                                        Thread.sleep(500);
                                    }

                                }
                                else if (summa.length() == 6) {
                                    _son_to_voice(summa.charAt(0));
                                    Thread.sleep(500);
                                    voice(R.raw.yuz);
                                    Thread.sleep(500);

                                    if (summa.charAt(1) != '0') {
                                        _son_on_to_voice(summa.charAt(1));
                                        Thread.sleep(500);
                                    }

                                    if (summa.charAt(2) != '0') {
                                        _son_to_voice(summa.charAt(2));
                                        Thread.sleep(500);
                                        voice(R.raw.ming);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(3) != '0') {
                                        _son_to_voice(summa.charAt(3));
                                        voice(R.raw.yuz);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(4) != '0') {
                                        _son_on_to_voice(summa.charAt(4));
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(5) != '0') {
                                        _son_to_voice(summa.charAt(5));
                                        Thread.sleep(500);
                                    }

                                }
                                else if (summa.length() == 7) {
                                    _son_to_voice(summa.charAt(0));
                                    Thread.sleep(500);
                                    voice(R.raw.million);
                                    Thread.sleep(500);
                                    if (summa.charAt(1) != '0') {
                                        _son_to_voice(summa.charAt(1));
                                        Thread.sleep(500);
                                        voice(R.raw.yuz);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(2) != '0') {
                                        _son_on_to_voice(summa.charAt(2));
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(3) != '0') {
                                        _son_to_voice(summa.charAt(3));
                                        Thread.sleep(500);
                                        voice(R.raw.ming);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(4) != '0') {
                                        _son_to_voice(summa.charAt(4));
                                        voice(R.raw.yuz);
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(5) != '0') {
                                        _son_on_to_voice(summa.charAt(5));
                                        Thread.sleep(500);
                                    }
                                    if (summa.charAt(6) != '0') {
                                        _son_to_voice(summa.charAt(6));
                                        Thread.sleep(500);
                                    }
                                }
                                voice(R.raw.som);
                                Thread.sleep(500);
                                voice(R.raw.end);

                            }
                        else{
                                t1=new TextToSpeech(taksometr.t, new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if(status != TextToSpeech.ERROR) {
                                            //Здравствуйте спасибо что воспользовались нашей службой   5000
                                            t1.setLanguage(new Locale("ru_RU"));
                                            String toSpeak = "стоимость вашей поездки"+summa+" суммов. благодарим за поездку. надеемся на дальнейшее сатрудничество.";
                                            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                });
                                Thread.sleep(5000);
                            }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    static void _son_to_voice(char b){

        if (b == '1') {
            voice(R.raw.bir);
        } else if (b == '2') {
            voice(R.raw.ikki);
        } else if (b == '3') {
            voice(R.raw.uch);
        } else if (b == '4') {
            voice(R.raw.tort);
        } else if (b == '5') {
            voice(R.raw.besh);
        } else if (b == '6') {
            voice(R.raw.olti);
        } else if (b == '7') {
            voice(R.raw.yetti);
        } else if (b == '8') {
            voice(R.raw.sakkiz);
        } else if (b == '9') {
            voice(R.raw.toqqiz);
        }
    }

    static void _son_on_to_voice(char b){
        if (b == '1') {
            voice(R.raw.un);
        } else if (b == '2') {
            voice(R.raw.y20);
        } else if (b == '3') {
            voice(R.raw.y30);
        } else if (b == '4') {
            voice(R.raw.y40);
        } else if (b == '5') {
            voice(R.raw.y50);
        } else if (b == '6') {
            voice(R.raw.y60);
        } else if (b == '7') {
            voice(R.raw.y70);
        } else if (b == '8') {
            voice(R.raw.y80);
        } else if (b == '9') {
            voice(R.raw.y90);
        }
    }

    static void voice(int a){
        MediaPlayer mPlayer= MediaPlayer.create(MainActivity.thisactivity, a);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.stop();
            }
        });

        mPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(APP_STOP){
            stopSelf();

        }
    }
}