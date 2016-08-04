package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {

    @BindView(R.id.headerView)
    TextView headerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
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
