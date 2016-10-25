package com.zemulla.android.app.user;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
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
    @BindView(R.id.pdfImage)
    ImageView pdfImage;
    @BindView(R.id.documentPath)
    TextView documentPath;
    private String kycType = "";
    private boolean isAttach = false;
    private static final int PICKFILE_RESULT_CODE = 1000;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
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
            try {
                Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
            } catch (Exception e) {
                Log.d("error","Exception");
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntentWithClearFlagWithWithPendingTransition(KYCActivity.this, HomeActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlagWithWithPendingTransition(KYCActivity.this, HomeActivity.class);
    }

    @OnClick(R.id.btnSelect)
    public void onKYCSelect() {
        Functions.setPermission(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        new MaterialDialog.Builder(KYCActivity.this)
                                .title("Upload Document")
                                .items(R.array.items_PDF)
                                .typeface(Functions.getLatoFont(KYCActivity.this), Functions.getLatoFont(KYCActivity.this))
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        if (which == 0) {
                                            captureCam();
                                        } else if (which == 1) {
                                            pickGallery();
                                        } else if (which == 2) {
                                            pickDocument();
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
                        hidePDFView();
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
                        hidePDFView();
                        setClearVisible();
                    }
                });
    }

    private void pickDocument() {

        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/pdf");
            startActivityForResult(intent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException exp) {
            Toast.makeText(getBaseContext(), "No File (Manager / Explorer)etc Found In Your Device", Toast.LENGTH_SHORT).show();
        }
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
        pdfImage.setVisibility(View.GONE);
        documentPath.setText("");
        documentPath.setVisibility(View.GONE);

        setClearVisible();
    }

    private void setClearVisible() {
        if (isAttach)
            txtClear.setVisibility(View.VISIBLE);
        else
            txtClear.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {

                    try {

                        String FilePath = data.getData().getPath();
                        String FileName = data.getData().getLastPathSegment();
                        int lastPos = FilePath.length() - FileName.length();


//                    textFile.setText("Full Path: \n" + FilePath + "\n");
//                    textFolder.setText("Folder: \n" + Folder + "\n");
//                    textFileName.setText("File Name: \n" + FileName + "\n");
//
                        filename thisFile = new filename(FileName);
                        if (!thisFile.getExt().equalsIgnoreCase("pdf")) {
                            Toast.makeText(KYCActivity.this, "PDF file allow only.", Toast.LENGTH_SHORT).show();

                        } else {
                            isAttach = true;
                            showPDFView();
                            documentPath.setText(FilePath);
                            setClearVisible();
                        }
//                    textFileName_WithoutExt.setText("Filename without Ext: " + thisFile.getFilename_Without_Ext());
//                    textFileName_Ext.setText("Ext: " + thisFile.getExt());


                    } catch (Exception e) {
                        Toast.makeText(KYCActivity.this, "File not allowed.", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

        }
    }


    private class filename {

        String filename_Without_Ext = "";
        String ext = "";

        filename(String file) {
            int dotposition = file.lastIndexOf(".");
            filename_Without_Ext = file.substring(0, dotposition);
            ext = file.substring(dotposition + 1, file.length());
        }

        String getFilename_Without_Ext() {
            return filename_Without_Ext;
        }

        String getExt() {
            return ext;
        }
    }

    public void showPDFView() {

        documentPath.setVisibility(View.VISIBLE);
        pdfImage.setVisibility(View.VISIBLE);
        imgDocument.setVisibility(View.GONE);
    }

    public void hidePDFView() {
        documentPath.setVisibility(View.GONE);
        pdfImage.setVisibility(View.GONE);
        imgDocument.setVisibility(View.VISIBLE);
    }
}
