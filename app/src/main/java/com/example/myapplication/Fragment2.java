package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.navigation.NavigationView;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

import static com.example.myapplication.TestService.RAYON;
import static java.lang.Thread.sleep;


public class Fragment2 extends Fragment {
    static boolean stopik = false;
    static boolean kaplya=false;
    static boolean gps_is_on=false;
    String text="";
    static TextView balans;
    static TextView gps;
    static View root;
    static int width,height;
    static int[] region_layouts ={R.id.region1,R.id.region2,R.id.region3,
            R.id.region4,R.id.region5,R.id.region6,
            R.id.region7,R.id.region8,R.id.region9,
            R.id.region10,R.id.region11,R.id.region12,
            R.id.region13,R.id.region14,R.id.region15,
            R.id.region16,R.id.region17,R.id.region18,
            R.id.region19,R.id.region20,R.id.region21,
            R.id.region22,R.id.region23,R.id.region24,
            R.id.region25,R.id.region26,R.id.region27,
            R.id.region28,R.id.region29,R.id.region30,
            R.id.region31,R.id.region32,R.id.region33,
            R.id.region34,R.id.region35,R.id.region36,
            R.id.region37,R.id.region38,R.id.region39,
            R.id.region40,R.id.region41,R.id.region42,
            R.id.region43,R.id.region44,R.id.region45,
            R.id.region46,R.id.region47,R.id.region48,
            R.id.region49,R.id.region50,R.id.region51};

    static int[] region_name ={R.id.region1_name,R.id.region2_name,R.id.region3_name,
            R.id.region4_name,R.id.region5_name,R.id.region6_name,
            R.id.region7_name,R.id.region8_name,R.id.region9_name,
            R.id.region10_name,R.id.region11_name,R.id.region12_name,
            R.id.region13_name,R.id.region14_name,R.id.region15_name,
            R.id.region16_name,R.id.region17_name,R.id.region18_name,
            R.id.region19_name,R.id.region20_name,R.id.region21_name,
            R.id.region22_name,R.id.region23_name,R.id.region24_name,
            R.id.region25_name,R.id.region26_name,R.id.region27_name,
            R.id.region28_name,R.id.region29_name,R.id.region30_name,
            R.id.region31_name,R.id.region32_name,R.id.region33_name,
            R.id.region34_name,R.id.region35_name,R.id.region36_name,
            R.id.region37_name,R.id.region38_name,R.id.region39_name,
            R.id.region40_name,R.id.region41_name,R.id.region42_name,
            R.id.region43_name,R.id.region44_name,R.id.region45_name,
            R.id.region46_name,R.id.region47_name,R.id.region48_name,
            R.id.region49_name,R.id.region50_name,R.id.region51_name};

    static int[] region_count ={R.id.region1_count,R.id.region2_count,R.id.region3_count,
            R.id.region4_count,R.id.region5_count,R.id.region6_count,
            R.id.region7_count,R.id.region8_count,R.id.region9_count,
            R.id.region10_count,R.id.region11_count,R.id.region12_count,
            R.id.region13_count,R.id.region14_count,R.id.region15_count,
            R.id.region16_count,R.id.region17_count,R.id.region18_count,
            R.id.region19_count,R.id.region20_count,R.id.region21_count,
            R.id.region22_count,R.id.region23_count,R.id.region24_count,
            R.id.region25_count,R.id.region26_count,R.id.region27_count,
            R.id.region28_count,R.id.region29_count,R.id.region30_count,
            R.id.region31_count,R.id.region32_count,R.id.region33_count,
            R.id.region34_count,R.id.region35_count,R.id.region36_count,
            R.id.region37_count,R.id.region38_count,R.id.region39_count,
            R.id.region40_count,R.id.region41_count,R.id.region42_count,
            R.id.region43_count,R.id.region44_count,R.id.region45_count,
            R.id.region46_count,R.id.region47_count,R.id.region48_count,
            R.id.region49_count,R.id.region50_count,R.id.region51_count};
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_2, container, false);

        balans = root.findViewById(R.id.textbalans);
        gps = root.findViewById(R.id.imageView);
        Taxi.orders_from_server=null;
        Display display = MainActivity.thisactivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


        if(TestService.theme) {
            ImageButton imageButton = root.findViewById(R.id.imageButton);
            imageButton.setBackgroundResource(R.color.imgb);
        }
        GridLayout gridLayout = root.findViewById(R.id.grid);
        gridLayout.setColumnCount(TestService.grid_column);
System.out.println("fr2");
        uje_bil="";
        if(Taxi.rayons_from_server!=null)
            refresh();
        return root;

    }
