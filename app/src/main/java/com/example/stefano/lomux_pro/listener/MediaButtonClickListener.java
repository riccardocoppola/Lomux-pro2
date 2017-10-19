package com.example.stefano.lomux_pro.listener;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.stefano.lomux_pro.R;

/**
 * Created by Stefano on 19/10/2017.
 */

public class MediaButtonClickListener implements View.OnClickListener {

    private LinearLayout linear_layout;
    private boolean hide;
    private boolean hasyoutubelink;
    private boolean hasspotifylink;

    ImageButton arrow_button;
    ImageButton share_button;
    ImageButton media_button;
    ImageButton youtube_button;
    ImageButton spotify_button;
    ImageButton back_button;

    public MediaButtonClickListener(LinearLayout linear_layout, boolean hide, boolean hasyoutubelink, boolean hasspotifylink) {
        this.linear_layout = linear_layout;
        this.hide = hide;
        this.hasyoutubelink = hasyoutubelink;
        this.hasspotifylink = hasspotifylink;


        this.arrow_button = linear_layout.findViewById(R.id.imagebutton_arrow_directions);
        this.share_button = linear_layout.findViewById(R.id.imagebutton_share);
        this.media_button = linear_layout.findViewById(R.id.imagebutton_play);
        this.youtube_button =  linear_layout.findViewById(R.id.imagebutton_youtube);
        this.spotify_button =  linear_layout.findViewById(R.id.imagebutton_spotify);
        this.back_button = linear_layout.findViewById(R.id.imagebutton_back);
    }

    @Override
    public void onClick(View v) {

        LinearLayout.LayoutParams lp_visible = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp_visible.weight=1.0f;

        LinearLayout.LayoutParams lp_hidden = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp_hidden.weight=0.0f;

        if (hide) {
            arrow_button.setLayoutParams(lp_hidden);
            share_button.setLayoutParams(lp_hidden);
            media_button.setLayoutParams(lp_hidden);
            if (hasyoutubelink)
                youtube_button.setLayoutParams(lp_visible);
            else
                youtube_button.setLayoutParams(lp_hidden);
            if (hasspotifylink)
                spotify_button.setLayoutParams(lp_visible);
            else
                spotify_button.setLayoutParams(lp_hidden);
            back_button.setLayoutParams(lp_visible);
        }
        else {
            reset_buttons();
        }


    }

    public void reset_buttons() {


        LinearLayout.LayoutParams lp_visible = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp_visible.weight=1.0f;

        LinearLayout.LayoutParams lp_hidden = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp_hidden.weight=0.0f;

        arrow_button.setLayoutParams(lp_visible);
        share_button.setLayoutParams(lp_visible);
        media_button.setLayoutParams(lp_visible);
        youtube_button.setLayoutParams(lp_hidden);
        spotify_button.setLayoutParams(lp_hidden);
        back_button.setLayoutParams(lp_hidden);

    }

}

