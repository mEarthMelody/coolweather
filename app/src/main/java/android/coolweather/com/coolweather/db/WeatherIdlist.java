package android.coolweather.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by hasee on 2017/5/1.
 */

public class WeatherIdlist extends DataSupport {
    public String getmWeatherId() {
        return mWeatherId;
    }

    public void setmWeatherId(String mWeatherId) {
        this.mWeatherId = mWeatherId;
    }

    private String mWeatherId;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    private String titleName;

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private Boolean isLocked;

    private String code;

    private String temp;
}
