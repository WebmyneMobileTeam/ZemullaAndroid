package com.zemulla.android.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.zemulla.android.app.helper.Functions;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class TfButton extends Button {

    private Context _ctx;

    public TfButton(Context context) {
        super(context);
        this._ctx = context;
        init();
    }

    public TfButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._ctx = context;
        init();
    }

    private void init() {
        try {
            setTypeface(Functions.getLatoFont(_ctx));
        }catch (Exception e){
            Log.d("error","Exception");
        }
    }


}
