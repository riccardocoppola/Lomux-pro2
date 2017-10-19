package com.example.stefano.lomux_pro.listener;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Created by Stefano on 18/10/2017.
 */

    public class URIClickListener implements View.OnClickListener {


        String uri;

        public URIClickListener(String uri) {

            this.uri = uri;

        }

        @Override
        public void onClick(View view) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(uri));
            view.getContext().startActivity(i);


        }
    }

