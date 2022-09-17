package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import static java.lang.Thread.sleep;

public class Fragment11 extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_11, container, false);

        if(TestService.lang_is_uz)
        {
            TextView sc = (TextView) root.findViewById(R.id.textView7);
            sc.setText("Хозирги пайтда олдиндан буюртмалар йўк");
            sc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else if(TestService.lang_is_uz_lotin)
        {
            TextView sc = (TextView) root.findViewById(R.id.textView7);
            sc.setText("Hozirgi paytda oldindan buyurtmalar yo'q");
            sc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        return root;
    }
}