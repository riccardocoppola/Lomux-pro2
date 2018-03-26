package com.example.stefano.lomux_pro.callbacks;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.stefano.lomux_pro.LomuxMapActivity;
import com.example.stefano.lomux_pro.model.Album;
import com.example.stefano.lomux_pro.model.Artist;
import com.example.stefano.lomux_pro.model.Pinnable;
import com.example.stefano.lomux_pro.model.Song;
import com.example.stefano.lomux_pro.rest.RestConfig;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Stefano on 16/10/2017.
 */

public class PinsCallback {
    private static PinsCallback pinsCallback = null;
    private Call<List<Pinnable>> call;


    public static PinsCallback getInstance() {
        if (pinsCallback == null)
            pinsCallback = new PinsCallback();
        return pinsCallback;

    }

    public Call<List<Pinnable>> getCall() {
        return call;
    }

    public void get_local_pins(final GoogleMap mMap, LatLngBounds maxArea, List<String> ids, final LomuxMapActivity context) {

        LatLng n_e = maxArea.northeast;
        LatLng s_w = maxArea.southwest;
        call = RestConfig.getInstance().getPinClient().getLocalPins(n_e.latitude, n_e.longitude, s_w.latitude, s_w.longitude, ids);
        call.enqueue(new Callback<List<Pinnable>>() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<Pinnable>> call, Response<List<Pinnable>> response) {

                if (response.isSuccessful()&&response.body()!=null) {

                    context.addPins(response.body());
                }
                else{
                    onFailure(call,new Throwable());
                }
            }

            @Override
            public void onFailure(Call<List<Pinnable>> call, Throwable t) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context.getApplicationContext(), "Connection Failure. Check your connection and retry", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("FAL","FALLITO"+ t.getMessage());

            }
        });

        }


    public Pinnable get_other_info(String pinId) throws IOException {

        Call<Pinnable> call = RestConfig.getInstance().getPinClient().getOtherInfo(pinId);
        Pinnable res = call.execute().body();
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

}
