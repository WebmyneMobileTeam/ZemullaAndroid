package com.zemulla.android.app.topup.cyber;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zemulla.android.app.R;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.payment.generatesignaturecs.GenerateSignatureCSResponse;
import com.zemulla.android.app.widgets.customdialog.SweetAlertDialog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CyberSourceWebViewActivity extends AppCompatActivity {

    GenerateSignatureCSResponse generateSignatureCSResponse;
    @BindView(R.id.webView)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    // String cardNumber, cardDate, amount, cardType;

    SweetAlertDialog sweetAlertDialogSuccess, sweetAlertDialogFailed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cyber_source_web_view);
        ButterKnife.bind(this);
        webview = (WebView) findViewById(R.id.webView);
        getDataFromIntent();
        webview.setVisibility(View.INVISIBLE);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setDomStorageEnabled(true);

        // this line did the trick for returning from secure return URL from payment gateway.
        if (Build.VERSION.SDK_INT >= 21) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, final String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


                Log.e("URL Started..", url);
                if (url.trim().equalsIgnoreCase(AppConstant.successURL)) {
                    progressBar.setVisibility(View.GONE);
                    sweetAlertDialogSuccess = Functions.showSuccessMsgReturn(CyberSourceWebViewActivity.this, "Transaction done successfully.", true, HomeActivity.class);

                } else if (url.trim().equalsIgnoreCase(AppConstant.failedURl)) {
                    progressBar.setVisibility(View.GONE);
                    sweetAlertDialogFailed = Functions.showFailedMsgReturn(CyberSourceWebViewActivity.this, "Transaction Failed.", true, HomeActivity.class);
                    if (sweetAlertDialogSuccess != null && sweetAlertDialogSuccess.isShowing()) {
                        sweetAlertDialogSuccess.dismiss();
                    }
                    sweetAlertDialogFailed.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Log.e("URL Loading..", url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  Log.e("URL Loading..", url);
                return super.shouldOverrideUrlLoading(view, url);


            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                //  Log.e("Webview", "JS Alert");
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                //  Log.e("Webview", "JS Confirm");
                return super.onJsConfirm(view, url, message, result);
            }
        });


    }

    private void test() {
//        Map<String, String> mapParams = new HashMap<String, String>();
//
//        mapParams.put("access_key", "adfa872ea41e34b5b2c36275fe48504e");
//        mapParams.put("profile_id", "4FBE6C9A-AA5D-491A-91D5-6E9FBF3E556A");
//        mapParams.put("transaction_uuid", "e590da7b-035f-46ef-bcda-09c6184598bf");
//        mapParams.put("signed_field_names", "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code");
//        mapParams.put("unsigned_field_names", "card_type,card_number,card_expiry_date");
//        mapParams.put("signed_date_time", "2016-09-12T14:47:23Z");
//        mapParams.put("locale", "en");
//        mapParams.put("transaction_type", "authorization");
//        mapParams.put("reference_number", "ZMW046794282360");
//        mapParams.put("amount", "1000.0");
//        mapParams.put("currency", "ZMW");
//        mapParams.put("payment_method", "card");
//        mapParams.put("bill_to_forename", "Raghav");
//        mapParams.put("bill_to_surname", "thakkar");
//        mapParams.put("bill_to_email", "abc@123.com");
//        mapParams.put("bill_to_phone", "9898989898");
//        mapParams.put("bill_to_address_line1", "asd");
//        mapParams.put("bill_to_address_city", "vadodara");
//        mapParams.put("bill_to_address_postal_code", "390007");
//        mapParams.put("bill_to_address_state", "CA");
//        mapParams.put("bill_to_address_country", "IN");
//        mapParams.put("card_type", "001");
//        mapParams.put("card_number", "4111111111111111");
//        mapParams.put("card_expiry_date", "12-2020");
//        mapParams.put("signature", "L4B2LqPbve+0lNBLegTC8zZ2GFjEuPBKB5CBpQ7l510=");
//
//
//        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
//        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);

//
        Map<String, String> mapParams = new HashMap<String, String>();

        mapParams.put("access_key", "adfa872ea41e34b5b2c36275fe48504e");
        mapParams.put("profile_id", "4FBE6C9A-AA5D-491A-91D5-6E9FBF3E556A");
        mapParams.put("transaction_uuid", "73ccc953-c291-41f0-a09d-a223b4213509");
        mapParams.put("signed_field_names", "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code");
        mapParams.put("unsigned_field_names", "card_type,card_number,card_expiry_date");
        mapParams.put("signed_date_time", "2016-09-12T15:05:46Z");
        mapParams.put("locale", "en");
        mapParams.put("transaction_type", "authorization");
        mapParams.put("reference_number", "ZMW053414200428");
        mapParams.put("amount", "1000.00");
        mapParams.put("currency", "ZMW");
        mapParams.put("payment_method", "card");
        mapParams.put("bill_to_forename", "vaibhavii");
        mapParams.put("bill_to_surname", "panchal");
        mapParams.put("bill_to_email", "vaibhavipanchal04@gmail.com");
        mapParams.put("bill_to_phone", "9904188487");
        mapParams.put("bill_to_address_line1", "vadodara");
        mapParams.put("bill_to_address_city", "zambia");
        mapParams.put("bill_to_address_postal_code", "390009");
        mapParams.put("bill_to_address_state", "CA");
        mapParams.put("bill_to_address_country", "ZM");
        mapParams.put("card_type", "001");
        mapParams.put("card_number", "4111111111111111");
        mapParams.put("card_expiry_date", "12-2020");
        mapParams.put("signature", "aegQnk8AfTzmxj79DeppJ34pupatuFmGGZPJPuj3lwE=");


        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        generateSignatureCSResponse = (GenerateSignatureCSResponse) intent.getSerializableExtra(Intent.EXTRA_REFERRER);
        // amount = intent.getStringExtra(IntentConstant.EXTRA_AMOUNT);
        // cardNumber = intent.getStringExtra(IntentConstant.EXTRA_CARD_NUMER);
        //  cardDate = intent.getStringExtra(IntentConstant.EXTRA_CARD_DATE);
        //  cardType = intent.getStringExtra(IntentConstant.EXTRA_CARD_TYPE);
        if (generateSignatureCSResponse == null) {
            Functions.showFailedMsg(CyberSourceWebViewActivity.this, "Error,Please try again later.", true, HomeActivity.class);
        } else {
            realData();
            // test();
            // test30();
        }
    }

    private void test30() {
        Map<String, String> mapParams = new HashMap<String, String>();

        mapParams.put("access_key", "adfa872ea41e34b5b2c36275fe48504e");
        mapParams.put("profile_id", "4FBE6C9A-AA5D-491A-91D5-6E9FBF3E556A");
        mapParams.put("transaction_uuid", "ec361671-6a0d-40f1-a202-becdecd01bdd");
        mapParams.put("signed_field_names", "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code");
        mapParams.put("unsigned_field_names", "card_type,card_number,card_expiry_date");
        mapParams.put("signed_date_time", "2016-09-13T06:10:34Z");
        mapParams.put("locale", "en");
        mapParams.put("transaction_type", "authorization");
        mapParams.put("reference_number", "ZMW020675416167");
        mapParams.put("amount", "1000.00");
        mapParams.put("currency", "ZMW");
        mapParams.put("payment_method", "card");
        mapParams.put("bill_to_forename", "Raghav");
        mapParams.put("bill_to_surname", "thakkar");
        mapParams.put("bill_to_email", "abc@123.com");
        mapParams.put("bill_to_phone", "9898989898");
        mapParams.put("bill_to_address_line1", "asd");
        mapParams.put("bill_to_address_city", "vadodara");
        mapParams.put("bill_to_address_postal_code", "390007");
        mapParams.put("bill_to_address_state", "CA");
        mapParams.put("bill_to_address_country", "IN");
        mapParams.put("card_type", "001");
        mapParams.put("card_number", "4111111111111111");
        mapParams.put("card_expiry_date", "12-2020");
        mapParams.put("signature", "WWUdMKv4ev2CTqNOzgNJVAJt9W4l7bPJ3JV61ky+AC0=");


        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);
    }

    private void realData() {

        Map<String, String> mapParams = new HashMap<String, String>();

        mapParams.put("access_key", generateSignatureCSResponse.getAccess_key());
        mapParams.put("bill_to_address_city", generateSignatureCSResponse.getBill_to_address_city());
        mapParams.put("bill_to_address_country", generateSignatureCSResponse.getBill_to_address_country());
        mapParams.put("bill_to_address_line1", generateSignatureCSResponse.getBill_to_address_line1());
        mapParams.put("bill_to_address_postal_code", generateSignatureCSResponse.getBill_to_address_postal_code());
        mapParams.put("bill_to_address_state", generateSignatureCSResponse.getBill_to_address_state());
        mapParams.put("bill_to_email", generateSignatureCSResponse.getBill_to_email());
        mapParams.put("bill_to_forename", generateSignatureCSResponse.getBill_to_forename());
        mapParams.put("bill_to_phone", generateSignatureCSResponse.getBill_to_phone());
        mapParams.put("bill_to_surname", generateSignatureCSResponse.getBill_to_surname());
        mapParams.put("currency", generateSignatureCSResponse.getCurrency());
        mapParams.put("locale", generateSignatureCSResponse.getLocale());
        mapParams.put("payment_method", "card");
        mapParams.put("profile_id", generateSignatureCSResponse.getProfile_id());
        mapParams.put("reference_number", generateSignatureCSResponse.getReference_number().trim());
        mapParams.put("signature", generateSignatureCSResponse.getSignature().trim());
        mapParams.put("signed_date_time", generateSignatureCSResponse.getSigned_date_time().trim());
        mapParams.put("signed_field_names", "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code");
        mapParams.put("transaction_type", "authorization");
        mapParams.put("transaction_uuid", generateSignatureCSResponse.getTransaction_uuid().trim());
        mapParams.put("unsigned_field_names", "card_type,card_number,card_expiry_date");
        mapParams.put("amount", generateSignatureCSResponse.getAmount());
        mapParams.put("card_type", generateSignatureCSResponse.getCard_type());
        mapParams.put("card_number", generateSignatureCSResponse.getCard_number());
        mapParams.put("card_expiry_date", generateSignatureCSResponse.getCard_expiry_date());


        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);

