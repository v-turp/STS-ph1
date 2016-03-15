package com.score.sts.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.widget.Toast;

import com.score.sts.R;

/**
 * Created by Who Dat on 2/13/2016.
 * The purpose of this class is to manage the size of images
 * so that they are the appropriate size for use in the application
 * so as not to cause an out of memory exception. This class will be
 * used for images that were retrieved primarily over the network but
 * will also be used for local images as well. This class is also used
 * for caching images and caching responsibilities. This class is a
 * work in progress and responsibilities may change in the future
 * and so will this description.
 */
public class ImageManager {

    public Context context;
    private LruCache<String, Bitmap> lruCache;

    public ImageManager(Context context){
        this.context = context;
    }

    // gets stats for local resource images
    public void getImageStats(Resources resources){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.sts_logo_original, options);
        int width = options.outWidth;
        int height = options.outHeight;
        String mimeType = options.outMimeType;
        String imageStuff = "width: " + width + "\nheight: " + height + "\nmime type: " + mimeType;
        Toast.makeText(context, imageStuff, Toast.LENGTH_LONG).show();
    }

    public int calculateSampleSize(BitmapFactory.Options options, int requiredHeight, int requiredWidth){

        // raw height and width of the image
        int actualWidth = options.outWidth;
        int actualHeight = options.outHeight;
        int inSampleSize = 1;

        if(actualHeight > requiredHeight || actualWidth > requiredWidth){
            final int halfHeight = actualHeight / 2;
            final int halfWidth = actualWidth / 2;

            while((halfHeight / inSampleSize) > requiredHeight && (halfWidth / inSampleSize) > requiredWidth){
                inSampleSize*=2;
            }
        }

        return inSampleSize;
    }

    public void setupLruCache(@Nullable int size){
        // first, get available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes, as LruCache takes an int in its constructor
        final int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;

        // use 1/8 of the available memory for this memory cache
        int cacheSize = maxMemory / 8;

           lruCache = new LruCache<String,Bitmap>(maxMemory){
               @Override
               protected int sizeOf(String key, Bitmap value) {
                   return value.getByteCount() / 1024;
               }
           };

    } // end method setupLruCache

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(getBitmapFromCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String key){
        return lruCache.get(key);
    }
}
