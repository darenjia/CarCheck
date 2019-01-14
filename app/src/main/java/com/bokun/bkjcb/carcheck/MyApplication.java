package com.bokun.bkjcb.carcheck;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bokun.bkjcb.carcheck.Model.MyObjectBox;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by DengShuai on 2018/11/1.
 * Description :
 */
public class MyApplication extends Application {
    private static Context context;
    private static BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
        boxStore = MyObjectBox.builder().androidContext(context).build();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(this);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static BoxStore getBoxStore(){
        return boxStore;
    }
    public static Context getContext() {
        return context;
    }

    public static boolean isDebug() {
        if(BuildConfig.DEBUG){
            return true;
        }
        return false;
    }
}
