package android.coolweather.com.coolweather;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by hasee on 2017/4/23.
 */

public class MyApplication extends Application{
    private static Context context;
    public void onCreate(){
        context = getApplicationContext();
        LitePalApplication.initialize(context);
    }
    public static Context getContext(){
        return context;
    }

}
