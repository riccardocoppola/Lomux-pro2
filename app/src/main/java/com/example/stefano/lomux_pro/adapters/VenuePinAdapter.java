package com.example.stefano.lomux_pro.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stefano.lomux_pro.R;

import java.util.ArrayList;
import java.util.List;

public class VenuePinAdapter extends RecyclerView.Adapter<VenuePinAdapter.VenueHolder> {
    private List<String> mDataset;

    public VenuePinAdapter(List<String> myDataset, int operationCode, ProgressBar bar) {
        if(operationCode == 0){
            //in loading
            bar.setVisibility(View.VISIBLE);
            mDataset = new ArrayList<>();
        }
        else {
            bar.setVisibility(View.GONE);
            mDataset = myDataset;
        }
    }

        // Create new views (invoked by the layout manager)
        @Override
        public VenuePinAdapter.VenueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.venue_pin_model, parent, false);
            VenueHolder vh = new VenueHolder(v);
            return vh;
        }

    @Override
    public void onBindViewHolder(@NonNull VenueHolder holder, int position) {
        holder.bindItinerary(mDataset.get(position));
    }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }




    public static class VenueHolder extends RecyclerView.ViewHolder {

     //   private ImageButton holderImageButton;
        private TextView holderText;
       // private Itinerary itinerary;


        public VenueHolder(View v) {
            super(v);

            //holderImageButton = (ImageButton) v.findViewById(R.id.itinerary_button_image);

            holderText = (TextView) v.findViewById(R.id.textView2);

        }

        public void bindItinerary(String test) {
            holderText.setText(test);

    }



}
}
