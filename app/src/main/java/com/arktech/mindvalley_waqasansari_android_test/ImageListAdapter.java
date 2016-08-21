package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;


public class ImageListAdapter extends BaseAdapter {
    List<String> urls;
    Context context;

    public ImageListAdapter(List<String> urls, Context context) {
        this.urls = urls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.card_images, null);

        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        final Button btnDownload = (Button) view.findViewById(R.id.btnDownload);
        final Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);

        final String url = urls.get(i);
        final int position = i;

        final ManagerClass managerClass = new ManagerClass(i);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerClass.loadImageFromURL(url)
                        .withPlaceHolder(R.drawable.placeholder)
                        .withErrorImage(R.drawable.error_icon)
                        .withCancelImage(R.drawable.cancel)
                        .cacheImage(context)
                        .withFileName("my_image")
                        .withProgressBar(progressBar)
                        .into(imageView)
                        .download();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerClass.cancelDownload(position);
            }
        });

        return view;
    }
}
