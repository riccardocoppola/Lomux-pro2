package com.example.stefano.lomux_pro.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.stefano.lomux_pro.PinRenderer;
import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.UserManager;
import com.example.stefano.lomux_pro.adapters.RecyclerAdapter;
import com.example.stefano.lomux_pro.callbacks.ItineraryCallback;
import com.example.stefano.lomux_pro.callbacks.PinsCallback;
import com.example.stefano.lomux_pro.fragment.YoutubeFragment;
import com.example.stefano.lomux_pro.listener.ClusterMangerListener;
import com.example.stefano.lomux_pro.listener.DrawnerItemClickListener;
import com.example.stefano.lomux_pro.listener.ItineraryAttachListener;
import com.example.stefano.lomux_pro.listener.MapChangesListener;
import com.example.stefano.lomux_pro.model.Event;
import com.example.stefano.lomux_pro.model.Gallery;
import com.example.stefano.lomux_pro.model.Itinerary;
import com.example.stefano.lomux_pro.model.Pin;
import com.example.stefano.lomux_pro.model.Pinnable;
import com.example.stefano.lomux_pro.model.Venue;
import com.example.stefano.lomux_pro.model.VenuePin;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.clustering.ClusterManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LomuxMapActivity extends FragmentActivity implements RecyclerAdapter.OnItemClickListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapClickListener, YoutubeFragment.OnYoutubeBackListener {

    private GoogleMap mMap;
    private final static LatLng turin_center = new LatLng(45.05, 7.666667);
    private ClusterManager<Pin> mClusterManager;
    private FirebaseFirestore database;
    private List<String> ids;
    SlidingUpPanelLayout slidingUpPanelLayout;
    PinRenderer pinRenderer;


    //itinerary recycler view
    private RecyclerView mRecyclerView;
    private String selected_itinerary = null;
    private FirebaseUser localUser;
    private NavigationView navigationView;
    public String getSelected_itinerary() {
        return selected_itinerary;
    }


    //TODO
    //load di TUTTI gli itinerari a start dell'applicazione, chiamare sincrona prima di getlocalpins
    private ArrayList<Itinerary> getItineraries(List<Itinerary> itineraries) {
        ArrayList<Itinerary> tmp_itinerary_array = new ArrayList<>(itineraries);

        Itinerary tmp_itinerary0 = new Itinerary();
        tmp_itinerary0.setName("All Pins");
        tmp_itinerary0.setInfo("all pins");
        tmp_itinerary_array.add(0, tmp_itinerary0);
       /* Itinerary tmp_itinerary1 = new Itinerary();
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
*/
        selected_itinerary = "All Pins";
        return tmp_itinerary_array;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomux_map);
        navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new DrawnerItemClickListener(this));
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setOverlayed(true);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        database = FirebaseFirestore.getInstance();
        localUser = UserManager.getLocalUser();
        setUserInfoOnDrawnerMenu();
        ids = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        float zoom = 13.5f;
        // Add a marker in London and move the camera
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(turin_center));

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mClusterManager = new ClusterManager<>(this, mMap);
        mClusterManager.setAnimation(true);
        pinRenderer = new PinRenderer(this.getApplicationContext(), mMap, mClusterManager);
        mClusterManager.setRenderer(pinRenderer);


       // ClusterMangerListener cml = new ClusterMangerListener(mClusterManager, pinRenderer, slidingUpPanelLayout, this, mMap);
       // mClusterManager.setOnClusterClickListener(cml);
       // mMap.setOnMarkerClickListener(cml);
        //mMap.setOnMarkerClickListener(new ClusterMangerListener(mClusterManager,pinRenderer,slidingUpPanelLayout,this));

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLoadedCallback(this);

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
    public void onItemLongClick(Itinerary itinerary) {

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


    public List<String> getListIds() {
        return ids;
    }

    @Override
    public void onMapLoaded() {
        MapChangesListener mapChangesListener = new MapChangesListener(mMap, this);
        mMap.setOnCameraIdleListener(mapChangesListener);
        //PinsCallback.getInstance().get_local_pins(mMap, mapChangesListener.getActualVisibleArea(), ids, this);


     /*   database.collection(Pin.class.getSimpleName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Pin p;
                        if(document.getId().equals("4djvffLElntalactr4Sc"))
                        {p= document.toObject(Pin.class);
                        if (p instanceof VenuePin)
                            Log.d("Pin", p.getTitle());
                        }
                       // mClusterManager.addItem(p);
                        Log.d("Pin", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w("ERROR", "Error getting documents.", task.getException());
                }
            }
        });*/
     if(localUser==null)
         UserManager.login(this);
     else
         Log.d("OK","OK USER"+localUser.getEmail());
    //    Intent intentGetMessage=new Intent(this,UserManager.class);
     // startActivityForResult(intentGetMessage,11);
        Venue v = new Venue("test","NAME","INFO", new ArrayList<Event>(),new GeoPoint(10,10), null);
        database.collection(Venue.class.getSimpleName())
                .add(v)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference doc) {
                        Log.d("SUCCESS", "DocumentSnapshot successfully written!"+doc.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error writing document", e);
                    }
                });
        ItineraryCallback.getInstance().get_itinerary(this);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {

    }

    public void addPins(List<Pinnable> pins) {

        for (Pinnable elem : pins) {
           // ids.add(elem.getIdPin());
           // mClusterManager.addItem(elem);
        }
        mClusterManager.cluster();
    }

    public void addItinerary(List<Itinerary> itineraries) {
        //itinerary recycler view init
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        RecyclerAdapter mAdapter = new RecyclerAdapter(getItineraries(itineraries), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new ItineraryAttachListener(this));
    }

    public void clusterManagerOnCameraIdle() {
        mClusterManager.onCameraIdle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMapClick(LatLng latLng) {
        if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        else if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED)
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        View v = getCurrentFocus();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            // if PinInfoSlider is open
            // check if youtube is playing
            SlidingUpPanelLayout youtubePlayerSlider = findViewById(R.id.sliding_layout_youtube);
            if (youtubePlayerSlider.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
                youtubePlayerSlider.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            else
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        } else if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED)
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        else
            super.onBackPressed();


    }

    private void setUserInfoOnDrawnerMenu() {
        if(localUser!=null){
            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView .findViewById(R.id.userNameDraw);
            userName.setText(localUser.getDisplayName());
            userName.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                userName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            }
            final ImageView image = headerView.findViewById(R.id.logoDraw);
            final ProgressBar loader = headerView.findViewById(R.id.userImageLoaderDraw);
            loader.setVisibility(View.VISIBLE);
            loader.setZ(99);
            Picasso.with(this)
                    .load(localUser.getPhotoUrl())
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            loader.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            image.setImageResource(R.drawable.info_pin_placeholder);
                            loader.setVisibility(View.GONE);

                        }
                    });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UserManager.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Snackbar.make(this.mRecyclerView,"BENVENUTO",Snackbar.LENGTH_SHORT).show();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Snackbar.make(this.mRecyclerView,"ANNULLATO",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(this.mRecyclerView,"NO INTERNET CONNECTION",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Snackbar.make(this.mRecyclerView,"ERRORE SCONOSCIUTO",Snackbar.LENGTH_SHORT).show();
                Log.e("ERRORE", "Sign-in error: ", response.getError());
            }
        }
    }


    @Override
    public void onYoutubeBack() {

        Log.d("BACK", "BACK");
    }
}
