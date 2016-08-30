package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.reports.ReportsAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DatabaseHandler;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.user.notification.CheckedNotificationRequest;
import com.zemulla.android.app.model.user.notification.CheckedNotificationResponse;
import com.zemulla.android.app.model.user.notification.Notification;
import com.zemulla.android.app.model.user.notification.NotificationAdapter;
import com.zemulla.android.app.model.user.notification.ResponseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notificationRecyclerView)
    RecyclerView notificationRecyclerView;

    NotificationAdapter notificationAdapter;
    List<Notification> notifications;
    ProgressDialog progressDialog;

    ReportsAPI reportsAPI;
    CheckedNotificationRequest checkedNotificationRequest;
    Call<CheckedNotificationResponse> checkedNotificationResponseCall;

    DatabaseHandler databaseHandler;

    Subscription subscription, subscription1;
    HashMap<Long, Long> integerIntegerHashMap = new HashMap<>();
    @BindView(R.id.emptyImageView)
    ImageView emptyImageView;
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        hidEmptyView();
        showProgressDialog();
        init();
        initToolbar();
        initRecyclerView();
        getNotification();
    }

    private void init() {
        reportsAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
        databaseHandler = new DatabaseHandler(this);
        checkedNotificationRequest = new CheckedNotificationRequest();
        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notifications, this);

    }

    private void initToolbar() {
        if (toolbar != null) {
            try {
                if (toolbar != null) {
                    toolbar.setTitle("Notifications");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntentWithClearFlag(NotificationActivity.this, HomeActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Functions.fireIntentWithClearFlag(NotificationActivity.this, HomeActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private void initRecyclerView() {
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notificationRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int pixelPadding = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(pixelPadding, pixelPadding, pixelPadding, pixelPadding);
            }
        });
    }


    private void getNotification() {

        try {
            notifications = databaseHandler.getAllNotification();
            if (notifications.size() == 0) {
                showEmptyView();
            } else {

            }
            notificationAdapter.setItems(notifications);
            getMaxValuePIDNotification(notifications);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getMaxValuePIDNotification(final List<Notification> notifications) {


        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        subscription = Observable.from(notifications)
                .distinct(new Func1<Notification, Object>() {
                    @Override
                    public Object call(Notification notification) {
                        return notification.getServiceDetailID();
                    }
                })
                .subscribe(new Action1<Notification>() {
                    @Override
                    public void call(final Notification notification1) {
                        subscription1 = Observable.from(notifications).filter(new Func1<Notification, Boolean>() {
                            @Override
                            public Boolean call(Notification notification) {
                                return notification1.getServiceDetailID() == notification.getServiceDetailID();
                            }
                        }).toSortedList(new Func2<Notification, Notification, Integer>() {
                            @Override
                            public Integer call(Notification notification, Notification notification2) {
                                return notification1.getPKID() > notification2.getPKID() ? -1 : 0;
                            }
                        }).subscribe(new Action1<List<Notification>>() {
                            @Override
                            public void call(List<Notification> notifications) {

                                integerIntegerHashMap.put(notifications.get(0).getServiceDetailID(), notifications.get(0).getPKID());

                            }
                        });
                    }
                });


        List<ResponseData> responseDatas = new ArrayList<>();
        for (Map.Entry<Long, Long> longLongHashMap : integerIntegerHashMap.entrySet()) {

            long serviceDetailsID = longLongHashMap.getKey();
            long pid = longLongHashMap.getValue();
            ResponseData responseData = new ResponseData();
            responseData.setPKID(pid);
            responseData.setServiceDetailID(serviceDetailsID);
            responseData.setUserID(PrefUtils.getUserID(this));
            responseDatas.add(responseData);
        }

        checkedNotificationRequest.setResponseData(responseDatas);
        callCheckNotification();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Functions.removeSubscitions(subscription, subscription1);

    }

    private void callCheckNotification() {
        checkedNotificationResponseCall = reportsAPI.checkedNotificationCommon(checkedNotificationRequest);
        checkedNotificationResponseCall.enqueue(new Callback<CheckedNotificationResponse>() {
            @Override
            public void onResponse(Call<CheckedNotificationResponse> call, Response<CheckedNotificationResponse> response) {
                hidProgressDialog();
                if (response.isSuccessful() && response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                    PrefUtils.setNotificationOpen(NotificationActivity.this, false);
                } else {
                    Functions.showError(NotificationActivity.this, "Failed to Update", false);
                }
            }

            @Override
            public void onFailure(Call<CheckedNotificationResponse> call, Throwable t) {
                hidProgressDialog();
                Functions.showError(NotificationActivity.this, false);
            }
        });
    }


    public void showEmptyView() {
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyImageView.setImageResource(R.drawable.ic_notifications_white_24dp);
        emptyTextView.setText("No Notification");
    }

    public void hidEmptyView() {
        emptyImageView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            initProgressDialog();
        }
        progressDialog.show();
    }

    private void hidProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
