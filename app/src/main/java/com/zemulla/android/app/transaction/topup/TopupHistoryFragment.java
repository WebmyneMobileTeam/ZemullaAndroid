package com.zemulla.android.app.transaction.topup;

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
import com.zemulla.android.app.api.reports.GetTopUpApiReportDetailsAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.GetTopUpApiReportDetailsResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.TopUpApiReportDetails;
import com.zemulla.android.app.widgets.CalenderDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class TopupHistoryFragment extends Fragment {


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
    private ArrayList<Object> items;
    private ReportRequest reportRequest;
    private GetTopUpApiReportDetailsAPI getTopUpApiReportDetailsAPI;
    int mServiceId = 0;
    private boolean isDateSelected;
    private String fromDateValue, toDateValue = "";

    public TopupHistoryFragment() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setData();
        }
    }

    // TODO: Rename and change types and number of parameters
    public static TopupHistoryFragment newInstance(int serviceId) {
        TopupHistoryFragment fragment = new TopupHistoryFragment();
        Bundle args = new Bundle();
        args.putInt("serviceId", serviceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceId = getArguments().getInt("serviceId", 0);
        getTopUpApiReportDetailsAPI = new GetTopUpApiReportDetailsAPI();
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

        reportRequest.setFrom("19-08-2016");
        reportRequest.setIsPageLoad(true);
        reportRequest.setServiceDetailID(mServiceId);
        reportRequest.setTo("19-08-2016");
        reportRequest.setUserID(PrefUtils.getUserID(getActivity()));

        if (isDateSelected && !TextUtils.isEmpty(fromDateValue)) {

            reportRequest.setFrom(fromDateValue);
            reportRequest.setTo(toDateValue);
            reportRequest.setIsPageLoad(false);
        }


        getTopUpApiReportDetailsAPI.getTopUpWalletBankTransferApi(reportRequest, getTopUpApiReportDetailsResponseAPIListener);

    }

    APIListener<GetTopUpApiReportDetailsResponse> getTopUpApiReportDetailsResponseAPIListener = new APIListener<GetTopUpApiReportDetailsResponse>() {
        @Override
        public void onResponse(Response<GetTopUpApiReportDetailsResponse> response) {

            try {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    GetTopUpApiReportDetailsResponse getTopUpApiReportDetailsResponse = response.body();
                    if (getTopUpApiReportDetailsResponse.getResponseCode() == AppConstant.ResponseSuccess) {
                        List<TopUpApiReportDetails> topUpApiReportDetailses = getTopUpApiReportDetailsResponse.getResponseData().getData();
                        historyRecyclerViewAdapter.setServiceDetailsId(mServiceId);
                        historyRecyclerViewAdapter.setItems(topUpApiReportDetailses);
                        if (getTopUpApiReportDetailsResponse.getResponseData().getData().size() == 0) {
                            showEmptyView();
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
        public void onFailure(Call<GetTopUpApiReportDetailsResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    };
    public void showEmptyView() {
        mainRecyler.setVisibility(View.GONE);
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void hidEmptyView() {
        mainRecyler.setVisibility(View.VISIBLE);
        emptyImageView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);
    }

    @OnClick({R.id.cancelFilter, R.id.filterImageButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelFilter:
                cancelFilter();
                break;
            case R.id.filterImageButton:
                OpenCalener();
                break;
        }
    }

    private void cancelFilter() {
        fromDateValue = "";
        toDateValue = "";
        fromDateTextView.setVisibility(View.INVISIBLE);
        toDate.setVisibility(View.INVISIBLE);
        cancelFilter.setVisibility(View.INVISIBLE);
        isDateSelected = false;
        hidEmptyView();
        setData();
    }

    private void OpenCalener() {
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
}
