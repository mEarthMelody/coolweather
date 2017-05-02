package android.coolweather.com.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.coolweather.com.coolweather.db.WeatherIdlist;
import android.coolweather.com.coolweather.gson.Daily;
import android.coolweather.com.coolweather.gson.HeWeather;
import android.coolweather.com.coolweather.gson.Hourly;
import android.coolweather.com.coolweather.util.HttpUtil;
import android.coolweather.com.coolweather.util.Utility;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.widget.Toast.makeText;


public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    public static boolean START_REFRESH = true;
    public static boolean BACK_REFRESH = true;
    public static boolean TONGZHI = true;

    private static boolean REFRESH_SUCCESSFUL = false;
    private SwipeRefreshLayout srFresg;
    private List<Hourly> hourlyList;
    private List<Daily> dailyList;
    private RecyclerView recycler;
    private View aqiview;
    String weatherid;
    private String mgetData;
    private boolean isFail = false;
    private ScrollView weatherLayout;
    private LinearLayout forecastLayout;
    private ImageView background,titleBackground,ivDaily1,ivDaily2,ivTitleWeather;
    public DrawerLayout drawerLayout;
    private HeWeather heWeather;
    private TextView tvNowtemp,tvNowmax,tvNowmin,tvNowday,tvNowweather,toolbartitle,
                     tvDailydate,tvDailymaxtemp,tvDailymintemp,tvDailyWind,tvDailyWindLevel,tvDailyWindSpeed,tvDailyhum,
                     tvDailypop,tvDailyPres,tvDailyvis,tvDailyuv, tvDailydate2,tvDailymaxtemp2,tvDailymintemp2,tvDailyWind2,tvDailyWindLevel2,tvDailyWindSpeed2,tvDailyhum2,
                     tvDailypop2,tvDailyPres2,tvDailyvis2,tvDailyuv2,
                     tvAQI,tvAQIaqi,tvAQIpm25,tvAQIno2,tvAQIo3,tvAQIpm10,tvAQIso2,
                     tvSuair,tvSucomf,tvSucw,tvSudrsg,tvSuflu,tvSusport,tvSutrav,tvSuuv,
                     tvTodayrise,tvTodaydown,tvTopop,tvTohum,tvTodir,tvTofl,tvTopcpn,tvTopres,tvTovis,tvTouv,tvbtcancle;
    private Button btAddCity;
    private String getData;
    public View fragmentChooseArea;
    public String title;
    private ListView lvListChoose;
    private String nowweatherid;
    private MyListView mylistview;
    private List<WeatherIdlist> weatherIdlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        drawerLayout = (DrawerLayout)findViewById(R.id.drawable);
        toolbartitle = (TextView)findViewById(R.id.toolbartitle);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        srFresg = (SwipeRefreshLayout) findViewById(R.id.srFresg);
        fragmentChooseArea = findViewById(R.id.choose_area_fragment2);
        background = (ImageView) findViewById(R.id.background);
        fragmentChooseArea.setVisibility(View.GONE);
        lvListChoose = (ListView)findViewById(R.id.lvListChoose);
        tvbtcancle = (TextView)findViewById(R.id.tvButtoncancle);
        aqiview = findViewById(R.id.aqilayout);
        srFresg.setColorSchemeResources(R.color.fresh);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        weatherid = intent.getStringExtra("choose");
        nowweatherid = weatherid;
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu1);
        }
        init();
        listener();
        if(START_REFRESH){
            requestWeather(weatherid);
        }else{
            HeWeather weather = readHistoryData();
            showWeatherInfo(weather);
        }
        srFresg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                REFRESH_SUCCESSFUL = true;
                requestWeather(nowweatherid);
            }
        });
    }

    private void listener() {
        btAddCity.setOnClickListener(this);
        tvbtcancle.setOnClickListener(this);
    }

    private void init() {
        btAddCity = (Button)findViewById(R.id.btAddCity);
        ivTitleWeather = (ImageView)findViewById(R.id.ivTitleWeather);
        tvDailydate = (TextView)findViewById(R.id.tvDailyDate);
        tvDailyhum = (TextView)findViewById(R.id.tvDailyhum);
        tvDailymaxtemp = (TextView)findViewById(R.id.tvDailyMaxTemp);
        tvDailymintemp = (TextView)findViewById(R.id.tvDailyMinTemp);
        tvDailypop = (TextView)findViewById(R.id.tvDailypop);
        tvDailyPres = (TextView)findViewById(R.id.tvDailypres);
        tvDailyuv = (TextView)findViewById(R.id.tvDailyuv);
        tvDailyvis = (TextView)findViewById(R.id.tvDailyvis);
        tvDailyWind = (TextView)findViewById(R.id.tvDailyWinddir);
        tvDailyWindLevel = (TextView)findViewById(R.id.tvDailyWindsc);
        tvDailyWindSpeed = (TextView)findViewById(R.id.tvDailyWindspd);
        ivDaily1 = (ImageView)findViewById(R.id.ivDaily);
        tvDailydate2 = (TextView)findViewById(R.id.tvDailyDate2);
        tvDailyhum2 = (TextView)findViewById(R.id.tvDailyhum2);
        tvDailymaxtemp2 = (TextView)findViewById(R.id.tvDailyMaxTemp2);
        tvDailymintemp2 = (TextView)findViewById(R.id.tvDailyMinTemp2);
        tvDailypop2 = (TextView)findViewById(R.id.tvDailypop2);
        tvDailyPres2 = (TextView)findViewById(R.id.tvDailypres2);
        tvDailyuv2 = (TextView)findViewById(R.id.tvDailyuv2);
        tvDailyvis2 = (TextView)findViewById(R.id.tvDailyvis2);
        tvDailyWind2 = (TextView)findViewById(R.id.tvDailyWinddir2);
        tvDailyWindLevel2 = (TextView)findViewById(R.id.tvDailyWindsc2);
        tvDailyWindSpeed2 = (TextView)findViewById(R.id.tvDailyWindspd2);
        ivDaily2 = (ImageView)findViewById(R.id.ivDaily2);

        tvAQI = (TextView)findViewById(R.id.tvAqi);
        tvAQIaqi = (TextView)findViewById(R.id.tvAqiaqi);
        tvAQIpm25 = (TextView)findViewById(R.id.tvAqipm25);
        tvAQIno2 = (TextView)findViewById(R.id.tvAqino2);
        tvAQIso2 = (TextView)findViewById(R.id.tvAqiso2);
        tvAQIo3 = (TextView)findViewById(R.id.tvAqio3);
        tvAQIpm10 = (TextView)findViewById(R.id.tvAqipm10);

        tvSuair = (TextView)findViewById(R.id.tvSuggestAir);
        tvSucomf = (TextView)findViewById(R.id.tvSuggestcomf);
        tvSucw = (TextView)findViewById(R.id.tvSuggestcw);
        tvSudrsg = (TextView)findViewById(R.id.tvSuggestdrsg);
        tvSuflu = (TextView)findViewById(R.id.tvSuggestflu);
        tvSusport = (TextView)findViewById(R.id.tvSuggestsport);
        tvSutrav = (TextView)findViewById(R.id.tvSuggesttrav);
        tvSuuv = (TextView)findViewById(R.id.tvSuggestuv);

        tvTodayrise = (TextView)findViewById(R.id.tvTodaysr);
        tvTodaydown = (TextView)findViewById(R.id.tvTodayss);
        tvTodir = (TextView)findViewById(R.id.tvTodayWindspd);
        tvTofl = (TextView)findViewById(R.id.tvTodayfl);
        tvTohum = (TextView)findViewById(R.id.tvTodayhum);
        tvTopcpn = (TextView)findViewById(R.id.tvTodaypcpn);
        tvTopop = (TextView)findViewById(R.id.tvTodaypop);
        tvTouv = (TextView)findViewById(R.id.tvTodayuv);
        tvTovis = (TextView)findViewById(R.id.tvTodayvis);
        tvTopres = (TextView)findViewById(R.id.tvTodaypres);

        tvNowday = (TextView)findViewById(R.id.tvNowToday);
        tvNowmax = (TextView)findViewById(R.id.tvNowMaxTemp);
        tvNowmin = (TextView)findViewById(R.id.tvNowMinTemp);
        tvNowtemp = (TextView)findViewById(R.id.tvNowTemp);
        tvNowweather = (TextView)findViewById(R.id.tvNowWeather);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weathertoolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.setting:
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                int w = display.getWidth();
                int h = display.getHeight();

                Bitmap bmp = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888);
                View decorview = this.getWindow().getDecorView();
                decorview.setDrawingCacheEnabled(true);
                bmp = decorview.getDrawingCache();
                break;
        }
        return true;
    }
        public void requestWeather(String weatherId) {
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city=" + weatherId + "&key=fec03db205d3417ba114ef75a4f918f1";
                HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iffail();
                            }
                        });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                getData = responseText;
                mgetData = responseText;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MyApplication.getContext(), responseText, Toast.LENGTH_SHORT).show();
