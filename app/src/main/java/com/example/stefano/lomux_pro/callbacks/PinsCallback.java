package com.example.stefano.lomux_pro.callbacks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stefano.lomux_pro.LomuxMapActivity;
import com.example.stefano.lomux_pro.model.Album;
import com.example.stefano.lomux_pro.model.Artist;
import com.example.stefano.lomux_pro.model.Pin;
import com.example.stefano.lomux_pro.model.PinHasPintype;
import com.example.stefano.lomux_pro.model.Pintype;
import com.example.stefano.lomux_pro.model.Song;
import com.example.stefano.lomux_pro.rest.RestConfig;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Stefano on 16/10/2017.
 */

public class PinsCallback {
    private static PinsCallback pinsCallback = null;


    public static PinsCallback getInstance() {
        if (pinsCallback == null)
            pinsCallback = new PinsCallback();
        return pinsCallback;

    }

    public void get_local_pins(final GoogleMap mMap, LatLngBounds maxArea, List<String> ids, final LomuxMapActivity context) {

        LatLng n_e = maxArea.northeast;
        LatLng s_w = maxArea.southwest;/*
        Call<List<Pin>> call = RestConfig.getInstance().getPinClient().getLocalPins(n_e.latitude, n_e.longitude, s_w.latitude, s_w.longitude, ids);
        call.enqueue(new Callback<List<Pin>>() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<Pin>> call, Response<List<Pin>> response) {

                if (response.isSuccessful()&&response.body()!=null) {
                    getPintypeByPin(response.body(), context);
                    //context.addPins(response.body());
                }
                else{
                    onFailure(call,new Throwable());
                }
            }

            @Override
            public void onFailure(Call<List<Pin>> call, Throwable t) {
                Toast.makeText(context,"Server connection error", Toast.LENGTH_SHORT);
                Log.d("FAL","FALLITO"+ t.getMessage());

            }
        });*/

        DoWorkPin task = new DoWorkPin(maxArea.northeast, maxArea.southwest, ids, context);

        task.execute();
    }


    public Pin get_other_info(String pinId) throws IOException {

        Call<Pin> call = RestConfig.getInstance().getPinClient().getOtherInfo(pinId);
        Pin res = call.execute().body();
        return res;
    }

    public List<Song> getSongsByPin(String idPin) throws IOException {
        Call<List<Song>> call = RestConfig.getInstance().getPinClient().getSongsByPin(idPin);
        List<Song> res = call.execute().body();
        return res;
    }

    public List<Artist> getArtistsByPin(String idPin) throws IOException {
        Call<List<Artist>> call = RestConfig.getInstance().getPinClient().getArtistsByPin(idPin);
        List<Artist> res = call.execute().body();
        return res;
    }

    public List<Album> getAlbumsByPin(String idPin) throws IOException {
        Call<List<Album>> call = RestConfig.getInstance().getPinClient().getAlbumsByPin(idPin);
        List<Album> res = call.execute().body();
        return res;
    }

    public static void getPintypeByPin(List<Pin> pins, final LomuxMapActivity context) {
        for (final Pin p : pins) {
            Call<List<PinHasPintype>> call = RestConfig.getInstance().getPinClient().getPintypeByPin(p.getIdPin());
            call.enqueue(new Callback<List<PinHasPintype>>() {

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<List<PinHasPintype>> call, Response<List<PinHasPintype>> response) {

                    if (response.isSuccessful() && response.body() != null) {

                        p.setPinHasPintypeDTOList(response.body());
                    } else {
                        onFailure(call, new Throwable());
                    }
                }

                @Override
                public void onFailure(Call<List<PinHasPintype>> call, Throwable t) {
                    Toast.makeText(context, "Server connection error", Toast.LENGTH_SHORT);
                    Log.d("FAL", "FALLITO" + t.getMessage());

                }
            });

        }
        context.addPins(pins);
    }

    class DoWorkPin extends AsyncTask<Void, Void, Void> {
        LatLng n_e;
        LatLng s_w;
        List<String> ids;
        LomuxMapActivity context;


        public DoWorkPin(LatLng northeast, LatLng southwest, List<String> ids, LomuxMapActivity context) {
            n_e = northeast;
            s_w = southwest;
            this.ids = ids;
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Call<List<Pin>> call = RestConfig.getInstance().getPinClient().getLocalPins(n_e.latitude, n_e.longitude, s_w.latitude, s_w.longitude, ids);
                final List<Pin> pins= call.execute().body();
                for(Pin p:pins){
                    Call<List<PinHasPintype>> call2=RestConfig.getInstance().getPinClient().getPintypeByPin(p.getIdPin());
                    p.setPinHasPintypeDTOList(call2.execute().body());
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.addPins(pins);

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}
