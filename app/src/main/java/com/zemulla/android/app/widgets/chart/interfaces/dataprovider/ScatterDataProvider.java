package com.zemulla.android.app.widgets.chart.interfaces.dataprovider;

import com.zemulla.android.app.widgets.chart.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
