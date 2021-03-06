package com.example.stefano.lomux_pro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
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
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
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
    private LinearLayout address_textview_horizontal_layout;
    private TextView artists_textview;
    private LinearLayout artists_textview_horizontal_layout;
    private TextView songs_textview;
    private LinearLayout songs_textview_horizontal_layout;
    private TextView lyrics_textview;
    private LinearLayout lyrics_textview_horizontal_layout;
    private TextView albums_textview;
    private LinearLayout albums_textview_horizontal_layout;

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
        this.songs_textview = view.findViewById(R.id.pin_fragment_layout_textview_songs);
        this.albums_textview = view.findViewById(R.id.pin_fragment_layout_textview_albums);
        this.lyrics_textview = view.findViewById(R.id.pin_fragment_layout_textview_lyrics);
        this.address_textview_horizontal_layout = view.findViewById(R.id.pin_fragment_layout_textview_address_horizontal_layout);
        this.albums_textview_horizontal_layout = view.findViewById(R.id.pin_fragment_layout_textview_albums_horizontal_layout);
        this.artists_textview_horizontal_layout = view.findViewById(R.id.pin_fragment_layout_textview_artists_horizontal_layout);
        this.songs_textview_horizontal_layout = view.findViewById(R.id.pin_fragment_layout_textview_songs_horizontal_layout);
        this.lyrics_textview_horizontal_layout = view.findViewById(R.id.pin_fragment_layout_textview_lyrics_horizontal_layout);



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
        this.content_layout = view.findViewById(R.id.pin_fragment_layout_inner_left_vertical_layout);
        this.progressBar = view.findViewById(R.id.progress_other_info);
        this.source_layout = view.findViewById(R.id.pin_fragment_layout_linearlayout_for_source);
        this.loader = view.findViewById(R.id.progress_image);
    }

   public void setTitle(){
       /*set the title info with the appropriate color and icon*/

       title.setText(pin.getTitle());
       subtitle_textview.setText(pin.getSubtitle());
       //TODO capire come è implementato il subtitle nel database, eventualmente aggiungerlo

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

       loader.setVisibility(View.VISIBLE);
       pin_fragment_image.setVisibility(View.VISIBLE);
       final ProgressBar progressView = loader;
       Picasso.with(view)
               .load(pin.getImageUrl(view))
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

            //1) Address: geolocation from the lat-long, and address inside address_textview
            Geocoder geocoder = new Geocoder(view, Locale.getDefault());
            List<Address> addresses = null;


            try {
                addresses = geocoder.getFromLocation(
                        pin.getLat().doubleValue(),
                        pin.getLon().doubleValue(),
                        // In this sample, get just a single address.
                        1);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (addresses.size() != 0 && addresses != null) {
               address_textview_horizontal_layout.setVisibility(View.VISIBLE);
               Address address = addresses.get(0);
               String[] address_parts = address.getAddressLine(0).split(",");
                address_textview.setText(address_parts[0]);
                address_textview.setTypeface(null, Typeface.BOLD);
                address_textview.setOnClickListener(new ArrowClickListener(pin.getLon().doubleValue(), pin.getLat().doubleValue()));
            }

            else {
                address_textview_horizontal_layout.setVisibility(View.GONE);
                Log.wtf("address", "not found");
            }


            //2) artists: get the artists from the pin; show them in the artists textview
            //relation: pinhasartist
            //TODO: for now the artist is just one, and linked to the individual song of the pin; update with the new model with the multiple possible artists
            String artists = ret.getSongidSong().getArtistidArtist().getName();
            if (artists != null && !(artists.equals("-"))) {
                artists_textview_horizontal_layout.setVisibility(View.VISIBLE);
                artists_textview.setText(ret.getSongidSong().getArtistidArtist().getName());
            }
            else {
                artists_textview_horizontal_layout.setVisibility(View.GONE);
            }


            //3) songs: get the songs id from the pin; show the songs in the songs textview
            //relation: pinhassongs
            //then use relation songhasmediatype to put the right link on the individual songs
            //TODO: for now the song is just one; update with the new model with the multiple possible songs
            //TODO: capire perché le song sono uguali agli artisti
            String songs = ret.getSongidSong().getTitle();
            if (songs != null && !(songs.equals("-"))) {
                songs_textview_horizontal_layout.setVisibility(View.VISIBLE);
                songs_textview.setText(ret.getSongidSong().getArtistidArtist().getName());
            }
            else {
                songs_textview_horizontal_layout.setVisibility(View.GONE);
            }


            //4) lyrics: if present, get the lyrics id and show preview in the textview, plus link to the site
            //relation: songhaslyrics
            String lyrics = ret.getSongidSong().getLyrics();
            if (lyrics != null && !(lyrics.equals("-"))) {
                lyrics_textview_horizontal_layout.setVisibility(View.VISIBLE);
                lyrics_textview.setText(ret.getSongidSong().getArtistidArtist().getName());
            }
            else {
                lyrics_textview_horizontal_layout.setVisibility(View.GONE);
            }

            //5) albums: if present, get the albums and show the names in the textview, plus link if they have mediatype
            //relations: pinhasalbum, albumhasmediatype
            String albums = ret.getSongidSong().getLyrics();
            if (albums != null && !(albums.equals("-"))) {
                albums_textview_horizontal_layout.setVisibility(View.VISIBLE);
                albums_textview.setText(ret.getSongidSong().getArtistidArtist().getName());
            }
            else {
                albums_textview_horizontal_layout.setVisibility(View.GONE);
            }

            //6) set description of the pin in the info text view, inside the scrollable view. The scrollable view will
            //also contain the source (if present; if not, the visibility of the layout containing the source has to
            //be set to gone)

            info.setText(ret.getInfo());

            SpannableString formore_text = new SpannableString(ret.getSourceidSource().getSourceName());
            formore_text.setSpan(new UnderlineSpan(), 0, ret.getSourceidSource().getSourceName().length(), 0);
            formore.setText(formore_text);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            lp.weight=0.1f;
            source_layout.setLayoutParams(lp);
            Link sourceLink = new Link(ret.getSourceidSource().getSourceName(), ret.getSourceLink());

            if (sourceLink != null&&!sourceLink.getText().equals("-")) {
                formore.setOnClickListener(new URIClickListener(sourceLink.getUri()));
                lp.weight=0.1f;
                source_layout.setLayoutParams(lp);

            }
            else {
                lp.weight=0.0f;
                source_layout.setLayoutParams(lp);

            }






           // view.getSupportFragmentManager().beginTransaction()
             //       .add(R.id.youtube_fragment, new YoutubeFragment(), "info").commit();


            //7) set button for sharing
            //TODO recuperare codice sharelistener
            //share_button.setOnClickListener(new ShareListener(args.getDouble(PinInfoFragment.ARG_LNG), args.getDouble(PinInfoFragment.ARG_LAT), getContext().getPackageManager()));

            //TODO FINITA LA PROVA RIABILITARE TUTTO DOPO!!!!!!!!!!!!!!!
            return;


//            boolean hasyoutubelink=false, hasspotifylink=false;
//            if(mediatypeList.isEmpty()){
//                media_button.setAlpha(0.2f);
//                media_button.setEnabled(false);
//            }
//            else{
//                for(final SongHasMediatype media:mediatypeList){
//                    if(media.getMediatypeDTO().getMedia().toLowerCase().equals("youtube")){
//                        hasyoutubelink=true;
//                        youtube_button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                SlidingUpPanelLayout slidingUpPanelLayout = view.findViewById(R.id.sliding_layout_youtube);
//                                view.getSupportFragmentManager().beginTransaction()
//                                        .add(R.id.youtube_fragment, new YoutubeFragment(slidingUpPanelLayout,media.getUrlMedia()), "info").commit();
//                            }
//                        });
//                    }
//                    else if(media.getMediatypeDTO().getMedia().toLowerCase().equals("spotify")){
//                        hasspotifylink=true;
//                        spotify_button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                if(isPackageInstalled("com.spotify.music",view.getContext().getPackageManager()))
//                                {
//                                    String spotifyUri = media.getUrlMedia();
//
//                                    Intent spotifyIntent = new Intent(Intent.ACTION_VIEW);
//                                    spotifyIntent.setPackage("com.spotify.music");
//                                    spotifyIntent.setData(Uri.parse(spotifyUri));
//                                    view.getContext().startActivity(spotifyIntent);
//                                }
//                                else{
//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music"));
//                                    view.getContext().startActivity(intent);
//                                }
//                            }
//                        });
//                    }
//                    }
//                }
//
//            media_button.setOnClickListener(new MediaButtonClickListener(buttons_layout, true, hasyoutubelink, hasspotifylink));
//            back_button.setOnClickListener(new MediaButtonClickListener(buttons_layout, false, false, false));

            //TODO
            //retrieve address from pins//address_textview.setText(ret.getAddress());

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

    public class ArrowClickListener implements View.OnClickListener
    {

        private double lng;
        private double lat;

        public ArrowClickListener(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
            Log.d("Directions", "created listener");
        }

        @Override
        public void onClick(View v)
        {
            Log.d("Directions", String.valueOf(lng) + " - " + String.valueOf(lat));
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+String.valueOf(lat)+","+String.valueOf(lng));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            view.startActivity(mapIntent);

        }

    }






    }

