package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import static android.Manifest.permission.CALL_PHONE;
import static com.example.myapplication.TestService.loc;

public class taksometr extends AppCompatActivity {
    static taksometr t;
    boolean gps=false;
    String text2="";
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taksometr);
        t=this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        TestService.usluga_start();
        TestService.fr3=false;
        ImageView gps_status = (ImageView)findViewById(R.id.imageView3);
        TextView orientir = (TextView)findViewById(R.id.taksometr_orientir);
        orientir.setText("\uD83D\uDEA9 "+TestService.zakaz_region+", "+TestService.zakaz_orientir);
        TextView km_text = (TextView)findViewById(R.id.taksometr_km);
        TextView ojidaniya = (TextView)findViewById(R.id.taksometr_ojidaniya);
        ((TextView)findViewById(R.id.zakazid)).setText(TestService.zakaz_id);

        TextView internet = (TextView)findViewById(R.id.internet);
        TextView taksometr_v_puti = (TextView)findViewById(R.id.taksometr_v_puti);
        TextView taksometr_summa = (TextView)findViewById(R.id.taksometr_summa);
        Button taksometr_start = (Button) findViewById(R.id.taksometr_start);
        Button taksometr_stop = (Button) findViewById(R.id.taksometr_finish);
        Button taksometr_stayanka = (Button)findViewById(R.id.taksometr_stayanka);
        Button taksometr_klient = (Button)findViewById(R.id.taksometr_stayanka2);
        ImageView bagaj = (ImageView) findViewById(R.id.bagaj_icon);
        ImageView dostavka = (ImageView) findViewById(R.id.dostavka_icon);
        TextView text_class = findViewById(R.id.class_text);
        TextView min_sum = findViewById(R.id.min_sum);
        TextView km_sum = findViewById(R.id.km_sum_text);
        Button dispet = (Button)findViewById(R.id.taksometr_stayanka5);



        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            go_gps();
        }

        if(TestService.lang_is_uz){
            dispet.setTextSize(11);
            dispet.setText("☎Диспетчерлар");
            taksometr_stayanka.setText("Кутиш");
            taksometr_klient.setText("\uD83D\uDCDEмижоз");


        }
        else if(TestService.lang_is_uz_lotin) {
            dispet.setTextSize(11);
            dispet.setText("☎Dispetcherlar");
            taksometr_stayanka.setText("Kutish");
            taksometr_klient.setText("\uD83D\uDCDEmijoz");
            taksometr_stop.setText("\uD83C\uDFC1Finish");
            taksometr_start.setText("Start");
        }
        if(TestService.theme){
            ConstraintLayout cos = findViewById(R.id.taks);
            cos.setBackgroundResource(R.color.taks);
            taksometr_summa.setTextColor(Color.BLACK);
            TextView textView = findViewById(R.id.textView);
            textView.setTextColor(R.color.purple_500);
            TextView textView2 = findViewById(R.id.textView2);
            textView2.setTextColor(Color.parseColor("#03A6D6"));
            taksometr_stayanka.setTextColor(Color.BLACK);
            km_text.setTextColor(Color.BLACK);
            taksometr_stop.setTextColor(Color.RED);
            taksometr_stop.setBackgroundResource(R.color.finishbg);
            ojidaniya.setTextColor(Color.parseColor("#DC0606"));
            taksometr_v_puti.setTextColor(Color.parseColor("#838303"));
            TextView t = findViewById(R.id.taksometr);
            t.setBackgroundResource(R.color.tak);
            t.setTextColor(Color.BLACK);
            orientir.setTextColor(Color.BLACK);
            text_class.setTextColor(Color.BLACK);
            min_sum.setTextColor(Color.BLACK);
            km_sum.setTextColor(Color.BLACK);

        }



        if(TestService.taksometr_summa==0){

            if(TestService.taksometr_vputi>0){
                TestService.started=true;
                String[] tt = String.valueOf(TestService.last_km+TestService.last_out_km).split("\\.");
                int ind=1;
                if(tt[1].length()>3)
                    ind = 3;

                km_text.setText(tt[0]+"."+tt[1].substring(0,ind));


                //km_text.setText(TestService.last_km+"");
                taksometr_summa.setText(String.valueOf(TestService.last_summa));
                TestService.taksometr_status="Пауза";
            }

            if(!TestService.gps_started)
                startService(new Intent(this, taxometrService.class));
            //TestService.gps_start();
        }
        taksometr_start.setText(TestService.taksometr_status);


        MediaPlayer mPlayer= MediaPlayer.create(MainActivity.thisactivity, R.raw.wait);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.stop();
                try {
                    mPlayer.prepare();
                    mPlayer.seekTo(0);
                }
                catch (Throwable t) {
                }
            }
        });
        taksometr_stayanka.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!TestService.ojidaniya){
                    mPlayer.start();
                    if(TestService.lang_is_uz)
                        taksometr_stayanka.setText("Кетдик");
                    else if(TestService.lang_is_uz_lotin)
                        taksometr_stayanka.setText("Ketdik");

                    else
                        taksometr_stayanka.setText("Поехали");
                    TestService.ojidaniya=true;
                    TestService.loc=null;
                    TestService.loc2=null;
                    Dialog dialog = new Dialog(taksometr.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().setDimAmount(0);
                    dialog.setContentView(R.layout.ojidaniya);

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();


                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            TextView textView1 = dialog.findViewById(R.id.dialogtextView);
                            TextView timer_text = (TextView)dialog.findViewById(R.id.editTextTime);
                            Button poexali = dialog.findViewById(R.id.na_meste);
                            if(TestService.lang_is_uz){
                                textView1.setText("Кутишлар");
                                poexali.setText("Кетдик");
                            }
                            else if(TestService.lang_is_uz_lotin){
                                textView1.setText("Kutishlar");
                                poexali.setText("Ketdik");
                            }
                            if(TestService.theme){
                                ConstraintLayout cos = dialog.findViewById(R.id.ojidaniya_dialog);
                                cos.setBackgroundColor(Color.parseColor("#ACBDE8"));
                                poexali.setBackgroundColor(Color.parseColor("#165E04"));
                                timer_text.setTextColor(Color.parseColor("#050505"));
                                textView1.setTextColor(Color.BLACK);
                            }
                            poexali.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    loc=null;
                                    TestService.loc2=null;
                                    TestService.ojidaniya = false;
                                    TestService.taksometr_ojidaniya=0;
                                    dialog.cancel();
                                }});

                            while (dialog.isShowing()){


                                    int b = TestService.taksometr_ojidaniya;
                                    text2=((b)/3600)+":";
                                    if(b/3600<10){
                                        text2 = "0"+text2;
                                    }

                                    if(b/60<10){
                                        text2 += "0";
                                    }
                                    text2+=((b)/60);
                                    if(b%60<10){
                                        text2 += ":0"+b%60;
                                    }
                                    else{
                                        text2 += ":"+b%60;
                                    }
                                timer_text.post(new Runnable() {
                                    public void run() {
                                        if(TestService.lang_is_uz)
                                            timer_text.setText("⏰"+text2);
                                         else if(TestService.lang_is_uz_lotin)
                                                timer_text.setText("⏰"+text2);
                                            else
                                                timer_text.setText("⏰"+text2);
                                    }
                                });



                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    //e.printStackTrace();
                                }
                            }



                        }

                    }).start();




                }
                else{

                    if(TestService.lang_is_uz)
                        taksometr_stayanka.setText("Кутиш");
                    else if(TestService.lang_is_uz_lotin)
                        taksometr_stayanka.setText("Kutish");
                    else
                        taksometr_stayanka.setText("Стоянка");
                    taksometr_stayanka.setBackgroundColor(Color.parseColor("#4C5D6F"));
                    TestService.ojidaniya=false;
                    TestService.taksometr_ojidaniya=0;
                }

            }
        });
        taksometr_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(TestService.internet) {
                    if(TestService.taksometr_status.equals("Старт")){
                        AlertDialog alertDialog = new AlertDialog.Builder(taksometr.this).create();
                        if(TestService.lang_is_uz) {
                            alertDialog.setTitle("Буюртмани бошлаш");
                            alertDialog.setMessage("Сиз ростдан хам буюртмани бошламокчимисиз?");
                        }
                        else if(TestService.lang_is_uz_lotin) {
                            alertDialog.setTitle("Buyurtmani boshlash");
                            alertDialog.setMessage("Siz rostdan ham buyurtmani boshlamoqchimisiz?");
                        }
                        else {
                            alertDialog.setTitle("Начать заказ");
                            alertDialog.setMessage("Вы действительно хотите начать заказ?");

                        }
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        TestService.start();
                                        TestService.do_start_ojidaniya = false;
                                        if(TestService.ojidaniya){
                                            mPlayer.stop();
                                            try {
                                                mPlayer.prepare();
                                                mPlayer.seekTo(0);
                                            }
                                            catch (Throwable t) {
                                            }
                                        }
                                        TestService.ojidaniya = false;
                                        taksometr_start.setText(TestService.taksometr_status);
                                        taksometr_stayanka.setEnabled(true);
                                        taksometr_stayanka.setBackgroundColor(Color.parseColor("#4C5D6F"));

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
                    else {
                        TestService.start();
                        taksometr_start.setText(TestService.taksometr_status);
                    }
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(taksometr.this).create();
                    if(TestService.lang_is_uz){
                        alertDialog.setTitle("Уланиш муаммоси");
                        alertDialog.setMessage("Интернетга уланганлигини текширинг");
                    }
                    else if(TestService.lang_is_uz_lotin){
                        alertDialog.setTitle("Ulanish muammosi");
                        alertDialog.setMessage("Internetga ulanganligini tekshiring");
                    }
                    else{
                        alertDialog.setTitle("Проблема подключения");
                        alertDialog.setMessage("Проверьте подключения к интернету");
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"ОК",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick (DialogInterface dialog,int which){
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        taksometr_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(TestService.internet) {
                    AlertDialog alertDialog = new AlertDialog.Builder(taksometr.this).create();
                    if(TestService.lang_is_uz){
                        alertDialog.setTitle("Буюртмани якунлаш");
                        alertDialog.setMessage("Сиз ростдан хам буюртмани якунламоқчимисиз?");
                    }
                    else if(TestService.lang_is_uz_lotin){
                        alertDialog.setTitle("Buyurtmani yakunlash");
                        alertDialog.setMessage("Siz rostdan ham buyurtmani yakunlamoqchimisiz?");
                    }
                    else{
                        alertDialog.setTitle("Завершение заказа");
                        alertDialog.setMessage("Вы действительно хотите завершить заказ?");
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    TestService.stop();
                                    if(TestService.ojidaniya){
                                        mPlayer.stop();
                                        try {
                                            mPlayer.prepare();
                                            mPlayer.seekTo(0);
                                        }
                                        catch (Throwable t) {
                                        }
                                    }
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
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(taksometr.this).create();
                    if(TestService.lang_is_uz){
                        alertDialog.setTitle("Уланиш муаммоси");
                        alertDialog.setMessage("Интернетга уланганлигини текширинг");
                    }
                    else if(TestService.lang_is_uz_lotin){
                        alertDialog.setTitle("Ulanish muammosi");
                        alertDialog.setMessage("Internetga ulanganligini tekshiring");
                    }
                    else{
                        alertDialog.setTitle("Проблема подключения");
                        alertDialog.setMessage("Проверьте подключения к интернету");
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"ОК",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick (DialogInterface dialog,int which){
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!TestService.zakaz_id.equalsIgnoreCase("") && !TestService.stopped)
                {
                    try {
                        orientir.post(new Runnable() {

                            public void run() {

                                if(TestService.internet){
                                    internet.setText("\uD83C\uDF10");
                                }
                                else{
                                    internet.setText("");
                                }
                                text_class.setText(TestService.zakaz_class);
                                min_sum.setText(TestService.zakaz_wait_summ*60 + "/мин");
                                if(TestService.in_rayon){
                                    km_sum.setText(TestService.zakaz_last_km+"/км");
                                }
                                else
                                    km_sum.setText(TestService.zakaz_out_km+"/км");


                                if(TestService.ojidaniya){
                                    if(TestService.lang_is_uz)
                                        taksometr_stayanka.setText("Кетдик");
                                    else if(TestService.lang_is_uz_lotin)
                                        taksometr_stayanka.setText("Ketdik");
                                    else
                                        taksometr_stayanka.setText("Поехали");
                                    taksometr_stayanka.setBackgroundColor(Color.parseColor("#272727"));
                                }
                                else{
                                    taksometr_stayanka.setBackgroundColor(Color.parseColor("#4C5D6F"));
                                    if(TestService.lang_is_uz)
                                        taksometr_stayanka.setText("Кутиш");
                                    else if(TestService.lang_is_uz_lotin)
                                        taksometr_stayanka.setText("Kutish");
                                    else
                                        taksometr_stayanka.setText("Стоянка");
                                }
                                if(TestService.do_start_ojidaniya){
                                    if(TestService.lang_is_uz)
                                        taksometr_stayanka.setText("Кетдик");
                                    else if(TestService.lang_is_uz_lotin)
                                        taksometr_stayanka.setText("Ketdik");
                                    else
                                        taksometr_stayanka.setText("Поехали");
                                    taksometr_stayanka.setBackgroundColor(Color.parseColor("#272727"));
                                    taksometr_stayanka.setEnabled(false);
                                }

                                if(TestService.taksometr_bagaj)
                                    bagaj.setVisibility(View.VISIBLE);

                                else bagaj.setVisibility(View.GONE);
                                if(TestService.taksometr_dostavka)
                                    dostavka.setVisibility(View.VISIBLE);

                                else dostavka.setVisibility(View.GONE);
                                String[] tt = String.valueOf(TestService.taksometr_km+TestService.last_out_km).split("\\.");

                                int ind=1;
                                if(tt[1].length()>3)
                                    ind = 3;

                                km_text.setText(tt[0]+"."+tt[1].substring(0,ind));


                                if(TestService.do_start_ojidaniya){
                                    String text="";
                                    int b = TestService.taksometr_ojidaniya_do_starta;
                                    text=((b)/3600)+":";
                                    if(b/3600<10){
                                        text = "0"+text;
                                    }

                                    if(b/60<10){
                                        text += "0";
                                    }
                                    text+=((b)/60);
                                    if(b%60<10){
                                        text += ":0"+b%60;
                                    }
                                    else{
                                        text += ":"+b%60;

                                    }
                                    if(TestService.lang_is_uz)
                                        ojidaniya.setText("⏰кутишлар: "+text);
                                    else if(TestService.lang_is_uz_lotin)
                                        ojidaniya.setText("⏰kutishlar: "+text);
                                    else
                                        ojidaniya.setText("⏰ожидание: "+text);
                                    taksometr_summa.setText((taksometr_summa+"").split("\\.")[0]+"");
                                }


                                int b = TestService.taksometr_vputi;
                                String text=((b)/3600)+":";
                                if(b/3600<10){
                                    text = "0"+text;
                                }
                                if(b/60<10){
                                    text += "0";
                                }
                                text+=((b/60)%60);
                                if(b%60<10){
                                    text += ":0"+b%60;
                                }
                                else{
                                    text += ":"+b%60;

                                }
                                if(TestService.lang_is_uz)
                                    taksometr_v_puti.setText("⏱йўлда: "+text);
                                else if(TestService.lang_is_uz_lotin)
                                    taksometr_v_puti.setText("⏱yo'lda: "+text);
                                else
                                    taksometr_v_puti.setText("⏱в пути: "+text);

                                double summ=TestService.taksometr_summa;
                                if (TestService.taksometr_summa-TestService.last_ojidaniya_summa < TestService.zakaz_min_summ && !TestService.zakaz_uchtaxi)
                                    summ = TestService.zakaz_min_summ + TestService.last_ojidaniya_summa;

                                if(TestService.taksometr_dostavka)
                                    summ+=TestService.dostavka_summa;
                                if(TestService.taksometr_bagaj)
                                    summ+=TestService.bagaj_summa;

                                taksometr_summa.setText(((summ+TestService.dop_summa+TestService.dop2_summa)+"").split("\\.")[0]);
                                if(TestService.loc!=null){
                                    if(!gps){
                                        gps_status.setImageDrawable(ContextCompat.getDrawable(taksometr.this, R.drawable.gps_on));
                                        gps=true;
                                    }

                                }
                                else{
                                    if(gps){
                                        gps_status.setImageDrawable(ContextCompat.getDrawable(taksometr.this, R.drawable.gps_off));
                                        gps=false;
                                    }
                                }


                            }
                        });
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }



            }
        }).start();
    }
    void ggppss(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void go_gps(){
        AlertDialog alertDialog = new AlertDialog.Builder(taksometr.this).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        if(TestService.lang_is_uz){
            alertDialog.setTitle("Сизда GPS ўчирилган.");
            alertDialog.setMessage("GPS йоқилсин?");
        }
        else if(TestService.lang_is_uz_lotin){
            alertDialog.setTitle("Sizda GPS o'chirilgan.");
            alertDialog.setMessage("GPS yoqilsin?");
        }
        else{
            alertDialog.setTitle("У вас отключен GPS.");
            alertDialog.setMessage("Включить GPS?");
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ggppss();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void client_tel(View view) {
        if (!TestService.zakaz_tel.equalsIgnoreCase("")) {
            //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + TestService.zakaz_tel)));
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
    public void operator_tel(View view) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+TestService.operator));

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }

    public void trevogab(View view) {
        if(TestService.loc2!=null) {

            AlertDialog alertDialog = new AlertDialog.Builder(taksometr.this).create();
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
                            Taxi.trevoga="trevoga|" + TestService.id+"|"+TestService.RAYON;

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

    @Override
    protected void onStop() {
        super.onStop();

    }
}