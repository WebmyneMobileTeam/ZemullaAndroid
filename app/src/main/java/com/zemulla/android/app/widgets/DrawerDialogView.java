package com.zemulla.android.app.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.home.DrawerOptionBean;
import com.zemulla.android.app.home.DrawerOptionsConfiguration;

import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.attr.state_drag_can_accept;

/**
 * Created by dhruvil on 28-07-2016.
 */

public class DrawerDialogView extends LinearLayout {

    private Context _ctx;
    private LayoutInflater inflater;
    private LinearLayout linearOptionLayout;
    private LayoutParams params;

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
        linearOptionLayout = (LinearLayout)findViewById(R.id.linearOptionLayout);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        fillOptions();
    }

    private void fillOptions() {

        DrawerOptionsConfiguration configuration = new DrawerOptionsConfiguration();
        ArrayList<DrawerOptionBean> options = configuration.getAllOptions();

        for(DrawerOptionBean option : options){

            TfTextView txtView = new TfTextView(_ctx);
            txtView.setPadding(12,12,12,12);
            txtView.setGravity(Gravity.CENTER_VERTICAL);
            txtView.setCompoundDrawablePadding(22);
            txtView.setText(option.getOptionName());
            txtView.setTag(option.getId());
            Drawable image = ContextCompat.getDrawable(_ctx,option.getIcon());
            image.setBounds( 0, 0, 56, 56 );
            txtView.setCompoundDrawables(image, null, null, null );
            txtView.setOnClickListener(itemClick);
            linearOptionLayout.addView(txtView,params);
        }
    }

    private View.OnClickListener itemClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            itemsClickListner.onClick((DrawerOptionsConfiguration.OptionID) v.getTag());
        }
    };

    public interface OnItemsClickListner{
            public void onClick(DrawerOptionsConfiguration.OptionID id);
    }
}
