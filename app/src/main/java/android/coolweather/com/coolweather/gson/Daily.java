package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hasee on 2017/4/28.
 */

public class Daily {
    @SerializedName("astro")
    public Astro astro;

    public class Astro{
        @SerializedName("mr")
        public String mr;

        @SerializedName("ms")
        public String ms;

        @SerializedName("sr")
        public String sr;

        @SerializedName("ss")
        public String ss;
    }

    @SerializedName("cond")
    public Cond cond;

    public class Cond{
        @SerializedName("code_d")
        public String coded;
        @SerializedName("code_n")
        public String coden;
        @SerializedName("txt_d")
        public String txtd;
        @SerializedName("txt_n")
        public String txtn;
    }

    @SerializedName("date")
    public String date;

    @SerializedName("hum")
    public String hum;

    @SerializedName("pcpn")
    public String pcpn;

    @SerializedName("pop")
    public String pop;

    @SerializedName("pres")
    public String pres;

    @SerializedName("tmp")
    public Tmp tmp;

    public class Tmp{
        @SerializedName("max")
        public String max;

        @SerializedName("min")
        public String min;
    }

    @SerializedName("uv")
    public String uv;

    @SerializedName("vis")
    public String vis;

    @SerializedName("wind")
    public Wind wind;

    public class Wind{
        @SerializedName("dir")
        public String dir;
        @SerializedName("sc")
        public String sc;
        @SerializedName("spd")
        public String spd;
    }
}
