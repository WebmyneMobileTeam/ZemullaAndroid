package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.user.api.ContactApi;
import com.zemulla.android.app.user.api.ServiceListApi;
import com.zemulla.android.app.user.model.ContactRequest;
import com.zemulla.android.app.user.model.ServiceItem;
import com.zemulla.android.app.user.model.ServiceListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

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
    private ServiceListApi serviceListApi;
    private APIListener<ServiceListResponse> serviceApiListener;
    private ProgressDialog progressDialog;
    private ArrayList<String> serviceList;
    private int serviceID = -1;
    private ContactRequest request;
    private ContactApi contactApi;
    private APIListener<com.zemulla.android.app.model.base.Response> contactApiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        unbinder = ButterKnife.bind(this);
        serviceListApi = new ServiceListApi();
        request = new ContactRequest();
        contactApi = new ContactApi();

        init();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void init() {

        initProgressDialog();

        initToolbar();

        actionListener();

        fetchServiceList();
    }

    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void actionListener() {
        serviceApiListener = new APIListener<ServiceListResponse>() {
            @Override
            public void onResponse(Response<ServiceListResponse> response) {
                hideProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ServiceListResponse listResponse = response.body();
                            if (listResponse.getResponseCode() == AppConstant.ResponseSuccess) {

                                setServiceSpinner(listResponse.getResponseData().getData());
                            } else {
                                Functions.showError(ContactUsActivity.this, listResponse.getResponseMsg(), false);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.LOGE("msg", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideProgressDialog();
            }
        };

        contactApiListener = new APIListener<com.zemulla.android.app.model.base.Response>() {
            @Override
            public void onResponse(Response<com.zemulla.android.app.model.base.Response> response) {
                hideProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                                Functions.showError(ContactUsActivity.this, "Your inquiry is submitted successfully", true);
                            } else {
                                Functions.showError(ContactUsActivity.this, response.body().getResponseMsg(), false);
                            }
                        } else {
                            Functions.showError(ContactUsActivity.this, "Something went wrong. Please try again.", false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<com.zemulla.android.app.model.base.Response> call, Throwable t) {
                hideProgressDialog();
                Functions.showError(ContactUsActivity.this, t.getMessage(), false);
            }
        };
    }

    private void setServiceSpinner(final List<ServiceItem> serviceDataList) {
        for (int i = 0; i < serviceDataList.size(); i++) {
            serviceList.add(serviceDataList.get(i).getServiceName());
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>
                (this, android.R.layout.simple_dropdown_item_1line, serviceList);
        inquireTypeSpinner.setAdapter(adp);
        inquireTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceID = serviceDataList.get(position).getServiceID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fetchServiceList() {
        serviceList = new ArrayList<>();

        showProgressDialog();

        serviceListApi.getService(serviceApiListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        removeListener(serviceApiListener);
        removeListener(contactApiListener);
    }

    private void removeListener(APIListener<?> listener) {
        if (listener != null) {
            listener = null;
        }
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

        if (serviceID == -1) {
            Functions.showError(this, "Please Select Inquiry Type", false);
            return;
        }

        if (Functions.isEmpty(descriptionEditText)) {
            Functions.showError(this, "Please Enter Description.", false);
            return;
        }

        doContact();
        //call  webservice and send contact us request.

    }

    private void doContact() {
        showProgressDialog();

        request.setDescription(Functions.toStingEditText(descriptionEditText));
        request.setSubject(Functions.toStingEditText(subjectEditText));
        request.setUserID(PrefUtils.getUserID(this));
        request.setZemullaServicesID(serviceID);
        request.setZemullaTransactionID(Functions.toStingEditText(transactionIdEditText));
        contactApi.doContact(request, contactApiListener);
    }
}
