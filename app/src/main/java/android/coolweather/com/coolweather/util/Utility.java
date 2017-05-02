package android.coolweather.com.coolweather.util;

import android.coolweather.com.coolweather.db.City;
import android.coolweather.com.coolweather.db.County;
import android.coolweather.com.coolweather.db.Province;
import android.coolweather.com.coolweather.gson.HeWeather;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
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
            }
        }
        return false;
    }
    public static boolean handleCityResponse(String response,int privateId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                for(int i = 0; i<allCities.length();i++){
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(privateId);
                    city.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handleCountryResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray jsonObjectCity = new JSONArray(response);
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
            }
        }
        return false;
    }

    public static HeWeather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,HeWeather.class);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
