package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
public class taxometrService extends Service {
    public taxometrService() {
    }



    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    public void someTask() {
        System.out.println("GPS_START");
        if(!TestService.gps_started) {
            TestService.gps_started = true;
            if(TestService.last_summa==0 && TestService.zakaz_uchtaxi){
                TestService.last_summa=TestService.zakaz_min_summ;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    double km, distance;
                    km = TestService.last_km;

                    if(TestService.RAYON.equals("Шахрисабз_Китоб")){
                        if (km<1 && !TestService.zakaz_uchtaxi)
                            TestService.taksometr_summa = (TestService.zakaz_first_km_2*km) + TestService.last_ojidaniya_summa;
                        else
                            TestService.taksometr_summa = TestService.last_summa;
                    }
                    else if(TestService.RAYON.equals("Мингбулок") || TestService.RAYON.equals("Чартак")){
                        if (km<1.5 && !TestService.zakaz_uchtaxi)
                            TestService.taksometr_summa = (TestService.zakaz_first_km_2*km) + TestService.last_ojidaniya_summa;
                        else
                            TestService.taksometr_summa = TestService.last_summa;

                    }
                    else{
                        if (km<2 && !TestService.zakaz_uchtaxi)
                            TestService.taksometr_summa = (TestService.zakaz_first_km_2*km) + TestService.last_ojidaniya_summa;
                        else
                            TestService.taksometr_summa = TestService.last_summa;

                    }
                    System.out.println("TAKSOMETR KM "+km);
                    System.out.println("TAKSOMETR SUMMA "+TestService.taksometr_summa);
                    while (!TestService.zakaz_id.equalsIgnoreCase("") && !TestService.stopped) {
                        try {
                            System.out.println("Taxometr robit");
                            Thread.sleep(1000);
                            if (TestService.paused) {
                                continue;
                            }
                            if (TestService.do_start_ojidaniya) {
                                TestService.taksometr_ojidaniya_do_starta += 1;
                            }
                            if (TestService.ojidaniya) {
                                if(!TestService.taksometr_dostavka)
                                    TestService.taksometr_ojidaniya += 1;
                            }
                            if (TestService.started) {

                                System.out.println(TestService.RAYON+" test");
                                if (TestService.loc != null && TestService.loc2 != null && !TestService.ojidaniya) {
                                    distance = (TestService.loc.distanceTo(TestService.loc2));
                                    if (distance > 50) {
                                        if(TestService.RAYON.equals("Шахрисабз_Китоб")) {
                                            if (km < 1 && !TestService.zakaz_uchtaxi) {
                                                if (km + (distance / 1000) > 1) {
                                                    TestService.taksometr_summa += (km + (distance / 1000) - 1) * TestService.zakaz_last_km;
                                                    TestService.taksometr_summa += (1 - km) * TestService.zakaz_first_km_2;
                                                } else
                                                    TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_first_km_2;
                                            } else if (TestService.in_rayon)
                                                TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_last_km;
                                            else
                                                TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_out_km;
                                            km += distance / 1000;
                                        }
                                        else if(TestService.RAYON.equals("Мингбулок")  || TestService.RAYON.equals("Чартак")) {
                                            System.out.println("RAYONNNN   "+TestService.in_rayon);
                                            if (!TestService.in_rayon)
                                            {
                                                TestService.dop2_summa += (distance / 1000) * TestService.zakaz_out_km;
                                                TestService.last_out_km += distance / 1000;
                                            }
                                            else if (km < 1.5 && !TestService.zakaz_uchtaxi) {

                                                if (km + (distance / 1000) > 1.5) {
                                                    TestService.taksometr_summa += (km + (distance / 1000) - 1.5) * TestService.zakaz_last_km;
                                                    TestService.taksometr_summa += (1.5 - km) * TestService.zakaz_first_km_2;
                                                } else
                                                    TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_first_km_2;
                                                km += distance / 1000;
                                            } else
                                            {
                                                TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_last_km;
                                                km += distance / 1000;
                                            }



                                        }
                                        else{
                                            if (km < 2 && !TestService.zakaz_uchtaxi) {
                                                if (km + (distance / 1000) > 2) {
                                                    TestService.taksometr_summa += (km + (distance / 1000) - 2) * TestService.zakaz_last_km;
                                                    TestService.taksometr_summa += (2 - km) * TestService.zakaz_first_km_2;
                                                } else
                                                    TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_first_km_2;
                                            } else if (TestService.in_rayon)
                                                TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_last_km;
                                            else
                                                TestService.taksometr_summa += (distance / 1000) * TestService.zakaz_out_km;
                                            km += distance / 1000;
                                        }


                                        TestService.loc = TestService.loc2;
                                        TestService.gps_list.add(TestService.loc2);
                                    }
                                }
                            }

                            TestService.taksometr_km = km ;



                            if (TestService.taksometr_ojidaniya > 0) {
                                TestService.last_ojidaniya_summa += TestService.zakaz_wait_summ;
                                TestService.taksometr_summa += TestService.zakaz_wait_summ;
                            }

                            if (TestService.do_start_ojidaniya && (TestService.taksometr_ojidaniya_do_starta > TestService.zakaz_free_wait)) {
                                TestService.last_ojidaniya_summa += TestService.zakaz_wait_summ;
                                TestService.taksometr_summa += TestService.zakaz_wait_summ;
                            }
                            if (TestService.do_start_ojidaniya) {
                                continue;
                            }
                            if (TestService.started) {
                                TestService.taksometr_vputi += 1;

                            }

                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                        }
                    }
                    TestService.gps_started=false;
                    System.out.println("GPS_STOP");
                    stopService(new Intent(MainActivity.thisactivity, taxometrService.class));
                }
            }).start();
        }
    }
}