//                    }
//                });
                final HeWeather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null){
                            showWeatherInfo(weather);
                        }
                    }
                });
            }
        });
    }

    private void iffail() {
        isFail = true;
        Toast toast = makeText(MyApplication.getContext(), "天气获取失败，请稍后刷新重试", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        REFRESH_SUCCESSFUL = false;
        HeWeather weather = readHistoryData();
        showWeatherInfo(weather);
    }
    private HeWeather readHistoryData(){
        HeWeather weather = null;
        SharedPreferences perf = PreferenceManager.getDefaultSharedPreferences(this);
        String data = perf.getString("getData",null);
        mgetData = data;
        if(!"".equals(data)){
            weather = Utility.handleWeatherResponse(data);
        }else{
            finish();
        }
        return weather;
    }

    private void showWeatherInfo(HeWeather mweather) {
        HeWeather weather = mweather;
        SharedPreferences perf = PreferenceManager.getDefaultSharedPreferences(this);
        if(!"ok".equals(weather.status)) {
            isFail = true;
            Toast toast = makeText(this, "服务器返回数据有误，请稍后刷新重试", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            String data = perf.getString("getData",null);
            if(!"".equals(data)){
                weather = Utility.handleWeatherResponse(data);
            }
        }else{
            isFail = false;
            SharedPreferences.Editor editor = perf.edit();
            editor.putString("getData",getData);
            editor.apply();
            if(REFRESH_SUCCESSFUL == true){
                Toast toast = makeText(MyApplication.getContext(), "刷新成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        }
        String weatherid = weather.basic.weatherId;
        List<WeatherIdlist> mlist = DataSupport.where("mWeatherId = ?",weatherid).find(WeatherIdlist.class);
        WeatherIdlist weatherIdlist = mlist.get(0);
        toolbartitle.setText(weatherIdlist.getTitleName()+"");
        int timee = Integer.parseInt(weather.basic.update.updateTime.substring(11,13));
        if(timee>=0 && timee<=6)
            background.setImageResource(R.drawable.night);
        else if(timee>6 && timee <= 18)
            background.setImageResource(R.drawable.weather);
        else{
            background.setImageResource(R.drawable.night2);
        }
        String codee = weather.now.cond.code + "";
        if("300".equals(codee)||"301".equals(codee)||"302".equals(codee)
                ||"303".equals(codee)||"304".equals(codee)||"305".equals(codee)||"306".equals(codee)||"307".equals(codee)||"308".equals(codee)
                ||"309".equals(codee)||"310".equals(codee)||"311".equals(codee)||"312".equals(codee)||"313".equals(codee)){
            background.setImageResource(R.drawable.rain);
        }

        WeatherIdlist weatheridlist = new WeatherIdlist();
        weatheridlist.setCode(weather.now.cond.code + "");
        weatheridlist.setTemp(weather.now.tmp + "");
        weatheridlist.updateAll("mWeatherId = ?", weather.basic.weatherId + "");

        hourlyList = weather.hourlyList;
        dailyList = weather.dailyList;
        recycler = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(hourlyList);
        recycler.setAdapter(adapter);
        ChoosePicture.setPicture(weather.now.cond.code, ivTitleWeather);
        nowweatherid = weather.basic.weatherId;

        Daily daily = dailyList.get(0);
        tvNowtemp.setText(weather.now.tmp + "℃");
        tvNowweather.setText(weather.now.cond.txt);
        tvNowmin.setText(daily.tmp.min);
        tvNowmax.setText(daily.tmp.max);
        tvNowday.setText(Weekday.getday(daily.date));
        tvTodayrise.setText(daily.astro.sr);
        tvTodaydown.setText(daily.astro.ss);
        tvTouv.setText(daily.uv);
        tvTopop.setText(daily.pop + "%");
        daily = dailyList.get(1);
        tvDailyuv.setText("紫外线指数：" + daily.uv);
        tvDailyWindLevel.setText(daily.wind.sc + "级");
        tvDailyWind.setText(daily.wind.dir);
        tvDailyWindSpeed.setText(daily.wind.spd + "m/s");
        tvDailypop.setText("降水概率:" + daily.pop + "%");
        tvDailymaxtemp.setText(daily.tmp.max);
        tvDailymintemp.setText(daily.tmp.min);
        tvDailyvis.setText("能见度" + daily.vis + "km");
        tvDailydate.setText(daily.date);
        tvDailyPres.setText("气压："+daily.pres+"百帕");
        tvDailyhum.setText("湿度："+ daily.hum + "%");
        ChoosePicture.setPicture(daily.cond.coded,ivDaily1);
        daily = dailyList.get(2);
        tvDailyuv2.setText("紫外线指数：" + daily.uv);
        tvDailyWindLevel2.setText(daily.wind.sc + "级");
        tvDailyWind2.setText(daily.wind.dir);
        tvDailyWindSpeed2.setText(daily.wind.spd + "m/s");
        tvDailypop2.setText("降水概率:" + daily.pop + "%");
        tvDailymaxtemp2.setText(daily.tmp.max);
        tvDailymintemp2.setText(daily.tmp.min);
        tvDailyvis2.setText("能见度" + daily.vis + "km");
        tvDailydate2.setText(daily.date);
        tvDailyPres2.setText("气压："+daily.pres+"百帕");
        tvDailyhum2.setText("湿度："+ daily.hum + "%");
        ChoosePicture.setPicture(daily.cond.coded,ivDaily2);
        srFresg.setRefreshing(false);

        if(mgetData.indexOf("aqi") != -1){
            aqiview.setVisibility(View.VISIBLE);
            tvAQIpm10.setText("pm10:"+weather.aqi.city.pm10);
            tvAQI.setText(weather.aqi.city.qlty);
            tvAQIo3.setText("o3:" + weather.aqi.city.o3);
            tvAQIso2.setText("so2:"+weather.aqi.city.so2);
            tvAQIno2.setText("no2:"+weather.aqi.city.no2);
            tvAQIpm25.setText(weather.aqi.city.pm25);
            tvAQIaqi.setText(weather.aqi.city.aqi);
        }else{
            aqiview.setVisibility(View.GONE);
        }


        tvSuuv.setText("紫外线指数：" + weather.suggestion.uv.brf + "   " + weather.suggestion.uv.txt);
        tvSutrav.setText("旅游建议：" + weather.suggestion.trav.brf + "   " + weather.suggestion.trav.txt);
        tvSusport.setText("运动建议：" + weather.suggestion.sport.brf + "   " + weather.suggestion.sport.txt);
        tvSuflu.setText("感冒可能：" + weather.suggestion.flu.brf + "   " + weather.suggestion.flu.txt);
        tvSuair.setText("空气质量：" + weather.suggestion.air.brf + "   " + weather.suggestion.air.txt);
        tvSucomf.setText("体感：" + weather.suggestion.comf.brf + "   " + weather.suggestion.comf.txt);
        tvSudrsg.setText("着装建议：" + weather.suggestion.drsg.brf + "   " + weather.suggestion.drsg.txt);
        tvSucw.setText("洗车建议：" + weather.suggestion.cw.brf + "   " + weather.suggestion.cw.txt);

        tvTodir.setText(weather.now.wind.dir + "  " + weather.now.wind.sc + "米/秒");
        tvTofl.setText(weather.now.fl +"°");
        tvTopcpn.setText(weather.now.pcpn + "厘米");
        tvTohum.setText(weather.now.hum + "%");
        tvTopres.setText(weather.now.pres + "百帕");
        tvTovis.setText(weather.now.vis + "千米");

        weatherIdlists = DataSupport.findAll(WeatherIdlist.class);
//        final List<WeatherIdlist> weatherIdlistss = weatherIdlists;
        mylistview = new MyListView(MyApplication.getContext(),R.layout.chooselist,weatherIdlists);
        //Toast.makeText(this, weatherIdlists.size() + "", Toast.LENGTH_SHORT).show();
        lvListChoose.setAdapter(mylistview);
        lvListChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idd = weatherIdlists.get(position).getmWeatherId();
                requestWeather(idd);
                drawerLayout.closeDrawers();
            }
        });
        lvListChoose.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idd = weatherIdlists.get(position).getmWeatherId();
                if(position != 0){
                    Snackbar.make(view,"是否删除当前城市？",Snackbar.LENGTH_SHORT)
                            .setAction("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mdeleteData(idd);

                                }
                            }).show();
                }
                return true;
            }
        });

    }

    private void mdeleteData(String idd) {
        List<WeatherIdlist> mm  = DataSupport.where("mWeatherId = ?",idd).find(WeatherIdlist.class);
//        Toast.makeText(this, mm.size() + "", Toast.LENGTH_SHORT).show();
        if(mm.size() != 0){
            DataSupport.deleteAll(WeatherIdlist.class,"mWeatherId = ?",idd);
        }
//        weatherIdlists = DataSupport.findAll(WeatherIdlist.class);
        SharedPreferences perf = PreferenceManager.getDefaultSharedPreferences(this);
        String id = perf.getString("choosed",null);
        requestWeather(id);
//        mylistview.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int action = v.getId();
        switch(action){
            case R.id.btAddCity:
                fragmentChooseArea.setVisibility(View.VISIBLE);
                tvbtcancle.setVisibility(View.VISIBLE);
                break;
            case R.id.tvButtoncancle:
                fragmentChooseArea.setVisibility(View.GONE);
                break;
        }
    }
}
