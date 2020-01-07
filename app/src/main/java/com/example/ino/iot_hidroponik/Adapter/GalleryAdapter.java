package com.example.ino.iot_hidroponik.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.iot_hidroponik.Model.Gallery;
import com.example.ino.iot_hidroponik.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {


    private Context context;

    List<Gallery> galleryList;

    public GalleryAdapter(List<Gallery> galleryList, Context context){
        this.galleryList = galleryList;
        this.context = context;
    }


    class GalleryViewHolder extends RecyclerView.ViewHolder {
        //Views
        private TextView textViewdate;
        private ImageView imageView,imageViewicon;


        //Initializing Views
        public GalleryViewHolder(View itemView) {
            super(itemView);
            textViewdate = (TextView) itemView.findViewById(R.id.gallery_date);
            imageView = (ImageView)itemView.findViewById(R.id.gallery_image);
            imageViewicon = (ImageView)itemView.findViewById(R.id.warning);

        }
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery,parent,false);
        GalleryViewHolder viewHolder = new GalleryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int postition) {
        String status = galleryList.get(postition).getStatus();
        if(status.equals("SEHAT")){
            holder.imageViewicon.setVisibility(View.INVISIBLE);
        }else {
            holder.imageViewicon.setVisibility(View.VISIBLE);
        }
        if (galleryList.get(postition).getUrl().equals("http://iot.dinus.ac.id/")){
            Glide.with(context).load(R.drawable.ic_warning)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }else {
            Glide.with(context).load(galleryList.get(postition).getUrl())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }
        holder.textViewdate.setText(galleryList.get(postition).getDate());

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
}
