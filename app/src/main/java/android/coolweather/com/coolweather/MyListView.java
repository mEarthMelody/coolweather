package android.coolweather.com.coolweather;

import android.content.Context;
import android.coolweather.com.coolweather.db.WeatherIdlist;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hasee on 2017/5/1.
 */

public class MyListView extends ArrayAdapter<WeatherIdlist> {

    List<WeatherIdlist> weatherIdlists;
    private int resourceId;

    public MyListView(Context context, int resource, List<WeatherIdlist> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.weatherIdlists = objects;
    }

    //    resourceId = resource;
//    this.weatherIdlists = weatherIdlists;
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherIdlist weather = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView  == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.ivList);
            viewHolder.temp = (TextView) view.findViewById(R.id.tvListTemp);
            viewHolder.lock = (ImageView)view.findViewById(R.id.ivListLock);
            viewHolder.title = (TextView)view.findViewById(R.id.tvListTitle);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        if(!"".equals(weather.getTitleName())){
            viewHolder.title.setText(weather.getTitleName() + "");
        }

        if(weather.getIsLocked()){
            viewHolder.lock.setImageResource(R.drawable.lock);
        }
        if(!"".equals(weather.getCode() + "")){
            ChoosePicture.setPicture(weather.getCode(),viewHolder.imageView);
        }
        if(!"".equals(weather.getTemp() + "")){
            viewHolder.temp.setText(weather.getTemp() + "℃");
        }
        return view;

    }
    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView temp;
        ImageView lock;
    }
}
