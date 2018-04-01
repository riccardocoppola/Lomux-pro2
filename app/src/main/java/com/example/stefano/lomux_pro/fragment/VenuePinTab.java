package com.example.stefano.lomux_pro.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.activity.AddPinActivity;
import com.example.stefano.lomux_pro.adapters.VenuePinAdapter;
import com.example.stefano.lomux_pro.model.Venue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VenuePinTab extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar bar;
    private FloatingActionButton addPinButton;


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venue_pin_tab, container, false);
        mRecyclerView =  view.findViewById(R.id.venuePinList);
        addPinButton = view.findViewById(R.id.addPinVenue);
        addPinButton.setOnClickListener(new AddPinListener());
        bar = view.findViewById(R.id.progressBar2);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(mLayoutManager);




        FirebaseFirestore.getInstance().collection(Venue.class.getSimpleName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> test = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       Venue v =document.toObject(Venue.class);
                        test.add(v.getVenueName());
                    }
                        mAdapter = new VenuePinAdapter(test, 1, bar);
                        mRecyclerView.setAdapter(mAdapter);

                } else {
                    Log.w("ERROR", "Error getting documents.", task.getException());
                }
            }
        });



        mAdapter = new VenuePinAdapter(null, 0, bar);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

private class AddPinListener implements View.OnClickListener
{

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddPinActivity.class);
        startActivity(intent);
    }
}

}