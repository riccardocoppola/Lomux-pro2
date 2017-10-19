package com.example.stefano.lomux_pro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.net.Uri;

import com.example.stefano.lomux_pro.callbacks.PinsCallback;
import com.example.stefano.lomux_pro.callbacks.SongCallback;
import com.example.stefano.lomux_pro.fragment.YoutubeFragment;
import com.example.stefano.lomux_pro.listener.MediaButtonClickListener;
import com.example.stefano.lomux_pro.listener.URIClickListener;
import com.example.stefano.lomux_pro.model.Link;
import com.example.stefano.lomux_pro.model.Mediatype;
import com.example.stefano.lomux_pro.model.Pin;
import com.example.stefano.lomux_pro.model.Pintype;
import com.example.stefano.lomux_pro.model.SongHasMediatype;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Stefano on 18/10/2017.
 */

public class PinInfoSlidedPanel {

    private LomuxMapActivity view;
    private Pin pin;
    private TextView title;
    private ImageView pin_title_image;
    private TextView address_textview;
    private TextView artists_textview;
    private TextView info;
    private TextView formore;
    private ImageView pin_fragment_image;
    private TextView subtitle_textview;
    private ImageButton arrow_button;
    private ImageButton share_button;
    private ImageButton media_button;
    private ImageButton back_button;
    private ImageButton spotify_button;
    private ImageButton youtube_button;
    private TextView source_label;
    private LinearLayout buttons_layout;
    private LinearLayout layout_title;
    private LinearLayout content_layout;
    private ProgressBar progressBar;
    private LinearLayout source_layout;
    private ProgressBar loader;


    public PinInfoSlidedPanel(LomuxMapActivity view, Pin pin) {
        this.view = view;
        this.pin = pin;
        this.title = view.findViewById(R.id.pin_fragment_layout_title);
        this.layout_title = view.findViewById(R.id.pin_fragment_layout_linear_layout_for_title);
        this.pin_title_image = view.findViewById(R.id.pin_fragment_layout_title_image);
        this.address_textview = view.findViewById(R.id.pin_fragment_layout_textview_address);
        this.artists_textview = view.findViewById(R.id.pin_fragment_layout_textview_artists);
        this.info = view.findViewById(R.id.pin_fragment_layout_textview_info);
        this.formore = view.findViewById(R.id.pin_fragment_layout_textview_formore);
        this.pin_fragment_image = view.findViewById(R.id.pin_fragment_layout_imageview);
        this.subtitle_textview = view.findViewById(R.id.pin_fragment_layout_textview_subtitle);
        this.arrow_button = view.findViewById(R.id.imagebutton_arrow_directions);
        this.share_button = view.findViewById(R.id.imagebutton_share);
        this.media_button = view.findViewById(R.id.imagebutton_play);
        this.back_button =  view.findViewById(R.id.imagebutton_back);
        this.spotify_button = view.findViewById(R.id.imagebutton_spotify);
        this.youtube_button = view.findViewById(R.id.imagebutton_youtube);
        this.source_label =  view.findViewById(R.id.pin_fragment_layout_source_label);
        this.buttons_layout = view.findViewById(R.id.pin_fragment_layout_button_horizontal_layout);
        this.content_layout = view.findViewById(R.id.pin_fragment_layout_inner_container_layout);
        this.progressBar = view.findViewById(R.id.progress_other_info);
        this.source_layout = view.findViewById(R.id.pin_fragment_layout_linearlayout_for_source);
        this.loader = view.findViewById(R.id.progress_image);
    }

