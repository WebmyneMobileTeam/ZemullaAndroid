package com.zemulla.android.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class TfTextView extends TextView {

    private Context _ctx;
    private boolean isBold;

    public TfTextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    public TfTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TfTextView, 0, 0);
            try {
                isBold = a.getBoolean(R.styleable.TfTextView_isBold, false);
            } finally {
                a.recycle();
            }

            this._ctx = context;
            init();
        }

    }

    private void init() {
        try {
            setTypeface(Functions.getLatoFont(_ctx));
            if (isBold) {
                setTypeface(getTypeface(), Typeface.BOLD);
            }
        } catch (Exception e) {
            Log.d("error","Exception");
        }
    }

}
