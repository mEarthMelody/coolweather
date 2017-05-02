package android.coolweather.com.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString("choose",null) != null){
            Intent intent = new Intent(this,WeatherActivity.class);
            intent.putExtra("choose",prefs.getString("choose",null));
            startActivity(intent);
            finish();
        }else{
            Toast toast = Toast.makeText(MainActivity.this,"请选择所在城市",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
