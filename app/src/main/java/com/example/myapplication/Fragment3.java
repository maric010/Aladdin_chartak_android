package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.myapplication.TestService.RAYON;
import static java.lang.Thread.sleep;


public class Fragment3 extends Fragment {
static boolean stopik;
static int count=0;
int click_count=0;
    static int zakazs[] ={R.id.zakaz1,R.id.zakaz2,R.id.zakaz3,
            R.id.zakaz4,R.id.zakaz5,R.id.zakaz6,R.id.zakaz7,R.id.zakaz8,R.id.zakaz9,
            R.id.zakaz10,R.id.zakaz11,R.id.zakaz12,R.id.zakaz13,R.id.zakaz14
            ,R.id.zakaz15,R.id.zakaz16,R.id.zakaz17,R.id.zakaz18,R.id.zakaz19,
            R.id.zakaz20,R.id.zakaz21,R.id.zakaz22,R.id.zakaz23,R.id.zakaz24,
            R.id.zakaz25,R.id.zakaz26,R.id.zakaz27,R.id.zakaz28,R.id.zakaz29,
            R.id.zakaz30};
    static int zakazs_orientir[] ={R.id.zakaz1_orientir,R.id.zakaz2_orientir,R.id.zakaz3_orientir,
            R.id.zakaz4_orientir,R.id.zakaz5_orientir,R.id.zakaz6_orientir,R.id.zakaz7_orientir,R.id.zakaz8_orientir,R.id.zakaz9_orientir,
            R.id.zakaz10_orientir,R.id.zakaz11_orientir,R.id.zakaz12_orientir,R.id.zakaz13_orientir,R.id.zakaz14_orientir
            ,R.id.zakaz15_orientir,R.id.zakaz16_orientir,R.id.zakaz17_orientir,R.id.zakaz18_orientir,R.id.zakaz19_orientir,
            R.id.zakaz20_orientir,R.id.zakaz21_orientir,R.id.zakaz22_orientir,R.id.zakaz23_orientir,R.id.zakaz24_orientir,
            R.id.zakaz25_orientir,R.id.zakaz26_orientir,R.id.zakaz27_orientir,R.id.zakaz28_orientir,R.id.zakaz29_orientir,
            R.id.zakaz30_orientir};
    static int zakazs_name[] ={R.id.zakaz1_name,R.id.zakaz2_name,R.id.zakaz3_name,
            R.id.zakaz4_name,R.id.zakaz5_name,R.id.zakaz6_name,R.id.zakaz7_name,R.id.zakaz8_name,R.id.zakaz9_name,
            R.id.zakaz10_name,R.id.zakaz11_name,R.id.zakaz12_name,R.id.zakaz13_name,R.id.zakaz14_name
            ,R.id.zakaz15_name,R.id.zakaz16_name,R.id.zakaz17_name,R.id.zakaz18_name,R.id.zakaz19_name,
            R.id.zakaz20_name,R.id.zakaz21_name,R.id.zakaz22_name,R.id.zakaz23_name,R.id.zakaz24_name,
            R.id.zakaz25_name,R.id.zakaz26_name,R.id.zakaz27_name,R.id.zakaz28_name,R.id.zakaz29_name,
            R.id.zakaz30_name};
    static int zakazs_note[] ={R.id.zakaz1_note,R.id.zakaz2_note,R.id.zakaz3_note,
            R.id.zakaz4_note,R.id.zakaz5_note,R.id.zakaz6_note,R.id.zakaz7_note,R.id.zakaz8_note,R.id.zakaz9_note,
            R.id.zakaz10_note,R.id.zakaz11_note,R.id.zakaz12_note,R.id.zakaz13_note,R.id.zakaz14_note
            ,R.id.zakaz15_note,R.id.zakaz16_note,R.id.zakaz17_note,R.id.zakaz18_note,R.id.zakaz19_note,
            R.id.zakaz20_note,R.id.zakaz21_note,R.id.zakaz22_note,R.id.zakaz23_note,R.id.zakaz24_note,
            R.id.zakaz25_note,R.id.zakaz26_note,R.id.zakaz27_note,R.id.zakaz28_note,R.id.zakaz29_note,
            R.id.zakaz30_note};
    static int zakazs_class[] ={R.id.zakaz1_class,R.id.zakaz2_class,R.id.zakaz3_class,
            R.id.zakaz4_class,R.id.zakaz5_class,R.id.zakaz6_class,R.id.zakaz7_class,R.id.zakaz8_class,R.id.zakaz9_class,
            R.id.zakaz10_class,R.id.zakaz11_class,R.id.zakaz12_class,R.id.zakaz13_class,R.id.zakaz14_class
            ,R.id.zakaz15_class,R.id.zakaz16_class,R.id.zakaz17_class,R.id.zakaz18_class,R.id.zakaz19_class,
            R.id.zakaz20_class,R.id.zakaz21_class,R.id.zakaz22_class,R.id.zakaz23_class,R.id.zakaz24_class,
            R.id.zakaz25_class,R.id.zakaz26_class,R.id.zakaz27_class,R.id.zakaz28_class,R.id.zakaz29_class,
            R.id.zakaz30_class};
    static boolean clicked=false;
    String x="";
    String y="";
    long timek=0;
    static View root;
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_3, container, false);
        click_count=0;
        count=0;
        clicked=false;
        uje_bil="";
        ScrollView scrollView = root.findViewById(R.id.scroll);
        LinearLayout zakaz_layout = root.findViewById(R.id.zakaz_layout);
        if(TestService.theme) {


            scrollView.setBackgroundResource(R.color.scroll);
            zakaz_layout.setBackgroundResource(R.color.scroll);
        }
        TextView sc = (TextView) root.findViewById(R.id.textView7);
        sc.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                x=event.getX()+":"+event.getY();
                if(!x.equalsIgnoreCase(y)){
                    y=x;
                    x="";
                }
                if(timek==0){
                    timek=System.currentTimeMillis();
                    click_count=1;
                }
                else{
                    if(x.equalsIgnoreCase(y) || click_count>4){
                        click_count=-100;
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.thisactivity).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);
                        if(TestService.lang_is_uz){
                            alertDialog.setTitle("Автокликер детектори.");
                            alertDialog.setMessage("Автокликер тақиқланган!");
                        }
                        else{
                            alertDialog.setTitle("Автокликер детектор.");
                            alertDialog.setMessage("Автокликер запрещается!");
                        }
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        TestService.fr3=false;
                        TestService.fr4=false;
                        TestService.fr2=true;
                        MainActivity.thisactivity.fragment_switch(new Fragment2());
                    }

                    else if(System.currentTimeMillis()-timek>1000)
                    {
                        timek = System.currentTimeMillis();
                        click_count=1;
                    }
                    else{
                        click_count+=1;
                    }

                }

            }
            return false;
        });
        if(TestService.theme)
            sc.setTextColor(Color.BLACK);
