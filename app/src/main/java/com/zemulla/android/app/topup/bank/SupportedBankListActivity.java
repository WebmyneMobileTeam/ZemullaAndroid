package com.zemulla.android.app.topup.bank;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetSupportedBankDetailsAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetails;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetailsResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.topup.bank.adapter.SupportedBankAdapter;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SupportedBankListActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TfTextView txtTopupWayName;
    @BindView(R.id.otherBankRecyclerView)
    RecyclerView otherBankRecyclerView;
    @BindView(R.id.mainHolder)
    LinearLayout mainHolder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.activity_topup_initial_transaction)
    RelativeLayout activityTopupInitialTransaction;
    private SupportedBankAdapter supportedBankAdapter;
    private List<GetSupportedBankDetails> getSupportedBankDetailses;
    private GetSupportedBankDetailsAPI getSupportedBankDetailsAPI;
    private LoginResponse loginResponse;
    private GetWalletDetailResponse walletResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supported_bank_list);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        getSupportedBankDetailsAPI = new GetSupportedBankDetailsAPI();
        getSupportedBankDetailses = new ArrayList<>();

        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);

        initToolbar();
        initRecyclerView();
        getSupportedBankDetailsAPI.getSupportedBankDetailsAPI(getSupportedBankDetailsResponseAPIListener);
    }


    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        otherBankRecyclerView.setLayoutManager(linearLayoutManager);
        supportedBankAdapter = new SupportedBankAdapter(getSupportedBankDetailses, this);
        otherBankRecyclerView.setAdapter(supportedBankAdapter);
        otherBankRecyclerView.setHasFixedSize(true);
        otherBankRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int padding = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(padding, padding, padding, padding);
            }
        });

    }


    private void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            try {
                Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
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


    APIListener<GetSupportedBankDetailsResponse> getSupportedBankDetailsResponseAPIListener = new APIListener<GetSupportedBankDetailsResponse>() {
        @Override
        public void onResponse(Response<GetSupportedBankDetailsResponse> response) {
            hideProgressBar();
            try {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                        supportedBankAdapter.setItems(response.body().getResponseData().getData());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetSupportedBankDetailsResponse> call, Throwable t) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportedBankDetailsAPI.onDestory();
    }
}
