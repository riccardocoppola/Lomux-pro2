package com.example.stefano.lomux_pro.rest;

/**
 * Created by FrancescoMargiotta on 18/07/2017.
 */
import com.example.stefano.lomux_pro.model.Itinerary;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestConfig {

    private static RestConfig ourInstance=null;
    private PinClient pinClient;
    private ItineraryClient itineraryClient;

    public static RestConfig getInstance() {
        if(ourInstance==null)
            ourInstance= new RestConfig();
        return ourInstance;
    }
    private RestConfig() {
        Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("https://lomux-webapp-lomux.193b.starter-ca-central-1.openshiftapps.com/rest/")
               // .baseUrl("http://192.168.169.3:8080/lomux_webapp/rest/") //per provare in locale
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pinClient = retrofit.create(PinClient.class);
        itineraryClient=retrofit.create(ItineraryClient.class);
    }


    public PinClient getPinClient() {
        return pinClient;
    }
    public ItineraryClient getItineraryClient() {
        return itineraryClient;
    }


}
