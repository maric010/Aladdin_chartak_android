package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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


public class Fragment9 extends Fragment {
    static boolean stopik;
    EditText editText;
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_9, container, false);

        editText = root.findViewById(R.id.editTextTextMultiLine);
        EditText editText1 = root.findViewById(R.id.editTextTextPersonName);
        ImageButton button = root.findViewById(R.id.button15);

        TextView textView = root.findViewById(R.id.lang_sms2);
        if(TestService.theme){
            ConstraintLayout cos = root.findViewById(R.id.fr9);
            cos.setBackgroundResource(R.color.fr9);
            editText1.setTextColor(Color.BLACK);
            editText.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.parseColor("#B0C4FB"));
            textView.setTextColor(Color.BLACK);

        }
        if(TestService.lang_is_uz){
         textView.setText("Хабарлар");
        }
        else if(TestService.lang_is_uz_lotin){
            textView.setText("Xabarlar");
        }


        return root;

    }

    @Override
    public void onStop() {
        super.onStop();
        stopik=true;

    }

}