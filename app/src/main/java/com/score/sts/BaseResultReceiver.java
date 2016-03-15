package com.score.sts;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by Who Dat on 1/20/2016.
 * NOTE: this class may or may not be used for quick and simple network calls.
 * It was originally being used to return results from the ContactsUpdateService
 * to update the ContactRecyclerView but was causing memory leaks so I switched
 * to using BroadcastReceiver. So, this class may be used in some cases on a case
 * by case trial. Must always be checked for memory leaks.
 */
@SuppressWarnings("ParcelCreator")
public class BaseResultReceiver extends ResultReceiver {

    private static final String TAG = BaseResultReceiver.class.getSimpleName();

    private Receiver receiver;

    public BaseResultReceiver(Handler handler){
        super(handler);
    }

    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if(receiver != null){
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

    /**
     * this setup with the interface makes this receiver much more flexible and extensible.
     * with this interface being contained within the class and replicating the onReceiveResult
     * method, an instance of the ContactsUpdateResultsReceiver can set a Receiver and implement
     * the method to suit each Activity/Fragment's individual needs.
     * FLOW: Create and instance of BaseResultReceiver, call setReceiver(), implement Receiver method
     * onReceive. this should be done inside the calling Activity/Fragment
     * */
    public interface Receiver{
        void onReceiveResult(int resultCode, Bundle resultData);
    }

}
