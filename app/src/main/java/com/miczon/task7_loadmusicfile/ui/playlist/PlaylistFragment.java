package com.miczon.task7_loadmusicfile.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.miczon.task7_loadmusicfile.R;
import com.miczon.task7_loadmusicfile.ViewPagerAdapter;
import com.miczon.task7_loadmusicfile.ui.audio.AudioFragment;
import com.miczon.task7_loadmusicfile.ui.vedio.VideoFragment;

public class PlaylistFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_playlist, container, false);

        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager = root.findViewById(R.id.viewpager);

        // setting/Initializing up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        setupViewPager(viewPager);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout.setupWithViewPager(viewPager);

        //setting icons
        tabLayout.getTabAt(0).setText("Audio");
        tabLayout.getTabAt(1).setText("Video");




        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // add the fragments
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PFragmentAudio(), "Audio");
        adapter.addFragment(new PFragmentVideo(), "Video");
        viewPager.setAdapter(adapter);
    }
}