package com.example.stefano.lomux_pro.callbacks;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.stefano.lomux_pro.LomuxMapActivity;
import com.example.stefano.lomux_pro.model.Itinerary;
import com.example.stefano.lomux_pro.model.Pin;
import com.example.stefano.lomux_pro.rest.RestConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Stefano on 07/12/2017.
 */

public class ItineraryCallback {
    private static ItineraryCallback itineraryCallback= null;
    private Call<List<Itinerary>> call;


    public static ItineraryCallback getInstance() {
        if (itineraryCallback == null)
            itineraryCallback = new ItineraryCallback();
        return itineraryCallback;

    }

    public Call<List<Itinerary>> getCall() {
        return call;
    }

    public void get_itinerary(final LomuxMapActivity context) {
        call = RestConfig.getInstance().getItineraryClient().getItinerary();
        call.enqueue(new Callback<List<Itinerary>>() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {

                if (response.isSuccessful()&&response.body()!=null) {

                    context.addItinerary(response.body());
                }
                else{
                    onFailure(call,new Throwable());
                }
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {
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
}
