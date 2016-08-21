package com.arktech.mindvalley_waqasansari_android_test;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://placeholdit.imgix.net/~text?txtsize=33&txt=350%C3%97150&w=350&h=150";
        List<String> urls = new ArrayList<>();
        for(int i=0; i<5; i++)
            urls.add(url);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ImageListAdapter(urls, ActivityMain.this));

        /*
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if(recyclerView != null)
            recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityMain.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ImageRecyclerAdapter(urls, ActivityMain.this));



        /*

        ManagerClass managerClass = new ManagerClass();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imgLayout);

        ImageView img[] = new ImageView[10];
        for(int i=0; i<10; i++) {
            img[i] = addImage();
            linearLayout.addView(img[i]);
            if(managerClass.isImageCached("my_image" + String.valueOf(i))) {
                managerClass.loadImageFromCache("my_image" + String.valueOf(i))
                        .into(img[i]);
            } else {
                managerClass.loadImageFromURL("https://placeholdit.imgix.net/~text?txtsize=33&txt=350%C3%97150&w=350&h=150")
                        .withPlaceHolder(R.mipmap.ic_launcher)
                        .withErrorImage(R.mipmap.ic_launcher)
                        .cacheImage(this)
                        .withFileName("my_image" + String.valueOf(i))
                        .into(img[i])
                        .download();
            }
        }

        */

    }

}
