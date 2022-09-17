package com.example.myapplication;

import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import java.net.InetSocketAddress;
import java.net.Socket;

import static com.example.myapplication.connection.mSocket;

public class server {

    static boolean is_busy = false;
    static connection  mConnect  = null;

    static boolean is_connected=false;
    public static void connect(boolean wait)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("connected0");
                        InetSocketAddress sockAdr = new InetSocketAddress("188.120.251.80", 5555);
                        connection.mSocket = new Socket();
                        int timeout = 3000;
                        connection.mSocket.connect(sockAdr, timeout);
                        connection.mSocket.setSoTimeout(5000);
                        System.out.println("connected");
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        mConnect = null;
                        continue;
                    }
                }
            }
        });
        thread.start();
        if(wait){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        is_connected=false;
    }


}
