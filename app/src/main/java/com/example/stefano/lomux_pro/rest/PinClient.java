package com.example.stefano.lomux_pro.rest;


import com.example.stefano.lomux_pro.model.Album;
import com.example.stefano.lomux_pro.model.Artist;
import com.example.stefano.lomux_pro.model.Pinnable;
import com.example.stefano.lomux_pro.model.PinHasPintype;
import com.example.stefano.lomux_pro.model.Song;
import com.example.stefano.lomux_pro.model.SongHasMediatype;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by FrancescoMargiotta on 18/07/2017.
 */

public interface PinClient {
    //@POST("read_database/get_local_pins")
    @GET("read_database/get_local_pins")
    //@FormUrlEncoded
    Call<List<Pinnable>> getLocalPins(@Query("neLat") double neLat, @Query("neLon") double neLon, @Query("swLat") double swLat, @Query("swLon") double swLon, @Query("ids") List<String> ids);

    //@POST("read_database/get_local_pins")
    @GET("read_database/get_other_info")
    //@FormUrlEncoded
    Call<Pinnable> getOtherInfo(@Query("pinId") String id);

    //@POST("read_database/get_local_pins")
    @GET("read_database/get_media_info")
    //@FormUrlEncoded
    Call<List<SongHasMediatype>> getMediaInfo(@Query("songId") int id);

    @POST("read_database/get_songs_by_pin")
    @FormUrlEncoded
    Call<List<Song>> getSongsByPin(@Field("pinId") String pinId);

    @POST("read_database/get_albums_by_pin")
    @FormUrlEncoded
    Call<List<Album>> getAlbumsByPin(@Field("pinId") String pinId);

    @POST("read_database/get_artists_by_pin")
    @FormUrlEncoded
    Call<List<Artist>> getArtistsByPin(@Field("pinId") String pinId);

    @POST("read_database/get_pintype_by_pin")
    @FormUrlEncoded
    Call<List<PinHasPintype>> getPintypeByPin(@Field("pinId") String pinId);

}
