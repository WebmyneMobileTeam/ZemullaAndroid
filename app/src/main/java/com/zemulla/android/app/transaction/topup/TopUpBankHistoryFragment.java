package com.zemulla.android.app.transaction.topup;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.reports.GetTopUpBankTransferReportDetailsAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.topup.transaction.bank.GetTopUpBankTransferReportDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class TopUpBankHistoryFragment extends Fragment {


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

    private Unbinder unbinder;
    private TopupHistoryRecyclerViewAdapter historyRecyclerViewAdapter;
    private List<Object> items;
    private ReportRequest reportRequest;
    private GetTopUpBankTransferReportDetailsAPI getTopUpBankTransferReportDetailsAPI;

    private int serviceDetails;

    public TopUpBankHistoryFragment() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setData(ServiceDetails.TopUpByAdmin.getId());
        }
    }

    // TODO: Rename and change types and number of parameters
    public static TopUpBankHistoryFragment newInstance() {
        TopUpBankHistoryFragment fragment = new TopUpBankHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTopUpBankTransferReportDetailsAPI = new GetTopUpBankTransferReportDetailsAPI();
        reportRequest = new ReportRequest();
        items = new ArrayList<>();
        historyRecyclerViewAdapter = new TopupHistoryRecyclerViewAdapter(items, getActivity());

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
                setData(ServiceDetails.TopUpByAdmin.getId());
            }
        });
        hidEmptyView();
        return fragmentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
            getTopUpBankTransferReportDetailsAPI.onDestory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setData(int ServiceDetailsID) {

        this.serviceDetails = ServiceDetailsID;
        reportRequest.setFrom("19-08-2016");
        reportRequest.setIsPageLoad(true);
        reportRequest.setServiceDetailID(ServiceDetailsID);
        reportRequest.setTo("19-08-2016");
        reportRequest.setUserID(PrefUtils.getUserID(getActivity()));
        getTopUpBankTransferReportDetailsAPI.getSendMoneyApiReportDetailsAPI(reportRequest, getTopUpApiReportDetailsResponseAPIListener);

    }

    APIListener<GetTopUpBankTransferReportDetailsResponse> getTopUpApiReportDetailsResponseAPIListener = new APIListener<GetTopUpBankTransferReportDetailsResponse>() {
        @Override
        public void onResponse(Response<GetTopUpBankTransferReportDetailsResponse> response) {

            progressBar.setVisibility(View.GONE);
            try {
                if (response.isSuccessful() && response.body() != null) {
                    GetTopUpBankTransferReportDetailsResponse getTopUpApiReportDetailsResponse = response.body();
                    if (getTopUpApiReportDetailsResponse.getResponseCode() == AppConstant.ResponseSuccess) {
                        historyRecyclerViewAdapter.setServiceDetailsId(serviceDetails);
                        historyRecyclerViewAdapter.setItems(getTopUpApiReportDetailsResponse.getResponseData().getData());
                        if (getTopUpApiReportDetailsResponse.getResponseData().getData().size() == 0) {
                            showEmptyView();
                        } else {
                            hidEmptyView();
                        }
                    } else {
                        showEmptyView();
                    }
                } else {
                    //set error msg
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetTopUpBankTransferReportDetailsResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
        }
    };

    public void showEmptyView() {
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void hidEmptyView() {
        emptyImageView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);
    }
}
