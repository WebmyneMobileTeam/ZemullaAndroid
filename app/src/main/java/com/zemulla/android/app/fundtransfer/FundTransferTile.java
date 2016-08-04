package com.zemulla.android.app.fundtransfer;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.HalfSquareLayout;


public class FundTransferTile extends HalfSquareLayout {

    private Context _ctx;
    private LayoutInflater inflater;
    private TextView txtView;
    private FundTransferTileBean homeTileBean;
    private ImageView imgHomeTileIcon;

    public FundTransferTile(Context context) {
        super(context);
        _ctx = context;
        init();
    }

    public FundTransferTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        _ctx = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) _ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_topup_tile, this);
        txtView = (TextView) findViewById(R.id.txtTopupTileName);
        txtView.setTypeface(Functions.getLatoFont(_ctx));
        imgHomeTileIcon = (ImageView) findViewById(R.id.imgTopupTileIcon);

    }

    public FundTransferTileBean getTile() {
        return homeTileBean;
    }

    public void setupTile(FundTransferTileBean bean) {
        this.homeTileBean = bean;
        txtView.setText(bean.getTileName());
        setBackgroundColor(Color.parseColor(bean.getBackgroundColor()));
        imgHomeTileIcon.setImageResource(bean.getTileIcon());
        imgHomeTileIcon.setColorFilter(Color.parseColor(bean.getBackgroundColorDark()));
        txtView.setTextColor(Color.parseColor(bean.getBackgroundColorDark()));

    }


}
