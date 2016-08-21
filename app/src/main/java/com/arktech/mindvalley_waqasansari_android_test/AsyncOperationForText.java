package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncOperationForText extends AsyncTask<Void, Void, String> {
    private Context context;

    private String url;

    private String loadedText = null;

    private TextView textView;
    private boolean cache = false;
    private String fileName;
    private ProgressBar progressBar;

    public AsyncOperationForText(String url, TextView textView, boolean cache, String fileName, ProgressBar progressBar,
                                 Context context) {
        this.url = url;
        this.textView = textView;
        this.cache = cache;
        this.fileName = fileName;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            return convertStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s != null) {
            if(textView != null) {
                textView.setText(s);
                if(cache) {
                    CacheObjects.cacheTextIntoFile(fileName, s, context);
                }
            }
        }
    }


    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
