package com.zemulla.android.app.report;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.user.UserAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.dashboard.BaseChart;
import com.zemulla.android.app.model.user.dashboard.ChartData;
import com.zemulla.android.app.model.user.dashboard.GetDashboardData;
import com.zemulla.android.app.model.user.dashboard.TransactionData;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.ReportTile;
import com.zemulla.android.app.widgets.chart.animation.Easing;
import com.zemulla.android.app.widgets.chart.charts.PieChart;
import com.zemulla.android.app.widgets.chart.data.PieData;
import com.zemulla.android.app.widgets.chart.data.PieDataSet;
import com.zemulla.android.app.widgets.chart.data.PieEntry;
import com.zemulla.android.app.widgets.chart.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.topUpTile)
    ReportTile topUpTile;
    @BindView(R.id.sendMoneyTile)
    ReportTile sendMoneyTile;
    @BindView(R.id.paymentTile)
    ReportTile paymentTile;
    @BindView(R.id.chart1)
    PieChart chart1;
    @BindView(R.id.chart2)
    PieChart chart2;
    @BindView(R.id.chart3)
    PieChart chart3;
    @BindView(R.id.chart1Name)
    TextView chart1Name;
    @BindView(R.id.chart2Name)
    TextView chart2Name;
    @BindView(R.id.chart3Name)
    TextView chart3Name;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private ProgressDialog progressDialog;
    private UserAPI userAPI;
    private Call<GetDashboardData> getDashboardDataCall;
    private GetDashboardData getDashboardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        unbinder = ButterKnife.bind(this);
        userAPI = ZemullaApplication.getRetrofit().create(UserAPI.class);
        showProgressDialog();
        init();
        initChart(chart1);
        initChart(chart2);
        initChart(chart3);
        getDashBoardData();

    }

    private void initChart(PieChart mChart) {


        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        // mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawHoleEnabled(false);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(50f);
        mChart.setTransparentCircleRadius(52f);

        mChart.setDrawCenterText(false);
        mChart.setDrawEntryLabels(false);

        // mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
    }

    private void getDashBoardData() {
        getDashboardDataCall = userAPI.GetDashboardData(String.valueOf(PrefUtils.getUserID(this)));
        getDashboardDataCall.enqueue(new Callback<GetDashboardData>() {
            @Override
            public void onResponse(Call<GetDashboardData> call, Response<GetDashboardData> response) {
                hidProgressDialog();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        getDashboardData = response.body();
                        setTransactionDetails(getDashboardData.Data.TransactionAmount);
                        setChartData(getDashboardData.Data.chartRootData);

                    } else {

                        //show error dialog
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetDashboardData> call, Throwable t) {

            }
        });
    }

    private void setChartData(List<BaseChart> chartRootData) {
        for (int i = 0; i < chartRootData.size(); i++) {
            if (i == 0) {
                chart1.setCenterText(chartRootData.get(i).getChartName());
                chart1Name.setText(chartRootData.get(i).getChartName());
                setChart(chart1, chartRootData.get(i).ChartData);
            } else if (i == 1) {
                chart2.setCenterText(chartRootData.get(i).getChartName());
                setChart(chart2, chartRootData.get(i).ChartData);
                chart2Name.setText(chartRootData.get(i).getChartName());
            } else if (i == 2) {
                chart3.setCenterText(chartRootData.get(i).getChartName());
                setChart(chart3, chartRootData.get(i).ChartData);
                chart3Name.setText(chartRootData.get(i).getChartName());
            }
        }

    }

    private void setChart(PieChart chart1, List<ChartData> chartData) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < chartData.size(); i++) {
            ChartData chartData1 = chartData.get(i);
            if (chartData1.getData() != 0) {
                entries.add(new PieEntry(chartData1.getData(), chartData1.getLabel()));
                colors.add(Color.parseColor(chartData1.getColor()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        chart1.setData(data);

        // undo all highlights
        chart1.highlightValues(null);

        chart1.invalidate();

        chart1.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void setTransactionDetails(List<TransactionData> transactionAmount) {
        for (int i = 0; i < transactionAmount.size(); i++) {
            if (i == 0) {
                topUpTile.setAmountText(String.valueOf(transactionAmount.get(i).getAmount()));
                topUpTile.setHeaderText(transactionAmount.get(i).getTransactionType());
            } else if (i == 1) {
                sendMoneyTile.setAmountText(String.valueOf(transactionAmount.get(i).getAmount()));
                sendMoneyTile.setHeaderText(transactionAmount.get(i).getTransactionType());
            } else if (i == 2) {
                paymentTile.setAmountText(String.valueOf(transactionAmount.get(i).getAmount()));
                paymentTile.setHeaderText(transactionAmount.get(i).getTransactionType());
            }
        }
    }

    private void init() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        initToolbar();

    }

    private void initToolbar() {

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

                Functions.fireIntentWithClearFlag(ReportsActivity.this, HomeActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlag(ReportsActivity.this, HomeActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
