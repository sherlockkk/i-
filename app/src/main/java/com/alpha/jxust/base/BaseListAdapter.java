package com.alpha.jxust.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/3/3.
 * @e-mail 1129574214@qq.com
 */
public class BaseListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater inflater;
    public List<T> datas = new ArrayList<T>();

    public BaseListAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public BaseListAdapter(Context context, List<T> datas) {
        mContext = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void addAll(List<T> subDatas) {
        datas.addAll(subDatas);
        notifyDataSetChanged();
    }

    public void add(T object) {
        datas.add(object);
        notifyDataSetChanged();
    }

    public void remove(int postion) {
        datas.remove(postion);
        notifyDataSetChanged();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
