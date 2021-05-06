package com.miczon.task7_loadmusicfile.ui.playlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.miczon.task7_loadmusicfile.R;

import static android.app.Activity.RESULT_OK;

public class PFragmentAudio extends Fragment {

    private Button button;
    private PlayerView mPlayerView ;

    public PFragmentAudio() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p_audio, container, false);

        mPlayerView = view.findViewById(R.id.exoplayerView_vv);
        button = view.findViewById(R.id.btn_uoploadaudio);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("audio/*");
                startActivityForResult(pickIntent, 1);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedMediaUri = data.getData();
            if (selectedMediaUri.toString().contains("audio")) {

                Toast.makeText(getContext(), "audio", Toast.LENGTH_SHORT).show();
                TrackSelector trackSelectorDef = new DefaultTrackSelector();
                SimpleExoPlayer absPlayerInternal = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelectorDef);
                String userAgent = Util.getUserAgent(getContext(), "dfgfd");
                DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(getContext(),userAgent);
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(selectedMediaUri);
                absPlayerInternal.prepare(mediaSource);
                absPlayerInternal.setPlayWhenReady(true);

                mPlayerView.setPlayer(absPlayerInternal);

            }
        }
    }

}