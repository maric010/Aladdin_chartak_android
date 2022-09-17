package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;


public class Fragment5 extends Fragment {

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_5, container, false);

        Button day= (Button)root.findViewById(R.id.button_day);
        Button dark= (Button)root.findViewById(R.id.button_dark);

        Button ru = (Button)root.findViewById(R.id.button_lang_ru);
        Button uz= (Button)root.findViewById(R.id.button_lang_uz);
        Button uz_lotin= (Button)root.findViewById(R.id.button_lang_uz2);

        Button k3= (Button)root.findViewById(R.id.kl3);
        Button k4= (Button)root.findViewById(R.id.kl4);
        Button k5= (Button)root.findViewById(R.id.kl5);
        Button k6= (Button)root.findViewById(R.id.kl6);


        k3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                k3.setEnabled(false);
                k4.setEnabled(true);
                k5.setEnabled(true);
                k6.setEnabled(true);
                TestService.grid_column=3;
                Taxi.send_set_column="set_column|"+TestService.id+"|3";
            }
        });
        k4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                k3.setEnabled(true);
                k4.setEnabled(false);
                k5.setEnabled(true);
                k6.setEnabled(true);
                TestService.grid_column=4;
                Taxi.send_set_column="set_column|"+TestService.id+"|4";
            }
        });
        k5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                k3.setEnabled(true);
                k4.setEnabled(true);
                k5.setEnabled(false);
                k6.setEnabled(true);
                TestService.grid_column=5;
                Taxi.send_set_column="set_column|"+TestService.id+"|5";
            }
        });
        k6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                k3.setEnabled(true);
                k4.setEnabled(true);
                k5.setEnabled(true);
                k6.setEnabled(false);
                TestService.grid_column=6;
                Taxi.send_set_column="set_column|"+TestService.id+"|6";
            }
        });
        AppCompatButton button = root.findViewById(R.id.button13);
        if(TestService.theme) {
            day.setBackgroundResource(R.color.bg);
            dark.setBackgroundResource(R.color.bg);
            ru.setBackgroundResource(R.color.bg);
            uz.setBackgroundResource(R.color.bg);
            uz_lotin.setBackgroundResource(R.color.bg);

            ConstraintLayout cos = root.findViewById(R.id.nastroyka);
            cos.setBackgroundResource(R.color.nastroyka);
            TextView textView6 = root.findViewById(R.id.textView6);
            textView6.setTextColor(R.color.black);
            TextView textView5 = root.findViewById(R.id.textView5);
            textView5.setTextColor(R.color.black);
            uz.setTextColor(Color.WHITE);
            uz_lotin.setTextColor(Color.WHITE);
            ru.setTextColor(Color.WHITE);

            button.setBackgroundResource(R.color.button_13);
        }
        if(TestService.theme){
            day.setEnabled(false);
            dark.setEnabled(true);
        }
        else{
            day.setEnabled(true);
            dark.setEnabled(false);
        }
        if(TestService.lang_is_uz){
            button.setText("Тизим билдиришномалар созламаси");
            ru.setEnabled(true);
            uz.setEnabled(false);
            uz_lotin.setEnabled(true);
            day.setText("☀ Кун");
            dark.setText("\uD83C\uDF19 Тун");
            //ru.setText("Русча");
            //uz.setText("Кирилча");
            //uz_lotin.setText("Ўзбекча");
            TextView textView = root.findViewById(R.id.textView3);
            textView.setText("⚙ Созламалар");
        }
        else if(TestService.lang_is_uz_lotin){
            button.setText("Tizim bildirishnomalar sozlamasi");
            ru.setEnabled(true);
            uz.setEnabled(true);
            uz_lotin.setEnabled(false);
            day.setText("☀ Kun");
            dark.setText("\uD83C\uDF19 Tun");
            //ru.setText("Ruscha");
            //uz.setText("Kirilcha");
            //uz_lotin.setText("O'zbekcha");
            TextView textView = root.findViewById(R.id.textView3);
            textView.setText("⚙ Sozlamalar");
        }
        else{

            ru.setEnabled(false);
            uz.setEnabled(true);
        }

        ru.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ru.setEnabled(false);
                uz.setEnabled(true);
                uz_lotin.setEnabled(true);
                TestService.lang_is_uz=false;
                TestService.lang_is_uz_lotin=false;
                Taxi.send_set_language = "set_language|"+TestService.id+"|false";
                MainActivity.thisactivity.fragment_switch(new Fragment5());
                refresh_lang();
            }
        });

        uz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ru.setEnabled(true);
                uz.setEnabled(false);
                uz_lotin.setEnabled(true);
                TestService.lang_is_uz=true;
                TestService.lang_is_uz_lotin=false;
                Taxi.send_set_language = "set_language|"+TestService.id+"|true";
                MainActivity.thisactivity.fragment_switch(new Fragment5());
                refresh_lang();
            }
        });

        uz_lotin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ru.setEnabled(true);
                uz.setEnabled(true);
                uz_lotin.setEnabled(false);
                TestService.lang_is_uz=false;
                TestService.lang_is_uz_lotin=true;
                Taxi.send_set_lotin = "set_lotin|"+TestService.id+"|true";
                MainActivity.thisactivity.fragment_switch(new Fragment5());
                refresh_lang();
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                day.setEnabled(false);
                dark.setEnabled(true);
                TestService.theme=true;
                Taxi.send_set_theme = "set_theme|"+TestService.id+"|true";
                MainActivity.thisactivity.fragment_switch(new Fragment5());
            }
        });

        dark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                day.setEnabled(true);
                dark.setEnabled(false);
                TestService.theme=false;
                Taxi.send_set_theme = "set_theme|"+TestService.id+"|false";
                MainActivity.thisactivity.fragment_switch(new Fragment5());
            }
        });

        return root;

    }
