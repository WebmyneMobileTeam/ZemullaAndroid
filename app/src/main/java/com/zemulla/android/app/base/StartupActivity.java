package com.zemulla.android.app.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.user.LoginActivity;

import java.util.ArrayList;

public class StartupActivity extends AppCompatActivity {
    private AppCompatImageView imgLogo;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        init();


        if (!Functions.isNetworkAvailable(this)) {
            Functions.showError(this, getString(R.string.no_internet_connection), true);
            return;
        }

        Functions.setPermission(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        if (PrefUtils.getLoggedIn(StartupActivity.this)) {
                            Intent homeIntent = new Intent(StartupActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                            finish();

                        } else {

                            Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                            //  String transitionName = "logo_trans";
                            startActivity(intent);
                            finish();
//                    if (android.os.Build.VERSION.SDK_INT >= 21) {
//
//                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(StartupActivity.this, imgLogo, transitionName);
//                        startActivity(intent, transitionActivityOptions.toBundle());
//
//                        finish();
//
//                    } else {
//
//                    }
                        }

                    }
                }.start();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {
                Toast.makeText(StartupActivity.this, "Service unavailable", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void init() {
        imgLogo = (AppCompatImageView) findViewById(R.id.imgLogo);

    }

}
