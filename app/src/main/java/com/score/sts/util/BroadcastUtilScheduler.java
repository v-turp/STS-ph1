package com.score.sts.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Who Dat on 1/25/2016.
 * This is a utility scheduler that will schedule all events until further notice.
 * This will be used primarily to schedule database updates and push notifications.
 * NOTE: The BroadcastUtilScheduler runs on a separate process(:remote) as that of the application(see the manifest)
 */
public class BroadcastUtilScheduler extends BroadcastReceiver {

    private static final String TAG = BroadcastUtilScheduler.class.getSimpleName();
    public static final int REQUEST_CODE = 1234;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent testIntent = new Intent(C.ACTION_UTIL_SERVICE, null, context, UtilService.class);
        testIntent.putExtra("testing", "Alarm Test");
        context.startService(testIntent);
    }
}
