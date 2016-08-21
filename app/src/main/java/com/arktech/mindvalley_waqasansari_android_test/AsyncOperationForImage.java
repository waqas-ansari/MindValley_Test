package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncOperationForImage extends AsyncTask<Void, Void, Bitmap> {

    private int placeHolderResId = -1, errorResId = -1, cancelResId = -1;
    private ProgressBar progressBar = null;
    private ImageView imageView;
    private int width=0, height=0;
    private boolean cache = false;
    private String fileName;

    private String url;

    Context context;

    public AsyncOperationForImage(Context context,
                                  ImageView imageView, String url, boolean cache, String fileName,
                                  int placeHolderResId, int errorResId, int cancelResId,
                                  ProgressBar progressBar,
                                  int width, int height) {

        this.context = context;
        this.imageView = imageView; this.url = url; this.cache = cache; this.fileName = fileName;
        this.placeHolderResId = placeHolderResId;   this.errorResId = errorResId;   this.cancelResId = cancelResId;
        this.progressBar = progressBar;
        this.width = width; this.height = height;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);

        if(placeHolderResId != -1)
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), placeHolderResId));

    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            if(errorResId != -1) {
                ((ActivityMain) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), errorResId));
                    }
                });

            }
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(progressBar != null)
            progressBar.setVisibility(View.GONE);
        if(cancelResId != -1)
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), cancelResId));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(progressBar != null)
            progressBar.setVisibility(View.GONE);
        if (bitmap != null) {
            if(width != 0 && height != 0) {
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                imageView.setImageBitmap(bitmap);
            } else imageView.setImageBitmap(bitmap);
            if(cache) {
                CacheObjects.addBitmapToCache(fileName, bitmap);
            }
        } else {
            if(errorResId != -1)
                imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), errorResId));
        }
    }
}
