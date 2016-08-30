package com.zemulla.android.app.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.DrawerOptionBean;
import com.zemulla.android.app.home.DrawerOptionsConfiguration;
import com.zemulla.android.app.model.account.login.LoginResponse;

import java.util.ArrayList;

/**
 * Created by dhruvil on 28-07-2016.
 */

public class DrawerDialogView extends LinearLayout {

    private Context _ctx;
    private LayoutInflater inflater;
    private LinearLayout linearOptionLayout;
    private LayoutParams params;
    private ImageView profile_image;
    private TfTextView profileTextView, profileEmailTextView;

    public void setItemsClickListner(OnItemsClickListner itemsClickListner) {
        this.itemsClickListner = itemsClickListner;
    }

    private OnItemsClickListner itemsClickListner;

    public DrawerDialogView(Context context) {
        super(context);
        _ctx = context;
        init();
    }

    public DrawerDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _ctx = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) _ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_dialog_profile, this);
        linearOptionLayout = (LinearLayout) findViewById(R.id.linearOptionLayout);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        profileTextView = (TfTextView) findViewById(R.id.profileTextView);
        profileEmailTextView = (TfTextView) findViewById(R.id.profileEmailTextView);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        fillOptions();
    }

    private void fillOptions() {

        LoginResponse response = PrefUtils.getUserProfile(_ctx);
        if (TextUtils.isEmpty(response.getProfilePic())) {
            profile_image.setImageResource(R.drawable.default_user);
        } else {
            Functions.setRoundImage(_ctx, profile_image, response.getProfilePicURL() + response.getProfilePic());
        }
        profileTextView.setText(String.format("%s %s", response.getFirstName(), response.getLastName()));
        profileEmailTextView.setText(String.format("%s", response.getEmail()));

        DrawerOptionsConfiguration configuration = new DrawerOptionsConfiguration();
        ArrayList<DrawerOptionBean> options = configuration.getAllOptions();

        for (DrawerOptionBean option : options) {

            TfTextView txtView = new TfTextView(_ctx);
            txtView.setPadding(16, 16, 16, 16);
            txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Functions.convertPixelsToDp(_ctx.getResources().getDimension(R.dimen.L_TEXT), _ctx));
            txtView.setGravity(Gravity.CENTER_VERTICAL);
            txtView.setCompoundDrawablePadding(22);
            txtView.setText(option.getOptionName());
            txtView.setTag(option.getId());
            Drawable image = ContextCompat.getDrawable(_ctx, option.getIcon());
            image.setBounds(0, 0, 56, 56);
            txtView.setCompoundDrawables(image, null, null, null);
            txtView.setOnClickListener(itemClick);
            linearOptionLayout.addView(txtView, params);
        }
    }

    private View.OnClickListener itemClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            itemsClickListner.onClick((DrawerOptionsConfiguration.OptionID) v.getTag());
        }
    };

    public interface OnItemsClickListner {
        void onClick(DrawerOptionsConfiguration.OptionID id);
    }
}
