package com.zemulla.android.app.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by raghavthakkar on 16-09-2016.
 */

public class SMSReceiver extends BroadcastReceiver {

    public SMSListener mSmsListener;

    public void setSmsListener(SMSListener smsListener) {
        mSmsListener = smsListener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        String msg = "Dear Member,  Your One Time Password is ";
                        String opt = msgBody.substring(msg.length(), msg.length() + 6);
                        if (mSmsListener != null) {
                            mSmsListener.readSMS(opt);
                        }
                        //Toast.makeText(context, opt, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, msgBody, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    public void unRegisterListener() {
        if (mSmsListener != null) {
            mSmsListener = null;
        }
    }
}
