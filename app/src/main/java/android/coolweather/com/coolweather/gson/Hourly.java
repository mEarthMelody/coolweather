package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hasee on 2017/4/28.
 */

public class Hourly {

    @SerializedName("cond")
    public Cond cond;

    @SerializedName("date")
    public String date;

    @SerializedName("hum")
    public String hum;

    @SerializedName("pop")
    public String pop;

    @SerializedName("pres")
    public String pres;

    @SerializedName("tmp")
    public String tmp;

    @SerializedName("wind")
    public Wind wind;

    public class Cond{
        @SerializedName("code")
        public String code;

        @SerializedName("txt")
        public String txt;
    }

    public class Wind{
        @SerializedName("dir")
        public String dir;

        @SerializedName("sc")
        public String sc;

        @SerializedName("spd")
        public String spd;
    }
}
