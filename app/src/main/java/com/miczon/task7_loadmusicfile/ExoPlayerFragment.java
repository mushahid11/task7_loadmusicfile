package com.miczon.task7_loadmusicfile;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class ExoPlayerFragment extends Fragment {

    private PlayerView mPlayerView ;
    public ExoPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exo_player, container, false);
        Bundle arguments = getArguments();
        String desired_string = arguments.getString("uri");

        mPlayerView = view.findViewById(R.id.exoplayerView_vedio);
        TrackSelector trackSelectorDef = new DefaultTrackSelector();
        SimpleExoPlayer absPlayerInternal = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelectorDef);
        String userAgent = Util.getUserAgent(getContext(), "dfgfd");
        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(getContext(),userAgent);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(Uri.parse(desired_string));
        absPlayerInternal.prepare(mediaSource);
        absPlayerInternal.setPlayWhenReady(true);

        mPlayerView.setPlayer(absPlayerInternal);
        return view;
    }
}