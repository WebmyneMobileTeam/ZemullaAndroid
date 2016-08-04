package com.zemulla.android.app.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zemulla.android.app.helper.Functions;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class TfEditText extends EditText {

    private Context _ctx;

    public TfEditText(Context context) {
        super(context);
        this._ctx = context;
        init(context);
    }

    public TfEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._ctx = context;
        init(context);
    }

    private void init(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), Functions.LATO_FONT);
        setTypeface(typeface);
    }


}
