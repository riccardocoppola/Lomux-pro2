package com.example.stefano.lomux_pro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.stefano.lomux_pro.adapters.RecyclerAdapter;
import com.example.stefano.lomux_pro.callbacks.PinsCallback;
import com.example.stefano.lomux_pro.fragment.YoutubeFragment;
import com.example.stefano.lomux_pro.listener.ClusterMangerListener;
import com.example.stefano.lomux_pro.listener.DrawnerItemClickListener;
import com.example.stefano.lomux_pro.listener.ItineraryAttachListener;
import com.example.stefano.lomux_pro.listener.MapChangesListener;
import com.example.stefano.lomux_pro.model.Itinerary;
import com.example.stefano.lomux_pro.model.Pin;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class LomuxMapActivity extends FragmentActivity implements RecyclerAdapter.OnItemClickListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapClickListener,YoutubeFragment.OnYoutubeBackListener {

    private GoogleMap mMap;
    private final static LatLng london_center = new LatLng(51.509865, -0.118092);
    private final  int panelInfoHeigth = 200;
    private ClusterManager<Pin> mClusterManager;
    private SearchView searchView;
    private List<String> ids;
    SlidingUpPanelLayout slidingUpPanelLayout;
    PinRenderer pinRenderer;

    //itinerary recycler view
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private String selected_itinerary = null;

    public String getSelected_itinerary() {
        return selected_itinerary;
    }

    //TODO
    //load di TUTTI gli itinerari a start dell'applicazione, chiamare sincrona prima di getlocalpins
    private ArrayList<Itinerary> getItineraries() {
        ArrayList<Itinerary> tmp_itinerary_array = new ArrayList<Itinerary>();
        Itinerary tmp_itinerary0 = new Itinerary();
        tmp_itinerary0.setName("a0");
        tmp_itinerary0.setInfo("bbbbbb");
        Itinerary tmp_itinerary1 = new Itinerary();
        tmp_itinerary1.setName("a1");
        tmp_itinerary1.setInfo("bbbbbb");
        Itinerary tmp_itinerary2 = new Itinerary();
        tmp_itinerary2.setName("a2");
        tmp_itinerary2.setInfo("bbbbbb");
        Itinerary tmp_itinerary3 = new Itinerary();
        tmp_itinerary3.setName("a3");
        tmp_itinerary3.setInfo("bbbbbb");
        Itinerary tmp_itinerary4 = new Itinerary();
        tmp_itinerary4.setName("a4");
        tmp_itinerary4.setInfo("bbbbbb");
        Itinerary tmp_itinerary5 = new Itinerary();
        tmp_itinerary5.setName("a5");
        tmp_itinerary5.setInfo("bbbbbb");

        tmp_itinerary_array.add(tmp_itinerary0);
        tmp_itinerary_array.add(tmp_itinerary1);
        tmp_itinerary_array.add(tmp_itinerary2);
        tmp_itinerary_array.add(tmp_itinerary3);
        tmp_itinerary_array.add(tmp_itinerary4);
        tmp_itinerary_array.add(tmp_itinerary5);

        selected_itinerary="a0";
        return tmp_itinerary_array;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomux_map);
        // create our manager instance after the content view is set

        //super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new DrawnerItemClickListener(this));
        //searchView = findViewById(R.id.searchbar);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setOverlayed(true);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        ids = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //itinerary recycler view init
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapter(getItineraries(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new ItineraryAttachListener(this));


    }

    @Override
    public void onItemClick(Itinerary itinerary) {

        if (!itinerary.getName().equals(selected_itinerary)) {
            for (int childCount = mRecyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                final RecyclerAdapter.ItineraryHolder holder = (RecyclerAdapter.ItineraryHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                holder.hideItinerary();
            }

            selected_itinerary = itinerary.getName();
//
//            if (itinerary.getID() == 0) {
//                placeAllPins();
//            } else {
//                placeItineraryPins(itinerary.getID());
//            }
//            closePinFragment();
//
        }



    }

    @Override
    public void onItemLongClick(Itinerary itinerary){

//        Log.d("longclicked", itinerary.getName());
//
//        Intent intent = new Intent(this, ItineraryDetailsActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("itinerary", itinerary);
//        intent.putExtras(bundle);
//
//        closePinFragment();
//
//        startActivityForResult(intent, ITINERARY_DETAIL_REQUEST);

        Log.wtf("itinerary", itinerary.getName() + "longclicked");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        float zoom=13.5f;
        // Add a marker in London and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london_center));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mClusterManager = new ClusterManager<>(this, mMap);
        mClusterManager.setAnimation(true);
        pinRenderer = new PinRenderer(this.getApplicationContext(), mMap, mClusterManager);
        mClusterManager.setRenderer(pinRenderer);


        ClusterMangerListener cml =new ClusterMangerListener(mClusterManager,pinRenderer,slidingUpPanelLayout,this, mMap);
        mClusterManager.setOnClusterClickListener(cml);
        mMap.setOnMarkerClickListener(cml);
        //mMap.setOnMarkerClickListener(new ClusterMangerListener(mClusterManager,pinRenderer,slidingUpPanelLayout,this));

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLoadedCallback(this);

    }
    public List<String> getListIds(){
        return ids;
    }

    @Override
    public void onMapLoaded() {
        MapChangesListener mapChangesListener =new MapChangesListener(mMap,this);
        mMap.setOnCameraIdleListener(mapChangesListener);
        PinsCallback.getInstance().get_local_pins(mMap,mapChangesListener.getActualVisibleArea(),ids,this);
    }

    public void addPins(List<Pin> pins){

        for(Pin elem:pins){
            ids.add(elem.getIdPin());
            mClusterManager.addItem(elem);
        }
        mClusterManager.cluster();
    }

    public void clusterManagerOnCameraIdle(){
        mClusterManager.onCameraIdle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMapClick(LatLng latLng) {
        if (slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED)
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        else if(slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED)
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        View v =getCurrentFocus();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED) {
            // if PinInfoSlider is open
            // check if youtube is playing
            SlidingUpPanelLayout youtubePlayerSlider = findViewById(R.id.sliding_layout_youtube);
            if (youtubePlayerSlider.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
                youtubePlayerSlider.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            else
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        }
        else if(slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED)
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        else
            super.onBackPressed();


    }

    @Override
    public void onYoutubeBack() {

        Log.d("BACK","BACK");
    }
}
