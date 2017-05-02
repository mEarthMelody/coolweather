package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hasee on 2017/4/28.
 */

public class HeWeather {
    @SerializedName("aqi")
    public AQI aqi;

    @SerializedName("basic")
    public Basic basic;

    @SerializedName("daily_forecast")
    public List<Daily> dailyList;

    @SerializedName("hourly_forecast")
    public List<Hourly> hourlyList;

    @SerializedName("now")
    public Now now;

    @SerializedName("status")
    public String status;

    @SerializedName("suggestion")
    public Suggestion suggestion;
}
