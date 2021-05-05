package com.miczon.task7_loadmusicfile.ui.audio;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.miczon.task7_loadmusicfile.MainActivity;
import com.miczon.task7_loadmusicfile.R;
import com.miczon.task7_loadmusicfile.adapter.AudioAdapter;
import com.miczon.task7_loadmusicfile.adapter.VideoAdapter;
import com.miczon.task7_loadmusicfile.model.VideoModel;
import com.miczon.task7_loadmusicfile.ui.vedio.DashboardViewModel;

import java.util.ArrayList;

public class AudioFragment extends Fragment {

    public static ArrayList<VideoModel> videoArrayList;
    private RecyclerView recyclerView;
    public static final int PERMISSION_READ = 0;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_audio, container, false);


        recyclerView = root. findViewById(R.id.recycle_audio);
        if (checkPermission()) {
            videoList();
        }
        videoArrayList = new ArrayList<>();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void videoList() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        videoArrayList = new ArrayList<>();
        getVideos();
    }

    //get video files from storage
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getVideos() {

        Context applicationContext = MainActivity.getContextOfApplication();
        applicationContext.getContentResolver();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Log.d("dsffsf", "getVideos: "+title);
                Log.d("sdfsdf", "getVideos: "+duration);
                Log.d("sdfsdfsd", "getVideos: "+data);
                VideoModel  videoModel  = new VideoModel ();
                videoModel .setVideoTitle(title);
                videoModel .setVideoUri(Uri.parse(data));
                videoModel .setVideoDuration(timeConversion(Long.parseLong(duration)));
                videoArrayList.add(videoModel);

            } while (cursor.moveToNext());
        }

        AudioAdapter adapter = new AudioAdapter (getActivity(), videoArrayList);
        recyclerView.setAdapter(adapter);

//        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int pos, View v) {
//                Intent intent = new Intent(getContext(), VideoFragment.class);
//                intent.putExtra("pos", pos);
//                startActivity(intent);
//            }
//        });

    }

    //time conversion
    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        videoList();
                    }
                }
            }
        }
    }

}