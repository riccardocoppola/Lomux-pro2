package com.example.stefano.lomux_pro.rest;

import com.example.stefano.lomux_pro.model.Itinerary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Stefano on 07/12/2017.
 */

public interface ItineraryClient {
    @POST("read_database/get_itinerary")
   // @FormUrlEncoded
    Call<List<Itinerary>> getItinerary();
}
