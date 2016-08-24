package com.zemulla.android.app.widgets.chart.interfaces.dataprovider;

import com.zemulla.android.app.widgets.chart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
