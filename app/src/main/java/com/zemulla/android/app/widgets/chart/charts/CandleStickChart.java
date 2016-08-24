
package com.zemulla.android.app.widgets.chart.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.zemulla.android.app.widgets.chart.data.CandleData;
import com.zemulla.android.app.widgets.chart.interfaces.dataprovider.CandleDataProvider;
import com.zemulla.android.app.widgets.chart.renderer.CandleStickChartRenderer;

/**
 * Financial chart type that draws candle-sticks (OHCL chart).
 *
 * @author Philipp Jahoda
 */
public class CandleStickChart extends BarLineChartBase<CandleData> implements CandleDataProvider {

    public CandleStickChart(Context context) {
        super(context);
    }

    public CandleStickChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CandleStickChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new CandleStickChartRenderer(this, mAnimator, mViewPortHandler);
    }

    @Override
    public CandleData getCandleData() {
        return mData;
    }
}
