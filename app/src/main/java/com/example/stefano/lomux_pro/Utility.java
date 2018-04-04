package com.example.stefano.lomux_pro;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Utility {

    public static void loadImageInView(final ImageView toLoad, Uri url, final ProgressBar loader, Context context, final int placeHolder){
        loader.setVisibility(View.VISIBLE);
        Picasso.with(context)
                .load(url)
                .into(toLoad, new Callback() {
                    @Override
                    public void onSuccess() {
                        loader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        toLoad.setImageResource(placeHolder);
                        loader.setVisibility(View.GONE);

                    }
                });
    }
    public static class InternetException extends Exception{
        public InternetException(String message){
            super(message);
        }
    }
    public static boolean isInternetAvailable() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = process.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static class EditTextUtility{
    /**
     * permette di chiudere la tastiera virtuale quando si preme fuori dagli edit text
     **/
    public static void setupUI(View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView,activity);
            }
        }
    }

    private static void hideSoftKeyboard(@NonNull Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean checkInputBeforeSubmit(View view, Activity activity) {

        boolean isOk = true;
        // Set up touch listener for non-text box views to hide keyboard.
        if(view instanceof SearchView){
            EditText editText = view.findViewById(R.id.search_src_text);
            isOk=checkError(editText);
        }
        else if(view instanceof android.support.v7.widget.SearchView){
            EditText editText = view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            isOk=checkError(editText);
        }
        else if (view instanceof EditText) {
            isOk = checkError((EditText) view);
        }
        else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                if(!checkInputBeforeSubmit(innerView,activity)){
                    isOk = false;
                }
            }
        }
        return isOk;
    }

    private static boolean checkError(EditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError("Il campo non può essere vuoto");
            return false;
        }
        return true;
        }
    }

}
