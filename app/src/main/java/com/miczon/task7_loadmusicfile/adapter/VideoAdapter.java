package com.miczon.task7_loadmusicfile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.miczon.task7_loadmusicfile.R;
import com.miczon.task7_loadmusicfile.model.VideoModel;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {

    Context context;
    ArrayList<VideoModel> videoArrayList;
    public OnItemClickListener onItemClickListener;
    private Bitmap bMap;

    public VideoAdapter(Context context, ArrayList<VideoModel> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
        Log.d("dfsdf", "onBindViewHolder: ");
    }

    @Override
    public VideoAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.viewHolder holder, final int i) {
        holder.title.setText(videoArrayList.get(i).getVideoTitle());
        holder.duration.setText(videoArrayList.get(i).getVideoDuration());

        new Thread(() -> {
            bMap = ThumbnailUtils.createVideoThumbnail(String.valueOf(videoArrayList.get(i).getVideoUri()), MediaStore.Video.Thumbnails.MICRO_KIND);
            AppCompatActivity compatActivity = (AppCompatActivity) context;
            compatActivity.runOnUiThread(() -> holder.imageView.setImageBitmap(bMap));
        }).start();
    }


    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView title, duration;
        private ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            duration = (TextView) itemView.findViewById(R.id.duration);
            imageView = (ImageView) itemView.findViewById(R.id.video_thumbnail);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick(getAdapterPosition(), v);
//                }
//            });
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}
