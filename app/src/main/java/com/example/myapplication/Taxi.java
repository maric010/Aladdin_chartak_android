package com.example.myapplication;

import android.app.ActivityManager;
import android.app.AlertDialog;
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
import android.telephony.SmsManager;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;

import org.apache.commons.io.input.BoundedInputStream;

import java.awt.font.NumericShaper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Context.WINDOW_SERVICE;
import static com.example.myapplication.TestService.RAYON;
import static java.lang.Thread.sleep;
public class Taxi {
    public static String rayons_from_server;
    public static String orders_from_server;
    public static boolean get_orders_from_server=false;
    public static boolean zakaz_is_free=false;
    public static String zakaz_is_free_id;
    public static String zakaz_is_free_result;
    public static boolean decline_zakaz;
    public static String decline_zakaz_id;
    public static boolean send_gps_list;
    public static String message;
    public static String summa;
    public static String xz;
    public static boolean is_connected=false;
    public static boolean get_usluga=false;
    public static String get_usluga_text;
    public static String get_usluga_result;
    public static String send_na_meste="";
    public static String send_set_column="";
    public static String send_set_language="";
    public static String send_set_lotin="";
    public static String send_set_theme="";
    public static String send_zakaz_start="";
    public static boolean get_trevoga=false;
    public static String get_trevoga_text="";
    public static String get_trevoga_result="";
    public static boolean history=false;
    public static String history_text="";
    public static String history_result="";
    public static String trevoga="";
    public static boolean sgl_end=false;
    public static String[] split;
    public static String auto_order_accept="";
    public static String[] auto_order_finalSplit1;
    public static LinearLayout rootView;

