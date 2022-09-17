package com.example.myapplication;


import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class connection {
    static OutputStream sender = null;
    static InputStream input = null;
    static Socket mSocket = null;
    static  String  mHost   = null;
    static  int     mPort   = 0;
    public static final String LOG_TAG = "SOCKET";
    public connection(String s, int i) {
        this.mHost = s;
        this.mPort = i;
    }
    static public void openConnection() throws Exception
    {
        try {
            mSocket = new Socket(mHost, mPort);
        } catch (IOException e) {
            throw new Exception("Невозможно создать сокет: "+e.getMessage());
        }
    }
    /**
     * Метод закрытия сокета
     */
    static public void closeConnection()
    {
        /* Проверяем сокет. Если он не зарыт, то закрываем его и освобдождаем соединение.*/
        if (mSocket != null && !mSocket.isClosed()) {
            try {
                mSocket.close();
                System.out.println("MSOCKET CLOSED");
            } catch (IOException e) {
                Log.e(LOG_TAG, "Невозможно закрыть сокет: " + e.getMessage());
            } finally {
                //mSocket = null;
                //System.out.println("MSOCKET CLOSED");
            }
        }
        mSocket = null;
    }
    /**
     * Метод отправки данных
     */
    static String sendData(byte[] data){
        while (true)
        {
            try {
                sender =  mSocket.getOutputStream();
                sender.write(data);
                sender.flush();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                server.connect(true);
                //refresh();
                continue;
            }
        }

        return "";
    }
    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
    }

}