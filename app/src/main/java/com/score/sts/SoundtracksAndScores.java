package com.score.sts;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Who Dat on 1/14/2016.
 */
public class SoundtracksAndScores extends Application {

    public static final boolean DEBUG = true;
    private static String TAG = SoundtracksAndScores.class.getSimpleName();

    public static RefWatcher getRefWatcher(Context context){
        SoundtracksAndScores application = (SoundtracksAndScores) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        StrictMode.enableDefaults();
    }
}
