package com.alpha.jxust.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.jxust.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/2/9.
 * @e-mail 1129574214@qq.com
 */
public class MenuAdpater extends BaseAdapter {
    private Context context;

    private List<String> items;
    private int[] pics;


    public MenuAdpater(Context context) {
        this.context = context;

        items = new ArrayList<>();
        items.add("item");
        items.add("item");
        items.add("item");
        items.add("item");
        items.add("item");
        pics = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_listview, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_lv_menu);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_lv_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(pics[position]);
        viewHolder.textView.setText(items.get(position) + position + "");
        //viewHolder.textView.setText("111111");
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
