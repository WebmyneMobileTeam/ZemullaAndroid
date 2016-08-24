package com.zemulla.android.app.widgets.chart.interfaces.dataprovider;

import com.zemulla.android.app.widgets.chart.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isHighlightFullBarEnabled();
}
