package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContactUsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.inquireTypeSpinner)
    AppCompatSpinner inquireTypeSpinner;
    @BindView(R.id.subjectEditText)
    EditText subjectEditText;
    @BindView(R.id.transactionIdEditText)
    EditText transactionIdEditText;
    @BindView(R.id.descriptionEditText)
    EditText descriptionEditText;
    @BindView(R.id.submitButton)
    Button submitButton;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.submitButton)
    public void onClick() {


        if (Functions.isEmpty(subjectEditText)) {
            Functions.showError(this, "Please Enter Subject.", false);
            return;
        }

        if (Functions.isEmpty(transactionIdEditText)) {
            Functions.showError(this, "Please Enter Transaction Number.", false);
            return;
        }

        if (Functions.isEmpty(descriptionEditText)) {
            Functions.showError(this, "Please Enter Description.", false);
            return;
        }

        //call  webservice and send contact us request.

    }
}