@SuppressLint("SetTextI18n")
void refresh_lang(){
    NavigationView navigationView = MainActivity.thisactivity.findViewById(R.id.nav_view);

    View headerView = navigationView.getHeaderView(0);
    TextView balance = headerView.findViewById(R.id.textView);
    balance.setText("\uD83D\uDCB0"+TestService.balance);
    if(TestService.lang_is_uz) {
        ((TextView) headerView.findViewById(R.id.lang_sos)).setText("Хавф хатар");
        ((TextView) headerView.findViewById(R.id.lang_dispetcher)).setText("Диспетчерлар");
        ((TextView) headerView.findViewById(R.id.lang_regions)).setText("  \uD83D\uDDFA  Худудлар");
        ((TextView) headerView.findViewById(R.id.lang_pre_orders)).setText("  ⏰  Олдиндан\nбуюртмалар");
        ((TextView) headerView.findViewById(R.id.lang_sms)).setText("  \uD83D\uDCE8  Хабарлар");
        ((TextView) headerView.findViewById(R.id.lang_history)).setText("  \uD83D\uDCBC  Тарих");
        ((TextView) headerView.findViewById(R.id.cabinet)).setText("  \uD83D\uDC68\uD83C\uDFFB\u200D✈️  Шахсий кабинет");
        ((TextView) headerView.findViewById(R.id.lang_settings)).setText("  \uD83D\uDEE0️  Созламалар");
        ((TextView) headerView.findViewById(R.id.lang_exit2)).setText("  ⛔  Ишдан чиқиш");
    }
    else if(TestService.lang_is_uz_lotin) {
        ((TextView) headerView.findViewById(R.id.lang_sos)).setText("Xavf xatar");
        ((TextView) headerView.findViewById(R.id.lang_dispetcher)).setText("Dispetcherlar");
        ((TextView) headerView.findViewById(R.id.lang_regions)).setText("  \uD83D\uDDFA  Hududlar");
        ((TextView) headerView.findViewById(R.id.lang_pre_orders)).setText("  ⏰  Oldindan\nbuyurtmalar");
        ((TextView) headerView.findViewById(R.id.lang_sms)).setText("  \uD83D\uDCE8  Xabarlar");
        ((TextView) headerView.findViewById(R.id.lang_history)).setText("  \uD83D\uDCBC  Tarix");
        ((TextView) headerView.findViewById(R.id.cabinet)).setText("  \uD83D\uDC68\uD83C\uDFFB\u200D✈️  Shaxsiy kabinet");
        ((TextView) headerView.findViewById(R.id.lang_settings)).setText("  \uD83D\uDEE0️  Sozlamalar");
        ((TextView) headerView.findViewById(R.id.lang_exit2)).setText("  ⛔  Ishdan chiqish");
    }
    else{
        ((TextView) headerView.findViewById(R.id.lang_sos)).setText("Тревога");
        ((TextView) headerView.findViewById(R.id.lang_dispetcher)).setText("Диспетчеры");
        ((TextView) headerView.findViewById(R.id.lang_regions)).setText("  \uD83D\uDDFA  Районы");
        ((TextView) headerView.findViewById(R.id.lang_pre_orders)).setText("  ⏰  Пред.заказы");
        ((TextView) headerView.findViewById(R.id.lang_sms)).setText("  \uD83D\uDCE8  Сообщения");
        ((TextView) headerView.findViewById(R.id.lang_history)).setText("  \uD83D\uDCBC  История");
        ((TextView) headerView.findViewById(R.id.cabinet)).setText("  \uD83D\uDC68\uD83C\uDFFB\u200D✈️  Личный кабинет");
        ((TextView) headerView.findViewById(R.id.lang_settings)).setText("  \uD83D\uDEE0️  Настройки");
        ((TextView) headerView.findViewById(R.id.lang_exit2)).setText("  ⛔  Выйти");
    }
}
    @Override
    public void onStop() {
        super.onStop();

    }
}