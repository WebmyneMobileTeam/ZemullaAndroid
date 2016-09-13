package com.zemulla.android.app.fundtransfer.transaction;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.reports.GetW2WReportDetailsAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.TopUpApiReportDetails;
import com.zemulla.android.app.model.reports.w2w.W2WReportResponse;
import com.zemulla.android.app.transaction.topup.TopupHistoryRecyclerViewAdapter;
import com.zemulla.android.app.widgets.CalenderDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class W2WFundTransferHistoryFragment extends Fragment {


    @BindView(R.id.mainRecyler)
    RecyclerView mainRecyler;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.emptyImageView)
    ImageView emptyImageView;
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fromDate)
    TextView fromDateTextView;
    @BindView(R.id.toDate)
    TextView toDate;
    @BindView(R.id.cancelFilter)
    TextView cancelFilter;
    @BindView(R.id.filterImageButton)
    ImageButton filterImageButton;
    @BindView(R.id.filterHolder)
    LinearLayout filterHolder;

    private Unbinder unbinder;
    private TopupHistoryRecyclerViewAdapter historyRecyclerViewAdapter;
    private List<TopUpApiReportDetails> items;
    private ReportRequest reportRequest;
    private GetW2WReportDetailsAPI getW2WReportDetailsAPI;
    private boolean isDateSelected;
    private String fromDateValue = "", toDateValue = "";

    public W2WFundTransferHistoryFragment() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setData();
        }
    }

    // TODO: Rename and change types and number of parameters
    public static W2WFundTransferHistoryFragment newInstance() {
        W2WFundTransferHistoryFragment fragment = new W2WFundTransferHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getW2WReportDetailsAPI = new GetW2WReportDetailsAPI();
        reportRequest = new ReportRequest();
        items = new ArrayList<>();
        historyRecyclerViewAdapter = new TopupHistoryRecyclerViewAdapter(items, getActivity());
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        mainRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainRecyler.setAdapter(historyRecyclerViewAdapter);
        mainRecyler.setHasFixedSize(true);
        mainRecyler.setItemAnimator(new DefaultItemAnimator());
        mainRecyler.addItemDecoration(new RecyclerView.ItemDecoration() {
            int pixelPadding = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(pixelPadding, pixelPadding, pixelPadding, pixelPadding);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
        hidEmptyView();
        return fragmentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void setData() {

        if (reportRequest == null) {
            reportRequest = new ReportRequest();
        }
        if (getW2WReportDetailsAPI == null) {
            getW2WReportDetailsAPI = new GetW2WReportDetailsAPI();
        }
        reportRequest.setFrom("19-08-2016");
        reportRequest.setIsPageLoad(true);
        reportRequest.setServiceDetailID(ServiceDetails.WalletToWallet.getId());
        reportRequest.setTo("19-08-2016");
        reportRequest.setUserID(PrefUtils.getUserID(getActivity()));

        if (isDateSelected && !TextUtils.isEmpty(fromDateValue)) {

            reportRequest.setFrom(fromDateValue);
            reportRequest.setTo(toDateValue);
            reportRequest.setIsPageLoad(false);
        }

        getW2WReportDetailsAPI.getSendMoneyApiReportDetailsAPI(reportRequest, getTopUpApiReportDetailsResponseAPIListener);

    }

    APIListener<W2WReportResponse> getTopUpApiReportDetailsResponseAPIListener = new APIListener<W2WReportResponse>() {
        @Override
        public void onResponse(Response<W2WReportResponse> response) {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            if (response.isSuccessful() && response.body() != null) {
                W2WReportResponse w2WReportResponse = response.body();
                if (w2WReportResponse.getResponseCode() == AppConstant.ResponseSuccess) {
                    historyRecyclerViewAdapter.setServiceDetailsId(ServiceDetails.WalletToWallet.getId());
                    historyRecyclerViewAdapter.setItems(w2WReportResponse.getResponseData().getData());
                    if (w2WReportResponse.getResponseData().getData().size() == 0) {
                        showEmptyView();
                    }
                } else {
                    showEmptyView();
                }
            } else {
                //set error msg
            }

        }

        @Override
        public void onFailure(Call<W2WReportResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
        }
    };

    public void showEmptyView() {
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
        mainRecyler.setVisibility(View.GONE);
    }

    public void hidEmptyView() {
        emptyImageView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);
        mainRecyler.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.filterImageButton)
    public void onClick() {
        // Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
        CalenderDialog calenderDialog = new CalenderDialog();
        calenderDialog.showDialog(fromDateValue, toDateValue, getChildFragmentManager(), new CalenderDialog.OnSuccessListener() {
            @Override
            public void onSuccess(String fromDate, String todate) {
                fromDateTextView.setText(String.format("From : %s", fromDate));
                toDate.setText(String.format("To : %s", todate));
                fromDateTextView.setVisibility(View.VISIBLE);
                toDate.setVisibility(View.VISIBLE);
                fromDateValue = fromDate;
                toDateValue = todate;
                cancelFilter.setVisibility(View.VISIBLE);
                isDateSelected = true;
                progressBar.setVisibility(View.VISIBLE);
                hidEmptyView();
                setData();
            }
        });


    }

    @OnClick(R.id.cancelFilter)
    public void oncancelFilterClick() {
        fromDateValue = "";
        toDateValue = "";
        fromDateTextView.setVisibility(View.INVISIBLE);
        toDate.setVisibility(View.INVISIBLE);
        cancelFilter.setVisibility(View.INVISIBLE);
        isDateSelected = false;
        progressBar.setVisibility(View.VISIBLE);
        hidEmptyView();
        setData();
    }
}
