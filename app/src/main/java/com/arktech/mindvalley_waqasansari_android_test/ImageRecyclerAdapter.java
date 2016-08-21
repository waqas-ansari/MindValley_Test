package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Linta Ansari on 8/19/2016.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImagesViewHolder> {
    List<String> urls;
    Context context;

    public ImageRecyclerAdapter(List<String> urls, Context context) {
        this.urls = urls;
        this.context = context;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_images, parent, false);

        return new ImagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, final int position) {
        final ManagerClass managerClass = new ManagerClass(position);
        final ImagesViewHolder viewHolder = holder;
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerClass.loadImageFromURL(urls.get(viewHolder.getAdapterPosition()))
                        .withPlaceHolder(R.drawable.placeholder)
                        .withErrorImage(R.drawable.cancel)
                        .cacheImage(context)
                        .withFileName("my_image")
                        .withProgressBar(viewHolder.progressBar)
                        .into(viewHolder.imageView)
                        .download();
            }
        });

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerClass.cancelDownload(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button btnDownload;
        Button btnCancel;
        ProgressBar progressBar;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            btnDownload = (Button) itemView.findViewById(R.id.btnDownload);
            btnCancel = (Button) itemView.findViewById(R.id.btnCancel);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }

    }
}
