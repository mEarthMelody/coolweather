package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hasee on 2017/4/25.
 */
/**
 * aqi
 * */
public class AQI {

    @SerializedName("city")
    public AQICity city;

    public class AQICity{
        @SerializedName("aqi")
        public String aqi;

        @SerializedName("no2")
        public String no2;

        @SerializedName("o3")
        public String o3;

        @SerializedName("pm10")
        public String pm10;

        @SerializedName("pm25")
        public String pm25;

        @SerializedName("qlty")
        public String qlty;

        @SerializedName("so2")
        public String so2;
    }
}
