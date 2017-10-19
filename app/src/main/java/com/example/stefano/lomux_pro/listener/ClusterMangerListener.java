package com.example.stefano.lomux_pro.listener;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.stefano.lomux_pro.LomuxMapActivity;
import com.example.stefano.lomux_pro.PinInfoSlidedPanel;
import com.example.stefano.lomux_pro.PinRenderer;
import com.example.stefano.lomux_pro.model.Pin;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.concurrent.Executors;

/**
 * Created by Stefano on 18/10/2017.
 */

public class ClusterMangerListener implements GoogleMap.OnMarkerClickListener {

    ClusterManager clusterManager;
    PinRenderer pinRenderer;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LomuxMapActivity view;

    public ClusterMangerListener(ClusterManager<Pin> clusterManager, PinRenderer pinRenderer, SlidingUpPanelLayout slidingUpPanelLayout, LomuxMapActivity view) {
        this.clusterManager = clusterManager;
        this.pinRenderer = pinRenderer;
        this.slidingUpPanelLayout = slidingUpPanelLayout;
        this.view = view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Pin pin = pinRenderer.getClusterItem(marker);
        if(pin!=null)
            return onClusterItemClick(marker,pin);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        return clusterManager.onMarkerClick(marker);
    }


    public boolean onClusterItemClick(final Marker marker, final Pin pin) {

        final PinInfoSlidedPanel pinInfoSlidedPanel = new PinInfoSlidedPanel(view, pin);
        pinInfoSlidedPanel.setTitle();
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            private boolean start=false;
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                 if(slideOffset>0) { //The panel is slieded up
                     if(!start){
                        pinInfoSlidedPanel.setAdditionalInfo();
                        start=true;
                     }

                 }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        return clusterManager.onMarkerClick(marker);

    }
}
