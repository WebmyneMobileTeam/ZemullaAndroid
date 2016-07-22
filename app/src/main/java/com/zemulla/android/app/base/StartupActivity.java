package com.zemulla.android.app.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.user.LoginActivity;

public class StartupActivity extends AppCompatActivity {
    private AppCompatImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();

        init();

        new CountDownTimer(1000,1500){

            @Override
            public void onTick(long millisUntilFinished) {

            }


            @Override
            public void onFinish() {
                Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                String transitionName = "logo_trans";

                if (android.os.Build.VERSION.SDK_INT >= 21) {

                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(StartupActivity.this, imgLogo, transitionName);
                    startActivity(intent, transitionActivityOptions.toBundle());

                    finish();

                }else{
                    startActivity(intent);
                    finish();
                }
            }
        }.start()
        ;
    }

    private void init() {
        imgLogo = (AppCompatImageView)findViewById(R.id.imgLogo);


    }
}
