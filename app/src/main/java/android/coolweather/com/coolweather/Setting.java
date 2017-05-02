package android.coolweather.com.coolweather;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

public class Setting extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private Switch sw1,sw2,sw3;
    private ImageButton imageButton;
    private ImageView background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        imageButton = (ImageButton)findViewById(R.id.ibSetting) ;
        background = (ImageView)findViewById(R.id.ivsettingbackground);

        sw1 = (Switch)findViewById(R.id.switch1);
        sw2 = (Switch)findViewById(R.id.switch2);
        sw3 = (Switch)findViewById(R.id.switch3);

        sw1.setOnCheckedChangeListener(this);
        sw2.setOnCheckedChangeListener(this);
        sw3.setOnCheckedChangeListener(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int action = buttonView.getId();
        switch (action){
            case R.id.switch1:
                if(isChecked){
                    WeatherActivity.START_REFRESH = true;
                }else if(!isChecked){
                    WeatherActivity.START_REFRESH = false;
                }
                break;
            case R.id.switch2:
                if(isChecked){
                    WeatherActivity.BACK_REFRESH = true;
                }else if(!isChecked){
                    WeatherActivity.BACK_REFRESH = false;
                }
                break;
            case R.id.switch3:
                if(isChecked){
                    WeatherActivity.TONGZHI = true;
                }else if(!isChecked){
                    WeatherActivity.TONGZHI = false;
                }
                break;
        }
    }

}