   public void setTitle(){
       /*set the title info with the appropriate color and icon*/

       title.setText(pin.getTitle());
       subtitle_textview.setText(pin.getSubtitle());

       switch(pin.getPinTypeidPinType().getIdPinType()) {
           case Pintype.PRIVATE:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorPrivate));
               pin_title_image.setImageResource(R.drawable.title_layout_p);
               break;
           case Pintype.WORK:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorWork));
               pin_title_image.setImageResource(R.drawable.title_layout_w);
               break;
           case Pintype.STUDIO:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorStudio));
               pin_title_image.setImageResource(R.drawable.title_layout_r);
               break;
           case Pintype.MONUMENT:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorMonument));
               pin_title_image.setImageResource(R.drawable.title_layout_m);
               break;
           case Pintype.VENUE:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorVenue));
               pin_title_image.setImageResource(R.drawable.title_layout_v);
               break;
           case Pintype.LOTM:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorLOTM));
               pin_title_image.setImageResource(R.drawable.title_layout_l);
               break;
           default:
               layout_title.setBackgroundColor(ContextCompat.getColor(view, R.color.colorPrimary));
               pin_title_image.setBackgroundResource(R.color.colorPrimary);
               break;
       }
   }


   public void setAdditionalInfo(){
       //show the progress bar
       progressBar.setVisibility(View.VISIBLE);
       content_layout.setVisibility(View.INVISIBLE);
       DoWork task = new DoWork(progressBar);

       task.execute();

   }

    class DoWork extends AsyncTask<Void, Void, Void> {
        private ProgressBar progressBar;

        public DoWork(ProgressBar progressBar){
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final Pin ret = PinsCallback.getInstance().get_other_info(pin.getIdPin());
                final List<SongHasMediatype> mediatypeList = SongCallback.getInstance().get_media_info(ret.getSongidSong().getIdSong());
                view.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        complete_info(ret, mediatypeList);

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            progressBar.setVisibility(View.INVISIBLE);
            content_layout.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }



        private void complete_info(Pin ret, List<SongHasMediatype> mediatypeList){
            artists_textview.setText(ret.getSongidSong().getArtistidArtist().getName());
            info.setText(ret.getInfo());
            formore.setText(ret.getSourceidSource().getSourceName());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            lp.weight=0.1f;
            source_layout.setLayoutParams(lp);
            Link sourceLink = new Link(ret.getSourceidSource().getSourceName(), ret.getSourceLink());

            if (sourceLink != null&&!sourceLink.equals("-"))
                formore.setOnClickListener(new URIClickListener(sourceLink.getUri()));
          /*  if (ret.getImage()>0)
                Picasso.with(view).load(ret.getImageUrl(view)).into(pin_fragment_image);*/

            loader.setVisibility(View.VISIBLE);
            pin_fragment_image.setVisibility(View.VISIBLE);
            final ProgressBar progressView = loader;
            Picasso.with(view)
                    .load(ret.getImageUrl(view))
                    .into(pin_fragment_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            pin_fragment_image.setImageResource(R.drawable.info_pin_placeholder);
                            progressView.setVisibility(View.GONE);

                        }
                    });




           // view.getSupportFragmentManager().beginTransaction()
             //       .add(R.id.youtube_fragment, new YoutubeFragment(), "info").commit();




           // arrow_button.setOnClickListener(new ArrowClickListener(args.getDouble(PinInfoFragment.ARG_LNG), args.getDouble(PinInfoFragment.ARG_LAT)));
           // share_button.setOnClickListener(new ShareListener(args.getDouble(PinInfoFragment.ARG_LNG), args.getDouble(PinInfoFragment.ARG_LAT), getContext().getPackageManager()));


            boolean hasyoutubelink=false, hasspotifylink=false;
            if(mediatypeList.isEmpty()){
                media_button.setAlpha(0.2f);
                media_button.setEnabled(false);
            }
            else{
                for(final SongHasMediatype media:mediatypeList){
                    if(media.getMediatypeDTO().getMedia().toLowerCase().equals("youtube")){
                        hasyoutubelink=true;
                        youtube_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SlidingUpPanelLayout slidingUpPanelLayout = view.findViewById(R.id.sliding_layout_youtube);
                                view.getSupportFragmentManager().beginTransaction()
                                        .add(R.id.youtube_fragment, new YoutubeFragment(slidingUpPanelLayout,media.getUrlMedia()), "info").commit();
                            }
                        });
                    }
                    else if(media.getMediatypeDTO().getMedia().toLowerCase().equals("spotify")){
                        hasspotifylink=true;
                        spotify_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(isPackageInstalled("com.spotify.music",view.getContext().getPackageManager()))
                                {
                                    String spotifyUri = media.getUrlMedia();

                                    Intent spotifyIntent = new Intent(Intent.ACTION_VIEW);
                                    spotifyIntent.setPackage("com.spotify.music");
                                    spotifyIntent.setData(Uri.parse(spotifyUri));
                                    view.getContext().startActivity(spotifyIntent);
                                }
                                else{
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music"));
                                    view.getContext().startActivity(intent);
                                }
                            }
                        });
                    }
                    }
                }

            media_button.setOnClickListener(new MediaButtonClickListener(buttons_layout, true, hasyoutubelink, hasspotifylink));
            back_button.setOnClickListener(new MediaButtonClickListener(buttons_layout, false, false, false));
          }


        private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
            try {
                packageManager.getPackageInfo(packagename, 0);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

    }
    }

