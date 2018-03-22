package com.example.stefano.lomux_pro.listener;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


/**
 * Created by Franc on 04/12/2017.
 */

public class YoutubeInitializationListener implements YouTubePlayer.OnInitializedListener {

    private final String url;
    private final Activity activityOfPlayer;
    private final SlidingUpPanelLayout youtubeSlider;

    private final String API_KEY = "AIzaSyDyQ4VecF0EhVMxHPORdKzNNOh3jRYZUEY";

    public YoutubeInitializationListener(String youtubeUrl)
    {
        // Costruttore temporaneo fino a che non capisco come passare un' Activity a questo listener
        this.url = youtubeUrl;
        activityOfPlayer = null;
        youtubeSlider = null;
    }

    public YoutubeInitializationListener(SlidingUpPanelLayout ytSlider, String youtubeUrl, Activity activity)
    {
        youtubeSlider = ytSlider;
        this.url = youtubeUrl;
        this.activityOfPlayer = activity;
    }

    public String getApiKey()
    {
        return this.API_KEY;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer.setShowFullscreenButton(false);
            youTubePlayer.loadVideo(url);
            youTubePlayer.play();
            youtubeSlider.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String errorMessage = youTubeInitializationResult.toString();
        Toast.makeText(activityOfPlayer, errorMessage, Toast.LENGTH_LONG).show();
        Log.d("errorMessage:", errorMessage);
    }
}
