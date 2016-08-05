package com.zemulla.android.app.user;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.TfButton;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class KYCActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radioNational)
    AppCompatRadioButton radioNational;
    @BindView(R.id.radioPassport)
    AppCompatRadioButton radioPassport;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.btnSelect)
    TfButton btnSelect;
    @BindView(R.id.imgDocument)
    ImageView imgDocument;
    @BindView(R.id.btnUpload)
    TfButton btnUpload;

    Unbinder unbinder;
    @BindView(R.id.txtClear)
    TfTextView txtClear;
    private String kycType = "";
    private boolean isAttach = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioNational) {
                    kycType = "National ID";
                } else {
                    kycType = "Passport";
                }
            }
        });

        setClearVisible();
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btnSelect)
    public void onKYCSelect() {
        Functions.setPermission(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        new MaterialDialog.Builder(KYCActivity.this)
                                .title("Add Profile Photo")
                                .items(R.array.items)
                                .typeface(Functions.getLatoFont(KYCActivity.this), Functions.getLatoFont(KYCActivity.this))
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        if (which == 0) {
                                            captureCam();
                                        } else {
                                            pickGallery();
                                        }
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {

                        Toast.makeText(KYCActivity.this, "Permission Denied", Toast.LENGTH_SHORT);
                    }
                });
    }

    private void pickGallery() {
        RxImagePicker.with(this).requestImage(Sources.GALLERY)
                .flatMap(new Func1<Uri, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Uri uri) {
                        return RxImageConverters.uriToBitmap(KYCActivity.this, uri);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        // Do something with Bitmap
                        isAttach = true;
                        imgDocument.setImageBitmap(bitmap);
                        imgDocument.setVisibility(View.VISIBLE);
                        setClearVisible();
                    }
                });
    }

    private void captureCam() {
        RxImagePicker.with(this).requestImage(Sources.CAMERA)
                .flatMap(new Func1<Uri, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Uri uri) {
                        return RxImageConverters.uriToBitmap(KYCActivity.this, uri);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        // Do something with Bitmap
                        isAttach = true;
                        imgDocument.setImageBitmap(bitmap);
                        imgDocument.setVisibility(View.VISIBLE);
                        setClearVisible();
                    }
                });
    }

    @OnClick(R.id.btnUpload)
    public void onUploadClick() {
        if (kycType.equalsIgnoreCase("")) {
            Functions.showError(KYCActivity.this, "Select KYC type", false);
        } else if (!isAttach) {
            Functions.showError(KYCActivity.this, "Select KYC Document", false);
        } else {
            Toast.makeText(KYCActivity.this, "KYC upload", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txtClear)
    public void onClearClick() {
        isAttach = false;
        imgDocument.setVisibility(View.INVISIBLE);
        setClearVisible();
    }

    private void setClearVisible() {
        if (isAttach)
            txtClear.setVisibility(View.VISIBLE);
        else
            txtClear.setVisibility(View.GONE);
    }

}
