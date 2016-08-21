package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Linta Ansari on 8/18/2016.
 */
public class CacheObjects {

    private static CacheObjects singleInstance;

    private static LruCache<String, Bitmap> imageCache;

    public CacheObjects() {
    }

    public static CacheObjects getSingleInstance() {
        if(singleInstance == null)
            singleInstance = new CacheObjects();
        return singleInstance;
    }


    //Images Cache
    private static void prepareImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static void addBitmapToCache(String key, Bitmap bitmap) {
        prepareImageCache();
        if(getBitmapFromCache(key) == null)
            imageCache.put(key, bitmap);
    }

    public static Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap;
        try {
            bitmap = imageCache.get(key);
        } catch (NullPointerException e) {
            bitmap = null;
        }
        return bitmap;
    }


    //Text Cache
    public static void cacheTextIntoFile(String fileName, String textToBeCached, Context context) {
        CacheTextInFile.cacheText(context, fileName, textToBeCached);
    }

    public static String getCachedTextFromFile(String fileName, Context context) {
        return CacheTextInFile.getCachedText(context, fileName);
    }

}
