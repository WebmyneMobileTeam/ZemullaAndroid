package com.zemulla.android.app.constant;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class AppConstant {

    public static final int ClientRoleID = 3;
    public static final int ResponseFailed = 0;
    public static final int ResponseSuccess= 1;
    public static final String BASEURL = "http://ws-srv-net.in.webmyne.com/Applications/Zemulla";
    public static final String BASEURLSERVICEURL = String.format("%s%s", BASEURL, "/WCF/Services/");
    public static final String PrivacyPolicy = String.format("%s%s", BASEURL, "/Marketing/PrivacyPolicy/4");
    public static final String TermsAndConditions = String.format("%s%s", BASEURL, "/Marketing/TermsAndConditions/2");

    //------------------

    public static final String AccountSVC = "Account.svc/json/";
    public static final String GETCOUNTRYLISTAD = AccountSVC + "GetCountryListAD";
    public static final String Login = AccountSVC + "LoginAD";


}