//        Map<String, String> mapParams = new HashMap<String, String>();
//
//        mapParams.put("access_key", "adfa872ea41e34b5b2c36275fe48504e");
//        mapParams.put("bill_to_address_city", "vadodara");
//        mapParams.put("bill_to_address_country", "IN");
//        mapParams.put("bill_to_address_line1", "asd");
//        mapParams.put("bill_to_address_postal_code", "390007");
//        mapParams.put("bill_to_address_state", "CA");
//        mapParams.put("bill_to_email", "abc@123.com");
//        mapParams.put("bill_to_forename", "Raghav");
//        mapParams.put("bill_to_phone", "9898989898");
//        mapParams.put("bill_to_surname", "thakkar");
//        mapParams.put("currency", "ZMW");
//        mapParams.put("locale", "en");
//        mapParams.put("payment_method", "card");
//        mapParams.put("profile_id", "4FBE6C9A-AA5D-491A-91D5-6E9FBF3E556A");
//        mapParams.put("reference_number", generateSignatureCSResponse.getReference_number().trim());
//        mapParams.put("signature", generateSignatureCSResponse.getSignature().trim());
//        mapParams.put("signed_date_time", generateSignatureCSResponse.getSigned_date_time().trim());
//        mapParams.put("signed_field_names", "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code");
//        mapParams.put("transaction_type", "authorization");
//        mapParams.put("transaction_uuid", generateSignatureCSResponse.getTransaction_uuid().trim());
//        mapParams.put("unsigned_field_names", "card_type,card_number,card_expiry_date");
//        mapParams.put("amount", "1000.00");
//        mapParams.put("card_type", "001");
//        mapParams.put("card_number", "4111111111111111");
//        mapParams.put("card_expiry_date", "12-2020");
//
//
//        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
//        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);
    }

    public static void webview_ClientPost(WebView webView, String url, Collection<Map.Entry<String, String>> postData) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));

        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format("<input name='%s' type='text' value='%s' />", item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");

        webView.loadData(sb.toString(), "text/html", "UTF-8");
    }


