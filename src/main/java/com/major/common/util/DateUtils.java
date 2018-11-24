package com.major.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Title: 日期工具类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/19 15:00      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class DateUtils {

    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_YESTERDAY = "昨天 HH:mm";
    public static final String HH_MM_BEFORE_YESTERDAY = "前天 HH:mm";
    public static final String MM_DD_HH_MM = "MM月dd日 HH:mm";
    public static final String YYYY_MM_DD_HH_MM = "yyyy年MM月dd日 HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String SDF_DAT = "yyyy-MM-dd";
    public static final String YYYY_MM_DD = "yyyyMMdd";
    public static final String YYYY_MM_DD_DEFEAT = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final int TODAY = 0;
    public static final int YESTERDAY  = 1;
    public static final int BEFORE_YESTERDAY = 2;
    public static final Long THREE_DAY_MINUTE=4320L;
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static String getTimestamp() {
        return String.valueOf(getCurrentTimestamp()/1000);
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 将字符串按yyyy-MM-dd HH:mm:ss格式转换成Date类型的日期
     * @param ts 要转换的字符串
     * @return
     */
    public static Date dateTime(String ts) {
        return dateTime(YYYY_MM_DD_HH_MM_SS, ts);
    }

    /**
     * 将字符串按输出格式转换成Date类型的日期
     * @param format 输出格式
     * @param ts 要转换的字符串
     * @return
     */
    public static Date dateTime(String format, String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Date类型的日期按输出格式转换成时间字符串
     * @param format 输出格式
     * @param date 要转换的Date类型的日期
     * @return
     */
    public static String parseDateToStr(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 判断两个日期是否同年份
     * @param smallDate
     * @param bigDate
     * @return 返回true表示同年份
     */
    public static boolean isSameYear(Date smallDate, Date bigDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(smallDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(bigDate);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        if (year1 != year2) {
            return false;
        }

        return true;
    }

    /**
     * 计算两个同年份日期之间相差的天数
     * @param smallDate
     * @param bigDate
     * @return
     */
    public static int differentDaysBySameYear(Date smallDate, Date bigDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(smallDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(bigDate);

        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }

    /**
     * 获取上个月日期
     * @param format 格式
     * @return
     */
    public static String getLastMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return parseDateToStr(format, cal.getTime());
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smallDate 较小的时间
     * @param bigDate 较大的时间
     * @return
     */
    public static int differentDays(Date smallDate, Date bigDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(smallDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(bigDate);

        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        if (year1 != year2) {
            // 不同年
            int timeDistance = 0;

            for(int i = year1; i < year2; i ++) {
                boolean flag = i%4==0 && i%100!=0 || i%400==0;
                if(flag) {
                    // 闰年
                    timeDistance += 366;
                } else {
                    // 不是闰年
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1);
        } else {
            // 同一年
            return day2 - day1;
        }
    }

    public static long differentSeconds(Date smallDate, Date bigDate) {
        return (bigDate.getTime() - smallDate.getTime()) / 1000;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date monStrToDate(String dateStr) throws ParseException{
        return dateStrToDate(dateStr, DateUtils.SDF_DAT);
    }
    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param dateStr     日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date dateStrToDate(String dateStr, String pattern) throws ParseException {
        Date myDate = null;
        if (dateStr != null) {
            myDate = getDateFormat(pattern).parse(dateStr);
        }
        return myDate;
    }
    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern)  throws RuntimeException {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取当前时间点离一天结束剩余秒数（线程安全）
     * @param currentDate
     * @return
     */
    public static long getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        return ChronoUnit.SECONDS.between(currentDateTime, midnight);
    }

    public static long getRemainSecondsOneMonth(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        return ChronoUnit.SECONDS.between(currentDateTime, midnight);
    }

    /**
     *获取N天前的0点到24点之间的时间段
     * @param date
     * @return
     */
    public static Date[] theDayBefore(Date date,int day){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day *(-1));
        date = calendar.getTime();
        String smallTimeString = parseDateToStr(DateUtils.YYYY_MM_DD_DEFEAT, date)+" 00:00:00";
        String bigTimeString = parseDateToStr(DateUtils.YYYY_MM_DD_DEFEAT, date)+" 23:59:59";
        Date smallTime  = dateTime(smallTimeString);
        Date bigTime =  dateTime(bigTimeString);
        Date[] dates = {smallTime,bigTime};
        return dates;
    }

    /**
     * 给时间加十分钟
     * @param begin
     * @return
     */
    public static String addTenTime(Date begin) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date afterDate = new Date(begin .getTime() + 600000);
        return sdf.format(afterDate );
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static Date getFutureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        return  calendar.getTime();
    }

    /**
     * 获取时间的cron表达式
     * @param date 指定时间
     * @param amount 上下浮动秒数
     * @return
     */
    public static String getCronExpression(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, amount);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String cronExpression = second + " " + minute + " " + hour + " " + day + " " + month + " ? " + year;
        return cronExpression;
    }
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ?";

    public static Date getDate(final String cron) {
        if(cron == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;// 此处缺少异常处理,自己根据需要添加
        }
        return date;
    }

    /**
     * 计算两日期相差分钟数
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long dateMethod(Date startTime,Date endTime) {
        try {
            long diff = endTime.getTime() - startTime.getTime();
            //计算两个时间之间差了多少分钟
            Long minutes = diff / (1000 * 60);
            return minutes;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0L;

    }

    public static void main(String[] args) {
        Date d1 = DateUtils.dateTime("2018-10-15 17:50:42");
        System.out.println(DateUtils.getCronExpression(d1, 0));
        System.out.println(DateUtils.getRemainSecondsOneMonth(DateUtils.getNowDate()));

    }

}
