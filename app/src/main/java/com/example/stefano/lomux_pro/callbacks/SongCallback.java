package com.example.stefano.lomux_pro.callbacks;

import com.example.stefano.lomux_pro.model.SongHasMediatype;
import com.example.stefano.lomux_pro.rest.RestConfig;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Stefano on 19/10/2017.
 */

public class SongCallback {

    private static SongCallback songCallback;

    public static SongCallback getInstance(){
        if(songCallback == null)
            songCallback = new SongCallback();
        return songCallback;

    }
    public List<SongHasMediatype> get_media_info(int songId) throws IOException {

        Call<List<SongHasMediatype>> call = RestConfig.getInstance().getPinClient().getMediaInfo(songId);
        return call.execute().body();
    }

}
