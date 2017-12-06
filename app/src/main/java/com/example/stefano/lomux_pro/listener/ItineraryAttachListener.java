package com.example.stefano.lomux_pro.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stefano.lomux_pro.LomuxMapActivity;
import com.example.stefano.lomux_pro.R;

/**
 * Created by Riccardo on 04/12/2017.
 */

public class ItineraryAttachListener implements RecyclerView.OnChildAttachStateChangeListener {

    private LomuxMapActivity context = null;

    public ItineraryAttachListener(LomuxMapActivity context) {
        this.context = context;
    }

    @Override
    public void onChildViewAttachedToWindow(View view) {
        ImageButton i = (ImageButton) view.findViewById(R.id.itinerary_button_image);
        TextView t = (TextView) view.findViewById(R.id.itinerary_button_text);
        if (!t.getText().equals(context.getSelected_itinerary())) {
            i.setAlpha(0.5f);
            t.setAlpha(0.5f);
        }
        else {
            i.setAlpha(1f);
            t.setAlpha(1f);
        }
    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {

    }


}