//
//    @BindView(R.id.webView)
//    WebView webview;
//    GenerateSignatureCSResponse generateSignatureCSResponse;
//    String cardNumber, cardDate, amount, cardType;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_cyber_source_web_view);
//        ButterKnife.bind(this);
//
//        // getDataFromIntent();
//
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webview.getSettings().setSupportMultipleWindows(true);
//        webview.getSettings().setAllowContentAccess(true);
//        webview.getSettings().setDomStorageEnabled(true);
//
//        // this line did the trick for returning from secure return URL from payment gateway.
//        if (Build.VERSION.SDK_INT >= 21) {
//            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                //super.onReceivedSslError(view, handler, error);
//
//                handler.proceed();
//
//            }
//
//            @Override
//            public void onPageStarted(WebView view, final String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                Log.e("URL Loading..", view.getUrl());
//
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.e("URL Loading..", view.getUrl());
//
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e("URL Loading..", view.getUrl());
//                return super.shouldOverrideUrlLoading(view, url);
//
//
//            }
//        });
//
//        webview.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                //Required functionality here
//                Log.e("Webview", "JS Alert");
//                return super.onJsAlert(view, url, message, result);
//            }
//
//            @Override
//            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//                Log.e("Webview", "JS Confirm");
//                return super.onJsConfirm(view, url, message, result);
//            }
//        });
//
//
//        testData();
//        // realData();
//
//
//    }
//
//    private void testData() {
//        Map<String, String> mapParams = new HashMap<String, String>();
//        mapParams.put("access_key", "adfa872ea41e34b5b2c36275fe48504e");
//        mapParams.put("profile_id", "4FBE6C9A-AA5D-491A-91D5-6E9FBF3E556A");
//        mapParams.put("transaction_uuid", "7e0bd743-65c8-42bf-958a-9003823e7253");
//        mapParams.put("signed_field_names", "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code");
//        mapParams.put("unsigned_field_names", "card_type,card_number,card_expiry_date");
//        mapParams.put("signed_date_time", "2016-09-12T14:10:53Z");
//        mapParams.put("locale", "en");
//        mapParams.put("transaction_type", "authorization");
//        mapParams.put("reference_number", "ZMW050842778580");
//        mapParams.put("amount", "1000.00");
//        mapParams.put("currency", "ZMW");
//        mapParams.put("payment_method", "card");
//        mapParams.put("bill_to_forename", "Raghav");
//        mapParams.put("bill_to_surname", "thakkar");
//        mapParams.put("bill_to_email", "abc@123.com");
//        mapParams.put("bill_to_phone", "9898989898");
//        mapParams.put("bill_to_address_line1", "asd");
//        mapParams.put("bill_to_address_city", "vadodara");
//        mapParams.put("bill_to_address_postal_code", "390007");
//        mapParams.put("bill_to_address_state", "CA");
//        mapParams.put("bill_to_address_country", "IN");
//        mapParams.put("card_type", "001");
//        mapParams.put("card_number", "4111111111111111");
//        mapParams.put("card_expiry_date", "12-2020");
//        mapParams.put("signature", "ELLM/JG5ZSNir9zlWcTLC6L/O2sSELoA4o4UXSOYOB0=");
//        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
//        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);
//    }
//
//    private void realData() {
//        Map<String, String> mapParams = new HashMap<String, String>();
//
//        mapParams.put("access_key", generateSignatureCSResponse.getAccess_key());
//        mapParams.put("profile_id", generateSignatureCSResponse.getProfile_id());
//        mapParams.put("transaction_uuid", generateSignatureCSResponse.getTransaction_uuid());
//        mapParams.put("signed_field_names", generateSignatureCSResponse.getSigned_field_names());
//        mapParams.put("unsigned_field_names", generateSignatureCSResponse.getUnsigned_field_names());
//        mapParams.put("signed_date_time", generateSignatureCSResponse.getSigned_date_time());
//        mapParams.put("locale", generateSignatureCSResponse.getLocale());
//        mapParams.put("transaction_type", generateSignatureCSResponse.getTransaction_type());
//        mapParams.put("reference_number", generateSignatureCSResponse.getReference_number());
//        mapParams.put("amount", amount);
//        mapParams.put("currency", AppConstant.ZMW);
//        mapParams.put("payment_method", "card");
//
//
//        mapParams.put("bill_to_forename", generateSignatureCSResponse.getBill_to_forename());
//        mapParams.put("bill_to_surname", generateSignatureCSResponse.getBill_to_surname());
//        mapParams.put("bill_to_email", generateSignatureCSResponse.getBill_to_email());
//        mapParams.put("bill_to_phone", generateSignatureCSResponse.getBill_to_phone());
//        mapParams.put("bill_to_address_line1", generateSignatureCSResponse.getBill_to_address_line1());
//        mapParams.put("bill_to_address_city", generateSignatureCSResponse.getBill_to_address_city());
//        mapParams.put("bill_to_address_postal_code", generateSignatureCSResponse.getBill_to_address_postal_code());
//        mapParams.put("bill_to_address_state", generateSignatureCSResponse.getBill_to_address_state());
//        mapParams.put("bill_to_address_country", generateSignatureCSResponse.getBill_to_address_country().trim());
//
//
//        mapParams.put("card_type", cardType);
//        mapParams.put("card_number", cardNumber);
//        mapParams.put("card_expiry_date", cardDate);
//        mapParams.put("signature", generateSignatureCSResponse.getSignature());
//
//        Collection<Map.Entry<String, String>> postData = mapParams.entrySet();
//        webview_ClientPost(webview, "https://testsecureacceptance.cybersource.com/silent/pay", postData);
//    }
//
//    private void getDataFromIntent() {
//        Intent intent = getIntent();
//        generateSignatureCSResponse = (GenerateSignatureCSResponse) intent.getSerializableExtra(Intent.EXTRA_REFERRER);
//        amount = intent.getStringExtra(IntentConstant.EXTRA_AMOUNT);
//        cardNumber = intent.getStringExtra(IntentConstant.EXTRA_CARD_NUMER);
//        cardDate = intent.getStringExtra(IntentConstant.EXTRA_CARD_DATE);
//        cardType = intent.getStringExtra(IntentConstant.EXTRA_CARD_TYPE);
//        if (generateSignatureCSResponse == null) {
//            Functions.showFailedMsg(CyberSourceWebViewActivity.this, "Error,Please try again later.", true, HomeActivity.class);
//        }
//    }
//
//    public static void webview_ClientPost(WebView webView, String url, Collection<Map.Entry<String, String>> postData) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("<html><head></head>");
//        sb.append("<body onload='form1.submit()'>");
//        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));
//
//        for (Map.Entry<String, String> item : postData) {
//            sb.append(String.format("<input name='%s' type='text' value='%s' />", item.getKey(), item.getValue()));
//        }
//        sb.append("</form></body></html>");
//
//        webView.loadData(sb.toString(), "text/html", null);
//    }
}

