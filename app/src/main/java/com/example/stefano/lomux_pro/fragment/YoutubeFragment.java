package com.example.stefano.lomux_pro.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.listener.YoutubeInitializationListener;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


/**
 * Created by Franc on 07/10/2017.
 */

public class YoutubeFragment extends Fragment {

    private OnYoutubeBackListener youtubeBackListener;
    private TextView back;
    private SlidingUpPanelLayout youtubeSlider;
    private SlidingUpPanelLayout pinInfoSlider;
    private String url;

    public interface OnYoutubeBackListener {

        public void onYoutubeBack();
    }

    public YoutubeFragment(SlidingUpPanelLayout youtubeSlider, SlidingUpPanelLayout pinInfoSlider, String url){
        this.youtubeSlider = youtubeSlider;
        this.pinInfoSlider = pinInfoSlider;
        this.url = url;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            youtubeBackListener = (OnYoutubeBackListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private static final String API_KEY = "AIzaSyDyQ4VecF0EhVMxHPORdKzNNOh3jRYZUEY";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pin_info_slide3, container, false);

        //back = (TextView) rootView.findViewById(R.id.youtube_fragment_back);
      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeBackListener.onYoutubeBack();
            }
        }); */


        final YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commitNow();

        // TODO calculate height
       // final float youtubePlayerHeight = this.getView().getLayoutParams().height;
        youTubePlayerFragment.initialize(API_KEY,
                new YoutubeInitializationListener(youtubeSlider, url, getActivity()));

        return rootView;
    }

}

