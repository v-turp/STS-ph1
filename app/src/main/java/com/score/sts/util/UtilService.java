package com.score.sts.util;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Who Dat on 1/25/2016.
 * This is the utility service class to perform
 * all general service requests
 */
public class UtilService extends IntentService {

    private final static String TAG = UtilService.class.getSimpleName();

    public UtilService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Wake Up!!!");
    }
}