    public static void command(String command) throws Exception {
        String[] split=command.split("/");
        System.out.println("command "+split[0]);
        if(split.length>0)
            switch (split[0]){
                case "finish_ok":
                    Taxi.send_gps_list=false;
                    /*
                    try {
                        Socket client = new Socket("188.120.251.80", 6106);
                        client.setSoTimeout(5000);
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                        out.write("gps_list|"+RAYON+"|"+TestService.zakaz_id+"|"+Taxi.message);
                        out.flush();
                        out.close();
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                    return;
                case "iron_check_ok":
                        if(check(split[split.length-1].split("&"))){
                            splashActivity.th.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(splashActivity.th).create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.setTitle("Диққат!!!");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ОК",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    TestService.APP_STOP = true;
                                                    dialog.dismiss();
                                                    splashActivity.th.finish();

                                                }
                                            });
                                    alertDialog.show();
                                }
                            });
                            return;
                        }
                    System.out.println(command);
                        TestService.id = split[1];
                        TestService.balance = split[2];
                        TestService.name = split[3];
                        TestService.avto = split[4];
                        TestService.date = split[5];
                        TestService.avto_class = split[6];
                        TestService.RAYON = split[7];
                        if (split[8].equalsIgnoreCase("True"))
                            TestService.theme = true;
                        if (split[9].equalsIgnoreCase("True")) {
                            TestService.lang_is_uz = true;
                        } else if (split[9].equalsIgnoreCase("lotin"))
                            TestService.lang_is_uz_lotin = true;
                        TestService.company = split[10];
                        TestService.tarif = split[11];
                        TestService.tel = split[12];
                        TestService.grid_column = Integer.parseInt(split[13]);
                        TestService.operator = split[14];
                        if (split.length > 16) {
                            TestService.zakaz_id = split[15];
                            if (split[16].equalsIgnoreCase("None"))
                                TestService.last_summa = 0;
                            else
                                TestService.last_summa = Double.parseDouble(split[16]);
                            if (split[17].equalsIgnoreCase("None"))
                                TestService.taksometr_ojidaniya = 0;
                            else
                                TestService.taksometr_ojidaniya = Integer.parseInt(split[17]);
                            if (split[18].equalsIgnoreCase("None"))
                                TestService.taksometr_vputi = 0;
                            else
                                TestService.taksometr_vputi = Integer.parseInt(split[18]);
                            TestService.zakaz_region = split[19];
                            TestService.zakaz_orientir = split[20];
                            TestService.zakaz_tel = split[21];
                            TestService.zakaz_class = split[22];
                            if (split[23].equalsIgnoreCase("None"))
                                TestService.last_km = 0;
                            else
                                TestService.last_km = Double.parseDouble(split[23]);
                            if (split[24].equalsIgnoreCase("None"))
                                TestService.last_ojidaniya_summa = 0;
                            else
                                TestService.last_ojidaniya_summa = Double.parseDouble(split[24]);
                            if (split[18].equals("0")) {
                                TestService.taksometr_status = "Старт";
                            }
                            if(split[25].equalsIgnoreCase("True")){
                                TestService.taksometr_bagaj=true;
                            }
                            else{
                                TestService.taksometr_bagaj=false;
                            }
                            if(split[26].equalsIgnoreCase("True")){
                                TestService.taksometr_dostavka=true;
                            }
                            else{
                                TestService.taksometr_dostavka=false;
                            }
                            TestService.last_out_km = Double.parseDouble(split[27]);
                        }


                    TestService.lat_array.clear();
                    TestService.long_array.clear();
                    connection.sendData(("get_gps_list2|" + TestService.RAYON+"|").getBytes());
                    return;

                case "gps_list":
                    split = split[1].replace(",", ".").split(";");

                    for (int i = 0; i < split.length - 1; i++) {
                        String[] sp = split[i].split("o");
                        TestService.lat_array.add(Double.valueOf(sp[0]));
                        TestService.long_array.add(Double.valueOf(sp[1]));
                    }

                    System.out.println("finished");

                    Intent intent = new Intent(splashActivity.th, MainActivity.class);
                    splashActivity.th.finish();
                    splashActivity.th.startActivity(intent);
                    return;
                case "ORDERS":
                    if(split[1].equalsIgnoreCase("!"+TestService.region+"!")){
                        Taxi.orders_from_server=command.replace("ORDERS/"+split[1]+"/","");
                        Fragment3.refresh();
                    }

                    return;
                case "auto_order_accepted":
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!TestService.zakaz_id.equalsIgnoreCase("")){
                                TestService.zakazdop_region = Taxi.auto_order_finalSplit1[1];
                                TestService.zakazdop_orientir = Taxi.auto_order_finalSplit1[2];
                                TestService.zakazdop_id = Taxi.auto_order_finalSplit1[3];
                                TestService.zakazdop_tel = Taxi.auto_order_finalSplit1[4];
                                //TestService.zakazdop_name = split[9];
                                TestService.zakazdop_reg_date = Taxi.auto_order_finalSplit1[5];
                                TestService.zakazdop_class = Taxi.auto_order_finalSplit1[6];
                                TestService.zakazdop_min_summ=Integer.parseInt(Taxi.auto_order_finalSplit1[8]);
                                TestService.zakazdop_note= Taxi.auto_order_finalSplit1[7];
                                String sms_text = TestService.sms_na_meste_text;
                                sms_text = sms_text.replace("[id]",TestService.id);
                                sms_text = sms_text.replace("[car]",TestService.avto);
                                sms_text = sms_text.replace("[order_class]",TestService.zakazdop_class);
                                sms_text = sms_text.replace("[order_id]",TestService.zakazdop_id);
                                sms_text = sms_text.replace("[order_navigate]",TestService.zakazdop_orientir);
                                sms_text = sms_text.replace("[order_region]",TestService.zakazdop_region);
                                if(!sms_text.equalsIgnoreCase(""))
                                {
                                    try{
                                        SmsManager smgr = SmsManager.getDefault();
                                        smgr.sendTextMessage(TestService.zakazdop_tel,null,sms_text,null,null);
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            }
                            else{
                                TestService.zakaz_region = Taxi.auto_order_finalSplit1[1];
                                TestService.zakaz_orientir = Taxi.auto_order_finalSplit1[2];
                                TestService.zakaz_id = Taxi.auto_order_finalSplit1[3];
                                TestService.zakaz_tel = Taxi.auto_order_finalSplit1[4];
                                //TestService.zakaz_name = split[9];
                                TestService.zakaz_reg_date = Taxi.auto_order_finalSplit1[5];
                                TestService.zakaz_class = Taxi.auto_order_finalSplit1[6];
                                TestService.last_summa=0;
                                TestService.zakaz_min_summ=Integer.parseInt(Taxi.auto_order_finalSplit1[9]);
                                TestService.zakaz_note= Taxi.auto_order_finalSplit1[7];
                            }

                            if(TestService.zakazdop_id.equalsIgnoreCase("")){
                                ActivityManager activityManager = (ActivityManager) MainActivity.thisactivity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                                activityManager.moveTaskToFront(MainActivity.thisactivity.getTaskId(), ActivityManager.MOVE_TASK_NO_USER_ACTION);

                                Dialog dialog = new Dialog(rootView.getContext());
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
                                        if (TestService.lang_is_uz) {
                                            textView1.setText("Мижозга боряпмиз\n"+TestService.zakaz_note);
                                            na_meste.setText("Жойида");
                                            textView2.setText("Мижоз билан уланиш");
                                        } else if (TestService.lang_is_uz_lotin) {
                                            textView1.setText("Mijozga boryapmiz\n"+TestService.zakaz_note);
                                            na_meste.setText("Joyida");
                                            textView2.setText("Mijoz bilan ulanish");
                                        }
                                        else{
                                            textView1.setText(textView1.getText().toString()+"\n"+TestService.zakaz_note);
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
                                            public void onClick(View v) {
                                                if (!TestService.zakaz_tel.equalsIgnoreCase("")) {
                                                    Intent i = new Intent(Intent.ACTION_CALL);
                                                    i.setData(Uri.parse("tel:" + TestService.zakaz_tel));

                                                    if (ContextCompat.checkSelfPermission(MainActivity.thisactivity.getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                                        MainActivity.thisactivity.startActivity(i);
                                                    } else {
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                            MainActivity.thisactivity.requestPermissions(new String[]{CALL_PHONE}, 1);
                                                        }
                                                    }
                                                }
                                            }});
                                        na_meste.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                //TestService.loc=null;
                                                TestService.do_start_ojidaniya = true;
                                                Taxi.send_na_meste = "na_meste|"+TestService.zakaz_id+"|"+TestService.RAYON;
                                                TestService.usluga_start();
                                                MainActivity.thisactivity.runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Intent dialogIntent = new Intent(MainActivity.thisactivity, taksometr.class);
                                                        MainActivity.thisactivity.startActivity(dialogIntent);
                                                    }
                                                });
                                                dialog.cancel();
                                            }});
                                        final String[] text = new String[1];
                                        final int[] a = new int[1];
                                        final int[] b = new int[1];
                                        for (a[0] = TestService._min; a[0] > 0; a[0] -= 1) {
                                            if(!dialog.isShowing() || TestService.zakaz_id.equalsIgnoreCase("")){
                                                TestService.do_start_ojidaniya = true;
                                                TestService._min=100000;
                                                break;
                                            }

                                            TestService._min = TestService._min-1;
                                            b[0] =TestService._min;
                                            text[0] =((b[0])/60)+"";
                                            if(b[0] /60<10){
                                                text[0] = "0"+ text[0];
                                            }
                                            if(b[0] %60<10){
                                                text[0] += ":0"+ b[0] %60;
                                            }
                                            else{
                                                text[0] += ":"+ b[0] %60;
                                            }

                                            timer_text.post(new Runnable() {
                                                public void run() {
                                                    timer_text.setText(text[0] +"");
                                                }
                                            });

                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                // e.printStackTrace();
                                            }
                                        }
                                        dialog.cancel();

                                    }
                                }).start();
                                String sms_text = TestService.sms_na_meste_text;
                                sms_text = sms_text.replace("[id]",TestService.id);
                                sms_text = sms_text.replace("[car]",TestService.avto);
                                sms_text = sms_text.replace("[order_class]",TestService.zakaz_class);
                                sms_text = sms_text.replace("[order_id]",TestService.zakaz_id);
                                sms_text = sms_text.replace("[order_navigate]",TestService.zakaz_orientir);
                                sms_text = sms_text.replace("[order_region]",TestService.zakaz_region);
                                if(!sms_text.equalsIgnoreCase(""))
                                {
                                    try{
                                        SmsManager smgr = SmsManager.getDefault();
                                        smgr.sendTextMessage(TestService.zakaz_tel,null,sms_text,null,null);
                                    }
                                    catch (Exception e){
                                        //e.printStackTrace();
                                    }

                                }
                            }
                        }
                    });

                    return;

                case "REGIONS":
                    Taxi.rayons_from_server = command.replace(split[0]+"/","");
                    Fragment2.refresh();
                    return;
                case "blacklist":
                    TestService.APP_STOP = true;
                    MainActivity.stopped = true;
                        MainActivity.thisactivity.stopService(MainActivity.serv);
                        MainActivity.thisactivity.finish();
                    return;
                case "balance":
                    try {
                        split = command.replace("balance/","").split("/");
                        if (!split[0].equalsIgnoreCase(TestService.balance)) {
                            int bl = Integer.parseInt(TestService.balance);

                            if(Integer.parseInt(TestService.balance)>bl){
                                MediaPlayer mPlayer= MediaPlayer.create(MainActivity.thisactivity, R.raw.balans);
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
                                mPlayer.start();
                            }
                            TestService.balance = Integer.parseInt(split[0])+"";

                        }
                        TestService.sms_na_meste_text=split[1];
                        TestService.sms_finish_text=split[2];

                        MainActivity.thisactivity.headerView.post(new Runnable() {
                            public void run() {
                                TextView balance = MainActivity.thisactivity.headerView.findViewById(R.id.textView);
                                if(balance.getText()!=TestService.balance)
                                    balance.setText("\uD83D\uDCB0"+TestService.balance);
                            }
                        });


                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                case "trevoga":
                    TestService.trevoga_lat = Double.parseDouble(split[1]);
                        TestService.trevoga_lng = Double.parseDouble(split[2]);
                        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.thisactivity, R.raw.trevoga);
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mPlayer.stop();
                            }
                        });
                        AudioManager mAudioManager = (AudioManager)MainActivity.thisactivity.getSystemService(Context.AUDIO_SERVICE);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);

                        mPlayer.start();
                    String[] finalSplit5 = split;
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                            public void run() {
                                MainActivity.trev(finalSplit5);

                            }
                        });

                    return;
                case "alert":
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(MainActivity.thisactivity.getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    String[] finalSplit6 = split;
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.thisactivity).create();
                            alertDialog.setTitle(finalSplit6[1]);
                            alertDialog.setMessage(finalSplit6[2].replace("|n|", "\n"));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, finalSplit6[3],
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }
                    });
                    return;
                case "history3":
                    {
                        TableLayout tableLayout = Fragment10.root.findViewById(R.id.table);
                        String s = command.replace("history3/","");
                        android.widget.TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                        android.widget.TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(1, 1, 1, 1);
                        layoutParam.setMargins(1, 1, 1, 1);
                        split = s.split(";");
                        String[] finalSplit4 = split;
                        tableLayout.post(new Runnable() {
                            public void run() {
                                tableLayout.removeAllViews();
                                String[] sp = finalSplit4[0].split("/");
                                TextView textView = Fragment10.root.findViewById(R.id.itog);
                                if(TestService.lang_is_uz){
                                    textView.setText("Тўлдирилган сони: "+sp[0]+"\nТўлдирилган маблағ: "+sp[1]);
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("To'ldirilgan soni: "+sp[0]+"\nTo'ldirilgan mablag': "+sp[1]);
                                }
                                else
                                    textView.setText("Количество пополнений: "+sp[0]+"\nПополненная сумма: "+sp[1]);

                                TableRow tableRow = new TableRow(Fragment10.root.getContext());
                                tableRow.setBackgroundColor(Color.parseColor("#000000"));

                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Маблағ");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Mablag'");
                                }
                                else
                                    textView.setText("Сумма");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Сана");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Sana");
                                }
                                else
                                    textView.setText("Дата");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                tableLayout.addView(tableRow, layoutParam);

                                for(int i = 1; i< finalSplit4.length; i++){
                                    sp= finalSplit4[i].split("/");
                                    tableRow = new TableRow(Fragment10.root.getContext());
                                    tableRow.setBackgroundColor(Color.parseColor("#000000"));
                                    String zid=sp[0];

                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[0]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);

                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[1]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);

                                    tableLayout.addView(tableRow, layoutParam);

                                }
                            }});
                    }
                    return;
                case "iron_no":
                    intent = new Intent(splashActivity.th, MainActivity.class);
                    splashActivity.th.finish();
                    splashActivity.th.startActivity(intent);
                    return;
                case "history2":
                    String s = command.replace("history2/","");
                    if (s != null) {
                        TableLayout tableLayout = Fragment10.root.findViewById(R.id.table);
                        TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(1, 1, 1, 1);
                        layoutParam.setMargins(1, 1, 1, 1);
                        split = s.split(";");
                        String[] finalSplit3 = split;
                        tableLayout.post(new Runnable() {
                            public void run() {
                                tableLayout.removeAllViews();
                                String[] sp = finalSplit3[0].split("/");
                                TextView textView = Fragment10.root.findViewById(R.id.itog);
                                if(TestService.lang_is_uz){
                                    textView.setText("Бекор қилинганлар сони: "+sp[0]);
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Bekor qilinganlar soni: "+sp[0]);
                                }
                                else
                                    textView.setText("Количество отказов: "+sp[0]);
                                Fragment10.all_count=Integer.parseInt(sp[0]);
                                TableRow tableRow = new TableRow(Fragment10.root.getContext());
                                tableRow.setBackgroundColor(Color.parseColor("#000000"));

                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Манзил");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Manzil");
                                }
                                else
                                    textView.setText("Адрес");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Сана");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Sana");
                                }
                                else
                                    textView.setText("Дата");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                tableLayout.addView(tableRow, layoutParam);

                                for(int i = 1; i< finalSplit3.length; i++){
                                    sp= finalSplit3[i].split("/");
                                    tableRow = new TableRow(Fragment10.root.getContext());
                                    tableRow.setBackgroundColor(Color.parseColor("#000000"));
                                    String zid=sp[0];
                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[1]+","+sp[2]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);


                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[4]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);
                                    tableLayout.addView(tableRow, layoutParam);

                                }
                            }});
                    }
                    return;
                case "history1":
                    s = command.replace("history1/","");
                        TableLayout tableLayout = Fragment10.root.findViewById(R.id.table);
                        android.widget.TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                        android.widget.TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(1, 1, 1, 1);
                        layoutParam.setMargins(1, 1, 1, 1);
                        split = s.split(";");
                    String[] finalSplit2 = split;
                    tableLayout.post(new Runnable() {
                            public void run() {
                                tableLayout.removeAllViews();
                                String[] sp = finalSplit2[0].split("/");
                                TextView textView = Fragment10.root.findViewById(R.id.itog);

                                if(TestService.lang_is_uz){
                                    textView.setText("Буюртмалар сони: "+sp[0]+"\nИшланган пул: "+sp[1]);
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Buyurtmalar soni: "+sp[0]+"\nIshlangan pul: "+sp[1]);
                                }
                                else
                                    textView.setText("Количество заказов: "+sp[0]+"\nЗаработанная сумма: "+sp[1]);

                                TableRow tableRow = new TableRow(Fragment10.root.getContext());
                                tableRow.setBackgroundColor(Color.parseColor("#000000"));

                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Манзил");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Manzil");
                                }
                                else
                                    textView.setText("Адрес");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Маблағ");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Mablag'");
                                }
                                else
                                    textView.setText("Сумма");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                textView = new TextView(Fragment10.root.getContext());
                                if(TestService.lang_is_uz){
                                    textView.setText("Сана");
                                }
                                else if(TestService.lang_is_uz_lotin){
                                    textView.setText("Sana");
                                }
                                else
                                    textView.setText("Дата");
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                textView.setTextSize(18);
                                tableRow.addView(textView, layoutParams);
                                tableLayout.addView(tableRow, layoutParam);

                                for(int i = 1; i< finalSplit2.length; i++){
                                    sp= finalSplit2[i].split("/");
                                    tableRow = new TableRow(Fragment10.root.getContext());
                                    tableRow.setBackgroundColor(Color.parseColor("#000000"));
                                    String zid=sp[0];
                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[1]+","+sp[2]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);

                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[3]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);

                                    textView = new TextView(Fragment10.root.getContext());
                                    textView.setText(sp[4]);
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextAppearance(Fragment10.root.getContext(), android.R.style.TextAppearance_Large);
                                    textView.setTextSize(15);
                                    tableRow.addView(textView, layoutParams);
                                    tableLayout.addView(tableRow, layoutParam);

                                }
                            }});

                    return;
                case "auto_zakaz":
                    if(!TestService.zakaz_id.equals(""))
                        return;
                    final long[] ao = {System.currentTimeMillis()};
                    String[] finalSplit1 = split;
                        MainActivity.thisactivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int LAYOUT_FLAG;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                                } else {
                                    LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
                                }
                                WindowManager manager = (WindowManager) MainActivity.thisactivity.getSystemService(WINDOW_SERVICE);
                                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT, // Ширина экрана
                                        ViewGroup.LayoutParams.WRAP_CONTENT, // Высота экрана
                                        LAYOUT_FLAG, // Говорим, что приложение будет поверх других. В поздних API > 26, данный флаг перенесен на TYPE_APPLICATION_OVERLAY
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // Необходимо для того чтобы TouchEvent'ы в пустой области передавались на другие приложения
                                        PixelFormat.TRANSLUCENT); // Само окно прозрачное

                                // Задаем позиции для нашего Layout
                                params.gravity = Gravity.TOP | Gravity.CENTER;
                                params.x = 0;
                                params.y = 0;
                                // Отображаем наш Layout
                                rootView = (LinearLayout) LayoutInflater.from(MainActivity.thisactivity).inflate(R.layout.auto_order, null);

                                TextView textView = rootView.findViewById(R.id.zakaz_info);
                                TextView textView2 = rootView.findViewById(R.id.zakaz_info2);
                                if(TestService.lang_is_uz)
                                    textView.setText("🏁Манзил: "+ finalSplit1[2]+"\nТариф: "+ finalSplit1[6]);
                                else if(TestService.lang_is_uz_lotin)
                                    textView.setText("🏁Manzil: "+ finalSplit1[2]+"\nTarif: "+ finalSplit1[6]);
                                else
                                    textView.setText("🏁Ориентир: "+ finalSplit1[2]+"\nТариф: "+ finalSplit1[6]);

                                if(!finalSplit1[7].equals("")){
                                    textView2.setText("Инфо: "+ finalSplit1[7]);
                                }
                                else{
                                    textView2.setVisibility(View.GONE);
                                }

                                final boolean[][] ok = {{false}};
                                final boolean[] closed_show = {false};

                                Button accept = rootView.findViewById(R.id.button10);
                                Button decline = rootView.findViewById(R.id.button9);
                                MediaPlayer r = MediaPlayer.create(MainActivity.thisactivity, R.raw.auto_order3);
                                r.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        r.stop();
                                        try {
                                            r.prepare();
                                            r.seekTo(0);
                                        }
                                        catch (Throwable t) {
                                        }
                                    }
                                });
                                r.start();
                                final boolean[] clicked = {false};
                                accept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(clicked[0])
                                            return;
                                        clicked[0] =true;
                                             Taxi.auto_order_accept="auto_zakaz_accept3|"+ finalSplit1[3]+"|"+TestService.id+"|"+ RAYON+"|";
                                           Taxi.auto_order_finalSplit1=finalSplit1;
                                        TestService._min = 25*60;
                                        ok[0][0]=true;
                                        closed_show[0] =true;

                                    }
                                });
                                decline.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        closed_show[0]=true;
                                        Taxi.auto_order_accept ="auto_zakaz_decline2|"+ finalSplit1[3]+"|"+TestService.id+"|"+ RAYON+"|";
                                    }
                                });

                                manager.addView(rootView, params);


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProgressBar progressBar = rootView.findViewById(R.id.progressBar);
                                        for (int i = 100; i > -1; i -= 1) {
                                            if (closed_show[0]) {
                                                System.out.println("PRINYAL");
                                                r.stop();
                                                manager.removeView(rootView);
                                                break;
                                            }
                                            progressBar.setProgress(i);
                                            try {
                                                sleep(100);
                                            } catch (InterruptedException e) {
                                                //e.printStackTrace();
                                            }
                                            if(i==0){
                                                System.out.println("OTMENYAYU I="+i);
                                                r.stop();
                                                manager.removeView(rootView);
                                                ao[0] = System.currentTimeMillis();
                                                Taxi.auto_order_accept ="auto_zakaz_decline2|"+ finalSplit1[3]+"|"+TestService.id+"|"+ RAYON+"|";
                                            }
                                        }
                                    }}).start();

                            }
                        });
                        return;
                case "zakaz_is_free":
                    System.out.println("ZAKAZ_IS_FREEOK");
                    split = Taxi.split;
                    String[] finalSplit7 = split;
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!TestService.zakaz_id.equalsIgnoreCase("")){
                                System.out.println("zakaz_is_free1");
                                TestService.zakazdop_region = finalSplit7[0];
                                TestService.zakazdop_orientir = finalSplit7[1];
                                TestService.zakazdop_id = finalSplit7[2];
                                TestService.zakazdop_tel = finalSplit7[3];
                                //TestService.zakazdop_name = split[9];
                                TestService.zakazdop_reg_date = finalSplit7[4];
                                TestService.zakazdop_class = finalSplit7[5];
                                TestService.zakazdop_min_summ=Integer.parseInt(finalSplit7[8]);
                                TestService.zakazdop_note= finalSplit7[6].replace("None","");
                                TestService.fr3=false;
                                TestService.fr4=true;
                                MainActivity.thisactivity.fragment_switch(new Fragment4());

                            }
                            else{
                                System.out.println("zakaz_is_free2");
                                try{
                                    TestService.zakaz_region = finalSplit7[0];

                                    TestService.zakaz_orientir = finalSplit7[1];
                                    TestService.zakaz_id = finalSplit7[2];
                                    TestService.zakaz_tel = finalSplit7[3];
                                    //TestService.zakaz_name = split[9];
                                    TestService.zakaz_reg_date = finalSplit7[4];
                                    TestService.zakaz_class = finalSplit7[5];
                                    TestService.last_summa=0;
                                    TestService.zakaz_min_summ=Integer.parseInt(finalSplit7[8]);
                                    TestService.zakaz_note= finalSplit7[6];

                                    TestService.fr3=false;
                                    TestService.fr4=true;
                                    MainActivity.thisactivity.fragment_switch(new Fragment4());
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                System.out.println("FRAGMENT4");
                            }

                        }
                    });

                    return;
                case "finish_order":
                    if (TestService.zakaz_id.equals(split[2])){
                        TestService.is_musr = true;
                        TestService.musr = split[1];
                        TestService.stop();
                        }
                    return;
                case "order":
                    s = command.replace(split[0]+"/"+split[1]+"/","");
                    if (!TestService.zakaz_id.equals(split[1]))
                        return;
                    split = s.split("/");
                    if (split[0].equals("true")) {
                        TestService.taksometr_bagaj = true;
                        TestService.bagaj_summa = Integer.parseInt(split[1]);
                    }
                    else
                        TestService.taksometr_bagaj = false;
                    if (split[2].equals("true")) {
                        TestService.taksometr_dostavka = true;
                        TestService.dostavka_summa = Integer.parseInt(split[3]);
                    } else
                        TestService.taksometr_dostavka = false;
                    if (!split[4].equalsIgnoreCase("0")) {
                        TestService.dop_summa = Integer.parseInt(split[4]);
                    } else {
                        TestService.dop_summa = 0;
                    }
                    if(split[5].equalsIgnoreCase("false") && !RAYON.equalsIgnoreCase("Чуст")){
                        TestService.zakaz_uchtaxi=false;
                    }
                    else{
                        TestService.zakaz_uchtaxi=true;
                    }
                    if (split[6].equalsIgnoreCase("false")) {
                        TestService.zakaz_id = "";
                        TestService.alertik=true;
                        TestService.do_start_ojidaniya = false;
                        TestService.taksometr_ojidaniya_do_starta = 0;
                        TestService.taksometr_summa = 0;
                        TestService.last_summa = 0;
                        TestService.last_km = 0;
                        TestService.last_ojidaniya_summa = 0;
                        TestService.taksometr_km = 0.0;
                        TestService.taksometr_ojidaniya = 0;
                        TestService.taksometr_vputi = 0;
                        TestService.zakaz_region = "";
                        TestService.zakaz_orientir = "";

                        TestService.zakaz_class = "";
                        TestService.zakaz_reg_date = "";
                        TestService.zakaz_tel = "";
                        TestService.taksometr_status = "Старт";
                        TestService.ojidaniya = false;
                        TestService.started = false;
                        TestService.paused = false;
                        TestService.gps_list.clear();
                        TestService.stopped = false;
                        if(taksometr.t!=null)
                            taksometr.t.finish();
                        break;
                    }
                    TestService.zakaz_min_summ = Integer.parseInt(split[7]);
                    TestService.zakaz_last_km = Integer.parseInt(split[8]);
                    TestService.zakaz_first_km_2 = Integer.parseInt(split[9]);
                    TestService.zakaz_out_km = Integer.parseInt(split[10]);
                    TestService.zakaz_wait_summ = Double.parseDouble(split[11]) / 60;
                    TestService.zakaz_free_wait = Integer.parseInt(split[12]) * 60;
                    return;
                case "zakaz_is_not_free":
                    Fragment3.clicked=false;
                    return;
                case "iron_check_no":
                    String[] finalSplit = split;
                    splashActivity.th.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(splashActivity.th).create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.setCanceledOnTouchOutside(false);
                            if (TestService.lang_is_uz) {
                                alertDialog.setTitle("Сиз блокландингиз!");
                                alertDialog.setMessage("Сабаб: " + finalSplit[1]);
                            } else if (TestService.lang_is_uz_lotin) {
                                alertDialog.setTitle("Siz bloklandingiz!");
                                alertDialog.setMessage("Sabab: " + finalSplit[1]);
                            } else {
                                alertDialog.setTitle("Вы заблокированы!");
                                alertDialog.setMessage("Причина: " + finalSplit[1]);
                            }
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ОК",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            TestService.APP_STOP = true;
                                            splashActivity.th.finish();
                                        }
                                    });
                            alertDialog.show();

                        }
                    });
                    return;

            }
        if(split.length>0)
            if(split[0].equals(""))
                System.out.println("COMM "+command);
    }

    public static void get()
    {
        Thread get_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BReader in = null;
                try {
                    in = new BReader(new InputStreamReader(connection.mSocket.getInputStream(), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String ru = "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
                while (!TestService.APP_STOP){
                    try {
                        String st = in.readLine();
                        if(st==null)
                            in = new BReader(new InputStreamReader(connection.mSocket.getInputStream(), StandardCharsets.UTF_8));
                        for(int i=ru.length()-1;i>-1;i--){
                            st = st.replaceAll("%"+i,ru.charAt(i)+"");
                        }
                        command(st);
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            in = new BReader(new InputStreamReader(connection.mSocket.getInputStream(), StandardCharsets.UTF_8));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        server.connect(true);
                    }
                }
                Taxi.is_connected=false;
            }
        });

        get_thread.start();



    }
    static boolean check(String[] apps){
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = splashActivity.th.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < pkgAppsList.size(); i++) {
            String appik = pkgAppsList.get(i) + "";
            for(String app : apps){
                if(!app.equals(""))
                if(appik.contains(app))
                    {
                        return true;
                    }

            }
        }

        return false;

    }
}
