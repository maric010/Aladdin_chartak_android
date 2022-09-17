package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import static android.Manifest.permission.CALL_PHONE;
import static com.example.myapplication.TestService.fr3;
import static com.example.myapplication.TestService.zakazdop_id;

public class Fragment4 extends Fragment {
    static boolean closed_show=false;

    boolean ok=false;
    int a,b;
    MediaPlayer r;
    String text;
    DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_4, container, false);
/*

        drawer = MainActivity.thisactivity.findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


 */
        r= MediaPlayer.create(MainActivity.thisactivity, R.raw.budilnik);
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
        closed_show=false;
        TextView textView = (TextView)root.findViewById(R.id.zakaz_info);
        if(TestService.lang_is_uz)
            textView.setText("Буюртма id: "+TestService.zakaz_id+"\n🏁Манзил: "+TestService.zakaz_orientir+"\nТариф: "+TestService.zakaz_class);
        else if(TestService.lang_is_uz_lotin)
            textView.setText("Buyurtma id: "+TestService.zakaz_id+"\n🏁Manzil: "+TestService.zakaz_orientir+"\nTarif: "+TestService.zakaz_class);

        else
            textView.setText("Заказ id: "+TestService.zakaz_id+"\n🏁Ориентир: "+TestService.zakaz_orientir+"\nТариф: "+TestService.zakaz_class);
        Button podtverdit= (Button)root.findViewById(R.id.button10);
        Button otkazatsa= (Button)root.findViewById(R.id.button9);
        if(TestService.theme) {
            ConstraintLayout constraintLayout = root.findViewById(R.id.coslayout);
            constraintLayout.setBackgroundResource(R.color.cos);
            textView.setBackgroundResource(R.color.white);
            textView.setTextColor(Color.BLACK);
            TextView textView2 = root.findViewById(R.id.textView4);
            textView2.setBackgroundResource(R.color.fr4);
            textView2.setTextColor(Color.BLACK);
            podtverdit.setTextColor(R.color.button10);
        }
        if(TestService.lang_is_uz){
            podtverdit.setText("Ха");
            otkazatsa.setText("Йўқ");
        }
        podtverdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TestService._min = 25*60;
                ok=true;
                closed_show=true;


                if(!zakazdop_id.equalsIgnoreCase("")){
                    TestService.fr2=true;
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, new Fragment2());
                    ft.commit();
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

                    Dialog dialog = new Dialog(root.getContext());
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
                            MainActivity.thisactivity.runOnUiThread(new Runnable() {
                                public void run() {
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
                                }});
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
                                        startActivity(i);
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
                                            startActivity(dialogIntent);
                                            TestService.fr3 = false;
                                            TestService.fr4=false;
                                            TestService.fr2=true;
                                            MainActivity.thisactivity.fragment_switch(new Fragment2());
                                        }
                                    });
                                    dialog.cancel();
                                }});

                            for (a = TestService._min; a > 0; a -= 1) {
                                if(!dialog.isShowing() || TestService.zakaz_id.equalsIgnoreCase("")){
                                    TestService.do_start_ojidaniya = true;
                                    TestService._min=100000;
                                    break;
                                }

                                TestService._min = TestService._min-1;
                                b=TestService._min;
                                text=((b)/60)+"";
                                if(b/60<10){
                                    text = "0"+text;
                                }
                                if(b%60<10){
                                    text += ":0"+b%60;
                                }
                                else{
                                    text += ":"+b%60;
                                }

                                timer_text.post(new Runnable() {
                                    public void run() {
                                        timer_text.setText(text+"");
                                    }
                                });

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    // e.printStackTrace();
                                }
                            }

                            dialog.cancel();

                            if(TestService.zakaz_id.equalsIgnoreCase("")){
                                TestService.fr3 = false;
                                TestService.fr4=false;
                                TestService.fr2=true;
                                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.nav_host_fragment, new Fragment2());
                                ft.commit();
                            }

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
                            e.printStackTrace();
                        }

                    }
                }




            }
        });
        otkazatsa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closed_show=true;
                ok=true;
                if(!TestService.zakazdop_id.equalsIgnoreCase("")){
                    Taxi.decline_zakaz=true;
                    Taxi.decline_zakaz_id=TestService.zakazdop_id;
                    TestService.zakazdop_region = "";
                    TestService.zakazdop_orientir= "";
                    TestService.zakazdop_id="";
                    TestService.zakazdop_tel="";
                    TestService.zakazdop_reg_date="";
                    TestService.zakazdop_class="";
                    TestService.fr2=false;
                    TestService.fr4=false;
                    fr3=true;
                    MainActivity.thisactivity.fragment_switch(new Fragment3());
                }
                else{
                    Taxi.decline_zakaz=true;
                    Taxi.decline_zakaz_id=TestService.zakaz_id;

                    TestService.zakaz_region = "";
                    TestService.zakaz_orientir= "";
                    TestService.zakaz_id="";
                    TestService.zakaz_tel="";
                    TestService.zakaz_reg_date="";
                    TestService.zakaz_class="";
                    TestService.fr2=false;
                    TestService.fr4=false;
                    fr3=true;
                    MainActivity.thisactivity.fragment_switch(new Fragment3());
                }


            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProgressBar progressBar = root.findViewById(R.id.progressBar);
                for (int i = 100; i > -1; i -= 1) {
                    if (closed_show) {
                        r.stop();
                        break;
                    }

                    progressBar.setProgress(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                    if(i==0){
                        ok=true;
                        r.stop();
                        if(!zakazdop_id.equalsIgnoreCase(""))
                        {
                            Taxi.decline_zakaz=true;
                            Taxi.decline_zakaz_id=TestService.zakazdop_id;
                            TestService.zakazdop_region = "";
                            TestService.zakazdop_orientir= "";

                            TestService.zakazdop_tel="";
                            TestService.zakazdop_reg_date="";
                            TestService.zakazdop_class="";
                            TestService.zakazdop_id="";
                            TestService.fr3 = false;
                            TestService.fr4=false;
                            TestService.fr2=true;
                            MainActivity.thisactivity.fragment_switch(new Fragment2());
                        }
                        else{
                            Taxi.decline_zakaz=true;
                            Taxi.decline_zakaz_id=TestService.zakaz_id;
                            TestService.zakaz_region = "";
                            TestService.zakaz_orientir= "";

                            TestService.zakaz_tel="";
                            TestService.zakaz_reg_date="";
                            TestService.zakaz_class="";
                            TestService.zakaz_id="";
                            TestService.fr3 = false;
                            TestService.fr4=false;
                            TestService.fr2=true;
                            MainActivity.thisactivity.fragment_switch(new Fragment2());
                        }


                    }
                }
            }}).start();
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!ok){
            if(!TestService.zakazdop_id.equalsIgnoreCase("")) {
                Taxi.decline_zakaz=true;
                Taxi.decline_zakaz_id=TestService.zakazdop_id;
                TestService.zakazdop_id="";
                TestService.zakazdop_region = "";
                TestService.zakazdop_orientir= "";
                TestService.zakazdop_tel="";
                TestService.zakazdop_reg_date="";
                TestService.zakazdop_class="";
            }
            else if(!TestService.zakaz_id.equalsIgnoreCase("")) {
                Taxi.decline_zakaz=true;
                Taxi.decline_zakaz_id=TestService.zakaz_id;
                TestService.zakaz_id="";
                TestService.zakaz_region = "";
                TestService.zakaz_orientir= "";
                TestService.zakaz_tel="";
                TestService.zakaz_reg_date="";
                TestService.zakaz_class="";

            }
    }
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}