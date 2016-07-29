package com.zemulla.android.app.topup;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.widgets.TfTextView;

import mbanje.kurt.fabbutton.FabButton;

public class TopupInitialTransactionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TfTextView txtTopupWayName;
    private FabButton fabButton;
    private LinearLayout lineatInitialViewTopup;
    private LinearLayout linearTrnsViewTopup;
    private FabButton btnProcessResetTransaction;
    private FlipAnimation animation;
    FrameLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_initial_transaction);

        initToolbar();
        init();

    }

    private void init() {
        txtTopupWayName = (TfTextView)findViewById(R.id.txtTopupWayName);
        if(getIntent().getExtras() !=null){
            txtTopupWayName.setText(getIntent().getStringExtra("wayname"));
        }
        lineatInitialViewTopup = (LinearLayout)findViewById(R.id.lineatInitialViewTopup);
        linearTrnsViewTopup = (LinearLayout)findViewById(R.id.linearTrnsViewTopup);


        fabButton = (FabButton)findViewById(R.id.btnProcessInitialTransaction);
        rootLayout = (FrameLayout) findViewById(R.id.frameRootTopup);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fabButton.showProgress(true);

                new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        fabButton.hideProgressOnComplete(true);
                        fabButton.onProgressCompleted();
                        animation = new FlipAnimation(lineatInitialViewTopup,linearTrnsViewTopup);
                        rootLayout.startAnimation(animation);

                    }
                }.start();

            }
        });

        btnProcessResetTransaction = (FabButton)findViewById(R.id.btnProcessResetTransaction);
        btnProcessResetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                rootLayout.startAnimation(animation);
            }
        });



    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dhruvil Patel");
        getSupportActionBar().setSubtitle("Effective Balance : ZMW 1222.5");
    }

}
