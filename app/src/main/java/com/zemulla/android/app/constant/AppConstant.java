package com.zemulla.android.app.constant;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class AppConstant {


    public static final int MAX_DIGITS_BEFORE_DECIMAL_POINT = 7;
    public static final int MAX_DIGITS_AFTER_DECIMAL_POINT = 2;


    public static final int ClientRoleID = 3;
    public static final String ZMW = "ZMW";
    public static final int ResponseFailed = 0;
    public static final int ResponseSuccess = 1;
    public static final int OTPResponseSuccess = 4;
    public static final int WorngCredential = 4;
    public static final String BASEURL = "http://ws-srv-net.in.webmyne.com/Applications/Zemulla";
    public static final String BASEURLSERVICEURL = String.format("%s%s", BASEURL, "/WCF/Services/");
    public static final String PROFILE_URL = String.format("%s%s", BASEURL, "/Admin/Upload/ProfilePic/");
    public static final String PrivacyPolicy = String.format("%s%s", BASEURL, "/Marketing/PrivacyPolicy/4");
    public static final String TermsAndConditions = String.format("%s%s", BASEURL, "/Marketing/TermsAndConditions/2");

    //------------------

    public static final String AccountSVC = "Account.svc/json/";
    public static final String ChangeEmail = AccountSVC + "ChangeEmailAD";
    public static final String ContactMail = AccountSVC + "ContactMailAD";
    public static final String ForgotPassword = AccountSVC + "ForgotPasswordAD";
    public static final String GETCOUNTRYLISTAD = AccountSVC + "GetCountryListAD";
    public static final String Login = AccountSVC + "LoginAD";
    public static final String OTPGenVal = AccountSVC + "OTPGenValAD";
    public static final String OTPGenValTemporary = AccountSVC + "OTPGenValTemporaryAD";
    public static final String Registration = AccountSVC + "RegistrationAD";
    public static final String ResetPassword = AccountSVC + "ResetPasswordAD";
    public static final String ServiceDropDownList = AccountSVC + "ServiceDropDownListAD";
    public static final String ValidateMoileEmail = AccountSVC + "ValidateMoileEmailAD";
    public static final String VerifyEmail = AccountSVC + "VerifyEmail";
    public static final String ServiceList = AccountSVC + "ServiceDropDownListAD";
    public static final String ContactZemulla = AccountSVC + "ContactMailAD";


    public static final String UserSVC = "Users.svc/json/";
    public static final String UserProfile = UserSVC + "GetUserDetailIDWiseAD/{USERID}";
    public static final String UpdateUserProfile = UserSVC + "UpdateUserProfileAD";
    public static final String GetWalletDetail = UserSVC + "GetWalletDetailAD/{USERID}";
    public static final String GetUserDetailIDWise = UserSVC + "GetUserDetailIDWiseAD/{USERID}";
    public static final String GetDashboardData = UserSVC + "GetDashboardData/{USERID}";
       public static final String ChangePassword = UserSVC + "ChangePasswordAD";

    public static final String PaymentSVC = "Payment.svc/json/";
    public static final String TopUpTransactionChargeCalculation = PaymentSVC + "TopUpTransactionChargeCalculationAD";
    public static final String TransactionChargeCalculation = PaymentSVC + "TransactionChargeCalculationAD";
    public static final String GetSupportedBankDetails = PaymentSVC + "GetSupportedBankDetailsAD";
    public static final String PaypalPayment1 = PaymentSVC + "PaypalPayment1AD";
    public static final String PaypalPayment2 = PaymentSVC + "PaypalPayment2AD";
    public static long DebounceTime = 800;

    public static final String ZWalletSVC = "ZWallet.svc/json/";
    public static final String TopupAPIWallet = ZWalletSVC + "TopupAPIWalletAD";
    public static final String SendMoneyToAPIWallet = ZWalletSVC + "SendMoneyToAPIWalletAD";
    public static final String GetDynamicText = ZWalletSVC + "GetDynamicTextAD/{APIMASTERID}";
    public static final String TopUpWalletBankTransfer = ZWalletSVC + "TopUpWalletBankTransferAD";
    public static final String SendMoneyBantTransfer = ZWalletSVC + "SendMoneyBantTransferAD";
    public static final String IsValidSendWallet = ZWalletSVC + "IsValidSendWalletAD";
    public static final String SendMoneyW2W = ZWalletSVC + "SendMoneyW2WAD";


    public static final String KazangSVC = "Kazang.svc/json/";
    public static final String GetKazangProductProvider = KazangSVC + "GetKazangProductProviderAD/{PRODUCTCATEGORY}";
    public static final String GetProductAPIProdMasterIDWise = KazangSVC + "GetProductAPIProdMasterIDWiseAD/{APIPRODUCTMASTERID}";
    public static final String GetKazangProductPlan = KazangSVC + "GetKazangProductPlanAD";
    public static final String KazangAirtime = KazangSVC + "KazangAirtimeAD";
    public static final String KazangDirectRecharge = KazangSVC + "KazangDirectRechargeAD";
    public static final String KazangTestElectricity = KazangSVC + "KazangTestElectricityAD/{METERNUMBER}";
    public static final String KazangElectricity = KazangSVC + "KazangElectricityAD";
    public static final String DSTVPayment1= KazangSVC + "DSTVPayment1AD";
    public static final String DSTVPayment2= KazangSVC + "DSTVPayment2AD";
    public static final String DSTVPayment3= KazangSVC + "DSTVPayment3AD";


    public static final String ReportsSVC = "Reports.svc/json/";
    public static final String GetCyberSourceReportDetails = ReportsSVC + "GetCyberSourceReportDetailsAD";
    public static final String GetPayPalReportDetails = ReportsSVC + "GetPayPalReportDetailsAD";
    public static final String GetTopUpBankTransferReportDetails = ReportsSVC + "GetTopUpBankTransferReportDetailsAD";
    public static final String GetTopUpApiReportDetails = ReportsSVC + "GetTopUpApiReportDetailsAD";
    public static final String GetSendMoneyApiReportDetails = ReportsSVC + "GetSendMoneyApiReportDetailsAD";
    public static final String GetSendMoneyW2WReportDetails = ReportsSVC + "GetSendMoneyW2WReportDetailsAD";
    public static final String GetSendMoneyBTReportDetails = ReportsSVC + "GetSendMoneyBTReportDetailsAD";
    public static final String GetKazangAirtimeDetails = ReportsSVC + "GetKazangAirtimeDetailsAD";
    public static final String GetKazangDirectRechargeDetails = ReportsSVC + "GetKazangDirectRechargeDetailsAD";
    public static final String GetKazangDSTVPaymentDetails = ReportsSVC + "GetKazangDSTVPaymentDetailsAD";
    public static final String GetKazangElectricityDetails = ReportsSVC + "GetKazangElectricityDetailsAD";
    public static final String GetNotificationCommon = ReportsSVC + "GetNotificationCommonAD";
    public static final String CheckedNotificationCommon = ReportsSVC + "CheckedNotificationCommonAD";



    // Password Tracker constants
    public static final int WEAK = 1;
    public static final int MEDIUM = 2;
    public static final int HIGH = 3;
}
