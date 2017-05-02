package android.coolweather.com.coolweather;

import android.coolweather.com.coolweather.gson.Hourly;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hasee on 2017/4/29.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Hourly> hourlyList;
    private String tvHourtemp,tvHourdate,tvHourhum,tvHourpres,tvHourdir,tvHoursc,picture;

    public MyAdapter(List<Hourly> hourly){
        hourlyList = hourly;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Hourly hourly = hourlyList.get(position);
        ChoosePicture.setPicture(hourly.cond.code,holder.imageView);
        holder.tvHourdate.setText(hourly.date.substring(11));
        holder.tvHourdir.setText(hourly.wind.dir);
        holder.tvHoursc.setText(hourly.wind.sc + "级");
        holder.tvHourhum.setText("湿度：" + hourly.hum);
        holder.tvHourpres.setText("气压:" + hourly.pres);
        holder.tvHourtemp.setText(hourly.tmp + "℃");
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvHourtemp,tvHourdate,tvHourhum,tvHourpres,tvHourdir,tvHoursc;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.ivHourly);
            tvHourdate = (TextView)itemView.findViewById(R.id.tvHourlyTime);
            tvHourdir = (TextView)itemView.findViewById(R.id.tvHourlydir);
            tvHourhum = (TextView)itemView.findViewById(R.id.tvHourlyhum);
            tvHourpres = (TextView)itemView.findViewById(R.id.tvHourlypres);
            tvHoursc = (TextView)itemView.findViewById(R.id.tvHourlysc);
            tvHourtemp = (TextView)itemView.findViewById(R.id.tvHourlyTemp);
        }
    }
}
