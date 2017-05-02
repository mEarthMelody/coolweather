package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hasee on 2017/4/25.
 */

public class Now {

    @SerializedName("cond")
    public Cond cond;

    public class Cond {
        @SerializedName("code")
        public String code;

        @SerializedName("txt")
        public String txt;
    }

    @SerializedName("fl")
    public String fl;

    @SerializedName("hum")
    public String hum;

    @SerializedName("pcpn")
    public String pcpn;

    @SerializedName("pres")
    public String pres;

    @SerializedName("tmp")
    public String tmp;

    @SerializedName("wind")
    public Wind wind;

    @SerializedName("vis")
    public String vis;

    public class Wind{
        @SerializedName("dir")
        public String dir;

        @SerializedName("sc")
        public String sc;

        @SerializedName("spd")
        public String spd;
    }
}
