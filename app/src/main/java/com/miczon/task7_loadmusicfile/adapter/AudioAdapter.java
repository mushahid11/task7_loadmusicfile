package com.miczon.task7_loadmusicfile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.miczon.task7_loadmusicfile.MainActivity;
import com.miczon.task7_loadmusicfile.R;
import com.miczon.task7_loadmusicfile.model.VideoModel;
import java.util.ArrayList;
import java.util.HashMap;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.viewHolder> {

    Context context;
    ArrayList<VideoModel> videoArrayList;
    public VideoAdapter.OnItemClickListener onItemClickListener;

    public AudioAdapter (Context context, ArrayList<VideoModel > videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;

    }

    @Override
    public AudioAdapter .viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_list, viewGroup, false);
        return new AudioAdapter.viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.title.setText(videoArrayList.get(position).getVideoTitle());
        holder.duration.setText(videoArrayList.get(position).getVideoDuration());

        Bitmap image = coverpicture(String.valueOf(videoArrayList.get(position).getVideoUri()));
        holder.imageView.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
      private   TextView title, duration;
        private  ImageView imageView;
        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            duration = (TextView) itemView.findViewById(R.id.duration);
            imageView = (ImageView) itemView.findViewById(R.id.audio_thumbnail);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick(getAdapterPosition(), v);
//                }
//            });
        }

    }
    public void setOnItemClickListener(VideoAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }

    public  Bitmap coverpicture(String path) {

        MediaMetadataRetriever mr = new MediaMetadataRetriever();

        mr.setDataSource(path);

        byte[] byte1 = mr.getEmbeddedPicture();
        mr.release();
        if(byte1 != null)
            return BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
        else
            return  null;

    }

}

