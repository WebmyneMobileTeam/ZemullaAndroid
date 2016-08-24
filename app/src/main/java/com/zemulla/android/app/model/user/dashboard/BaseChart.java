package com.zemulla.android.app.model.user.dashboard;

import java.util.List;

/**
 * Created by raghavthakkar on 23-08-2016.
 */
public class BaseChart {
    public List<ChartData> ChartData;
    public String ChartName;

    public List<com.zemulla.android.app.model.user.dashboard.ChartData> getChartData() {
        return ChartData;
    }

    public void setChartData(List<com.zemulla.android.app.model.user.dashboard.ChartData> chartData) {
        ChartData = chartData;
    }

    public String getChartName() {
        return ChartName;
    }

    public void setChartName(String chartName) {
        ChartName = chartName;
    }
}
