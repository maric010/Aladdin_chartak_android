package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.apache.commons.io.FileUtils;


public class Fragment8 extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_8, container, false);

        TextView id = (TextView)root.findViewById(R.id.kabinet_id);
        TextView name = (TextView)root.findViewById(R.id.name_and_avto);
        TextView date = (TextView)root.findViewById(R.id.kabinet_data_reg);
        TextView cl = (TextView)root.findViewById(R.id.kabinet_class);
        TextView company = (TextView)root.findViewById(R.id.kabinet_company);
        TextView gorod = (TextView)root.findViewById(R.id.kabinet_gorod);
        TextView tarif = root.findViewById(R.id.kabinet_tarif);
        TextView tel = root.findViewById(R.id.kabinet_tel);

        if(TestService.lang_is_uz){
            gorod.setText("Шахар:");
            date.setText("Рўйхатдан ўтган санаси:");
        }
        else if(TestService.lang_is_uz_lotin){
            gorod.setText("Shahar:");
            date.setText("Ro'yxatdan o'tgan sanasi:");
        }
        gorod.setText(gorod.getText()+" "+TestService.RAYON);
        company.setText(company.getText()+" "+TestService.company);
        id.setText(id.getText()+" "+TestService.id);
        name.setText(name.getText()+" "+TestService.name+"\n"+TestService.avto);
        date.setText(date.getText()+" "+TestService.date);
        cl.setText(cl.getText()+" "+TestService.avto_class);
        tel.setText(tel.getText()+" "+TestService.tel);
        if(Integer.parseInt(TestService.tarif)>0 && Integer.parseInt(TestService.tarif)<100) {
            tarif.setText(tarif.getText()+" "+TestService.tarif+"%");
        }
        else
            tarif.setText(tarif.getText()+" "+TestService.tarif+"сўм");
        if(TestService.theme) {
            ConstraintLayout cos = root.findViewById(R.id.lccos);
            cos.setBackgroundResource(R.color.lccos);
            id.setTextColor(Color.BLACK);
            cl.setTextColor(Color.BLACK);
            gorod.setTextColor(Color.BLACK);
            tarif.setTextColor(Color.BLACK);
            tel.setTextColor(Color.BLACK);
            company.setTextColor(Color.BLACK);
        }
        return root;

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}