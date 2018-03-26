package com.example.stefano.lomux_pro.listener;

import android.util.Log;
import android.view.View;

import com.example.stefano.lomux_pro.LomuxMapActivity;
import com.example.stefano.lomux_pro.PinInfoSlidedPanel;
import com.example.stefano.lomux_pro.PinRenderer;
import com.example.stefano.lomux_pro.model.Pinnable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by Stefano on 18/10/2017.
 */

public class ClusterMangerListener implements GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterClickListener<Pinnable> {

    ClusterManager clusterManager;
    PinRenderer pinRenderer;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LomuxMapActivity view;
    GoogleMap map;

    public ClusterMangerListener(ClusterManager<Pinnable> clusterManager, PinRenderer pinRenderer, SlidingUpPanelLayout slidingUpPanelLayout, LomuxMapActivity view, GoogleMap map) {
        this.clusterManager = clusterManager;
        this.pinRenderer = pinRenderer;
        this.slidingUpPanelLayout = slidingUpPanelLayout;
        this.view = view;
        this.map = map;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Pinnable pin = pinRenderer.getClusterItem(marker);
        if(pin!=null) {
            return onClusterItemClick(marker, pin);
        }
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        return clusterManager.onMarkerClick(marker);
        //return true;
    }


    public boolean onClusterItemClick(final Marker marker, final Pinnable pin) {

        final PinInfoSlidedPanel pinInfoSlidedPanel = new PinInfoSlidedPanel(view, pin);
        pinInfoSlidedPanel.setTitle();
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            private boolean start=false;
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                 if(slideOffset>0) { //The panel is slieded up
                     map.getUiSettings().setScrollGesturesEnabled(false);

                     if(!start){
                        pinInfoSlidedPanel.setAdditionalInfo();
                        start=true;
                     }

                 }
                 else {
                     map.getUiSettings().setScrollGesturesEnabled(true);
                 }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        //return clusterManager.onMarkerClick(marker);
        return true;
    }

    @Override
    public boolean onClusterClick(Cluster<Pinnable> cluster) {
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                cluster.getPosition(), (float) Math.floor(map
//                        .getCameraPosition().zoom + 1)), 300,
//                null);

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        final LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));


        Log.wtf("cluster", "cluster clicked");
        return true;
    }
}
