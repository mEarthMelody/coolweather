package android.coolweather.com.coolweather;

/**
 * Created by hasee on 2017/4/30.
 */

public class Weekday {
    private final static int WEEK_DAY = 7;
    private final static int DAY_200011 = 6;
    public static String getday(String date){
        if("".equals(date))
            return "";
        String time[] = date.split("-");
        int year = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]);
        int day = Integer.parseInt(time[2]);
        int sum = 0;
        int add = (year%4 == 0)?1:0;
        year -=2001;
        sum = year*365 + year/4 + 1;
        if(month>1)
            sum+=31;
        if(month>2)
            sum+=(28 + add);
        if(month>3)
            sum+=31;
        if(month>4)
            sum+=30;
        if(month>5)
            sum+=31;
        if(month>6)
            sum+=30;
        if(month>7)
            sum+=31;
        if(month>8)
            sum+=31;
        if(month>9)
            sum+=30;
        if(month>10)
            sum+=31;
        if(month>11)
            sum+=30;
        sum+=day + (7 - DAY_200011 + 1) + 4;
        int weekday = sum%WEEK_DAY;
        String w[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        return w[weekday];

    }
}
