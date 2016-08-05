package com.zemulla.android.app.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.home.model.FullProfile;
import com.zemulla.android.app.home.model.ProfileAPI;
import com.zemulla.android.app.home.model.ProfileResponse;
import com.zemulla.android.app.model.login.LoginResponse;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNoKYC)
    TfTextView txtNoKYC;
    @BindView(R.id.profile_image)
    ImageView profileImage;
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
    @BindView(R.id.txtUsername)
    TfTextView txtUsername;
    private LoginResponse response;

    private ProfileAPI profileAPI;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        unbinder = ButterKnife.bind(this);

        response = PrefUtils.getUserProfile(this);
        initProgressDialog();

        init();

        txtNoKYC.setText(Html.fromHtml("Please Upload KYC Document. " + "<u>" + "Click Here" + "</u>"));

    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void init() {
        profileAPI = new ProfileAPI();

        initToolbar();

        fetchProfile();
        //setProfileDetails();
    }

    private void fetchProfile() {

        showProgressDialog();

        LogUtils.LOGE("call", "WS");

        profileAPI.getProfile(String.valueOf(PrefUtils.getUserID(this)), new APIListener<ProfileResponse>() {
            @Override
            public void onResponse(Response<ProfileResponse> response) {
                hideProgressDialog();

                LogUtils.LOGE("call", "HIDE");

                try {
                    if (response.isSuccessful()) {
                        LogUtils.LOGE("call", "Success");
                        ProfileResponse profileResponse = response.body();
                        if (profileResponse != null) {
                            LogUtils.LOGE("call", "not null");
                            if (profileResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                                setProfileDetails(profileResponse.getData());
                            } else {
                                Functions.showError(UserProfileActivity.this, profileResponse.getResponse().getResponseMsg(), false);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void setProfileDetails(FullProfile data) {
        Functions.setRoundImage(this, profileImage, response.getProfilePicURL() + response.getProfilePic());
        txtUsername.setText(String.format("%s %s", response.getFirstName(), response.getLastName()));
        firstNameEditText.setText(String.format("%s", response.getFirstName()));
        lastNameEditText.setText(String.format("%s", response.getLastName()));

        toolbar.setTitle(String.format("%s %s", response.getFirstName(), response.getLastName()));

        emailEditText.setText(String.format("%s", response.getEmail()));
        phoneNumberEditText.setText(String.format("%s", response.getMobile()));

        countryEditText.setText(data.getCountryName());
        stateEditText.setText(data.getState());
        cityEditText.setText(data.getCity());
        addressEditText.setText(data.getAddress());
        zipCodeEditText.setText(data.getZipCode());
        bankNameEditText.setText(data.getBankName());
        accountNameEditText.setText(data.getAccountName());
        accountNumberEditText.setText(data.getAccountNumber());
        branchNameEditText.setText(data.getBranchName());
        swiftCodeEditText.setText(data.getSwiftCode());
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("");
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
