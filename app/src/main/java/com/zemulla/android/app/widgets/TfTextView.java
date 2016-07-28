package com.zemulla.android.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zemulla.android.app.helper.Functions;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class TfTextView extends TextView {

    private Context _ctx;
    public TfTextView(Context context) {
        super(context);
        this._ctx = context;
        init();
    }

    public TfTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._ctx = context;
        init();
    }

    private void init() {
        setTypeface(Functions.getLatoFont(_ctx));
    }


}
