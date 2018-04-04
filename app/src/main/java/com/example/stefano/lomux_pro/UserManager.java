package com.example.stefano.lomux_pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.Login;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class UserManager {

    public static FirebaseUser getLocalUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void login(LoginActions loginActions){
        // not signed in
        loginActions.activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.logo)
                        .setTheme(R.style.MyMaterialTheme)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG , true )
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build()))
                        .build(),
                loginActions.RC_SIGN_IN);
    }

    public static void logout(final LoginActions loginActions){
       AuthUI.getInstance().signOut(loginActions.activity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               loginActions.onLogout();
            }
        });
    }
    public static boolean localUserIsSignIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static void requireLogin(final LoginActions loginResult){


        AlertDialog alertDialog = new AlertDialog.Builder(
                loginResult.activity).create();

        // Setting Dialog Title
        alertDialog.setTitle("Accesso richiesto");

        // Setting Dialog Message
        alertDialog.setMessage("La funzione è disponibile solo dopo aver effettuato l'accesso. Continuare?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_info_black_24dp);

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                login(loginResult);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Annulla",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    public static abstract class LoginActions{
        private Activity activity;
        private int RC_SIGN_IN;

        public int getRC_SIGN_IN() {
            return RC_SIGN_IN;
        }

        public void setRC_SIGN_IN(int RC_SIGN_IN) {
            this.RC_SIGN_IN = RC_SIGN_IN;
        }


        public LoginActions(Activity activity){
            this.activity = activity;
        }
        public void loginResultOnActivityResult(int requestCode, int resultCode, Intent data){
            if(requestCode == RC_SIGN_IN) {
                onLoginResult(resultCode, data);
            }
        }

        public abstract void onLoginResult(int resultCode, Intent data);
        public abstract void onLogout();
    }
}

