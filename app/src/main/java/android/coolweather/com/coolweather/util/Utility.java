package android.coolweather.com.coolweather.util;

import android.coolweather.com.coolweather.MyApplication;
import android.coolweather.com.coolweather.db.City;
import android.coolweather.com.coolweather.db.County;
import android.coolweather.com.coolweather.db.Province;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hasee on 2017/4/23.
 */

public class Utility {
    /**
     *接收OKHttp的response，处理Json数据并储存进数据库
     * 共三个函数，分别处理省市区
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray jsonObjectProvince = new JSONArray(response);
                for(int i = 0;i<jsonObjectProvince.length();i++){
                    JSONObject jsonObject = jsonObjectProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(jsonObject.getString("name"));
                    province.setProvinceCode(jsonObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(MyApplication.getContext(), "get json error", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
    public static boolean handleCityResponse(String response,int privateId){
        if(TextUtils.isEmpty(response)){
            try {
                JSONArray jsonObjectCity = new JSONArray();
                for(int i = 0; i<jsonObjectCity.length();i++){
                    JSONObject jsonObject = jsonObjectCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(jsonObject.getString("name"));
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setProvinceId(privateId);
                    city.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MyApplication.getContext(), "get json error", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
    public static boolean handleCountryResponse(String response,int cityId){
        if(TextUtils.isEmpty(response)){
            try {
                JSONArray jsonObjectCity = new JSONArray();
                for(int i = 0; i<jsonObjectCity.length();i++){
                    JSONObject jsonObject = jsonObjectCity.getJSONObject(i);
                    County country = new County();
                    country.setCityId(cityId);
                    country.setCountryName(jsonObject.getString("name"));
                    country.setWeatherId(jsonObject.getString("weather_id"));
                    country.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MyApplication.getContext(), "get json error", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}
