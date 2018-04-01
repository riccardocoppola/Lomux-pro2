package com.example.stefano.lomux_pro.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stefano.lomux_pro.R;
import com.example.stefano.lomux_pro.Utility;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = findViewById(R.id.logoDraw);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.start_animation);

        img.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //txt.setVisibility(View.GONE);
                //progressBar.setVisibility(View.GONE);

                if(!Utility.isInternetAvailable())
                {
                    Snackbar.make(findViewById(android.R.id.content), "Nessuna connessione", Snackbar.LENGTH_LONG).show();
                    //MainActivity.create_snack_bar(findViewById(android.R.id.content));
                }


                    //animate_visitLondon_button();
                //Intent intent = new Intent(getApplicationContext(), LomuxMapActivity.class);
                Intent intent = new Intent(getApplicationContext(), LomuxMapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


/*
    public void animate_visitLondon_button() {
        final ImageButton visitLondon = (ImageButton) findViewById(R.id.imageButton);
        visitLondon.setVisibility(View.VISIBLE);
        visitLondon.setAlpha(0.0f);
        visitLondon.setScaleX(1.4f);
        visitLondon.setScaleY(1.4f);
        visitLondon.animate().
                scaleX(1).
                scaleY(1)
                .alpha(1.0f)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        visitLondon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), LomuxMapActivity.class);
                                intent.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                intent.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


    public  static void create_snack_bar(final View v){

        Snackbar.make(v, "Connection error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Restart App", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent restartIntent = v.getContext().getPackageManager()
                                .getLaunchIntentForPackage(v.getContext().getPackageName() );

                        PendingIntent intent = PendingIntent.getActivity(
                                v.getContext(), 0, restartIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager manager = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);
                        manager.set(AlarmManager.RTC, System.currentTimeMillis(), intent);
                        System.exit(0);
                    }
                }).setActionTextColor(Color.RED)
                .show();
    }*/
}
