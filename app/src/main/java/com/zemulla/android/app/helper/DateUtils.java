package com.zemulla.android.app.helper;


import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

//    D	day in year	(Number)	189
//    E	day of week	(Text)	E/EE/EEE:Tue, EEEE:Tuesday, EEEEE:T
//    F	day of week in month	(Number)	2 (2nd Wed in July)
//    G	era designator	(Text)	AD
//    H	hour in day (0-23)	(Number)	0
//    K	hour in am/pm (0-11)	(Number)	0
//    L	stand-alone month	(Text)	L:1 LL:01 LLL:Jan LLLL:January LLLLL:J
//    M	month in year	(Text)	M:1 MM:01 MMM:Jan MMMM:January MMMMM:J
//    S	fractional seconds	(Number)	978
//    W	week in month	(Number)	2
//    Z	time zone (RFC 822)	(Time Zone)	Z/ZZ/ZZZ:-0800 ZZZZ:GMT-08:00 ZZZZZ:-08:00
//    a	am/pm marker	(Text)	PM
//    c	stand-alone day of week	(Text)	c/cc/ccc:Tue, cccc:Tuesday, ccccc:T
//    d	day in month	(Number)	10
//    h	hour in am/pm (1-12)	(Number)	12
//    k	hour in day (1-24)	(Number)	24
//    m	minute in hour	(Number)	30
//    s	second in minute	(Number)	55
//    w	week in year	(Number)	27
//    y	year	(Number)	yy:10 y/yyy/yyyy:2010
//    z	time zone	(Time Zone)	z/zz/zzz:PST zzzz:Pacific Standard Time
//    '	escape for text	(Delimiter)	'Date=':Date=
//            ''	single quote	(Literal)	'o''clock':o'clock

    public static final String HHmmss = "HH:mm:ss";
    public static final String HHmm = "HH:mm";
    public static final String hhmm = "hh:mm";
    public static final String hhmmAMPM = "hh:mm a";
    public static final String hhmmss = "hh:mm:ss";

    public static final String dd = "dd";
    public static final String MMM = "MMM";
    public static final String yyyy = "yyyy";

    public static final String yyyyMMDD = "yyyy-MM-dd";
    public static final String mDDMMMYYYY = "dd-MMM-yyyy";
    public static final String mDDMMYYYY = "dd-MM-yyyy";
    public static final String mMMMDDYYY = "MMM dd, yyyy";

    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String HHmmssMMddyyyy = "HH:mm:ss MM-dd-yyyy";
    public static final String EEMMddyyyyHHmmss = "EE, MM-dd-yyyy  HH:mm:ss";
    public static final String mMMMDDYYYhhmmAMPM = mMMMDDYYY + " " + hhmmAMPM;
    public static final String ddMMMMyyyy = "dd MMMM, yyyy";
    public static final String ddMMMM = "dd MMMM";
    public static final String ddMMM = "dd MMM";
    public static final String ddMMMMyyyy_hhmmAMPM = "dd MMMM, yyyy hh:mm a";
    public static final String emmmdd_hhmmsszyyyy = "E MMM dd HH:mm:ss Z yyyy";


    public static final String MoningTime1 = "04:00 AM";
    public static final String MoningTime2 = "11:59 AM";

    public static final String AfternoonTime1 = "12:00 PM";
    public static final String AfternoonTime2 = "03:59 PM";

    public static final String EveningTime1 = "04:00 PM";
    public static final String EveningTime2 = "07:59 PM";

    public static final String NightTime1 = "08:00 PM";
    public static final String NightTime2 = "03:59 AM";
    public static final String NightTime3 = "00:00 AM";


    public static final int StartTime = 4;
    public static final int EndTime = 3;

    public static final Calendar calendar = Calendar.getInstance();

    public static String dateToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date).trim();
    }

        public static Date stringToDate(String dateStr, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }


        public static String formatDate(Date date, String type)     {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(String inputDate, String inputPattern, String outputPattern) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        date = inputFormat.parse(inputDate);
        str = outputFormat.format(date);
        return str;
    }


    public static Date parseDate(String dateStr, String type) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
           // e.printStackTrace();
        }
        return date;

    }


    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }


    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getTodayDate(String type) {
        Date d1 = new Date();
        try {
            return stringToDate(formatDate(d1, type), type);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return new SimpleDateFormat(type).format(today.getTime()).trim();
        return null;
    }

    public static String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "今天";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "昨天";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
            return "前天";
        } else if (time > todayStartTime + oneDay) { // future
            return "将来某一天";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }


    private String translateDate(long time, long curTime) {
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance();    //今天
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1分钟前";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + "分钟前";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

    public static Date getFirstDayOfLastWeekRange() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -7);
        Date start = c.getTime();
        return start;
    }

    public static String[] getDashboardDate(Calendar cal) {
        Date date = cal.getTime();
        String[] fullDate = new String[3];
        fullDate[0] = dateToString(date, dd);
        fullDate[1] = dateToString(date, MMM);
        fullDate[2] = dateToString(date, yyyy);
        return fullDate;
    }

    public static Date getTomorrow(Calendar calendar) {

        Log.e("hrs", calendar.get(Calendar.HOUR_OF_DAY) + "");
        Log.e("rem_hrs", 24 - calendar.get(Calendar.HOUR_OF_DAY) + "");

        calendar.add(Calendar.HOUR_OF_DAY, 24 - calendar.get(Calendar.HOUR_OF_DAY));
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        // cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.HOUR_OF_DAY, EndTime);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, StartTime);
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
       /* cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));*/
        //
        return cal.getTime();
    }

    public static Date parseDateToDate(String inputDate, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;

        try {
            date = outputFormat.parse(inputFormat.format(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