static String uje_bil="";
static void refresh(){

    try {


        if(Integer.parseInt(TestService.balance)<750){
            if(balans.getVisibility()==View.GONE) {
                balans.post(new Runnable() {
                    public void run() {
                        if(TestService.lang_is_uz)
                            balans.setText("Илтимос хисобингизни тўлдиринг");
                        else if(TestService.lang_is_uz_lotin)
                            balans.setText("Iltimos hisobingizni to'ldiring");
                        else
                            balans.setText("Пожалуйста пополните свой баланс");

                        balans.setVisibility(View.VISIBLE);
                    }});
            }
            return;
        }

        else{
            if(balans.getVisibility()!=View.GONE){
                balans.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        balans.setVisibility(View.GONE);
                    }});
            }

        }

        String s_temp1 = Taxi.rayons_from_server;

        if(s_temp1.equals(uje_bil))
        {
            return;
        }
        uje_bil=s_temp1;
        String s =s_temp1;
        System.out.println(s.length());

        String[] zakazi = s.split(";");
        if(zakazi.length>0){
            for (int i=0;i<zakazi.length;i++) {
                //Thread.sleep(10);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                if(TestService.grid_column==3){
                    params.width=width/3 - 30;//220
                    params.height=height/5 - 60;
                    params.setMargins(10, 10, 10, 10);
                }
                if(TestService.grid_column==4){

                    params.width=width/4 - 40;//160
                    params.height=height/8 - 30;
                    params.setMargins(10, 10, 10, 10);
                }
                if(TestService.grid_column==5){
                    params.width=width/5 - 30;//160
                    params.height=height/10 - 10;
                    params.setMargins(10, 10, 10, 10);
                }
                if(TestService.grid_column==6){
                    params.width=width/6 - 20;//160
                    params.height=height/12+10;
                    params.setMargins(6, 10, 6, 10);

                }
                String[] split = zakazi[i].split("/");
                if(split.length==3){
                    LinearLayout linearLayout = root.findViewById(region_layouts[i]);
                    TextView region_text = root.findViewById(region_name[i]);
                    TextView count = root.findViewById(region_count[i]);
                    if(TestService.grid_column==3) {
                        region_text.setTextSize(18);
                        count.setTextSize(18);
                    }
                    else if(TestService.grid_column==4){
                        region_text.setTextSize(14);
                        count.setTextSize(14);
                    }
                    else if(TestService.grid_column==5){
                        region_text.setTextSize(12);
                        count.setTextSize(12);
                    }
                    else if(TestService.grid_column==6){
                        region_text.setTextSize(10);
                        count.setTextSize(10);
                    }
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        public void run() {
                            linearLayout.setLayoutParams(params);
                        }
                    });
                    if(linearLayout.getVisibility()!=View.VISIBLE)
                    {
                        MainActivity.thisactivity.runOnUiThread(new Runnable() {
                            public void run() {
                                linearLayout.setVisibility(View.VISIBLE);
                            }});
                    }
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        public void run() {
                            region_text.setText(""+split[0]+"");
                            count.setText(split[1] + "/" + split[2]+"\n");
                        }});

                    if (!split[1].equals("0")) {
                        if(region_text.getCurrentTextColor()!=Color.BLACK){
                            kaplya=true;
                        }
                        MainActivity.thisactivity.runOnUiThread(new Runnable() {
                            public void run() {
                                linearLayout.setBackgroundColor(Color.YELLOW);
                                region_text.setTextColor(Color.BLACK);
                                count.setTextColor(Color.BLACK); }});
                    }
                    else{
                        if(TestService.theme) {
                            MainActivity.thisactivity.runOnUiThread(new Runnable() {
                                public void run() {
                                    linearLayout.setBackgroundResource(R.color.purple_200);
                                    region_text.setTextColor(Color.BLACK);
                                    count.setTextColor(Color.BLACK);
                                }
                            });
                        }
                        else{
                            MainActivity.thisactivity.runOnUiThread(new Runnable() {
                                public void run() {
                                    linearLayout.setBackgroundColor(Color.parseColor("#3C4C5B"));
                                    region_text.setTextColor(Color.WHITE);
                                    count.setTextColor(Color.WHITE);
                                }
                            });

                        }
                    }
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            linearLayout.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if (TestService.zakaz_id.equalsIgnoreCase("") || (TestService.zakazdop_id.equalsIgnoreCase("")) && TestService.started) {
                                        Taxi.orders_from_server = null;
                                        TestService.region = split[0];

                                        Taxi.get_orders_from_server = true;
                                        stopik = true;

                                        TestService.fr2 = false;
                                        TestService.fr4=false;
                                        TestService.fr3 = true;
                                        MainActivity.thisactivity.fragment_switch(new Fragment3());
                                    } else {
                                        if (TestService.lang_is_uz)
                                            ((Toast) Toast.makeText(root.getContext(), "Аввал буюртмани якунланг!", Toast.LENGTH_SHORT)).show();
                                        else if (TestService.lang_is_uz_lotin)
                                            ((Toast) Toast.makeText(root.getContext(), "Avval buyurtmani yakunlang!", Toast.LENGTH_SHORT)).show();
                                        else
                                            ((Toast) Toast.makeText(root.getContext(), "Сначало завершите существующий заказ!", Toast.LENGTH_SHORT)).show();

                                    }
                                }
                            });

                        }
                    });
                }

            }

            if(kaplya && TestService.zakaz_id.equals("")){
                MediaPlayer mPlayer= MediaPlayer.create(MainActivity.thisactivity, R.raw.kapli);
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
                kaplya=false;
            }

            for (int i = zakazi.length; i <42; i++) {
                LinearLayout linearLayout = root.findViewById(region_layouts[i]);
                if(linearLayout.getVisibility()!=View.GONE)
                {
                    MainActivity.thisactivity.runOnUiThread(new Runnable() {
                        public void run() {
                            linearLayout.setVisibility(View.GONE);
                        }
                    });
                }

            }

        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopik=true;
    }




}