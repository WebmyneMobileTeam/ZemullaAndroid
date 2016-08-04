package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNoKYC)
    TextView txtNoKYC;
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
        toolbar.setTitle("Dhruvil Patel");
        setSupportActionBar(toolbar);


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


}
