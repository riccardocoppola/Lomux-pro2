package com.example.stefano.lomux_pro.listener;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.stefano.lomux_pro.activity.LomuxMapActivity;
import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.UserManager;
import com.example.stefano.lomux_pro.activity.PinsActivity;

/**
 * Created by Stefano on 16/10/2017.
 */

public class DrawnerItemClickListener implements  NavigationView.OnNavigationItemSelectedListener {
    private LomuxMapActivity view;
    private UserManager.LoginActions loginActions;

    private SearchView searchView;

    public DrawnerItemClickListener(LomuxMapActivity view, UserManager.LoginActions loginActions) {
        this.view = view;
        this.loginActions = loginActions;
        //this.searchView = view.findViewById(R.id.searchbar);
    }

    /**
     * Swaps fragments in the main content view
     */
   /* private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            control_check(item);
            press_search();

        } else if (id == R.id.nav_add) {
            control_check(item);
            press_add();

        } else if (id == R.id.nav_slideshow) {
            control_check(item);

        } else if (id == R.id.nav_manage) {
            control_check(item);

        } else if (id == R.id.nav_share) {
            control_check(item);

        } else if (id == R.id.nav_send) {
            control_check(item);

        }
        else if (id == R.id.nav_login) {
            control_check(item);
            press_login();

        }else if (id == R.id.nav_logout) {
            control_check(item);
            press_logout();
        }
        Log.d("CLICK", "test"+ id);
        DrawerLayout drawer = view.findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new Drawer_listener(item, drawer));

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void press_logout() {
        UserManager.logout(loginActions);
    }

    private void press_login() {
        UserManager.login(loginActions);
    }

    private void press_add() {
        if(UserManager.localUserIsSignIn()){
            Intent intent = new Intent(view.getApplicationContext(), PinsActivity.class);
            view.startActivity(intent);
        }else {
            UserManager.requireLogin(loginActions);
        }
    }







    private void control_check(MenuItem item){
        if(item.isChecked())
            item.setChecked(false);
    }

    private void press_search(){


    }

    private class Drawer_listener implements DrawerLayout.DrawerListener{
        private MenuItem item;
        private DrawerLayout drawer;


        private Drawer_listener(MenuItem item, DrawerLayout drawer){
            this.item = item;
            this.drawer = drawer;
        }

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull  View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            item.setChecked(false);
            item.setCheckable(true);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (newState == DrawerLayout.STATE_SETTLING) {
                if (!drawer.isDrawerOpen(Gravity.START)) {
                    // starts opening

                 /*  if(searchView.isInTouchMode()){
                        searchView.setQuery(searchView.getQuery(),false);
                        searchView.setIconified(true);
                    }*/

                } else {
                    // closing drawer
                }
            }

        }
    }
}
