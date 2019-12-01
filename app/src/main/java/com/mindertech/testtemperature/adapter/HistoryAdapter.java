package com.mindertech.testtemperature.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mindertech.testtemperature.MainActivity;
import com.mindertech.testtemperature.R;
import com.mindertech.testtemperature.model.Temperature;
import com.mindertech.testtemperature.utils.RealmUtils;
import com.mindertech.testtemperature.utils.Utils;

import java.util.List;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.adapter
 * @anthor xiangxia
 * @time 2019-11-27 16:44
 * @description 描述
 */
public class HistoryAdapter extends BaseAdapter {

    private Context context;//上下文对象
    private List<Temperature> dataList;//ListView显示的数据

    public HistoryAdapter(Context context, List<Temperature> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setData(List<Temperature> list) {
        if (null == list) {
            return;
        }
        dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Temperature temperature = dataList.get(position);

        TextView timeView;
        TextView temperatureView;
        //判断是否有缓存
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history, null);
        }
        timeView = (TextView) convertView.findViewById(R.id.tv_time);
        temperatureView = (TextView) convertView.findViewById(R.id.tv_temperature);

        timeView.setText(Utils.stampToDate(temperature.getTimestamp()));
        temperatureView.setText(temperature.getTemperature() + " °C");

        return convertView;
    }
}
