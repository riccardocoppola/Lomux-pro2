package com.example.stefano.lomux_pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.stefano.lomux_pro.activity.LomuxMapActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
                        .setTheme(R.style.MyMaterialTheme)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build()))
                        .build(),
                RC_SIGN_IN);

    }

    public static void logout(final LomuxMapActivity activity){
        AuthUI.getInstance().signOut(activity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                activity.logoutComplete();
            }
        });
    }
    public static boolean localUserIsSignIn() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            return true;
        else
            return false;
    }

    public static void requireLogin(final Activity activity){


        AlertDialog alertDialog = new AlertDialog.Builder(
                activity).create();

        // Setting Dialog Title
        alertDialog.setTitle("Accesso richiesto");

        // Setting Dialog Message
        alertDialog.setMessage("La funzione è disponibile solo dopo aver effettuato l'accesso. Continuare?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_info_black_24dp);

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                login(activity);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Annulla",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }
}

