package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hasee on 2017/4/25.
 */

public class Suggestion {

    @SerializedName("air")
    public Air air;

    @SerializedName("comf")
    public Comfort comf;

    @SerializedName("cw")
    public CarWash cw;

    @SerializedName("drsg")
    public Drsg drsg;

    @SerializedName("flu")
    public Flu flu;

    @SerializedName("sport")
    public Sport sport;

    @SerializedName("trav")
    public Trav trav;

    @SerializedName("uv")
    public Uv uv;

    public class Comfort{

        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class CarWash{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class Sport{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class Drsg{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class Flu{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class Trav{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class Uv{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }

    public class Air{
        @SerializedName("brf")
        public String brf;

        @SerializedName("txt")
        public String txt;
    }
}
