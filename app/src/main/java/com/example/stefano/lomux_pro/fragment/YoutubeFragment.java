package com.example.stefano.lomux_pro.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.listener.YoutubeListener;
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
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private String url;

    public interface OnYoutubeBackListener {

        public void onYoutubeBack();
    }

    public YoutubeFragment(SlidingUpPanelLayout slidingUpPanelLayout, String url){
        this.slidingUpPanelLayout = slidingUpPanelLayout;
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
        View rootView = inflater.inflate(R.layout.you_tube_api, container, false);

        back = (TextView) rootView.findViewById(R.id.youtube_fragment_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeBackListener.onYoutubeBack();
            }
        });


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();


        youTubePlayerFragment.initialize(API_KEY, new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(url);
                    player.play();
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);


                }
            }

            
            @Override
            public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        return rootView;
    }

}

