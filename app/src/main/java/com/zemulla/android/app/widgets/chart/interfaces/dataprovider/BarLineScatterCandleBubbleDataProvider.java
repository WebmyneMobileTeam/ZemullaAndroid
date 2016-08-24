package com.zemulla.android.app.widgets.chart.interfaces.dataprovider;

import com.zemulla.android.app.widgets.chart.components.YAxis.AxisDependency;
import com.zemulla.android.app.widgets.chart.data.BarLineScatterCandleBubbleData;
import com.zemulla.android.app.widgets.chart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
