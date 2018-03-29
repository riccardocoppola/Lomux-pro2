package com.example.stefano.lomux_pro;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class UserManager {

    public static final int RC_SIGN_IN = 123;

    public static FirebaseUser getLocalUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void login(Activity context){
        // not signed in
        context.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build()))
                        .build(),
                RC_SIGN_IN);

    }

    public static boolean localUserIsSignIn() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            return true;
        else
            return false;
    }
}

