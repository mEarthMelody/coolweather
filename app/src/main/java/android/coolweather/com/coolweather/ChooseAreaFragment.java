package android.coolweather.com.coolweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.coolweather.com.coolweather.db.City;
import android.coolweather.com.coolweather.db.County;
import android.coolweather.com.coolweather.db.Province;
import android.coolweather.com.coolweather.db.WeatherIdlist;
import android.coolweather.com.coolweather.util.HttpUtil;
import android.coolweather.com.coolweather.util.Utility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hasee on 2017/4/24.
 */

public class ChooseAreaFragment extends Fragment {
//fec03db205d3417ba114ef75a4f918f1
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel = -1;
    private StringBuilder nameTitle = new StringBuilder("");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    nameTitle.append(selectedCity.getCityName() + "-");
                    queryCounties();
                }else if(currentLevel == LEVEL_COUNTRY){
                    String weatherId = countyList.get(position).getWeatherId();
                    int index = nameTitle.toString().indexOf("-");
                    nameTitle.delete(index+1,nameTitle.length());
                    nameTitle.append(countyList.get(position).getCountryName() + "");
                    String title = nameTitle.toString();
                    if(getActivity() instanceof MainActivity){
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString("choose", weatherId);
                        editor.putString("choosed", weatherId);
                        editor.apply();

                        WeatherIdlist weatherIdlist = new WeatherIdlist();
                        weatherIdlist.setTitleName(title);
                        weatherIdlist.setIsLocked(true);
                        //Toast.makeText(MyApplication.getContext(), weatherId, Toast.LENGTH_SHORT).show();
                        weatherIdlist.setmWeatherId(weatherId);
                        weatherIdlist.save();
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("choose", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity activity = (WeatherActivity)getActivity();
                        SharedPreferences perf = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        if(!(perf.getString("choose",null).equals(weatherId))) {//判定选择城市有没有重复
                            WeatherIdlist weatherIdlist = new WeatherIdlist();
                            weatherIdlist.setTitleName(title);
                            weatherIdlist.setmWeatherId(weatherId);
                            weatherIdlist.save();
                        }
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString("choose", weatherId);
                        editor.apply();
                        activity.drawerLayout.closeDrawers();
                        activity.requestWeather(weatherId);
                        activity.fragmentChooseArea.setVisibility(View.GONE);
                    }

                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_COUNTRY){
                    nameTitle.setLength(0);
                    nameTitle.append("");
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    nameTitle.setLength(0);
                    nameTitle.append("");
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
            String address = "http://guolin.tech/api/china";
            queryFormServer(address,"province");
        }
    }
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            dataList.clear();
            for(City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFormServer(address,"city");
        }
    }

    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            dataList.clear();
            for(County county:countyList){
                dataList.add(county.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTRY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFormServer(address,"county");
        }

    }

    private void queryFormServer(String address, final String county) {
        showProgressDialog();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if("province".equals(county)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if("city".equals(county)){
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if("county".equals(county)){
                    result = Utility.handleCountryResponse(responseText,selectedCity.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(county)){
                                queryProvinces();
                            }else if("city".equals(county)){
                                queryCities();
                            }else if("county".equals(county)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载。。。");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
