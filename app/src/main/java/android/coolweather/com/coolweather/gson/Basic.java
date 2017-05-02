package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hasee on 2017/4/25.
 */

public class Basic {
    @SerializedName("city")
    public String cityname;

    @SerializedName("id")
    public String weatherId;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;

    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
