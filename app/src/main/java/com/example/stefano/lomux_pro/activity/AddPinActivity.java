package com.example.stefano.lomux_pro.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefano.lomux_pro.Firebase;
import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.Utility;
import com.example.stefano.lomux_pro.model.Event;
import com.example.stefano.lomux_pro.model.Venue;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;


public class AddPinActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnFocusChangeListener{

    int PLACE_PICKER_REQUEST = 1;
    int BUTTON_SEARCH = 1;
    int BUTTON_ADD = 2;
    EditText nameText;
    EditText subtitleText;
    EditText infoText;
    AnimationDrawable animationDrawable;
    TextView latText;
    TextView lonText;
    SearchView.SearchAutoComplete searchTextArea;
    SearchView searchView;
    ImageView searchCloseButton;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_venue_pin);
        Utility.EditTextUtility.setupUI(getWindow().getDecorView().getRootView(),this);
        startBackgroundAnimation();
        searchView = findViewById(R.id.searchAddress);
        searchTextArea = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        latText = findViewById(R.id.latText);
        lonText = findViewById(R.id.lonText);
        nameText = findViewById(R.id.nameVenue);
        subtitleText = findViewById(R.id.subtitleVenue);
        infoText = findViewById(R.id.infoVenue);
        FloatingActionButton button = findViewById (R.id.addVenuePinButton);

        searchTextArea.setHintTextColor(Color.WHITE);
        searchTextArea.setTextColor(Color.WHITE);
        ImageView searchButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchCloseButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseButton.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchButton.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        }

        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(new ButtonClickListener(BUTTON_SEARCH) );
        searchView.setOnQueryTextFocusChangeListener(this);
        button.setOnClickListener(new ButtonClickListener(BUTTON_ADD));
    }

    private void startBackgroundAnimation() {
        //start background animation
        NestedScrollView scrollView = findViewById(R.id.scrollViewAddVenue);
        animationDrawable = (AnimationDrawable) scrollView.getBackground();

        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                //utente ha aggiunto un luogo
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                latText.setText(String.valueOf(place.getLatLng().latitude));
                lonText.setText(String.valueOf(place.getLatLng().longitude));
                searchView.setQuery(place.getAddress(),false);
                searchTextArea.setSelection(0);
                searchTextArea.setError(null);
                searchCloseButton.setVisibility(View.GONE);
                }
        }
    }



    private void addPin(){
      if(Utility.EditTextUtility.checkInputBeforeSubmit(getWindow().getDecorView().getRootView(),this)){
          //ho tutti i campi, costruisco l'oggetto e lo invio al DB
          String name = nameText.getText().toString();
          String subtitle = subtitleText.getText().toString();
          String info = infoText.getText().toString();
          String address = searchView.getQuery().toString();
          double lat = Double.parseDouble(latText.getText().toString());
          double lon = Double.parseDouble(lonText.getText().toString());
          Venue v = new Venue(name,subtitle,info,address, new ArrayList<Event>(), new GeoPoint(lat,lon),null);
          dialog = ProgressDialog.show(this, "Aggiunta nuovo pin",
                  "Attendi", true);
          Firebase.Firestore.addObjet(v,new DatabaseListener());
      }
    }

    void startPlacePicker(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startPlacePicker();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            v.clearFocus();
            startPlacePicker();
        }
    }

    class  DatabaseListener implements Firebase.FirestoreListenerAddObject {

        @Override
        public void onFailure(@NonNull Exception e) {

            dialog.dismiss();
            if(e instanceof Utility.InternetException)
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Errore durante il salvataggio sul DB, riprova più tardi",Toast.LENGTH_SHORT).show();
            Log.d("ERROR","Errore salvataggio venue pin", e);
        }

        @Override
        public void onSuccess(DocumentReference documentReference) {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"Vanue pin correttamente salvato sul DB",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    class ButtonClickListener implements View.OnClickListener{
        int BUTTON_TYPE;
        ButtonClickListener(int BUTTON_TYPE){
            this.BUTTON_TYPE = BUTTON_TYPE;
        }
        @Override
        public void onClick(View v) {
            if(BUTTON_TYPE == BUTTON_SEARCH)
                startPlacePicker();
            else if(BUTTON_TYPE == BUTTON_ADD)
                addPin();
        }
    }
}
