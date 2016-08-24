package com.zemulla.android.app.widgets.chart.renderer;

import com.zemulla.android.app.widgets.chart.animation.ChartAnimator;
import com.zemulla.android.app.widgets.chart.data.DataSet;
import com.zemulla.android.app.widgets.chart.data.Entry;
import com.zemulla.android.app.widgets.chart.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.zemulla.android.app.widgets.chart.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.zemulla.android.app.widgets.chart.interfaces.datasets.IDataSet;
import com.zemulla.android.app.widgets.chart.utils.ViewPortHandler;

/**
 * Created by Philipp Jahoda on 09/06/16.
 */
public abstract class BarLineScatterCandleBubbleRenderer extends DataRenderer {

    /**
     * buffer for storing the current minimum and maximum visible x
     */
    protected XBounds mXBounds = new XBounds();

    public BarLineScatterCandleBubbleRenderer(ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
    }

    /**
     * Returns true if the DataSet values should be drawn, false if not.
     *
     * @param set
     * @return
     */
    protected boolean shouldDrawValues(IDataSet set) {
        return set.isVisible() && set.isDrawValuesEnabled();
    }

    /**
     * Checks if the provided entry object is in bounds for drawing considering the current animation phase.
     *
     * @param e
     * @param set
     * @return
     */
    protected boolean isInBoundsX(Entry e, IBarLineScatterCandleBubbleDataSet set) {

        if (e == null)
            return false;

        float entryIndex = set.getEntryIndex(e);

        if (e == null || entryIndex >= set.getEntryCount() * mAnimator.getPhaseX()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Class representing the bounds of the current viewport in terms of indices in the values array of a DataSet.
     */
    protected class XBounds {

        /**
         * minimum visible entry index
         */
        public int min;

        /**
         * maximum visible entry index
         */
        public int max;

        /**
         * range of visible entry indices
         */
        public int range;

        /**
         * Calculates the minimum and maximum x values as well as the range between them.
         *
         * @param chart
         * @param dataSet
         */
        public void set(BarLineScatterCandleBubbleDataProvider chart, IBarLineScatterCandleBubbleDataSet dataSet) {
            float phaseX = Math.max(0.f, Math.min(1.f, mAnimator.getPhaseX()));

            float low = chart.getLowestVisibleX();
            float high = chart.getHighestVisibleX();

            Entry entryFrom = dataSet.getEntryForXValue(low, DataSet.Rounding.DOWN);
            Entry entryTo = dataSet.getEntryForXValue(high, DataSet.Rounding.UP);

            min = dataSet.getEntryIndex(entryFrom);
            max = dataSet.getEntryIndex(entryTo);
            range = (int) ((max - min) * phaseX);
        }
    }
}
