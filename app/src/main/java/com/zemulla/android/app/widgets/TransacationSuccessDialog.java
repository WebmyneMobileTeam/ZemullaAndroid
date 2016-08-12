package com.zemulla.android.app.widgets;

import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sagartahelyani on 04-08-2016.
 */
public class TransacationSuccessDialog extends MaterialDialog {

    MaterialDialog materialDialog;
    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.success)
    ImageView success;
    @BindView(R.id.btnOk)
    TfButton btnOk;
    private View customView;


    public TransacationSuccessDialog(Builder builder) {
        super(builder);
        init(builder);

    }

    private void init(Builder builder) {
        customView = View.inflate(getContext(), R.layout.dialog_success, null);

        materialDialog = builder.customView(customView, true)
                .cancelable(false)
                .build();
        ButterKnife.bind(customView);

    }

    public void show() {
        materialDialog.show();
    }


    @OnClick({R.id.imgClose, R.id.btnOk})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                dismiss();
                getOwnerActivity().finish();
                break;
            case R.id.btnOk:
                getOwnerActivity().finish();
                break;
        }
    }
}
