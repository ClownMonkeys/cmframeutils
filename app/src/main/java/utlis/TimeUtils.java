package utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ProjectName：cmframeutils
 * PackageName：utlis
 * FileName：TimeUtils.java
 * Date：2015/8/14 36
 * Author：大鹏
 * ClassName:TimeUtils
 **/
public class TimeUtils {

    public static final int ONE_MINUTE = 1000 * 60;
    public static final int ONE_HOUR =  60 * ONE_MINUTE;
    public static final int ONE_DAY = 24 * ONE_HOUR;

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_YEAR_MONTH_DAY   = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_DAY_MONTH_YEAR   = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat DATE_YEAR_MONTH_DAY_HOURS_MINUTES= new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_HOURS_MINUTES= new SimpleDateFormat("HH:mm");


    public static  int getIntervalHour(long time){
        return (int) (time / ONE_HOUR);
    }

    public static int getIntervalMinute(long time){
        return (int) (time % ONE_HOUR / ONE_MINUTE);
    }

    public static int getIntervalSecond(long time){
        return (int)(time % ONE_HOUR % ONE_MINUTE / 1000);
    }

    public static long getCurrentTime(long serverTimeDiff){
        return (int)(System.currentTimeMillis() - serverTimeDiff);
    }

    /**
     * long time to string
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    /**
     * 得到二个日期间的间隔天数
     *  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
     */
    public static String getTwoDay(String startTime, String endTime,SimpleDateFormat dateFormat) {
        long day = 0;
        try {
            java.util.Date date = dateFormat.parse(startTime);
            java.util.Date mydate = dateFormat.parse(endTime);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
            if (day < 0) {
                String dateString = dateFormat.format(mydate);
                return dateString + "";
            }
        } catch (Exception e) {
            return "";
        }
        return day + "天";
    }
    public static String getUserDate(String sformat,SimpleDateFormat dateFormat) {
        Date currentTime = null;
        try {
            currentTime = dateFormat.parse(sformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = dateFormat.format(currentTime);
        return dateString;
    }
    /**
     *
     * compareDate
     * 比较日期<br/>
     * @param date1
     * @param date2
     * @return
     * Boolean
     * @exception
     * @since  1.0.0
     */
    public static Boolean compareDate(String date1, String date2,SimpleDateFormat simpleDateFormat) {
        Boolean flag = false;
        try {
            Date d1 = simpleDateFormat.parse(date1);
            Date d2 = simpleDateFormat.parse(date2);
            flag = d1.before(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     *
     * format
     * 时间格式
     * @param date
     * @param simpleDateFormat
     * @return
     * String
     * @exception
     * @since  1.0.0
     */
    public static String format(String date,SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.format(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
