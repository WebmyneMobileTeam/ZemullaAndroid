package com.zemulla.android.app.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zemulla.android.app.model.user.notification.Notification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 24-08-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "zemulla.db";
    private static final String NotificationTable = "Notification";
    private static final String DATABASE_PATH = "/data/data/com.zemulla.android.app/databases/";
    private Context context;
    private SQLiteDatabase myDataBase = null;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        if (dbExist) {
//            Log.v("log_tag", "database does exist");
        } else {
//            Log.v("log_tag", "database does not exist");
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean checkDataBase() {

        File folder = new File(DATABASE_PATH);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    public void saveAllNotification(List<Notification> notifications) {

        try {
            openDatabase();
            for (Notification notification : notifications) {
                ContentValues values = new ContentValues();
                values.put("Message", notification.getMessage());
                values.put("PKID", notification.getPKID());
                values.put("ProfilePicWithURL", notification.getProfilePicWithURL());
                    values.put("ServiceDetailID", notification.getServiceDetailID());
                values.put("UserID", notification.getUserID());
                // Inserting Row

                myDataBase.insert(NotificationTable, null, values);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myDataBase.close();
        }

    }

    public void openDatabase() {
        try {
            if (myDataBase == null  || !myDataBase.isOpen()) {
                myDataBase = getWritableDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getAllNotification() {
        //String query = "select * from " + USER_SELECTED_SPORT + " where IsPrimarySport=\"" + String.valueOf(IsPrimarySport) + "\"";

        List<Notification> notifications = new ArrayList<>();
        String query = "select * from " + NotificationTable;
        try {
            myDataBase = this.getWritableDatabase();

            Cursor cursor = myDataBase.rawQuery(query, null);
            cursor.moveToFirst();
            do {
                Notification notification = new Notification();
                notification.setMessage(cursor.getString(cursor.getColumnIndex("Message")));
                notification.setPKID(cursor.getLong(cursor.getColumnIndex("PKID")));
                notification.setProfilePicWithURL(cursor.getString(cursor.getColumnIndex("ProfilePicWithURL")));
                notification.setUserID(cursor.getLong(cursor.getColumnIndex("UserID")));
                notification.setServiceDetailID(cursor.getLong(cursor.getColumnIndex("ServiceDetailID")));
                notifications.add(notification);
            } while (cursor.moveToNext());
        } catch (Exception e) {


        } finally {
            myDataBase.close();
        }
        return notifications;

    }

    private void closeDataBase() {
        try {
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
