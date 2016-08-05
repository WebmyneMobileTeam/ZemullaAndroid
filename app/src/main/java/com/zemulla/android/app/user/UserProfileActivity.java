package com.zemulla.android.app.user;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNoKYC)
    TfTextView txtNoKYC;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.personal_Information_textView)
    TextView personalInformationTextView;
    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;
    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @BindView(R.id.regionalInformationTextView)
    TextView regionalInformationTextView;
    @BindView(R.id.countryEditText)
    EditText countryEditText;
    @BindView(R.id.stateEditText)
    EditText stateEditText;
    @BindView(R.id.cityEditText)
    EditText cityEditText;
    @BindView(R.id.addressEditText)
    EditText addressEditText;
    @BindView(R.id.zipCodeEditText)
    EditText zipCodeEditText;
    @BindView(R.id.backDetailsTextView)
    TextView backDetailsTextView;
    @BindView(R.id.bankNameEditText)
    EditText bankNameEditText;
    @BindView(R.id.accountNameEditText)
    EditText accountNameEditText;
    @BindView(R.id.accountNumberEditText)
    EditText accountNumberEditText;
    @BindView(R.id.branchNameEditText)
    EditText branchNameEditText;
    @BindView(R.id.swiftCodeEditText)
    EditText swiftCodeEditText;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.activity_user_profile)
    LinearLayout activityUserProfile;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        unbinder = ButterKnife.bind(this);

        init();

        txtNoKYC.setText(Html.fromHtml("Please Upload KYC Document. " + "<u>" + "Click Here" + "</u>"));

    }

    private void init() {
        initToolbar();
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
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

    private void setUnderLineText(TextView textView) {

        textView.setText(Html.fromHtml(String.format("<p><u>%s</u></p>", textView.getText().toString())));
    }

    @OnClick(R.id.btnSave)
    public void onClick() {

        if (Functions.isEmpty(firstNameEditText)) {
            Functions.showError(this, "Please Enter First Name.", false);
            return;
        }

        if (Functions.isEmpty(lastNameEditText)) {
            Functions.showError(this, "Please Enter Last Name.", false);
            return;
        }


        if (Functions.isEmpty(emailEditText)) {
            Functions.showError(this, "Please Enter Email.", false);
            return;
        }

        if (!Functions.emailValidation(Functions.toStingEditText(emailEditText))) {
            Functions.showError(this, "Invalid e-mail.", false);
            return;
        }

        if (Functions.isEmpty(phoneNumberEditText)) {
            Functions.showError(this, "Please Enter phone Number.", false);
            return;
        }

        if (Functions.isEmpty(countryEditText)) {
            Functions.showError(this, "Please Enter Country.", false);
            return;
        }

        if (Functions.isEmpty(stateEditText)) {
            Functions.showError(this, "Please Enter State.", false);
            return;
        }

        if (Functions.isEmpty(cityEditText)) {
            Functions.showError(this, "Please Enter City.", false);
            return;
        }

        if (Functions.isEmpty(addressEditText)) {
            Functions.showError(this, "Please Enter address.", false);
            return;
        }
        if (Functions.isEmpty(zipCodeEditText)) {
            Functions.showError(this, "Please Enter ZipCode.", false);
            return;
        }

        if (!Functions.isEmpty(bankNameEditText)
                || !Functions.isEmpty(accountNameEditText)
                || !Functions.isEmpty(accountNumberEditText)
                || !Functions.isEmpty(branchNameEditText)
                || !Functions.isEmpty(swiftCodeEditText)) {

            if (Functions.isEmpty(bankNameEditText)
                    || Functions.isEmpty(accountNameEditText)
                    || Functions.isEmpty(accountNumberEditText)
                    || Functions.isEmpty(branchNameEditText)
                    || Functions.isEmpty(swiftCodeEditText)) {

                Functions.showError(this, "Please Enter all back details.", false);
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.profile_image)
    public void onProfileClick() {
        Functions.setPermission(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        new MaterialDialog.Builder(UserProfileActivity.this)
                                .title("Add Profile Photo")
                                .items(R.array.items)
                                .typeface(Functions.getLatoFont(UserProfileActivity.this), Functions.getLatoFont(UserProfileActivity.this))
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

                        Toast.makeText(UserProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT);
                    }
                });
    }

    private void pickGallery() {
        RxImagePicker.with(this).requestImage(Sources.GALLERY)
                .flatMap(new Func1<Uri, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Uri uri) {
                        return RxImageConverters.uriToBitmap(UserProfileActivity.this, uri);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        // Do something with Bitmap
                        profileImage.setImageBitmap(bitmap);
                    }
                });
    }

    private void captureCam() {
        RxImagePicker.with(this).requestImage(Sources.CAMERA)
                .flatMap(new Func1<Uri, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Uri uri) {
                        return RxImageConverters.uriToBitmap(UserProfileActivity.this, uri);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        // Do something with Bitmap
                        profileImage.setImageBitmap(bitmap);
                    }
                });
    }

    @OnClick(R.id.txtNoKYC)
    public void onKycClick() {
        Functions.fireIntent(this, KYCActivity.class);
    }
}
