package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManagerClass {
    Context context;

    private String url;

    int position;

    AsyncOperationForImage operationForImage;

    //For Image
    private ImageView imageView;
    private int placeHolderResId=-1, errorResId=-1, cancelResId=-1;
    private ProgressBar progressBar=null;
    private int resizedWidth=0, resizedHeight=0;
    private Bitmap bitmapFromCache = null;

    //For JSON/XML
    private TextView textView;

    //Cache
    private boolean cache = false;
    private String fileName;
    private String textFromCache = null;


    public ManagerClass(int position) {
        this.position = position;
    }


    //*****************************IMAGE STUFF*****************************************************

    //Mandatory
    public ManagerClass loadImageFromURL(String url) {
        this.url = url;
        return this;
    }

    public boolean isImageCached(String fileName) {
        return CacheObjects.getBitmapFromCache(fileName) != null;
    }

    public ManagerClass loadImageFromCache(String fileName) {
        bitmapFromCache = CacheObjects.getBitmapFromCache(fileName);
        return this;
    }

    //Optionals
    public ManagerClass withPlaceHolder(int resId) {
        this.placeHolderResId = resId;
        return this;
    }
    public ManagerClass withErrorImage(int resId) {
        this.errorResId = resId;
        return this;
    }
    public ManagerClass withCancelImage(int resId) {
        this.cancelResId = resId;
        return this;
    }
    public ManagerClass withProgressBar(ProgressBar progressBar){
        this.progressBar = progressBar;
        return this;
    }
    public ManagerClass resize(int width, int height) {
        this.resizedWidth = width;
        this.resizedHeight = height;
        return this;
    }
    public ManagerClass cacheImage(Context context) {
        this.cache = true;
        this.context = context;
        return this;
    }
    public ManagerClass withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    //Mandatory
    public ManagerClass into(ImageView imageView){
        this.imageView = imageView;

        if(bitmapFromCache != null) {
            imageView.setImageBitmap(bitmapFromCache);
        }

        return this;
    }

    public void download() {
        Utils.managerClassList.add(position, this);
        operationForImage = new AsyncOperationForImage(context,
                this.imageView, url, cache, fileName,
                placeHolderResId, errorResId, cancelResId,
                progressBar,
                resizedWidth, resizedHeight);
        operationForImage.execute();

    }

    public void cancelDownload(int position) {
        Utils.managerClassList.get(position).cancelDownload();
    }
    
    private void cancelDownload(){
        operationForImage.cancel(true);
    }



    //*********************************TEXT STUFF**************************************************
    public ManagerClass loadTextFromURL(String url) {
        this.url = url;
        return this;
    }

    public ManagerClass loadTextFromCache(String fileName) {
        this.textFromCache = CacheObjects.getCachedTextFromFile(fileName, context);
        return this;
    }

    public ManagerClass cacheText(Context context) {
        this.context = context;
        this.cache = true;
        return this;
    }

    public ManagerClass intoFile(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void display(TextView textView) {
        this.textView = textView;
        if(textFromCache != null) {
            textView.setText(textFromCache);
            return;
        }

        new AsyncOperationForText(url, textView, cache, fileName, progressBar, context);
    }

}
