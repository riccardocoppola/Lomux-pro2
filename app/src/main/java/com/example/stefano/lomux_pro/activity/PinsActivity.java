package com.example.stefano.lomux_pro.activity;


import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.adapters.PinTabAdapter;
import com.example.stefano.lomux_pro.fragment.EventPinTab;
import com.example.stefano.lomux_pro.fragment.VenuePinTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PinsActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SparseArray<String> tabIcons = new SparseArray<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pins");
        tabIcons.append(R.drawable.ic_menu_slideshow, "VENUE PIN");
        tabIcons.append(R.drawable.ic_menu_manage, "EVENT PIN");
        tabIcons.append(R.drawable.ic_menu_share, "TRIVIA PIN");

        fragments.add(new VenuePinTab());
        fragments.add(new EventPinTab());
        //TODO mettere trivia pin
        fragments.add(new EventPinTab());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeActionContentDescription("TORNA ALLA MAPPA");


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        PinTabAdapter adapter = new PinTabAdapter(getSupportFragmentManager());
        TextView tab;
        for(int i = 0; i < tabIcons.size(); i++) {
            adapter.addFrag(fragments.get(i), tabIcons.valueAt(i) );
        }
        viewPager.setAdapter(adapter);

        for(int i = 0; i < tabIcons.size(); i++) {
            tab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tab.setText(tabIcons.valueAt(i) );
            tab.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tab.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons.keyAt(i), 0, 0);
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
