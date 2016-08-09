package com.zemulla.android.app.helper;

import android.text.InputFilter;
import android.text.Spanned;

import com.zemulla.android.app.constant.AppConstant;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public class DecimalDigitsInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source
                .subSequence(start, end).toString());
        if (!builder.toString().matches(
                "(([0-9]{1})([0-9]{0," + (AppConstant.MAX_DIGITS_BEFORE_DECIMAL_POINT - 1) + "})?)?(\\.[0-9]{0," + AppConstant.MAX_DIGITS_AFTER_DECIMAL_POINT + "})?"

        )) {
            if (source.length() == 0)
                return dest.subSequence(dstart, dend);
            return "";
        }
        return null;
    }
}
