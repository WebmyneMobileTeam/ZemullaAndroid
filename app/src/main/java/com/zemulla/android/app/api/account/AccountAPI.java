package com.zemulla.android.app.api.account;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.home.model.FullProfile;
import com.zemulla.android.app.home.model.ProfileResponse;
import com.zemulla.android.app.home.model.UpdateProfileRequest;
import com.zemulla.android.app.model.base.Response;
import com.zemulla.android.app.model.changeemail.ChangeEmailRequest;
import com.zemulla.android.app.model.changeemail.ChangeEmailResponse;
import com.zemulla.android.app.model.contact.ContactUsRequest;
import com.zemulla.android.app.model.contact.ContactUsResopnse;
import com.zemulla.android.app.model.country.CountryResponse;
import com.zemulla.android.app.model.forgotpassword.ForgotPasswordRequest;
import com.zemulla.android.app.model.forgotpassword.ForgotPasswordResponse;
import com.zemulla.android.app.model.login.LoginRequest;
import com.zemulla.android.app.model.login.LoginResponse;
import com.zemulla.android.app.model.optgenval.OTPGenValRequest;
import com.zemulla.android.app.model.optgenval.OTPGenValResponse;
import com.zemulla.android.app.model.otpgenvaltemporary.OTPGenValTemporaryRequest;
import com.zemulla.android.app.model.otpgenvaltemporary.OTPGenValTemporaryResponse;
import com.zemulla.android.app.model.registration.RegistrationRequest;
import com.zemulla.android.app.model.registration.RegistrationResponse;
import com.zemulla.android.app.model.resetpassword.ResetPasswordResponse;
import com.zemulla.android.app.model.servicedropdownlist.ServiceDropDownListResponse;
import com.zemulla.android.app.model.validatemobileemail.ValidateMobileEmailRequest;
import com.zemulla.android.app.model.validatemobileemail.ValidateMobileEmailResponse;
import com.zemulla.android.app.user.model.ChangePasswordRequest;
import com.zemulla.android.app.user.model.ContactRequest;
import com.zemulla.android.app.user.model.ServiceListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public interface AccountAPI {


    @POST(AppConstant.ChangeEmail)
    Call<ChangeEmailResponse> ChangeEmailAD(@Body ChangeEmailRequest changeEmailRequest);

    @POST(AppConstant.ContactMail)
    Call<ContactUsResopnse> ContactMail(@Body ContactUsRequest contactUsRequest);

    @POST(AppConstant.ForgotPassword)
    Call<ForgotPasswordResponse> ForgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @GET(AppConstant.GETCOUNTRYLISTAD)
    Call<CountryResponse> GetCountryListAD();

    @POST(AppConstant.Login)
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

    @POST(AppConstant.OTPGenVal)
    Call<OTPGenValResponse> OTPGenVal(@Body OTPGenValRequest otpGenValRequest);


    @POST(AppConstant.OTPGenValTemporary)
    Call<OTPGenValTemporaryResponse> OTPGenValTemporary(@Body OTPGenValTemporaryRequest otpGenValTemporaryRequest);


    @POST(AppConstant.Registration)
    Call<RegistrationResponse> Registration(@Body RegistrationRequest registrationRequest);

    @POST(AppConstant.ResetPassword)
    Call<ResetPasswordResponse> ResetPassword(@Body RegistrationRequest registrationRequest);

    @GET(AppConstant.ServiceDropDownList)
    Call<ServiceDropDownListResponse> ServiceDropDownList(@Body RegistrationRequest registrationRequest);

    @POST(AppConstant.ValidateMoileEmail)
    Call<ValidateMobileEmailResponse> ValidateMoileEmail(@Body ValidateMobileEmailRequest validateMobileEmailRequest);

    @GET(AppConstant.VerifyEmail)
    Call<ValidateMobileEmailResponse> VerifyEmail(@Body ValidateMobileEmailRequest validateMobileEmailRequest);

    @GET(AppConstant.UserProfile)
    Call<ProfileResponse> UserProfile(@Path("USERID") String USERID);

    @POST(AppConstant.UpdateUserProfile)
    Call<Response> updateProfile(@Body UpdateProfileRequest updateProfileRequest);

    @GET(AppConstant.ServiceList)
    Call<ServiceListResponse> getServiceList();

    @POST(AppConstant.ContactZemulla)
    Call<Response> contactZemulla(@Body ContactRequest contactRequest);

    @POST(AppConstant.ChangePassword)
    Call<Response> changePassword(@Body ChangePasswordRequest changePasswordRequest);
}
