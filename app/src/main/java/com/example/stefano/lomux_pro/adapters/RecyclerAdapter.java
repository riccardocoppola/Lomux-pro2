package com.example.stefano.lomux_pro.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.model.Itinerary;

import java.util.ArrayList;

/**
 * Created by Riccardo on 31/08/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItineraryHolder> {


    public interface OnItemClickListener {
        void onItemClick(Itinerary itinerary);
        void onItemLongClick(Itinerary itinerary);
    }


    private ArrayList<Itinerary> itineraries;
    private ArrayList<ImageButton> itinerary_images;
    private final OnItemClickListener listener;

    private int selected_itinerary = 0;




    public RecyclerAdapter(ArrayList<Itinerary> itineraries, OnItemClickListener listener) {
        this.itineraries = itineraries;
        this.listener = listener;
    }

    @Override
    public RecyclerAdapter.ItineraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itinerary_button_layout, parent, false);

        return new ItineraryHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ItineraryHolder holder, int position) {
        Itinerary itemItinerary = itineraries.get(position);
        holder.bindItinerary(itemItinerary, listener);



    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    public static class ItineraryHolder extends RecyclerView.ViewHolder {

        private ImageButton holderImageButton;
        private TextView holderText;
        private Itinerary itinerary;

        private static final String PHOTO_KEY = "PHOTO";

        public ItineraryHolder(View v) {
            super(v);

            holderImageButton = (ImageButton) v.findViewById(R.id.itinerary_button_image);

            holderText = (TextView) v.findViewById(R.id.itinerary_button_text);

        }

        public void hideItinerary() {
            holderImageButton.animate().alpha(0.5f).setDuration(200);
            holderText.animate().alpha(0.5f).setDuration(200);
        }

        public void showItinerary() {
            holderText.animate().alpha(1.0f).setDuration(200);
            holderImageButton.animate().alpha(1.0f).setDuration(200);
        }


        public void bindItinerary(final Itinerary itinerary, final OnItemClickListener listener) {
            this.itinerary = itinerary;
            holderText.setText(itinerary.getName());

            //TODO fix
            //if (itinerary.getImage_reference() != -1 ) {
              //  holderImageButton.setImageResource(itinerary.getImage_circle_reference());
            //}
            holderImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itinerary);
                    holderImageButton.animate().alpha(1.0f).setDuration(200);
                    holderText.animate().alpha(1.0f).setDuration(200);
                }
            });

            holderImageButton.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v){

                    listener.onItemClick(itinerary);
                    holderImageButton.animate().alpha(1.0f).setDuration(200);
                    holderText.animate().alpha(1.0f).setDuration(200);
                    listener.onItemLongClick(itinerary);

                    return true;
                }

            });


            if (!itinerary.getName().equals("a0")) {

                holderImageButton.setAlpha(0.5f);
                holderText.setAlpha(0.5f);
            }

        }


    }


}
