package com.example.stefano.lomux_pro.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.stefano.lomux_pro.PinRenderer;
import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.UserManager;
import com.example.stefano.lomux_pro.Utility;
import com.example.stefano.lomux_pro.listener.DrawnerItemClickListener;
import com.example.stefano.lomux_pro.listener.MapChangesListener;
import com.example.stefano.lomux_pro.model.Pin;
import com.example.stefano.lomux_pro.model.Venue;
import com.example.stefano.lomux_pro.model.VenuePin;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.clustering.ClusterManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LomuxMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private GoogleMap mMap;
    public final static LatLng turin_center = new LatLng(45.05, 7.666667);
    private ClusterManager<Pin> mClusterManager;
    private List<String> ids;
    SlidingUpPanelLayout slidingUpPanelLayout;
    PinRenderer pinRenderer;


    //itinerary recycler view
   // private RecyclerView mRecyclerView;
    //private String selected_itinerary = null;
    private FirebaseUser localUser;
    private NavigationView navigationView;
    private CoordinatorLayout dragView;
    private FloatingSearchView searchView;
    private SupportMapFragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomux_map);

        navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new DrawnerItemClickListener(this));
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);

        dragView = findViewById(R.id.dragViewMap);
        searchView = findViewById(R.id.floating_search_view);
        searchView.attachNavigationDrawerToMenuButton((DrawerLayout) findViewById(R.id.drawer_layout));
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        localUser = UserManager.getLocalUser();
        setUserInfoOnDrawnerMenu();
        ids = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        float zoom = 13.5f;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(turin_center));
        mClusterManager = new ClusterManager<>(this, mMap);
        mClusterManager.setAnimation(true);
        Utility.Firebase.Firestore.readCollection(Venue.class ,new Utility.Firebase.FirestoreListenerGetObject(){
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Venue v;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            v = document.toObject(Venue.class);
                           v.setId(document.getId());
                          mMap.addMarker(new MarkerOptions().position(new LatLng(v.getLocation().getLatitude(),v.getLocation().getLongitude())).title(v.getAddress()));
                        }
                        mClusterManager.cluster();
                    } else {
                        Log.d("ERROR", "Error getting documents: ", task.getException());
                    }
                }
        });



        ViewGroup.LayoutParams layoutParams = dragView.getLayoutParams();
        //per bloccare lo slide prima della search bar
        layoutParams.height = (int)(dragView.getHeight()-(searchView.getY()+searchView.getHeight()-26));
        dragView.setLayoutParams(layoutParams);
        slidingUpPanelLayout.setAnchorPoint(0.3f);
        final int shadow = slidingUpPanelLayout.getShadowHeight();

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if(slideOffset==1){
                    slidingUpPanelLayout.setShadowHeight(0);
                    mapFragment.getView().setVisibility(View.INVISIBLE);
                    for(Marker m:mClusterManager.getClusterMarkerCollection().getMarkers())
                    Log.d("CLUSTER", m.getId()+" "+m.getPosition().toString());
                }
                else{
                    slidingUpPanelLayout.setShadowHeight(shadow);
                    mapFragment.getView().setVisibility(View.VISIBLE);
                }
                correctMap(slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
        correctMap(0);


        //mMap.getUiSettings().setMapToolbarEnabled(false);

        pinRenderer = new PinRenderer(this.getApplicationContext(), mMap, mClusterManager);
        mClusterManager.setRenderer(pinRenderer);


       // ClusterMangerListener cml = new ClusterMangerListener(mClusterManager, pinRenderer, slidingUpPanelLayout, this, mMap);
       // mClusterManager.setOnClusterClickListener(cml);
       // mMap.setOnMarkerClickListener(cml);
        //mMap.setOnMarkerClickListener(new ClusterMangerListener(mClusterManager,pinRenderer,slidingUpPanelLayout,this));

      //  mMap.setOnMapClickListener(this);
        mMap.setOnMapLoadedCallback(this);

    }

    private void correctMap(float slideOffset) {
        final int panelHeight = dragView.getHeight();
        final int visiblePanelHeight = slidingUpPanelLayout.getPanelHeight();
        CameraPosition cameraPosition = mMap.getCameraPosition();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.setPadding(5, 10, 10, (int) (visiblePanelHeight + (panelHeight - visiblePanelHeight) * slideOffset));
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onMapLoaded() {
        MapChangesListener mapChangesListener = new MapChangesListener(mMap, this);
        mMap.setOnCameraIdleListener(mapChangesListener);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UserManager.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Snackbar.make(navigationView,"Accesso effettutato",Snackbar.LENGTH_SHORT).show();
                localUser = FirebaseAuth.getInstance().getCurrentUser();
                setUserInfoOnDrawnerMenu();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Snackbar.make(navigationView,"Accesso annullato",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(navigationView,"Nessuna connessione internet",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Snackbar.make(navigationView,"Errore sconosciuto",Snackbar.LENGTH_SHORT).show();
                Log.e("ERRORE", "Sign-in error: ", response.getError());
            }
        }
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
            MenuItem login = navigationView.getMenu().findItem(R.id.nav_login);
            login.setVisible(false);
            MenuItem logout= navigationView.getMenu().findItem(R.id.nav_logout);
            logout.setVisible(true);

            final ImageView image = headerView.findViewById(R.id.logoDraw);
            Uri photoUrl = localUser.getPhotoUrl();
            if(photoUrl == null){
                image.setImageResource(R.drawable.info_pin_placeholder);
                return;
            }
            final ProgressBar loader = headerView.findViewById(R.id.userImageLoaderDraw);
            Utility.loadImageInView(image,photoUrl, loader, this, R.drawable.info_pin_placeholder);

        }
    }


    public void logoutComplete() {
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView .findViewById(R.id.userNameDraw);
        userName.setVisibility(View.GONE);
        ImageView image = headerView.findViewById(R.id.logoDraw);
        image.setImageResource(R.drawable.it0circle);
        MenuItem login = navigationView.getMenu().findItem(R.id.nav_login);
        login.setVisible(true);
        MenuItem logout= navigationView.getMenu().findItem(R.id.nav_logout);
        logout.setVisible(false);
        localUser = FirebaseAuth.getInstance().getCurrentUser();
        Snackbar.make(navigationView,"Logout effettutato",Snackbar.LENGTH_SHORT).show();
    }


}
 /*   @Override
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

    //TODO
    //load di TUTTI gli itinerari a start dell'applicazione, chiamare sincrona prima di getlocalpins
    private ArrayList<Itinerary> getItineraries(List<Itinerary> itineraries) {
        ArrayList<Itinerary> tmp_itinerary_array = new ArrayList<>(itineraries);

        Itinerary tmp_itinerary0 = new Itinerary();
        tmp_itinerary0.setName("All Pins");
        tmp_itinerary0.setInfo("all pins");
        tmp_itinerary_array.add(0, tmp_itinerary0);
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

        selected_itinerary = "All Pins";
        return tmp_itinerary_array;
    }

    public List<String> getListIds() {
        return ids;
    }

    @Override
    public void onMapLoaded() {
        MapChangesListener mapChangesListener = new MapChangesListener(mMap, this);
        mMap.setOnCameraIdleListener(mapChangesListener);
        //PinsCallback.getInstance().get_local_pins(mMap, mapChangesListener.getActualVisibleArea(), ids, this);

    /*    Venue v = new Venue("test","NAME","INFO", new ArrayList<Event>(),new GeoPoint(10,10), null);
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
       // ItineraryCallback.getInstance().get_itinerary(this);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {

    }

    public void addPins(List<Pinnable> pins) {

        for (Pinnable elem : pins) {
          //  ids.add(elem.getIdPin());
          //  mClusterManager.addItem(elem);
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






    @Override
    public void onYoutubeBack() {

        Log.d("BACK", "BACK");
    }


}*/
