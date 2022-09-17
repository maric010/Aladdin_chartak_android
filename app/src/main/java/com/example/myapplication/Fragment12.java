package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import org.apache.commons.io.FileUtils;


public class Fragment12 extends Fragment {
    static boolean closed_show=false;
    boolean stopik=false;
    boolean ok=false;
    int a,b;
    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    Ringtone r;
    String text;
    DrawerLayout drawer;

    private AppBarConfiguration mAppBarConfiguration;
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_12, container, false);


        if(TestService.theme){
            ConstraintLayout cos = root.findViewById(R.id.fr12);
            cos.setBackgroundResource(R.color.fr12);
            TextView textView4 = root.findViewById(R.id.textView4);
            textView4.setBackgroundResource(R.color.B0C4FB);
            textView4.setTextColor(Color.BLACK);
            TextView textView = root.findViewById(R.id.zakaz_info);
            textView.setBackgroundResource(R.color.B1AEAE);
            textView.setTextColor(Color.BLACK);
            Button textView1 = root.findViewById(R.id.button101);
            textView1.setTextColor(Color.parseColor("#16AA24"));
        }
        r = RingtoneManager.getRingtone(MainActivity.thisactivity, notification);
        r.play();
        closed_show=false;
        TextView textView = (TextView)root.findViewById(R.id.zakaz_info);
        textView.setText("Заказ id: "+TestService.zakaz_id+"\n🏁Откуда: "+TestService.zakaz_region+","+TestService.zakaz_orientir+"\nКласс: "+TestService.zakaz_class+"\n🕑Время регистрации: "+TestService.zakaz_reg_date.split(" ")[1]+"\nКуда: "+TestService.zakaz_kuda+"\nСтоимость: "+TestService.zakaz_cost+"\nКогда: "+TestService.zakaz_kogda);
        Button podtverdit= (Button)root.findViewById(R.id.button101);
        Button otkazatsa= (Button)root.findViewById(R.id.button91);


        return root;

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}