package com.zemulla.android.app.widgets.chart.interfaces.dataprovider;

import com.zemulla.android.app.widgets.chart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
