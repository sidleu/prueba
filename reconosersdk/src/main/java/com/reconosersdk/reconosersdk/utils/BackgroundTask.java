package com.reconosersdk.reconosersdk.utils;

import android.os.AsyncTask;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BackgroundTask implements Runnable {

    public static void task(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code

            }
        });
    }

    @Override
    public void run() {
        System.out.println("Hello world");
    }

    /*
    public void exectuter(){
        long period = 100; // the period between successive executions
        exec.scheduleAtFixedRate(new BackgroundTask(), 0,period,TimeUnit.MICROSECONDS);
        long delay = 100; //the delay between the termination of one execution and the commencement of the next
        exec.scheduleWithFixedDelay(new BackgroundTask(), 0,delay,TimeUnit.MICROSECONDS);
    }*/
}


