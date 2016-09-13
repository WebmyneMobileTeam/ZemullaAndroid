package com.zemulla.android.app.emarket.electricity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.kazang.KazangElectricityAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityRequest;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;
import com.zemulla.android.app.widgets.TfTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.initFab)
    FabButton initFab;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.edtName)
    TfTextView edtName;
    @BindView(R.id.edtAddress)
    TfTextView edtAddress;
    @BindView(R.id.edtOtherDetails)
    TfTextView edtOtherDetails;
    private Unbinder unbinder;
    private FlipAnimation animation;
    private KazangElectricityResponse kazangElectricityResponse;
    private OTPDialogAfterLogin mOtpDialogAfterLogin;
    private ProgressDialog progressDialog;
    private KazangElectricityAPI kazangElectricityAPI;
    private KazangElectricityRequest kazangElectricityRequest;
    private LoginResponse loginResponse;
    private GetWalletDetailResponse walletResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        unbinder = ButterKnife.bind(this);
        init();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        kazangElectricityRequest = (KazangElectricityRequest) intent.getSerializableExtra(Intent.EXTRA_REFERRER);
        kazangElectricityResponse = (KazangElectricityResponse) intent.getSerializableExtra(Intent.EXTRA_REFERRER_NAME);
        edtAddress.setText(String.format("Address : %s", kazangElectricityResponse.getCustomer_address()));
        edtName.setText(String.format("Name : %s", kazangElectricityResponse.getCustomer_name()));
        edtOtherDetails.setText(String.format("Other Details : %s", kazangElectricityResponse.getConfirmation_message()));

    }


    private void init() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        initObject();
        initToolbar();
        actionListener();
        mOtpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(ConfirmationActivity.this));
        mOtpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
    }

    private void initObject() {
        kazangElectricityAPI = new KazangElectricityAPI();
        kazangElectricityRequest = new KazangElectricityRequest();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            initProgressDialog();
        }
        progressDialog.show();
    }

    private void hidProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    OTPDialogAfterLogin.OnSubmitListener onSubmitListener = new OTPDialogAfterLogin.OnSubmitListener() {
        @Override
        public void onSubmit(String OTP) {
            confirmEbill(OTP);

        }

        @Override
        public void OTPReceived() {
            hidProgressDialog();
        }
    };

    private void confirmEbill(String otp) {

        //MeterNumber,TotalCharge,Amount,TotalPayableAmount,
        // UserID,UserID,product_id,
        // confirmation_number,
        // ZemullaTransionID,
        // VerificationCode
        showProgressDialog();
        kazangElectricityRequest.setVerificationCode(otp);
        kazangElectricityRequest.setZemullaTransionID(kazangElectricityResponse.getZemullaTransactionID());
        kazangElectricityRequest.setConfirmation_number(kazangElectricityResponse.getConfirmation_number());
        kazangElectricityAPI.kazangDirectRechargeAPI(kazangElectricityRequest, kazangElectricityResponseAPIListener);

    }


    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call Api for requesting OTP
                showProgressDialog();
                mOtpDialogAfterLogin.generateOPT();
            }
        });
    }

    private void initToolbar() {

        try {
            Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    APIListener<KazangElectricityResponse> kazangElectricityResponseAPIListener = new APIListener<KazangElectricityResponse>() {
        @Override
        public void onResponse(Response<KazangElectricityResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    KazangElectricityResponse kazangElectricityResponse = response.body();
                    if (kazangElectricityResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        mOtpDialogAfterLogin.dismiss();
                        Intent intent = new Intent(ConfirmationActivity.this, ElectriCityBillReicptActivity.class);
                        intent.putExtra(Intent.EXTRA_REFERRER, kazangElectricityResponse);
                        Functions.showSuccessMsg(ConfirmationActivity.this, kazangElectricityResponse.getResponse().getResponseMsg(), true, intent);
                    } else {
                        Functions.showError(ConfirmationActivity.this, kazangElectricityResponse.getResponse().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(Call<KazangElectricityResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
