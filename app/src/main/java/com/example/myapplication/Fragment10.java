package com.example.myapplication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceConfig;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Locale;


public class Fragment10 extends Fragment {
    @SuppressLint("SetTextI18n")
    String ot_date="";
    String do_date="";
    String ot_time="00:00";
    String do_time="00:00";
    int MIN=0;
    int MAX=50;
    long a,b;
    String zakaz_id="";
    String status="";
    static int all_count=0;
    static View root;
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_10, container, false);

        Button button = root.findViewById(R.id.button3);
        Button get_zakaz = root.findViewById(R.id.get_zakaz);
        Button get_otkaz = root.findViewById(R.id.get_otkaz);
        Button get_balance = root.findViewById(R.id.get_balance);

        if(TestService.theme){
            TextView itog = root.findViewById(R.id.itog);
            itog.setTextColor(Color.BLACK);
        }

        if(TestService.lang_is_uz){
            get_zakaz.setText("Буюртмалар");
            get_otkaz.setText("Бекор қилинганлар");
            get_balance.setText("Маблағ");
        }
        else if(TestService.lang_is_uz_lotin){
            get_zakaz.setText("Buyurtmalar");
            get_otkaz.setText("Bekor qilinganlar");
            get_balance.setText("Mablag'");
        }

        Button back = root.findViewById(R.id.back);
        Button next = root.findViewById(R.id.next);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(MIN>0){
                    MIN-=50;
                    MAX-=50;
                    if(status.equalsIgnoreCase("zakaz")){
                         Taxi.history_text = "get_zakaz|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                                    Taxi.history = true;
                    }
                    else if(status.equalsIgnoreCase("otkaz")){
                        Taxi.history_text="get_otkaz|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                        Taxi.history = true;
                    }
                    else if(status.equalsIgnoreCase("balance")){
                        Taxi.history_text = "get_balance|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                        Taxi.history = true;
                    }

                }

            }});
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(MAX>all_count){
                    MIN+=50;
                    MAX+=50;
                    if(status.equalsIgnoreCase("zakaz")){
                                    Taxi.history_text = "get_zakaz|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                                    Taxi.history = true;

                    }
                    else if(status.equalsIgnoreCase("otkaz")){
                        Taxi.history_text = "get_otkaz|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                        Taxi.history = true;
                    }
                    else if(status.equalsIgnoreCase("balance")){
                        Taxi.history_text = "get_balance|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                        Taxi.history = true;
                    }

                }

            }});
        get_zakaz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MIN=0;
                MAX=50;
                status="zakaz";
                  Taxi.history_text = "get_zakaz|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                  Taxi.history = true;

            }});
        get_otkaz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MIN=0;
                MAX=50;
                status="otkaz";
                Taxi.history_text = "get_otkaz|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                Taxi.history = true;
                }});
        get_balance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MIN=0;
                MAX=50;
                status="balance";
                Taxi.history_text = "get_balance|" + TestService.id+ "|" + TestService.RAYON+"|"+ot_date+" "+ot_time+"|"+do_date+" "+do_time+"|"+MIN+"|"+MAX;
                Taxi.history = true;
            }});
        TextView picked_date= root.findViewById(R.id.picked_date);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_date);

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                TextView time_ot = dialog.findViewById(R.id.time_ot);
                TextView time_do = dialog.findViewById(R.id.time_do);
                time_ot.setText(ot_time);
                time_do.setText(do_time);
                CalendarView calendarView_ot = dialog.findViewById(R.id.calendarView_ot);
                CalendarView calendarView_do = dialog.findViewById(R.id.calendarView_do);
                calendarView_ot.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year,
                                                    int month, int dayOfMonth) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date date = sdf.parse(dayOfMonth+"-"+(month+1)+"-"+year);
                            calendarView_ot.setDate(date.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                calendarView_do.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year,
                                                    int month, int dayOfMonth) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date date = sdf.parse(dayOfMonth+"-"+(month+1)+"-"+year);
                            calendarView_do.setDate(date.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                calendarView_ot.setDate(a);
                calendarView_do.setDate(b);
                Date currentTime = Calendar.getInstance().getTime();
                Button today_date = dialog.findViewById(R.id.today_date);
                Button yesterday_date = dialog.findViewById(R.id.yesterday_date);
                today_date.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(View v) {
                        calendarView_ot.setDate(currentTime.getTime());
                        calendarView_do.setDate(currentTime.getTime());
                        time_ot.setText("00:00");
                        time_do.setText("23:59");
                    }});
                yesterday_date.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(View v) {
                        calendarView_ot.setDate(currentTime.getTime()-86400000);
                        calendarView_do.setDate(currentTime.getTime()-86400000);
                        time_ot.setText("00:00");
                        time_do.setText("23:59");
                    }});
                Button save_button = dialog.findViewById(R.id.save_date_history);
                save_button.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(View v) {
                        ot_time=time_ot.getText().toString();
                        do_time=time_do.getText().toString();
                        ot_date = DateFormat.format("yyyy-MM-dd", new Date(calendarView_ot.getDate())).toString();
                        a=calendarView_ot.getDate();
                        System.out.println("a="+a);
                        do_date = DateFormat.format("yyyy-MM-dd", new Date(calendarView_do.getDate())).toString();
                        b=calendarView_do.getDate();
                        picked_date.setText(ot_date+" - "+do_date+"\n"+ot_time+" - "+do_time);
                        dialog.cancel();
                    }});
            }});



        if(TestService.theme){
            ConstraintLayout constraintLayout = root.findViewById(R.id.history);
            constraintLayout.setBackgroundResource(R.color.white);
            TextView textView = root.findViewById(R.id.lang_history2);
            textView.setTextColor(Color.BLACK);
        }
        if(TestService.lang_is_uz){
            TextView textView = root.findViewById(R.id.lang_history2);
            textView.setText("Тарих");
        }
        else if(TestService.lang_is_uz_lotin){
            TextView textView = root.findViewById(R.id.lang_history2);
            textView.setText("Tarix");
        }

        return root;

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}