uje_bil="uje_bil";
if(Taxi.orders_from_server!=null)
    refresh();
        return root;
    }
    static String uje_bil="";
    static void refresh(){
    try {
        if(root==null)
            return;
        if(Integer.parseInt(TestService.balance)<750){
            return;
        }
        String s = Taxi.orders_from_server;
        //System.out.println(s);
        if(s.equals(uje_bil))
        {
            return;
        }
        uje_bil=Taxi.orders_from_server;
        String[] zakazi = s.split(";");
        LinearLayout linearLayout2 = root.findViewById(zakazs[0]);
        linearLayout2.post(new Runnable() {
            @SuppressLint({"SetTextI18n", "ResourceAsColor"})
            public void run() {
                System.out.println("ZAKAZI LENGHT "+zakazi.length+"|   "+count);
                if(zakazi.length>0  && !zakazi[0].contains("not_found")){
                    TextView sc = (TextView) root.findViewById(R.id.textView7);
                    sc.setVisibility(View.GONE);
                    if(count<zakazi.length){
                        MediaPlayer mPlayer= MediaPlayer.create(MainActivity.thisactivity, R.raw.zakaz);
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
                    count=zakazi.length;

                }
                else{
                    TextView sc = root.findViewById(R.id.textView7);
                    if(TestService.theme)
                        sc.setTextColor(R.color.black);
                    if(TestService.lang_is_uz)
                        sc.setText("Хозирги пайтда буюртмалар йўк");
                    else if(TestService.lang_is_uz_lotin)
                        sc.setText("Hozirgi paytda buyurtmalar yo'q");
                    sc.setVisibility(View.VISIBLE);
                    count=zakazi.length;
                    for (int i = 0; i <30; i++) {
                        LinearLayout linearLayout = (LinearLayout) root.findViewById(zakazs[i]);
                        linearLayout.setVisibility(View.GONE);
                    }
                    return;
                }
                for (int i = 0; i < zakazi.length; i++) {
                    if(i>29)
                        break;
                    String[] split = zakazi[i].split("/");
                    System.out.println(zakazi[i]);
                    LinearLayout linearLayout = (LinearLayout) root.findViewById(zakazs[i]);
                    TextView zakaz_orientir = (TextView) root.findViewById(zakazs_orientir[i]);
                    TextView zakaz_name = (TextView) root.findViewById(zakazs_name[i]);
                    TextView zakaz_note = (TextView) root.findViewById(zakazs_note[i]);
                    TextView zakaz_class = (TextView) root.findViewById(zakazs_class[i]);
                    if (TestService.theme){
                        linearLayout.setBackgroundResource(R.color.zakaz);
                        zakaz_orientir.setTextColor(Color.BLACK);
                        zakaz_name.setTextColor(Color.BLACK);
                        zakaz_note.setTextColor(Color.BLACK);
                        zakaz_class.setTextColor(Color.BLACK);
                    }
                    linearLayout.setVisibility(View.VISIBLE);

                    zakaz_orientir.setText(split[1]);

                    zakaz_class.setText(split[5].replace("None",""));
                    if(split.length>6)
                        zakaz_note.setText(split[6].replace("None",""));
                    else
                        zakaz_note.setText("");
                    //if(split.length>9)
                    //    zakaz_name.setText(split[9].replace("None",""));
                    //else
                    zakaz_name.setText("");
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if(!clicked){
                                clicked=true;
                                try {
                                    Taxi.split=split;
                                    Taxi.zakaz_is_free_id=split[2];
                                    Taxi.zakaz_is_free=true;
                                } catch (Exception e) {
                                    clicked=false;
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                }
                for (int i = zakazi.length; i <30; i++) {
                    LinearLayout linearLayout = (LinearLayout) root.findViewById(zakazs[i]);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });


    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopik=true;
        clicked=false;
    }


}