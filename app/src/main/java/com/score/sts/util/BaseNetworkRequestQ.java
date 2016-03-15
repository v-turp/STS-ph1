package com.score.sts.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Who Dat on 2/1/2016.
 * This is the primary/base class for making calls over the
 * network. This class will work with servics as well individually.
 * Use will be determined on a case by case manner. This claas is a
 * Uses the Singleton patter to ensure it lasts the lifetime of the
 * application.
 */
public class BaseNetworkRequestQ {

    private static BaseNetworkRequestQ mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;

    private BaseNetworkRequestQ(Context context){
        this.mContext = context;
        this.mRequestQueue = getRequestQueue();
        // ?? what is the purpose of the ImageLoader && what is the purpose of LruCache ??
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache(){

            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized BaseNetworkRequestQ getInstance(Context context){
        if(mInstance == null){
            mInstance = new BaseNetworkRequestQ(context);
        }
        return  mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequesQueue(Request<T> request){
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
