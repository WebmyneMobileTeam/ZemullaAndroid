package com.zemulla.android.app.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
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
import com.zemulla.android.app.home.model.FullProfile;
import com.zemulla.android.app.home.model.ProfileAPI;
import com.zemulla.android.app.home.model.ProfileResponse;
import com.zemulla.android.app.home.model.UpdateProfileApi;
import com.zemulla.android.app.home.model.UpdateProfileRequest;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.widgets.TfButton;
import com.zemulla.android.app.widgets.TfEditText;
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
    TfTextView personalInformationTextView;
    @BindView(R.id.firstNameEditText)
    TfEditText firstNameEditText;
    @BindView(R.id.lastNameEditText)
    TfEditText lastNameEditText;
    @BindView(R.id.emailEditText)
    TfEditText emailEditText;
    @BindView(R.id.phoneNumberEditText)
    TfEditText phoneNumberEditText;
    @BindView(R.id.regionalInformationTextView)
    TfTextView regionalInformationTextView;
    @BindView(R.id.countryEditText)
    TfEditText countryEditText;
    @BindView(R.id.stateEditText)
    TfEditText stateEditText;
    @BindView(R.id.cityEditText)
    EditText cityEditText;
    @BindView(R.id.addressEditText)
    TfEditText addressEditText;
    @BindView(R.id.zipCodeEditText)
    TfEditText zipCodeEditText;
    @BindView(R.id.backDetailsTextView)
    TextView backDetailsTextView;
    @BindView(R.id.bankNameEditText)
    TfEditText bankNameEditText;
    @BindView(R.id.accountNameEditText)
    TfEditText accountNameEditText;
    @BindView(R.id.accountNumberEditText)
    TfEditText accountNumberEditText;
    @BindView(R.id.branchNameEditText)
    TfEditText branchNameEditText;
    @BindView(R.id.swiftCodeEditText)
    TfEditText swiftCodeEditText;
    @BindView(R.id.btnSave)
    TfButton btnSave;
    @BindView(R.id.activity_user_profile)
    LinearLayout activityUserProfile;

    Unbinder unbinder;
    @BindView(R.id.txtUsername)
    TfTextView txtUsername;
    private LoginResponse response;

    private ProfileAPI profileAPI;
    private UpdateProfileApi updateProfileApi;
    private ProgressDialog progressDialog;
    private UpdateProfileRequest request;
    private ProfileResponse profileResponse;
    private APIListener updateApiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        unbinder = ButterKnife.bind(this);

        response = PrefUtils.getUserProfile(this);
        initProgressDialog();

        request = new UpdateProfileRequest();

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
        updateProfileApi = new UpdateProfileApi();

        initToolbar();

        fetchProfile();

        actionListener();
        //setProfileDetails();
    }

    private void actionListener() {
        updateApiListener = new APIListener() {
            @Override
            public void onResponse(Response response) {
                hideProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            com.zemulla.android.app.model.base.Response baseResponse = (com.zemulla.android.app.model.base.Response) response.body();
                            if (baseResponse.getResponseCode() == AppConstant.ResponseSuccess) {
                                Toast.makeText(UserProfileActivity.this, "Profile Updates Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Functions.showError(UserProfileActivity.this, baseResponse.getResponseMsg(), false);
                            }
                        } else {
                            Functions.showError(UserProfileActivity.this, "Something went wrong. Please try again.", false);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideProgressDialog();
                Functions.showError(UserProfileActivity.this, t.getMessage(), false);

            }
        };
    }

    private void fetchProfile() {

        showProgressDialog();

        profileAPI.getProfile(String.valueOf(PrefUtils.getUserID(this)), new APIListener<ProfileResponse>() {
            @Override
            public void onResponse(Response<ProfileResponse> response) {
                hideProgressDialog();

                try {
                    if (response.isSuccessful()) {
                        profileResponse = response.body();
                        if (profileResponse != null) {
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
        if (TextUtils.isEmpty(response.getProfilePic())) {
            profileImage.setImageResource(R.drawable.default_user);
        } else {
            Functions.setRoundImage(this, profileImage, response.getProfilePicURL() + response.getProfilePic());
        }
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

        validateProfile();
    }

    private void validateProfile() {
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

        doUpdateProfile();
    }

    private void doUpdateProfile() {

        showProgressDialog();

        request.setFirstName(Functions.toStingEditText(firstNameEditText));
        request.setLastName(Functions.toStingEditText(lastNameEditText));
        request.setAccountName(Functions.toStingEditText(accountNameEditText));
        request.setAccountNumber(Functions.toStingEditText(accountNumberEditText));
        request.setAddress(Functions.toStingEditText(addressEditText));
        request.setBankName(Functions.toStingEditText(bankNameEditText));
        request.setBranchName(Functions.toStingEditText(branchNameEditText));
        request.setCity(Functions.toStingEditText(cityEditText));
        request.setCountryID(profileResponse.getData().getCountryID());
        request.setState(Functions.toStingEditText(stateEditText));
        request.setSwiftCode(Functions.toStingEditText(swiftCodeEditText));
        request.setUserID(PrefUtils.getUserID(this));
        request.setZipCode(Functions.toStingEditText(zipCodeEditText));

        updateProfileApi.updateProfile(request, updateApiListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
            removeListener(updateApiListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeListener(APIListener updateApiListener) {
        if (updateApiListener != null)
            updateApiListener = null;
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
